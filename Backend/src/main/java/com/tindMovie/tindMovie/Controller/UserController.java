package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.RoleEntity;
import com.tindMovie.tindMovie.Model.UserRoleEntity;
import com.tindMovie.tindMovie.Model.UsersEntity;
import com.tindMovie.tindMovie.Repository.RoleRepository;
import com.tindMovie.tindMovie.Repository.UserRepository;
import com.tindMovie.tindMovie.Repository.UserRoleRepository;
import com.tindMovie.tindMovie.Service.UserService;
import com.tindMovie.tindMovie.Service.FileService.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import java.security.MessageDigest;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRoleRepository userRoleRepository;

  /**
   * Attribut permettant d'utiliser le système de log "slf4j" (API de
   * journalisation Java)
   * Pour plus d'informations sur slf4j ->
   * https://www.baeldung.com/slf4j-with-log4j2-logback
   */
  Logger logger = LoggerFactory.getLogger(FileStorageService.class);

  @Autowired
  private FileStorageService fileStorageService;

  @CrossOrigin
  @PostMapping(value = "/stations", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
  @ResponseStatus(HttpStatus.OK)
  public UsersEntity post(@ModelAttribute UsersEntity user) {
    try {
      // on récupère l'image provenant de la classe user (traitement automatique à
      // partir de la requête)
      MultipartFile imageFile = user.getImageFile();
      if (!imageFile.isEmpty()) {
        logger.info("Sauvegarde du fichier image");

        // calcul du hash du fichier pour obtenir un nom unique
        String storageHash = getStorageHash(imageFile).get();
        Path rootLocation = this.fileStorageService.getRootLocation();
        // récupération de l'extension
        String fileExtension = mimeTypeToExtension(imageFile.getContentType());
        // ajout de l'extension au nom du fichier
        storageHash = storageHash + fileExtension;
        // on retrouve le chemin de stockage de l'image
        Path saveLocation = rootLocation.resolve(storageHash);

        // suppression du fichier au besoin
        Files.deleteIfExists(saveLocation);

        // tentative de sauvegarde
        Files.copy(imageFile.getInputStream(), saveLocation);

        // à ce niveau il n'y a pas eu d'exception
        // on ajoute le nom utilisé pour stocké l'image
        user.setImageName(storageHash);
      }

      // Vérifier si l'e-mail est déjà utilisé
      UsersEntity existingUser = userRepository.findByEmail(user.getEmail());
      if (existingUser != null) {
        // L'e-mail est déjà utilisé, renvoyer une erreur
        throw new RuntimeException("L'adresse e-mail est déjà utilisée.");
      }
      String shareCode = userService.generateRandomCode(5);
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      String passwordEncode = bCryptPasswordEncoder.encode(user.getPassword());
      user.setPassword(passwordEncode);
      RoleEntity userRole = roleRepository.findByName("ROLE_USER");
      UsersEntity savedUser = userRepository.save(user);
      if (userRole == null) {
        throw new RuntimeException("Role introuvable");
      }
      UserRoleEntity userRoleEntity = new UserRoleEntity();
      userRoleEntity.setUserId(savedUser);
      userRoleEntity.setRole(userRole);
      user.setShareCode(shareCode);
      userRoleRepository.save(userRoleEntity);
      return userRepository.save(user);

    } catch (IOException e) {
      logger.error(e.getMessage());
    }

    // Si on arrive là alors erreur
    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Impossible de sauvegarder la ressource.");
  }

  @GetMapping
  @CrossOrigin
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<Long> findUserIdByEmail(@RequestParam String email) {
    UsersEntity user = userRepository.findByEmail(email);
    if (user != null) {
      return ResponseEntity.ok(user.getId());
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @GetMapping("/getUserById/{userId}")
  @CrossOrigin
  public ResponseEntity<?> getUserById(@PathVariable Long userId) {
    Optional<UsersEntity> userOptional = userRepository.findById(userId);

    if (userOptional.isPresent()) {
      UsersEntity user = userOptional.get();
      user.setIdPartenaire(user.getPartenaire().getId());

      if (user != null) {
        return ResponseEntity.ok(user);
      }
    }
    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body("Utilisateur introuvable");
  }

  @GetMapping("/{userId}/partenaire")
  public ResponseEntity<String> getPartenaireNameByUserId(
      @PathVariable Long userId) {
    Optional<UsersEntity> userOptional = userRepository.findById(userId);

    if (userOptional.isPresent()) {
      UsersEntity user = userOptional.get();
      String partenaireUsername = user.getPartenaire().getUsername();

      if (partenaireUsername != null) {
        return ResponseEntity.ok(partenaireUsername);
      } else {
        return ResponseEntity.notFound().build();
      }
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @CrossOrigin
  @PostMapping(value = "/inscription", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public UsersEntity createUser(@RequestBody UsersEntity users) {
    // Vérifier si l'e-mail est déjà utilisé
    UsersEntity existingUser = userRepository.findByEmail(users.getEmail());
    if (existingUser != null) {
      // L'e-mail est déjà utilisé, renvoyer une erreur
      throw new RuntimeException("L'adresse e-mail est déjà utilisée.");
    }
    String shareCode = userService.generateRandomCode(5);
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    String passwordEncode = bCryptPasswordEncoder.encode(users.getPassword());
    users.setPassword(passwordEncode);
    RoleEntity userRole = roleRepository.findByName("ROLE_USER");
    UsersEntity savedUser = userRepository.save(users);
    if (userRole == null) {
      throw new RuntimeException("Role introuvable");
    }
    UserRoleEntity userRoleEntity = new UserRoleEntity();
    userRoleEntity.setUserId(savedUser);
    userRoleEntity.setRole(userRole);
    users.setShareCode(shareCode);
    userRoleRepository.save(userRoleEntity);
    return userRepository.save(users);
  }

  @CrossOrigin
  @PutMapping("/link")
  public ResponseEntity<String> linkUsers(
      @RequestParam String shareCode,
      @RequestParam Long userId) {
    UsersEntity user = userRepository.findByShareCode(shareCode);

    if (user == null) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("Le code de partage n'est pas valide.");
    }

    // Assure de récupérer l'utilisateur à lier en fonction de son ID
    UsersEntity userToLink = userRepository.findById(userId).orElse(null);

    if (userToLink == null) {
      return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body("L'utilisateur à lier n'existe pas.");
    }

    // Mettre à jour l'utilisateur qui possède le code de partage pour y ajouter
    // l'utilisateur à lier
    user.setPartenaire(userToLink);

    // Mettre à jour l'utilisateur à lier pour enregistrer l'ID de l'utilisateur qui
    // a entré le code
    userToLink.setPartenaire(user);

    // Enregistre les mises à jour dans la base de données
    userRepository.save(user);
    userRepository.save(userToLink);

    return ResponseEntity.ok("Les utilisateurs ont été liés avec succès.");
  }

  @GetMapping("/linkedUsername")
  public ResponseEntity<String> getLinkedUsername(@RequestParam Long userId) {
    UsersEntity user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      return ResponseEntity.badRequest().body("L'utilisateur n'existe pas.");
    }
    UsersEntity partenaire = user.getPartenaire();
    if (partenaire == null) {
      return ResponseEntity.ok("Vous n'êtes lié à aucun utilisateur.");
    }
    String linkedUsername = partenaire.getUsername();
    return ResponseEntity.ok(linkedUsername);
  }

  /**
   * Retourne l'extension d'un fichier en fonction d'un type MIME
   * pour plus d'informations sur les types MIME :
   * https://developer.mozilla.org/fr/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
   */
  private String mimeTypeToExtension(String mimeType) {
    return switch (mimeType) {
      case "image/jpeg" -> ".jpeg";
      case "image/png" -> ".png";
      case "image/svg" -> ".svg";
      default -> "";
    };
  }

  /**
   * Permet de retrouver un hash qui pourra être utilisé comme nom de fichier
   * uniquement pour le stockage.
   *
   * Le hash sera calculé à partir du nom du fichier, de son type MIME
   * (https://developer.mozilla.org/fr/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types)
   * et de la date d'upload.
   *
   * @return Le hash encodé en base64
   */
  private Optional<String> getStorageHash(MultipartFile file) {
    String hashString = null;

    if (!file.isEmpty()) {
      try {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        // La méthode digest de la classe "MessageDigest" prend en paramètre un byte[]
        // il faut donc transformer les différents objets utilisés pour le hachage en
        // tableau d'octets
        // Nous utiliserons la classe "ByteArrayOutputStream" pour se faire
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(file.getOriginalFilename().getBytes());
        outputStream.write(file.getContentType().getBytes());
        LocalDate date = LocalDate.now();
        outputStream.write(date.toString().getBytes());

        // calcul du hash, on obtient un tableau d'octets
        byte[] hashBytes = messageDigest.digest(outputStream.toByteArray());

        // on retrouve une chaîne de caractères à partir d'un tableau d'octets
        hashString = String.format("%032x", new BigInteger(1, hashBytes));
      } catch (NoSuchAlgorithmException | IOException e) {
        logger.error(e.getMessage());
      }
    }

    return Optional.ofNullable(hashString);
  }

}

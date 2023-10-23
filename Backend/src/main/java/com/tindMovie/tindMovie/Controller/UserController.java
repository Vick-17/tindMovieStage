package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.RoleEntity;
import com.tindMovie.tindMovie.Model.UserRoleEntity;
import com.tindMovie.tindMovie.Model.UsersEntity;
import com.tindMovie.tindMovie.Repository.RoleRepository;
import com.tindMovie.tindMovie.Repository.UserRepository;

import com.tindMovie.tindMovie.Repository.UserRoleRepository;
import com.tindMovie.tindMovie.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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


    @GetMapping("/allUser")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Iterable<UsersEntity> get() {
        return userRepository.findAll();
    }

    @GetMapping("/getUserById/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public Optional<UsersEntity> getUserById(@PathVariable Long userId) {
        return userRepository.findById(userId);
    }


    @CrossOrigin
    @PostMapping(value = "/inscription", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UsersEntity createUser(@RequestBody UsersEntity users) {
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
    public ResponseEntity<String> linkUsers(@RequestParam String shareCode, @RequestParam Long userId) {
        UsersEntity user = userRepository.findByShareCode(shareCode);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le code de partage n'est pas valide.");
        }

        // Assure de récupérer l'utilisateur à lier en fonction de son ID
        UsersEntity userToLink = userRepository.findById(userId).orElse(null);

        if (userToLink == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("L'utilisateur à lier n'existe pas.");
        }

        // Mettre à jour l'utilisateur qui possède le code de partage pour y ajouter l'utilisateur à lier
        user.setPartenaire(userToLink);

        // Mettre à jour l'utilisateur à lier pour enregistrer l'ID de l'utilisateur qui a entré le code
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


}

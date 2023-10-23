package com.tindMovie.tindMovie.Service;

import com.tindMovie.tindMovie.Model.UserRoleEntity;
import com.tindMovie.tindMovie.Model.UsersEntity;
import com.tindMovie.tindMovie.Repository.UserRepository;
import com.tindMovie.tindMovie.Repository.UserRoleRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

  private static final String USER_NOT_FOUND_MESSAGE = "user not found";
  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;

  @Override
  public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
    UsersEntity user = userRepository.findByEmail(email);
    if (user == null) {
      String message = String.format(USER_NOT_FOUND_MESSAGE, email);
      log.error(message);
      throw new UsernameNotFoundException(message);
    } else {
      log.debug("User found in the database: {}", email);

      // Récupérer les rôles associés à l'utilisateur
      List<UserRoleEntity> userRoles = userRoleRepository.findByUser_Id(
        user.getId()
      );

      // Convertir les rôles en SimpleGrantedAuthority
      Set<SimpleGrantedAuthority> authorities = userRoles
        .stream()
        .map(userRole ->
          new SimpleGrantedAuthority(userRole.getRole().getName())
        )
        .collect(Collectors.toSet());

      return new User(user.getEmail(), user.getPassword(), authorities);
    }
  }

  public String generateRandomCode(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder code = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int index = random.nextInt(characters.length());
      char randomChar = characters.charAt(index);
      code.append(randomChar);
    }

    return code.toString();
  }
}

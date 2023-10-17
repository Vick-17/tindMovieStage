package com.tindMovie.tindMovie.Repository;


import com.tindMovie.tindMovie.Model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginRepository extends JpaRepository<UsersEntity, Integer> {
    UsersEntity findByEmail(String email);
    UsersEntity findByusername(String username);
}

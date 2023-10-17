package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UsersEntity, Long> {
    UsersEntity findByEmail(String email);
    UsersEntity findByShareCode(String shareCode);
}

package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.RoleEntity;
import com.tindMovie.tindMovie.Model.UserRoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRoleEntity, Long> {
    List<UserRoleEntity> findByUser_Id(Long userId);
}

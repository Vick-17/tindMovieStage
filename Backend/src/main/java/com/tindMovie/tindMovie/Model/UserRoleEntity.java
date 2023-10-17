package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "user_role")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public UsersEntity getUserId() {
        return user;
    }

    public void setUserId(UsersEntity userId) {
        this.user = userId;
    }

    public String getName() {
        return null;
    }
}

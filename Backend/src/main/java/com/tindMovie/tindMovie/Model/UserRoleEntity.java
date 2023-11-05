package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

    public RoleEntity getRole() {
        return role;
    }

    public UsersEntity getUserId() {
        return user;
    }

    public void setUserId(UsersEntity userId) {
        this.user = userId;
    }
}

package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "badge")
public class BadgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String icon;
    private String description;

    @Column(name = "user_id")
    private Long userId;
}

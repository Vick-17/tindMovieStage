package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "match") // Assurez-vous que le nom de la table correspond à votre schéma de base de données
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id1")
    private Long userId1;

    @Column(name = "user_id2")
    private Long userId2;

    @Column(name = "film_id")
    private Long filmId;

}

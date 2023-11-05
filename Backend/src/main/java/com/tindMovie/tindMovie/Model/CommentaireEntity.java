package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "commentaire")
public class CommentaireEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "users_id")
    private Long usersId;

    @Column(name = "film_id")
    private Long filmId;
}

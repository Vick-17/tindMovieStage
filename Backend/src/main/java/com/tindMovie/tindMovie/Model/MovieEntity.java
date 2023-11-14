package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String dates;
    private String duree;
    private String image;

    private String synopsis;

    @Column(name = "commentaire_id")
    private Long commentaireId;

}

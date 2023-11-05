package com.tindMovie.tindMovie.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "nom_genre")
    private String nomGenre;

    @Column(name = "movie_ids")
    private Long[] movieIds;
}

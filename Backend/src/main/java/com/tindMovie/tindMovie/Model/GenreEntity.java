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

    @ElementCollection
    @CollectionTable(name = "genre_movie_ids", joinColumns = @JoinColumn(name = "genre_id"))
    @Column(name = "movie_id")
    private List<Long> movieIds;
}

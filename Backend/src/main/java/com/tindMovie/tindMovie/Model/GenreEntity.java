package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "genre")
public class GenreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "nom_genre")
    private String nomGenre;

    @Column(name = "movie_ids")
    private Long[] movieIds;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomGenre() {
        return this.nomGenre;
    }

    public void setNomGenre(String nomGenre) {
        this.nomGenre = nomGenre;
    }

    public Long[] getMovieIds() {
        return this.movieIds;
    }

    public void setMovieIds(Long[] movieIds) {
        this.movieIds = movieIds;
    }

}

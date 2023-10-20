package com.tindMovie.tindMovie.Model;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "realisator")
public class RealisatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "realisator_name")
    private String realisatorName;

    @Column(name = "movie_ids")
    private List<Long> movieIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealisatorName() {
        return realisatorName;
    }

    public void setRealisatorName(String realisatorName) {
        this.realisatorName = realisatorName;
    }

    public List<Long> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(List<Long> movieIds) {
        this.movieIds = movieIds;
    }
}

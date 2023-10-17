package com.tindMovie.tindMovie.Model;
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
    private Long[] movieIds;

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

    public Long[] getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(Long[] movieIds) {
        this.movieIds = movieIds;
    }
}

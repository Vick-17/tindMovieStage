package com.tindMovie.tindMovie.Model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "actor")
public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "movie_ids")
    private Long[] movieIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Long[] getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(Long[] movieIds) {
        this.movieIds = movieIds;
    }

}

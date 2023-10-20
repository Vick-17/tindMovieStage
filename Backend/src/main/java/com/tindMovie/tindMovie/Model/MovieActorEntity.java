package com.tindMovie.tindMovie.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie_actor")
public class MovieActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movie;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private ActorEntity actor;


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MovieEntity getMovie() {
        return this.movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }

    public ActorEntity getActor() {
        return this.actor;
    }

    public void setActor(ActorEntity actor) {
        this.actor = actor;
    }
    
}


package com.tindMovie.tindMovie.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "actor")
public class ActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "actor_name")
    private String actorName;

    @ElementCollection
    @CollectionTable(name = "actor_movie_ids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "movie_id")
    private List<Long> movieIds;
}

package com.tindMovie.tindMovie.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "realisator")
public class RealisatorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "realisator_name")
    private String realisatorName;

    @ElementCollection
    @CollectionTable(name = "realisator_movie_ids", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "movie_id")
    private List<Long> movieIds;

}

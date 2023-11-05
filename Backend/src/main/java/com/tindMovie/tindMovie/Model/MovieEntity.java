package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @Column(name = "note_moyenne")
    private Integer noteMoyenne;
    private String synopsis;

    @Column(name = "commentaire_id")
    private Long commentaireId;

}

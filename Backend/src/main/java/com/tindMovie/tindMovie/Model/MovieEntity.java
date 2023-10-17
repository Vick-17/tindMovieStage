package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
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

    @ManyToMany
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<ActorEntity> actors;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getDurée() {
        return duree;
    }

    public void setDurée(String durée) {
        this.duree = durée;
    }

    public Integer getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(Integer noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public Long getCommentaireId() {
        return commentaireId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCommentaireId(Long commentaireId) {
        this.commentaireId = commentaireId;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<ActorEntity> getActors() {
        return actors;
    }

    public void setActors(List<ActorEntity> actors) {
        this.actors = actors;
    }
}

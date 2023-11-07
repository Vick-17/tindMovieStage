package com.tindMovie.tindMovie.Model;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UsersEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private int age;
  private String email;
  private String password;
  private String username;
  private String country;
  private Long IdPartenaire;

  @Column(name = "image_name")
  private String imageName;

  /**
   * Attribut permettant de récupérer une image à partir d'une requête http
   * "multipart/data".
   * Attention, le contenu du fichier est stocké de manière temporaire (en mémoire
   * ou sur le disque)
   */
  @JsonIgnore
  @Transient
  private MultipartFile imageFile;

  @Column(name = "badge_id")
  private Long badgeId;

  @ManyToOne
  @JoinColumn(name = "partenaire_id", referencedColumnName = "id")
  @JsonIgnore
  private UsersEntity partenaire;

  @Column(name = "share_code")
  private String shareCode;

  public void setImageFile(MultipartFile imageFile) {
    this.imageFile = imageFile;
  }

  public MultipartFile getImageFile() {
    return imageFile;
  }

}

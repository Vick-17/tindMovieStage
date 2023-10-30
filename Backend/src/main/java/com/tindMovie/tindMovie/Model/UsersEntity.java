package com.tindMovie.tindMovie.Model;



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

  @Column(name = "badge_id")
  private Long badgeId;

  @ManyToOne
  @JoinColumn(name = "partenaire_id", referencedColumnName = "id")
  @JsonIgnore
  private UsersEntity partenaire;

  @Column(name = "share_code")
  private String shareCode;
}

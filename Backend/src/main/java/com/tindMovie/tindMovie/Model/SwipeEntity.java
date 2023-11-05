package com.tindMovie.tindMovie.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "swipe")
public class SwipeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "liked")
    private boolean liked;

    @Column(name = "not_liked")
    private boolean notLiked;

    @Column(name = "swipe_direction")
    private String swipeDirection;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "film_id")
    private Long filmId;

    @Column(name = "is_watched")
    private boolean isWatched;

}

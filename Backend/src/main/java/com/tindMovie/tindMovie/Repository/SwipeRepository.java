package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.SwipeEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SwipeRepository extends CrudRepository<SwipeEntity, Long> {
    boolean existsByUserIdAndFilmId(Long userId, Long filmId);

    List<SwipeEntity> findFilmIdsByUserId(Long userId);

    List<SwipeEntity> findByUserId(Long userId);

    Optional<SwipeEntity> findByUserIdAndFilmId(Long id, Long filmId);

    SwipeEntity findByFilmId(Long swipeId);
}

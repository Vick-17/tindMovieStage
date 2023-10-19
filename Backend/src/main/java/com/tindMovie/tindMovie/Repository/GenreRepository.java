package com.tindMovie.tindMovie.Repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tindMovie.tindMovie.Model.GenreEntity;

@Repository
public interface GenreRepository extends CrudRepository<GenreEntity, Long> {

    List<GenreEntity> findByMovieIdsContains(Long movieId);
    
}

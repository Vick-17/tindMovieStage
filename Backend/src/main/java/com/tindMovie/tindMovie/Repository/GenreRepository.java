package com.tindMovie.tindMovie.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tindMovie.tindMovie.Model.GenreEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;

@Repository
public interface GenreRepository extends CrudRepository<GenreEntity, Long> {

@Query("SELECT m FROM MovieEntity m WHERE m.id IN :movieIds")
List<MovieEntity> findMovieByGenre(@Param("movieIds") Long[] movieIds);
    
}

package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.MovieEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Long> {

    List<MovieEntity> findByIdIn(@Param("filmIds") List<Long> likedFilmIds);
}

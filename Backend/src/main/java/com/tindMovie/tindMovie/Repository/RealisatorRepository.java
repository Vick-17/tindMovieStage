package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RealisatorRepository extends CrudRepository<RealisatorEntity, Long> {
    @Query("SELECT m FROM MovieEntity m WHERE m.id IN :movieIds")
    List<MovieEntity> findMovieByRealisator(@Param("movieIds") Long[] movieIds);

}

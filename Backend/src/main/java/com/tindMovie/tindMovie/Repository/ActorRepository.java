package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends CrudRepository<ActorEntity, Long> {

    @Query("SELECT m FROM MovieEntity m WHERE m.id IN :movieIds")
    List<MovieEntity> findMovieByActor(@Param("movieIds") Long[] movieIds);

    @Query("SELECT a.actorName FROM ActorEntity a WHERE :movieId IN (a.movieIds)")
    List<String> findActorsForMovie(@Param("movieId") Long movieId);    

    // @Query("SELECT a FROM ActorEntity a " +
    //         "WHERE a.id NOT IN :actorIds " +
    //         "AND EXISTS (SELECT m FROM MovieEntity m WHERE m.id != :movieId AND a MEMBER OF m.actors)")
    // List<ActorEntity> findActorsInOtherMovies(@Param("actorIds") List<Long> actorIds, @Param("movieId") Long movieId);

    // @Query("SELECT m FROM MovieEntity m JOIN m.actors a WHERE a.id = :actorId AND m.id != :movieId")
    // List<MovieEntity> findMoviesForActorInOtherMovies(@Param("actorId") Long actorId, @Param("movieId") Long movieId);
}

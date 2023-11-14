package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.MovieEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Long> {

        List<MovieEntity> findByIdIn(@Param("filmIds") List<Long> likedFilmIds);

        List<MovieEntity> findByTitreContainingIgnoreCase(String searchTerm);

        @Query("SELECT DISTINCT f FROM MovieEntity f JOIN NoteEntity n ON f.id = n.movieId WHERE n.rating >= :minNote")
        List<MovieEntity> findFilmsByNoteGreaterThanEqual(@Param("minNote") double minNote);

        // @Query("SELECT DISTINCT m FROM MovieEntity m " +
        //                 "WHERE (:genreId IS NULL OR m.id IN :genreMovieIds) " +
        //                 "AND (:actorId IS NULL OR m.id IN :actorMovieIds) " +
        //                 "AND (:realisatorId IS NULL OR m.id IN :realisatorMovieIds) " +
        //                 "AND (:minRating IS NULL OR m.id IN (SELECT n.movieId FROM NoteEntity n WHERE n.rating >= :minRating))")
        // List<MovieEntity> findFilteredMovies(
        //                 @Param("genreId") Long genreId,
        //                 @Param("actorId") Long actorId,
        //                 @Param("realisatorId") Long realisatorId,
        //                 @Param("minRating") Double minRating,
        //                 @Param("genreMovieIds") List<Long> genreMovieIds,
        //                 @Param("actorMovieIds") List<Long> actorMovieIds,
        //                 @Param("realisatorMovieIds") List<Long> realisatorMovieIds);

}

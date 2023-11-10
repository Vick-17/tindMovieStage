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

}

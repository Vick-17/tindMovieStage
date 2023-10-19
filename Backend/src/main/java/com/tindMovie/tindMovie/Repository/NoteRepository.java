package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.NoteEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<NoteEntity, Long> {
    NoteEntity findByUserIdAndMovieId(Long userId, Long movieId);

    List<NoteEntity> findByMovieId(Long movieId);

    List<NoteEntity> findByUserId(Long userId);
}

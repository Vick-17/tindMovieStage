package com.tindMovie.tindMovie.Repository;

import com.tindMovie.tindMovie.Model.CommentaireEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends CrudRepository<CommentaireEntity, Long> {
    CommentaireEntity findByUsersIdAndFilmId(Long usersId, Long filmId);

    List<CommentaireEntity> findByFilmId(Long filmId);
}

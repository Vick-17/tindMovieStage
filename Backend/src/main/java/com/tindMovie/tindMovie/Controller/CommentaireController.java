package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.CommentaireEntity;
import com.tindMovie.tindMovie.Repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentaireController {
    @Autowired
    private CommentaireRepository commentRepo;

    @CrossOrigin
    @PostMapping("/addComment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentaireEntity addComment(@RequestBody CommentaireEntity commentaire) {
        // Vérifie si un commentaire de l'utilisateur existe deja pour se film
        CommentaireEntity existingComment = commentRepo.findByUsersIdAndFilmId(commentaire.getUsersId(), commentaire.getFilmId());

        if (existingComment != null) {
            // un commentaire existe, renvoie une erreur
            throw new RuntimeException("L'utilisateur a déjà commenté ce film.");
        } else {
            // pas de comentaire alors l'envoie a la bdd
            return commentRepo.save(commentaire);
        }
    }

    @CrossOrigin
    @GetMapping(value = "getComment/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentaireEntity> getCommentByMovie(@PathVariable Long movieId) {
        if (movieId == null) {
            throw new RuntimeException("Film absent");
        }
        return commentRepo.findByFilmId(movieId);
    }


}

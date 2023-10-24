package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.NoteEntity;
import com.tindMovie.tindMovie.Repository.CommentaireRepository;
import com.tindMovie.tindMovie.Repository.MovieRepository;
import com.tindMovie.tindMovie.Repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;



    @CrossOrigin
    @PostMapping(value = "/addNote")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteEntity addNote(@RequestBody NoteEntity note) {
        // Vérifier si une note existante correspondant à l'utilisateur et au film
        NoteEntity existingNote = noteRepository.findByUserIdAndMovieId(note.getUserId(), note.getMovieId());

        if (existingNote != null) {
            // Une note existe déjà pour cet utilisateur et ce film, renvoyer une erreur
            throw new RuntimeException("L'utilisateur a déjà noté ce film.");
        } else {
            // Aucune note existante n'a été trouvée, ajouter la nouvelle note
            return noteRepository.save(note);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/getNote/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public double getMoyenne(@PathVariable Long movieId) {
        if (movieId == null) {
            throw new RuntimeException("Film absent");
        }

        List<NoteEntity> ratingMovie = noteRepository.findByMovieId(movieId);

        if (ratingMovie.isEmpty()) {
            // Aucune note n'a été trouvée pour ce film, vous pouvez choisir de renvoyer une valeur par défaut.
            return 0.0;
        }

        // Calcule la somme des notes
        double sum = 0;
        for (NoteEntity note : ratingMovie) {
            sum += note.getRating();
        }

        // Calcule la moyenne
        return sum / ratingMovie.size();
    }
}

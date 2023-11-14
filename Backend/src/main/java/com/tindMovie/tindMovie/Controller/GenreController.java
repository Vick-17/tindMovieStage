package com.tindMovie.tindMovie.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tindMovie.tindMovie.Model.GenreEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Repository.GenreRepository;

@RequestMapping("/genre")
@RestController
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;


    @GetMapping("/allGenre")
    public Iterable<GenreEntity> getAllGenre() {
        return genreRepository.findAll();
    }

    @GetMapping("/moviesByGenre")
    public List<MovieEntity> getMoviesForGenres(@RequestParam Long genreId) {
        // recheche du genre par l'id
        GenreEntity genre = genreRepository.findById(genreId).orElse(null);

        // Vérification si le genre existe et s'il a des film associés
        if (genre != null && genre.getMovieIds() != null && genre.getMovieIds().length > 0) {
            // Convertir les IDs de films une liste 
            Long[] movieIds = genre.getMovieIds();

            // Rechecher les films correspondants dans genre
            return genreRepository.findMovieByGenre(movieIds);
        } else {
            // Si l'acteur n'existe pas ou n'a pas de films, retourner une liste vide
            return Collections.emptyList();
        }
    }
}
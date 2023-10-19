package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.GenreEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.NoteEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import com.tindMovie.tindMovie.Model.SwipeEntity;
import com.tindMovie.tindMovie.Repository.ActorRepository;
import com.tindMovie.tindMovie.Repository.GenreRepository;
import com.tindMovie.tindMovie.Repository.MovieRepository;
import com.tindMovie.tindMovie.Repository.NoteRepository;
import com.tindMovie.tindMovie.Repository.RealisatorRepository;
import com.tindMovie.tindMovie.Repository.SwipeRepository;
import com.tindMovie.tindMovie.Service.ActorService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Le contrôleur pour gérer les requêtes liées aux films.
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SwipeRepository swipeRepository;

    @Autowired
    private ActorService actorService;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private RealisatorRepository realisatorRepository;

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping(value = "/allMovies")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<MovieEntity> getAllMovie() {
        return movieRepository.findAll();
    }

    @GetMapping("/{movieId}/actors")
    @ResponseStatus(HttpStatus.OK)
    public List<ActorEntity> getActorsForMovie(@PathVariable Long movieId) {
        return actorService.getActorsForMovie(movieId);
    }

    @GetMapping("/getMovieById/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<MovieEntity> getMovieById(@PathVariable Long movieId) {
        return movieRepository.findById(movieId);
    }

    @GetMapping(value = "/allMovieByUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieEntity> getMovieByUser(@PathVariable Long userId) {
        // Récupérer la liste de tous les films
        Iterable<MovieEntity> allIterableMovies = movieRepository.findAll();

        // Convertir l'itérable en une liste
        List<MovieEntity> allMovies = StreamSupport
                .stream(allIterableMovies.spliterator(), false)
                .toList();

        // Récupérer la liste des films déjà swipés par l'utilisateur
        List<SwipeEntity> swipesByUser = swipeRepository.findByUserId(userId);

        // Extraire les IDs des films swipés par l'utilisateur
        List<Long> movieSwipedIds = swipesByUser.stream()
                .map(SwipeEntity::getFilmId)
                .toList();

        // Filtrer les films à afficher en excluant ceux déjà swipés
        return allMovies.stream()
                .filter(movie -> !movieSwipedIds.contains(movie.getId()))
                .collect(Collectors.toList());
    }

    @GetMapping("/recommendation/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getRecommendationForUser(@PathVariable Long userId) {
        List<ActorEntity> matchingActors = new ArrayList<>();
        List<RealisatorEntity> matchingRealisator = new ArrayList<>();
        List<GenreEntity> matchinGenre = new ArrayList<>();
        
        // Récupère tous les films et note de l'utilisateur
        List<NoteEntity> userNote = noteRepository.findByUserId(userId);

        // Extraire les Ids des films notée par l'utilisateur et filtrer les note supérieur ou egal a 3
        List<Long> hightRatingMoviesIds = userNote.stream()
            .filter(s -> s.getRating() >= 3)
            .map(s -> s.getMovieId())
            .toList();

        for (Long movieId : hightRatingMoviesIds) {
            // Recherche d'acteurs corespondant
            List<ActorEntity> actors = actorRepository.findByMovieIdsContains(movieId);
            matchingActors.addAll(actors);

            // Recherche de real correspondant
            List<RealisatorEntity> realisators = realisatorRepository.findByMovieIdsContains(movieId);
            matchingRealisator.addAll(realisators);

            // Recherche de genre corespondant
            List<GenreEntity> genres = genreRepository.findByMovieIdsContains(movieId);
            matchinGenre.addAll(genres);
        }

        List<List<?>> result = new ArrayList<>();
        result.add(matchingActors);
        result.add(matchingRealisator);
        result.add(matchinGenre);

        return result;
    }
}

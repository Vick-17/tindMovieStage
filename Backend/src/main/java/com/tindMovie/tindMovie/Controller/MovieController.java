package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.NoteEntity;
import com.tindMovie.tindMovie.Model.SwipeEntity;
import com.tindMovie.tindMovie.Repository.MovieRepository;
import com.tindMovie.tindMovie.Repository.NoteRepository;
import com.tindMovie.tindMovie.Repository.SwipeRepository;
import com.tindMovie.tindMovie.Service.ActorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
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
    private NoteRepository noteRepository;

    @CrossOrigin
    @GetMapping(value = "/allMovies")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<MovieEntity> getAllMovie() {
        return movieRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{movieId}/actors")
    @ResponseStatus(HttpStatus.OK)
    public List<ActorEntity> getActorsForMovie(@PathVariable Long movieId) {
        return actorService.getActorsForMovie(movieId);
    }

    @CrossOrigin
    @GetMapping("/getMovieById/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<MovieEntity> getMovieById(@PathVariable Long movieId) {
        return movieRepository.findById(movieId);
    }

    @CrossOrigin
    @GetMapping(value = "/allMovieByUser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieEntity> getMovieByUser(@PathVariable Long userId) {
        Iterable<MovieEntity> allIterableMovies = movieRepository.findAll();

        List<MovieEntity> allMovies = StreamSupport
                .stream(allIterableMovies.spliterator(), false)
                .toList();

        List<SwipeEntity> swipesByUser = swipeRepository.findByUserId(userId);

        List<Long> movieSwipedIds = swipesByUser.stream()
                .map(SwipeEntity::getFilmId)
                .toList();

        return allMovies.stream()
                .filter(movie -> !movieSwipedIds.contains(movie.getId()))
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping("/recommendation/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieEntity> getRecommendationForUser(@PathVariable Long userId) {

        List<NoteEntity> userNote = noteRepository.findByUserId(userId);

        List<Long> hightRatingMoviesIds = userNote.stream()
                .filter(s -> s.getRating() >= 3)
                .map(s -> s.getMovieId())
                .toList();

        List<Long> likedActors = new ArrayList<>();

        for (Long movieId : hightRatingMoviesIds) {
            List<ActorEntity> actorsForMovie = actorService.getActorsForMovie(movieId);
            for (ActorEntity actor : actorsForMovie) {
                likedActors.add(actor.getId());
            }
        }

        // Filtre les acteurs qui apparaissent au moins deux fois
        Map<Long, Long> actorCounts = likedActors.stream()
                .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()));

        List<Long> commonActors = actorCounts.entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Obtenir des films recommandés basés sur les acteurs communs
        List<MovieEntity> recommendedMovies = new ArrayList<>();

        for (Long actorId : commonActors) {
            List<MovieEntity> moviesByActor = actorService.getMoviesByActorId(actorId);

            // Ajoute les films non notés par l'utilisateur
            recommendedMovies.addAll(moviesByActor.stream()
                    .filter(movie -> !hightRatingMoviesIds.contains(movie.getId()))
                    .collect(Collectors.toList()));
        }

        return recommendedMovies;
    }
}

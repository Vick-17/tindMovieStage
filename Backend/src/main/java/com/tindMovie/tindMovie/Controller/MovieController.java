package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.NoteEntity;
import com.tindMovie.tindMovie.Model.SwipeEntity;
import com.tindMovie.tindMovie.Repository.MovieRepository;
import com.tindMovie.tindMovie.Repository.NoteRepository;
import com.tindMovie.tindMovie.Repository.SwipeRepository;
import com.tindMovie.tindMovie.Service.ActorService;
import com.tindMovie.tindMovie.Service.AlgoService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
  private AlgoService algoService;

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

    List<Long> movieSwipedIds = swipesByUser
      .stream()
      .map(SwipeEntity::getFilmId)
      .toList();

    return allMovies
      .stream()
      .filter(movie -> !movieSwipedIds.contains(movie.getId()))
      .collect(Collectors.toList());
  }

  /**
   * Obtenir des recommandations de films pour un utilisateur en fonction de ses notes et des acteurs qu'il a appréciés.
   * @param userId L'identifiant de l'utilisateur pour lequel les recommandations sont demandées.
   * @return Une liste de films recommandés.
   */
  @CrossOrigin
  @GetMapping("/recommendation/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public List<MovieEntity> getRecommendationForUser(@PathVariable Long userId) {
    // Récupère les notes de l'utilisateur
    List<NoteEntity> userNotes = noteRepository.findByUserId(userId);

    // Extraire les identifiants de films avec des notes élevées (>=3)
    List<Long> hightRatingMovieIds = algoService.getHighRatingMovieIds(
      userNotes
    );

    // Extraire les acteurs et réalisateur appréciés par l'utilisateur
    List<Long> likedActors = algoService.getLikedActors(hightRatingMovieIds);
    List<Long> likedRealisators = algoService.getLikedRealisators(hightRatingMovieIds);
    List<Long> likedGenre = algoService.getLikedGenres(hightRatingMovieIds);

    // Identifier les acteurs et realisateurs appréciés fréquement
    List<Long> commonActors = algoService.getCommonActors(likedActors);
    List<Long> commonRealisators = algoService.getCommonRealisators(likedRealisators);
    List<Long> commonGenre = algoService.getCommonGenres(likedGenre);

    // Obtenir kes films recommandés en fonction des acteurs et réal
    List<MovieEntity> recommendedMovies = algoService.getRecommendedMovies(
      commonActors,
      commonRealisators,
      commonGenre,
      hightRatingMovieIds
    );

    // Retourner la liste de films recommandés
    return recommendedMovies;
  }
}

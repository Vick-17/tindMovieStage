package com.tindMovie.tindMovie.Service;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.GenreEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.NoteEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlgoService {

  private final ActorService actorService;

  private final RealisatorService realisatorService;

  private final GenreService genreService;

  @Autowired
  public AlgoService(
    ActorService actorService,
    RealisatorService realisatorService,
    GenreService genreService
  ) {
    this.actorService = actorService;
    this.realisatorService = realisatorService;
    this.genreService = genreService;
  }

  // Récupère les films avec des notes >= 3
  public List<Long> getHighRatingMovieIds(List<NoteEntity> userNotes) {
    return userNotes
      .stream()
      .filter(s -> s.getRating() >= 3)
      .map(s -> s.getMovieId())
      .toList();
  }

  // Obtient la liste des acteurs appréciés par l'utilisateur à partir des films notés
  public List<Long> getLikedActors(List<Long> highRatingMovieIds) {
    List<Long> likedActors = new ArrayList<>();

    for (Long movieId : highRatingMovieIds) {
      List<ActorEntity> actorsForMovie = actorService.getActorsForMovie(
        movieId
      );
      likedActors.addAll(
        actorsForMovie.stream().map(ActorEntity::getId).toList()
      );
    }

    return likedActors;
  }

  // Obtient la liste des réalisateurs appréciés par l'utilisateur à partir des films notés
  public List<Long> getLikedRealisators(List<Long> highRatingMovieIds) {
    List<Long> likedRealisators = new ArrayList<>();

    for (Long movieId : highRatingMovieIds) {
      List<RealisatorEntity> realisatorsForMovie = realisatorService.getRealForMovie(
        movieId
      );
      likedRealisators.addAll(
        realisatorsForMovie.stream().map(RealisatorEntity::getId).toList()
      );
    }

    return likedRealisators;
  }

  public List<Long> getLikedGenres(List<Long> highRatingMovieIds) {
    List<Long> likedGenres = new ArrayList<>();

    for (Long movieId : highRatingMovieIds) {
      List<GenreEntity> genreForMovie = genreService.getGenreForMovie(movieId);
      likedGenres.addAll(
        genreForMovie.stream().map(GenreEntity::getId).toList()
      );
    }
    return likedGenres;
  }

  // Obtient les acteurs appréciés fréquemment (au moins 2 fois)
  public List<Long> getCommonActors(List<Long> likedActors) {
    Map<Long, Long> actorCounts = likedActors
      .stream()
      .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()));

    return actorCounts
      .entrySet()
      .stream()
      .filter(entry -> entry.getValue() >= 2)
      .map(Map.Entry::getKey)
      .toList();
  }

  public List<Long> getCommonGenres(List<Long> likedGenre) {
    Map<Long, Long> genreCounts = likedGenre
      .stream()
      .collect(Collectors.groupingBy(actor -> actor, Collectors.counting()));

    return genreCounts
      .entrySet()
      .stream()
      .filter(entry -> entry.getValue() >= 2)
      .map(Map.Entry::getKey)
      .toList();
  }

  // Obtient les réalisateurs appréciés fréquemment (au moins 2 fois)
  public List<Long> getCommonRealisators(List<Long> likedRealisators) {
    Map<Long, Long> realisatorCounts = likedRealisators
      .stream()
      .collect(
        Collectors.groupingBy(realisator -> realisator, Collectors.counting())
      );

    return realisatorCounts
      .entrySet()
      .stream()
      .filter(entry -> entry.getValue() >= 2)
      .map(Map.Entry::getKey)
      .toList();
  }

  // Obtient les films recommandés en fonction des acteurs et réalisateurs appréciés
  public List<MovieEntity> getRecommendedMovies(
    List<Long> commonActors,
    List<Long> commonRealisators,
    List<Long> commonGenres,
    List<Long> highRatingMovieIds
  ) {
    Set<MovieEntity> recommendedMovies = new HashSet<>();

    for (Long actorId : commonActors) {
      List<MovieEntity> moviesByActor = actorService.getMoviesByActorId(
        actorId
      );
      moviesByActor
        .stream()
        .filter(movie -> !highRatingMovieIds.contains(movie.getId()))
        .forEach(recommendedMovie -> recommendedMovies.add(recommendedMovie));
    }

    for (Long realisatorId : commonRealisators) {
      List<MovieEntity> moviesByRealisator = realisatorService.getMoviesByRealId(
        realisatorId
      );
      moviesByRealisator
        .stream()
        .filter(movie -> !highRatingMovieIds.contains(movie.getId()))
        .forEach(recommendedMovie -> recommendedMovies.add(recommendedMovie));
    }

    for (Long genreId : commonGenres) {
      List<MovieEntity> moviesByGenre = genreService.getMoviesByGenreId(
        genreId
      );
      moviesByGenre
        .stream()
        .filter(movie -> !highRatingMovieIds.contains(movie.getId()))
        .forEach(recommendedMovie -> recommendedMovies.add(recommendedMovie));
    }

    return new ArrayList<>(recommendedMovies);
  }
}

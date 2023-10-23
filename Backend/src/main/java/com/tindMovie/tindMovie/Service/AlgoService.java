package com.tindMovie.tindMovie.Service;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.NoteEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlgoService {

  private final ActorService actorService;

  private final RealisatorService realisatorService;

  @Autowired
  public AlgoService(
    ActorService actorService,
    RealisatorService realisatorService
  ) {
    this.actorService = actorService;
    this.realisatorService = realisatorService;
  }

  // Récupère les films avec des notes élevées (>= 3)
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
    List<Long> highRatingMovieIds
  ) {
    List<MovieEntity> recommendedMovies = new ArrayList<>();

    for (Long actorId : commonActors) {
      List<MovieEntity> moviesByActor = actorService.getMoviesByActorId(
        actorId
      );
      recommendedMovies.addAll(
        moviesByActor
          .stream()
          .filter(movie -> !highRatingMovieIds.contains(movie.getId()))
          .collect(Collectors.toList())
      );
    }

    for (Long realisatorId : commonRealisators) {
      List<MovieEntity> moviesByRealisator = realisatorService.getMoviesByRealId(
        realisatorId
      );
      recommendedMovies.addAll(
        moviesByRealisator
          .stream()
          .filter(movie -> !highRatingMovieIds.contains(movie.getId()))
          .collect(Collectors.toList())
      );
    }

    return recommendedMovies;
  }
}

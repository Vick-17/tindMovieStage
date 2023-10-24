package com.tindMovie.tindMovie.Service;

import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import com.tindMovie.tindMovie.Repository.MovieRepository;
import com.tindMovie.tindMovie.Repository.RealisatorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class RealisatorService {

  private final RealisatorRepository realisatorRepository;

  private final MovieRepository movieRepository;

  public RealisatorService(
    RealisatorRepository realisatorRepository,
    MovieRepository movieRepository
  ) {
    this.realisatorRepository = realisatorRepository;
    this.movieRepository = movieRepository;
  }

  public List<RealisatorEntity> getRealForMovie(Long movieId) {
    // Rècupère tous le real de la bdd
    List<RealisatorEntity> allReal = (List<RealisatorEntity>) realisatorRepository.findAll();

    // Créer une liste pour stocker les real associer au film
    List<RealisatorEntity> realForMovie = new ArrayList<>();

    // Parcour tous les real
    for (RealisatorEntity realisator : allReal) {
      Long[] movieIds = realisator.getMovieIds();
      if (movieIds != null) {
        // Si le tableau de movieIds contient l'ID du film recherché, ajouter a la liste
        for (Long id : movieIds) {
          if (id.equals(movieId)) {
            realForMovie.add(realisator);
            break;
          }
        }
      }
    }
    return realForMovie;
  }

  public List<MovieEntity> getMoviesByRealId(Long realId) {
    List<MovieEntity> moviesByReal = new ArrayList<>();
    Iterable<RealisatorEntity> allRealItterable = realisatorRepository.findAll();
    List<RealisatorEntity> allReal = StreamSupport
      .stream(allRealItterable.spliterator(), false)
      .collect(Collectors.toList());

    for (RealisatorEntity real : allReal) {
      if (real.getId().equals(realId)) {
        Long[] movieIds = real.getMovieIds();
        if (movieIds != null) {
          for (Long movieId : movieIds) {
            Optional<MovieEntity> movieOptional = movieRepository.findById(
              movieId
            );
            if (movieOptional.isPresent()) {
              moviesByReal.add(movieOptional.get());
            }
          }
        }
      }
    }
    return moviesByReal;
  }
  // public List<MovieEntity> getFilmographyForRealisator(Long realisatorId) {
  //     RealisatorEntity realisator = realisatorRepository.findById(realisatorId).orElse(null);
  //     if (realisator != null && realisator.getMovieIds() != null && realisator.getMovieIds().length > 0 ) {
  //         List<Long> movieIds = Arrays.asList(realisator.getMovieIds());
  //         return realisatorRepository.findMovieByRealisator(movieIds);
  //     } else {
  //         return Collections.emptyList();
  //     }
  // }
}

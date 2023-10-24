package com.tindMovie.tindMovie.Service;

import com.tindMovie.tindMovie.Model.GenreEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Repository.GenreRepository;
import com.tindMovie.tindMovie.Repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

@Service
public class GenreService {

  private final GenreRepository genreRepository;

  private final MovieRepository movieRepository;

  private GenreService(GenreRepository genreRepository, MovieRepository movieRepository) {
    this.genreRepository = genreRepository;
    this.movieRepository = movieRepository;
  }

  public List<GenreEntity> getGenreForMovie(Long movieId) {
    // Rècupère tous le real de la bdds
    List<GenreEntity> allGenre = (List<GenreEntity>) genreRepository.findAll();

    // Créer une liste pour stocker les real associer au film
    List<GenreEntity> realForMovie = new ArrayList<>();

    // Parcour tous les real
    for (GenreEntity genre : allGenre) {
      Long[] movieIds = genre.getMovieIds();
      if (movieIds != null) {
        // Si le tableau de movieIds contient l'ID du film recherché, ajouter a la liste
        for (Long id : movieIds) {
          if (id.equals(movieId)) {
            realForMovie.add(genre);
            break;
          }
        }
      }
    }
    return realForMovie;
  }

  public List<MovieEntity> getMoviesByGenreId(long genreId) {
        List<MovieEntity> moviesByReal = new ArrayList<>();
    Iterable<GenreEntity> allRealItterable = genreRepository.findAll();
    List<GenreEntity> allReal = StreamSupport
      .stream(allRealItterable.spliterator(), false)
      .collect(Collectors.toList());

    for (GenreEntity real : allReal) {
      if (real.getId().equals(genreId)) {
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
}

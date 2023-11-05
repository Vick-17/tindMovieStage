package com.tindMovie.tindMovie.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Repository.MovieRepository;

@Service
public class MovieSearchService {
    @Autowired
    private MovieRepository movieRepository;

    public List<MovieEntity> searchMovies(String searchTerm) {
        return movieRepository.findByTitreContainingIgnoreCase(searchTerm);
    }
}

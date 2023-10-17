package com.tindMovie.tindMovie.Service;

import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import com.tindMovie.tindMovie.Repository.RealisatorRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RealisatorService {

    private final RealisatorRepository realisatorRepository;


    public RealisatorService(RealisatorRepository realisatorRepository) {
        this.realisatorRepository = realisatorRepository;
    }

    public List<MovieEntity> getFilmographyForRealisator(Long realisatorId) {
        RealisatorEntity realisator = realisatorRepository.findById(realisatorId).orElse(null);
        if (realisator != null && realisator.getMovieIds() != null && realisator.getMovieIds().length > 0 ) {
            List<Long> movieIds = Arrays.asList(realisator.getMovieIds());
            return realisatorRepository.findMovieByRealisator(movieIds);
        } else {
            return Collections.emptyList();
        }
    }
}

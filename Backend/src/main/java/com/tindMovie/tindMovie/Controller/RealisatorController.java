package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import com.tindMovie.tindMovie.Repository.RealisatorRepository;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/realisator")
public class RealisatorController {


    private RealisatorRepository realisatorRepository;

    @Autowired
    public RealisatorController(RealisatorRepository realisatorRepository) {
        this.realisatorRepository = realisatorRepository;
    }
    
    // @CrossOrigin
    // @GetMapping("/{realisatorId}/realisator-filmography")
    // public List<MovieEntity> getRealFilmography(@PathVariable Long realisatorId) {
    //     return realisatorService.getFilmographyForRealisator(realisatorId);
    // }

    @CrossOrigin
    @GetMapping("/allRealisator")
    public Iterable<RealisatorEntity> getAllRealisator() {
        return realisatorRepository.findAll();
    }

        @GetMapping("/moviesByRealisator")
    public List<MovieEntity> getMoviesForReal(@RequestParam Long realisatorId) {
        // recheche du genre par l'id
        RealisatorEntity real = realisatorRepository.findById(realisatorId).orElse(null);

        // Vérification si le genre existe et s'il a des film associés
        if (real != null && real.getMovieIds() != null && real.getMovieIds().length > 0) {
            // Convertir les IDs de films une liste 
            Long[] movieIds = real.getMovieIds();

            // Rechecher les films correspondants dans real
            return realisatorRepository.findMovieByRealisator(movieIds);
        } else {
            // Si le real n'existe pas ou n'a pas de films, retourner une liste vide
            return Collections.emptyList();
        }
    }
}

package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.RealisatorEntity;
import com.tindMovie.tindMovie.Service.RealisatorService;
import com.tindMovie.tindMovie.Repository.RealisatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realisator")
public class RealisatorController {

    private final RealisatorService realisatorService;

    private RealisatorRepository realisatorRepository;

    @Autowired
    public RealisatorController(RealisatorService realisatorService, RealisatorRepository realisatorRepository) {
        this.realisatorService = realisatorService;
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
}

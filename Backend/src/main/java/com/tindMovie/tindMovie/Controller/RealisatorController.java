package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Service.RealisatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realisator")
public class RealisatorController {
    private final RealisatorService realisatorService;

    @Autowired
    public RealisatorController(RealisatorService realisatorService) {
        this.realisatorService = realisatorService;
    }

    // @CrossOrigin
    // @GetMapping("/{realisatorId}/realisator-filmography")
    // public List<MovieEntity> getRealFilmography(@PathVariable Long realisatorId) {
    //     return realisatorService.getFilmographyForRealisator(realisatorId);
    // }
}

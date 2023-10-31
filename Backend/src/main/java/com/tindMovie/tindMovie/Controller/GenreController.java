package com.tindMovie.tindMovie.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tindMovie.tindMovie.Model.GenreEntity;
import com.tindMovie.tindMovie.Repository.GenreRepository;

@RequestMapping("/genre")
@RestController
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/allGenre")
    public Iterable<GenreEntity> getAllGenre() {
        return genreRepository.findAll();
    }
}
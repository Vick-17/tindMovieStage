package com.tindMovie.tindMovie.Service;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Repository.ActorRepository;
import com.tindMovie.tindMovie.Repository.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Un service pour gérer les opérations liées aux acteurs et à leur
 * filmographie.
 */
@Service
public class ActorService {

    private final ActorRepository actorRepository;

    private final MovieRepository movieRepository;

    /**
     * Constructeur du service ActorService.
     *
     * @param actorRepository Le référentiel (repository) des acteurs qui sera
     *                        injecté automatiquement par Spring.
     */
    @Autowired
    public ActorService(ActorRepository actorRepository, MovieRepository movieRepository) {
        this.actorRepository = actorRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * Récupère la filmographie d'un acteur spécifique en fonction de son ID.
     *
     * @param actorId L'identifiant de l'acteur dont la filmographie est demandée.
     * @return Une liste des entités de films représentant la filmographie de
     *         l'acteur.
     */
    public List<MovieEntity> getFilmographyForActor(Long actorId) {
        // Recherche de l'acteur par ID
        ActorEntity actor = actorRepository.findById(actorId).orElse(null);

        // Vérification si l'acteur existe et s'il a des films associés
        if (actor != null && actor.getMovieIds() != null && actor.getMovieIds().length > 0) {
            // Convertir les IDs de films en une liste
            Long[] movieIds = actor.getMovieIds();

            // Rechercher les films correspondants dans le référentiel des acteurs
            return actorRepository.findMovieByActor(movieIds);
        } else {
            // Si l'acteur n'existe pas ou n'a pas de films, retourner une liste vide
            return Collections.emptyList();
        }
    }

    public List<ActorEntity> getActorsForMovie(Long movieId) {
        // Récupère tous les acteurs de la bdd
        List<ActorEntity> allActors = (List<ActorEntity>) actorRepository.findAll();

        // créer une liste pour stocker les acteurs associés au film
        List<ActorEntity> actorForMovie = new ArrayList<>();

        // Parcour tous les acteurs
        for (ActorEntity actor : allActors) {
            Long[] movieIds = actor.getMovieIds();
            if (movieIds != null) {
                // Si le tableau de movieIds contient l'ID du film recherché, ajouter a la liste
                for (Long id : movieIds) {
                    if (id.equals(movieId)) {
                        actorForMovie.add(actor);
                        break;
                    }
                }
            }
        }
        return actorForMovie;
    }

    public List<MovieEntity> getMoviesByActorId(Long actorId) {
        List<MovieEntity> moviesByActor = new ArrayList<>();
        Iterable<ActorEntity> allActorsIterable = actorRepository.findAll();
        List<ActorEntity> allActors = StreamSupport.stream(allActorsIterable.spliterator(), false)
                .collect(Collectors.toList());
    
        for (ActorEntity actor : allActors) {
            if (actor.getId().equals(actorId)) {
                Long[] movieIds = actor.getMovieIds();
                if (movieIds != null) {
                    for (Long movieId : movieIds) {
                        Optional<MovieEntity> movieOptional = movieRepository.findById(movieId);
                        if (movieOptional.isPresent()) {
                            moviesByActor.add(movieOptional.get());
                        }
                    }
                }
            }
        }
        return moviesByActor;
    }
    
}

package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.ActorEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Repository.ActorRepository;
import com.tindMovie.tindMovie.Service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Le contrôleur pour gérer les requêtes liées aux acteurs et à leur filmographie.
 */
@RestController
@RequestMapping("/actors")
public class ActorController {

    private final ActorService actorService;
    private final ActorRepository actorRepository;

    /**
     * Constructeur du contrôleur ActorController.
     *
     * @param actorService Le service des acteurs qui sera injecté automatiquement par Spring.
     */
    @Autowired
    public ActorController(ActorService actorService, ActorRepository actorRepository) {
        this.actorService = actorService;
        this.actorRepository = actorRepository;
    }

    /**
     * Récupère la filmographie d'un acteur spécifique en fonction de son ID.
     *
     * @param actorId L'identifiant de l'acteur dont la filmographie est demandée.
     * @return Une liste des entités de films représentant la filmographie de l'acteur.
     */
    @GetMapping("/{actorId}/actor-filmography")
    public List<MovieEntity> getActorFilmography(@PathVariable Long actorId) {
        return actorService.getFilmographyForActor(actorId);
    }
    @GetMapping("/allActor")
    public Iterable<ActorEntity> getAllActor() {
        return actorRepository.findAll();
    }
}


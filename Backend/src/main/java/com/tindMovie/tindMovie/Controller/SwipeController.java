package com.tindMovie.tindMovie.Controller;

import com.tindMovie.tindMovie.Model.MatchEntity;
import com.tindMovie.tindMovie.Model.MovieEntity;
import com.tindMovie.tindMovie.Model.SwipeEntity;
import com.tindMovie.tindMovie.Model.UsersEntity;
import com.tindMovie.tindMovie.Repository.MatchRepository;
import com.tindMovie.tindMovie.Repository.MovieRepository;
import com.tindMovie.tindMovie.Repository.SwipeRepository;
import com.tindMovie.tindMovie.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/swipe")
public class SwipeController {

    @Autowired
    private SwipeRepository swipeRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;

    @CrossOrigin
    @PostMapping(value = "/like")
    @ResponseStatus(HttpStatus.CREATED)
    public SwipeEntity createSwipe(@RequestBody SwipeEntity swipe) {

        if (!swipeRepository.existsByUserIdAndFilmId(swipe.getUserId(), swipe.getFilmId())) {
            if ("left".equals((swipe.getSwipeDirection()))) {
                swipe.setNotLiked(true);
                swipe.setLiked(false);

            } else if ("right".equals(swipe.getSwipeDirection())) {
                swipe.setLiked(true);
                swipe.setNotLiked(false);
            }

            swipeRepository.save(swipe);
            Optional<UsersEntity> userOptional = userRepository.findById(swipe.getUserId());
            if (userOptional.isPresent()) {
                UsersEntity users = userOptional.get();
                if (users.getPartenaire() != null) {
                    Optional<SwipeEntity> userSwipeOptional = swipeRepository.findByUserIdAndFilmId(swipe.getUserId(), swipe.getFilmId());
                    Optional<SwipeEntity> partnerSwipeOptional = swipeRepository.findByUserIdAndFilmId(users.getPartenaire().getId(), swipe.getFilmId());

                    if (userSwipeOptional.isPresent() && partnerSwipeOptional.isPresent() &&
                            userSwipeOptional.get().isLiked() && partnerSwipeOptional.get().isLiked()) {
                        MatchEntity match = new MatchEntity();
                        match.setUserId1(swipe.getUserId());
                        match.setUserId2(users.getPartenaire().getId());
                        match.setFilmId(swipe.getFilmId());
                        matchRepository.save(match);
                    }
                }
            }
        }
        return swipe;
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CrossOrigin
    public ResponseEntity<Void> deleteSwipe(@RequestBody SwipeEntity swipe) {
        // Recherche du swipe à suprimmer
        Optional<SwipeEntity> optionalSwipe = swipeRepository.findByUserIdAndFilmId(swipe.getUserId(), swipe.getFilmId());
        if (optionalSwipe.isPresent()) {
            // Si le swipe existe, alors on le supprime
            swipeRepository.delete(optionalSwipe.get());
            ;
            // Retourne une réponse avec le statut 204 No Content pour indiquer le delete est bon
            return ResponseEntity.noContent().build();
        } else {
            // si le swipe n'est pas trouvé, retourne une réponse 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/allSwipe/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public List<MovieEntity> getAllSwipeByUser(@PathVariable Long userId) {
        if (userId == null) {
            throw new RuntimeException("user_id est absent");
        } else {
            // Récupére le swipe de l'utilisateur
            List<SwipeEntity> userSwipe = swipeRepository.findByUserId(userId);

            List<Long> likedFilmIds = new ArrayList<>();

            // Parcour les swipes pour extraire les ID des films likés
            for (SwipeEntity swipe : userSwipe) {
                if (swipe.isLiked() && !swipe.isWatched()) {
                    likedFilmIds.add(swipe.getFilmId());
                }
            }
            // Récupére les films correspondant aux ID des films aimées
            return movieRepository.findByIdIn(likedFilmIds);
        }
    }

    @GetMapping("/watchedMovie/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public List<MovieEntity> getAllWatchedMovieByUser(@PathVariable Long userId) {
        if (userId == null) {
            throw new RuntimeException("user_id est absent");
        } else {
            // Récupére le swipe de l'utilisateur
            List<SwipeEntity> userSwipe = swipeRepository.findByUserId(userId);

            List<Long> likedFilmIds = new ArrayList<>();

            // Parcour les swipes pour extraire les ID des films likés
            for (SwipeEntity swipe : userSwipe) {
                if (swipe.isLiked() && swipe.isWatched()) {
                    likedFilmIds.add(swipe.getFilmId());
                }
            }
            // Récupére les films correspondant aux ID des films aimées
            return movieRepository.findByIdIn(likedFilmIds);
        }
    }

    @PutMapping("/watched")
    @CrossOrigin
    public ResponseEntity<Void> movieWatch(@RequestBody SwipeEntity swipe) {
        try {
            // Recherche du swipe en fonction de l'ID de l'utilisateur et de l'ID du film
            Optional<SwipeEntity> optionalSwipe = swipeRepository.findByUserIdAndFilmId(swipe.getUserId(), swipe.getFilmId());

            if (optionalSwipe.isPresent()) {
                SwipeEntity existingSwipe = optionalSwipe.get();
                // Mettez à jour la propriété isWatched à true
                existingSwipe.setWatched(true);
                swipeRepository.save(existingSwipe);
                return ResponseEntity.ok().build();
            } else {
                // Si le swipe n'est pas trouvé, retournez une réponse 404 Not Found
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

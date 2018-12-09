package com.podtube.services;

import com.podtube.models.Rating;
import com.podtube.models.Podcast;
import com.podtube.models.User;
import com.podtube.repositories.PodcastRepository;
import com.podtube.repositories.RatingRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    PodcastRepository podcastRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/podcast/{podcastId}/rating")
    ResponseEntity<List<Rating>> findRatingsForPodcast(@PathVariable("podcastId") int podcastId) {

        Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
        if(!podcastOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Podcast podcast = podcastOpt.get();
        return new ResponseEntity<>(ratingRepository.findAllByPodcast_IdOrderByCreatedOnDesc(podcast.getId()),
                HttpStatus.OK);
    }//findRatingsForPodcast..

    @GetMapping("/api/user/{userId}/ratings")
    ResponseEntity<List<Rating>> findRatingsForUser(@PathVariable("userId") int userId) {

        Optional<User> userOpt = userRepository.findById(userId);
        if(!userOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = userOpt.get();
        return new ResponseEntity<>(ratingRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId()), HttpStatus.OK);
    }//findRatingsForUser..

    @PostMapping("/api/podcast/{podcastId}/rating")
    ResponseEntity<Rating> createRating(HttpSession httpSession,
                                          @PathVariable("podcastId") int podcastId,
                                          @RequestBody Rating rating) {

        // check LoggedIn
        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // check if user with id exists
        int id = (int) httpSession.getAttribute("id");

        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent())
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        User user = userOpt.get();

        // check if podcastId exists
        Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
        if (!podcastOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //Create rating
        Podcast podcast = podcastOpt.get();
        rating.setPodcast(podcast);
        rating.setUser(user);
        Rating savedRating = ratingRepository.save(rating);
        return new ResponseEntity<>(savedRating, HttpStatus.OK);
    }//createRating..


    @DeleteMapping("/api/rating/{ratingId}")
    ResponseEntity<List<Rating>> deleteRating(HttpSession httpSession,
                                              @PathVariable("ratingId") int ratingId) {
        // check LoggedIn
        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        // check if user with id exists
        int id = (int) httpSession.getAttribute("id");
        Optional<User> userOpt = userRepository.findById(id);
        if (!userOpt.isPresent())
            new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        User user = userOpt.get();

        // check if commentId exists
        Optional<Rating> ratingOpt = ratingRepository.findById(ratingId);

        if (!ratingOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // check if comment is owned by logged in user
        Rating rating = ratingOpt.get();
        if (rating.getUser().getId() != user.getId())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // delete rating
        Podcast podcast = rating.getPodcast();
        ratingRepository.delete(rating);
        List<Rating> ratings = ratingRepository.findAllByPodcast_IdOrderByCreatedOnDesc(podcast.getId());
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }
}

package com.podtube.services;

import com.podtube.models.Category;
import com.podtube.models.Podcast;
import com.podtube.models.User;
import com.podtube.repositories.PodcastRepository;
import com.podtube.repositories.SubscriptionRepository;
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
public class PodcastService {
	@Autowired
	PodcastRepository podcastRepository;
	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/podcasts")
	public ResponseEntity<List<Podcast>> findAllPodcasts() {

		return new ResponseEntity<>((List<Podcast>) podcastRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/api/categories/{categoryId}/podcasts")
	public ResponseEntity<List<Podcast>> getPodcastsForCategory(HttpSession httpSession,
																 @PathVariable("categoryId") int categoryId) {
		int id = 0;
		if (httpSession.getAttribute("id") != null)
			id = (int) httpSession.getAttribute("id");

		List<Podcast> podcasts = podcastRepository.getPodcastsForCategory(categoryId);
		if (id != 0) {
			Optional<User> userOpt = userRepository.findById(id);
			if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			User user = userOpt.get();
			for (Podcast podcast : podcasts) {
				if (subscriptionRepository.findByUserAndPodcastOrderByCreatedOnDesc(user, podcast) != null)
					podcast.setSubscribed(true);
			}
		}
		return new ResponseEntity<>(podcastRepository.getPodcastsForCategory(categoryId), HttpStatus.OK);
	}

	@GetMapping("/api/podcasts/{podcastId}")
	public ResponseEntity<Podcast> getPodcast(@PathVariable("podcastId") int podcastId) {

		Optional<Podcast> data = podcastRepository.findById(podcastId);
		if(data.isPresent()){
			Podcast podcast = data.get();
			return new ResponseEntity<>(podcast, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}

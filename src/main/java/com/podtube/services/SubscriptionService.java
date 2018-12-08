package com.podtube.services;

import com.podtube.repositories.PodcastRepository;
import com.podtube.repositories.SubscriptionRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.podtube.models.*;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class SubscriptionService {
	@Autowired
	SubscriptionRepository subscriptionRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PodcastRepository podcastRepository;
	
	@GetMapping("/api/subscription")
	public ResponseEntity<List<Subscription>> findAllUserSubscriptions(HttpSession httpSession) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");
		return new ResponseEntity<>(subscriptionRepository.findSubsriptionsByUser_Id(id), HttpStatus.OK);
	}

	@PostMapping("/api/subscription/podcast/{podcastId}")
	public ResponseEntity<Podcast> createSubscription(HttpSession httpSession,
													  @PathVariable("podcastId") int podcastId) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		return userOpt.map(user -> {
			Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
			if(!podcastOpt.isPresent()) return new ResponseEntity<Podcast>(HttpStatus.BAD_REQUEST);
			Podcast podcast = podcastOpt.get();
			Subscription subscription = new Subscription();
			subscription.setPodcast(podcast);
			subscription.setUser(user);
			try {
				Subscription addedSubscription = subscriptionRepository.save(subscription);
				addedSubscription.getPodcast().setSubscribed(true);
				return new ResponseEntity<>(addedSubscription.getPodcast(), HttpStatus.OK);
			} catch (RuntimeException ex) {
				return new ResponseEntity<Podcast>(HttpStatus.BAD_REQUEST);
			}
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/api/subscription/podcast/{podcastId}")
	public ResponseEntity<Podcast> cancelSubscription(HttpSession httpSession,
													  @PathVariable("podcastId") int podcastId) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		return userOpt.map(user -> {
			Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
			if(!podcastOpt.isPresent()) return new ResponseEntity<Podcast>(HttpStatus.BAD_REQUEST);
			Podcast podcast = podcastOpt.get();
			Subscription subscriptionToDelete = subscriptionRepository.findByUserAndPodcast(user, podcast);
			if (subscriptionToDelete == null){
				return new ResponseEntity<Podcast>(HttpStatus.BAD_REQUEST);
			}
			subscriptionRepository.delete(subscriptionToDelete);
			podcast.setSubscribed(false);
			return new ResponseEntity<>(podcast, HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}
}

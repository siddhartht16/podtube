package com.podtube.services;

import com.podtube.datasourceapi.APIUtils;
import com.podtube.gpodderentities.GPodderPodcast;
import com.podtube.repositories.PodcastRepository;
import com.podtube.repositories.SubscriptionRepository;

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

	private APIUtils apiUtils = new APIUtils();

	private Podcast getPodcastFromGpodder(String podcastUrl) {
		GPodderPodcast gPodderPodcast = apiUtils.getPodCastFromGpodder(podcastUrl);
		Podcast podcast = null;
		String url = gPodderPodcast.getUrl();
		if ("".equals(url))
			return podcast;

		podcast = new Podcast();
		podcast.setUrl(gPodderPodcast.getUrl());
		podcast.setTitle(gPodderPodcast.getTitle());
		podcast.setDescription(gPodderPodcast.getDescription());
		podcast.setLogo_url(gPodderPodcast.getLogoUrl());
		podcast.setMygpo_link(gPodderPodcast.getMyGPOLink());
		podcast.setScaled_logo_url(gPodderPodcast.getScaledLogoUrl());
		podcast.setGpodder_subscribers(gPodderPodcast.getSubscribers());
		podcast.setGpodder_subscribers_last_week(gPodderPodcast.getSubscribersLastWeek());
		podcast.setWebsite(gPodderPodcast.getWebsite());

		Podcast createdPodcast = podcastRepository.save(podcast);

		return createdPodcast;
	}

	@GetMapping("/api/subscription")
	public ResponseEntity<List<Subscription>> findAllUserSubscriptions(HttpSession httpSession) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		int id = (int) httpSession.getAttribute("id");
		List<Subscription> userSubscriptions = subscriptionRepository.findAllByUser_IdOrderByCreatedOnDesc(id);
		for (Subscription subscription : userSubscriptions) {
			subscription.getPodcast().setSubscribed(true);
		}
		return new ResponseEntity<>(userSubscriptions, HttpStatus.OK);
	}

	@PostMapping("/api/subscription/podcast/{podcastId}")
	public ResponseEntity<Podcast> createSubscription(HttpSession httpSession,
													  @PathVariable("podcastId") int podcastId) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		return userOpt.map(user -> {
			Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
			if(!podcastOpt.isPresent()) return new ResponseEntity<Podcast>(HttpStatus.BAD_REQUEST);
			Podcast podcast = podcastOpt.get();
			Subscription existingSubscription = subscriptionRepository.findByUserAndPodcast(user, podcast);
			if (existingSubscription != null) return new ResponseEntity<Podcast>(HttpStatus.BAD_REQUEST);
			Subscription subscription = new Subscription();
			subscription.setPodcast(podcast);
			subscription.setUser(user);
            Subscription addedSubscription = subscriptionRepository.save(subscription);
            addedSubscription.getPodcast().setSubscribed(true);
            return new ResponseEntity<>(addedSubscription.getPodcast(), HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
	}

	@PostMapping("/api/subscription/podcast")
	public ResponseEntity<Podcast> createSubscriptionByUrl(HttpSession httpSession,
														   @RequestParam("url") String url) {
		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		User user = userOpt.get();

		if (url == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Podcast podcast = podcastRepository.findByUrlEquals(url);
		if (podcast == null) {
			podcast = getPodcastFromGpodder(url);
			if (podcast == null) {
				// UI sent us a bad podcast url
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		// check if already subscribed. Should not hit this case when request is sent from UI since
		// UI would have fetched subscription list and performed join
		Subscription existingSubscription = subscriptionRepository.findByUserAndPodcast(user, podcast);
		if (existingSubscription != null) return new ResponseEntity<Podcast>(HttpStatus.BAD_REQUEST);

		Subscription subscription = new Subscription();
		subscription.setPodcast(podcast);
		subscription.setUser(user);
		Subscription addedSubscription = subscriptionRepository.save(subscription);
		addedSubscription.getPodcast().setSubscribed(true);
		return new ResponseEntity<>(addedSubscription.getPodcast(), HttpStatus.OK);
	}

	@DeleteMapping("/api/subscription/podcast/{podcastId}")
	public ResponseEntity<Podcast> cancelSubscription(HttpSession httpSession,
													  @PathVariable("podcastId") int podcastId) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
	}
}

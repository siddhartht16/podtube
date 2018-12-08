package com.podtube.services;

import com.podtube.models.Category;
import com.podtube.models.Podcast;
import com.podtube.repositories.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class PodcastService {
	@Autowired
	PodcastRepository podcastRepository;

	@GetMapping("/api/podcasts")
	public List<Podcast> findAllPodcasts() {

		return (List<Podcast>) podcastRepository.findAll();
	}

	@GetMapping("/api/categories/{categoryId}/podcasts")
	public List<Podcast> getPodcastsForCategory(@PathVariable("categoryId") int categoryId) {
		return podcastRepository.getPodcastsForCategory(categoryId);
	}

	@GetMapping("/api/podcasts/{podcastId}")
	public Podcast getPodcast(@PathVariable("podcastId") int podcastId) {

		Optional<Podcast> data = podcastRepository.findById(podcastId);
		if(data.isPresent()){
			Podcast podcast = data.get();
			return podcast;
		}
		return null;
	}


}

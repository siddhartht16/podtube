package com.podtube.services;

import com.podtube.models.Category;
import com.podtube.models.Podcast;
import com.podtube.repositories.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

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

}

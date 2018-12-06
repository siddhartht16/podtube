package com.podtube.services;

import com.podtube.models.Episode;
import com.podtube.models.Podcast;
import com.podtube.repositories.EpisodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class EpisodeService {
	@Autowired
	EpisodeRepository episodeRepository;

	@GetMapping("/api/episodes")
	public List<Episode> findAllEpisodes() {
		return (List<Episode>) episodeRepository.findAll();
	}
}

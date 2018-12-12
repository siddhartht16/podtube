package com.podtube.services;

import com.podtube.models.*;
import com.podtube.repositories.BookmarkRepository;
import com.podtube.repositories.EpisodeRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class EpisodeService {
	@Autowired
	EpisodeRepository episodeRepository;
	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	SyncService syncService;

//	@GetMapping("/api/episodes")
//	public ResponseEntity<List<Episode>> findAllEpisodes() {
//
//		return new ResponseEntity<>((List<Episode>) episodeRepository.findAll(), HttpStatus.OK);
//	}

	@GetMapping("/api/podcasts/{podcastId}/episodes")
	public ResponseEntity<List<Episode>>  getEpisodesForPodcast(HttpSession httpSession,
																@PathVariable("podcastId") int podcastId) {
		int userId = 0;
		if (httpSession.getAttribute("id") != null)
			userId = (int) httpSession.getAttribute("id");
		List<Episode> episodes = episodeRepository.findEpisodesByPodcastId(podcastId);
		if (episodes.size() == 0) {
			//TODO: This will add the querying user as the user in audit fields for created by, modified by
			syncService.syncEpisodesForPodcastFromRSSFeed(userId, podcastId);
			episodes = episodeRepository.findEpisodesByPodcastId(podcastId);
		}
		if (userId != 0){
			// verify if user exists
			Optional<User> userOpt = userRepository.findById(userId);
			if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

			List<Bookmark> userBookmarks = bookmarkRepository.findAllByUser_IdOrderByCreatedOnDesc(userId);
			Set<Episode> bookmarkedEpisodes = new HashSet<>();

			for (Bookmark bookmark : userBookmarks)
				bookmarkedEpisodes.add(bookmark.getEpisode());
			for (Episode episode : episodes) {
				if (bookmarkedEpisodes.contains(episode))
					episode.setBookmarked(true);
			}
		}
		return new ResponseEntity<>(episodes, HttpStatus.OK);
	}

	@GetMapping("/api/episodes/{episodeId}")
	public ResponseEntity<Episode> getEpisode(@PathVariable("episodeId") int episodeId) {

		Optional<Episode> data = episodeRepository.findById(episodeId);
		if (data.isPresent()) {
			Episode episode = data.get();
			return new ResponseEntity<>(episode, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}

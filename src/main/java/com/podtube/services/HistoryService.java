package com.podtube.services;

import com.podtube.customentities.ResponseWrapper;
import com.podtube.models.*;
import com.podtube.repositories.BookmarkRepository;
import com.podtube.repositories.EpisodeRepository;
import com.podtube.repositories.HistoryRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class HistoryService {

	@Autowired
	HistoryRepository historyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	EpisodeRepository episodeRepository;

	@Autowired
	BookmarkRepository bookmarkRepository;

	@GetMapping("/api/history")
	ResponseEntity<List<History>> findHistoryForUser(HttpSession httpSession) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		if(!userOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		List<History> historyList = historyRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId());
		List<Bookmark> bookmarks = bookmarkRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId());
		Set<Episode> bookmarkedEpisodes = new HashSet<>();
		for (Bookmark bookmark : bookmarks) {
			bookmarkedEpisodes.add(bookmark.getEpisode());
		}
		for (History history : historyList) {
			if (bookmarkedEpisodes.contains(history.getEpisode()))
				history.getEpisode().setBookmarked(true);
		}


		return new ResponseEntity<>(historyList, HttpStatus.OK);
	}//findHistorysForUser..


	@PostMapping("/api/history/episode/{episodeId}")
	ResponseEntity<History> createHistoryItem(HttpSession httpSession,
											  @PathVariable("episodeId") int episodeId) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent())
			new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		// check if episode exists
		Optional<Episode> episodeOpt = episodeRepository.findById(episodeId);
		if (!episodeOpt.isPresent()) new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Episode episode = episodeOpt.get();

		History history = historyRepository.findByUserAndEpisode(user, episode);
		// delete if already exists
		if (history != null) historyRepository.delete(history);

		history = new History();
		history.setUser(user);
		history.setEpisode(episode);
		History savedHistory = historyRepository.save(history);
		return new ResponseEntity<>(savedHistory, HttpStatus.OK);
	}//createHistory..



	@DeleteMapping("/api/history/{historyId}")
	ResponseEntity<List<History>> deleteHistory(HttpSession httpSession,
												  @PathVariable("historyId") int historyId) {
		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		// check if history item exists
		Optional<History> historyOpt = historyRepository.findById(historyId);
		if (!historyOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// check if history is owned by logged in user
		History history = historyOpt.get();
		if (history.getUser().getId() != user.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// delete comment
		List<History> historyList = historyRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId());
		List<Bookmark> bookmarks = bookmarkRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId());
		Set<Episode> bookmarkedEpisodes = new HashSet<>();
		for (Bookmark bookmark : bookmarks) {
			bookmarkedEpisodes.add(bookmark.getEpisode());
		}
		for (History h : historyList) {
			if (bookmarkedEpisodes.contains(history.getEpisode()))
				h.getEpisode().setBookmarked(true);
		}

		return new ResponseEntity<>(historyList, HttpStatus.OK);
	}//deleteHistory..

	@DeleteMapping("/api/history/clear")
	ResponseEntity<ResponseWrapper> clearHistory(HttpSession httpSession) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		// delete comment
		List<History> historyList = historyRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId());
		historyRepository.deleteAll(historyList);

		ResponseWrapper responseWrapper = new ResponseWrapper();
		responseWrapper.setSuccess(true);

		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}//deleteHistory..


}//HistoryService..

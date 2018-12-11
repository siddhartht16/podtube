package com.podtube.services;

import com.podtube.models.*;
import com.podtube.repositories.BookmarkRepository;
import com.podtube.repositories.EpisodeRepository;
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
public class BookmarkService {

	@Autowired
	BookmarkRepository bookmarkRepository;

	@Autowired
	EpisodeRepository episodeRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/bookmarks")
	ResponseEntity<List<Bookmark>> findBookmarksForUser(HttpSession httpSession) {
		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		int id = (int) httpSession.getAttribute("id");
		// Does user exist
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		List<Bookmark> userBookmarks = bookmarkRepository.findAllByUser_IdOrderByCreatedOnDesc(id);
		for (Bookmark bookmark : userBookmarks) {
			bookmark.getEpisode().setBookmarked(true);
		}
		return new ResponseEntity<>(userBookmarks, HttpStatus.OK);
	}//findBookmarksForUser..


	@PostMapping("/api/bookmarks/episode/{episodeId}")
	ResponseEntity<Episode> createBookmark(HttpSession httpSession,
											@PathVariable("episodeId") int episodeId) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		int id = (int) httpSession.getAttribute("id");

		// Does user exist
		Optional<User> userOpt = userRepository.findById(id);
		return userOpt.map(user -> {
			Optional<Episode> episodeOpt = episodeRepository.findById(episodeId);
			// Does episode exist
			if(!episodeOpt.isPresent()) return new ResponseEntity<Episode>(HttpStatus.BAD_REQUEST);
			Episode episode = episodeOpt.get();

			// Bookmark should not exist
			Bookmark existingBookmark = bookmarkRepository.findByUserAndEpisode(user, episode);
			if (existingBookmark != null) return new ResponseEntity<Episode>(HttpStatus.BAD_REQUEST);
			Bookmark bookmark = new Bookmark();
			bookmark.setEpisode(episode);
			bookmark.setUser(user);
			Bookmark addedBookmark = bookmarkRepository.save(bookmark);
			addedBookmark.getEpisode().setBookmarked(true);
			return new ResponseEntity<>(addedBookmark.getEpisode(), HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
	}//createBookmark..


	@DeleteMapping("/api/bookmarks/episode/{episodeId}")
	ResponseEntity<Episode> deleteBookmark(HttpSession httpSession,
												@PathVariable("episodeId") int episodeId) {
		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();
		// check if episode exists
		Optional<Episode> episodeOpt = episodeRepository.findById(episodeId);
		if(!episodeOpt.isPresent()) return new ResponseEntity<Episode>(HttpStatus.BAD_REQUEST);
		Episode episode = episodeOpt.get();

		// check if bookmark exists
		Bookmark bookmarkToDelete = bookmarkRepository.findByUserAndEpisode(user, episode);
		if (bookmarkToDelete == null){
			return new ResponseEntity<Episode>(HttpStatus.BAD_REQUEST);
		}
		bookmarkRepository.delete(bookmarkToDelete);
		episode.setBookmarked(false);
		return new ResponseEntity<>(episode, HttpStatus.OK);
	}//deleteBookmark..
}//BookmarkService..

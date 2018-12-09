package com.podtube.services;

import com.podtube.models.*;
import com.podtube.repositories.BookmarkRepository;
import com.podtube.repositories.PodcastRepository;
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
	PodcastRepository podcastRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/bookmarks")
	ResponseEntity<List<Bookmark>> findBookmarksForUser(HttpSession httpSession) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");

		Optional<User> userOpt = userRepository.findById(id);

		if(!userOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		User user = userOpt.get();

		return new ResponseEntity<>(bookmarkRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId()), HttpStatus.OK);
	}//findBookmarksForUser..


	@PostMapping("/api/bookmarks")
	ResponseEntity<Bookmark> createBookmark(HttpSession httpSession,
											@RequestBody Bookmark bookmark) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");

		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent())
			new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		//TODO: Not sure how to check if episode exists which came in json

		//Create bookmark
		bookmark.setUser(user);
		Bookmark savedBookmark = bookmarkRepository.save(bookmark);
		return new ResponseEntity<>(savedBookmark, HttpStatus.OK);
	}//createBookmark..

	@DeleteMapping("/api/bookmarks/{bookmarkId}")
	ResponseEntity<List<Bookmark>> deleteBookmark(HttpSession httpSession,
												@PathVariable("bookmarkId") int bookmarkId) {
		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		// check if commentId exists
		Optional<Bookmark> bookmarkOpt = bookmarkRepository.findById(bookmarkId);
		if (!bookmarkOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// check if comment is owned by logged in user
		Bookmark bookmark = bookmarkOpt.get();
		if (bookmark.getUser().getId() != user.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// delete comment
		bookmarkRepository.delete(bookmark);
		List<Bookmark> bookmarks = bookmarkRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId());
		return new ResponseEntity<>(bookmarks, HttpStatus.OK);
	}//deleteBookmark..
}//BookmarkService..

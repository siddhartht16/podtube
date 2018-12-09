package com.podtube.services;

import com.podtube.models.*;
import com.podtube.repositories.HistoryRepository;
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
public class HistoryService {

	@Autowired
	HistoryRepository historyRepository;

	@Autowired
	PodcastRepository podcastRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/history")
	ResponseEntity<List<History>> findHistoryForUser(HttpSession httpSession) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");

		Optional<User> userOpt = userRepository.findById(id);

		if(!userOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		User user = userOpt.get();

		return new ResponseEntity<>(historyRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId()), HttpStatus.OK);
	}//findHistorysForUser..


	@PostMapping("/api/history")
	ResponseEntity<History> createHistoryItem(HttpSession httpSession,
												@RequestBody History history) {

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

		//Create history
		history.setUser(user);
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

		// check if comment is owned by logged in user
		History history = historyOpt.get();
		if (history.getUser().getId() != user.getId())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		// delete comment
		historyRepository.delete(history);
		List<History> historyList = historyRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId());
		return new ResponseEntity<>(historyList, HttpStatus.OK);
	}//deleteHistory..
}//HistoryService..

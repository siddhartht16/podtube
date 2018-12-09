package com.podtube.services;

import com.podtube.models.Podcast;
import com.podtube.models.Playlist;
import com.podtube.models.User;
import com.podtube.repositories.PlaylistRepository;
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
public class PlaylistService {
	@Autowired
	PlaylistRepository playlistRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/playlists")
	ResponseEntity<List<Playlist>> findPlaylistsForUser(HttpSession httpSession) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");

		Optional<User> userOpt = userRepository.findById(id);

		if (!userOpt.isPresent())
			new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		return new ResponseEntity<>(playlistRepository.findAllByUser_IdOrderByCreatedOnDesc(user.getId()),
				HttpStatus.OK);
	}//findPlaylistsForUser..

	@GetMapping("/api/playlists/{playlistId}")
	ResponseEntity<Playlist> findPlaylist(HttpSession httpSession,
										  @PathVariable("playlistId") int playlistId) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");

		Optional<User> userOpt = userRepository.findById(id);

		if (!userOpt.isPresent())
			new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		Optional<Playlist> playlistOpt = playlistRepository.findById(playlistId);
		if(!playlistOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		Playlist playlist = playlistOpt.get();
		return new ResponseEntity<>(playlistRepository.findByIdEqualsAndUserId(user.getId(), playlist.getId()),
				HttpStatus.OK);
	}//findPlaylist..

}

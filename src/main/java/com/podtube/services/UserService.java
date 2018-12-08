package com.podtube.services;

import com.podtube.models.User;
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
public class UserService {

	@Autowired
	UserRepository userRepository;

	@PostMapping("/api/register")
	public ResponseEntity<User> register(@RequestBody User user, HttpSession httpSession) {
		List<User> users = userRepository.findUserByUsername(user.getUsername());
		if (!users.isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		User userToAdd = new User(user.getUsername(), user.getPassword());
		User newUser = userRepository.save(userToAdd);
		httpSession.setAttribute("id", newUser.getId());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@GetMapping("/api/profile")
	public ResponseEntity<User> profile(HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		return userOpt.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@GetMapping("/api/users")
	public ResponseEntity<List<User>> findAllUsers(HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>((List<User>) userRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/api/login")
	public ResponseEntity<User> login(@RequestBody User credentials, HttpSession httpSession) {
		List<User> users = userRepository.findUserByCredentials(credentials.getUsername(), credentials.getPassword());
		if (!users.isEmpty()){
			User userToReturn = users.get(0);
			httpSession.setAttribute("id", userToReturn.getId());
			return new ResponseEntity<>(userToReturn, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("/api/logout")
	public void logout(HttpSession httpSession) {
		httpSession.invalidate();
	}

	@PutMapping("/api/profile")
	public ResponseEntity<User> updateProfile(@RequestBody User profile, HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		return userOpt.map(user -> {
			if (!"".equals(profile.getFirstName())) user.setFirstName(profile.getFirstName());
			if (!"".equals(profile.getLastName())) user.setLastName(profile.getLastName());
			if (!"".equals(profile.getPhone())) user.setPhone(profile.getPhone());
			if (!"".equals(profile.getEmail())) user.setEmail(profile.getEmail());
			user.setRole(profile.getRole());
			if (!"".equals(profile.getDateOfBirth())) user.setDateOfBirth(profile.getDateOfBirth());
			user = userRepository.save(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

}

package com.podtube.services;

import com.podtube.common.UserRole;
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

		User existingUser = userRepository.findUserByUsernameEquals(user.getUsername());
		if (existingUser != null)
			//TODO: Indicate there is already a user with that username
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		user.setUserRole(UserRole.USER);
		User newUser = userRepository.save(user);
		httpSession.setAttribute("id", newUser.getId());
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@GetMapping("/api/profile")
	public ResponseEntity<User> profile(HttpSession httpSession) {

		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		//TODO: Check for if logged in user is normal user

		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		return userOpt.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@GetMapping("/api/users")
	public ResponseEntity<List<User>> findAllUsers(HttpSession httpSession) {

		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		//TODO: Check for if logged in user is normal user

		return new ResponseEntity<>((List<User>) userRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/api/login")
	public ResponseEntity<User> login(@RequestBody User user, HttpSession httpSession) {

		User userToLogIn = userRepository.findUserByUsernameEqualsAndPasswordEqualsAndUserRoleEquals(
				user.getUsername(),
				user.getPassword(),
				UserRole.USER);

		if(userToLogIn==null){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		httpSession.setAttribute("id", userToLogIn.getId());
		return new ResponseEntity<>(userToLogIn, HttpStatus.OK);
	}

	@PostMapping("/api/logout")
	public void logout(HttpSession httpSession) {
		httpSession.invalidate();
	}

	@PutMapping("/api/profile")
	public ResponseEntity<User> updateProfile(@RequestBody User user, HttpSession httpSession) {

		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		//TODO: Check for if logged in user is normal user

		//TODO: Check if id retrieved is valid id for the incoming user object
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		return new ResponseEntity<>(user, HttpStatus.OK);

		//TODO: Check this implementation
//		return userOpt.map(userToUpdate -> {
//			if (!"".equals(user.getFirstName())) userToUpdate.setFirstName(user.getFirstName());
//			if (!"".equals(user.getLastName())) userToUpdate.setLastName(user.getLastName());
//			if (!"".equals(user.getPhone())) userToUpdate.setPhone(user.getPhone());
//			if (!"".equals(user.getEmail())) userToUpdate.setEmail(user.getEmail());
//			user.setRole(profile.getRole());
//			if (!"".equals(profile.getDateOfBirth())) user.setDateOfBirth(profile.getDateOfBirth());
//			user = userRepository.save(user);
//			return new ResponseEntity<>(user, HttpStatus.OK);
//		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

}

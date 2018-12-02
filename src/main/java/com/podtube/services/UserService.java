package com.podtube.services;

import com.podtube.models.User;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class UserService {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/users")
	public List<User> findAllUsers() {

		return (List<User>) userRepository.findAll();
	}

	@GetMapping("/api/user/{userId}")
	public User findUserById(
			@PathVariable("userId") int userId
			) {
		return userRepository.findById(userId).get();
	}

	@PostMapping("/api/users")
	public List<User> createUser(@RequestBody User user) {
		userRepository.save(user);
		return (List<User>) userRepository.findAll();
	}
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session) {
		session.setAttribute("currentUser", user);
		userRepository.save(user);
		return user;
	}
	
	@GetMapping("/api/profile")
	public User profile(HttpSession session) {
		User currentUser = (User)session.getAttribute("currentUser");
		return userRepository.findById(currentUser.getId()).get();
	}

	@PutMapping("/api/profile")
	public User profile(HttpSession session,
                        @RequestBody User user) {

		User currentUser = (User)session.getAttribute("currentUser");
		User userToUpdate = userRepository.findById(currentUser.getId()).get();
//		userToUpdate.setFirstName(user.getFirstName());
//		userToUpdate.setLastName(user.getLastName());
//		userToUpdate.setDateOfBirth(user.getDateOfBirth());
//		userToUpdate.setPhoneNumber(user.getPhoneNumber());
//		userToUpdate.setEmail(user.getEmail());
//		userToUpdate.setRole(user.getRole());
		userToUpdate = userRepository.save(userToUpdate);
		return userToUpdate;
	}


	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
//	@PostMapping("/api/login")
//	public User login(@RequestBody User credentials, HttpSession session) {
//
//		User user = userRepository.findByUsernameAndPassword(credentials.getUsername(), credentials.getPassword());
//	    session.setAttribute("currentUser", user);
//	    return user;
//	}
}

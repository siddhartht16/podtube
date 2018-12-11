package com.podtube.services;

import com.podtube.common.UserRole;
import com.podtube.customentities.UserProfile;
import com.podtube.customentities.UserPublicProfile;
import com.podtube.models.Comment;
import com.podtube.models.FollowLink;
import com.podtube.models.Rating;
import com.podtube.models.User;
import com.podtube.repositories.CommentRepository;
import com.podtube.repositories.FollowLinkRepository;
import com.podtube.repositories.RatingRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	RatingRepository ratingRepository;

	@Autowired
	FollowLinkRepository followLinkRepository;

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

//	@GetMapping("/api/profile")
//	public ResponseEntity<User> profile(HttpSession httpSession) {
//
//		if (!ServiceUtils.isValidSession(httpSession))
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//		//TODO: Check for if logged in user is normal user
//
//		int id = (int) httpSession.getAttribute("id");
//		Optional<User> userOpt = userRepository.findById(id);
//
//		return userOpt.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
//				.orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
//	}

	@GetMapping("/api/profile")
	public ResponseEntity<UserProfile> self_profile(HttpSession httpSession) {

		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		//TODO: Check for if logged in user is normal user

		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		if(!userOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		User user = userOpt.get();

		List<Comment> comments = commentRepository.findAllByUser_IdOrderByCreatedOnDesc(id);
		List<Rating> ratings = ratingRepository.findAllByUser_IdOrderByCreatedOnDesc(id);

		//Get user followers
		List<FollowLink> followersLinkList = followLinkRepository.findAllByFolloweeId(user.getId());

		//Get the user which the user is following
		List<FollowLink> userFolloweesLinkList = followLinkRepository.findAllByFollowerId(user.getId());

		List<UserPublicProfile> followersList = new ArrayList<>();
		List<UserPublicProfile> followeesList = new ArrayList<>();

		//Populate followers list
		for(FollowLink followLink: followersLinkList){
			User follower = followLink.getFollower();
			UserPublicProfile userPublicProfile = new UserPublicProfile();
			userPublicProfile.setId(follower.getId());
			userPublicProfile.setFirstname(follower.getFirstname());
			userPublicProfile.setLastname(follower.getLastname());
			userPublicProfile.setUsername(follower.getUsername());
			followersList.add(userPublicProfile);
		}

		//Populate followees list
		for(FollowLink followLink: userFolloweesLinkList){
			User userfollowee = followLink.getFollowee();
			UserPublicProfile userPublicProfile = new UserPublicProfile();
			userPublicProfile.setId(userfollowee.getId());
			userPublicProfile.setFirstname(userfollowee.getFirstname());
			userPublicProfile.setLastname(userfollowee.getLastname());
			userPublicProfile.setUsername(userfollowee.getUsername());
			followeesList.add(userPublicProfile);
		}

		UserProfile userProfile = new UserProfile();
		userProfile.setUser(user);
		userProfile.setComments(comments);
		userProfile.setRatings(ratings);
		userProfile.setFollowers(followersList);
		userProfile.setFollowing(followeesList);

		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}


//	@GetMapping("/api/users")
//	public ResponseEntity<List<User>> findAllUsers(HttpSession httpSession) {
//
//		if (!ServiceUtils.isValidSession(httpSession))
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//		//TODO: Check for if logged in user is normal user
//
//		return new ResponseEntity<>((List<User>) userRepository.findAll(), HttpStatus.OK);
//	}

	@PostMapping("/api/login")
	public ResponseEntity<User> login(@RequestBody User user, HttpSession httpSession) {

		User userToLogIn = userRepository.findUserByUsernameEqualsAndPasswordEqualsAndUserRoleEquals(
				user.getUsername(),
				user.getPassword(),
				UserRole.USER);

		if(userToLogIn==null){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		// check if user with id exists
		if (!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User userToUpdate = userOpt.get();
		if (user.getFirstname() != null
				&& !("".equals(user.getFirstname())))
			userToUpdate.setFirstname(user.getFirstname());

		if (user.getLastname() != null
				&& !("".equals(user.getLastname())))
			userToUpdate.setLastname(user.getLastname());

		if (user.getEmail() != null
				&& !("".equals(user.getEmail())))
			userToUpdate.setEmail(user.getEmail());

		if (user.getPassword() != null
				&& !("".equals(user.getPassword())))
			userToUpdate.setPassword(user.getPassword());

		user = userRepository.save(userToUpdate);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@GetMapping("/api/profile/{userId}")
	public ResponseEntity<UserPublicProfile> getUserProfile(HttpSession httpSession,
													  @PathVariable("userId") int userId) {

		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		if(!userOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		User user = userOpt.get();

		Optional<User> queriedUserOpt = userRepository.findById(userId);

		if(!queriedUserOpt.isPresent())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		User queriedUser = queriedUserOpt.get();

		UserPublicProfile userPublicProfile = new UserPublicProfile();

		userPublicProfile.setId(queriedUser.getId());
		userPublicProfile.setUsername(queriedUser.getUsername());
		userPublicProfile.setFirstname(queriedUser.getFirstname());
		userPublicProfile.setLastname(queriedUser.getLastname());

		FollowLink followLink = followLinkRepository.findByFolloweeIdAndFollowerId(queriedUser.getId(), user.getId());
		if (followLink != null)
			userPublicProfile.setFollwed(true);

		return new ResponseEntity<>(userPublicProfile, HttpStatus.OK);
	}//getUserProfile..
}//UserService..

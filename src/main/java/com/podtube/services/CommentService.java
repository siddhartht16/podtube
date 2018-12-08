package com.podtube.services;

import com.podtube.models.Comment;
import com.podtube.models.Podcast;
import com.podtube.models.User;
import com.podtube.repositories.CommentRepository;
import com.podtube.repositories.PodcastRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.activation.CommandMap;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class CommentService {
//	@Autowired
//	CommentRepository commentRepository;
//	@Autowired
//	PodcastRepository podcastRepository;
//	@Autowired
//	UserRepository userRepository;
//
//	@GetMapping("/api/podcast/{podcastId}/comment")
//	ResponseEntity<List<Comment>> findCommentsForPodcast(@PathVariable("podcastId") int podcastId) {
//		Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
//		if(!podcastOpt.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		Podcast podcast = podcastOpt.get();
//		return new ResponseEntity<>(commentRepository.findCommentsByPodcast_IdAndOrderByCreatedOnDesc(podcast.getId()), HttpStatus.OK);
//	}
//
//	@GetMapping("/api/user/{userId}/comment")
//	ResponseEntity<List<Comment>> findCommentsForUser(@PathVariable("userId") int userId) {
//		Optional<User> userOpt = userRepository.findById(userId);
//		if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		User user = userOpt.get();
//		return new ResponseEntity<>(commentRepository.findCommentsByUser_IdAndOrderByCreatedOnDesc(user.getId()), HttpStatus.OK);
//	}
//
//	@PostMapping("/api/podcast/{podcastId}/comment")
//	ResponseEntity<Comment> createComment(HttpSession httpSession,
//										  @PathVariable("podcastId") int podcastId,
//										  @RequestBody Comment comment) {
//        // check LoggedIn
//		if (!ServiceUtils.isValidSession(httpSession))
//			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//        // check if user with id exists
//		int id = (int) httpSession.getAttribute("id");
//		Optional<User> userOpt = userRepository.findById(id);
//		if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//		User user = userOpt.get();
//
//        // check if podcastId exists
//		Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
//		if (!podcastOpt.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//		// create comment
//		Podcast podcast = podcastOpt.get();
//		comment.setPodcast(podcast);
//		comment.setUser(user);
//		return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
//	}
//
//
//	@DeleteMapping("/api/comment/{commentId}")
//	ResponseEntity<List<Comment>> deleteComment(HttpSession httpSession,
//												@PathVariable("commentId") int commentId) {
//	    // check LoggedIn
//        if (!ServiceUtils.isValidSession(httpSession))
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//        // check if user with id exists
//        int id = (int) httpSession.getAttribute("id");
//        Optional<User> userOpt = userRepository.findById(id);
//        if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//
//        User user = userOpt.get();
//
//        // check if commentId exists
//        Optional<Comment> commentOpt = commentRepository.findById(commentId);
//        if (!commentOpt.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        // check if comment is owned by logged in user
//        Comment comment = commentOpt.get();
//        if (comment.getUser().getId() != user.getId()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//
//        // delete comment
//        Podcast podcast = comment.getPodcast();
//        commentRepository.delete(comment);
//        List<Comment> comments = commentRepository.findCommentsByPodcast_IdAndOrderByCreatedOnDesc(podcast.getId());
//        return new ResponseEntity<>(comments, HttpStatus.OK);
//	}
}
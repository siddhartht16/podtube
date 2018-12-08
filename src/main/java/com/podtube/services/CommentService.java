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
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PodcastRepository podcastRepository;
	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/podcast/{podcastId}/comment")
	ResponseEntity<List<Comment>> findCommentsForPodcast(@PathVariable("podcastId") int podcastId) {
		Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
		if(!podcastOpt.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Podcast podcast = podcastOpt.get();
		return new ResponseEntity<>(commentRepository.findCommentsByPodcast(podcast), HttpStatus.OK);
	}

	@GetMapping("/api/user/{userId}/comment")
	ResponseEntity<List<Comment>> findCommentsForUser(@PathVariable("userId") int userId) {
		Optional<User> userOpt = userRepository.findById(userId);
		if(!userOpt.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		User user = userOpt.get();
		return new ResponseEntity<>(commentRepository.findCommentsByUser(user), HttpStatus.OK);
	}

	@PostMapping("/api/podcast/{podcastId}/comment")
	ResponseEntity<Comment> createComment(HttpSession httpSession,
										  @PathVariable("podcastId") int podcastId,
										  @RequestBody Comment comment) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent()) new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		User user = userOpt.get();
		Optional<Podcast> podcastOpt = podcastRepository.findById(podcastId);
		if (!podcastOpt.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Podcast podcast = podcastOpt.get();
		comment.setPodcast(podcast);
		comment.setUser(user);
		return new ResponseEntity<>(commentRepository.save(comment), HttpStatus.OK);
	}
}

/*public class CourseService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CourseRepository courseRepository;

	@PostMapping("/api/course")
	public ResponseEntity<List<Course>> createCourse(@RequestBody Course course, HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		Course newCourse = new Course(course.getTitle());
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		return userOpt.map(user -> {
			newCourse.setUser(user);
			Course addedCourse = courseRepository.save(newCourse);
			return new ResponseEntity<>(addedCourse.getUser().getCourses(), HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@GetMapping("/api/courses")
	public ResponseEntity<List<Course>> findAllCourses(HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");
		Optional<User> userOpt = userRepository.findById(id);

		return userOpt.map(user -> new ResponseEntity<>(user.getCourses(), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST))  ;
	}

	@GetMapping("/api/course/{cid}")
	public ResponseEntity<Course> findCourseById(@PathVariable int cid, HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");

		Optional<Course> courseOpt = courseRepository.findById(cid);

		return courseOpt.filter(course -> course.retrieveUserId() == id)
				.map(course -> new ResponseEntity<>(course, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@PutMapping("/api/course/{cid}")
	public ResponseEntity<Course> updateCourse(@PathVariable int cid,
											   @RequestBody Course updatedCourse,
											   HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");

		Optional<Course> courseOpt = courseRepository.findById(cid);

		return courseOpt.filter(course -> course.retrieveUserId() == id)
				.map(course -> {
					course.setTitle(updatedCourse.getTitle());
					course = courseRepository.save(course);
					return new ResponseEntity<>(course, HttpStatus.OK);
				}).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("/api/course/{cid}")
	public ResponseEntity<List<Course>> deleteCourse(@PathVariable int cid, HttpSession httpSession) {
		if (httpSession.getAttribute("id") == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		int id = (int) httpSession.getAttribute("id");

		Optional<Course> courseOpt = courseRepository.findById(cid);

		return courseOpt.filter(course -> course.retrieveUserId() == id)
				.map(course -> {
					courseRepository.delete(course);

					// user must exist since if we are here then filter must have succeeded
					User user = userRepository.findById(id).get();
					return new ResponseEntity<>(user.getCourses(), HttpStatus.OK);
				})
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
	}
}*/

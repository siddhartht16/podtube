package com.podtube.services;

import com.podtube.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class CommentService {
	@Autowired
	CommentRepository commentRepository;

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

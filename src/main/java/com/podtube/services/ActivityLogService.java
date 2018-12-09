package com.podtube.services;

import com.podtube.models.ActivityLog;
import com.podtube.models.Podcast;
import com.podtube.models.User;
import com.podtube.repositories.ActivityLogRepository;
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
public class ActivityLogService {

	@Autowired
	ActivityLogRepository activityLogRepository;

	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/activity")
	ResponseEntity<List<ActivityLog>> findActivityLogsForUser(HttpSession httpSession) {

		// check LoggedIn
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if user with id exists
		int id = (int) httpSession.getAttribute("id");

		Optional<User> userOpt = userRepository.findById(id);
		if (!userOpt.isPresent())
			new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		User user = userOpt.get();

		return new ResponseEntity<>(activityLogRepository.findActivityLogsByUser_Id(user.getId()), HttpStatus.OK);
	}//findActivityLogsForUser..
}

package com.podtube.services;

import com.podtube.repositories.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class PodcastService {
	@Autowired
	PodcastRepository podcastRepository;

}

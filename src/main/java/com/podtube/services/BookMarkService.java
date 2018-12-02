package com.podtube.services;

import com.podtube.repositories.BookMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class BookMarkService {
	@Autowired
	BookMarkRepository bookMarkRepository;

}

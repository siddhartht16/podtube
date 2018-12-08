package com.podtube.services;

import com.podtube.common.UserRole;
import com.podtube.models.Category;
import com.podtube.models.User;
import com.podtube.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/api/categories")
	public List<Category> getAllCategories() {

		return (List<Category>) categoryRepository.findAll();
	}

	@GetMapping("/api/categories/{categoryId}")
	public Category getCategory(@PathVariable("categoryId") int categoryId) {

		Optional<Category> data = categoryRepository.findById(categoryId);
		if(data.isPresent()){
			Category category = data.get();
			return category;
		}
		return null;
	}

}

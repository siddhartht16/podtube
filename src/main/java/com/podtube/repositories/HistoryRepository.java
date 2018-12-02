package com.podtube.repositories;

import com.podtube.models.Category;
import org.springframework.data.repository.CrudRepository;


public interface HistoryRepository
	extends CrudRepository<Category, Integer> {


}
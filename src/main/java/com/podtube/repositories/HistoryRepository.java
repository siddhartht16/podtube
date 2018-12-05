package com.podtube.repositories;

import com.podtube.models.History;
import org.springframework.data.repository.CrudRepository;


public interface HistoryRepository
	extends CrudRepository<History, Integer> {


}
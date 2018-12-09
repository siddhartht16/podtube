package com.podtube.repositories;

import com.podtube.models.History;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface HistoryRepository
	extends CrudRepository<History, Integer> {

	List<History> findAllByUser_IdOrderByCreatedOnDesc(int userId);

}
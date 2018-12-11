package com.podtube.repositories;

import com.podtube.models.Episode;
import com.podtube.models.History;
import com.podtube.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface HistoryRepository
	extends CrudRepository<History, Integer> {

	List<History> findAllByUser_IdOrderByCreatedOnDesc(int userId);

	History findByUserAndEpisode(User user, Episode episode);
}
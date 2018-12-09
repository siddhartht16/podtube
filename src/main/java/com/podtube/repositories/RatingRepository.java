package com.podtube.repositories;

import com.podtube.models.Rating;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface RatingRepository
	extends CrudRepository<Rating, Integer> {

	List<Rating> findAllByPodcast_IdOrderByCreatedOnDesc(int podcastId);

	List<Rating> findAllByUser_IdOrderByCreatedOnDesc(int userId);

}
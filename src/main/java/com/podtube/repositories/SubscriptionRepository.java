package com.podtube.repositories;

import com.podtube.models.Podcast;
import com.podtube.models.Subscription;

import java.util.List;

import com.podtube.models.User;
import org.springframework.data.repository.CrudRepository;


public interface SubscriptionRepository
	extends CrudRepository<Subscription, Integer> {
	
	List<Subscription> findAllByUser_IdOrderByCreatedOnDesc(int userId);

	Subscription findByUserAndPodcastOrderByCreatedOnDesc(User user, Podcast podcast);
}
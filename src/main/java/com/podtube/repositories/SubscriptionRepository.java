package com.podtube.repositories;

import com.podtube.models.Subscription;
import org.springframework.data.repository.CrudRepository;


public interface SubscriptionRepository
	extends CrudRepository<Subscription, Integer> {


}
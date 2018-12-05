package com.podtube.repositories;

import com.podtube.models.ActivityLog;
import org.springframework.data.repository.CrudRepository;


public interface ActivityRepository
	extends CrudRepository<ActivityLog, Integer> {


}
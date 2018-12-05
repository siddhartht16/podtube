package com.podtube.repositories;

import com.podtube.models.ActivityLog;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ActivityRepository
	extends CrudRepository<ActivityLog, Integer> {

	List<ActivityLog> findActivityLogsByUser_Id(int userId);
}
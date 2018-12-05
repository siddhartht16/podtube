package com.podtube.repositories;

import com.podtube.models.Playlist;
import org.springframework.data.repository.CrudRepository;


public interface PlaylistRepository
	extends CrudRepository<Playlist, Integer> {


}
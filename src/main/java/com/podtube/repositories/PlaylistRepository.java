package com.podtube.repositories;

import com.podtube.models.Bookmark;
import com.podtube.models.Playlist;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PlaylistRepository
	extends CrudRepository<Playlist, Integer> {

	List<Playlist> findAllByUser_IdOrderByCreatedOnDesc(int userId);

	Playlist findByIdEqualsAndUserId(int playlistId, int userId);

}
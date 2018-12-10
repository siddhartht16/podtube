package com.podtube.repositories;

import com.podtube.models.FollowLink;
import com.podtube.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FollowLinkRepository
        extends CrudRepository<FollowLink, Integer> {

    List<FollowLink> findAllByFolloweeId(int followeeId);

    List<FollowLink> findAllByFollowerId(int followerId);
}

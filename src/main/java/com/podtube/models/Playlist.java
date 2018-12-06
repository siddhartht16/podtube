package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "playlist")
public class Playlist {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@ManyToMany
	@JoinTable(name = "playlist_episodes",
			joinColumns = @JoinColumn(name = "playlist_id"),
			inverseJoinColumns = @JoinColumn(name = "episodes_id")
	)
	private Set<Episode> episodes;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@Column(name = "title")
	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	public Playlist() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Set<Episode> episodes) {
		this.episodes = episodes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	// TODO: Add add episode function

	// TODO: Add remove episode function
}

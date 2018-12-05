package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

//TODO : Add table name, column names
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Playlist {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	// TODO: Add join table and join column fields
	@ManyToMany
	private Set<Episode> episodes;

	// TODO: Add join column field
	@ManyToOne
	@JsonIgnore
	private User user;

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

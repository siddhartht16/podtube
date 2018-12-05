package com.podtube.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//TODO : Add table name, column names
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Bookmark {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	// TODO: Add join column field
	@ManyToOne
	private User user;

	// TODO: Add join column field
	@ManyToOne
	private Episode episode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	public Bookmark() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}
}

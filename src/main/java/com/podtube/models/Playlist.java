package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Playlist {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Playlist() {}

	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	//TODO: Add multiple episodes in one playlist
	//Episode is not concerned with playlist

	@OneToOne
//	@JsonIgnore
	private User user;
}

package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.podtube.common.RatingTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//TODO : Add table name, column names
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Rating {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private User user;

	@ManyToOne
	@JsonIgnore
	private Podcast podcast;

	private RatingTypes ratingType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Rating() {}

	public RatingTypes getRatingType() {
		return ratingType;
	}

	public void setRatingType(RatingTypes ratingType) {
		this.ratingType = ratingType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Podcast getPodcast() {
		return podcast;
	}

	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}
}

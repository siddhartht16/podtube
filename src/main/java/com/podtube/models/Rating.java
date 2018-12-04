package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.podtube.common.RatingTypes;

import javax.persistence.*;

@Entity
public class Rating {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
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

	private RatingTypes ratingType;

	@ManyToOne
//	@JsonIgnore
	private User user;

	@ManyToOne
	@JsonIgnore
	private Podcast podcast;
}

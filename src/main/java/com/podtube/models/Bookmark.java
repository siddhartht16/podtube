package com.podtube.models;

import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Bookmark {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Bookmark() {}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	//TODO: Calculated field to compute played status for each user

	@OneToOne
//	@JsonIgnore
	private User user;

	@OneToOne
//	@JsonIgnore
	private Podcast podcast;

	@OneToOne
//	@JsonIgnore
	private Episode episode;

}

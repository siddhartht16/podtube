package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class History {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public History() {}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "played_on", nullable = false)
	private Date playedOn;

	@ManyToOne
//	@JsonIgnore
	private User user;

	//TODO: Manual delete history when either of them is deleted
	@OneToOne
//	@JsonIgnore
	private Podcast podcast;

	@OneToOne
//	@JsonIgnore
	private Episode episode;
}

package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Podcast {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	public Podcast() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIgnore
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	private String url;
	private String title;
	@Lob
	private String description;
	private int subscribers;
	private int subscribers_last_week;
	private String logo_url;
	private String scaled_logo_url;
	private String website;
	private String mygpo_link;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(int subscribers) {
		this.subscribers = subscribers;
	}

	public int getSubscribers_last_week() {
		return subscribers_last_week;
	}

	public void setSubscribers_last_week(int subscribers_last_week) {
		this.subscribers_last_week = subscribers_last_week;
	}

	public String getLogo_url() {
		return logo_url;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public String getScaled_logo_url() {
		return scaled_logo_url;
	}

	public void setScaled_logo_url(String scaled_logo_url) {
		this.scaled_logo_url = scaled_logo_url;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMygpo_link() {
		return mygpo_link;
	}

	public void setMygpo_link(String mygpo_link) {
		this.mygpo_link = mygpo_link;
	}


	//	@ManyToOne
//	@OnDelete(action = OnDeleteAction.CASCADE)
////	@JsonIgnore
//	private User createdBy;
//
//	@ManyToOne
//	@OnDelete(action = OnDeleteAction.CASCADE)
////	@JsonIgnore
//	private User modifiedBy;


}

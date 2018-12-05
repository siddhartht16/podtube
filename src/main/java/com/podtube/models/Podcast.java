package com.podtube.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

//TODO : Add table name, column names
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Podcast {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	// TODO: Add join table and join column fields
	@ManyToMany(cascade = {
			CascadeType.PERSIST,
			CascadeType.MERGE
	})
//	@JoinTable(name = "category_podcasts",
//			joinColumns = @JoinColumn(name = "podcast_id"),
//			inverseJoinColumns = @JoinColumn(name = "category_id")
//	)
	private Set<Category> categories;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	private String createdBy;
	private String modifiedBy;

	@Transient
	private boolean isSubscribed;

	@Transient
	private double averageRating;

	@Transient
	private long numSubscriptions;

	@Transient
	private long subscribersLastWeek;

	public Podcast() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}


	//Read from episode data
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public boolean isSubscribed() {
		return isSubscribed;
	}

	public void setSubscribed(boolean subscribed) {
		isSubscribed = subscribed;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public long getNumSubscriptions() {
		return numSubscriptions;
	}

	public void setNumSubscriptions(long numSubscriptions) {
		this.numSubscriptions = numSubscriptions;
	}

	public long getSubscribersLastWeek() {
		return subscribersLastWeek;
	}

	public void setSubscribersLastWeek(long subscribersLastWeek) {
		this.subscribersLastWeek = subscribersLastWeek;
	}

	// TODO: Add add category function

	// TODO: Add remove category function
}

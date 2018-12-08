package com.podtube.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "podcast")
public class Podcast {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@ManyToMany
	@JoinTable(name = "podcast_categories",
			joinColumns = @JoinColumn(name = "podcast_id"),
			inverseJoinColumns = @JoinColumn(name = "categories_id")
	)
	private Set<Category> categories = new HashSet<>();

	@Column(name = "url")
	private String url;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	@Lob
	private String description;

	@Column(name = "gpodder_subscribers")
	private int gpodder_subscribers;

	@Column(name = "gpodder_subscribers_last_week")
	private int gpodder_subscribers_last_week;

	@Column(name = "logo_url")
	private String logo_url;

	@Column(name = "scaled_logo_url")
	private String scaled_logo_url;

	@Column(name = "website")
	private String website;

	@Column(name = "mygpo_link")
	private String mygpo_link;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "author")
	private String author;

	@Transient
	private boolean isSubscribed;

	@Transient
	private double averageRating;

	@Transient
	private long numSubscriptions;

	@Transient
	private long subscribersLastWeek;

	public Podcast() {}

	public void addCategory(Category category){
		this.categories.add(category);
	}

	public void removeCategory(Category category){
		this.categories.remove(category);
	}

	public String getUrl() {
		return url;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGpodder_subscribers(int subscribers) {
		this.gpodder_subscribers = subscribers;
	}

	public void setGpodder_subscribers_last_week(int subscribers_last_week) {
		this.gpodder_subscribers_last_week = subscribers_last_week;
	}

	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}

	public void setScaled_logo_url(String scaled_logo_url) {
		this.scaled_logo_url = scaled_logo_url;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setMygpo_link(String mygpo_link) {
		this.mygpo_link = mygpo_link;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setSubscribed(boolean subscribed) {
		isSubscribed = subscribed;
	}

	public int getId() {
		return id;
	}
}

package com.podtube.models;

import com.podtube.common.MediaTypes;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.podtube.common.AppConstants.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Episode {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	public Episode() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JsonIgnore
	private Podcast podcast;

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public Podcast getPodcast() {
		return podcast;
	}

	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	private MediaTypes mediaTypes;

	public MediaTypes getMediaTypes() {
		return mediaTypes;
	}

	public void setMediaTypes(MediaTypes mediaTypes) {
		this.mediaTypes = mediaTypes;
	}

//	{
//		"title": "Episode 472 - 13 Piece Drought - 10/25/18",
//			"pubDate": "2018-10-25 07:00:00",
//			"link": "http://www.tested.com/old-categories/podcasts/853514-episode-472-13-piece-drought-102518/",
//			"guid": "http://www.tested.com/old-categories/podcasts/853514-episode-472-13-piece-drought-102518/",
//			"author": "Will Smith, Norman Chan, Jeremy Williams",
//			"thumbnail": "http://files.tested.com/static/podcast_logo.jpg",
//			"description": "The world crowns a new Tetris champion, a legend of animation is set to retire, and Netflix shocks us with two announcements. Plus, we discuss iPhone XR reviews, expectations for the upcoming Apple event, and a surprise car model from Tesla.",
//			"content": "The world crowns a new Tetris champion, a legend of animation is set to retire, and Netflix shocks us with two announcements. Plus, we discuss iPhone XR reviews, expectations for the upcoming Apple event, and a surprise car model from Tesla.",
//			"enclosure": {
//		"link": "https://media.blubrry.com/thisisonlyatest/d2rormqr1qwzpz.cloudfront.net/podcast/thisisonlyatest_20181025.mp3",
//				"type": "audio/mpeg",
//				"length": 50036782,
//				"duration": 6187,
//				"thumbnail": "http://files.tested.com/static/podcast_logo.jpg",
//				"rating": {
//			"scheme": "urn:itunes",
//					"value": "no"
//		}
//	},
//		"categories": []
//	},

	private String title;
	private Date pubDate;
	private String link;
	private String guid;
	private String author;
	private String thumbnail;

	@Lob
	private String description;

	@Lob
	private String content;

	private String enclosureLink;
	private String enclosureType;
	private Long enclosureLength;
	private Long enclosureDuration;
	private String enclosureThumbnail;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEnclosureLink() {
		return enclosureLink;
	}

	public void setEnclosureLink(String enclosureLink) {
		this.enclosureLink = enclosureLink;
	}

	public String getEnclosureType() {
		return enclosureType;
	}

	public void setEnclosureType(String enclosureType) {
		this.enclosureType = enclosureType;

		//Set media type based off enclosure type
		switch (this.enclosureType.toLowerCase()){
			case GPODDER_MEDIA_TYPE_AUDIO:this.setMediaTypes(MediaTypes.AUDIO);break;
			case GPODDER_MEDIA_TYPE_VIDEO:this.setMediaTypes(MediaTypes.VIDEO);break;
			default:this.setMediaTypes(MediaTypes.AUDIO);
		}
	}

	public Long getEnclosureLength() {
		return enclosureLength;
	}

	public void setEnclosureLength(Long enclosureLength) {
		this.enclosureLength = enclosureLength;
	}

	public Long getEnclosureDuration() {
		return enclosureDuration;
	}

	public void setEnclosureDuration(Long enclosureDuration) {
		this.enclosureDuration = enclosureDuration;
	}

	public String getEnclosureThumbnail() {
		return enclosureThumbnail;
	}

	public void setEnclosureThumbnail(String enclosureThumbnail) {
		this.enclosureThumbnail = enclosureThumbnail;
	}
//Based off categories in episode json
	//TODO: Revisit this
	//private Category category;

}

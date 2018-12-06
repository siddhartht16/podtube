package com.podtube.models;

import com.podtube.common.MediaType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

import static com.podtube.common.AppConstants.*;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "episode")
public class Episode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "podcast_id")
	private Podcast podcast;

	@Column(name = "media_type")
	private MediaType mediaType;

	@Column(name = "title")
	private String title;

	@Column(name = "pub_date")
	private Date pubDate;

	@Column(name = "link")
	private String link;

	@Column(name = "guid")
	private String guid;

	@Column(name = "author")
	private String author;

	@Column(name = "thumbnail")
	private String thumbnail;

	@Column(name = "description")
	@Lob
	private String description;

	@Column(name = "content")
	@Lob
	private String content;

	//TODO: Consider scenarios where this may not have the media link, check in other places
	@Column(name = "enclosure_link")
	private String enclosureLink;

	@Column(name = "enclosure_type")
	private String enclosureType;

	@Column(name = "enclosure_length")
	private Long enclosureLength;

	@Column(name = "enclosure_duration")
	private Long enclosureDuration;

	@Column(name = "enclosure_thumbnail")
	private String enclosureThumbnail;

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

	@Transient
	private boolean isPlayed;

	public Episode() {
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public void setEnclosureType(String enclosureType) {
		this.enclosureType = enclosureType;

		//Set media type based off enclosure type
		switch (this.enclosureType.toLowerCase()) {
			case GPODDER_MEDIA_TYPE_AUDIO:
				this.setMediaType(MediaType.AUDIO);
				break;
			case GPODDER_MEDIA_TYPE_VIDEO:
				this.setMediaType(MediaType.VIDEO);
				break;
			default:
				this.setMediaType(MediaType.AUDIO);
		}
	}

	// TODO: Verify if link is the unique business key
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Episode e = (Episode) o;
		return link.equals(e.link);
	}

	@Override
	public int hashCode() {
		return link.hashCode();
	}
}

package com.podtube.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "history")
public class History {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@ManyToOne
    @JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
    @JoinColumn(name = "episode_id")
	private Episode episode;

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

	public History() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Episode getEpisode() {
		return episode;
	}

	public void setEpisode(Episode episode) {
		this.episode = episode;
	}

	public Date getPlayedOn() {
		return playedOn;
	}

	public void setPlayedOn(Date playedOn) {
		this.playedOn = playedOn;
	}
}

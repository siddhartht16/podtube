package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Category {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	private String title;
	private String tag;

	public int getTagUsage() {
		return tagUsage;
	}

	public void setTagUsage(int tagUsage) {
		this.tagUsage = tagUsage;
	}

	private int tagUsage;

	public Category() {}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

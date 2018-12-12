package com.podtube.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "category")
public class Category {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "tag")
	private String tag;

	@Column(name = "tag_usage")
	private int tagUsage;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	public Category() {}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Category cat = (Category) o;
		return tag.equals(cat.tag);
	}

	@Override
	public int hashCode() {
		return tag.hashCode();
	}

	public String getTag() {
		return tag;
	}

	public int getId() {
		return id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setTagUsage(int tagUsage) {
		this.tagUsage = tagUsage;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}

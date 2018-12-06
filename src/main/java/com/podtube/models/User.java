package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.podtube.common.UserRole;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_role")
	private UserRole userRole;

	@Column(name = "username")
	private String username;

	@JsonIgnore
	@Column(name = "password")
	private String password;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "lastname")
	private String lastname;

	@Column(name = "email")
	private String email;

	@Transient
	//This means the number of users following this user
	private long followerCount;

	@Transient
	//This means the number of users this user follows
	private long followeeCount;

	//When a user views another user, show if the logged in user is following the viewed user
	@Transient
	private boolean isFollowing;

	//When a user searches for another user, show if the viewed user follows the loggedin user
	@Transient
	private boolean isFollowedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false, updatable = false)
	@CreatedDate
	private Date createdOn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modified_on", nullable = false)
	@LastModifiedDate
	private Date modifiedOn;

	public User() {}
}

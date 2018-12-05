package com.podtube.models;

import com.podtube.common.UserRole;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

//TODO : Add table name, column names
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	private UserRole userRole;

	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;

	@Transient
	private long followers;

	@Transient
	private long followees;

	// When a user searches for another user, show if the loggedin user is following the other user
	@Transient
	private boolean isFollowing;

	// When a user searches for another user, show if the loggedin user is followed by the other user
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getFollowers() {
		return followers;
	}

	public void setFollowers(long followers) {
		this.followers = followers;
	}

	public long getFollowees() {
		return followees;
	}

	public void setFollowees(long followees) {
		this.followees = followees;
	}

	public boolean isFollowing() {
		return isFollowing;
	}

	public void setFollowing(boolean following) {
		isFollowing = following;
	}

	public boolean isFollowedBy() {
		return isFollowedBy;
	}

	public void setFollowedBy(boolean followedBy) {
		isFollowedBy = followedBy;
	}
}

package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.podtube.common.UserRoles;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

	public User() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public UserRoles getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(UserRoles userRoles) {
		this.userRoles = userRoles;
	}

	private UserRoles userRoles;

	private String username;
	private String password;
	private String firstname;
	private String lastname;

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

	private String email;

	@OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<Comment>();

	@OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Playlist> playlists = new ArrayList<Playlist>();

	@OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Rating> ratings = new ArrayList<Rating>();

	@OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Bookmark> bookmarks = new ArrayList<Bookmark>();

	@OneToMany(mappedBy = "user", cascade= CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<History> history = new ArrayList<History>();

	//TODO: Define many to many for followers


}

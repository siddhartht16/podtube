package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Podcast {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String title;
	public Podcast() {}
	public Podcast(String title) {
		this.title = title;
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
}

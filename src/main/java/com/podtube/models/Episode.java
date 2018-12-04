package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
public class Episode {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String title;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String url;

    public String iconUrl;
    public String description;

    @ManyToOne
    @JsonIgnore
    public Podcast podcast;

	public Episode() {}

	public Episode(String title) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Podcast getPodcast() {
        return podcast;
    }

    public void setPodcast(Podcast podcast) {
        this.podcast = podcast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Episode e = (Episode) o;
        return url.equals(e.url);
    }

    @Override
    public int hashCode() {
	    return url.hashCode();
    }
}

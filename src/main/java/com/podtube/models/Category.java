package com.podtube.models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Category {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;

    @NaturalId
    @Column(nullable = false, unique = true)
	private String title;

	@ManyToMany(mappedBy = "categories", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
	private Set<Podcast> podcasts;

	public Category() {}

	public Category(String title) {
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

	public Set<Podcast> getPodcasts() {
	    return podcasts;
    }

    public void setPodcasts(Set<Podcast> podcasts) {
	    this.podcasts = podcasts;
    }

    public void addPodcast(Podcast podcast) {
	    podcasts.add(podcast);
	    podcast.getCategories().add(this);
    }

    public void removePodcast(Podcast podcast) {
	    podcasts.remove(podcast);
	    podcast.getCategories().remove(this);
    }

    public void remove() {
	    for (Podcast p : podcasts) {
	        p.removeCategory(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category cat = (Category) o;
        return title.equals(cat.title);
    }

    @Override
    public int hashCode() {
	    return title.hashCode();
    }
}

package com.podtube.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Podcast {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	private String title;

	@NaturalId
    @Column(nullable = false, unique = true)
	private String url;

	private String description;
	private String iconUrl;

	@ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonIgnore
	private Set<Category> categories;

	@OneToMany(mappedBy = "podcast", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Episode> episodes;

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

	public Set<Category> getCategories() {
	    return categories;
    }

    public void setCategories(Set<Category> categories) {
	    this.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<Episode> getEpisodes() {
	    return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
	    this.episodes = episodes;
    }

    public void addCategory(Category category) {
	    categories.add(category);
	    category.getPodcasts().add(this);
    }

    public void removeCategory(Category category) {
	    categories.remove(category);
	    category.getPodcasts().remove(this);
    }

    public void addEpisode(Episode episode) {
	    episodes.add(episode);
	    episode.setPodcast(this);
    }

    public void removeEpisode(Episode episode) {
	    episodes.remove(episode);
	    episode.setPodcast(null);
    }

    public void remove() {
	    for (Category c : categories) {
	        c.removePodcast(this);
        }
    }

    @Override
    public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Podcast p = (Podcast) o;
	    return url.equals(p.url);
    }

    @Override
    public int hashCode() {
	    return url.hashCode();
    }
}

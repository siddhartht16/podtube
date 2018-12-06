package com.podtube.services;

import com.podtube.datasourceapi.APIUtils;
import com.podtube.feedentities.RSSFeedItem;
import com.podtube.gpodderentities.GPodderCategory;
import com.podtube.gpodderentities.GPodderPodcast;
import com.podtube.models.Category;
import com.podtube.models.Episode;
import com.podtube.models.Podcast;
import com.podtube.repositories.CategoryRepository;
import com.podtube.repositories.EpisodeRepository;
import com.podtube.repositories.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class SyncService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	PodcastRepository podcastRepository;

	@Autowired
	EpisodeRepository episodeRepository;

	private APIUtils apiUtils = new APIUtils();

	private void syncCategoriesFromGpodder(){

		//Get categories from GPodder
		List<GPodderCategory> gPodderCategories = apiUtils.getCategories();

		//
		for(GPodderCategory gPodderCategory: gPodderCategories){

			String tag = gPodderCategory.getTag();

			Category categoryForTag = null;

			try {
				categoryForTag = categoryRepository.findByTagEquals(tag.toLowerCase());
			}
			catch (NullPointerException ex){
				//TODO: Log this
			}

			if(categoryForTag==null){
				//Add to database
				Category category = new Category();
				category.setTitle(gPodderCategory.getTitle());
				category.setTag(gPodderCategory.getTag());
				category.setTagUsage(gPodderCategory.getTagUsage());
				categoryRepository.save(category);
			}//if..
		}//for..
	}//syncCategoriesFromGpodder..

	public void syncPodcastsForCategoryFromGpodder(int categoryId){

		Optional<Category> data = categoryRepository.findById(categoryId);
		if(!data.isPresent()){
			return;
		}

		Category category = data.get();

		//Get categories from GPodder
		List<GPodderPodcast> gPodderPodcasts = apiUtils.getPodcastsForTag(category.getTag());

		//
		for(GPodderPodcast gPodderPodcast: gPodderPodcasts){

			//TODO: REVISIT THIS CRITERIA
			//Considering url as unique criteria as of now for podcasts
			String url = gPodderPodcast.getUrl();

			Podcast podcastForUrl = null;

			try {
				podcastForUrl = podcastRepository.findByUrlEquals(url.toLowerCase());
			}
			catch (NullPointerException ex){
				//TODO: Log this
			}

			if(podcastForUrl==null){
				//Add to database
				Podcast podcast = new Podcast();

				//podcast.setCategory(category);
				podcast.setUrl(gPodderPodcast.getUrl());
				podcast.setTitle(gPodderPodcast.getTitle());
				podcast.setDescription(gPodderPodcast.getDescription());
				podcast.setLogo_url(gPodderPodcast.getLogoUrl());
				podcast.setMygpo_link(gPodderPodcast.getMyGPOLink());
				podcast.setScaled_logo_url(gPodderPodcast.getScaledLogoUrl());
				podcast.setSubscribers(gPodderPodcast.getSubscribers());
				podcast.setSubscribers_last_week(gPodderPodcast.getSubscribersLastWeek());
				podcast.setWebsite(gPodderPodcast.getWebsite());
				podcastRepository.save(podcast);
			}//if..
		}//for..
	}//syncPodcastsForCategoryFromGpodder..

	public void syncEpisodesForPodcastFromRSSFeed(int podcastId){

		Optional<Podcast> data = podcastRepository.findById(podcastId);
		if(!data.isPresent()){
			return;
		}

		Podcast podcast = data.get();

		//Get categories from GPodder
		List<RSSFeedItem> rssFeedItems = apiUtils.getEpisodesForPodcast(podcast.getUrl());

		//
		for(RSSFeedItem rssFeedItem: rssFeedItems){

			//TODO: REVISIT THIS CRITERIA
			//Considering url as unique criteria as of now for podcasts
			String enclosure_link = rssFeedItem.getEnclosure_link();

			Episode episodeForUrl = null;

			try {
				episodeForUrl = episodeRepository.findByEnclosureLinkEquals(enclosure_link.toLowerCase());
			}
			catch (NullPointerException ex){
				//TODO: Log this
			}

			if(episodeForUrl==null){
				//Add to database
				Episode episode = new Episode();
				episode.setPodcast(podcast);
				episode.setTitle(rssFeedItem.getTitle());
				episode.setPubDate(rssFeedItem.getPubDate());
				episode.setLink(rssFeedItem.getLink());
				episode.setGuid(rssFeedItem.getGuid());
				episode.setAuthor(rssFeedItem.getAuthor());
				episode.setThumbnail(rssFeedItem.getThumbnail());
				episode.setDescription(rssFeedItem.getDescription());
				episode.setContent(rssFeedItem.getContent());
				episode.setEnclosureLink(rssFeedItem.getEnclosure_link());
				episode.setEnclosureType(rssFeedItem.getEnclosure_type());
				episode.setEnclosureDuration(rssFeedItem.getEnclosure_duration());
				episode.setEnclosureLength(rssFeedItem.getEnclosure_length());
				episode.setEnclosureThumbnail(rssFeedItem.getEnclosure_thumbnail());
				episodeRepository.save(episode);
			}//if..
		}//for..
	}//syncEpisodesForPodcastFromRSSFeed..

	@PostMapping("/api/sync/categories")
	public List<Category> syncCategories() {

		this.syncCategoriesFromGpodder();
		return (List<Category>) categoryRepository.findAll();
	}

//	@PostMapping("/api/sync/categories/{categoryId}/podcasts")
//	public List<Podcast> syncPodcastsForCategory(@PathVariable("categoryId") int categoryId) {
//
//		this.syncPodcastsForCategoryFromGpodder(categoryId);
//		return (List<Podcast>) podcastRepository.findPodcastsByCategoryId(categoryId);
//	}

	@PostMapping("/api/sync/podcasts/{podcastId}/episodes")
	public List<Episode> syncEpisodesForPodcast(@PathVariable("podcastId") int podcastId) {

		this.syncEpisodesForPodcastFromRSSFeed(podcastId);
		return (List<Episode>) episodeRepository.findEpisodesByPodcastId(podcastId);
	}
}

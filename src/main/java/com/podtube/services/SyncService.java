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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

	@Autowired
	AdminService adminService;

	private APIUtils apiUtils = new APIUtils();

	private void syncCategoriesFromGpodder(){

		//Get categories from GPodder
		List<GPodderCategory> gPodderCategories = apiUtils.getCategories();

		//
		for(GPodderCategory gPodderCategory: gPodderCategories){

			String tag = gPodderCategory.getTag();

			// skip if tag is empty
			if ("".equals(tag))
				continue;

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

				//Add created by and modified by from session

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

		//Create new podcasts if not already present
		for(GPodderPodcast gPodderPodcast: gPodderPodcasts){

			//TODO: REVISIT THIS CRITERIA
			//Considering url as unique criteria as of now for podcasts

			// skip if url is empty
			String url = gPodderPodcast.getUrl();
			if ("".equals(url))
				continue;

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

				//Set podcast fields
				podcast.setUrl(gPodderPodcast.getUrl());
				podcast.setTitle(gPodderPodcast.getTitle());
				podcast.setDescription(gPodderPodcast.getDescription());
				podcast.setLogo_url(gPodderPodcast.getLogoUrl());
				podcast.setMygpo_link(gPodderPodcast.getMyGPOLink());
				podcast.setScaled_logo_url(gPodderPodcast.getScaledLogoUrl());
				podcast.setGpodder_subscribers(gPodderPodcast.getSubscribers());
				podcast.setGpodder_subscribers_last_week(gPodderPodcast.getSubscribersLastWeek());
				podcast.setWebsite(gPodderPodcast.getWebsite());

				//Set category
				podcast.addCategory(category);

				//Add created by and modified by from session

				podcastRepository.save(podcast);
			}//if..
			else{
				//Add this category to existing podcast
				podcastForUrl.addCategory(category);
			}
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
			//Considering url as unique criteria as of now for podcasts
			String enclosure_link = rssFeedItem.getEnclosure_link();

			// Skip episode if no playable link
			if ("".equals(enclosure_link))
				continue;

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

	@PostMapping("/admin/sync/categories")
	public ResponseEntity<List<Category>> syncCategories(HttpSession httpSession) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if logged in user is admin
		int id = (int) httpSession.getAttribute("id");
		if(!adminService.isAdminUser(id)){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		this.syncCategoriesFromGpodder();
		List<Category> syncedCategories = (List<Category>) categoryRepository.findAll();
		return new ResponseEntity<>(syncedCategories, HttpStatus.OK);
	}

	@PostMapping("/admin/sync/categories/{categoryId}/podcasts")
	public ResponseEntity<List<Podcast>> syncPodcastsForCategory(HttpSession httpSession,
												 @PathVariable("categoryId") int categoryId) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if logged in user is admin
		int id = (int) httpSession.getAttribute("id");
		if(!adminService.isAdminUser(id)){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		this.syncPodcastsForCategoryFromGpodder(categoryId);
		List<Podcast> syncedPodcasts = podcastRepository.getPodcastsForCategory(categoryId);
		return new ResponseEntity<>(syncedPodcasts, HttpStatus.OK);
	}

	@PostMapping("/admin/sync/podcasts/{podcastId}/episodes")
	public ResponseEntity<List<Episode>> syncEpisodesForPodcast(HttpSession httpSession,
												@PathVariable("podcastId") int podcastId) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if logged in user is admin
		int id = (int) httpSession.getAttribute("id");
		if(!adminService.isAdminUser(id)){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		this.syncEpisodesForPodcastFromRSSFeed(podcastId);
		List<Episode> syncedEpisodes = episodeRepository.findEpisodesByPodcastId(podcastId);
		return new ResponseEntity<>(syncedEpisodes, HttpStatus.OK);
	}
}

package com.podtube.services;

import com.mysql.jdbc.MysqlDataTruncation;
import com.podtube.common.UserRole;
import com.podtube.customentities.ResponseWrapper;
import com.podtube.datasourceapi.APIUtils;
import com.podtube.feedentities.RSSFeedItem;
import com.podtube.gpodderentities.GPodderCategory;
import com.podtube.gpodderentities.GPodderPodcast;
import com.podtube.models.Category;
import com.podtube.models.Episode;
import com.podtube.models.Podcast;
import com.podtube.models.User;
import com.podtube.repositories.CategoryRepository;
import com.podtube.repositories.EpisodeRepository;
import com.podtube.repositories.PodcastRepository;
import com.podtube.repositories.UserRepository;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
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
	UserRepository userRepository;

	@Autowired
	AdminService adminService;

	private APIUtils apiUtils = new APIUtils();

	private void syncCategoriesFromGpodder(int userId){

		if(userId<1){
			return;
		}

		User user = userRepository.findByIdEqualsAndUserRoleEquals(userId, UserRole.ADMIN);

		if(user==null){
			return;
		}

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

			Category categoryObj;

			if(categoryForTag==null){
				//Add to database
				Category category = new Category();
				category.setTitle(gPodderCategory.getTitle());
				category.setTag(gPodderCategory.getTag());
				category.setTagUsage(gPodderCategory.getTagUsage());

				//Add created by and modified by from session
				category.setCreatedBy(user.getUsername());
				category.setModifiedBy(user.getUsername());

				//Add last synced on
				category.setLastSyncedOn(new Date());

				categoryObj = category;

//				try {
//					categoryRepository.save(category);
//				}
//				catch (DataException dataException){
//					dataException.printStackTrace();
//				}
			}//if..
			else{
				//Add created by and modified by
				if (categoryForTag.getCreatedBy() == null || "".equals(categoryForTag.getCreatedBy()))
					categoryForTag.setCreatedBy(user.getUsername());
				if (categoryForTag.getModifiedBy() == null || "".equals(categoryForTag.getModifiedBy()))
					categoryForTag.setModifiedBy(user.getUsername());

				//Add last synced on
				categoryForTag.setLastSyncedOn(new Date());

				categoryObj = categoryForTag;
			}//else..

			try {
				categoryRepository.save(categoryObj);
			}
			catch (DataException dataException){
				dataException.printStackTrace();
			}
		}//for..
	}//syncCategoriesFromGpodder..

	private void syncPodcastsForAllCategoriesFromGpodder(int userId) throws InterruptedException {

		if(userId<1){
			return;
		}

		User user = userRepository.findByIdEqualsAndUserRoleEquals(userId, UserRole.ADMIN);

		if(user==null){
			return;
		}

		this.syncCategoriesFromGpodder(userId);
		Thread.sleep(1000);

		List<Category> categoriesList = (List<Category>) categoryRepository.findAll();

		for (Category category : categoriesList) {

			int categoryId = category.getId();
			this.syncPodcastsForCategoryFromGpodder(userId, categoryId);
			Thread.sleep(1000);
		}//for..
	}//syncCategoriesFromGpodder..

	public void syncPodcastsForCategoryFromGpodder(int userId, int categoryId){

		if(userId<1){
			return;
		}

		User user = userRepository.findByIdEqualsAndUserRoleEquals(userId, UserRole.ADMIN);

		if(user==null){
			return;
		}

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

			Podcast podcastObj;

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
				podcast.setCreatedBy(user.getUsername());
				podcast.setModifiedBy(user.getUsername());

				//Set last synced on
				podcast.setLastSyncedOn(new Date());

//				try {
//					podcastRepository.save(podcast);
//				}
//				catch (DataException dataException){
//					dataException.printStackTrace();
//				}

				podcastObj = podcast;
			}//if..
			else{
				//Add this category to existing podcast
				podcastForUrl.addCategory(category);

				//Add created by and modified by
				if (podcastForUrl.getCreatedBy() == null || "".equals(podcastForUrl.getCreatedBy()))
					podcastForUrl.setCreatedBy(user.getUsername());
				if (podcastForUrl.getCreatedBy() == null || "".equals(podcastForUrl.getModifiedBy()))
					podcastForUrl.setModifiedBy(user.getUsername());

				podcastObj = podcastForUrl;
			}//else..

			try {
				podcastRepository.save(podcastObj);
			}
			catch (DataException dataException){
				dataException.printStackTrace();
			}
		}//for..
	}//syncPodcastsForCategoryFromGpodder..

	public void syncEpisodesForPodcastFromRSSFeed(int userId, int podcastId){

		if(userId<1){
			return;
		}

		User user = userRepository.findByIdEquals(userId);

		if(user==null){
			return;
		}

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

			Episode episodeObj;

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

				//Add createdby and modified by
				episode.setCreatedBy(user.getUsername());
				episode.setModifiedBy(user.getUsername());

				//Set last synced on
				episode.setLastSyncedOn(new Date());

//				try {
//					episodeRepository.save(episode);
//				}
//				catch (DataException dataException){
//					dataException.printStackTrace();
//				}

				episodeObj = episode;
			}//if..
			else{
				//Add created by and modified by
				if (episodeForUrl.getCreatedBy() == null || "".equals(episodeForUrl.getCreatedBy()))
					episodeForUrl.setCreatedBy(user.getUsername());
				if (episodeForUrl.getCreatedBy() == null || "".equals(episodeForUrl.getModifiedBy()))
					episodeForUrl.setModifiedBy(user.getUsername());

				episodeObj = episodeForUrl;
			}//else..

			try {
				episodeRepository.save(episodeObj);
			}
			catch (DataException dataException){
				dataException.printStackTrace();
			}
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

		this.syncCategoriesFromGpodder(id);
		List<Category> syncedCategories = (List<Category>) categoryRepository.findAll();
		return new ResponseEntity<>(syncedCategories, HttpStatus.OK);
	}

	@PostMapping("/admin/sync/allcategories/podcasts")
	public ResponseEntity<ResponseWrapper> syncAllCategoriesPodcasts(HttpSession httpSession) {
		if (!ServiceUtils.isValidSession(httpSession))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

		// check if logged in user is admin
		int id = (int) httpSession.getAttribute("id");
		if (!adminService.isAdminUser(id)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		ResponseWrapper responseWrapper = new ResponseWrapper();

		try {
			this.syncPodcastsForAllCategoriesFromGpodder(id);
			responseWrapper.setSuccess(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
			responseWrapper.setSuccess(false);
		}

		return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
	}//syncAllCategoriesPodcasts..

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

		this.syncPodcastsForCategoryFromGpodder(id, categoryId);
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

		this.syncEpisodesForPodcastFromRSSFeed(id, podcastId);
		List<Episode> syncedEpisodes = episodeRepository.findEpisodesByPodcastId(podcastId);
		return new ResponseEntity<>(syncedEpisodes, HttpStatus.OK);
	}
}

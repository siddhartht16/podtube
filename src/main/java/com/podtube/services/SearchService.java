package com.podtube.services;

import com.podtube.common.UserRole;
import com.podtube.datasourceapi.APIUtils;
import com.podtube.gpodderentities.GPodderPodcast;
import com.podtube.models.Category;
import com.podtube.models.Podcast;
import com.podtube.models.Subscription;
import com.podtube.models.User;
import com.podtube.repositories.PodcastRepository;
import com.podtube.repositories.SubscriptionRepository;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class SearchService {

    @Autowired
    PodcastRepository podcastRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    private APIUtils apiUtils = new APIUtils();

    private List<Podcast> getSearchResultsFromGPodder(String searchTerm, int userId) {

        //Get search results from GPodder
        List<GPodderPodcast> searchedPodcasts = apiUtils.searchGPodder(searchTerm);

        List<Podcast> podcastListToReturn = new ArrayList<>();

        //Create new podcasts if not already present
        for (GPodderPodcast gPodderPodcast : searchedPodcasts) {

            //Considering url as unique criteria as of now for podcasts
            // skip if url is empty
            String url = gPodderPodcast.getUrl();
            if ("".equals(url))
                continue;

            Podcast podcastForUrl = null;

            try {
                podcastForUrl = podcastRepository.findByUrlEquals(url.toLowerCase());
            } catch (NullPointerException ex) {
                //TODO: Log this
            }

            Podcast podcast;
            if (podcastForUrl == null) {

                //Add to database
                podcast = new Podcast();

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

                //No categories to set
//                podcast.addCategory(category);
                podcastRepository.save(podcast);

                //Set subscription status
                podcast.setSubscribed(false);
            }//if..
            else {
                //Podcast already exists
                podcast = podcastForUrl;

                //Get subscription information if user session exists
                if (userId != -1) {

                    Subscription userSubscription = null;
                    try {
                        userSubscription = subscriptionRepository.
                                findSubscriptionByPodcastIdAndUserId(podcast.getId(), userId);
                    } catch (NullPointerException nex) {
                    }

                    if (userSubscription != null) {

                        podcast.setSubscribed(true);
                    }//if..
                }//if..
            }

            //Add to result
            podcastListToReturn.add(podcast);
        }//for..

        return podcastListToReturn;
    }//getSearchResultsFromGPodder..

    @GetMapping("/api/search/{searchTerm}")
    public ResponseEntity<List<Podcast>> getSearchResults(HttpSession httpSession,
                                                          @PathVariable("searchTerm") String searchTerm) {

        int userId = -1;

        if (ServiceUtils.isValidSession(httpSession))
            userId = (int) httpSession.getAttribute("id");

        return new ResponseEntity<>(getSearchResultsFromGPodder(searchTerm, userId), HttpStatus.OK);
    }//findAllUsers..
}//AdminService..

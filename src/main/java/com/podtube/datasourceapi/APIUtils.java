package com.podtube.datasourceapi;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.podtube.feedentities.RSSFeedItem;
import com.podtube.feedentities.RSSFeedItemImpl;
import com.podtube.gpodderentities.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIUtils {

    private int NUMBER_OF_RESPONSES = 1000;

    private JSONArray makeRequest(String url) {
        JSONArray result = null;

        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url)
                    .header("accept", "application/json")
                    .asJson();

            result = jsonResponse.getBody().getArray();
        } catch (UnirestException e) {
            //TODO: Log exception and handle properly
            e.printStackTrace();
        }

        return result;
    }//makeRequest..

    private JSONObject makeRequestForSingleJsonNodeResponse(String url) {
        JSONObject result = null;
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
            result = jsonResponse.getBody().getObject();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getKeyValue(Object jsonNode, String key){

        String result = "";
        try {
            result = ((JSONObject) jsonNode).get(key).toString();
        }catch (JSONException ignored){
            //TODO: Log this
        }
        return result;
    }//getKeyValue..

    private Object getKeyObjectValue(Object jsonNode, String key){

        Object result = null;
        try {
            result = ((JSONObject) jsonNode).get(key);
        }catch (JSONException ignored){
            //TODO: Log this
        }
        return result;
    }//getKeyValue..

    public List<GPodderCategory> getCategories() {

        String tagsUrl = Urls.get_gpodder_tags_url(NUMBER_OF_RESPONSES);
        List<GPodderCategory> gPodderCategories = new ArrayList<>();

        JSONArray tagsResponse = makeRequest(tagsUrl);

        //In case some exception
        //TODO: Log exception and handle properly
        if (tagsResponse == null) {
            return gPodderCategories;
        }

        for (Object jsonNode : tagsResponse) {

            String tag = getKeyValue(jsonNode, APIConstants.GPODDER_TAGS_RESPONSE_KEY_TAG);
            String title = getKeyValue(jsonNode, APIConstants.GPODDER_TAGS_RESPONSE_KEY_TITLE);
            String tagUsage = getKeyValue(jsonNode, APIConstants.GPODDER_TAGS_RESPONSE_KEY_USAGE);

            GPodderCategory gPodderCategory = new GPodderCategoryImpl(title,
                    tag,
                    Integer.valueOf(tagUsage));
            gPodderCategories.add(gPodderCategory);
        }//for..

//        System.out.println("Done");
        return gPodderCategories;
    }//getCategories..

    public List<GPodderPodcast> getPodcastsForTag(String tagName) {

        String tagPodcastsUrl = Urls.get_gpodder_tag_podcasts_url(tagName, NUMBER_OF_RESPONSES);
        List<GPodderPodcast> gPodderTagPodcasts = new ArrayList<>();

        JSONArray tagPodcastsResponse = makeRequest(tagPodcastsUrl);

        //In case some exception
        //TODO: Log exception and handle properly
        if (tagPodcastsResponse == null) {
            return gPodderTagPodcasts;
        }//if..

        for (Object jsonNode : tagPodcastsResponse) {

            String url = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_URL);
            String title = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_TITLE);
            String description = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_DESCRIPTION);
            String subscribers = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS);
            String subscribers_last_week = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS_LAST_WEEK);
            String logo_url = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_LOGO_URL);
            String scaled_logo_url = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SCALED_LOGO_URL);
            String website = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_WEBSITE);
            String mygpo_link = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_MYGPO_LINK);

            GPodderPodcast gPodderPodcast = new GPodderPodcastImpl(
                    url,
                    title,
                    description,
                    Integer.valueOf(subscribers),
                    Integer.valueOf(subscribers_last_week),
                    logo_url,
                    scaled_logo_url,
                    website,
                    mygpo_link);

            gPodderTagPodcasts.add(gPodderPodcast);
        }//for..

        System.out.println("Done");
        return gPodderTagPodcasts;
    }//getPodcastsForTag..

    public List<RSSFeedItem> getEpisodesForPodcast(String feedUrl) {

        String rsstojsonUrl = Urls.get_rsstojson_url(feedUrl, NUMBER_OF_RESPONSES);
        List<RSSFeedItem> rssFeedItemsList = new ArrayList<>();

        JSONArray rssFeedItemsResponse = makeRequest(rsstojsonUrl);

        //In case some exception
        //TODO: Log exception and handle properly
        if (rssFeedItemsResponse == null) {
            return rssFeedItemsList;
        }//if..

        //Get JSONObject for complete response
//        Object response = ((JSONObject)rssFeedItemsResponse.get(0));

        //Read Status, Feed, Items
        Object status = ((JSONObject)rssFeedItemsResponse.get(0)).get(APIConstants.RSSTOJSON_RESPONSE_KEY_STATUS);
        Object feed = ((JSONObject)rssFeedItemsResponse.get(0)).get(APIConstants.RSSTOJSON_RESPONSE_KEY_FEED);
        JSONArray items = (JSONArray) ((JSONObject)rssFeedItemsResponse.get(0)).get(APIConstants.RSSTOJSON_RESPONSE_KEY_ITEMS);

        for (Object jsonNode : items) {

            String title = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_TITLE);
            String pubDate = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_PUBDATE);
            String link = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_LINK);
            String guid = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_GUID);
            String author = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_AUTHOR);
            String thumbnail = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_THUMBNAIL);
            String description = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_DESCRIPTION);
            String content = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_CONTENT);
            String categories = getKeyValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_CATEGORIES);

            //Get enclosure object
            Object enclosure = getKeyObjectValue(jsonNode, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE);

            String enclosure_link = "";
            String enclosure_type = "";
            String enclosure_length = "";
            String enclosure_duration = "";
            String enclosure_thumbnail = "";

            if(enclosure!=null) {
                //Read properties from enclosure object
                enclosure_link = getKeyValue(enclosure, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_LINK);
                enclosure_type = getKeyValue(enclosure, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_TYPE);
                enclosure_length = getKeyValue(enclosure, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_LENGTH);
                enclosure_duration = getKeyValue(enclosure, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_DURATION);
                enclosure_thumbnail = getKeyValue(enclosure, APIConstants.RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_THUMBNAIL);
            }

            //Create RSSFeedItem instance
            RSSFeedItem rssFeedItem = new RSSFeedItemImpl(
                    title,
                    pubDate,
                    link,
                    guid,
                    author,
                    thumbnail,
                    description,
                    content,
                    enclosure_link,
                    enclosure_type,
                    enclosure_length,
                    enclosure_duration,
                    enclosure_thumbnail,
                    categories
            );

            rssFeedItemsList.add(rssFeedItem);
        }//for..

        System.out.println(rssFeedItemsList.size());
        return rssFeedItemsList;
    }//getEpisodesForPodcast..

    public List<GPodderPodcast> searchGPodder(String searchTerm) {

        String searchUrl = Urls.get_gpodder_search_url(searchTerm);
        List<GPodderPodcast> gPodderPodcasts = new ArrayList<>();

        JSONArray searchResponse = makeRequest(searchUrl);

        //In case some exception
        //TODO: Log exception and handle properly
        if (searchResponse == null) {
            return gPodderPodcasts;
        }//if..

        for (Object jsonNode : searchResponse) {

            String url = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_URL);
            String title = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_TITLE);
            String description = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_DESCRIPTION);
            String subscribers = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS);
            String subscribers_last_week = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS_LAST_WEEK);
            String logo_url = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_LOGO_URL);
            String scaled_logo_url = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SCALED_LOGO_URL);
            String website = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_WEBSITE);
            String mygpo_link = getKeyValue(jsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_MYGPO_LINK);

            GPodderPodcast gPodderPodcast = new GPodderPodcastImpl(
                    url,
                    title,
                    description,
                    Integer.valueOf(subscribers),
                    Integer.valueOf(subscribers_last_week),
                    logo_url,
                    scaled_logo_url,
                    website,
                    mygpo_link);

            gPodderPodcasts.add(gPodderPodcast);
        }//for..

        System.out.println("Done");
        return gPodderPodcasts;
    }//searchGPodder..

    public GPodderPodcast getPodCastFromGpodder(String podcastUrl) {
        String podcastRetrievalUrl = Urls.get_gpodder_retrieve_podcast_url(podcastUrl);
        JSONObject podcastJsonNode = makeRequestForSingleJsonNodeResponse(podcastRetrievalUrl);

        String url = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_URL);
        String title = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_TITLE);
        String description = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_DESCRIPTION);
        String subscribers = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS);
        String subscribers_last_week = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS_LAST_WEEK);
        String logo_url = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_LOGO_URL);
        String scaled_logo_url = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SCALED_LOGO_URL);
        String website = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_WEBSITE);
        String mygpo_link = getKeyValue(podcastJsonNode, APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_MYGPO_LINK);

        GPodderPodcast gPodderPodcast = new GPodderPodcastImpl(
                url,
                title,
                description,
                Integer.valueOf(subscribers),
                Integer.valueOf(subscribers_last_week),
                logo_url,
                scaled_logo_url,
                website,
                mygpo_link);
        System.out.println("Done");
        return gPodderPodcast;
    }

}//APIUtils..
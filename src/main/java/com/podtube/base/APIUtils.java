package com.podtube.base;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIUtils {

    private int GPODDER_NUMBER_OF_RESPONSES = 1000;

    //RSS2JSON
    private String RSS2JSON_API_KEY_PARAM = "api_key=";
    private String RSS2JSON_API_KEY = "r7sdexy0c7hjnpk8hte5vy8pvg3t6xudtxrnavgq";

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

    public List<GPodderCategory> getCategories(){

        String tagsUrl = Urls.get_gpodder_tags_url(GPODDER_NUMBER_OF_RESPONSES);
        List<GPodderCategory> gPodderCategories = new ArrayList<>();

        JSONArray tagsResponse = makeRequest(tagsUrl);

        //In case some exception
        //TODO: Log exception and handle properly
        if(tagsResponse==null){
            return gPodderCategories;
        }

        for(Object jsonNode: tagsResponse) {

            String tag = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAGS_RESPONSE_KEY_TAG).toString();
            String title = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAGS_RESPONSE_KEY_TITLE).toString();
            String tagUsage = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAGS_RESPONSE_KEY_USAGE).toString();

            GPodderCategory gPodderCategory = new GPodderCategoryImpl(title,
                    tag,
                    Integer.valueOf(tagUsage));
            gPodderCategories.add(gPodderCategory);
        }//for..

//        System.out.println("Done");
        return gPodderCategories;
    }//getCategories..

    public List<GPodderPodcast> getPodcastsForTag(String tagName){

        String tagPodcastsUrl = Urls.get_gpodder_tag_podcasts_url(tagName, GPODDER_NUMBER_OF_RESPONSES);
        List<GPodderPodcast> gPodderTagPodcasts = new ArrayList<>();

        JSONArray tagPodcastsResponse = makeRequest(tagPodcastsUrl);

        //In case some exception
        //TODO: Log exception and handle properly
        if(tagPodcastsResponse==null){
            return gPodderTagPodcasts;
        }//if..

        for(Object jsonNode: tagPodcastsResponse) {

            String url = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_URL).toString();
            String title = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_TITLE).toString();
            String description = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_DESCRIPTION).toString();
            String subscribers = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS).toString();
            String subscribers_last_week = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS_LAST_WEEK).toString();
            String logo_url = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_LOGO_URL).toString();
            String scaled_logo_url = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_SCALED_LOGO_URL).toString();
            String website = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_WEBSITE).toString();
            String mygpo_link = ((JSONObject) jsonNode).get(APIConstants.GPODDER_TAG_PODCAST_RESPONSE_KEY_MYGPO_LINK).toString();

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
        return gPodderTagPodcasts ;
    }//getPodcastsForTag..
}

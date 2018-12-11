package com.podtube.datasourceapi;

public final class Urls {

    //GPodder Urls
    private static final String GPODDER_BASE_URL = "https://gpodder.net/";
    private static final int GPODDER_API_VERSION = 2;
    private static final String GPODDER_API_ROOT = "api/";
    private static final String GPODDER_RESPONSE_FORMAT_JSON = ".json";
    private static final String GPODDER_TAGS = "/tags/";
    private static final String GPODDER_TAG = "/tag/";
    private static final String GPODDER_SEARCH = "search";
    private static final String GPODDER_SEARCH_QUERY_PARAM = "?q=";
    private static final String GPODDER_DATA = "/data";
    private static final String GPODDER_PODCAST = "/podcast";
    private static final String GPODDER_GET_PODCAST_QUERY_PARAM = "?url=";

    //RSS2JSON Urls
    private static final String RSSTOJSON_BASE_URL = "https://api.rss2json.com/v1/api.json?";
    private static final String RSSTOJSON_API_KEY_PARAM = "api_key=";
    private static final String RSSTOJSON_RSS_URL_PARAM = "rss_url=";
    private static final String RSSTOJSON_ITEMS_COUNT_PARAM = "count=";
    private static final String RSSTOJSON_API_KEY = "r7sdexy0c7hjnpk8hte5vy8pvg3t6xudtxrnavgq";

    public static String get_gpodder_tags_url(int p_number_of_tags) {

        //    https://gpodder.net/api/2/tags/500.json
        String result = GPODDER_BASE_URL +
                        GPODDER_API_ROOT +
                        GPODDER_API_VERSION +
                        GPODDER_TAGS +
                        p_number_of_tags +
                        GPODDER_RESPONSE_FORMAT_JSON;

        return result;
    }//get_gpodder_tags_url..

    public static String get_gpodder_tag_podcasts_url(String p_tag, int p_number_of_podcasts) {

        //    https://gpodder.net/api/2/tag/tech-news/500.json
        String result = GPODDER_BASE_URL +
                GPODDER_API_ROOT +
                GPODDER_API_VERSION +
                GPODDER_TAG +
                p_tag + "/" +
                p_number_of_podcasts +
                GPODDER_RESPONSE_FORMAT_JSON;

        return result;
    }//get_gpodder_tags_url..

    public static String get_gpodder_search_url(String p_search_term) {

        //    https://gpodder.net/api/2/tag/tech-news/500.json
        String result = GPODDER_BASE_URL +
                GPODDER_SEARCH +
                GPODDER_RESPONSE_FORMAT_JSON +
                GPODDER_SEARCH_QUERY_PARAM +
                p_search_term;

        return result;
    }//get_gpodder_tags_url..

    public static String get_rsstojson_url(String p_feedurl, int p_number_of_items) {

//        https://api.rss2json.com/v1/api.json?rss_url=http://www.tested.com/podcast-xml/&api_key=r7sdexy0c7hjnpk8hte5vy8pvg3t6xudtxrnavgq&count=1000
        String result = RSSTOJSON_BASE_URL +
                RSSTOJSON_RSS_URL_PARAM +
                p_feedurl + "&" +
                RSSTOJSON_API_KEY_PARAM +
                RSSTOJSON_API_KEY + "&" +
                RSSTOJSON_ITEMS_COUNT_PARAM +
                p_number_of_items;

        return result;
    }//get_gpodder_tags_url..

    public static String get_gpodder_retrieve_podcast_url(String podcastUrl) {
        String result = GPODDER_BASE_URL +
                GPODDER_API_ROOT +
                GPODDER_API_VERSION +
                GPODDER_DATA +
                GPODDER_PODCAST +
                GPODDER_RESPONSE_FORMAT_JSON +
                GPODDER_GET_PODCAST_QUERY_PARAM +
                podcastUrl;
        return result;
    }
}//Urls..

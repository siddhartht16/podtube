package com.podtube.base;

public final class Urls {

    private static final String GPODDER_BASE_URL = "https://gpodder.net/";
    private static final int GPODDER_API_VERSION = 2;
    private static final String GPODDER_API_ROOT = "api/";
    private static final String GPODDER_RESPONSE_FORMAT_JSON = ".json";
    private static final String GPODDER_TAGS = "/tags/";
    private static final String GPODDER_TAG = "/tag/";

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

}

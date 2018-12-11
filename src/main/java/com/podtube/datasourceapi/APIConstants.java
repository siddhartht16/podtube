package com.podtube.datasourceapi;

public final class APIConstants {

//    {
//        "title": "\"Star Trek\"",
//            "tag": "star-trek",
//            "usage": 2
//    },

    //JSON Keys for Tags Response
    public static final String GPODDER_TAGS_RESPONSE_KEY_USAGE = "usage";
    public static final String GPODDER_TAGS_RESPONSE_KEY_TAG = "tag";
    public static final String GPODDER_TAGS_RESPONSE_KEY_TITLE = "title";

    //    {
//        "url": "http://feeds.feedburner.com/thinkrelevance/podcast",
//            "title": "Audio - Cognitect Blog",
//            "description": "A podcast by Cognitect, Inc. about software and the people that create it. We\nfrequently talk about Clojure, ClojureScript, Datomic, agile software\ndevelopment, distributed systems, functional programming, and lots of other\nwonderfully geeky things.",
//            "subscribers": 23,
//            "subscribers_last_week": 23,
//            "logo_url": "http://static1.squarespace.com/static/5372821be4b0aefc6719057e/t/53c036c1e4b08da38445708a/1405105924029/1500w/cognitect.jpg",
//            "scaled_logo_url": "http://gpodder.net/logo/64/77d/77d170d69e3527765697b6d83c41e088e018c2bd",
//            "website": "http://blog.cognitect.com/cognicast/",
//            "mygpo_link": "http://gpodder.net/podcast/thinkrelevance-the-podcast"
//    },
//
    //JSON Keys for Tag Podcasts Response
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_URL = "url";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_TITLE = "title";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_DESCRIPTION = "description";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS = "subscribers";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_SUBSCRIBERS_LAST_WEEK = "subscribers_last_week";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_LOGO_URL = "logo_url";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_SCALED_LOGO_URL = "scaled_logo_url";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_WEBSITE = "website";
    public static final String GPODDER_TAG_PODCAST_RESPONSE_KEY_MYGPO_LINK = "mygpo_link";

    //RSS2JSON
//     "status": "ok",
//             "feed": {
//        "url": "http://www.tested.com/podcast-xml/",
//                "title": "This is Only a Test",
//                "link": "http://www.tested.com/this-is-only-a-test/",
//                "author": "Will Smith, Norman Chan, Jeremy Williams",
//                "description": "This is the official podcast of Tested.com. Tested brings you the week's technology and science news, with hosts Will Smith, Norman Chan, and Jeremy Williams. There's no jargon here, just solid explanations of the week's news--and plenty of wacky tangents. Make sure you stick around after the outro for fake outtakes!",
//                "image": "http://files.tested.com/static/podcast_logo.jpg"
//    },
//            "items": [
//    {
//        "title": "Episode 477 - An Affront to Science - 11/29/18",
//            "pubDate": "2018-11-30 02:00:00",
//            "link": "http://www.tested.com/tech/858028-episode-477-affront-science-112918/",
//            "guid": "http://www.tested.com/tech/858028-episode-477-affront-science-112918/",
//            "author": "Will Smith, Norman Chan, Jeremy Williams",
//            "thumbnail": "http://files.tested.com/static/podcast_logo.jpg",
//            "description": "Everyone's back from their Thanksgiving travels as we hear about visiting national monuments, sharing VR experiences with family, and reactions to the black friday deals. Plus, our thoughts on the demise of the Chevy Volt, new movie trailers, and CRISPR news.",
//            "content": "Everyone's back from their Thanksgiving travels as we hear about visiting national monuments, sharing VR experiences with family, and reactions to the black friday deals. Plus, our thoughts on the demise of the Chevy Volt, new movie trailers, and CRISPR news.",
//            "enclosure": {
//        "link": "https://media.blubrry.com/thisisonlyatest/d2rormqr1qwzpz.cloudfront.net/podcast/thisisonlyatest_20181129.mp3",
//                "type": "audio/mpeg",
//                "length": 57807790,
//                "duration": 7159,
//                "thumbnail": "http://files.tested.com/static/podcast_logo.jpg",
//                "rating": {
//            "scheme": "urn:itunes",
//                    "value": "no"
//        }
//    },
//        "categories": []
//    },

    //JSON Keys for RSS2JSON Response
    public static final String RSSTOJSON_RESPONSE_KEY_STATUS = "status";
    public static final String RSSTOJSON_RESPONSE_KEY_FEED = "feed";
    public static final String RSSTOJSON_RESPONSE_KEY_ITEMS = "items";

    //Feed object keys
    public static final String RSSTOJSON_FEED_RESPONSE_KEY_URL = "url";
    public static final String RSSTOJSON_FEED_RESPONSE_KEY_TITLE = "title";
    public static final String RSSTOJSON_FEED_RESPONSE_KEY_LINK = "link";
    public static final String RSSTOJSON_FEED_RESPONSE_KEY_AUTHOR = "author";
    public static final String RSSTOJSON_FEED_RESPONSE_KEY_DESCRIPTION = "description";
    public static final String RSSTOJSON_FEED_RESPONSE_KEY_IMAGE = "image";

    //Items object keys
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_TITLE = "title";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_PUBDATE = "pubDate";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_LINK = "link";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_GUID = "guid";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_AUTHOR = "author";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_THUMBNAIL = "thumbnail";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_DESCRIPTION = "description";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_CONTENT = "content";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_CATEGORIES = "categories";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE = "enclosure";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_LINK = "link";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_TYPE = "type";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_LENGTH = "length";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_DURATION = "duration";
    public static final String RSSTOJSON_ITEMS_RESPONSE_KEY_ENCLOSURE_THUMBNAIL = "thumbnail";
}//APIConstants..

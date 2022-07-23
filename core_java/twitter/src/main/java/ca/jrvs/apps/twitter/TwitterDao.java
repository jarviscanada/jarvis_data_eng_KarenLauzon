package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements CrdDao<Tweet, String> {

    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    private static final int HTTP_OK = 200;

    private final HttpHelper httpHelper;

    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }


    private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
        Tweet tweet = null;

        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode) {
            try {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entry");
            }
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }
        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert entity to Sting", e);
        }

        try {
            tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert JSON str to Object", e);
        }
        return tweet;
    }


    @Override
    public Tweet create(Tweet tweet) {

        URI uri;
        try {
            uri = getPostUri(tweet);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid tweet input", e);
        }

        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    @Override
    public Tweet findById(String s) {
        URI uri;
        try {
            uri = getShowUri(s);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid id", e);
        }

        HttpResponse response = httpHelper.httpGet(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    @Override
    public Tweet deleteById(String s) {
        URI uri;
        try {
            uri = getDeleteUri(s);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid id", e);
        }

        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    private URI getPostUri(Tweet tweet) throws URISyntaxException {
        return new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + tweet.getText() + AMPERSAND + "lat" + EQUAL + tweet.getCoordinates().getCoordinates().get(1) + AMPERSAND + "lon" + EQUAL + tweet.getCoordinates().getCoordinates().get(0));
    }

    private URI getShowUri(String id) throws URISyntaxException {
        return new URI(API_BASE_URI +SHOW_PATH+QUERY_SYM+"id"+EQUAL+id);
    }

    private URI getDeleteUri(String id) throws URISyntaxException {
        return new URI(API_BASE_URI +DELETE_PATH+QUERY_SYM+"id"+EQUAL+id);
    }
}

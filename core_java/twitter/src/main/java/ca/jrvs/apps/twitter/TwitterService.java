package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;

import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service {

    private final CrdDao dao;

    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    private static void validatePostTweet(Tweet tweet) {

        ArrayList<Double> coords = tweet.getCoordinates().getCoordinates();
        Double lat = coords.get(1);
        Double lon = coords.get(0);
        if (tweet.getText().length() > 140) {
            throw new IllegalArgumentException("Invalid tweet. text must not exceed 140 characters");
        }
        if (lat.toString().split("\\.")[1].length() > 8 || lat < -90 || lat > 90) {
            throw new IllegalArgumentException("Invalid Latitude");
        }
        if (lon.toString().split("\\.")[1].length() > 8 || lat < -180 || lat > 180) {
            throw new IllegalArgumentException("Invalid Longitude");
        }
    }

    private static void validateId(String id) {

        if (!id.matches("^\\d+$")) {
            throw new IllegalArgumentException("Invalid id input: " + id);
        }
    }

    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePostTweet(tweet);
        return (Tweet) dao.create(tweet);
    }

    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateId(id);
        return (Tweet) dao.findById(id);
    }

    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (String id : ids) {
            validateId(id);
            tweets.add((Tweet) dao.deleteById(id));
        }
        return tweets;
    }

}

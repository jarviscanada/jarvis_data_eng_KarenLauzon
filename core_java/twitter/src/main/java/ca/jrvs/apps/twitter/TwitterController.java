package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.controller.Controller;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private final Service service;

    public TwitterController(Service service) {
        this.service = service;
    }

    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        String tweet_text = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2 || StringUtils.isEmpty(tweet_text)) {
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }
        Double lat = null;
        Double lon = null;
        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"", e);
        }

        Tweet postTweet = buildTweet(tweet_text, lon, lat);
        return service.postTweet(postTweet);
    }

    @Override
    public Tweet showTweet(String[] args) {
        String id = args[0];
        String[] fields = new String[args.length - 1];
        //noinspection ManualArrayCopy
        for (int index = 1; index <= args.length; index++) {
            fields[index - 1] = args[index];
        }
        if (id == null) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp show tweet_id [field1,fields2]");
        }
        return service.showTweet(id, fields);
    }

    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp delete [id1,id2,..] ");
        }
        String[] ids = args[0].split(",");

        return service.deleteTweets(ids);
    }

    private Tweet buildTweet(String tweet_text, Double lon, Double lat) {
        Tweet tweet = new Tweet();

        ArrayList<Double> coord = new ArrayList<>();
        coord.add(lon);
        coord.add(lat);

        Coordinates coords = new Coordinates();
        coords.setCoordinates(coord);

        tweet.setCoordinates(coords);
        tweet.setText(tweet_text);
        return tweet;
    }
}

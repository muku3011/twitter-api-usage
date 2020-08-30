package com.twitterapi;


import twitter4j.*;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    private static Twitter getTwitterInstance() {
        //return getInstanceUsingBuilder();
        return TwitterFactory.getSingleton();
    }

    static Status createTweet(String tweet) throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return twitter.updateStatus(tweet);
    }

    static void destroyTweet(long statusId) throws TwitterException {
        Twitter twitter = getTwitterInstance();
        twitter.destroyStatus(statusId);
    }

    static List<String> getTimeLine() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        List<Status> statuses = twitter.getHomeTimeline();
        return statuses.stream().map(
                Status::getText).collect(
                Collectors.toList());
    }

    static DirectMessage sendDirectMessage(String recipientName, String msg) throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return  twitter.sendDirectMessage(recipientName, msg);
    }

    static void destroyDirectMessage(long id) throws TwitterException {
        Twitter twitter = getTwitterInstance();
        twitter.destroyDirectMessage(id);
    }

    static List<DirectMessage> getDirectMessages() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        return twitter.getDirectMessages(100);
    }

    public static List<String> searchtweets() throws TwitterException {
        Twitter twitter = getTwitterInstance();
        Query query = new Query("source:LearnByExample");
        QueryResult result = twitter.search(query);
        List<Status> statuses = result.getTweets();
        return statuses.stream().map(
                Status::getText).collect(
                Collectors.toList());
    }

    public static void streamFeed() {

        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg) {
                System.out.println("Got a status deletion notice id:" + arg.getStatusId());
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onStatus(Status status) {
                System.out.println(status.getUser().getName() + " : " + status.getText());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }
        };

        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

        twitterStream.addListener(listener);

        twitterStream.sample();

    }

/*    private static Twitter getInstanceUsingBuilder() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("//TODO")
                .setOAuthConsumerSecret("//TODO")
                .setOAuthAccessToken("//TODO")
                .setOAuthAccessTokenSecret("//TODO");
        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        return twitterFactory.getSingleton();
    }*/

}

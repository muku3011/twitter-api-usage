package com.twitterapi;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.TwitterException;

import static com.twitterapi.Application.destroyDirectMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ApplicationTest {

    /**
     * In order run this jUnit test you need to configure your API details in the twitter4j.properties
     */

    //@Test
    public void givenCredential_fetchStatus() throws TwitterException {
        String tweet = "Tweet using API";

        Status status = Application.createTweet(tweet);
        assertEquals(tweet, status.getText());

        //Assert last tweet in the timeline
        assertEquals(tweet, Application.getTimeLine().get(0));

        //Assert last tweet is not the one post by the test
        Application.destroyTweet(status.getId());
        assertNotEquals(tweet, Application.getTimeLine().get(0));
    }

    //@Test
    public void givenRecipientNameAndMessage_sendDirectMessage() throws TwitterException, InterruptedException {
        String receiptentName = "Kusum_Negi_";
        String directMessage = "Message using Twitter API";

        DirectMessage msg = Application.sendDirectMessage(receiptentName, directMessage);
        assertEquals(directMessage, msg.getText());

        Thread.sleep(2000);
        assertEquals(directMessage, Application.getDirectMessages().get(0).getText());

        destroyDirectMessage(msg.getId());
        Thread.sleep(2000);
        assertNotEquals(directMessage, Application.getDirectMessages().get(0).getText());

    }

}

package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import java.net.URI;
import org.apache.http.HttpResponse;

public class TwitterHttpHelperTest {
  private TwitterHttpHelper httpHelper;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("CONSUMER_KEY");
    String consumerSecret = System.getenv("CONSUMER_SECRET");
    String accessToken = System.getenv("ACCESS_TOKEN");
    String tokenSecret = System.getenv("TOKEN_SECRET");

    System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
    httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
  }
  @Test
  public void httpPost() throws URISyntaxException, IOException {
    HttpResponse response = httpHelper.httpPost(
        new URI("https://api.twitter.com/1.1/statuses/update.json/status=first_tweet2"));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }

  @Test
  public void httpGet() throws IOException, URISyntaxException {
    HttpResponse response = httpHelper.httpGet(
        new URI("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=naleywater"));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
}
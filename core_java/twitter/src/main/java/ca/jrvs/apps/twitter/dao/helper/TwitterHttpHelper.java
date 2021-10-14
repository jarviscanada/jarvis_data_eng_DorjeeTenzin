package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.HttpResponse;
import java.net.URI;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class TwitterHttpHelper implements HttpHelper {
  private OAuthConsumer consumer;
  private HttpClient httpClient;

  /**
   * Constructor
   * Setup dependencies using secrets
   * @param consumerKey
   * @param consumerSecret
   * @param accessToken
   * @param tokenSecret
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
    consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);
    httpClient = HttpClientBuilder.create().build();
  }

  @Override
  public HttpResponse httpPost(URI uri) {
    return;
  }

  @Override
  public HttpResponse httpGet(URI uri) {
    return;
  }

}

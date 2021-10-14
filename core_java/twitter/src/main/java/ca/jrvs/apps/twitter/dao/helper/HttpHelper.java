package ca.jrvs.apps.twitter.dao.helper;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;

public interface HttpHelper {

  /**
   * Execute a Http Post request
   * @param uri
   * @return
   */
  HttpResponse httpPost(URI uri);

  /**
   * Execute a Http Get request
   * @param uri
   * @return
   */
  HttpResponse httpGet(URI uri);
}

package nl.jorncruijsen.guildwars.service;

import java.io.InputStream;
import java.util.List;

import nl.jorncruijsen.guildwars.dev.WebClientDevWrapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class URLRetriever {
  public static InputStream getContent(String urlTxt) {
    return getContent(urlTxt, null);
  }

  public static InputStream getContent(String urlTxt, List<NameValuePair> urlParameters) {
    HttpClient httpClient = new DefaultHttpClient();
    WebClientDevWrapper.wrapClient(httpClient);

    try {
      HttpGet httpGet = new HttpGet(urlTxt);

      // Execute and get the response.
      HttpResponse response;
      response = httpClient.execute(httpGet);
      HttpEntity entity = response.getEntity();

      if (entity != null) {
        return entity.getContent();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}

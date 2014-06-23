package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.AbstractHttpMessage;

public class ApacheWebRequest implements IGetWebRequest, IPostWebRequest {

  private String m_scheme;
  private String m_host;  

  public ApacheWebRequest(String scheme, String host) {
    m_scheme = scheme;
    m_host = host;
  }
  
  @Override
  public String makeRequest(String path, Map<String, String> headers) {
    throw new UnsupportedOperationException("Use overloaded methods for POST/GET.");
  }

  @Override
  public String makeRequest(String path, Map<String, String> headers, String data, Map<String, String> params) {
    URIBuilder uriBuilder = new URIBuilder();
    uriBuilder.setScheme(m_scheme);
    uriBuilder.setHost(m_host);
    uriBuilder.setPath(path);
    
    setParams(uriBuilder, params);
    
    URI uri = null;
    
    try {
      uri = uriBuilder.build();
    }
    catch (URISyntaxException e) {
      return "";
    }
    System.out.println(uri.toString());
    HttpPost post = new HttpPost();
    post.setURI(uri);
    setHeaders(post, headers);
    
    try {
      post.setEntity(new StringEntity(data));
    } catch (UnsupportedEncodingException e1) {
      return "";
    }
    
    return executeRequest(post);
  }

  @Override
  public String makeRequest(String path, Map<String, String> headers,
      Map<String, String> data) {
    URIBuilder uriBuilder = new URIBuilder();
    uriBuilder.setScheme(m_scheme);
    uriBuilder.setHost(m_host);
    uriBuilder.setPath(path);
    
    setParams(uriBuilder, data);
    
    URI uri = null;
    try {
      uri = uriBuilder.build();
    } catch(URISyntaxException e) {
      return "";
    }
    
    HttpGet get = new HttpGet();
    get.setURI(uri);
    setHeaders(get, headers);
    
    return executeRequest(get);
  }
  
  private void setHeaders(AbstractHttpMessage method, Map<String, String> headers) {
    if(headers != null && !headers.isEmpty()) {
      Iterator<Entry<String, String>> it = headers.entrySet().iterator();
      while(it.hasNext()) {
        Entry<String, String> entry = it.next();
        method.addHeader(entry.getKey(), entry.getValue());
      }
    }
  }
  
  private void setParams(URIBuilder builder, Map<String, String> params) {
    if(params != null && !params.isEmpty()) {
      Iterator<Entry<String, String>> it = params.entrySet().iterator();
      while(it.hasNext()) {
        Entry<String, String> entry = it.next();
        builder.addParameter(entry.getKey(), entry.getValue());
      }
    }
  }
  
  private String executeRequest(HttpUriRequest method) {
    HttpClient client = HttpClientBuilder.create().build();
    HttpResponse response = null;
    
    try {
      response = client.execute(method);
    } catch (ClientProtocolException e) {
      return "";
    } catch (IOException e) {
      return "";
    }
    
    StringBuilder stringBuilder = new StringBuilder();
    
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
      try {
        String line = null;
        while((line = reader.readLine()) != null) {
          stringBuilder.append(line);
        }
      } catch (IOException e) {
        return "";
      }
    } catch (IllegalStateException e) {
      return "";
    } catch (IOException e) {
      return "";
    }
    
    return stringBuilder.toString();
  }
}

package main;

import java.util.Map;

public interface IWebRequest {
  String makeRequest(String uri, Map<String, String> headers);
}

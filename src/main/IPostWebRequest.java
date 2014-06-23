package main;

import java.util.Map;

public interface IPostWebRequest extends IWebRequest {
  String makeRequest(String uri, Map<String, String> headers, String data, Map<String, String> params);
}

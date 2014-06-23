package main;

import java.util.Map;

public interface IGetWebRequest extends IWebRequest {
  String makeRequest(String uri, Map<String, String> headers, Map<String, String> data);
}

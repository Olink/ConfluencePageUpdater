package main;

import java.util.HashMap;
import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;

public class JsonRpcRequest {
  private String m_host;
  private String m_scheme;
  private String m_encodedAuth;
  
  private final static String BASEPATH = "/rpc/json-rpc/confluenceservice-v2";
  private final static String BASICAUTH = "?os_authType=basic";
  
  public JsonRpcRequest(String host, String scheme, String username, String password) {
    m_host = host;
    m_scheme = scheme;
    m_encodedAuth = getAuth(username, password);
  }
  
  private String getAuth(String username, String password) {
    String authString = username + ":" + password;
    byte[] encoded = Base64.encodeBase64(authString.getBytes());
    return new String(encoded);
  }
  
  public JsonObject postRequest(JsonObject data) {
    IPostWebRequest request = new ApacheWebRequest(m_scheme, m_host);
    
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Authorization", "Basic " + m_encodedAuth);
    headers.put("Content-Type", "application/json");

    Gson gson = new Gson();
    String json = gson.toJson(data);
    System.out.println(json);
    HashMap<String, String> map = new HashMap<String, String>();
    map.put("os_authType", "basic");
    String response = request.makeRequest(BASEPATH, headers, json, map);
    
    JsonObject object = gson.fromJson(response, JsonObject.class);
    
    return object;
  }
}

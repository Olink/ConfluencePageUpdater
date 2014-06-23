package main;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ConfluenceJsonPageManager implements ConfluencePageManager {
  
  private JsonRpcRequest jsonRpcRequest;
  
  public ConfluenceJsonPageManager(String host, String scheme, String username, String password) {
    jsonRpcRequest = new JsonRpcRequest(host, scheme, username, password);
  }
  
  @Override
  public ConfluencePage getPage(long id) {
    ConfluencePage p = new ConfluencePage();
    
    JsonObject obj = jsonRpcRequest.postRequest(createGetPage(id));
    
    if(!obj.has("result")) {
      return null;
    }
    
    p.FromJson(obj.get("result").getAsJsonObject());
    
    return p;
  }
  
  private JsonObject createGetPage(long id) {
    JsonArray params = new JsonArray();
    params.add(new JsonPrimitive(id));
    
    JsonObject data = new JsonObject();
    data.addProperty("jsonrpc", "2.0");
    data.addProperty("method", "getPage");
    data.add("params", params);
    data.addProperty("id", "1");
    
    return data;
  }
  
  @Override
  public String updatePage(ConfluencePage p, String comment, boolean minor) {
    return jsonRpcRequest.postRequest(createUpdatePage(p, comment, minor)).toString();
  }
  
  private JsonObject createUpdatePage(ConfluencePage p, String comment, boolean minor) {
    JsonObject pageDetails = p.toJson();
    
    JsonObject editDetails = new JsonObject();
    editDetails.addProperty("versionComment", comment);
    editDetails.addProperty("minorEdit", minor);
    
    JsonArray params = new JsonArray();
    params.add(pageDetails);
    params.add(editDetails);
    
    JsonObject data = new JsonObject();
    data.addProperty("jsonrpc", "2.0");
    data.addProperty("method", "updatePage");
    data.add("params", params);
    data.addProperty("id", "2");
    return data;
  }
}

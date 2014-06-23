package main;

import nu.xom.Element;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ConfluencePage {
  public long Id;
  public String Space;
  public String Title;
  public long ParentId;
  public String Content;
  public long Version;
  
  public JsonObject toJson() {
    JsonObject pageDetails = new JsonObject();
    pageDetails.add("id", new JsonPrimitive(Id));
    pageDetails.add("space", new JsonPrimitive(Space));
    pageDetails.add("title", new JsonPrimitive(Title));
    pageDetails.add("parentId", new JsonPrimitive(ParentId));
    pageDetails.add("content", new JsonPrimitive(Content));
    pageDetails.add("version", new JsonPrimitive(Version));
    return pageDetails;
  }
  
  public void FromJson(JsonObject json) {
    Id = json.get("id").getAsLong();
    Space = json.get("space").getAsString();
    Title = json.get("title").getAsString();
    ParentId = json.get("parentId").getAsLong();
    Content = json.get("content").getAsString();
    Version = json.get("version").getAsLong();
  }
  
  /*public String ToXml() {
    Element root = new Element();
  }
  
  public void FromXml(Element xom) {
    
  }*/
}

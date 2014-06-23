package main;

public interface ConfluencePageManager {
  public String updatePage(ConfluencePage p, String comment, boolean minor);
  public ConfluencePage getPage(long id);
}

package test;

import static org.junit.Assert.*;

import java.util.Map;

import main.ApacheWebRequest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ApacheWebRequestTest {
  private ApacheWebRequest m_uut;
  
  @Before
  public void setUp()
  {
    m_uut = new ApacheWebRequest("http", "www.google.com");
  }
  
  @Test(expected=UnsupportedOperationException.class)
  public void testMakeBadRequest() {
    m_uut.makeRequest("garbage", null);
  }
  
  @Test
  public void testMakePostWithInvalidPath() {
    String ret = m_uut.makeRequest("garbage://garbage", null, "", null);
    assertEquals("Invalid path return a non-empty string", "", ret);
  }
  
  @Test
  public void testMakePostWithInvalidScheme() {
    m_uut = new ApacheWebRequest("htt", "www.google.com");
    String ret = m_uut.makeRequest("/search", null, "", null);
    assertEquals("Invalid Scheme returned a non-empty string", "", ret);
  }
  
  @Test
  public void testMakePostWithInvalidHost() {
    m_uut = new ApacheWebRequest("http", "h:t:t:p");
    String ret = m_uut.makeRequest("/", null, "", null);
    assertEquals("Invalid Host returned a non-empty string", "", ret);
  }
  
  @Test
  public void testMakePost() {
    m_uut = new ApacheWebRequest("http", "www.google.com");
    String ret = m_uut.makeRequest("/search", null, "", null);
    assertNotSame("Valid request returned empty string", "", ret);
  }
  
  @Test
  public void testMakeGetWithInvalidPath() {
    Map<String, String> map = null;
    String ret = m_uut.makeRequest("garbage://garbage", map, map);
    assertEquals("Invalid path returned a non-empty string", "", ret);
  }
  
  @Test
  public void testMakeGetWithInvalidHost() {
    Map<String, String> map = null;
    m_uut = new ApacheWebRequest("http", "a:b:c:d");
    String ret = m_uut.makeRequest("/", map, map);
    assertEquals("Invalid Host returned a non-empty string", "", ret);
  }
  
  @Test
  public void testMakeGetWithInvalidScheme() {
    Map<String, String> map = null;
    m_uut = new ApacheWebRequest("htp", "www.google.com");
    String ret = m_uut.makeRequest("/", map, map);
    assertEquals("Invalid Scheme returned a non-empty string", "", ret);
  }
  
  @Test
  public void testMakeGet() {
    Map<String, String> map = null;
    String ret = m_uut.makeRequest("/", map, map);
    assertNotSame("Valid request returned an empty string", "", ret);
  }
}

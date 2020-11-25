package nesabyte_bapples.test;

import org.junit.jupiter.api.Test;

import static nesabyte_bapples.Handlers.LinkCode;
import static org.junit.jupiter.api.Assertions.*;

class HandlersTest {

  // testing good link with code of 200
  @Test
  void good_linkCode() throws Exception {
    String good_url = "http://google.com";

    int good_code = LinkCode(good_url);
    assertEquals(good_code, 200);
  }

  // testing none existing link
  @Test
  void none_linkCode() throws Exception {
    String bad_url = "http://gaaaaaoogle.com";

    int bad_code = LinkCode(bad_url);
    assertEquals(bad_code, 0);
  }

  // testing bad link with code of 404
  @Test
  void bad_linkCode() throws Exception {
    String bad_url = "http://zadkielm.blogspot.com/feeds/posts/default/-/open%20source";

    int bad_code = LinkCode(bad_url);
    assertEquals(bad_code, 404);
  }

  // testing unknown link with code of 301
  @Test
  void unknown_linkCode() throws Exception {
    String bad_url = "http://en.wikipedia.org/wiki/Hackergotchi";

    int bad_code = LinkCode(bad_url);
    assertEquals(bad_code, 301);
  }
}

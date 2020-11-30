package nesabyte_bapples;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalHostHandler {

  /***
   * this method takes in the localhost link, then save all its contents into a string.
   * The string then is processed by the use of the pullLink() method to take all of the links and be placed inside a HashSet.
   * Then the Results(int, alink, gcounter, bcounter, ucounter) method is called to display the result of this method
   * @param m_url
   */
  public static void classifyingLH(String m_url) {

    try {
      String content = UrlHandler.UrlReader(m_url);

      HashSet<String> aLink = pullPosts(content);

      System.out.println("\nTotal Apple count: " + aLink.size());

      // counters for good, bad, unknown links
      int Gcounter = 0, Bcounter = 0, Ucounter = 0;

      Handlers.Results(999, aLink, Gcounter, Bcounter, Ucounter);
    } catch (Exception e) {
      System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n" + m_url);
    }
  }

  /***
   * this method accepts a string, it finds all the /posts/* from the string.
   * It recognizes the posts id and store it into the links
   * then returns the LINKS
   *      *
   * @param text
   * @return
   */
  public static HashSet<String> pullPosts(String text) {
    HashSet<String> links = new HashSet<String>();

    String regex =
        "/posts/[-A-Za-z0-9]*[-A-Za-z0-9]"; // "(?:(?:https?|ftp)://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
    Pattern p = Pattern.compile(regex);
    Matcher m = p.matcher(text);
    while (m.find()) {
      String urlStr = m.group();
      if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
        urlStr = urlStr.substring(1, urlStr.length() - 1);
      }
      // if a link is found, save it in the hashset
      urlStr = "http://localhost:3000" + urlStr;
      links.add(urlStr);
    }
    return links;
  }
}

package nesabyte_bapples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;

public class UrlHandler {
  /**
   * This method takes in the a url, connects to the url, and store all its' html content in a
   * string. then the long string will be validated to take all the links This method reads from the
   * url then store all its html resource contents in a string. The string then is processed by the
   * use of the pullLinks() method to take all of the links and be placed inside a HashSet. Then
   * send the Hashset in FindUnsecured() to find all the links with http and turn them into https
   * and check their status
   *
   * @param url
   */
  public static void checkSecureURL(HashSet<String> url) {
    for (String myUrl : url) {
      try {
        String content = UrlReader(myUrl);

        HashSet<String> aLink = Handlers.pullLinks(content);

        Handlers.findUnsecured(aLink);

      } catch (Exception e) {
        System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n" + url);
      }
    }
  }

  /**
   * this method takes in 2 parameters: 1.) a HashSet that contains url to be checked. 2.) an in for
   * the statcode, if the user wants to search for a specific status code.
   *
   * <p>this method reads from the url then store all its html resource contents in a string. The
   * string then is processed by the use of the pullLinks() method to take all of the links and be
   * placed inside a HashSet. Then each of the link will be passed into the AppleCode() method to
   * check all the Status code. then it prints out the link with its status code.
   *
   * <p>Each status code has their own color: = RED = Bad Apples with status code 404 or 400 = GREEN
   * = Good Apples with status code 200 = YELLOW = Unknown Apples with other status code
   *
   * @param file
   * @param statcode
   */
  public static void classifyingURL(HashSet<String> m_url, int statcode) {
    // if user puts more urls to check
    for (String multi_link : m_url) {
      System.out.println("\n[ Counting Apples at " + multi_link + " ]");

      try {
        String content = UrlReader(multi_link);

        // string then is processed by the use of the pullLinks() method to take all of the links
        // and be placed inside a HashSet.
        HashSet<String> aLink = Handlers.pullLinks(content);
        System.out.println("\nTotal Apple count: " + aLink.size());

        // counters for good, bad, unknown links
        int Gcounter = 0, Bcounter = 0, Ucounter = 0;

        Handlers.Results(statcode, aLink, Gcounter, Bcounter, Ucounter);

        Handlers.bappleResults(Gcounter, Bcounter, Ucounter);
      } catch (Exception e) {
        System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n" + multi_link.toString());
      }
    }
  }

  public static String UrlReader(String multi_link) throws IOException {
    // connecting to myUrl
    URL url = new URL(multi_link);
    URLConnection myURLConnection = url.openConnection();
    myURLConnection.connect();

    // read the html from the url
    BufferedReader in =
        new BufferedReader(new InputStreamReader(myURLConnection.getInputStream(), "UTF-8"));
    String inputLine;
    StringBuilder a = new StringBuilder();

    // the contents of the html is now in one String
    while ((inputLine = in.readLine()) != null) a.append(inputLine);
    in.close();

    return a.toString();
  }
}

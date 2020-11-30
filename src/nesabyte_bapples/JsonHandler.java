package nesabyte_bapples;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonHandler {

  /**
   * this function turns the list of URLS into json. we feed the url its status code in Urljson
   * class with String url and int status code, then outputs using Gson gson = new
   * GsonBuilder().setPrettyPrinting().create(); to make the display pretty.
   *
   * @param file
   */
  public static void tobeJSON(String file) {
    try {
      String content = Handlers.HtmlReader(file);

      HashSet<String> aLink = Handlers.pullLinks(content);

      for (String link : aLink) {
        String regex = "(http://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(link);

        while (m.find()) {
          int stat = 0;
          String urlStr = m.group();

          if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
            urlStr = urlStr.substring(1, urlStr.length() - 1);
          }

          stat = Handlers.LinkCode(urlStr);
          Gson gson = new GsonBuilder().setPrettyPrinting().create();
          Urljson url = new Urljson(urlStr, stat);

          String json = gson.toJson(url);
          System.out.println(json);
        }
      }
    } catch (Exception e) {
      System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "[" + file + "]");
    }
  }
}

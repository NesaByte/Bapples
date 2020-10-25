package nesabyte_bapples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlHandler {
    /**
     * this method takes in the html file, reads the file and store it in a string.
     * then the long string will be validated to take all the links
     * this method reads from the url then store all its html resource contents in a string.
     * The string then is processed by the use of the pullLinks() method to take all of the links and be placed inside a HashSet.
     * Then send the Hashset in FindUnsecured() to find all the links with http and turn them into https and check their status
     * @param file
     */
    public static void checkSecureHTML(String file) {
        //if user puts more urls to check
        try {
            String content = Handlers.HtmlReader(file);

            //string then is processed by the use of the pullLinks() method to take all of the links and be placed inside a HashSet.
            HashSet<String> aLink = Handlers.pullLinks(content);

            Handlers.findUnsecured(aLink);

        } catch (Exception e) {
            System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "[" + file + "]");
        }
    }

    /**
     * this method takes in 2 parameters:
     * 1.) a string which is the absolute pathway of the .html file.
     * 2.) an in for the statcode, if the user wants to search for a specific status code.
     * <p>
     * this method reads from the *.html file then store all its contents in a string.
     * The string then is processed by the use of the pullLinks() method to take all of the links and be placed inside a HashSet.
     * Then each of the link will be passed into the AppleCode() method to check all the Status code.
     * then it prints out the link with its status code.
     * <p>
     * Each status code has their own color:
     * = RED      = Bad Apples with status code 404 or 400
     * = GREEN    = Good Apples with status code 200
     * = YELLOW     = Unknown Apples with other status code
     *
     * @param file
     * @param statcode
     */
    public static void classifyingHTML(String file, int statcode) {
        //if user puts more urls to check
        try {
            String content = Handlers.HtmlReader(file);

            //string then is processed by the use of the pullLinks() method to take all of the links and be placed inside a HashSet.
            HashSet<String> aLink = Handlers.pullLinks(content);
            System.out.println("\nTotal Apple count: " + aLink.size());

            //counters for good, bad, unknown links
            int Gcounter = 0, Bcounter = 0, Ucounter = 0;

            Handlers.Results(statcode,aLink, Gcounter, Bcounter, Ucounter);

        } catch (Exception e) {
            System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "[" + file + "]");
        }
    }

    /***
     * this method accepts a string, it finds all the *.txt from the string.
     * It recognizes the link .txt the help of the regex.
     * then returns the TXT FILE NAME
     *
     * @author Eunbee Kim
     * @param text
     * @return
     */
    public static String pullTXT(String text) {
        String TXTStr = "";

        //regex to find the txt filename
        String regex = "[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|].txt";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while (m.find()) {
            TXTStr = m.group();

            //return the html file name, if there is.
            return TXTStr;
        }
        //return an emtpy string
        return TXTStr;
    }

    /**
     * this method takes in 2 parameters:
     * It get the list of ignore from txt file, and the request url list are from html file.
     * the request url will get rid of the list of ignore urls.
     *
     * @author Eunbee Kim
     * @param txtFile
     * @param htmlFile
     * @param statcode
     */
    public static void classifyingTXT (String txtFile, String htmlFile, int statcode) {
        HashSet<String> aLink = null;
        HashSet<String> bLink = null;
        //if user puts more urls to check
        try {
            String contentTXT = Handlers.HtmlReader(txtFile);

            //string then is processed by the use of the pullLinksExcept() method to take all of the links and be placed inside a HashSet.
            aLink = pullLinksExcept(contentTXT);

            String contentHTML = Handlers.HtmlReader(htmlFile);

            //string then is processed by the use of the pullLinks() method to take all of the links and be placed inside a HashSet.
            bLink = Handlers.pullLinks(contentHTML);

            // string list has valid request urls applied ignored url set
            HashSet<String> cLink = ignoreUrl(bLink, aLink);

            System.out.println("\nTotal Apple count: " + cLink.size());

            //counters for good, bad, unknown links
            int Gcounter = 0, Bcounter = 0, Ucounter = 0;

            Handlers.Results(statcode,cLink, Gcounter, Bcounter, Ucounter);

        } catch (Exception e) {
            System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "[" + txtFile + "]");
        }

    }

    /***
     * this method is saving only valid request urls to string set
     *
     * this method accepts a string, it finds invalid urls from html file.
     * then returns the LINKS
     *
     * @author Eunbee Kim
     * @param text
     * @return
     */
    public static HashSet<String> ignoreUrl(HashSet<String> text, HashSet<String> ignore) {
        HashSet<String> links = new HashSet<String>();

        for(String url : text)
        {
            boolean ignorePass = true;

            for(String ign : ignore)
            {
                if(url.contains(ign)|| url.startsWith(ign)) ignorePass = false;
            }
            if(ignorePass) links.add(url);
        }

        return links;
    }

    /***
     * this method accepts a string, it finds all the links from the string.
     * It recognizes the link through the help of the regex.(# except)
     * then returns the LINKS
     *
     * @author Eunbee Kim
     * @param text
     * @return
     */
    public static HashSet<String> pullLinksExcept(String text) {
        HashSet<String> links = new HashSet<String>();

        //regex to find the link
        String regex = "(?:(?:https?|ftp)://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while (m.find()) {
            String urlStr = m.group();
            if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                urlStr = urlStr.substring(1, urlStr.length() - 1);
            }
            //if a link is found, save it in the hashset
            links.add(urlStr);
        }
        return links;
    }
}

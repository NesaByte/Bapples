package nesabyte_bapples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Handlers {
    /**
     * This method finds all the links with http and turn them into https and check their status
     * @param links
     */
    public static void findUnsecured(HashSet<String> links) {
        HashSet<String> unlinks = new HashSet<String>();
        HashSet<String> unlinks_test = new HashSet<String>();
        int Gcounter = 0, Bcounter = 0, Ucounter = 0;

        for (String link : links) {
            String regex = "(http://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(link);
            while (m.find()) {
                String urlStr = m.group();
                if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
                    urlStr = urlStr.substring(1, urlStr.length() - 1);
                }
                //if a link is found, save it in the hashset
                unlinks.add(urlStr);

            }
        }
        StringBuilder str;
        for (String un : unlinks) {
            str = new StringBuilder(un);
            str.insert(4, 's');

            try {
                int code = LinkCode(str.toString());
                if (code == 400 || code == 404) {
                    System.out.println(Cleaner.RED + "[ " + code + " ]   BAD APPLE     : " + str.toString() + Cleaner.RESET);
                    Bcounter++;
                } else if (code == 200) {
                    System.out.println(Cleaner.GREEN + "[ " + code + " ]   GOOD APPLE    : " + str.toString() + Cleaner.RESET);
                    Gcounter++;
                } else if (code == 0) {
                    System.out.println(Cleaner.YELLOW + "[ ??? ]   UNKNOWN APPLE : " + str.toString() + Cleaner.RESET);
                    Ucounter++;
                } else {
                    System.out.println(Cleaner.YELLOW + "[ " + code + " ]   UNKNOWN APPLE : " + str.toString() + Cleaner.RESET);
                    Ucounter++;
                }


            } catch (Exception e) {
                System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n" + str.toString());
            }
        }


        System.out.println("--------------------------------------------------------");
        System.out.println("      Done counting apples!");
        System.out.println("          Good apples:    " + Gcounter);
        System.out.println("          Bad apples:     " + Bcounter);
        System.out.println("          Unknown apples: " + Ucounter);
        System.out.println("--------------------------------------------------------");
    }

    /***
     * this method is inspired from https://www.computing.dcu.ie/~humphrys/Notes/Networks/java.html
     *
     * this method accepts a String containing a URL, and it will open a connection.
     * if connection is granted, it will get the response code of the link,
     * the connection max time is 10secs,
     * then it returns an Integer that holds the status code.
     *
     * @param link
     * @return
     * @throws Exception
     */
    public static int LinkCode(String link) throws Exception {

        HttpURLConnection connection = null;
        int myCode = 0;
        try {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            myCode = connection.getResponseCode();
            connection.setConnectTimeout(1000);
            return myCode;
        } catch (IOException e) {
            return myCode;
        } finally {
            // If the connection is open, disconnect.
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /***
     * this method accepts a string, it finds all the *.html from the string.
     * It recognizes the link .html the help of the regex.
     * then returns the HTML FILE NAME
     *
     * @param text
     * @return
     */
    public static String pullHTML(String text) {
        String HTMLStr = "";

        //regex to find the html filename
        String regex = "[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|].html";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while (m.find()) {
            HTMLStr = m.group();

            //return the html file name, if there is.
            return HTMLStr;
        }
        //return an emtpy string
        return HTMLStr;
    }

    /***
     * this method is inspired from http://blog.houen.net/java-get-url-from-string/
     *
     * this method accepts a string, it finds all the links from the string.
     * It recognizes the link through the help of the regex.
     * then returns the LINKS
     *
     * @param text
     * @return
     */
    public static HashSet<String> pullLinks(String text) {
        HashSet<String> links = new HashSet<String>();

        //regex to find the link
        String regex = "(?:(?:https?|ftp)://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
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

    /***
     * this method accepts a string, it finds all the COMMANDS from the string.
     * It recognizes the link through the help of the regex.
     * then returns the COMMANDS
     *
     * @param text
     * @return
     */
    public static HashSet<String> pullCommands(String text) {

        HashSet<String> commands = new HashSet<String>();
        String regex = "--[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while (m.find()) {
            String urlStr = m.group();
            commands.add(urlStr);
        }

        String regex_num = "--[0-9][0-9][0-9]";
        Pattern paa = Pattern.compile(regex_num);
        Matcher maa = paa.matcher(text);
        while (maa.find()) {
            String urlStrrr = maa.group();
            commands.add(urlStrrr);
        }
        return commands;
    }

    /***
     * This method is called when the user wants help, all commands is listed in here
     */
    public static void bappleHelp() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("- WELCOME TO BAPPLES! Finding bad apples from any LINKS or HTML files           -");
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("-      --v or --version | to check the Bapple version                           -");
        System.out.println("-      --h or --help    | to check the Bapple help                              -");
        System.out.println("-      <filename>       | to validate links within a file                       -");
        System.out.println("-      --200            | to list urls with status code: SUCCESS                -");
        System.out.println("-      --400 or --404   | to list urls with status code: CLIENT ERRORS          -");
        System.out.println("-      --XXX            | to list urls with status code: UNKNOWNS               -");
        System.out.println("-      --secure         | to check URLS with http:// if they work with https:// -");
        System.out.println("-      --all            | to list urls with all status                          -");
        System.out.println("-      --good           | to list urls with good status code: 200               -");
        System.out.println("-      --bad            | to list urls with bad status code: 404 and 400        -");
        System.out.println("-      --i or --ignore  | to list urls except ignore url list                   -");
        System.out.println("---------------------------------------------------------------------------------");
    }

    /***
     * This method is called when the tool is finished counting the bapples
     *
     * @author Royce Ayroso-Ong
     *
     * @param goodCounter number of valid links
     * @param badCounter number of bad links
     * @param unknownCounter number of unknown links
     */
    public static void bappleResults(int goodCounter, int badCounter, int unknownCounter) {
        System.out.println("--------------------------------------------------------");
        System.out.println("          Done counting apples!");
        System.out.println(Cleaner.GREEN + "          Good apples:    " + goodCounter + Cleaner.RESET);
        System.out.println(Cleaner.RED + "          Bad apples:     " + badCounter + Cleaner.RESET);
        System.out.println(Cleaner.YELLOW + "          Unknown apples: " + unknownCounter + Cleaner.RESET);
        System.out.println("--------------------------------------------------------");
    }

    /***
     * This method displays an exit message
     *
     * @author Royce Ayroso-Ong
     */
    public static void bappleExitMessage() {
        System.out.println("     ****************************");
        System.out.println("     ****** Bapples is out ******");
        System.out.println("     ****************************");
    }

    /***
     * This function displays the result of the command and the count
     * @param statcode
     * @param aLink
     * @param Gcounter
     * @param Bcounter
     * @param Ucounter
     * @throws Exception
     */
    public static void Results(int statcode, HashSet<String> aLink, int Gcounter, int Bcounter, int Ucounter) throws Exception {
        //if the user wants the specific status code of 200, it will only print the links with status code 200
        if (statcode == 200) {
            System.out.println("Finding Apples with Status " + statcode);

            for (String s : aLink) {
                int code = Handlers.LinkCode(s);
                if (code == statcode) {
                    System.out.println("[ " + statcode + " ]   GOOD APPLE    : " + s);
                    Gcounter++;
                }
            }
        } else if (statcode == 499) {
            System.out.println("Finding Apples with Status 404 or 400");

            for (String s : aLink) {
                int code = Handlers.LinkCode(s);
                if (code == 400 || code == 404) {
                    System.out.println("[ " + code + " ]   BAD APPLE     : " + s);
                    Bcounter++;
                }
            }
            //if the user does not have any specific status code, it will print all the links
        } else if (statcode == Cleaner.unknown) {
            for (String s : aLink) {
                int code = Handlers.LinkCode(s);

                if (code == 400 || code == 404) {
                    System.out.println("[ " + code + " ]   BAD APPLE     : " + s);
                    Bcounter++;
                } else if (code == 200) {
                    System.out.println("[ " + code + " ]   GOOD APPLE    : " + s);
                    Gcounter++;
                } else if (code == 0) {
                    System.out.println("[ ??? ]   UNKNOWN APPLE : " + s);
                    Ucounter++;
                } else {
                    System.out.println("[ " + code + " ]   UNKNOWN APPLE : " + s);
                    Ucounter++;
                }
            }
            //if the user wants the specific status code of 400 or 404, it will only print the links with status code 400 or 404
        } else if (statcode == 400 || statcode == 404) {
            System.out.println("Finding Apples with Status " + statcode);

            for (String s : aLink) {
                int code = Handlers.LinkCode(s);
                if (code == statcode) {
                    System.out.println("[ " + code + " ]   BAD APPLE     : " + s);
                    Bcounter++;
                }
            }
            //if the user does not have any specific status code, it will print all the links
        } else if (statcode == Cleaner.unknown) {
            for (String s : aLink) {
                int code = Handlers.LinkCode(s);

                if (code == 400 || code == 404) {
                    System.out.println("[ " + code + " ]   BAD APPLE     : " + s);
                    Bcounter++;
                } else if (code == 200) {
                    System.out.println("[ " + code + " ]   GOOD APPLE    : " + s);
                    Gcounter++;
                } else if (code == 0) {
                    System.out.println("[ ??? ]   UNKNOWN APPLE : " + s);
                    Ucounter++;
                } else {
                    System.out.println("[ " + code + " ]   UNKNOWN APPLE : " + s);
                    Ucounter++;
                }
            }
            //if user wants any other Status Code
        } else {
            System.out.println("Finding Apples with Status " + statcode);

            for (String s : aLink) {
                int code = Handlers.LinkCode(s);
                if (code == statcode) {
                    System.out.println("[ " + statcode + " ]   UNRIPE APPLE  : " + s); // pink
                    Ucounter++;
                }
            }
        }
        bappleResults(Gcounter, Bcounter, Ucounter);
    }

    /***
     * reading from HTML file, turn all html into one long string
     * @param file
     * @return
     * @throws IOException
     */
    public static String HtmlReader(String file) throws IOException {
        //reads from the *.html file then store all its contents in a string
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String inputLine;
        StringBuilder a = new StringBuilder();

        //reading all the content of the HTML
        while ((inputLine = reader.readLine()) != null)
            a.append(inputLine);
        reader.close();
        return a.toString();
    }
}

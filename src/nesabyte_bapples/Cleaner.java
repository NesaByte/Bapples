package nesabyte_bapples;


/***
 * @author nesa bertanico
 * @version 4
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.*;
import java.util.HashSet;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

//import static nesabyte_bapples.UrlCleaner.checkSecureURL;

public class Cleaner{// extends UrlCleaner{

    // for colored terminal text
    public static final String RESET = "\033[0m";
    final static String RED = "\033[0;31m";     // RED
    final static String GREEN = "\033[0;32m";   // GREEN
    final static String YELLOW = "\033[0;33m";  // YELLOW

    //set URL status code apart from 404 400 200 into 999
    final static int unknown = 999;

    /**
     * Demonstrate processing a single provided argument.
     *
     * @param arguments Command-line arguments; expecting a
     *                  String-based name.
     */
    public static void main(String[] args) throws Exception {

        String mUrl, html, txt = "";
        HashSet<String> m_url;
        HashSet<String> commands;

        if (args.length == 0) {
            Handlers.bappleHelp();
        } else {

            StringJoiner sb = new StringJoiner("");

            //loop through the args[] and save it into 1 string to be processed
            for (int i = 0; i < args.length; i++) {
                sb.add(args[i] + " ");
            }

            //turn the StringJoiner into a plain String
            String str = sb.toString();

            //get the commands from the command
            commands = Handlers.pullCommands(str);

            //get the urls from the command
            m_url = Handlers.pullLinks(str);

            //get the html from the command
            html = Handlers.pullHTML(str);

            try {
                //if user gave 1 or more commands
                if (commands.size() > 0) {

                    //loop through all the commands from the user
                    for (String cmds : commands) {

                        //if user wants the check the version
                        if (cmds.matches("--v") || cmds.matches("--version")) {
                            System.out.println("Bapples version: bap.v.03");

                            //if you wants to check the help
                        } else if (cmds.matches("--h") || cmds.matches("--help")) {
                            Handlers.bappleHelp();

                            //if you wants to check --all from HTML
                        } else if (cmds.matches("--all") && !html.equals("")) {
                            File directory = new File(html);
                            HtmlHandler.classifyingHTML(directory.getAbsolutePath(), unknown);

                            //if you wants to check --good from HTML
                        } else if (cmds.matches("--good") && !html.equals("")) {
                            File directory = new File(html);
                            HtmlHandler.classifyingHTML(directory.getAbsolutePath(), 200);

                            //if you wants to check --bad from HTML
                        } else if (cmds.matches("--bad") && !html.equals("")) {
                            File directory = new File(html);
                            HtmlHandler.classifyingHTML(directory.getAbsolutePath(), 499);

                            //if you wants to check --all from URL
                        } else if (cmds.matches("--all") && m_url.size() > 0) {
                            UrlHandler.classifyingURL(m_url, unknown);

                            //if you wants to check --good from URL
                        } else if (cmds.matches("--good") && m_url.size() > 0) {
                            UrlHandler.classifyingURL(m_url, 200);

                            //if you wants to check --bad from URL
                        } else if (cmds.matches("--bad") && m_url.size() > 0) {
                            UrlHandler.classifyingURL(m_url, 499);

                            //if user gives status command with a url
                        } else if (cmds.matches("--[0-9][0-9][0-9]") && m_url.size() > 0) {
                            String number = cmds.substring(2);
                            int num = Integer.parseInt(number.trim());
                            System.out.println("            nums: " + number);
                            UrlHandler.classifyingURL(m_url, num);

                            //if user give status command with an html file
                        } else if (cmds.matches("--[0-9][0-9][0-9]") && !html.equals("")) {
                            //String finall = "src\\nesabyte_bapples\\" + html;
                            File directory = new File(html);
                            //String fin = directory.getAbsolutePath();

                            String number = cmds.substring(2);
                            int num = Integer.parseInt(number.trim());
                            HtmlHandler.classifyingHTML(directory.getAbsolutePath(), num);

                            //if user wants to check if http can be https from a url
                        } else if (cmds.matches("--secure") && m_url.size() > 0) {
                            UrlHandler.checkSecureURL(m_url);

                            //if user wants to output JSON from html
                        } else if ((cmds.matches("--json") || cmds.matches("--j")) && !html.equals("")) {
                            JsonHandler.tobeJSON(html);

                            //if user wants to ignore urls, read ignore-url from txt file
                        } else if((cmds.matches("--ignore") || cmds.matches("--i"))){

                            txt = HtmlHandler.pullTXT(str);
                            File directory = new File(txt);
                            HtmlHandler.classifyingTXT(directory.getAbsolutePath(),html, unknown);

                            //if nothing matches, command is not allowed
                        } else {
                            System.out.println("Forbidden Command [" + cmds + "]");
                        }
                    }//end of for loop

                    //if user inputs 1 URL
                } else if (m_url.size() == 1) {
                    UrlHandler.classifyingURL(m_url, unknown); //


                    //if user inputs 1 HTML
                } else if (!html.equals("")) { // if user inputs an HTML
                    //String finall = "src\\nesabyte_bapples\\" + html;
                    File directory = new File(html);
                    // String fin = directory.getAbsolutePath();
                    HtmlHandler.classifyingHTML(directory.getAbsolutePath(), unknown);


                    //if nothing matches, command is not allowed
                } else {
                    System.out.println("You gave me a bad command");
                    Handlers.bappleHelp();
                }
                Handlers.bappleExitMessage(); // displays exit message

                //catch all exception errors
            } catch (Exception e) {
                System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n");
            }
        }
    }

}
package nesabyte_bapples;

/*import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;*/

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLHandshakeException;

import java.io.*;
/*
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;*/

public class Cleaner {

	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	/**
	 * Demonstrate processing a single provided argument.
	 *
	 * @param arguments Command-line arguments; expecting a
	 *    String-based name.
	 */
	public static void main(String[] args) throws Exception
	{
		
		String mUrl;
		HashSet<String> tree;
		HashSet<String> roots;
		
	   if (args.length == 0)
	   {
		   bappleHelp();
		   //mUrl = args[0];
		   //tree= pullLinks(mUrl);
		   //roots = pullCommands(mUrl);
		   System.out.println("Gimme an AppleTree: ");
		   
		   Scanner sc = new Scanner(System.in);
		   
		   //take in the command
		   mUrl = sc.nextLine();
		   
		   //get the commands from the command
		   roots = pullCommands(mUrl);	   
		
		   //get the urls from the command
		   tree = pullLinks(mUrl);
		   System.out.println("ARGS LENG: "+args.length);
	   }
	   else{		   
		  		   
		   //get the commands from the command
		   roots = pullCommands(args.toString());	   
		
		   //get the urls from the command
		   tree = pullLinks(args.toString());
		   
		   System.out.println("ARGS LENG: "+args.length);
		   
		   }
	   
	   try {
		   System.out.println("roots: "+ roots.size() +"     tree: "  + tree.size());
		   if(roots.size() > 0) {
			   for(String cmds : roots) {
				   System.out.println("cmds: [" + cmds +"] "+ tree.size());
			   if(cmds.toLowerCase().equals("--v ") ||  cmds.toLowerCase().equals("--version ") )  {
				   System.out.println("Bapples version: bap.v.01");
			   }
			   else if(cmds.equals("--h") ||  cmds.equals("--help")) {
				   bappleHelp();
			   }
			   else if(IsInt_ByException(cmds)){  // cmds.matches("[0-9][0-9][0-9]") && tree.size() != 0){
				   int num = Integer.parseInt(cmds.trim());
				   System.out.println("            nums: " + num);
				   classifyingApples(tree, num);   
			   }else if(roots.size() == 0 && tree.size() != 0){
				   classifyingApples(tree, 999);					   
			   }else if(cmds.equals("*.txt")) {
				   fileFinder(cmds);
			   }else {
				   System.out.println("Command ["+ cmds + "] is forbidden");
			   }
			 
			   }	
		   }else if(tree.size() > 0) {
			   classifyingApples(tree, 999);
		   }else {
			   System.out.println("You gave me a bad tree!");
		   }
		   
		   System.out.println(" *** Curtains closed *** ");
	   }catch(Exception e){
		   System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n");
  		}

	   }	   
	   
    private static File[] fileFinder( String dirName){
        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() { 
                 public boolean accept(File dir, String filename){
                	 System.out.println("filename: " + filename + dir.toString());
                	 return filename.endsWith(".txt"); }
        } );

    }
	
	/*https://www.computing.dcu.ie/~humphrys/Notes/Networks/java.html*/
	private static int AppleCode(String link) throws Exception {

		HttpURLConnection connection = null;
		int myCode = 0;
		 try{
						URL url = new URL(link);
						connection = (HttpURLConnection)url.openConnection();
						connection.setRequestMethod("GET");
						connection.connect();
						myCode = connection.getResponseCode();
						connection.setConnectTimeout(1000);
						return myCode;
						
					}catch(IOException e){
						return myCode;
					}finally {
						// If the connection is open, disconnect.
						if (connection != null) {
						connection.disconnect();
						}
					}
	}
	
	//Pull all links from the body for easy retrieval
	//http://blog.houen.net/java-get-url-from-string/
	private static HashSet<String> pullLinks(String text) {
		HashSet<String> links = new HashSet<String>();
	
		//https://gist.github.com/chrispinkney/069b09d2da5b9f7b73347d13ba3c32e7#file-index2-html-L9
		String regex = "(?:(?:https?|ftp)://)[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")){
				urlStr = urlStr.substring(1, urlStr.length() - 1);
				}
			links.add(urlStr);
		}
		return links;
	}
	
	private static HashSet<String> pullCommands(String text){
		HashSet<String> commands = new HashSet<String>();
		String regex = "(--)[-A-Za-z0-9]*[-A-Za-z0-9+&@#/%=~_()|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")){
				urlStr = urlStr.substring(1, urlStr.length() - 1);
				}
			commands.add(urlStr);
		}
		return commands;
		
	}
	
	private static HashSet<String> pullCodes(String text){
		HashSet<String> codes = new HashSet<String>();
		String regex = "[0-9]*[0-9]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")){
				urlStr = urlStr.substring(1, urlStr.length() - 1);
				}
			codes.add(urlStr);
		}
		return codes;
		
	}
	
	private static void classifyingApples(HashSet<String> tree, int statcode) {
		//if user puts more urls to check
		   for(String multi_link : tree) {
			   System.out.println("\n[ Counting Apples at " + multi_link + " ]");
			   try {
				   //connecting to myUrl
				   //Document myDoc = Jsoup.connect(myUrl).get();
				   URL URL = new URL(multi_link);
				   URLConnection myURLConnection = URL.openConnection();
				   myURLConnection.connect();
				   
				   //read the html from the url
			        BufferedReader in = new BufferedReader(new InputStreamReader(
			        		myURLConnection.getInputStream(), "UTF-8"));
			        String inputLine;
			        StringBuilder a = new StringBuilder();
			        while ((inputLine = in.readLine()) != null)
			            a.append(inputLine);
			        in.close();

			        String content = a.toString();
				    
				    HashSet<String> aLink= pullLinks(content);
				    System.out.println("\nTotal Apple count: "+ aLink.size());

					int Gcounter = 0, Bcounter = 0, Rcounter = 0;
													
					//https://gist.github.com/chrispinkney/069b09d2da5b9f7b73347d13ba3c32e7#file-index2-html-L9
					System.out.println("              -------- STATNUM : "+ statcode);
					if(statcode > 199 && statcode < 300) {
						System.out.println("Finding Apples with Status: 2XX: ");
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
							System.out.println(ANSI_GREEN + "[ " + code + " ]   GOOD APPLE    : "+ s.toString()); //green
							Gcounter++;	
						}
					}else if(statcode > 299 && statcode < 400) {	
						System.out.println("Finding Apples with Status: 3XX: ");
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
							System.out.println(ANSI_YELLOW + "[ " + code + " ]   OVERRIPE APPLE: "+ s.toString());//yellow
							Rcounter++;	
						}
					}else if(statcode > 399  && statcode < 500) {	
						System.out.println("Finding Apples with Status: 4XX: ");
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
							System.out.println(ANSI_RED + "[ " + code + " ]   BAD APPLE     : "+ s.toString()); //red
							Bcounter++;
						}
					}else if(statcode > 499 && statcode < 600){
						System.out.println("Finding Apples with Status: 5XX: ");
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
																		
							if(code == 500 ) {
								System.out.println(ANSI_CYAN + "[ " + code + " ]   UNRIPE APPLE  : "+ s.toString()); // pink
								Rcounter++;
							}
						}
					}else if(statcode == 999) {
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
																		
							if(code > 399  && code < 500) {
								System.out.println(ANSI_RED + "[ " + code + " ]   BAD APPLE     : "+ s.toString()); //red
								Bcounter++;
							}else if(code > 199 && code < 300) {
								System.out.println(ANSI_GREEN + "[ " + code + " ]   GOOD APPLE    : "+ s.toString()); //green
								Gcounter++;
							}else if(code == 0 ) {
								System.out.println(ANSI_PURPLE + "[ ??? ]   ROTTEN APPLE  : "+ s.toString()); //magenta
								Rcounter++;
							}else if(code > 299 && code < 400) {
								System.out.println(ANSI_YELLOW + "[ " + code + " ]   OVERRIPE APPLE: "+ s.toString());//yellow
								Rcounter++;
							}else if(code > 499 && code < 600 ) {
								System.out.println(ANSI_CYAN + "[ " + code + " ]   UNRIPE APPLE  : "+ s.toString()); // pink
								Rcounter++;
							}else {
								System.out.println(ANSI_WHITE + "[ " + code + " ]   GOOD APPLE    : "+ s.toString());//white
								Rcounter++;
							}
						}
					}else {
						System.out.println("Code Error");
					}
					
					System.out.println(ANSI_RESET +
							           "--------------------------------------------------------");
					System.out.println( ANSI_RESET + "      Done counting apples!");
				    System.out.println("          Good apples: " + Gcounter);
					System.out.println("          Bad apples: " + Bcounter);
					System.out.println("          Rotten apples: " + Rcounter);
					System.out.println("--------------------------------------------------------");								
			   }catch (Exception e){
				   System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n" + multi_link.toString());
			   		}
			   }
	}
	
	private static void bappleHelp(){
		System.out.println(ANSI_RESET +
		                   "-----------------------------------------------------------------------");
		System.out.println("-    WELCOME TO BAPPLES! Finding bad apples from any apple trees      -");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("-       --v or --version | to check the Bapple version                -");
		System.out.println("-       -2XX           | to list urls with status code: SUCCESS       -");
		System.out.println("-       -3XX           | to list urls with status code: REDIRECTION   -");
		System.out.println("-       -4XX           | to list urls with status code: CLIENT ERRORS -");
		System.out.println("-       -5XX           | to list urls with status code: SERVER ERRORS -");
		System.out.println("-----------------------------------------------------------------------");
	}
	
	private static boolean IsInt_ByException(String str){
	    try
	    {
	        Integer.parseInt(str.trim());
	        return true;
	    }
	    catch(NumberFormatException nfe)
	    {
	        return false;
	    }
	}
}

	


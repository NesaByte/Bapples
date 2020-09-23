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
import java.util.StringJoiner;
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
	public static final String ANSI_GRAY = "\u001b[0m";
	
	/**
	 * Demonstrate processing a single provided argument.
	 *
	 * @param arguments Command-line arguments; expecting a
	 *    String-based name.
	 */
	public static void main(String[] args) throws Exception
	{
		
		String mUrl, html = "";
		HashSet<String> tree;
		HashSet<String> commands;
		
	   if (args.length == 0)
	   {
		   bappleHelp();

		   System.out.println("Gimme an AppleTree: ");
		   
		   Scanner sc = new Scanner(System.in);
		   
		   //take in the command
		   mUrl = sc.nextLine();
		   
		   //get the commands from the command
		   commands = pullCommands(mUrl);	   
		
		   //get the urls from the command
		   tree = pullLinks(mUrl);
		   
		   //get html from the command
		   html = pullHTML(mUrl);
	   }
	   else{	
		  
		   StringJoiner sb = new StringJoiner("");
		   for(int i = 0; i < args.length; i++) {
			   sb.add(args[i] + " ");
		   }

		      String str = sb.toString();
		   commands = pullCommands(str);	   
		
		   //get the urls from the command
		   tree = pullLinks(str);
		   
		   html = pullHTML(str);
		   
		   
		}
		   
	   
	   try {		   
		   if(commands.size() > 0) { //if user input more than 0 commands
			   for(String cmds : commands) {
			   if(cmds.equals("--v") ||  cmds.equals("--version") )  {
				   System.out.println("Bapples version: bap.v.01");
			   }
			   else if(cmds.matches("--h") ||  cmds.matches("--help")) {
				   bappleHelp();
			   }
			   else if(cmds.matches("--[0-9][0-9][0-9]") && tree.size() > 0){ 
				   String number = cmds.substring(2);
				   int num = Integer.parseInt(number.trim());
				   System.out.println("            nums: " + number);
				   classifyingApples(tree, num);					   
			   }else if(cmds.matches("--[0-9][0-9][0-9]") && !html.equals("")) {
				   //fileFinder(cmds);

				   String finall = "src\\nesabyte_bapples\\" + html;
				   File directory = new File(finall);
				   String fin = directory.getAbsolutePath();
				   
				   String number = cmds.substring(2);
				   int num = Integer.parseInt(number.trim());
				     classifyingHTML(fin, num);
			   }else {
				   System.out.println("Forbidden Command ["+ cmds + "]");
			   }
			 
			   }	
		   }else if(tree.size() > 0) { //if user inputs more than 0 URL
			   classifyingApples(tree, 999);
			   
			   
		   }else if(!html.equals("")) { // if user inputs an HTML 
			   String finall = "src\\nesabyte_bapples\\" + html;
			   File directory = new File(finall);
			   String fin = directory.getAbsolutePath();
			   classifyingHTML(fin, 999);
		   }else {
			   System.out.println("You gave me a bad command");
		   }
		   System.out.println("     ****************************");
		   System.out.println("     ****** Bapples is out ******");
		   System.out.println("     ****************************");
	   }catch(Exception e){
		   System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "\n");
  		}

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
	
	private static String pullHTML(String text) {
		String HTMLStr = "";
		String regex ="[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|].html";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
			HTMLStr = m.group();
			return HTMLStr;
		}
		return HTMLStr;
	}
	
	private static HashSet<String> pullCommands(String text){
		 
		HashSet<String> commands = new HashSet<String>();
		String regex = "--[-A-Za-z0-9+&@#/%?=~_()|!:,.;]*[-A-Za-z0-9+&@#/%=~_()|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
			String urlStr = m.group();
			commands.add(urlStr);
		}
		
		String regex_num = "--[0-9][0-9][0-9]";
		Pattern paa = Pattern.compile(regex_num);
		Matcher maa = paa.matcher(text);
		while(maa.find()) {
			String urlStrrr = maa.group();
			commands.add(urlStrrr);
		}
		return commands;
	}
	
	private static void classifyingHTML(String file, int statcode) {
		//if user puts more urls to check
		 //String file ="src/nesabyte_bapples/testing.html";
	     
		try {			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String inputLine;
			StringBuilder a = new StringBuilder();
			while ((inputLine = reader.readLine()) != null)
				   a.append(inputLine);
				     String currentLine = reader.readLine();
				     reader.close();
				     String content = a.toString();
								    
				    HashSet<String> aLink= pullLinks(content);
				    System.out.println("\nTotal Apple count: "+ aLink.size());

					int Gcounter = 0, Bcounter = 0, Ucounter = 0;
					
					if(statcode == 200) {
						System.out.println("Finding Apples with Status " + statcode);
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
							if(code == statcode) {
								System.out.println(ANSI_GREEN + "[ " + statcode + " ]   GOOD APPLE    : "+ s.toString()); //green
								Gcounter++;
							}		
						}
					}else if(statcode == 400  || statcode == 404) {	
						System.out.println("Finding Apples with Status " + statcode);
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
							if(code == statcode) {
								System.out.println(ANSI_RED + "[ " + statcode + " ]   BAD APPLE     : "+ s.toString()); //red
								Bcounter++;		
							}
						}
					}else if(statcode == 999) {
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
																		
							if(code == 400  || code == 404) {
								System.out.println(  ANSI_RED + "[ " + code + " ]   BAD APPLE     : "+ s.toString()); //red
								Bcounter++;
							}else if(code == 200) {
								System.out.println(ANSI_GREEN + "[ " + code + " ]   GOOD APPLE    : "+ s.toString()); //green
								Gcounter++;
							}else if(code == 0 ) {
								System.out.println( ANSI_GRAY +          "[ ??? ]   UNKNOWN APPLE : "+ s.toString()); //magenta
								Ucounter++;
							}else {
								System.out.println( ANSI_GRAY + "[ " + code + " ]   UNKNOWN APPLE : "+ s.toString());//white
								Ucounter++;
							}
						}
					}else{
						System.out.println("Finding Apples with Status " + statcode);
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());							
							if(code == statcode) {
								System.out.println(ANSI_GRAY + "[ " + statcode + " ]   UNRIPE APPLE  : "+ s.toString()); // pink
								Ucounter++;
							}
						}
					}
					
					System.out.println(ANSI_RESET +
							           "--------------------------------------------------------");
					System.out.println( ANSI_RESET + "      Done counting apples!");
				    System.out.println("          Good apples:    " + Gcounter);
					System.out.println("          Bad apples:     " + Bcounter);
					System.out.println("          Unknown apples: " + Ucounter);
					System.out.println("--------------------------------------------------------");								
			   }catch (Exception e){
				   System.out.println("\n\nYou gave me a Bad Apple Tree: " + e + "[" + file + "]");
			   		}
			   
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

					int Gcounter = 0, Bcounter = 0, Ucounter = 0;
													
					//https://gist.github.com/chrispinkney/069b09d2da5b9f7b73347d13ba3c32e7#file-index2-html-L9
					System.out.println("              -------- STATNUM : "+ statcode);
					if(statcode == 200) {
						System.out.println("Finding Apples with Status " + statcode);
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
							if(code == statcode) {
								System.out.println(ANSI_GREEN + "[ " + statcode + " ]   GOOD APPLE    : "+ s.toString()); //green
								Gcounter++;
							}		
						}
					}else if(statcode == 400  || statcode == 404) {	
						System.out.println("Finding Apples with Status " + statcode);
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
							if(code == statcode) {
								System.out.println(ANSI_RED + "[ " + statcode + " ]   BAD APPLE     : "+ s.toString()); //red
								Bcounter++;		
							}
						}
					}else if(statcode == 999) {
						for (String s : aLink)  {
							int code = AppleCode(s.toString());
																		
							if(code == 400  || code == 404) {
								System.out.println(  ANSI_RED + "[ " + code + " ]   BAD APPLE     : "+ s.toString()); //red
								Bcounter++;
							}else if(code == 200) {
								System.out.println(ANSI_GREEN + "[ " + code + " ]   GOOD APPLE    : "+ s.toString()); //green
								Gcounter++;
							}else if(code == 0 ) {
								System.out.println( ANSI_GRAY +          "[ ??? ]   UNKNOWN APPLE : "+ s.toString()); //magenta
								Ucounter++;
							}else {
								System.out.println( ANSI_GRAY + "[ " + code + " ]   UNKNOWN APPLE : "+ s.toString());//white
								Ucounter++;
							}
						}
					}else{
						System.out.println("Finding Apples with Status " + statcode);
						
						for (String s : aLink)  {
							int code = AppleCode(s.toString());							
							if(code == statcode) {
								System.out.println(ANSI_GRAY + "[ " + statcode + " ]   UNRIPE APPLE  : "+ s.toString()); // pink
								Ucounter++;
							}
						}
					}
					
					System.out.println(ANSI_RESET +
							           "--------------------------------------------------------");
					System.out.println( ANSI_RESET + "      Done counting apples!");
				    System.out.println("          Good apples:    " + Gcounter);
					System.out.println("          Bad apples:     " + Bcounter);
					System.out.println("          Unknown apples: " + Ucounter);
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

	


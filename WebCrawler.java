/*
	The class WebCrawler takes a starting URL and a number of hops (n),
	and jumps from the starting URL to other URL's n number of times.
	
	Created By William Eng for Professor Dimpsey's CSS490 Cloud Computing
	class for Winter 2019, as part of a submission for Program 1.
*/

import java.lang.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WebCrawler {
        private static ArrayList<String> visited;
        private static URL link;
        private static URL error_url;
        private static String error_string = "https://an_error_occured.com";
        private static String report = "";
        private static int jumps;
        private static final Pattern P = Pattern.compile("a href=\"(http.*?)\"");
        
	//Construtor for the WebCrawler class
	//If startURL is not proper URL, it will print an error
	//message and terminate the program.
	//Otherwise, startURL becomes the start point for jumps,
	//and hops becomes the number of jumps to make.
	public WebCrawler(String startURL, int hops) throws MalformedURLException{
                try {
                        link = new URL(startURL);
                	error_url = new URL(error_string);
                } catch (MalformedURLException e) {
                        System.out.println("Initial URL is malformed or broken: " + startURL + "\nTerminating program...");
                        System.exit(-1);
                }
                jumps = hops;
                visited = new ArrayList<String>();
        }

	//Performs jumps from current url link jumps amount of times.
	//If there are no more valid a href links in the current URL's
	//http, it will print an error message and terminate.
        public static void doJumps() throws MalformedURLException{
                int curJump = 0;
                while(curJump <= jumps){
                        report += (curJump + ": " + link.toString() + "\n");
                        link = nextURL(link);
                        if(link.toString().equals(error_string)){
                                System.out.println("No more valid <a href>'s in " + visited.get(visited.size() - 1));
                                break;
                        }
                        curJump++;
                }
        }

	//Retrieves URL url's HTML information and returns it stored as
	//a StringBuilder object.
	//Will print an IOException if given a url that cannot be reached,
	//and will return null.
        public static StringBuilder retrieveHTML(URL url){
                StringBuilder builder = new StringBuilder();
                try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                                builder.append(inputLine);
                        }
                        in.close();
                        return builder;
                } catch (IOException e){
                        System.out.println("IO Exception with " + url.toString() + "\nTerminating program...");
                        return null;
                }
        }

        //Looks at the HTML information of the URL cur
        //It will return the next a href in that HTML that 
        //is valid and has not already been seen. 
        //If there are none, it will return a special empty URL error_url;
        public static URL nextURL(URL cur){
                Matcher m = P.matcher(retrieveHTML(cur));
                while(m.find()){
                	String tempURL = m.group(1);
                        if(!(visited.contains(tempURL)) || visited.contains(tempURL + "/") || visited.contains(tempURL.substring(0, tempURL.length() - 1))){
                                try {
                                        cur = nextURLHelper(new URL(m.group(1)));
                                        if(!cur.toString().equals(error_string)){
                                                return cur;
                                        }
                                } catch (MalformedURLException e){
                                        continue;
                                }
                        }
                }
                return error_url;
        }

	//Helper method that connects to a URL, checks it's response code, and
	//returns an appropriate URL:
	//
	//200's will return the original URL given
	//300's will return the redirect of the original URL
	//400's will return a special empty URL error_url
	//Any other response will return the error_url
        public static URL nextURLHelper(URL url) throws MalformedURLException{
                try {
                        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        int response = conn.getResponseCode();
			visited.add(url.toString());

                        if(response >= 200 && response < 300){
                                return url;
                        }
                        if(response >= 300 && response < 400){
                                String redirect = conn.getHeaderField("Location");
                                visited.add(redirect);
                                return (new URL(redirect));
                        }
                        if(response >= 400 && response < 500){
                                return error_url;
                        }

                } catch (Exception e){
                        return error_url;
                }
                return error_url;
        }

	//Prints the information about which links were visited,
	//and at what jump.
	public static void printReport(){
		System.out.println(report);
	}
}

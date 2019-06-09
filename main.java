/*
	This file contains the main method for Professor Dimpsey's CSS490 Cloud 
	Computing class for Winter 2019, as part of a submission for Program 1.

	Created By William Eng 
*/

import java.net.MalformedURLException;

public class main{

	//The main method first checks that there are only two arguments,
	//and that the arguments types are properly String and int, in
	//that order.
	//If either of these conditions are violated, and error message is
	//printed and the program is terminated.
        public static void main(String[] args) throws MalformedURLException{
		WebCrawler crawly; 
                if (args.length != 2) {
                        System.out.println("Inappropriate number of arguments.\nTerminating program...");
                        return;
                }

                try {
                        crawly = new WebCrawler(args[0], Integer.parseInt(args[1]));
                } catch (IllegalArgumentException e) {
                        System.out.println("Invalid arguments.\nTerminating program...");
                        return;
                }
                crawly.doJumps();
                crawly.printReport();
        }
}

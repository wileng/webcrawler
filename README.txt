Created by William Eng

In order to properly run, please do the following:

	0) This program was developed in Linux. While it may work in other environments, it has
	   not been tested to.
	   
	1) Make sure that the contents of the folder are all extracted and placed in the same folder.

	2) Run the build.sh file with the command "./build.sh"
		-You may have to open the file and change the path to your bash program.
		-If permission is denied, run the command "chmod a+x build.sh". I've done this already,
	 	but something may happen enroute that changes the

	3) Use the command "java main [INITIAL URL HERE] [NUMBER OF DESIRED HOPS HERE]" to exceute the program.


How the Program Works:

	This program works by first checking initially that the two arguments given are valid and
	of the appropriate type. Then, it gets the HTML data from the first link and parses it to
	find the first valid <a href> link. It jumps to that link if it valid, and repeats the 
	process, adding the URL to a "seen" data structure. If a link has been visited already,
	or returns a response code in the 400's it will be skipped over. The process is repeated
	until it has done n (the initial number given) number of hops or a URL's HTTP data has
	no more valid <a href> links, then it terminates and prints the links visited in what order.
	300's/redirects are treated as 1 hop.   

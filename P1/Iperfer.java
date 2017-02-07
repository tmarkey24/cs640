import java.util.*;
import java.io.*;
import java.net.*;

/*
Iperfer
CS 640 P1
Aaron Delander:	delander
Thomas Markey:	markey
*/
public class Iperfer {
	
    public static void main(String[] args) throws IOException {

		///////////////
    	/*CLIENT MODE*/
		///////////////
    	if((args.length == 7) && (args[0].equals("-c"))){
    		
			// Get inputs
    		String server_hostname = args[2];
    		int server_port = Integer.parseInt(args[4]);
    		int time = Integer.parseInt(args[6]);
    		
    		//Assure range for server port
    		if (server_port < 1024 || server_port > 65535){
    			System.out.println("Error: port number must be in the range 1024 to 65535");
    			System.exit(1);
    		}
    		
    		Socket clientSoc = new Socket(server_hostname, server_port);
    		PrintWriter out = new PrintWriter(clientSoc.getOutputStream(), true);
    			
        	// Send data chunks of 1000 bytes and data is all 0's
        	char[] dataChunk = new char[1000];
        	long timeMillis = time * 1000;
        	long startTime = System.currentTimeMillis();
        	boolean finished = false;
        		
        	int numSent = 0;
        		
        	//Send data for specified time
        	while(!finished){
        		out.write(dataChunk);
        		numSent++;
				finished = (System.currentTimeMillis() - startTime) >= timeMillis;
			}

        	// Calculations
			double rate =  numSent / (time*1000.0);
			System.out.printf("sent=%d KB rate=%.3f Mbps\n",numSent, rate);

        	// Shut down client sockets	
    		out.close();
    		clientSoc.close();
    	}

		///////////////
    	/*SERVER MODE*/
		///////////////
    	else if((args.length == 3) && (args[0].equals("-s"))){

    		int listen_port = Integer.parseInt(args[2]);
			int bytes_received = 0;
			int start_time = 0;
			int end_time = 0;
    		
    		// Assure range for listen port
    		if (listen_port < 1024 || listen_port > 65535){
    			System.out.println("Error: port number must be in the range 1024 to 65535");
    			System.exit(1);
    		}

			// Create server socket that listens to specified port
    		ServerSocket serverSoc = new ServerSocket(listen_port);
			System.out.println("Waiting for client connection...");	// TODO: delete debug
			
			// Record time when connection occurs
			Socket clientSoc = serverSoc.accept();
			System.out.println("Client connection made!");	// TODO: delete debug
			start_time = (int) (System.currentTimeMillis() / 1000); 

			BufferedReader in = new BufferedReader(			// input stream
									new InputStreamReader(clientSoc.getInputStream())
								);

			// Receive Data
			while(in.read() != -1) {
				bytes_received++;
			}
			
			// Calculations
			end_time = (int) (System.currentTimeMillis() / 1000); // get end time in seconds
			int duration = end_time - start_time;
			double rate =  bytes_received / (duration*1000000.0);
			System.out.printf("received=%d KB rate=%.3f Mbps\n", bytes_received/1000, rate);
			
			// Shut down server
			serverSoc.close();
			clientSoc.close();	
    	}

		///////////////////////
    	/*INCORRECT ARGUMENTS*/
		///////////////////////
    	else {
    		System.out.println("Error: missing or additional arguments");
    		
    	}	
    }
}


import java.util.*;
import java.io.*;
import java.net.*;

public class Iperfer {
	
    public static void main(String[] args) throws IOException {

    	/*CLIENT MODE*/
    	if((args.length == 7) && (args[0].equals("-c"))){
    		
    		String server_hostname = args[2];
    		int server_port = Integer.parseInt(args[4]);
    		int time = Integer.parseInt(args[6]);
    		
    		//Assure range for server port
    		if (server_port < 1024 || server_port > 65535){
    			System.out.println("Error: port number must be in the range 1024 to 65535");
    			System.exit(1);
    		}
    		
    		try {
    			Socket clientSoc = new Socket(server_hostname, server_port);
    			PrintWriter out = new PrintWriter(clientSoc.getOutputStream(), true);
    			
    			//TODO:
        		//Need to send data chunks of 1000 bytes and data is all 0's
        		byte[] dataChunk = new byte[1000];
        		long timeMillis = time * 1000;
        		long startTime = System.currentTimeMillis();
        		boolean finished = false;
        		
        		int numSent = 0;
        		
        		//Send data for specified time
        		while(!finished){
   
	     			//Send data on socket
        			out.print(dataChunk);
   
        			numSent++;
        			finished = (System.currentTimeMillis() - startTime) >= timeMillis;    			
        		}
      		
        		// Calculations
				System.out.println( "sent=" + numSent + " KB " +
								"rate=" +/* numSent/(time/1000000000) +*/ " Mbps");

        		
        		out.close();
        		clientSoc.close();
    		}
    		catch (IOException e){
    		}
    		
    	}
    	/*SERVER MODE*/
    	else if((args.length == 3) && (args[0].equals("-s"))){

    		int listen_port = Integer.parseInt(args[2]);
			int bytes_received = 0;
			int time = 0;	// seconds
			String text;
    		
    		//Assure range for listen port
    		if (listen_port < 1024 || listen_port > 65535){
    			System.out.println("Error: port number must be in the range 1024 to 65535");
    			System.exit(1);
    		}

			// Create server socket that listens to specified port
    		ServerSocket serverSoc = new ServerSocket(listen_port);
			System.out.println("Waiting for client connection...");	// debug
			
			Socket clientSoc = serverSoc.accept();
			System.out.println("Client connection made!");	// debug

			BufferedReader in = new BufferedReader(			// input stream
									new InputStreamReader(clientSoc.getInputStream())
								);

			// Receive Data
			while((text = in.readLine()) != null){
				bytes_received++;
				System.out.print("1");
			}
			
			// know when client has left, then continue

			// Output data
			System.out.println( "received=" + bytes_received/1000 + " KB " +
								"rate=" + /*bytes_received/(1000000*time) +*/ " Mbps");

			// Shut down server... 
			serverSoc.close();
			clientSoc.close();
    		
    	}
    	/*INCORRECT ARGUMENTS*/
    	else {
    		System.out.println("Error: missing or additional arguments");
    		
    	}	
    }
}


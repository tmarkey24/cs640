import java.net.*;
import java.io.*;
import java.util.*;

public class Server {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }

	int serverPort = Integer.parseInt(args[0]);

	/* 1. Create a ServerSocket that listens on the specified port */
	ServerSocket serverSoc = new ServerSocket(serverPort);		// calling the bind function also

	System.out.println("Waiting for client conneciton");
	/* 2. Block until a client requests a connection to this application */
	Socket clientSoc = serverSoc.accept();

	System.out.println("Client connection made");
	/* 3. Get handles to the output and input stream of the socket */
	PrintWriter out = new PrintWriter(clientSoc.getOutputStream(), true);						// output stream
	BufferedReader in = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));	// input stream
	
	
	String text;
	/* 4. Block until you read a line from the client (Incoming data can read from the input stream) */
	/* 5. Echo back the line read from the client (Write the incoming data to the output stream) */
	
	while((text = in.readLine()) != null) {
		out.println(text);	// echo back message
	}
	
	}
}

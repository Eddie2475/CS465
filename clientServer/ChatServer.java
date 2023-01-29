import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer 
   {
	ArrayList clientOutputStreams;

    public ChatServer() {
	// list for clients join 
    clientOutputStreams = new ArrayList<>();

	try 
	   {
		// create a server socket
		ServerSocket serverSock = new ServerSocket(5000);
		
		// loop while true
		while(true) 
		   {
			// create client socket
			Socket clientSocket = serverSock.accept();
			
			// add client to list by default (later change to JOIN )
			PrintWriter writer = new PrintWriter( clientSocket.getOutputStream());
			
            // test for join 
		    clientOutputStreams.add(writer);

            BufferedReader input = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));

            String message = input.readLine();
            System.out.println("Recieve from client: " + message);

			
			// create thread to handle multiple clients
			// Thread clientThread = new Thread(new ClientHandler( clientSocket ));
			
			// start the thread process
			// clientThread.start();
			// verify connection had been established
			System.out.println("Got a connection");
		   } 
		
		} catch( Exception ex ) 
	      {
			ex.printStackTrace();
	      }
	

   }
   public static void main(String[] args)
      {
        new ChatServer();
      }
    }

    


	  // run

	    // create output and input streams (in that order)

	    // read message

	    // message cases (switch)

	      // leave/shutdown cases

	        // remove participant

	        // print who was removed and who is left

package clientServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer
   {
	public static ArrayList<Node> clientOutputStreams;

    public ChatServer() {
	// list for clients join 
    clientOutputStreams = new ArrayList<Node>();

	try 
	   {
		// create a server socket
		ServerSocket serverSock = new ServerSocket(5000);
		
		// loop while true
		while(true) 
		   {
			// create client socket
			System.out.println("Server is running");
			Socket clientSocket = serverSock.accept();
			
			// create a thread for each cient
			Thread thread = new Thread(new ClientHandler(clientSocket));
			// start the thread
			thread.start();
			
		    // print to console if connection is made
			System.out.println("Got a connection");
		   } 
		
		} catch( Exception ex ) 
	      {
			ex.printStackTrace();
	      }
	

   }
    
    /*
    // parameters( message, clients node )
   public static void sendToAll( Message message, Node clientNode )
      {
      ObjectInputStream in;
      ObjectOutputStream out;
      Socket clientSocket;
   
      // loop through list
      
      //for( int i = 0; i < clientOutputStreams.size(); i++)
       //  {
         // create object out put stream
         // creste object input stream
         // create socket connection for each node
         try
            {
            clientSocket = new Socket(clientNode.address, clientNode.port);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            
            out.writeObject(message.getContent());
            
           // out.writeObject(message);
            System.out.println("message sent to client: " + message.getContent());
            
            clientSocket.close();
            } catch (IOException e)
            {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
         
      
         // write to client
         // print out message written to client 
      // end looop
       //  }
         // loop through the list
            // create a socket for 
         // 
      }
*/
    
 // add item to list of nodes 
   public static void addItem( Node clientNode )
      {
      clientOutputStreams.add(clientNode);
      
      }
   
   public static void main(String[] args)
      {
      // create new server
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

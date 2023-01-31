package clientServer;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.text.html.HTMLDocument.Iterator;

import message.Message;
import message.MessageTypes;

public class ClientHandler implements Runnable {
	
	static Receiver reciever = null;
	static Sender sender = null;
    
    

    public static Node serverNode;
    public static Node myNode;
    
    // initialize variables 
    InputStream inputStream;
    ObjectInputStream readFromNet;
    ObjectOutputStream writeToNet;
    Message message;
    
    private Socket clientSocket;

    // construct client socket
    public ClientHandler(Socket clientSocket) throws IOException
    	{
    	this.clientSocket = clientSocket;
    	
    	
    	}

    @Override
    
    public void run() {
  
    	try {
    	   // open object streams from client socket
    	   writeToNet = new ObjectOutputStream(clientSocket.getOutputStream());
    	   readFromNet = new ObjectInputStream(clientSocket.getInputStream());
    	   
    	   // cast meassgae from message object
    		message = (Message)readFromNet.readObject();
    		
    	}catch (IOException | ClassNotFoundException ex)
        {
            System.err.println("Error reading message");
        }
    	
    	// create casses for JOIN/ Leave/ Shutdown/Shutdown_ALL
    	switch( message.getType() )
    	{
    	
    	case MessageTypes.JOIN:
    	
    	   myNode = (Node) message.getContent();
    	   
    	   
    		System.out.println("Client joined the server");
    		
    		
    		ChatServer.addItem( myNode );
    		
    		
    		break;
    		
    	case MessageTypes.NOTE:
    	   
    	/*
    	   java.util.Iterator<Node> chatPeople = ChatServer.clientOutputStreams.iterator();
    	   while( chatPeople.hasNext())
    	      {
    	        Node chatPeopleInfo = chatPeople.next();
    	     try
              {
              clientSocket = new Socket(chatPeopleInfo.address, chatPeopleInfo.port);
              writeToNet = new ObjectOutputStream(clientSocket.getOutputStream());
              readFromNet = new ObjectInputStream(clientSocket.getInputStream());
              
              writeToNet.writeObject(message);
              
             // out.writeObject(message);
              System.out.println("message sent to client: " + message.getContent());
              
              clientSocket.close();
              } catch (IOException e)
              {
              // TODO Auto-generated catch block
              e.printStackTrace();
              }
           
    	      }
    	      */
    	  // print message recieved from sender
    	   System.out.println("Message recieved: " + message.getContent());
    	   
         break;
         
    	case MessageTypes.LEAVE, MessageTypes.SHUTDOWN:
    	
    	   
    	   // remove cleint from list
    	   ChatServer.clientOutputStreams.remove(writeToNet);
    	   
    	   System.out.println("Client left chat");
    	  try
           {
              clientSocket.close();
           }
           catch(IOException ex)
           {
              System.err.println("error with client connection");
           }
    	
    	   break;
      
      case MessageTypes.SHUTDOWN_ALL:
         
         // shut down server 
         System.out.println("Shutting down server");
         
         System.exit(0);
   
         break;
      
      
    	}
        
    }
     
}

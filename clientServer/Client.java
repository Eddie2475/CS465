package clientServer;
import java.net.*;
import java.io.*;

public class Client {

   // create sender and reciever instances
    Sender send;
    Receiver rec;

    public Client() 
    	{
    	   // create a send and reciever for each client 
        send = new Sender();
        send.start();
        
        while( ClientHandler.serverNode == null );
        rec = new Receiver();
    	  rec.start();
        }

    public static void main(String[] args) throws IOException
       {
        // Start a new client
    	 new Client();
    	

       }
    
}
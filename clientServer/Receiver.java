package clientServer;

import java.io.IOException;
import message.Message;
import message.MessageTypes;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Receiver extends Thread
{
   // initialize variables
    Message message;
    Socket serverSocket;
    ServerSocket receiverSocket;
    ObjectInputStream readFromNet;
    ObjectOutputStream writeToNet;

    public Receiver()
    {
       
        try
        {
           // create a reciever socket that connect to server
            receiverSocket = new ServerSocket(ClientHandler.myNode.getPort());
            System.out.println("[Receiver.Receiver] receiver socket crerted, listening on port" + ClientHandler.myNode.getPort());
           
        }
        catch (IOException ex)
        {
            System.err.println("Error creating socket and io streams");
        }


    }

    @Override
    public void run()
    {
    
    // run server loop
    while (true)
       {
          try
             {
             // create socket to listen to server messages
             serverSocket = receiverSocket.accept();
             // open streams 
             readFromNet = new ObjectInputStream(serverSocket.getInputStream());
             writeToNet = new ObjectOutputStream(serverSocket.getOutputStream());
             // cast message to object sent it 
             message = (Message)readFromNet.readObject();
             }catch (IOException | ClassNotFoundException ex)
             {
             System.err.println("Error reading message");
         }

          // create cases for message 
         switch (message.getType())
         {
             case MessageTypes.SHUTDOWN:
                 System.out.println("Received shutdown message from server, exiting");

                 try
                 {
                     serverSocket.close();
                 }
                 catch (IOException ex)
                 {
                     System.err.println("Error closing server connection");
                 }

                 System.exit(0);

                 break;

             case MessageTypes.NOTE:
                 System.out.println((String)message.getContent());

                 try
                 {
                     serverSocket.close();
                 }
                 catch (IOException ex)
                 {
                     System.err.println("Error closing server connection");
                 }

                 break;
         }
       }
    }
}

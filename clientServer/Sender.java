package clientServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import message.Message;
import message.MessageTypes;
import clientServer.ClientHandler;
import clientServer.Node;

public class Sender extends Thread implements MessageTypes
{
    // initialize variables
    Socket serverSocket;
    Scanner inputScan;
    Boolean joined;
    String inputString;
    
    Node myNode;

    
    // sender constructor
    public Sender()
    {
        // declare scanner and joined boolean
        inputScan = new Scanner(System.in);
        joined = false;
    }

    @Override
    public void run()
    {
        // initialize variables
        ObjectInputStream readFromServer;
        ObjectOutputStream writeToServer;
        
        myNode = new Node( "localhost", 5000, "Client");
        
        // loop while true
        while (true)
        {
            // get user input
            inputString = inputScan.nextLine();

            // check for invalid action based on message type
            if (inputString.startsWith("JOIN"))
            {
                if (joined)
                {
                    System.out.println("Error: Already joined chat");
                    continue;
                }

                // create node with server info
                String[] serverInfo = inputString.split(" ");

                ClientHandler.serverNode = new Node(serverInfo[1], Integer.parseInt(serverInfo[2]));

                // check for bad info -> print and ignore
                if (ClientHandler.serverNode == null)
                {
                    System.err.println("Error: No server connectivity information provided.");
                    continue;
                }
            }
            else
            {
                if (!joined)
                {
                    System.out.println("Error: Not in chat");
                    continue;
                }
            }


            try
            {
                // create socket
                serverSocket = new Socket(ClientHandler.serverNode.getAddress(), ClientHandler.serverNode.getPort());

                readFromServer = new ObjectInputStream(serverSocket.getInputStream());
                writeToServer = new ObjectOutputStream(serverSocket.getOutputStream());

                // send message of requested type
                if (inputString.startsWith("JOIN"))
                {
                    writeToServer.writeObject(new Message(JOIN, myNode));
                    joined = true;
                    System.out.println("Joined chat");
                }
                else if (inputString.startsWith("LEAVE"))
                {
                    writeToServer.writeObject(new Message(LEAVE, myNode));
                    joined = false;
                    System.out.println("Left chat");
                }
                else if (inputString.startsWith("SHUTDOWN_ALL"))
                {
                    writeToServer.writeObject(new Message(SHUTDOWN_ALL, myNode));
                    joined = false;
                    System.out.println("All connections shutdown");
                    System.exit(0);
                }
                else if (inputString.startsWith("SHUTDOWN"))
                {
                    writeToServer.writeObject(new Message(SHUTDOWN, myNode));
                    joined = false;
                    System.out.println("Connection shutdown");
                    System.exit(0);
                }
                
                else
                {
                    writeToServer.writeObject(new Message(NOTE, inputString));
                    System.out.println("Message sent");
                }

                serverSocket.close();
            }
            catch (IOException ex)
            {
                System.err.println("Error sending message");
            }
        }
    }
}
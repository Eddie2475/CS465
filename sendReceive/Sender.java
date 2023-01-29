package sendReceive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import message.Message;
import message.MessageTypes;
import clientServer.ChatServer;
import clientServer.Client;
import clientServer.ClientHandler;
import clientServer.Node;

public class Sender extends Thread implements MessageTypes
{
    // initialize variables
    Socket serverSocket;
    Scanner inputScan;
    Boolean joined;
    String inputString;

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
            }
            else if (inputString.startsWith("LEAVE") || inputString.startsWith("SHUTDOWN") || inputString.startsWith("SHUTDOWN_ALL"))
            {
                if (!joined)
                {
                    System.out.println("Error: Not in chat");
                    continue;
                }
            }
            else
            {
                System.out.println("Error: Invalid message type");
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

            try
            {
                // create socket
                serverSocket = new Socket(ClientHandler.serverNode.getAddress(), ClientHandler.serverNode.getPort());

                readFromServer = new ObjectInputStream(serverSocket.getInputStream());
                writeToServer = new ObjectOutputStream(serverSocket.getOutputStream());

                // send message of requested type
                if (inputString.startsWith("JOIN"))
                {
                    writeToServer.writeObject(new Message(JOIN, ClientHandler.myNode));
                    joined = true;
                    System.out.println("Joined chat");
                }
                else if (inputString.startsWith("LEAVE"))
                {
                    writeToServer.writeObject(new Message(LEAVE, ClientHandler.myNode));
                    joined = false;
                    System.out.println("Left chat");
                }
                else if (inputString.startsWith("SHUTDOWN"))
                {
                    writeToServer.writeObject(new Message(SHUTDOWN, ClientHandler.myNode));
                    joined = false;
                    System.out.println("Connection shutdown");
                }
                else if (inputString.startsWith("SHUTDOWN_ALL"))
                {
                    writeToServer.writeObject(new Message(SHUTDOWN_ALL, ClientHandler.myNode));
                    joined = false;
                    System.out.println("All connections shutdown");
                }

                serverSocket.close();
            }
            catch (IOException ex)
            {
                System.err.println("Error sending message");
            }

            System.out.println("Joined chat");
        }
    }
}
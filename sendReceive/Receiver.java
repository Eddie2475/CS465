package sendReceive;

import java.io.IOException;
import message.Message;
import message.MessageTypes;
import java.net.Socket;
import clientServer.ClientHandler;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Receiver extends Thread
{
    Message message;
    Socket serverSocket;
    ObjectInputStream readFromNet;
    ObjectOutputStream writeToNet;

    public Receiver()
    {
        try
        {
            serverSocket = new Socket(ClientHandler.serverNode.getAddress(), ClientHandler.serverNode.getPort());

            readFromNet = new ObjectInputStream(serverSocket.getInputStream());
            writeToNet = new ObjectOutputStream(serverSocket.getOutputStream());
        }
        catch (IOException ex)
        {
            System.err.println("Error creating socket and io streams");
        }


    }

    @Override
    public void run()
    {
        try
        {
            // read message
            message = (Message)readFromNet.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            System.err.println("Error reading message");
        }

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

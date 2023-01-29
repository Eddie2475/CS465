package clientServer;

import java.io.Serializable;

public class Node implements Serializable
{
    String address;
    int port;
    String name;

    // server info node
    public Node(String serverAddress, int serverPort)
    {
        address = serverAddress;
        port = serverPort;
        name = null;
    }

    // client info node
    public Node(String clientAddress, int clientPort, String clientName)
    {
        address = clientAddress;
        port = clientPort;
        name = clientName;
    }

    // getter methods
    public String getAddress()
    {
        return address;
    }

    public int getPort()
    {
        return port;
    }

    public String getName()
    {
        return name;
    }
}

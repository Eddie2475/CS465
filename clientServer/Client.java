import java.net.*;
import java.io.*;

public class Client {


    public static void main(String[] args) throws IOException {

        try{
        Socket socket = new Socket("localhost", 5000 );

        DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());

        dOut.writeUTF("Hello Server");
        dOut.flush();
        dOut.close();
        socket.close();
        }
        catch(Exception e) {System.out.println(e);}

        
        // InputStream input = socket.getInputStream();

        // BufferedReader reader = new BufferedReader(new InputStreamReader(input) );

    }
    
}

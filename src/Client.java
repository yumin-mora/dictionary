import java.io.*;
import java.net.*;

public class Client {
    public static String wordentered = null;
    public static String received = null;
    public static int port;

    public static void main(String args[]) {
        port = Integer.parseInt(args[0]);
        String serverName = "localhost";
        String command = "search:";
        try {
            System.out.println("Connecting to " + serverName + " on port " + port);
            //create new socket for the port as single client
            Socket client = new Socket(serverName, port);
            System.out.println("Connected to " + client.getRemoteSocketAddress());
            //reader n writer
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
            //take user input

            //Scanner scanner = new Scanner(System.in);
            String inputStr = "";

            //While the user input differs from "exit"
            while (!(inputStr = wordentered).equals("")) {
                System.out.println(wordentered);

                // Send the input string to the server by writing to the socket output stream
                //send command first
                out.write(command);
                out.write(inputStr + "\n");

                out.flush();
                System.out.println("Message sent");

                // Receive the reply from the server by reading from the socket input stream
                received = in.readLine(); // This method blocks until there  is something to read from the
                // input stream
                //file
                System.out.println("Message received: " + received);
                wordentered = "";
            }

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
/*
for COMP90015 Assignment 1
yumin li 1083371
 */

import java.io.*;
import java.net.*;

public class SimpleClient {
    public static String wordentered = null;
    public static String received = null;
    public static int port;
    public static String serverName;
    public static Socket client;
    public static String errormessage;
    public static BufferedReader in;
    public static BufferedWriter out;
    public static void main(String args[])   {
        try {

            port = Integer.parseInt(args[0]);
            serverName = args[1];
            //System.out.println("Connecting to " + serverName + " on port " + port);
            //create new socket for the port as single client
            Socket client = new Socket(serverName, port);
            //System.out.println("Connected to " + client.getRemoteSocketAddress());
            in = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
        }catch (IOException e) {
            e.printStackTrace();
            errormessage = "IOEception";
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            errormessage = "NumberFormatException";
        }
    }
public String welcomemessage(){
        return "Connected to " + serverName + " on port " + port;
}

public String geterror(){
        return errormessage;

}
    public void seterror(){
        errormessage = null;
    }



    public String getmeaning(String word){

        String command = "search:";
        wordentered = word;
        try {
            String inputStr = "";
            //While the user input differs from "exit"
            while (!(inputStr = wordentered).equals(""))
            {
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
                System.out.println("Message Received: " +  received);
                wordentered = "";
            }

            //client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return received;
    }



    public String add(String word){
        String command = "add:";
        wordentered = word;
        String status = "";
        try {

            String inputStr = "";

            //While the user input differs from "exit"
            while (!(inputStr = wordentered).equals(""))
            {

                // Send the input string to the server by writing to the socket output stream
                //send command first
                out.write(command);
                out.write(inputStr + "\n");

                out.flush();
                System.out.println("Message sent");

                // Receive the reply from the server by reading from the socket input stream
                status = in.readLine(); // This method blocks until there  is something to read from the
                // input stream
                //file
                System.out.println(status);
                wordentered = "";
            }

            // client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return status;
    }

    public String delete(String word){

            String command = "delete:";
            wordentered = word;
            String status = "";
            try {

                String inputStr = "";
                while (!(inputStr = wordentered).equals(""))
                {

                    // Send the input string to the server by writing to the socket output stream
                    //send command first
                    out.write(command);
                    out.write(inputStr + "\n");
                    out.flush();
                    System.out.println("Message sent:" + inputStr);

                    // Receive the reply from the server by reading from the socket input stream
                    status = in.readLine(); // This method blocks until there  is something to read from the
                    // input stream
                    //file
                    System.out.println(status);
                    wordentered = "";
                }






            }catch (IOException e1) {
                e1.printStackTrace();
            }
        return status;
    }
}



/*
for COMP90015 Assignment 1
yumin li 1083371
 */

import java.io.*;
import java.net.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MultiThreadedServer implements Runnable {
    private ServerSocket serverSocket;
    private static int port;

    public MultiThreadedServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                //connection start
                Socket server = serverSocket.accept();
                System.out.println("Connected to " + server.getRemoteSocketAddress());
                //new ClientHandler object been created for client i/o
                //new thread per connection/ request
                Thread thread = new Thread(new ClientHandler(server));
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) {
        //int port = 1234;
        port = Integer.parseInt(args[0]);
        try {
            Thread t = new Thread(new MultiThreadedServer(port));
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
// create and starts its own thread
class ClientHandler implements Runnable {
    private Socket client;
    private String command = "";

    public ClientHandler(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            String inraw;


            while ((inraw = in.readLine()) != null) {
                //search
                System.out.println(inraw);
                command = inraw.split(":")[0];
                String inputLine = inraw.split(":")[1];
                String inputL = inputLine.toLowerCase();
                System.out.println("Received message from client: " + inputL);
                String linefromfileS;
                String linefromfileA;
                String status;

                if (command.equalsIgnoreCase("search")) {
                    try (BufferedReader readDictS = new BufferedReader(new FileReader("DictionaryDatabase.txt"))) {

                        String outmeaning = "";
                        //keeps reading
                        while ((linefromfileS = readDictS.readLine()) != null && !linefromfileS.isEmpty()) {
                            //start of search

                            String[] DictLineS = linefromfileS.split(":");
                            String word = DictLineS[0];
                            String meaning = DictLineS[1];


                            if (inputL.equals(word)) {
                                outmeaning = outmeaning + meaning;
                            }


                        }
                        if (outmeaning == "") {
                            out.println("null");
                        } else {
                            out.println(outmeaning);
                            outmeaning = "";

                        }
                    } catch (IOException e) {
                        System.out.println("Error reading dictionary file.");
                        return;
                    }

                } else if (command.equals("add")) {
                    //System.out.println("add command received");
                    // add functionality from here
                    int duplicate = 0;
                    String writeword = "";
                    String writemean = "";
                    try (BufferedReader readDictA = new BufferedReader(new FileReader("DictionaryDatabase.txt"))) {
                       //start search function here
                        while ((linefromfileA = readDictA.readLine()) != null) {
                            //reads everything and find duplicate
                            String[] DictLineA = linefromfileA.split(":");
                            String word = DictLineA[0];
                            String[] inputLi = inputL.split("/");

                            if (inputLi.length != 2){
                                out.println("Invalid Input");
                            }else{
                                String inputword = inputLi[0];
                                String inputmean = inputLi[1];
                                //valid input from here
                                if (inputword.equalsIgnoreCase(word)){
                                    duplicate = 1;
                                    break;
                                }else{
                                    duplicate = 0;
                                    writeword = inputword;
                                    writemean = inputmean;
                                }
                            }
                        }
                        System.out.println(duplicate);
                        //outside while loop everthing been examied
                        if (duplicate == 0){
                            try {
                                FileWriter mywriter = new FileWriter("DictionaryDatabase.txt", true);
                                mywriter.write(writeword + ":" + writemean + "\n");
                                mywriter.close();
                                status = "Success!";
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                                return;
                            }
                        }else{
                            status = "Duplicated!";
                            duplicate = 0;
                        }

                        //end sear function here
                    out.println(status);

                    }catch (IOException e) {
                        System.out.println("Error reading dictionary file.");
                        return;
                    }

                }
            else if (command.equalsIgnoreCase("delete")){
                //start of delete functionality
                    int found = 0;
                    String linefromfileD;
                    String Linetoremove ="";
                    String statusD = "";
                    try (BufferedReader readDictD = new BufferedReader(new FileReader("DictionaryDatabase.txt"))){
                        while ((linefromfileD = readDictD.readLine()) != null) {
                            String[] DictLineD = linefromfileD.split(":");
                            String word = DictLineD[0];
                            String meaning = DictLineD[1];
                            String wordDel= inputL;
                            if (wordDel.equalsIgnoreCase(word)){
                                found = 1;
                                Linetoremove = word + ":"+meaning;
                                break;
                            }else{
                                found = 0;
                            }
                        }
                        if (found == 1){
                            System.out.println(linefromfileD);
                            //create new file
                            File tempFile = new File("tempFile.txt");
                            FileWriter mywriter = new FileWriter("tempFile.txt", true);
                            String currentLine;
                            BufferedReader reader = new BufferedReader(new FileReader("DictionaryDatabase.txt"));
                            while((currentLine = reader.readLine()) != null) {
                                // trim newline when comparing with lineToRemove
                                String trimmedLine = currentLine.trim();
                                if(trimmedLine.equals(Linetoremove)) continue;
                                mywriter.write(currentLine + System.getProperty("line.separator"));
                            }
                            reader.close();
                            mywriter.close();
                            readDictD.close();
                            Path p = Paths.get("DictionaryDatabase.txt");
                            System.out.println(p);
                            boolean successful = tempFile.renameTo(p.toFile());
                            statusD = "Successful";


                        }else{
                            statusD = "Word Not Found";
                            found = 0;
                        }

                    out.println(statusD);


                    }catch (IOException e) {
                        System.out.println("Error reading dictionary file.");
                        return;
                    }

                //end of delete functionality
                }else{
                out.println("no valid command found");
                }
            }
            client.close();
        }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

/*
for COMP90015 Assignment 1
yumin li 1083371
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFram extends JFrame{
    private JPanel panelMain;
    private JTextField txtWord;
    private JButton searchButton;
    private JLabel EnterTheWordLabel;
    private JButton addbutton;
    private JButton delbutton;
    private JButton updatebutton;
    private JTextField adressfield;
    private JTextField portfield;
    private JButton connectbut;
    private JLabel serlabel;
    private JLabel portlabel;
    private String wordentered;
    private String meaning;
    public static SimpleClient client;
    private String[] port;

public MainFram() throws IOException {

    //port number here
    connectbut.addActionListener(new ActionListener() {
        @Override

        public void actionPerformed(ActionEvent e) {

            String portnumber = portfield.getText();
            String serveraddress = adressfield.getText();
            String[] input = {portnumber, serveraddress};
            SimpleClient client = new SimpleClient();
            client.main(input);
            String error = client.geterror();
            if (error == null) {
                JOptionPane.showMessageDialog(connectbut, client.welcomemessage());

            }else{
                JOptionPane.showMessageDialog(connectbut,error);
                client.seterror();
            }

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    wordentered = txtWord.getText();
                    //SimpleClient client = new SimpleClient();
                    //set client port
                    meaning = client.getmeaning(wordentered);
                    if (meaning.equals("null")) {
                        JOptionPane.showMessageDialog(searchButton,"Sorry, Could Not Find The Given Word In Our Database");
                    }else {
                        JOptionPane.showMessageDialog(searchButton, "The Meaning Of Given Word Is " + meaning);
                    }
                }
            });

            addbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wordentered = txtWord.getText();
                    if (wordentered.contains("/")) {
                        //SimpleClient client = new SimpleClient();
                        String status = client.add(wordentered);
                        JOptionPane.showMessageDialog(addbutton, status);
                    }else{
                        JOptionPane.showMessageDialog(addbutton,"Invalid Command, Try Again!");
                    }
                }
            });


            delbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wordentered = txtWord.getText();
                    //SimpleClient client = new SimpleClient();
                    String status = client.delete(wordentered);
                    JOptionPane.showMessageDialog(delbutton, status);
                }
            });
            updatebutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wordentered = txtWord.getText();
                    if (wordentered.contains("/")) {
                        //SimpleClient client = new SimpleClient();
                        String word = wordentered.split("/")[0];
                        String statustemp = client.delete(word);
                        System.out.println(statustemp);
                        if (statustemp.equals("Successful")) {
                            String status = client.add(wordentered);
                            JOptionPane.showMessageDialog(updatebutton, status);
                        } else {
                            JOptionPane.showMessageDialog(updatebutton, "No Word Found In Database");
                        }
                    }else{
                        JOptionPane.showMessageDialog(updatebutton, "Invalid Command, Try Again!");
                    }
                }
            });

        }

    });


}



public static void main(String[] args) throws IOException {
    MainFram mainFram = new MainFram();
    mainFram.setContentPane(mainFram.panelMain);
    mainFram.setTitle("Dictionary");
    mainFram.setSize(800,300);
    mainFram.setVisible(true);

    mainFram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}



}


package de.tudortmund.cs.rvs.chat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class ConversationListener implements Runnable {

    private InputStream servidor;
    private String partnername;
    private Conversation conversation;

    public ConversationListener(Conversation conversation, String partnerName) {
        try {
            this.servidor = conversation.socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.partnername = partnerName;
        this.conversation = conversation;
    }

    public void run() {

        //listen to incoming messages
        String aux = "";
        Scanner s = new Scanner(this.servidor);
        while (s.hasNextLine()) {

            aux = s.nextLine();

            System.out.println("message from partner: " + aux);
            //if it is a message (starts with "m"), print with senders name
            if (aux.startsWith("n")){
                conversation.setName(aux.substring(2));
                partnername = conversation.getName();
                conversation.addNewConversation();
            }

            if (aux.startsWith("m"))
                System.out.println(partnername + " >> " + aux.substring(2));
            if (aux.startsWith("x"))
                conversation.closeConnection();
            if (aux.startsWith("e"))
                System.out.println("ERROR: " + aux.substring(2));

        }
    }
}
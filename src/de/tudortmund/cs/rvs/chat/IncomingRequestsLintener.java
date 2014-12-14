package de.tudortmund.cs.rvs.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class IncomingRequestsLintener implements Runnable {

    ServerSocket sSocket; //must be closed at the end
    Client client = null;
    boolean terminate = false;

    public IncomingRequestsLintener(Client client, int port) throws IOException {

        this.client = client;

        sSocket = new ServerSocket();

        sSocket.bind(new InetSocketAddress(port));

    }

    public void stopThread(){
        terminate = true;
    }

    @Override
    public void run() {

        while(!terminate){
            {
                try {
                    Conversation newIncomingConversation = new Conversation(client, sSocket.accept());
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("Exception thrown but no one cares! ;P");
                }
                System.out.println("I'm creating a new conversation!!!");

            }

            // how to know the name from new chat partner? We have just a socket :S Thats why echo receives a connection from "null" and
                // after message "n name" server knows how is its partner called.
                // Idea:
        }

    }
}


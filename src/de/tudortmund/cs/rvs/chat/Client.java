package de.tudortmund.cs.rvs.chat;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;


public class Client {

    public Client (String host, int port) throws IOException, InterruptedException {
        this.host = host;
        this.port = port;
        connectServer();
    }

    IncomingRequestsLintener inReq = null;
    private String username;
    private String host;
    private int port;
    public ServerListener r;
    private HashMap<String, Conversation> activeConversations = new HashMap<String, Conversation>();

    Socket serverSocket = null;

/*    public void connect (Socket socket, String host, int port) throws IOException, InterruptedException {

        try {
            socket = new Socket(host, port);
            System.out.println("Connected");
        } catch (Exception e){
            e.printStackTrace();
        }

    }
*/
    public void login (String name) throws IOException {
        username = name;
        PrintStream out = new PrintStream(serverSocket.getOutputStream());
        out.println("n " + name + " 12358");
        showOnlineUsers();

        //start a server sockets waiting for incoming connection requests
        inReq = new IncomingRequestsLintener(this, 12358);
        new Thread(inReq).start();

    }

    public void Disconnect() throws IOException {
        inReq.stopThread();
        closeActiveConversations();
        serverSocket.close();
        inReq.sSocket.close();
        System.out.println("Connection closed!");
    }

    public void showOnlineUsers() throws IOException {
        PrintStream out = new PrintStream(serverSocket.getOutputStream());
        out.println("t");

        //System.out.println(r.getMessage());
    }

    public void connectServer() throws IOException, InterruptedException {

        //connect to adress-book server
        serverSocket = new Socket("127.0.0.1", 2534);

        // thread listening to messages sent by the server
        r = new ServerListener(serverSocket.getInputStream());
        new Thread(r).start();
    }

    public void sendMessage(String name, String message) throws IOException {

        if (activeConversations.containsKey(name))
            activeConversations.get(name).sendMessage(message);
        else {
            Conversation newConversation = new Conversation(this, r.retrieveUser(name));
            //new Thread(newConversation).run();
            activeConversations.put(name, newConversation);
            newConversation.sendMessage(message);
        }

    }

    public void removeConversation(Conversation conversation) throws IOException {
        activeConversations.remove(conversation.getName());
        System.out.println("Conversation closed!");
    }

    public void closeActiveConversations() throws IOException {
        for (Conversation c : activeConversations.values()) {
            c.out.println("x byebye");
            c.socket.close();
        }
    }

    public String getUsername() {
        return username;
    }

    public void addConversation(Conversation conversation){
        activeConversations.put(conversation.getName(), conversation);
    }
}

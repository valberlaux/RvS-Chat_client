package de.tudortmund.cs.rvs.chat;


import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class Conversation {

    private String name;
    Socket socket;
    PrintStream out = null;
    Client client = null;


    public Conversation(Client client, User user) throws IOException {

        try {
            socket = new Socket(user.getHost(), Integer.parseInt(user.getPort()));
            System.out.println("Connected to user " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        name = user.getName();
        this.client = client;
        out = new PrintStream(socket.getOutputStream());

        ConversationListener n = new ConversationListener(this, name);
        new Thread(n).start();

        out.println("n " + client.getUsername());
    }

    public Conversation(Client client, Socket socket) throws IOException {
        this.socket = socket;
        this.client = client;

        out = new PrintStream(socket.getOutputStream());
        ConversationListener n = new ConversationListener(this, null);
        new Thread(n).start();



    }

    public void sendMessage(String message) {
        System.out.println("Sending message> " + message);
        out.println("m " + message);
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    /*
    @Override
    public void run() {

        //listen to incoming messages
        String aux = "";
        Scanner s = null;
        try {
            s = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (s.hasNextLine()) {
            aux = s.nextLine();
            System.out.println(aux);

            //if it is a message (starts with "m"), print with senders name
            /*if (aux.startsWith("m"))
                System.out.println(name + aux.substring(2));
            if (aux.startsWith("x"))
                closeConnection();


        }
    } */

    public void closeConnection() {

        out.println("x byebye");

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            client.removeConversation(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addNewConversation(){
        client.addConversation(this);
    }
}

package de.tudortmund.cs.rvs.chat;

import javax.jws.soap.SOAPBinding;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerListener implements Runnable {
 
    private InputStream servidor;
    private String serverOut;

    private ArrayList<User> users = new ArrayList<User>();

    public ServerListener(InputStream servidor) {
        this.servidor = servidor;
   }

    public void run() {
     // recebe msgs do servidor e imprime na tela
        Scanner s = new Scanner(this.servidor);
        String aux = "";

        while (s.hasNextLine()) {
            aux = s.nextLine();
            if(aux.startsWith("t")){
                users.clear();
                char n = aux.charAt(2);
                int lines = Character.getNumericValue(n);

                for(int i = 0; i < lines; i++){
                    String[] userInfos = s.nextLine().split(" "); // @TODO: dont add old users again
                    users.add(new User(userInfos[0], userInfos[1], userInfos[2]));
                }
                System.out.println("Online users:");
                for(User usr : users)
                    System.out.println(usr);


            }
            //serverOut = s.nextLine();
        }
    }

    public User retrieveUser(String name){
        for(User usr : users)
            if (usr.getName().equals(name)) return usr;
        return null;
    }

 }
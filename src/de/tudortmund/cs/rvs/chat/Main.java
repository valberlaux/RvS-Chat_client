package de.tudortmund.cs.rvs.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        Client client = new Client("127.0.0.1", 2534);

        /*try {
            client.connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
*/

        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        PrintStream out = new PrintStream(client.serverSocket.getOutputStream());

        String msg = "";

        while (!msg.equals("x")) {
            // cria linha para digitação da mensagem e a armazena na variavel msg
            System.out.print("Enter something > ");

            msg = teclado.readLine();

            String[] userimput = msg.split(" ");


            if (userimput[0].equals("login"))
                    client.login(userimput[1]);
                    //@TODO check server answear

            if (userimput[0].equals("whoisonline"))
                    client.showOnlineUsers();

            if (userimput[0].equals("@"))
                client.sendMessage(userimput[1], msg.substring((msg.indexOf(' ', 2))+1));

            if (userimput[0].equals("quit"))
                client.closeActiveConversations();


            /*switch (userimput[0]) {

                case "login":
                    client.login(userimput[1]);
                    //@TODO check server answear
                    break;

                case "whoisonline":
                    client.showOnlineUsers();
                    break;

                default:
                    System.out.println("Unknow command");
            }
            */


            // envia a mensagem para o servidor
        }

        out.close();
        teclado.close();


        client.Disconnect();

        System.out.println("Exiting...");

    }
}

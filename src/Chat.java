import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Chat {
    ArrayList<Client> clients = new ArrayList<>();
    ServerSocket serverSocket;
    Date date = new Date();

    Chat() {
        try {
            // создаем серверный сокет на порту 1234
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.out.println("Connection error. Cant create server socket");
            e.printStackTrace();
        }
    }

    public void startChat(){
        while(true) {
            System.out.println("Waiting...");
            // ждем клиента из сети

            try {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                // создаем клиента на своей стороне
                String name = Client.getName();
                clients.add(new Client(socket, this, name));

            } catch (IOException e) {
                System.out.println("Connection error. Acceptance is not allowed");
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String msg){
        for (Client client : clients) {
            client.receiveMsg("Wrote in " + date + ": " + msg);
        }
    }

    public static void main(String[] args) {
        new Chat().startChat();

    }
}
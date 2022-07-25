import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    Chat chat;
    static String name;

    public Client(Socket socket, Chat chat, String name){
        this.socket = socket;
        this.chat = chat;
        this.name = String.valueOf(name);
        new Thread(this).start();
    }

    public static String getName(){
        return name;
    }

    Scanner in;
    PrintStream out;

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            out.println("What is your nick name?");
            name = in.nextLine();
            setName(name);
            out.println("Ok, " + name + ", its time to chat. Lets start.");
            String input = in.nextLine();

            while (!input.equals("bye")) {
                chat.sendMsg(input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setName(String name) {
        this.name = name;
    }

    public void receiveMsg(String msg){
        out.println(msg);
        out.println("");
    }
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class TestDrive {


    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new TestDrive().run();
    }
    private void run () {

        try (ServerSocket serverSocket = new ServerSocket(9988)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, this);
                this.clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized void sendMassage (String massage, String name){
        ArrayList<ClientHandler> clientsTempList = this.clients;
        for (ClientHandler clientHandler: clientsTempList){
            Socket socket = clientHandler.getSocket();
            try {
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                writer.printf("%s пишет: %s\n", name, massage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

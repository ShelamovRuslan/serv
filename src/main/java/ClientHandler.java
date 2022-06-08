import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private String name;
    TestDrive testDrive;
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public TestDrive getTestDrive() {
        return testDrive;
    }

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler(Socket socket, TestDrive testDrive) {
        this.socket = socket;
        this.testDrive = testDrive;
    }
@Override
    public void run () {
        String line;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
                writer.println("Hello, what is your name?");
                setName(reader.readLine());
                do {
                    line = reader.readLine();
                   this.testDrive.sendMassage(line, getName());
                } while (true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

    }


}

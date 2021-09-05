import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[]  args){
        new Main();
    }

    private Main(){
        ServerSocket servSoc = null;
        try {
            servSoc = new ServerSocket(4400);
        } catch (IOException e) {
            System.out.println("Failed to start the server");
            e.printStackTrace();
            System.out.println("Closing the server...");
            System.exit(0);
        }

        System.out.println("Server running");
        System.out.println("Awaiting connections...");
        System.out.println("---------------");
        while(true){
            try {
                Socket clientSocket = servSoc.accept();
                new Connection(clientSocket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


import java.io.IOException;
import java.net.Socket;

public class Main {

    public static void main (String[] args){
        new Main(args);
    }

    private Main(String[] args){
        int port;
        String ip, operation, filePath;

        ip = args[0];
        port = Integer.parseInt(args[1]);
        operation = args[2];
        filePath = args[3];

        System.out.println(ip + " " + port + " " + operation + " " + filePath);

        Socket soc;
        try {
            System.out.println("Connecting to the server...");
            soc = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("Could not connect to the server");
            e.printStackTrace();
            System.out.println("Stopping the client");
            return;
        }

        System.out.println("Connected to " + soc.getRemoteSocketAddress().toString());

        if(operation.equalsIgnoreCase("-u")){
            System.out.println("Configuring the upload");
            new Upload(soc, filePath);
        }
        else if(operation.equalsIgnoreCase("-d")){
            System.out.println("Configuring the download");
            new Download(soc, filePath);
        }
        else{
            System.out.println("Invalid argument " +operation);
        }
    }
}

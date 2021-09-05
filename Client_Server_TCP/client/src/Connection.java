import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

abstract class Connection {

    private Socket soc;
    PrintStream ps;
    String filename;

    Connection(Socket soc, String filePath, String operation){
        this.soc = soc;
        filename = getFilePathFileName(filePath);

//        set up the the stream used for sending arguments
        try {
            ps = new PrintStream(soc.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to configure the connection");
            e.printStackTrace();
        }

//        send arguments
        ps.println(operation);
        ps.println(filename);
    }

    private String getFilePathFileName(String filepath){
        String[] str = filepath.split("/");
        return str[str.length-1];
    }

    public void connectionClose(){
        ps.flush();
        ps.close();

        try {
            soc.close();
            System.out.println("Connection closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

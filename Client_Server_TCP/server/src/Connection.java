import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;

public class Connection extends Thread {

    private Socket soc;
    Connection(Socket soc){
        this.soc = soc;
    }

    @Override
    public void run(){
//        <<<<<<<<<<<<<<<<<<<< SETUP >>>>>>>>>>>>>>>>>>>>
        String str = "Received a connection from: "
                + soc.getRemoteSocketAddress().toString();
        System.out.println(str);

//        Get arguments
        String operation = null;
        String filename = null;

        DataInputStream dis = null;
        try {
            dis = new DataInputStream(soc.getInputStream());
            operation = dis.readLine();
            filename = dis.readLine();

        } catch (IOException e) {
            System.out.println("Failed to initialize the DataInputStream");
            e.printStackTrace();
        }
//        <<<<<<<<<<<<<<<<<<<< UPLOAD>>>>>>>>>>>>>>>>>>>>

//        Upload request from client
        assert operation != null;
        if(operation.equalsIgnoreCase("u")){
            System.out.println("Upload request for " + filename);

//            Create new file
            File f = new File("../files/"+filename);

//            Set up FileInputStream
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                System.out.println("Failed to set up the FileOutputStream");
                e.printStackTrace();
            }

            System.out.println("Receiving the file from " + soc.getRemoteSocketAddress().toString());
//            Set up the InputStream for receiving the file
            try {
                assert fos != null;

                byte[] bytes = new byte[8*1024];
                int count;
                while ((count = dis.read(bytes)) > 0) {
                    fos.write(bytes, 0, count);
                }

                System.out.println("File " + filename + " received");
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

//        <<<<<<<<<<<<<<<<<<<< DOWNLOAD >>>>>>>>>>>>>>>>>>>>

//        Download request from client
        else if(operation.equalsIgnoreCase("d")){
            System.out.println("Download request for " + filename);

//        set up the the stream
        PrintStream ps = null;
        try {
            ps = new PrintStream(soc.getOutputStream());
        } catch (IOException e) {
            System.out.println("Failed to configure the PrintStream");
            e.printStackTrace();
        }

//        read the file
            File f = new File("../files/"+filename);
            FileInputStream fip = null;
            try {
                fip = new FileInputStream(f);
            } catch (FileNotFoundException e) {
                System.out.println("Did not find the file");
                e.printStackTrace();
            }

            System.out.println("Sending the file to " + soc.getRemoteSocketAddress().toString());
//        Send the file
            try {
                assert fip != null;

                ps.println(fip.available());
                byte[] bytes = new byte[8*1024];
                int count;
                while ((count = fip.read(bytes)) > 0) {
                    ps.write(bytes, 0, count);
                }
                fip.close();

                System.out.println("File " + filename + " sent");
            } catch (IOException e) {
                System.out.println("Failed to read the file");
                e.printStackTrace();
            }

//        close stuff
            ps.flush();
            ps.close();
        }
        else{
            System.out.println("Incorrect request");
        }

//        <<<<<<<<<<<<<<<<<<<< CLOSE CONNECTION >>>>>>>>>>>>>>>>>>>>
        try {
            dis.close();
            soc.close();
            System.out.println("Connection " + soc.getRemoteSocketAddress().toString() + " closed");
            System.out.println("---------------");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

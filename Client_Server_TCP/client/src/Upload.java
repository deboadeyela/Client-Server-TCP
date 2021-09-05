import java.io.*;
import java.net.Socket;

class Upload extends Connection {

    Upload(Socket soc, String filePath) {
        super(soc, filePath, "u");

//        Read the file
        File f = new File(filePath);
        FileInputStream fip = null;
        try {
            fip = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            System.out.println("Did not find the file");
            e.printStackTrace();
            System.out.println("Closing the client");
            System.exit(0);
        }

//        Send the file to the server
        try {
            System.out.println("Uploading " +filename);

            byte[] bytes = new byte[8 * 1024];
            float fileSize = fip.available();

            int progress = 0;
            int count;
            while ((count = fip.read(bytes)) > 0) {
                ps.write(bytes, 0, count);

                progress += (count / fileSize) * 100;
                System.out.println(progress + "%");

            }

            System.out.println("File " +filename + " uploaded");
        } catch (IOException e) {
            System.out.println("Failed to upload " + filename);
            e.printStackTrace();
        }

//        Close the connection
        connectionClose();
    }
}

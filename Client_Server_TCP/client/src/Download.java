import java.io.*;
import java.net.Socket;

class Download extends Connection{

    Download(Socket soc, String filePath){
        super(soc, filePath, "d");

//        Create a new file
        File f = new File("../files/"+filename);

//        Set up FileOutputStream - outputs to the file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to set up the FileOutputStream");
            e.printStackTrace();
        }

//        Download the file
        DataInputStream dis;
        try {
            assert fos != null;

            System.out.println("Downloading "+filename);

//            Set up the DataInputStream for downloading the file
//            from the server
            dis = new DataInputStream(soc.getInputStream());

            byte[] bytes = new byte[8*1024];
            float fileSize = Float.parseFloat(dis.readLine());

            int progress=0;
            int count;
            while ((count = dis.read(bytes)) > 0) {
                fos.write(bytes, 0, count);

                progress += (count/fileSize) * 100;
                System.out.println(progress +"%");
            }

            System.out.println("File " + filename + " downloaded");
            dis.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Failed to download " + filename);
            e.printStackTrace();
        }

//        Close the connection
        connectionClose();
    }
}

package com.kademika.day8.frame21.IOServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by dean on 6/23/15.
 */
public class CleanClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("localhost", 7777);

        try (
                OutputStream os = socket.getOutputStream()
                ) {
            os.write("R".getBytes());
            for (int i=0; i<3;i++) {
                os.write("F".getBytes());
                os.write("M".getBytes());
            }
            os.write("L".getBytes());
            for (int i=0; i<5;i++) {
                os.write("F".getBytes());
            }
            os.write("U".getBytes());
            for (int i=0; i<8;i++) {
                os.write("F".getBytes());
            }
            for (int i=0; i<8;i++) {
                os.write("M".getBytes());
                os.write("L".getBytes());
                for (int j=0; j<8;j++) {
                    os.write("F".getBytes());
                }
                os.write("U".getBytes());
            }
        }

    }

}

package com.kademika.day8.frame21.NIOServer;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.tanks.AbstractTank;
import com.kademika.day8.frame21.interfaces.Drawable;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by dean on 7/28/15.
 */
public class Client {

    BattleField bf;
    AbstractTank agressor;
    AbstractTank defender;
    Socket socket;
    JPanel panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            draw(bf, g);
            draw(agressor,g);
            draw(defender,g);
            draw(defender.getBullet(), g);
            draw(agressor.getBullet(), g);
        }
    };

    public Client() {
        JFrame frame = new JFrame("Tanks");
        frame.setContentPane(panel);
        frame.setBounds(0, 0, 1200, 800);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void connect() throws IOException {
        socket = new Socket("localhost", 9191);
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void init() {

        try( InputStream is = socket.getInputStream();
             BufferedInputStream bis = new BufferedInputStream(is);
             DataInputStream dis = new DataInputStream(bis)
//             ObjectInputStream ois = new ObjectInputStream(bais)
            ) {
            System.out.println(dis.readLong());

//            bf = (BattleField) ois.readObject();
//            agressor = (AbstractTank) ois.readObject();
//            defender = (AbstractTank) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private void draw(Drawable object, Graphics g) {
        if (object != null) {
            object.draw(g);
        }
    }
}

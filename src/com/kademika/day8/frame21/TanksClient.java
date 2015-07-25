package com.kademika.day8.frame21;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.tanks.BT7;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.BattleField.objects.tanks.T34;
import com.kademika.day8.frame21.BattleField.objects.tanks.Tiger;
import com.kademika.day8.frame21.interfaces.Tank;

import java.io.*;
import java.net.Socket;

/**
 * Created by dean on 6/25/15.
 */
public class TanksClient {

    BattleField bf;
    Socket socket;
    Tank tank1;
    Tank tank2;

    public void connect() throws IOException {
        socket = new Socket("localhost", 7777);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        final ObjectInputStream ois = new ObjectInputStream(in);
        try {
            bf = (BattleField) ois.readObject();
           new Thread(new Runnable() {
               @Override
               public void run() {
                   try {
                       Tank testTank1 = (Tank) ois.readObject();
                       if (testTank1.getClass().getSimpleName().equals("T43")) {
                           tank1 = new T34(bf, testTank1.getX(),
                                   testTank1.getY(), Direction.DOWN);
                       } else if (testTank1.getClass().getSimpleName().equals("Tiger")) {
                           tank1 = new Tiger(bf, testTank1.getX(),
                                   testTank1.getY(), Direction.DOWN);
                       } else if (testTank1.getClass().getSimpleName().equals("BT7")) {
                           tank1 = new BT7(bf, testTank1.getX(),
                                   testTank1.getY(), Direction.DOWN);
                       }
                      Tank testTank2 = (Tank) ois.readObject();
                       if (testTank2.getClass().getSimpleName().equals("T34")) {
                           tank2 = new T34(bf, testTank2.getX(),
                                   testTank2.getY(), Direction.DOWN);
                       } else if (testTank2.getClass().getSimpleName().equals("Tiger")) {
                           tank2 = new Tiger(bf, testTank2.getX(),
                                   testTank2.getY(), Direction.DOWN);
                       } else if (testTank2.getClass().getSimpleName().equals("BT7")) {
                           tank2 = new BT7(bf, testTank2.getX(),
                                   testTank2.getY(), Direction.DOWN);
                       }
                   } catch (IOException e) {
                       e.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }
               }
           }).start();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
    } //finally {
//            ois.close();
//        }
//        while (true) {
//
//        }
    }

    public void connect(String host) throws IOException {
        socket = new Socket(host, 7777);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        ObjectInputStream ois = new ObjectInputStream(in);
        try {
            bf = (BattleField) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } //finally {
//            ois.close();
//        }
    }

    public BattleField getBf() {
        return bf;
    }

    public void setBf(BattleField bf) {
        this.bf = bf;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Tank getTank1() {
        return tank1;
    }

    public void setTank1(Tank tank1) {
        this.tank1 = tank1;
    }

    public Tank getTank2() {
        return tank2;
    }

    public void setTank2(Tank tank2) {
        this.tank2 = tank2;
    }
}

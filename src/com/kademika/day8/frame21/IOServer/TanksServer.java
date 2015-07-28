package com.kademika.day8.frame21.IOServer;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.interfaces.Tank;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dean on 6/25/15.
 */
public class TanksServer {

    private static  final BattleField bf = new BattleField();;
    Tank agressor;
    Tank defender;
    ServerSocket ss;

    public final static TanksServer SERVER = new TanksServer();

    public TanksServer() {
    }

    public void start() throws IOException {

        ss = new ServerSocket(7777);

        while (true) {
            final Socket socket = ss.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Connection from " + socket);
                    try (
                            InputStream in = socket.getInputStream();
                            OutputStream out = socket.getOutputStream();
                    ) {
                        ObjectOutputStream oos = new ObjectOutputStream(out);
                        oos.writeObject(bf);
//                        new Thread(new Runnable() {
//
//                            @Override
//                            public void run() {
                        int i=0;
                        int j=0;
                                while (true)
                                {
                                    if (agressor != null && i == 0) {
                                        try {
                                            System.out.println("agressor: " + agressor.getClass().getSimpleName());
                                            oos.writeObject(agressor);
                                            i++;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else if (defender != null && j == 0) {
                                        try {
                                            System.out.println("defender: " + defender.getClass().getSimpleName());
                                            oos.writeObject(defender);
                                            j++;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else if (agressor != null && defender != null) {
                                        break;
                                    }
                                }
//
//                    }).start();
//                        oos.flush();
//                        oos.close();
                        int data;
                        while ((data = in.read()) != -1) {
                            out.write(data);
                        }
                    } catch (IOException e) {
                        System.out.println("Connection problem - " + e);
                    }
                }
            }).start();
        }

    }

    public void stop() throws IOException {
        ss.close();
    }

    public BattleField getBf() {
        return bf;
    }

    public ServerSocket getSs() {
        return ss;
    }

    public void setSs(ServerSocket ss) {
        this.ss = ss;
    }

    public Tank getAgressor() {
        return agressor;
    }

    public void setAgressor(Tank agressor) {
        this.agressor = agressor;
    }

    public Tank getDefender() {
        return defender;
    }

    public void setDefender(Tank defender) {
        this.defender = defender;
    }
}

package com.kademika.day8.frame21;

import com.kademika.day8.frame21.BattleField.objects.tanks.AbstractTank;
import com.kademika.day8.frame21.interfaces.Tank;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by dean on 6/23/15.
 */
public class TankSimpleServer {

    static ActionField af;

    public static void main(String[] args) throws IOException {

        af = new ActionField("server");
        AbstractTank tank = af.getDefender();
        ServerSocket ss = new ServerSocket(7777);

        while (true) {

            Socket socket = ss.accept();

        try (
                BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                ) {
            int data;
            char ch;
            while ((data = bis.read()) != -1) {
                ch = (char) data;

                    af.processAction(tank, ch);
            }

        }
    }
    }
}

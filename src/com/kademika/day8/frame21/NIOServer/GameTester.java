package com.kademika.day8.frame21.NIOServer;

import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;

import java.io.IOException;

/**
 * Created by dean on 7/28/15.
 */
public class GameTester {

    public static void main(String[] args) {
        final Server server = new Server(11,11);

          new Thread() {
              @Override
              public void run() {

                      server.start();

              }
          }.start();

        Client c = new Client();
        try {
            c.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        c.init();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        c.panel.repaint();

        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        c.defender.turn(Direction.DOWN);

//        c.panel.repaint();

    }

}

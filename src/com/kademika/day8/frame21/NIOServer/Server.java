package com.kademika.day8.frame21.NIOServer;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.tanks.AbstractTank;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.BattleField.objects.tanks.T34;
import com.kademika.day8.frame21.BattleField.objects.tanks.Tiger;
import com.kademika.day8.frame21.interfaces.Tank;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 * Created by dean on 7/28/15.
 */
public class Server {

    int dimentionX;
    int dimentionY;
    BattleField bf;
    AbstractTank defender;
    AbstractTank agressor;
    private Random randCoordinate = new Random();

    public Server(int dimentionX, int dimentionY) {
        this.dimentionX = dimentionX;
        this.dimentionY = dimentionY;
        bf = new BattleField(dimentionX,dimentionY);
        defender = new T34(bf, (bf.getQuadrantsX() / 2 + 1) * 64, (bf.getQuadrantsY() - 1) * 64, Direction.UP);
        agressor = new Tiger(bf, randCoordinate.nextInt(bf.getQuadrantsX() - 1) * 64,
                (randCoordinate.nextInt(bf.getQuadrantsY()/2)) * 64, Direction.DOWN);
        bf.updateQuadrant(agressor.getY() / 64, agressor.getX() / 64, null);
        bf.updateQuadrant(defender.getY() / 64, defender.getX() / 64, null);
    }

    public void start()/* throws IOException*/ {

        Vector<SocketChannel> connections = new Vector<>();
        Selector selector = null;
        try {
        ServerSocketChannel ssc = ServerSocketChannel.open();

            ssc.bind(new InetSocketAddress(9191));

        ssc.configureBlocking(false);

        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {

            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = keySet.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();
              /*  if (key.isValid() && defender != null && agressor != null) {
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = channel.accept();
                        sc.configureBlocking(false);
                        connections.add(sc);
                        sc.register(key.selector(), SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                        int read = sc.read(buffer);
                        if (read == -1) {
                            continue;
                        }
                        buffer.flip();
                        for(int i=0; i< buffer.limit(); i++) {
                            buffer.put(i, buffer.get(i));
                        }
                        for (Iterator<SocketChannel> iterChannel =
                                     connections.iterator(); iterChannel.hasNext();) {
                            SocketChannel channel = iterChannel.next();
                            if ( channel.socket().isClosed()) {
                                channel.close();
                                iterChannel.remove();
                            } else {
                                try {
                                channel.write(buffer);
                                buffer.rewind();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
                } else*/ if (key.isValid()) {
                    if (key.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = null;
                        try {
                            sc = channel.accept();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            sc.configureBlocking(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        connections.add(sc);
                        try {
                            sc.register(key.selector(), SelectionKey.OP_WRITE);
                        } catch (ClosedChannelException e) {
                            e.printStackTrace();
                        }
                    } else if (key.isWritable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        Socket soc = sc.socket();

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                        ObjectOutputStream oos = new ObjectOutputStream(bos);
//                        oos.writeObject(bf);
//                        oos.flush();
//                        oos.writeObject(agressor);
//                        oos.writeObject(defender);
                        byte[] bytes = bos.toByteArray();
                        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                        buffer.putLong(102003100312193l);
//                        buffer.wrap(bytes);
                        try {
                            sc.write(buffer);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Fuck");
                        }
                        try {
                            sc.register(key.selector(), SelectionKey.OP_WRITE);
                        } catch (ClosedChannelException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

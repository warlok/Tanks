package com.kademika.day8.frame21.NIOServer;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.tanks.AbstractTank;
import com.kademika.day8.frame21.interfaces.Tank;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
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

    public Server(int dimentionX, int dimentionY) {
        this.dimentionX = dimentionX;
        this.dimentionY = dimentionY;
        bf = new BattleField(dimentionX,dimentionY);
    }

    public void start() throws IOException {

        Vector<SocketChannel> connections = new Vector<>();
        Selector selector;

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(9191));
        ssc.configureBlocking(false);

        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            selector.select();
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
                        SocketChannel sc = channel.accept();
                        sc.configureBlocking(false);
                        connections.add(sc);
                        sc.register(key.selector(), SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                        int read = sc.read(buffer);

                        if (read == 0) {
//                            defender = new
                        } else if (read == 1) {

                        } else {
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
                }
            }
        }
    }
}

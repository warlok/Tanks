package com.kademika.day8.frame21.IOServer;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.tanks.*;
import com.kademika.day8.frame21.interfaces.Drawable;
import com.kademika.day8.frame21.interfaces.Tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Random;

/**
 * Created by dean on 6/24/15.
 */
public class UI  {

    private JFrame gameFrame = new JFrame("BATTLE CITY NEW");
    private Random randCoordinate = new Random();
    private JPanel serverPanel;
    private JPanel serverClientPanel;
    private JPanel clientPanel1;
    private JPanel clientPanel2;
    private JPanel gamePanel;
    private JPanel startPanel;
    private TanksServer server = TanksServer.SERVER;;
    private TanksClient client;
    private Tank tank1;
    private Tank tank2;


    public UI() {

        drawFirstWindow();

    }

    private void drawFirstWindow() {
        startPanel = new JPanel(new BorderLayout());
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        final JRadioButton serverButton = new JRadioButton("Start game server", true);
        final JRadioButton clientButton = new JRadioButton("Start client program");
        radioPanel.add(serverButton);
        radioPanel.add(clientButton);
        final JButton next = new JButton("Next");
        startPanel.add(radioPanel, BorderLayout.CENTER);
        startPanel.add(next, BorderLayout.AFTER_LAST_LINE);
        startPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gameFrame.setContentPane(startPanel);
        gameFrame.setLocation(100, 100);
        gameFrame.setMinimumSize(new Dimension(300,
                300));
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!serverButton.isSelected()) {
                    serverButton.setSelected(true);
                }
                clientButton.setSelected(false);
            }
        });

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!clientButton.isSelected()) {
                    clientButton.setSelected(true);
                }
                serverButton.setSelected(false);
            }
        });

        next.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (serverButton.isSelected()) {
                     drawServerWindow();
                } else if (clientButton.isSelected()) {
                    drawFirstClientWindow();
                }

            }
    });

 }

    private void drawServerWindow() {
        serverPanel = new JPanel(new BorderLayout());
        JPanel dimentionPanel = new JPanel(new GridLayout(2,2));
        serverPanel.add(dimentionPanel, BorderLayout.CENTER);
        JButton start = new JButton("Start Server");
        serverPanel.add(start, BorderLayout.AFTER_LAST_LINE);
        JLabel labelX = new JLabel("X Dimention: ");
        JLabel labelY = new JLabel("Y Dimention: ");
        final JTextField dimentionX = new JTextField("9", 5);
        final JTextField dimentionY = new JTextField("9", 5);
        dimentionPanel.add(labelX, 0);
        dimentionPanel.add(dimentionX, 1);
        dimentionPanel.add(labelY, 2);
        dimentionPanel.add(dimentionY, 3);
        gameFrame.setContentPane(serverPanel);
        gameFrame.validate();
        startPanel = null;

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        BattleField bf = server.getBf();
                        bf.setQuadrantsXY(Integer.valueOf(dimentionX.getText()), Integer.valueOf(dimentionY.getText()));
                        bf.generateBattleFieldString();
//                        bf.generateBattleField();

                        try {
                            server.start();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }).start();
                drawSeverClientWindow();
            }
        });
    }

    private void drawSeverClientWindow() {
        serverClientPanel = new JPanel(new BorderLayout());
        JButton connect = new JButton("Connect to server");
        JButton close = new JButton("Stop server");
        serverClientPanel.add(connect, BorderLayout.CENTER);
        serverClientPanel.add(close, BorderLayout.AFTER_LAST_LINE);
        gameFrame.setContentPane(serverClientPanel);
        gameFrame.validate();
        serverPanel = null;

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client = new TanksClient();
                try {
                    client.connect();
                    if (client.getSocket().isClosed()) {
                       JFrame dialogFrame = new JFrame();
                        JOptionPane.showMessageDialog(dialogFrame,
                                "Server is Down, try to restart it");
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                client.getBf().generateBattleField();
                            }
                        }).start();
                        drawSecondClientWindow();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!server.getSs().isClosed()) {
                        server.stop();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                drawFirstWindow();
            }
        });
    }

    private void drawFirstClientWindow() {
        clientPanel1 = new JPanel(new BorderLayout());
        JPanel dimentionPanel = new JPanel(new GridLayout(2,2));
        clientPanel1.add(dimentionPanel, BorderLayout.PAGE_START);
        JButton connect = new JButton("Connect");
        clientPanel1.add(connect, BorderLayout.AFTER_LAST_LINE);
        JLabel labelX = new JLabel("Enter IP address of server: ");
        final JTextField host = new JTextField("127.0.0.1", 5);
        dimentionPanel.add(labelX, 0);
        dimentionPanel.add(host, 1);
        gameFrame.setContentPane(clientPanel1);
        gameFrame.validate();
        serverClientPanel = null;

        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client = new TanksClient();
                try {
                    client.connect(host.getText());

                    if (client.getSocket().isClosed()) {
                        JFrame dialogFrame = new JFrame();
                        JOptionPane.showMessageDialog(dialogFrame,
                                "Server is Down, try to restart it");
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                client.getBf().generateBattleField();
                            }
                        }).start();
                        drawSecondClientWindow();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void drawSecondClientWindow() {
        clientPanel2 = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(2,2));
        JButton start = new JButton("Start Game");
        final JRadioButton bt7Button = new JRadioButton("BT7", true);
        final JRadioButton tigerButton = new JRadioButton("Tiger");
        final JRadioButton t34Button = new JRadioButton("T34");
        final ImageIcon icon1 = new ImageIcon("BT7_up.png");
        final ImageIcon icon2 = new ImageIcon("tiger_up.png");
        final ImageIcon icon3 = new ImageIcon("T34_up.png");
        final JLabel picture = new JLabel();
        picture.setIcon(icon1);
        JPanel radioPanel1 = new JPanel(new GridLayout(3, 3));
        radioPanel1.add(bt7Button,0);
        radioPanel1.add(tigerButton,1);
        radioPanel1.add(t34Button,2);
        final JRadioButton agressorButton = new JRadioButton("Agressor attacs a base", true);
        final JRadioButton defenderButton = new JRadioButton("Defender saves a base");
        panel.add(radioPanel1);
        panel.add(picture);
        panel.add(agressorButton);
        panel.add(defenderButton);
        clientPanel2.add(panel, BorderLayout.LINE_START);
        clientPanel2.add(start, BorderLayout.AFTER_LAST_LINE);
        gameFrame.setContentPane(clientPanel2);
        gameFrame.pack();
        gameFrame.validate();
        serverClientPanel = null;
        clientPanel1 = null;

        bt7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!bt7Button.isSelected()) {
                    bt7Button.setSelected(true);
                }
                picture.setIcon(icon1);
                tigerButton.setSelected(false);
                t34Button.setSelected(false);
            }
        });

        tigerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tigerButton.isSelected()) {
                    tigerButton.setSelected(true);
                }
                picture.setIcon(icon2);
                bt7Button.setSelected(false);
                t34Button.setSelected(false);
            }
        });

        t34Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t34Button.isSelected()) {
                    t34Button.setSelected(true);
                }
                picture.setIcon(icon3);
                bt7Button.setSelected(false);
                tigerButton.setSelected(false);
            }
        });

        agressorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!agressorButton.isSelected()) {
                    agressorButton.setSelected(true);
                }
                defenderButton.setSelected(false);
            }
        });

        defenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!defenderButton.isSelected()) {
                    defenderButton.setSelected(true);
                }
                agressorButton.setSelected(false);
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (agressorButton.isSelected() && server.getAgressor() == null) {
                    if (t34Button.isSelected()) {
                        server.setAgressor(new T34(server.getBf(), randCoordinate.nextInt(server.getBf().getQuadrantsX() - 1) * 64,
                                (randCoordinate.nextInt(server.getBf().getQuadrantsY()/2)) * 64, Direction.DOWN));
                    } else if (tigerButton.isSelected()) {
                        server.setAgressor(new Tiger(server.getBf(), randCoordinate.nextInt(server.getBf().getQuadrantsX() - 1) * 64,
                                (randCoordinate.nextInt(server.getBf().getQuadrantsY()/2)) * 64, Direction.DOWN));
                    } else if (bt7Button.isSelected()) {
                        server.setAgressor(new BT7(server.getBf(), randCoordinate.nextInt(server.getBf().getQuadrantsX() - 1) * 64,
                                (randCoordinate.nextInt(server.getBf().getQuadrantsY()/2)) * 64, Direction.DOWN));
                    }
                    drawGameWindow();
                } else if (defenderButton.isSelected() && server.getDefender() == null) {
                    if (t34Button.isSelected()) {
                        server.setDefender(new T34(server.getBf(), (server.getBf().getQuadrantsX() / 2 + 1) * 64,
                                (server.getBf().getQuadrantsY() - 1) * 64, Direction.UP));
                    } else if (tigerButton.isSelected()) {
                        server.setDefender(new Tiger(server.getBf(), (server.getBf().getQuadrantsX() / 2 + 1) * 64,
                                (server.getBf().getQuadrantsY() - 1) * 64, Direction.UP));
                    } else if (bt7Button.isSelected()) {
                        server.setDefender(new BT7(server.getBf(), (server.getBf().getQuadrantsX() / 2 + 1) * 64,
                                (server.getBf().getQuadrantsY() - 1) * 64, Direction.UP));
                    }
                    drawGameWindow();
                } else {
                    JFrame dialogFrame = new JFrame();
                    JOptionPane.showMessageDialog(dialogFrame,
                            "This role alredy exist, chose another one");
                }

            }
        });
    }

    private void drawGameWindow() {

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                draw(client.getBf(), g);
                draw(client.getTank1(), g);
                draw(client.getTank2(), g);
                if (client.getTank1() != null) {
                    draw(client.getTank1().getBullet(), g);
                }
                if (client.getTank2() != null) {
                    draw(client.getTank2().getBullet(), g);
                }


            }
        };
        gameFrame.setContentPane(gamePanel);
        gameFrame.setMinimumSize(new Dimension(client.getBf().getBF_WIDTH(),
                client.getBf().getBF_HEIGHT() + 22));
        gameFrame.pack();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    gamePanel.repaint();
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        gameFrame.setVisible(true);
        clientPanel2 = null;
    }

    private void draw(Drawable object, Graphics g) {
        if (object != null) {
            object.draw(g);
        }
    }
}

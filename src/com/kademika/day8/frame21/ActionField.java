package com.kademika.day8.frame21;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import javax.swing.*;
import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.AbstractObjects;
import com.kademika.day8.frame21.BattleField.objects.Eagle;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.BattleField.objects.tanks.Action;
import com.kademika.day8.frame21.BattleField.objects.tanks.BT7;
import com.kademika.day8.frame21.BattleField.objects.tanks.AbstractTank;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.BattleField.objects.tanks.T34;
import com.kademika.day8.frame21.interfaces.Tank;
import com.kademika.day8.frame21.BattleField.objects.tanks.Tiger;
import com.kademika.day8.frame21.BattleField.objects.tanks.bullet.Bullet;

public class ActionField {

    private JTextField dimentionX;
    private JTextField dimentionY;
    private Icon icon1;
    private Icon icon2;
    private JLabel picture;
    private JButton go;
    private JRadioButton bt7Button;
    private JRadioButton tigerButton;
    private JFrame gameFrame = new JFrame("BATTLE FIELD, DAY 8");
    private JPanel mainPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            bf.draw(g);
            agressor.draw(g);
            defender.draw(g);
            bul.draw(g);
        }
    };
    JPanel startPanel;
    private com.kademika.day8.frame21.BattleField.BattleField bf;
    private T34 defender;
    private Bullet bul;
    private AbstractTank agressor;
    private Random randCoordinate = new Random();
    private File logFile = new File("TanksLog");
    private BattleField savedBattlefield;
    private AbstractTank savedAgressor;
    private T34 savedDefender;
    private KeyListener keyboardListener = new KeyAdapter() {

        @Override
        public void keyPressed(KeyEvent e) {
            defender.setKey(KeyEvent.getKeyText(e.getKeyCode()));
            System.out.println("keyPressed=" + KeyEvent.getKeyText(e.getKeyCode()));
        }
    };

    public void replayGame() {
        try (
                FileInputStream inputStream = new FileInputStream(logFile);
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(reader);
        ) {
            String actionFromFile="";

            while ( (actionFromFile = br.readLine()) != null) {

                if (actionFromFile != null) {
                    processAction(agressor, Action.valueOf(actionFromFile));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    actionFromFile = br.readLine();
                if (actionFromFile != null) {
                    processAction(defender, Action.valueOf(actionFromFile));
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameOver();
        System.out.println("Game Over");
    }

    public void runTheGame() {

        try (
                FileOutputStream outputStream = new FileOutputStream(logFile);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream);
                BufferedWriter bw = new BufferedWriter(writer);
        ) {
        // processAction(defender, bul, defender.setupTank());
        while (!agressor.isDestroed() && !defender.isDestroed() && bf.scanQuadrant(bf.getQuadrantsY() - 1, findEagle()) != null) {

                Action agressorAction = agressor.setupTank();
                Action defenderAction = defender.setupTank();
                processAction(agressor, agressorAction);
                bw.write(agressorAction.toString() + "\n");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                processAction(defender, defenderAction);
                bw.write(defenderAction.toString() + "\n");
//                try {
//                    Thread.sleep(50);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameOver();
        System.out.println("Game Over");
    }

    public void processAction(Tank tank, Action a)  {
        switch (a) {
            case MOVE:
                processMove(tank);
                break;
            case MOVE_UP:
            processTurn(tank, Direction.UP);
            break;
            case MOVE_DOWN:
                processTurn(tank, Direction.DOWN);
                processMove(tank);
                break;
            case MOVE_LEFT:
                processTurn(tank,Direction.LEFT);
                processMove(tank);
                break;
            case MOVE_RIGHT:
                processTurn(tank,Direction.RIGHT);
                processMove(tank);
                break;
            case TURN_UP:
                processTurn(tank, Direction.UP);
                processMove(tank);
                break;
            case TURN_DOWN:
                processTurn(tank, Direction.DOWN);
                break;
            case TURN_LEFT:
                processTurn(tank,Direction.LEFT);
                break;
            case TURN_RIGHT:
                processTurn(tank,Direction.RIGHT);
                break;
            case FIRE:
                processFire(tank);
                break;
            default:
        }
    }

    public int findEagle() {
        for (int i = 0; i < bf.getQuadrantsX(); i++) {
            if (bf.scanQuadrant(bf.getQuadrantsY() - 1, i) instanceof Eagle) {
                return i;
            }
        }
        return -1;
    }

    public void processMove(Tank tank)  {
        int step = 1;
        int covered = 0;
        Direction direction = tank.getDirection();

        // check limits x: 0, 513; y: 0, 513
        if ((tank.getDirection() == Direction.UP && tank.getY() == 0)
                || (tank.getDirection() == Direction.DOWN && tank.getY() >= bf.getBF_HEIGHT()-64)
                || (tank.getDirection() == Direction.LEFT && tank.getX() == 0)
                || (tank.getDirection() == Direction.RIGHT && tank.getX() >= bf.getBF_WIDTH()-64)) {
            System.out.println("[illegal move] direction: "
                    + tank.getDirection() + " tankX: " + tank.getX()
                    + ", tankY: " + tank.getY());
            return;
        }

        tank.turn(direction);

        if (processInterceptionTank(tank)) {
            return;
        }

        while (covered < 64) {

            if (processInterceptionBetweenTanks(tank)) {
                return;
            }

            if (tank.getDirection() == Direction.UP) {

                tank.updateY(-step);
            } else if (tank.getDirection() == Direction.DOWN) {

                tank.updateY(step);
            } else if (tank.getDirection() == Direction.LEFT) {

                tank.updateX(-step);
            } else {

                tank.updateX(step);
            }

            covered += step;

            try {
                Thread.sleep(tank.getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void processTurn(Tank tank, Direction direction) {
        tank.turn(direction);
    }

    public void processFire(Tank tank) {

        bul.destroy();
        bul.setTank(tank);
        bul.updateX(tank.getX() + 125);
        bul.updateY(tank.getY() + 125);
        bul.setDirection(tank.getDirection());
        final int step2 = 1;

        new Thread() {
            @Override
            public void run() {
                while (bul.getBulletX() > 0 && bul.getBulletY() > 0
                        && bul.getBulletX() < bf.getBF_WIDTH() && bul.getBulletY() < bf.getBF_HEIGHT()) {

                    int step = 0;

                    while (step < 64) {

                        if (bul.getDirection().equals(Direction.UP)) {
                            bul.updateY(-step2);
                        } else if (bul.getDirection().equals(Direction.DOWN)) {
                            bul.updateY(step2);
                        } else if (bul.getDirection().equals(Direction.LEFT)) {
                            bul.updateX(-step2);
                        } else {
                            bul.updateX(step2);
                        }
                        step++;
                        if (processInterceptionBullet() || bul.getBulletX() < 0
                                && bul.getBulletY() < 0 || bul.getBulletX() > bf.getBF_WIDTH()
                                || bul.getBulletY() > bf.getBF_HEIGHT()) {
                            bul.destroy();
                        }

                        try {
                            Thread.sleep(bul.getSpeed());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();



    }

    private boolean processInterceptionBullet() {

        String koordinate = getQuadrant(bul.getBulletX(), bul.getBulletY());
        int delim = koordinate.indexOf("_");
        int elemY = Integer.parseInt(koordinate.substring(0, delim));
        int elemX = Integer.parseInt(koordinate.substring(delim + 1));

        if (elemY >= 0 && elemX >= 0 && elemY < bf.getQuadrantsY() && elemX < bf.getQuadrantsX()) {

            if (getQuadrant(bul.getBulletX(), bul.getBulletY()).equals(getQuadrant(agressor.getX(), agressor.getY()))
                    && !(agressor.equals(bul.getTank()))) {
                agressor.destroy();

                return true;
            } else if (getQuadrant(bul.getBulletX(), bul.getBulletY()).equals(getQuadrant(defender.getX(), defender.getY()))
                    && !(defender.equals(bul.getTank()))) {
                defender.destroy();

                return true;
            } else if (bf.scanQuadrant(elemY, elemX) != null && !(bf.scanQuadrant(elemY, elemX) instanceof Water)) {

                if (bf.scanQuadrant(elemY, elemX) instanceof AbstractObjects) {
                    AbstractObjects o = (AbstractObjects) bf.scanQuadrant(
                            elemY, elemX);
                    o.destroy();
                    bf.updateQuadrant(elemY, elemX, null);

                    return true;
                }

            }

        }
        return false;
    }

    private boolean processInterceptionBetweenTanks(Tank tank) {

        return tank.tanksInterception();

    }

    private boolean processInterceptionTank(Tank tank) {

        return tank.interception();
    }

    String getQuadrant(int x, int y) {
        String result = bf.getQuadrantsY() + "_" + bf.getQuadrantsX();
        if (x < bf.getBF_WIDTH() && x > 0 && y < bf.getBF_HEIGHT() && y > 0) {
            int quadrantX = x / 64;
            int quadrantY = y / 64;
            result = quadrantY + "_" + quadrantX;
        }
        return result;
    }

    public void gameOver() {

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(255, 19, 31));
                g.fillRect(0,0,1024,1024);
            }
        };
        panel.setLayout(new GridBagLayout());

        JButton button = new JButton("Try Again");
        button.setSize(20, 40);
        JLabel lable = new JLabel();
        lable.setText("GAME OVER");
        lable.setSize(100, 100);
        lable.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));

        JButton buttonReplay = new JButton("Replay Game");
        buttonReplay.setSize(20,40);

        panel.add(button, new GridBagConstraints(0, 1, 10, 10, 0, 0,
                GridBagConstraints.LINE_START, 0, new Insets(0, 94, 0, 0), 0, 0));
        panel.add(buttonReplay, new GridBagConstraints(0, 1, 10, 10, 0, 0,
                GridBagConstraints.LINE_START, 0, new Insets(0, 80, 50, 0), 0, 0));
        panel.add(lable, new GridBagConstraints(0, 0, 1, 1, 0, 0,
                GridBagConstraints.LINE_START, 0, new Insets(0, 0, 0, 0), 0, 0));
        gameFrame.setContentPane(panel);
        gameFrame.pack();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.setContentPane(startPanel);
                gameFrame.pack();
                gameFrame.pack();
            }
        });

        buttonReplay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bf.setBattleFieldString(savedBattlefield.getBattleFieldString());
                bf.generateBattleField();
                if (savedAgressor instanceof Tiger) {
                    agressor = new Tiger(bf, savedAgressor.getX(), savedAgressor.getY(), savedAgressor.getDirection());
                } else {
                    agressor = new BT7(bf, savedAgressor.getX(), savedAgressor.getY(), savedAgressor.getDirection());
                }
                defender = new T34(bf, savedDefender.getX(),savedDefender.getY(),savedDefender.getDirection());
                agressor.setEnemy(defender);
                defender.setEnemy(agressor);
                bf.updateQuadrant(agressor.getY() / 64, agressor.getX() / 64, null);
                bf.updateQuadrant(defender.getY() / 64, defender.getX() / 64, null);
                gameFrame.setContentPane(mainPanel);
                gameFrame.pack();
                mainPanel.requestFocus();
                new Thread() {
                    @Override
                    public void run() {
                        replayGame();
                    }
                }.start();
            }
        });
    }


    public ActionField() {

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    gameFrame.repaint();
                    try {
                        sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        drawGUI();
    }

    public void drawGUI() {
        bt7Button = new JRadioButton("BT7 faster tank", true);
        tigerButton = new JRadioButton("Tiger with good armor");
        go = new JButton("GO");
        icon1 = new ImageIcon("BT7_up.png");
        icon2 = new ImageIcon("tiger_up.png");
        picture = new JLabel();
        picture.setIcon(icon1);

        startPanel = new JPanel(new BorderLayout());
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        JPanel dimentionPanel = new JPanel();

        bf = new BattleField();
        bf.generateBattleField();
        bul = new Bullet(-100, -100, Direction.LEFT);

        JLabel labelX = new JLabel("X Dimention: ");
        JLabel labelY = new JLabel("Y Dimention: ");
        dimentionX = new JTextField("9", 5);
        dimentionY = new JTextField("9", 5);
        dimentionPanel.add(labelX);
        dimentionPanel.add(dimentionX);
        dimentionPanel.add(labelY);
        dimentionPanel.add(dimentionY);

        radioPanel.add(bt7Button);
        radioPanel.add(tigerButton);

        startPanel.add(dimentionPanel, BorderLayout.BEFORE_FIRST_LINE);
        startPanel.add(radioPanel, BorderLayout.LINE_START);
        startPanel.add(picture, BorderLayout.CENTER);
        startPanel.add(go, BorderLayout.AFTER_LAST_LINE);
        startPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gameFrame.setContentPane(startPanel);
        gameFrame.setLocation(1200, 100);
        gameFrame.setMinimumSize(new Dimension(bf.getBF_WIDTH(),
                bf.getBF_HEIGHT() + 22));
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.pack();
        gameFrame.setVisible(true);

        bt7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setIcon(icon1);
                tigerButton.setSelected(false);
            }
        });

        tigerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setIcon(icon2);
                bt7Button.setSelected(false);
            }
        });

        go.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {


                bf.setQuadrantsXY(Integer.valueOf(dimentionX.getText()), Integer.valueOf(dimentionY.getText()));
                bf.generateBattleField();

                defender = new T34(bf, (bf.getQuadrantsX() / 2 + 1) * 64, (bf.getQuadrantsY() - 1) * 64, Direction.UP);

                if (tigerButton.isSelected()) {
                    agressor = new Tiger(bf, randCoordinate.nextInt(bf.getQuadrantsX() - 1) * 64,
                            (randCoordinate.nextInt(bf.getQuadrantsY()/2)) * 64, Direction.DOWN);
                } else {
                    agressor = new BT7(bf, randCoordinate.nextInt(bf.getQuadrantsX() - 1) * 64,
                            (randCoordinate.nextInt(bf.getQuadrantsY()/2)) * 64, Direction.DOWN);
                }
                agressor.setEnemy(defender);
                defender.setEnemy(agressor);
                bf.updateQuadrant(agressor.getY() / 64, agressor.getX() / 64, null);
                bf.updateQuadrant(defender.getY() / 64, defender.getX() / 64, null);
                if (agressor instanceof Tiger) {
                    savedAgressor = new Tiger(bf, agressor.getX(), agressor.getY(), agressor.getDirection());
                } else {
                    savedAgressor = new BT7(bf, agressor.getX(), agressor.getY(), agressor.getDirection());
                }
                savedDefender = new T34(bf, defender.getX(),defender.getY(), defender.getDirection());
                savedBattlefield = new BattleField();
                savedBattlefield.setQuadrantsXY(Integer.valueOf(dimentionX.getText()),
                        Integer.valueOf(dimentionY.getText()));
                savedBattlefield.setBattleFieldString(bf.getBattleFieldString());
                mainPanel.setFocusable(true);
                mainPanel.addKeyListener(keyboardListener);
                gameFrame.setContentPane(mainPanel);
                gameFrame.setMinimumSize(new Dimension(bf.getBF_WIDTH(),
                        bf.getBF_HEIGHT() + 22));
                gameFrame.pack();
                gameFrame.setVisible(true);
                mainPanel.requestFocus();
                new Thread() {
                    @Override
                    public void run() {
                        runTheGame();
                    }
                }.start();


            }

        });
    }

}

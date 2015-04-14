package com.kademika.day8.frame21;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.swing.tree.ExpandVetoException;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.AbstractObjects;
import com.kademika.day8.frame21.BattleField.objects.Eagle;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.BattleField.objects.tanks.Action;
import com.kademika.day8.frame21.BattleField.objects.tanks.BT7;
import com.kademika.day8.frame21.BattleField.objects.tanks.AbstractTank;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.BattleField.objects.tanks.T34;
import com.kademika.day8.frame21.BattleField.objects.tanks.Tank;
import com.kademika.day8.frame21.BattleField.objects.tanks.Tiger;
import com.kademika.day8.frame21.BattleField.objects.tanks.bullet.Bullet;
import sun.rmi.runtime.NewThreadAction;

public class ActionField extends JPanel {

    private JFrame frame = new JFrame("BATTLE FIELD, DAY 8");
    private JPanel mainPanel = this;
    private com.kademika.day8.frame21.BattleField.BattleField bf;
    private T34 defender;
    private Bullet bul;
    private AbstractTank agressor;
    private Random randCoordinate = new Random();

    public void runTheGame() {
        // processAction(defender, bul, defender.setupTank());
        while (!agressor.isDestroed() && !defender.isDestroed() && bf.scanQuadrant(bf.getQuadrantsY()-1, findEagle()) != null) {
            try {
                processAction(agressor, bul, agressor.setupTank());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                processAction(defender, bul, defender.setupTank());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        gameOver();
        System.out.println("Game Over");


    }

    public void processAction(Tank tank, Bullet b, Action a) throws Exception {
        switch (a) {
            case MOVE:
                processMove(tank);
                break;
            case TURN:
                processTurn(tank);
                break;
            case FIRE:
                processFire(tank);
                break;
            default:
        }
    }

    public int findEagle() {
        for (int i=0; i<bf.getQuadrantsX();i++) {
            if (bf.scanQuadrant(bf.getQuadrantsY() - 1, i) instanceof Eagle) {
                return i;
            }
        }
        return -1;
    }

    public void processMove(Tank tank) throws Exception {
        int step = 1;
        int covered = 0;
        Direction direction = tank.getDirection();
        String tankKoordinate = getQuadrant(tank.getX(), tank.getY());
        int delim = tankKoordinate.indexOf("_");
        int elemTankY = Integer.parseInt(tankKoordinate.substring(0, delim));
        int elemTankX = Integer.parseInt(tankKoordinate.substring(delim + 1));
//		bf.updateQuadrant(elemTankY, elemTankX, tank);

        // check limits x: 0, 513; y: 0, 513
        if ((tank.getDirection() == Direction.UP && tank.getY() == 0)
                || (tank.getDirection() == Direction.DOWN && tank.getY() >= bf.getBF_HEIGHT())
                || (tank.getDirection() == Direction.LEFT && tank.getX() == 0)
                || (tank.getDirection() == Direction.RIGHT && tank.getX() >= bf.getBF_WIDTH())) {
            System.out.println("[illegal move] direction: "
                    + tank.getDirection() + " tankX: " + tank.getX()
                    + ", tankY: " + tank.getY());
            return;
        }

        tank.turn(direction);
        if (processTankInterception(tank)) {
//            processFire(tank, bul);
            System.out.println(tank.getClass() + " Illegal move");
            return;
        }

        while (covered < 64) {

            if (processTanksInterception(tank)) {
//                processFire(tank, bul);
                System.out.println(tank.getClass() + " Killing Enemy");
                return;
            }
            bf.scanQuadrant(elemTankY, elemTankX);

            if (tank.getDirection() == Direction.UP) {

                tank.updateY(-step);
                System.out.println("[move up] direction: "
                        + tank.getDirection() + " tankX: " + tank.getX()
                        + ", tankY: " + tank.getY());
            } else if (tank.getDirection() == Direction.DOWN) {

                tank.updateY(step);
                System.out.println("[move down] direction: "
                        + tank.getDirection() + " tankX: " + tank.getX()
                        + ", tankY: " + tank.getY());
            } else if (tank.getDirection() == Direction.LEFT) {

                tank.updateX(-step);
                System.out.println("[move left] direction: "
                        + tank.getDirection() + " tankX: " + tank.getX()
                        + ", tankY: " + tank.getY());
            } else {

                tank.updateX(step);
                System.out.println("[move right] direction: "
                        + tank.getDirection() + " tankX: " + tank.getX()
                        + ", tankY: " + tank.getY());
            }

            covered += step;
            repaint();
            Thread.sleep(tank.getSpeed());
        }

    }

    public void processTurn(Tank tank) throws Exception {
        repaint();
    }

    public void processFire(Tank tank) throws Exception {

        bul.destroy();
        bul.setTank(tank);
        bul.updateX(tank.getX() + 125);
        bul.updateY(tank.getY() + 125);
        bul.setDirection(tank.getDirection());
        int step2 = 1;

        while (bul.getBulletX() > 0 && bul.getBulletY() > 0
                && bul.getBulletX() < bf.getBF_WIDTH() && bul.getBulletY() < bf.getBF_HEIGHT()) {

            int step = 0;

            while (step < 64) {

                if (tank.getDirection().equals(Direction.UP)) {
                    bul.updateY(-step2);
                    System.out.println("[fire up] direction: "
                            + tank.getDirection() + " bulletY: "
                            + bul.getBulletY() + ", bulletX: "
                            + bul.getBulletX());
                } else if (tank.getDirection().equals(Direction.DOWN)) {
                    bul.updateY(step2);
                    System.out.println("[fire down] direction: "
                            + tank.getDirection() + " bulletY: "
                            + bul.getBulletY() + ", bulletX: "
                            + bul.getBulletX());
                } else if (tank.getDirection().equals(Direction.LEFT)) {
                    bul.updateX(-step2);
                    System.out.println("[fire left] direction: "
                            + tank.getDirection() + " bulletY: "
                            + bul.getBulletY() + ", bulletX: "
                            + bul.getBulletX());
                } else {
                    bul.updateX(step2);
                    System.out.println("[fire right] direction: "
                            + tank.getDirection() + " bulletY: "
                            + bul.getBulletY() + ", bulletX: "
                            + bul.getBulletX());
                }
                step++;
                if (processInterception() || bul.getBulletX() < 0
                        && bul.getBulletY() < 0 || bul.getBulletX() > bf.getBF_WIDTH()
                        || bul.getBulletY() > bf.getBF_HEIGHT()) {
                    bul.destroy();
                }
                repaint();
                Thread.sleep(bul.getSpeed());
            }
        }

    }

    private boolean processInterception() /* throws Exception */ {

        String koordinate = getQuadrant(bul.getBulletX(), bul.getBulletY());
        int delim = koordinate.indexOf("_");
        int elemY = Integer.parseInt(koordinate.substring(0, delim));
        int elemX = Integer.parseInt(koordinate.substring(delim + 1));

        if (elemY >= 0 && elemX >= 0 && elemY < bf.getQuadrantsY() && elemX < bf.getQuadrantsX()) {

            if (getQuadrant(bul.getBulletX(), bul.getBulletY()).equals(getQuadrant(agressor.getX(), agressor.getY()))
                    && !(agressor.equals(bul.getTank()))) {
                agressor.destroy();
                repaint();
                return true;
            } else if (getQuadrant(bul.getBulletX(), bul.getBulletY()).equals(getQuadrant(defender.getX(), defender.getY()))
                    && !(defender.equals(bul.getTank()))) {
                defender.destroy();
                repaint();
                return true;
            } else if (bf.scanQuadrant(elemY, elemX) != null && !(bf.scanQuadrant(elemY, elemX) instanceof Water)) {

                if (bf.scanQuadrant(elemY, elemX) instanceof AbstractObjects) {
                    AbstractObjects o = (AbstractObjects) bf.scanQuadrant(
                            elemY, elemX);
                    o.destroy();
                    bf.updateQuadrant(elemY, elemX, null);
                    repaint();
                    return true;
                }

            }

        }
        return false;
    }

    private boolean processTanksInterception(Tank tank) {

        if (getQuadrant(agressor.getX(), agressor.getY()).equals(getQuadrant(defender.getX(), defender.getY()))) {
            return true;
        }

        return false;
    }

    private boolean processTankInterception(Tank tank) {

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

    String getQuadrantXY(int v, int h) {
        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public JPanel getPane() {
        return this;
    }

    public void gameOver() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(new Font(Font.MONOSPACED,Font.BOLD,36));
                g.drawString("Game Over",200,100);
            }
        };
        JButton button = new JButton("Try Again");
        panel.add(button);
        frame.setContentPane(panel);
//        frame.getRootPane().add(panel);
        frame.pack();
        frame.repaint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });
    }

    public void restartGame() {
        bf = new BattleField();
        bf.generateBattleField();

        defender = new T34(bf, (bf.getQuadrantsX()/2+1)*64, (bf.getQuadrantsY()-1)*64, Direction.UP);
        defender.setEnemy(agressor);
        agressor = new BT7(bf, randCoordinate.nextInt(bf.getQuadrantsX()-1)*64, (randCoordinate.nextInt(bf.getQuadrantsY()-2))*64, Direction.DOWN);
        agressor.setEnemy(defender);

        bul = new Bullet(-100, -100, Direction.LEFT);
//        runTheGame();
        bf.updateQuadrant(agressor.getY() / 64, agressor.getX() / 64, null);
        bf.updateQuadrant(defender.getY()/64,defender.getX()/64,null);
        frame.setContentPane(mainPanel);
        frame.pack();
        frame.repaint();
//        runTheGame();
    }


    public ActionField() throws Exception {

        final JLayeredPane layeredPane = new JLayeredPane();
        final JRadioButton bt7Button = new JRadioButton("BT7 faster tank",true);
        final JRadioButton tigerButton = new JRadioButton("Tiger with good armor");
        JButton go = new JButton("Go");
        final JButton start = new JButton("Start Game");
        start.setVisible(true);
        final Icon icon1 = new ImageIcon("BT7_up.png");
        final Icon icon2 = new ImageIcon("tiger_up.png");
        final JLabel picture = new JLabel();

        JPanel panel = new JPanel(new BorderLayout());
        final JPanel mainPanel = this;
        mainPanel.setLayout(new FlowLayout());
        mainPanel.add(start);
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));

        layeredPane.add(panel,0);

        picture.setIcon(icon1);

        bf = new BattleField();
        bf.generateBattleField();

        defender = new T34(bf, (bf.getQuadrantsX()/2+1)*64, (bf.getQuadrantsY()-1)*64, Direction.UP);
        defender.setEnemy(agressor);
        agressor = new BT7(bf, randCoordinate.nextInt(bf.getQuadrantsX()-1)*64, (randCoordinate.nextInt(bf.getQuadrantsY()-2))*64, Direction.DOWN);
        agressor.setEnemy(defender);

        bul = new Bullet(-100, -100, Direction.LEFT);

        bf.updateQuadrant(agressor.getY() / 64, agressor.getX() / 64, null);
        bf.updateQuadrant(defender.getY()/64,defender.getX()/64,null);

        radioPanel.add(bt7Button);
        radioPanel.add(tigerButton);
        panel.add(radioPanel,BorderLayout.LINE_START);
        panel.add(picture, BorderLayout.CENTER);
        panel.add(go,BorderLayout.AFTER_LAST_LINE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        frame.setContentPane(layeredPane);
        frame.setLocation(1300, 100);
        frame.setMinimumSize(new Dimension(bf.getBF_WIDTH(),
                bf.getBF_HEIGHT() + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        bt7Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setIcon(icon1);
                tigerButton.setSelected(false);
                agressor = new BT7(bf, randCoordinate.nextInt(bf.getQuadrantsX()-1)*64, (randCoordinate.nextInt(bf.getQuadrantsY()-2))*64, Direction.DOWN);
                agressor.setEnemy(defender);
                bf.updateQuadrant(agressor.getY() / 64,agressor.getX()/64,null);
            }
        });

        tigerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                picture.setIcon(icon2);
                bt7Button.setSelected(false);
                agressor = new Tiger(bf, randCoordinate.nextInt(bf.getQuadrantsX()-1)*64, (randCoordinate.nextInt(bf.getQuadrantsY()-2))*64, Direction.DOWN);
                agressor.setEnemy(defender);
                bf.updateQuadrant(agressor.getY() / 64,agressor.getX()/64,null);
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
//                start.invalidate();
                layeredPane.add(mainPanel,2);
                runTheGame();
            }
        });

        go.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.setContentPane(mainPanel);
                frame.repaint();
                frame.pack();
                repaint();
//                runTheGame();
            }
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bf.draw(g);
        agressor.draw(g);
        defender.draw(g);
        bul.draw(g);

    }

}

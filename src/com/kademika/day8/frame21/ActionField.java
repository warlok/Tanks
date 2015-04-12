package com.kademika.day8.frame21;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

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

public class ActionField extends JPanel {

    private com.kademika.day8.frame21.BattleField.BattleField bf;
    private Tiger defender;
    private Bullet bul;
    private BT7 agressor;
    Random randCoordinate = new Random();

    public void runTheGame() throws Exception {
        // processAction(defender, bul, defender.setupTank());
        while (!agressor.isDestroed() && !defender.isDestroed() && bf.scanQuadrant(bf.getQuadrantsY()-1, findEagle()) != null) {
                processAction(agressor, bul, agressor.setupTank());
//				Thread.sleep(1000);
                processAction(defender, bul, defender.setupTank());
//				Thread.sleep(1000);
        }
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

    public ActionField() throws Exception {
        bf = new com.kademika.day8.frame21.BattleField.BattleField();
        bf.generateBattleField();
        defender = new Tiger(bf, randCoordinate.nextInt(bf.getQuadrantsX()-1)*64, (randCoordinate.nextInt(bf.getQuadrantsY()-1))*64, Direction.UP);
        agressor = new BT7(bf, randCoordinate.nextInt(bf.getQuadrantsX()-1)*64, (randCoordinate.nextInt(bf.getQuadrantsY()-1))*64, Direction.DOWN);
        agressor.setEnemy(defender);
        defender.setEnemy(agressor);
        bf.updateQuadrant(defender.getY()/64,defender.getX()/64,null);
        bf.updateQuadrant(agressor.getY() / 64,agressor.getX()/64,null);
        bul = new Bullet(-100, -100, Direction.LEFT);
        JFrame frame = new JFrame("BATTLE FIELD, DAY 8");
        frame.setLocation(100, 0);
        frame.setMinimumSize(new Dimension(bf.getBF_WIDTH(),
                bf.getBF_HEIGHT() + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
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

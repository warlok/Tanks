package com.kademika.day8.frame21.BattleField.objects.tanks;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.interfaces.Destroyable;
import com.kademika.day8.frame21.interfaces.Drawable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BT7 extends AbstractTank {

    BufferedImage img_left = null;
    BufferedImage img_right = null;
    BufferedImage img_up = null;
    BufferedImage img_down = null;

    public BT7(BattleField bf) {
        super(bf);
        speed = super.speed / 2;
        loadImage();
    }

    public BT7(BattleField bf, int x, int y, Direction direction) {
        super(bf, x, y, direction);
        speed = super.speed / 2;
        loadImage();
    }

    protected void loadImage() {
        try {
            img_left = ImageIO.read(new File("BT7_left.png"));

        } catch (IOException e) {
            System.err.println("Couldn't load image");
        }
        try {
            img_right = ImageIO.read(new File("BT7_right.png"));

        } catch (IOException e) {
            System.err.println("Couldn't load image");
        }
        try {
            img_up = ImageIO.read(new File("BT7_up.png"));

        } catch (IOException e) {
            System.err.println("Couldn't load image");
        }
        try {
            img_down = ImageIO.read(new File("BT7_down.png"));

        } catch (IOException e) {
            System.err.println("Couldn't load image");
        }
    }

    @Override
    public Action setupTank() throws Exception {

        if (x < 4 * 64) {
            while (x != 4 * 64) {
                if (direction != Direction.RIGHT) {
                    turn(Direction.RIGHT);
                    return Action.TURN;
                }
                if (interception()) {
                    return Action.FIRE;
                } else {
                    return Action.MOVE;
                }
            }
        } else {
            while (x != 4 * 64) {
                if (direction != Direction.LEFT) {
                    turn(Direction.LEFT);
                    return Action.TURN;
                }
                if (interception()) {
                    return Action.FIRE;
                } else {
                    return Action.MOVE;
                }
            }
        }

        if (y < 8 * 64) {
            while (y != 9 * 64) {
                if (direction != Direction.DOWN) {
                    turn(Direction.DOWN);
                    return Action.TURN;
                }
                if (interception()) {
                    return Action.FIRE;
                } else {
                    return Action.MOVE;
                }
            }
        } else {
            while (y != 9 * 64) {
                if (direction != Direction.UP) {
                    turn(Direction.UP);
                    return Action.TURN;
                }
                if (interception()) {
                    return Action.FIRE;
                } else {
                    return Action.MOVE;
                }
            }
        }
        return Action.NOTHING;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage img = null;
        if (this.getDirection() == Direction.UP) {
            img = img_up;
        } else if (this.getDirection() == Direction.DOWN) {
            img = img_down;
        } else if (this.getDirection() == Direction.LEFT) {
            img = img_left;
        } else {
            img = img_right;
        }
        g.drawImage(img, x, y, 64, 64, null);
    }
}

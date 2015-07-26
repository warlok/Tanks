package com.kademika.day8.frame21.BattleField.objects;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by User on 26.07.2015.
 */
public class Images {

    private BufferedImage imgWater = null;
    private BufferedImage imgBrick = null;
    private BufferedImage imgRock = null;
    private BufferedImage imgEagle = null;
    private BufferedImage imgBulletLeft = null;
    private BufferedImage imgBulletRight = null;
    private BufferedImage imgBulletUp = null;
    private BufferedImage imgBulletDown = null;
    private BufferedImage imgT34Left = null;
    private BufferedImage imgT34Right = null;
    private BufferedImage imgT34tUp = null;
    private BufferedImage imgT34Down = null;
    private BufferedImage imgTigerLeft = null;
    private BufferedImage imgTigerRight = null;
    private BufferedImage imgTigerUp = null;
    private BufferedImage imgTigerDown = null;
    private BufferedImage imgBT7Left = null;
    private BufferedImage imgBT7Right = null;
    private BufferedImage imgBT7Up = null;
    private BufferedImage imgBT7Down = null;

    {
        try {
            imgWater = ImageIO.read(new File("water4.png"));
            imgEagle = ImageIO.read(new File("eagle.png"));
            imgRock = ImageIO.read(new File("url.png"));
            imgBrick = ImageIO.read(new File("Brick_Block.png"));
            imgBulletLeft = ImageIO.read(new File("bullet_left.png"));
            imgBulletRight = ImageIO.read(new File("bullet_right.png"));
            imgBulletUp = ImageIO.read(new File("bullet_up.png"));
            imgBulletDown = ImageIO.read(new File("bullet_down.png"));
            imgBT7Left = ImageIO.read(new File("BT7_left.png"));
            imgBT7Right = ImageIO.read(new File("BT7_right.png"));
            imgBT7Up = ImageIO.read(new File("BT7_up.png"));
            imgBT7Down = ImageIO.read(new File("BT7_down.png"));
            imgT34Left = ImageIO.read(new File("T34_left.png"));
            imgT34Right = ImageIO.read(new File("T34_right.png"));
            imgT34tUp = ImageIO.read(new File("T34_up.png"));
            imgT34Down = ImageIO.read(new File("T34_down.png"));
            imgTigerLeft = ImageIO.read(new File("tiger_left.png"));
            imgTigerRight = ImageIO.read(new File("tiger_right.png"));
            imgTigerUp = ImageIO.read(new File("tiger_up.png"));
            imgTigerDown = ImageIO.read(new File("tiger_down.png"));
        } catch (IOException e) {
            System.err.println("Couldn't load image");
        }
    }

    public BufferedImage getImgWater() {
        return imgWater;
    }

    public BufferedImage getImgBrick() {
        return imgBrick;
    }

    public BufferedImage getImgRock() {
        return imgRock;
    }

    public BufferedImage getImgEagle() {
        return imgEagle;
    }

    public BufferedImage getImgBulletLeft() {
        return imgBulletLeft;
    }

    public BufferedImage getImgBulletRight() {
        return imgBulletRight;
    }

    public BufferedImage getImgBulletUp() {
        return imgBulletUp;
    }

    public BufferedImage getImgBulletDown() {
        return imgBulletDown;
    }

    public BufferedImage getImgT34Left() {
        return imgT34Left;
    }

    public BufferedImage getImgT34Right() {
        return imgT34Right;
    }

    public BufferedImage getImgT34tUp() {
        return imgT34tUp;
    }

    public BufferedImage getImgT34Down() {
        return imgT34Down;
    }

    public BufferedImage getImgTigerLeft() {
        return imgTigerLeft;
    }

    public BufferedImage getImgTigerRight() {
        return imgTigerRight;
    }

    public BufferedImage getImgTigerUp() {
        return imgTigerUp;
    }

    public BufferedImage getImgTigerDown() {
        return imgTigerDown;
    }

    public BufferedImage getImgBT7Left() {
        return imgBT7Left;
    }

    public BufferedImage getImgBT7Right() {
        return imgBT7Right;
    }

    public BufferedImage getImgBT7Up() {
        return imgBT7Up;
    }

    public BufferedImage getImgBT7Down() {
        return imgBT7Down;
    }
}

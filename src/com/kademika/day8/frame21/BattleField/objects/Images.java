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
            imgWater = ImageIO.read(getClass().getResourceAsStream("/water4.png"));
            imgEagle = ImageIO.read(getClass().getResourceAsStream("/eagle.png"));
            imgRock = ImageIO.read(getClass().getResourceAsStream("/url.png"));
            imgBrick = ImageIO.read(getClass().getResourceAsStream("/Brick_Block.png"));
            imgBulletLeft = ImageIO.read(getClass().getResourceAsStream("/bullet_left.png"));
            imgBulletRight = ImageIO.read(getClass().getResourceAsStream("/bullet_right.png"));
            imgBulletUp = ImageIO.read(getClass().getResourceAsStream("/bullet_up.png"));
            imgBulletDown = ImageIO.read(getClass().getResourceAsStream("/bullet_down.png"));
            imgBT7Left = ImageIO.read(getClass().getResourceAsStream("/BT7_left.png"));
            imgBT7Right = ImageIO.read(getClass().getResourceAsStream("/BT7_right.png"));
            imgBT7Up = ImageIO.read(getClass().getResourceAsStream("/BT7_up.png"));
            imgBT7Down = ImageIO.read(getClass().getResourceAsStream("/BT7_down.png"));
            imgT34Left = ImageIO.read(getClass().getResourceAsStream("/T34_left.png"));
            imgT34Right = ImageIO.read(getClass().getResourceAsStream("/T34_right.png"));
            imgT34tUp = ImageIO.read(getClass().getResourceAsStream("/T34_up.png"));
            imgT34Down = ImageIO.read(getClass().getResourceAsStream("/T34_down.png"));
            imgTigerLeft = ImageIO.read(getClass().getResourceAsStream("/tiger_left.png"));
            imgTigerRight = ImageIO.read(getClass().getResourceAsStream("/tiger_right.png"));
            imgTigerUp = ImageIO.read(getClass().getResourceAsStream("/tiger_up.png"));
            imgTigerDown = ImageIO.read(getClass().getResourceAsStream("/tiger_down.png"));
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

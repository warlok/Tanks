package com.kademika.day8.frame21.BattleField.objects.tanks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.interfaces.Destroyable;
import com.kademika.day8.frame21.interfaces.Drawable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import com.kademika.day8.frame21.BattleField.BattleField;

public class T34 extends AbstractTank {

	BufferedImage img_left = null;
	BufferedImage img_right = null;
	BufferedImage img_up = null;
	BufferedImage img_down = null;
	
	public T34(BattleField bf) {
		super(bf);
		colorTank = new java.awt.Color(0, 255, 0);
		colorTower = new java.awt.Color(255,0, 0);
		loadImage();
    
	}

	public T34(com.kademika.day8.frame21.BattleField.BattleField bf, int x, int y, Direction direction) {
		super(bf,x,y,direction);
		colorTank = new java.awt.Color(0, 255, 0);
		colorTower = new java.awt.Color(255,0, 0);
		loadImage();
	}
	
	private void loadImage() {
		try {
			img_left = ImageIO.read(new File("T34_left.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
		try {
			img_right = ImageIO.read(new File("T34_right.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
		try {
			img_up = ImageIO.read(new File("T34_up.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
		try {
			img_down = ImageIO.read(new File("T34_down.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
	}
	@Override
	public void draw(Graphics g) {
		BufferedImage img = null;
//		bf.updateQuadrant(y/64, x/64, this);
		if (this.getDirection() == Direction.UP) {
			img = img_up;
		} else if (this.getDirection() == Direction.DOWN) {
			img = img_down;
		} else if (this.getDirection() == Direction.LEFT) {
			img = img_left;
		} else {
			img = img_right;
		}
		g.drawImage(img, this.getX(), this.getY(), 64, 64, null);
	}

}

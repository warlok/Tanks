package com.kademika.day8.frame21.BattleField.objects.tanks;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.interfaces.Destroyable;
import com.kademika.day8.frame21.interfaces.Drawable;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tiger extends AbstractTank {

	private int armor;
	BufferedImage img_left = null;
	BufferedImage img_right = null;
	BufferedImage img_up = null;
	BufferedImage img_down = null;
	
	public Tiger(BattleField bf) {
		super(bf);
		colorTank = new java.awt.Color(255, 0, 0);
		colorTower = new java.awt.Color(0,255, 0);
		armor=1;
		loadImage();
	}

	public Tiger(BattleField bf, int x, int y, Direction direction) {
		super(bf,x,y,direction);
		colorTank = new java.awt.Color(255, 0, 0);
		colorTower = new java.awt.Color(0,255, 0);
		armor=1;
		loadImage();
	}
	
	private void loadImage() {
		try {
			img_left = ImageIO.read(new File("tiger_left.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
		try {
			img_right = ImageIO.read(new File("tiger_right.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
		try {
			img_up = ImageIO.read(new File("tiger_up.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
		try {
			img_down = ImageIO.read(new File("tiger_down.png"));

		} catch (IOException e) {
			System.err.println("Couldn't load image");
		}
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	@Override
	public void draw(Graphics g) {
		BufferedImage img = null;
		bf.updateQuadrant(y/64, x/64, this);
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

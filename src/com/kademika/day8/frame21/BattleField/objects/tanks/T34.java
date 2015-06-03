package com.kademika.day8.frame21.BattleField.objects.tanks;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import com.kademika.day8.frame21.BattleField.BattleField;

public class T34 extends AbstractTank {

	BufferedImage img_left = null;
	BufferedImage img_right = null;
	BufferedImage img_up = null;
	BufferedImage img_down = null;

	private String key = "";

	public void setKey(String key) {
		this.key = key;
	}

	public T34(BattleField bf) {
		super(bf);
		loadImage();
    
	}

	public T34(com.kademika.day8.frame21.BattleField.BattleField bf, int x, int y, Direction direction) {
		super(bf,x,y,direction);
		loadImage();
	}
	
	protected void loadImage() {
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

	@Override
	public Action setupTank() {

			if (key == "Up") {
				return Action.MOVE_UP;
			}
			if (key == "Down") {
				return Action.MOVE_DOWN;
			}
			if (key == "Right") {
				return Action.MOVE_RIGHT;
			}
			if (key == "Left") {
				return Action.MOVE_LEFT;
			}
			if (key == "Space") {
				return Action.FIRE;
			}

		return Action.NOTHING;
	}

	public boolean checkMinWay(int enemyY, int enemyX, int tankX, int tankY) {
		if (Math.abs(tankY - enemyY) > Math.abs(tankX - enemyX)) {
			return true;
		}
		return false;
	}

	public int getQuadrant(int num) {
		return num/64+1;
	}

}

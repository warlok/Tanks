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

	transient BufferedImage img_left = null;
	transient BufferedImage img_right = null;
	transient BufferedImage img_up = null;
	transient BufferedImage img_down = null;

	private String key = "";
	private String keyFire = "";

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

	public void setKeyFire(String keyFire) {
		this.keyFire = keyFire;
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
	public boolean isFire() {
		if (keyFire == "Space") {
			return true;
		}
		return false;
	}

	@Override
	public char setupTank() {


			if (key == "Up" && direction == Direction.UP) {
				return 'M';
			} else if (key == "Up") {
                return 'U';
            }
			if (key == "Down" && direction == Direction.DOWN) {
				return 'M';
			} else if (key == "Down") {
                return 'D';
            }
			if (key == "Right" && direction == Direction.RIGHT) {
				return 'M';
			} else if (key == "Right") {
                return 'R';
            }
			if (key == "Left" && direction == Direction.LEFT) {
				return 'M';
			} else if (key == "Left") {
                return 'L';
            }
//		    if (key == "Space") {
//				return 'F';
//			}

		return ' ';
	}

}

package com.kademika.day8.frame21.BattleField.objects.tanks.bullet;

import java.awt.Graphics;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.AbstractObjects;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.BattleField.objects.tanks.AbstractTank;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.interfaces.Tank;
import com.kademika.day8.frame21.interfaces.Destroyable;
import com.kademika.day8.frame21.interfaces.Drawable;
import java.awt.image.BufferedImage;

public class Bullet implements Destroyable, Drawable {

    Tank tank = null;
	private int x;
	private int y;
	private int speed = 1;
	private Direction direction;
    private boolean destroyed = true;

	public Bullet(int x, int y, Direction direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

//	public boolean isInterception() {
//		return interception;
//	}
//
//	public void setInterception(boolean interception) {
//		this.interception = interception;
//	}

	public void updateX(int bulletX) {
		x += bulletX;
	}

	public void updateY(int bulletY) {
		y += bulletY;
	}

	public int getBulletX() {
		return x;
	}

	public int getBulletY() {
		return y;
	}

	public int getSpeed() {
		return speed;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public boolean intersseption(BattleField bf, AbstractTank agressor, AbstractTank defender) {

		int elemY = quadrant(y);
		int elemX = quadrant(x);

		if (elemY >= 0 && elemX >= 0 && elemY < bf.getQuadrantsY() && elemX < bf.getQuadrantsX()) {

			if (elemX == quadrant(agressor.getX()) && elemY == quadrant(agressor.getY())
					&& !(agressor.equals(tank))) {
				agressor.destroy();
				return true;
			} else if (elemX == quadrant(defender.getX()) && elemY == quadrant(defender.getY())
					&& !(defender.equals(tank))) {
				defender.destroy();
				return true;
			} else if (bf.scanQuadrant(elemY, elemX) != null && !(bf.scanQuadrant(elemY, elemX) instanceof Water)) {

				if (bf.scanQuadrant(elemY, elemX) instanceof AbstractObjects) {
					AbstractObjects o = (AbstractObjects) bf.scanQuadrant(
							elemY, elemX);
					try {
						o.destroy();
					} catch (NullPointerException e) {
						System.out.println("Null Pointer Exception");
					}
					bf.updateQuadrant(elemY, elemX, null);

					return true;
				}
			}
		}
		return false;
	}

	public int quadrant(int num) {
		return num/64;
	}

	public void destroy() {
		x = -100;
		y = -100;
        destroyed = true;
	}

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

	public void draw(Graphics g) {
		BufferedImage img = null;
		if (this.getDirection() == Direction.UP) {
			img = IMAGES.getImgBulletUp();
		} else if (this.getDirection() == Direction.DOWN) {
			img = IMAGES.getImgBulletDown();
		} else if (this.getDirection() == Direction.LEFT) {
			img = IMAGES.getImgBulletLeft();
		} else {
			img = IMAGES.getImgBulletRight();
		}
		g.drawImage(img, x, y, 15, 15, null);
	}

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}

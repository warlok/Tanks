package com.kademika.day8.frame21.BattleField.objects.tanks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.BattleField.objects.tanks.bullet.Bullet;
import com.kademika.day8.frame21.interfaces.Destroyable;
import com.kademika.day8.frame21.interfaces.Drawable;


public class AbstractTank implements Destroyable, Drawable, Tank {

	protected Color colorTank;
	protected Color colorTower;
	protected int speed = 10;
	protected int x;
	protected int y;
	protected Direction direction;
	protected BattleField bf;	

	public AbstractTank(BattleField bf) {
		this.bf = bf;
	}

	public AbstractTank(BattleField bf, int x, int y, Direction direction) {
		this.bf = bf;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	public Color getColorTank() {
		return colorTank;
	}

	public void setColorTank(Color colorTank) {
		this.colorTank = colorTank;
	}

	public Color getColorTower() {
		return colorTower;
	}

	public void setColorTower(Color colorTower) {
		this.colorTower = colorTower;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public Direction getDirection() {
		return direction;
	}

	public void updateX(int x) {
		this.x += x;
	}

	public void updateY(int y) {
		this.y += y;
	}

	public void turn(Direction direction) throws Exception {
		if (!this.direction.equals(direction)) {
			this.direction = direction;
		}
	}

	public void fire() throws Exception {
//		Bullet bullet = new Bullet(x + 25, y + 25, direction);
	}

	public void destroy() /* throws Exception */{
		x = -100;
		y = -100;
		// Thread.sleep(3000);
		// af.processDestroy(this);
	}

	public void moveToQuadrant(int v, int h) throws Exception {

		int newY = (v - 1) * 64;
		int newX = (h - 1) * 64;

	}

	 public Action setupTank() throws Exception {
		 return Action.MOVE;
		 }
	 
		String getQuadrant(int x, int y) {

			int quadrantX = x / 64;
			int quadrantY = y / 64;
			String result = quadrantY + "_" + quadrantX;
			return result;
		}
	 
		protected boolean processTankInterception() {

			String koordinate = this.getQuadrant(this.getX() + 32, this.getY() + 32);
			int delim = koordinate.indexOf("_");
			int elemY = Integer.parseInt(koordinate.substring(0, delim));
			int elemX = Integer.parseInt(koordinate.substring(delim + 1));

			if (elemY >= 0 && elemX >= 0 && elemY < 9 && elemX < 9) {

				/*if (this.getDirection() == Direction.UP
						&& bf.scanQuadrant(elemY - 1, elemX) != null) {
					return true;
				} else if (this.getDirection() == Direction.DOWN
						&& bf.scanQuadrant(elemY + 1, elemX) != null) {
					return true;
				} else if (this.getDirection() == Direction.LEFT
						&& bf.scanQuadrant(elemY, elemX - 1) != null) {
					return true;
				} else if (this.getDirection() == Direction.RIGHT
						&& bf.scanQuadrant(elemY, elemX + 1) != null) {
					return true;
				}*/
                if (this.getDirection() == Direction.UP
                        && bf.scanQuadrant(elemY - 1, elemX) != null && !(bf.scanQuadrant(elemY - 1, elemX) instanceof Water)) {
                    return true;
                } else if (this.getDirection() == Direction.DOWN
                        && bf.scanQuadrant(elemY + 1, elemX) != null && !(bf.scanQuadrant(elemY + 1, elemX) instanceof Water)) {
                    return true;
                } else if (this.getDirection() == Direction.LEFT
                        && bf.scanQuadrant(elemY, elemX - 1) != null && !(bf.scanQuadrant(elemY, elemX - 1) instanceof Water)) {
                    return true;
                } else if (this.getDirection() == Direction.RIGHT
                        && bf.scanQuadrant(elemY, elemX + 1) != null && !(bf.scanQuadrant(elemY, elemX + 1) instanceof Water)) {
                    return true;
                }

			}
			return false;
		}

/*	public void draw(Graphics g) {

		BufferedImage tankImage = null;

		bf.updateQuadrant(y/64, x/64, this);

		if (this.getDirection() == Direction.UP) {
			try {
				tankImage = ImageIO.read(new File("tank2.png"));
			} catch (IOException e) {
				System.out.println("Couldn't load image");
			}
		} else if (this.getDirection() == Direction.DOWN) {
			try {
				tankImage = ImageIO.read(new File("tank2_down.png"));
			} catch (IOException e) {
				System.out.println("Couldn't load image");
			}
		} else if (this.getDirection() == Direction.LEFT) {
			try {
				tankImage = ImageIO.read(new File("tank2_left.png"));
			} catch (IOException e) {
				System.out.println("Couldn't load image");
			}
		} else {
			try {
				tankImage = ImageIO.read(new File("tank2_right.png"));
			} catch (IOException e) {
				System.out.println("Couldn't load image");
			}
		}
		g.drawImage(tankImage, this.getX(), this.getY(), 64, 64, null);
	}*/

    public void draw(Graphics g) {

		BufferedImage tankImage = null;
        Graphics2D g2d = (Graphics2D) g;

        try {
            tankImage = ImageIO.read(new File("BT7_up.png"));
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

//		bf.updateQuadrant(y/64, x/64, this);

		if (this.getDirection() == Direction.UP) {

		} else if (this.getDirection() == Direction.DOWN) {
			g2d.rotate(3.14);
		} else if (this.getDirection() == Direction.LEFT) {
            g2d.rotate(-1.57);
		} else {
            g2d.rotate(1.57);
		}
		g2d.drawImage(tankImage, x, y, 64, 64, null);
	}

}

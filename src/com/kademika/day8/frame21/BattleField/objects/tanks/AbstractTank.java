package com.kademika.day8.frame21.BattleField.objects.tanks;

import com.kademika.day8.frame21.BattleField.BattleField;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.BattleField.objects.tanks.bullet.Bullet;
import com.kademika.day8.frame21.interfaces.Drawable;
import com.kademika.day8.frame21.interfaces.Tank;

import java.awt.image.BufferedImage;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractTank implements Drawable, Tank {

	protected int speed = 5;
	protected int x;
	protected int y;
	protected Direction direction;
	protected transient BattleField bf;
    protected transient AbstractTank enemy;
    protected boolean destroed = false;
    private Bullet bul;
	private ReentrantLock lock = new ReentrantLock();
	protected transient BufferedImage img;

    public void setEnemy(AbstractTank enemy) {
        this.enemy = enemy;
    }

    public AbstractTank(BattleField bf) {
		this.bf = bf;
	}

	public boolean isFire() {
		return false;
	}

	public AbstractTank(BattleField bf, int x, int y, Direction direction) {
		this.bf = bf;
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

    public Bullet getBullet() {
        return bul;
    }

    public void setBullet(Bullet bullet) {
        this.bul = bullet;
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

	public void turn(Direction direction) {
		if (!this.direction.equals(direction)) {
			this.direction = direction;
		}
	}

	public ReentrantLock getLock() {
		return lock;
	}

	public void destroy() {
		x = -100;
		y = -100;
        destroed = true;
	}

    public boolean isDestroed() {
        return destroed;
    }

	public char setupTank() {
		 return ' ';
		 }

	public BattleField getBf() {
		return bf;
	}

	public String getQuadrant(int x, int y) {
			String result = bf.getQuadrantsY()+1 + "_" + bf.getQuadrantsX()+1;
			if (x < bf.getBF_WIDTH() && x >= 0 && y < bf.getBF_HEIGHT() && y >= 0) {
				int quadrantX = x / 64;
				int quadrantY = y / 64;
				result = quadrantY + "_" + quadrantX;
			}
			return result;
		}

	public boolean tanksInterception() {

		if (direction == Direction.DOWN
				&& (getQuadrant(enemy.getX(), enemy.getY()).equals(getQuadrant(x, y+64)))) {
			return true;
		} else if (direction == Direction.RIGHT
				&& (getQuadrant(enemy.getX(), enemy.getY()).equals(getQuadrant(x+64, y)))) {
			return true;
		} else if (getQuadrant(enemy.getX(), enemy.getY()).equals(getQuadrant(x, y))) {
			return true;
		}

		return false;
	}

	public void fire() {

				lock.lock();
				bul = new Bullet(-100, -100, Direction.LEFT);
				bul.setTank(this);
				bul.updateX(x + 125);
				bul.updateY(y + 125);
				bul.setDirection(direction);
				int step2 = 1;

				while (bul.getBulletX() > 0 && bul.getBulletY() > 0
						&& bul.getBulletX() < bf.getBF_WIDTH() && bul.getBulletY() < bf.getBF_HEIGHT()) {

					int step = 0;

					while (step < 64 | !bul.isDestroyed()) {

						if (bul.getDirection().equals(Direction.UP)) {
							bul.updateY(-step2);
						} else if (bul.getDirection().equals(Direction.DOWN)) {
							bul.updateY(step2);
						} else if (bul.getDirection().equals(Direction.LEFT)) {
							bul.updateX(-step2);
						} else {
							bul.updateX(step2);
						}
						step++;
						if (bul.intersseption(bf,enemy, this) || bul.getBulletX() <= 0
								|| bul.getBulletY() <= 0 || bul.getBulletX() >= bf.getBF_WIDTH()
								|| bul.getBulletY() >= bf.getBF_HEIGHT()) {
							bul.destroy();
						}

						try {
							Thread.sleep(bul.getSpeed());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				bul = null;
				lock.unlock();
			}


	public boolean interception() {

        String koordinate = getQuadrant(x + 32, y + 32);
        int delim = koordinate.indexOf("_");
        int elemY = Integer.parseInt(koordinate.substring(0, delim));
        int elemX = Integer.parseInt(koordinate.substring(delim + 1));

        if (elemY >= 0 && elemX >= 0 && elemY < bf.getQuadrantsY() && elemX < bf.getQuadrantsX()) {

            if (direction == Direction.UP
                    && bf.scanQuadrant(elemY - 1, elemX) != null && !(bf.scanQuadrant(elemY - 1, elemX) instanceof Water)) {
                return true;
            } else if (direction == Direction.DOWN
                    && bf.scanQuadrant(elemY + 1, elemX) != null && !(bf.scanQuadrant(elemY + 1, elemX) instanceof Water)) {
                return true;
            } else if (direction == Direction.LEFT
                    && bf.scanQuadrant(elemY, elemX - 1) != null && !(bf.scanQuadrant(elemY, elemX - 1) instanceof Water)) {
                return true;
            } else if (direction == Direction.RIGHT
                    && bf.scanQuadrant(elemY, elemX + 1) != null && !(bf.scanQuadrant(elemY, elemX + 1) instanceof Water)) {
                return true;
            }

        }
        return false;

		}

}

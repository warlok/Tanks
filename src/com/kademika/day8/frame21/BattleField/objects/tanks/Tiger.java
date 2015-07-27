package com.kademika.day8.frame21.BattleField.objects.tanks;

import com.kademika.day8.frame21.BattleField.BattleField;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tiger extends AbstractTank {

	private int armor;

	public Tiger(BattleField bf) {
		super(bf);
		armor=1;
	}

	public Tiger(BattleField bf, int x, int y, Direction direction) {
		super(bf,x,y,direction);
		armor=1;
	}

	public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

    @Override
    public void destroy() {
       if (armor == 0) {
           super.destroy();
           return;
       }
        armor--;
    }

    @Override
    public char setupTank() {
        char result = ' ';
        int enemyY = getQuadrant(enemy.getY());
        int enemyX = getQuadrant(enemy.getX());
        int tankX = getQuadrant(x);
        int tankY = getQuadrant(y);


        if (tankX == enemyX) {
            if (tankY < enemyY) {
                if (direction != Direction.DOWN) {
                    return 'D';
                }
            } else {
                if (direction != Direction.UP) {
                    return 'U';
                }
            }
            result = 'F';
        } else if (tankY == enemyY) {
            if (tankX < enemyX) {
                if (direction != Direction.RIGHT) {
                    return 'R';
                }
            } else {
                if (direction != Direction.LEFT) {;
                    return 'L';
                }
            }
            result = 'F';
        } else if (interception() || tanksInterception()){
            result = 'F';
        } else if (checkMinWay(enemyY, enemyX, tankX, tankY) && tankX < enemyX) {
            if (direction != Direction.RIGHT) {
                return 'R';
            }
            result = 'M';
        } else if (checkMinWay(enemyY, enemyX, tankX, tankY) && tankX > enemyX) {
            if (direction != Direction.LEFT) {
                return 'L';
            }
            result = 'M';
        } else if ( tankY > enemyY) {
            if (direction != Direction.LEFT) {
                return 'D';
            }
            result = 'M';
        } else if (tankY < enemyY) {
            if (direction != Direction.UP) {
                return 'U';
            }
            result = 'M';
        }

        return result;
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

    @Override
	public void draw(Graphics g) {
		BufferedImage img = null;
		if (this.getDirection() == Direction.UP) {
			img = IMAGES.getImgTigerUp();
		} else if (this.getDirection() == Direction.DOWN) {
			img = IMAGES.getImgTigerDown();
		} else if (this.getDirection() == Direction.LEFT) {
			img = IMAGES.getImgTigerLeft();
		} else {
			img = IMAGES.getImgTigerRight();
		}
		g.drawImage(img, x, y, 64, 64, null);
	}

}

package com.kademika.day8.frame21.BattleField.objects.tanks;

import com.kademika.day8.frame21.BattleField.BattleField;
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
		armor=1;
		loadImage();
	}

	public Tiger(BattleField bf, int x, int y, Direction direction) {
		super(bf,x,y,direction);
		armor=1;
		loadImage();
	}
	

    protected void loadImage() {
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
    public void destroy() {
       if (armor == 0) {
           super.destroy();
           return;
       }
        armor--;
    }

    @Override
    public Action setupTank() {
        Action result = Action.NOTHING;
        int enemyY = getQuadrant(enemy.getY());
        int enemyX = getQuadrant(enemy.getX());
        int tankX = getQuadrant(x);
        int tankY = getQuadrant(y);


        if (tankX == enemyX) {
            if (tankY < enemyY) {
                if (direction != Direction.DOWN) {
                    turn(Direction.DOWN);
                    return Action.TURN;
                }
            } else {
                if (direction != Direction.UP) {
                    turn(Direction.UP);
                    return Action.TURN;
                }
            }
            result = Action.FIRE;
        } else if (tankY == enemyY) {
            if (tankX < enemyX) {
                if (direction != Direction.RIGHT) {
                    turn(Direction.RIGHT);
                    return Action.TURN;
                }
            } else {
                if (direction != Direction.LEFT) {
                    turn(Direction.LEFT);
                    return Action.TURN;
                }
            }
            result = Action.FIRE;
        } else if (interception() || tanksInterception()){
            result = Action.FIRE;
        } else if (checkMinWay(enemyY, enemyX, tankX, tankY) && tankX < enemyX) {
            if (direction != Direction.RIGHT) {
                turn(Direction.RIGHT);
                return Action.TURN;
            }
            result = Action.MOVE;
        } else if (checkMinWay(enemyY, enemyX, tankX, tankY) && tankX > enemyX) {
            if (direction != Direction.LEFT) {
                turn(Direction.LEFT);
                return Action.TURN;
            }
            result = Action.MOVE;
        } else if ( tankY > enemyY) {
            if (direction != Direction.LEFT) {
                turn(Direction.DOWN);
                return Action.TURN;
            }
            result = Action.MOVE;
        } else if (tankY < enemyY) {
            if (direction != Direction.UP) {
                turn(Direction.UP);
                return Action.TURN;
            }
            result = Action.MOVE;
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

}

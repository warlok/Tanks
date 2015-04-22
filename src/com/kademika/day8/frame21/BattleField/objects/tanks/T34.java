package com.kademika.day8.frame21.BattleField.objects.tanks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.kademika.day8.frame21.BattleField.BattleField;

public class T34 extends AbstractTank {

	BufferedImage img_left = null;
	BufferedImage img_right = null;
	BufferedImage img_up = null;
	BufferedImage img_down = null;
	int counter;
	
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
		Action result = Action.NOTHING;

		int enemyY = getQuadrant(enemy.getY());
		int enemyX = getQuadrant(enemy.getX());
		int tankX = getQuadrant(x);
		int tankY = getQuadrant(y);


		// Avoid the Eagle
		if (tankY == bf.getQuadrantsY()) {
			if (direction != Direction.UP) {
				turn(Direction.UP);
				return Action.TURN;
			} else if (interception() || tanksInterception()) {
				return Action.FIRE;

			} else {
				return Action.MOVE;
			}
		}

		if (enemy instanceof BT7) {
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
			} else if (interception() || tanksInterception()) {
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
			} else if (tankY > enemyY) {
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
		} else {
			if (tankX != bf.getQuadrantsX()/2+1) {
				if (direction != Direction.LEFT) {
					turn(Direction.LEFT);
					return Action.TURN;
				} else if (interception() || tanksInterception()) {
					return Action.FIRE;

				} else {
					return Action.MOVE;
				}
			}

			if (counter == 0) {
				counter++;
				result = Action.FIRE;
			} else if (direction == Direction.RIGHT && counter > 0) {
				counter = 0;
				turn(Direction.UP);
				result = Action.TURN;
			} else if (direction == Direction.UP && counter > 0) {
				counter = 0;
				turn(Direction.LEFT);
				result = Action.TURN;
			} else if (direction == Direction.LEFT && counter > 0) {
				counter = 0;
				turn(Direction.RIGHT);
				result = Action.TURN;
			}

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

}

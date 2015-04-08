package com.kademika.day8.frame21.BattleField.objects.tanks;

import java.awt.Color;

public interface Tank {

	 public Action setupTank() throws Exception;
	 public Color getColorTank();

		public void setColorTank(Color colorTank);

		public Color getColorTower();

		public void setColorTower(Color colorTower);

		public int getX();

		public int getY();
		public void setX(int x);

		public void setY(int y);

		public int getSpeed();

		public Direction getDirection();

		public void updateX(int x);

		public void updateY(int y);

		public void turn(Direction right) throws Exception;

		public void fire() throws Exception;

		public void moveToQuadrant(int v, int h) throws Exception;
	 
}

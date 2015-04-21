package com.kademika.day8.frame21.BattleField.objects.tanks;


import java.awt.*;

public interface Tank {

	 public Action setupTank() throws Exception;
	 public boolean tanksInterception();
     public boolean interception();

	 public int getX();

	 public int getY();
	 public void setX(int x);

	 public void setY(int y);

	 public int getSpeed();

	 public Direction getDirection();

	 public void updateX(int x);

	 public void updateY(int y);
	 public boolean isDestroed();
	 public void turn(Direction right) throws Exception;

}

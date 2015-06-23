package com.kademika.day8.frame21.interfaces;


import com.kademika.day8.frame21.BattleField.objects.tanks.Action;
import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.BattleField.objects.tanks.bullet.Bullet;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public interface Tank extends Destroyable {

	 public ReentrantLock getLock();
	 public char setupTank();
	 public void fire();
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
	 public void turn(Direction right);
     public Bullet getBullet();
     public void setBullet(Bullet bullet);
	 public boolean isFire();

}

package com.kademika.day8.frame21.interfaces;

import com.kademika.day8.frame21.BattleField.objects.tanks.Direction;
import com.kademika.day8.frame21.BattleField.objects.tanks.bullet.Bullet;

public interface Tank extends Destroyable, Drawable {

	 public char setupTank();
	 public void fire();
	 public Direction getDirection();
	 public void updateX(int x);
	 public void updateY(int y);
	 public boolean isDestroed();
	 public void turn(Direction right);
     public Bullet getBullet();
	 public boolean isFire();

}

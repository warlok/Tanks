package com.kademika.day8.frame21.BattleField.objects.tanks;


public interface Tank {

	 public Action setupTank() throws Exception;

     public boolean interception();

		public int getX();

		public int getY();
		public void setX(int x);

		public void setY(int y);

		public int getSpeed();

		public Direction getDirection();

		public void updateX(int x);

		public void updateY(int y);

		public void turn(Direction right) throws Exception;

}

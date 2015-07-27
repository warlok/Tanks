package com.kademika.day8.frame21.BattleField.objects;

import com.kademika.day8.frame21.interfaces.Destroyable;
import com.kademika.day8.frame21.interfaces.Drawable;

public abstract class AbstractObjects implements Drawable, Destroyable {
	
	protected int x;
	protected int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void destroy() {
		x=-1000;
		y=-1000;
	}

}

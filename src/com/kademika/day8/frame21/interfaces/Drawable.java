package com.kademika.day8.frame21.interfaces;

import com.kademika.day8.frame21.BattleField.objects.Images;

import java.awt.Graphics;
import java.io.Serializable;

public interface Drawable extends Serializable{

	static final Images IMAGES = new Images();

	void draw(Graphics g);	
	
}

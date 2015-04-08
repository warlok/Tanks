package com.kademika.day8.frame21.BattleField.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kademika.day8.frame21.interfaces.Destroyable;
import com.kademika.day8.frame21.interfaces.Drawable;

public abstract class AbstractObjects implements Drawable, Destroyable {
	
	protected int x;
	protected int y;
	
	protected Color color;

	public Color getColor() {
		return color;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, 64, 64);
	}
	
	public void destroy() {
		x=-1000;
		y=-1000;
	}

}

package com.kademika.day8.frame21.BattleField.objects;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kademika.day8.frame21.interfaces.Destroyable;

public class Eagle extends AbstractObjects implements Destroyable {

	static final BufferedImage IMG = IMAGES.getImgEagle();
	
	public Eagle(int x, int y) {
		color = new Color(255, 255, 0);
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(IMG, x, y, 64, 64, null);
	}
}

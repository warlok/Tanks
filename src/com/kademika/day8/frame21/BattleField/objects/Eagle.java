package com.kademika.day8.frame21.BattleField.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.kademika.day8.frame21.interfaces.Destroyable;

public class Eagle extends AbstractObjects implements Destroyable {

	private transient BufferedImage img;
	
	public Eagle(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void draw(Graphics g) {
		img = IMAGES.getImgEagle();
		g.drawImage(img, x, y, 64, 64, null);
	}
}

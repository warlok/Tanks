package com.kademika.day8.frame21.BattleField.objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Water extends AbstractObjects {

	private transient BufferedImage img;

	public Water(int x, int y) {
	this.x = x;
	this.y = y;
	}
	
	public void destroy() {
	}

	@Override
	public void draw(Graphics g) {
		img = IMAGES.getImgWater();
        Graphics2D g2d = (Graphics2D) g;
        Composite comp = g2d.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
        g2d.setComposite(alpha);
        g2d.drawImage(img, x, y, 64, 64, null);
        g2d.setComposite(comp);
	}
}

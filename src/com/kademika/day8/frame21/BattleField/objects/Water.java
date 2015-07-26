package com.kademika.day8.frame21.BattleField.objects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Water extends AbstractObjects {

	static final BufferedImage IMG = IMAGES.getImgWater();

	public Water(int x, int y) {
	color = new Color(0, 0, 255);
	this.x = x;
	this.y = y;
	}
	
	public void destroy() {
	}

	@Override
	public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        Composite comp = g2d.getComposite();
        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5);
        g2d.setComposite(alpha);
        g2d.drawImage(IMG, x, y, 64, 64, null);
        g2d.setComposite(comp);
	}
}

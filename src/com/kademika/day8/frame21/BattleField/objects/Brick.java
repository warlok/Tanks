package com.kademika.day8.frame21.BattleField.objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import com.kademika.day8.frame21.interfaces.Destroyable;

public class Brick extends AbstractObjects implements Destroyable {

	static final BufferedImage IMG = IMAGES.getImgBrick();

	public Brick(int x, int y) {
		this.x = x;
		this.y = y;
	}


	@Override
	public void draw(Graphics g) {
		g.drawImage(IMG, x, y, 64, 64, null);
	}

}

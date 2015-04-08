package com.kademika.day8.frame21.BattleField;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kademika.day8.frame21.*;
import com.kademika.day8.frame21.BattleField.objects.AbstractObjects;
import com.kademika.day8.frame21.BattleField.objects.Brick;
import com.kademika.day8.frame21.BattleField.objects.Eagle;
import com.kademika.day8.frame21.BattleField.objects.Rock;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.interfaces.Drawable;

public class BattleField implements Drawable {

	private int BF_WIDTH = 590;
	private int BF_HEIGHT = 590;

	private String[][] battleFieldString = new String[][] {
			{ "B", "B", "B", "B", "B", "B", "B", "B", "B" },
			{ "B", "", "", "W", "W", "", "B", "", "B" },
			{ "B", "", "R", "", "R", "", "", "", "B" },
			{ "B", "B", "", "", "", "W", "B", "", "B" },
			{ "B", "B", "", "", "R", "", "", "", "B" },
			{ "B", "R", "", "W", "", "", "W", "B", "B" },
			{ "B", "W", "W", "", "", "B", "", "", "B" },
			{ "B", "W", "W", "B", "B", "B", "", "", "B" },
			{ "B", "B", "B", "B", "E", "B", "B", "B", "B" } };

	private int dimX = battleFieldString[0].length;
	private int dimY = battleFieldString.length;
	private Object[][] battleField = new Object[dimY][dimX];

	public BattleField() {

	}

	public void generateBattleField() {
		for (int j = 0; j < getDimentionY(); j++) {
			for (int k = 0; k < getDimentionX(); k++) {
				String XY = getQuadrantXY(j + 1, k + 1);
				int separator = XY.indexOf("_");
				int x = Integer.parseInt(XY.substring(separator + 1));
				int y = Integer.parseInt(XY.substring(0, separator));
				switch (battleFieldString[j][k]) {
				case "B":
					battleField[j][k] = new Brick(x, y);
					break;
				case "R":
					battleField[j][k] = new Rock(x, y);
					break;
				case "W":
					battleField[j][k] = new Water(x, y);
					break;
				case "E":
					battleField[j][k] = new Eagle(x, y);
					break;
				default:
					battleField[j][k] = null;
					break;

				}
			}
		}
	}

	public void draw(Graphics g) {

		g.setColor(new Color(160, 160, 160));
		g.fillRect(0, 0, 4000, 4000);

		for (Object[] objects : battleField) {
			for (Object object : objects) {
				if (object != null && object instanceof AbstractObjects) {
					AbstractObjects o = (AbstractObjects) object;
					o.draw(g);
				}
			}
		}
	}

	String getQuadrantXY(int v, int h) {
		return (v - 1) * 64 + "_" + (h - 1) * 64;
	}

	public BattleField(Object[][] battleField) {
		this.battleField = battleField;
	}

	public Object scanQuadrant(int x, int y) {
		if (x >= 0 && x < 9 && y >= 0 && y < 9) {
		Object value = battleField[x][y];
		return value;
		} 
		return null;
	}

	public void updateQuadrant(int x, int y, Object newValue) {
		if (x > 0 && x < 9 && y > 0 && y < 9) {
			battleField[x][y] = newValue;
		}
	}

	public int getDimentionX() {
		int value = battleField[0].length;
		return value;
	}

	public int getDimentionY() {
		int value = battleField.length;
		return value;
	}

	public int getBF_WIDTH() {
		return BF_WIDTH;
	}

	public int getBF_HEIGHT() {
		return BF_HEIGHT;
	}

}

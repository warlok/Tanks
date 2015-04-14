package com.kademika.day8.frame21.BattleField;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.kademika.day8.frame21.*;
import com.kademika.day8.frame21.BattleField.objects.AbstractObjects;
import com.kademika.day8.frame21.BattleField.objects.Brick;
import com.kademika.day8.frame21.BattleField.objects.Eagle;
import com.kademika.day8.frame21.BattleField.objects.Rock;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.interfaces.Drawable;

public class BattleField implements Drawable {

    private int size = 64;

    public int getQuadrantsX() {
        return quadrantsX;
    }

    public int getQuadrantsY() {
        return quadrantsY;
    }

    private int quadrantsX = 9;
    private int quadrantsY = 8;

    private int BF_WIDTH = quadrantsX*size;
	private int BF_HEIGHT = quadrantsY*size;

    private String[][] battleFieldString = new String[quadrantsY][quadrantsX];

    public String generateElements(int n) {
        String result = "";
        if (n < 35) {
            result = "B";
        } else if (n>35 && n<50) {
            result = "R";
        } else if (n>50 && n < 60) {
            result = "W";
        }
        return result;
    }

    public void generateBattleFieldString() {
        Random rand = new Random();
        for (int i=0; i<quadrantsY;i++){
            for (int j=0; j<quadrantsX;j++) {
                battleFieldString[i][j] = generateElements(rand.nextInt(100));
            }
        }
        battleFieldString[quadrantsY-1][quadrantsX/2] = "E";
    }

	/*private String[][] battleFieldString = new String[][] {
			{ "B", "B", "B", "B", "B", "B", "B", "B", "B" },
			{ "B", "", "", "W", "W", "", "B", "", "B" },
			{ "B", "R", "R", "", "", "", "", "", "B" },
			{ "B", "", "", "", "", "W", "B", "", "B" },
			{ "B", "", "", "", "", "", "", "", "B" },
			{ "B", "B", "", "W", "", "", "W", "B", "B" },
			{ "B", "W", "W", "", "", "B", "", "", "B" },
			{ "B", "W", "W", "B", "B", "B", "", "", "B" },
			{ "B", "B", "B", "B", "E", "B", "B", "B", "B" } };*/

	private int dimX = battleFieldString[0].length;
	private int dimY = battleFieldString.length;
	private Object[][] battleField = new Object[dimY][dimX];

	public BattleField() {
        generateBattleFieldString();
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
		if (x >= 0 && x < quadrantsY && y >= 0 && y < quadrantsX) {
		Object value = battleField[x][y];
		return value;
		} 
		return null;
	}

	public void updateQuadrant(int x, int y, Object newValue) {
		if (x >= 0 && x < quadrantsY && y >= 0 && y < quadrantsX) {
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

//    public void setQuadrantsX(int quadrantsX) {
//        this.quadrantsX = quadrantsX;
//    }
//
//    public void setQuadrantsY(int quadrantsY) {
//        this.quadrantsY = quadrantsY;
//    }

}

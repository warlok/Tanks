package com.kademika.day8.frame21.BattleField;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import com.kademika.day8.frame21.BattleField.objects.AbstractObjects;
import com.kademika.day8.frame21.BattleField.objects.Brick;
import com.kademika.day8.frame21.BattleField.objects.Eagle;
import com.kademika.day8.frame21.BattleField.objects.Rock;
import com.kademika.day8.frame21.BattleField.objects.Water;
import com.kademika.day8.frame21.interfaces.Drawable;

public class BattleField implements Drawable {

    private transient int size = 64;
    private transient int quadrantsX;
    private transient int quadrantsY;

    private transient int BF_WIDTH = quadrantsX*size;
	private transient int BF_HEIGHT = quadrantsY*size;

    public BattleField() {
        quadrantsX = 9;
        quadrantsY = 9;
        generateBattleFieldString();
    }

    public BattleField(int quadrantsX, int quadrantsY) {
        this.quadrantsX = quadrantsX;
        this.quadrantsY = quadrantsY;
        generateBattleFieldString();
    }

    public String[][] getBattleFieldString() {
		return battleFieldString;
	}

	public void setBattleFieldString(String[][] battleFieldString) {
		this.battleFieldString = battleFieldString;
	}

	private String[][] battleFieldString = new String[quadrantsY][quadrantsX];

	private Object[][] battleField = new Object[quadrantsY][quadrantsX];

	public void setBattleField(Object[][] battleField) {
		this.battleField = battleField;
	}

	public Object[][] getBattleField() {
		return battleField;
	}

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
        battleFieldString = new String[quadrantsY][quadrantsX];
        Random rand = new Random();
        for (int i=0; i<quadrantsY;i++){
            for (int j=0; j<quadrantsX;j++) {
                long a = System.currentTimeMillis();
                battleFieldString[i][j] = generateElements(rand.nextInt(100));
            }
        }
        battleFieldString[quadrantsY-1][quadrantsX/2] = "E";
    }

	public void generateBattleField() {
		for (int j = 0; j < quadrantsY; j++) {
			for (int k = 0; k < quadrantsX; k++) {
				int x = k*64;
				int y = j*64;
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

    public int getQuadrantsX() {
        return quadrantsX;
    }

    public int getQuadrantsY() {
        return quadrantsY;
    }

    public void setQuadrantsXY(int quadrantsX,int quadrantsY) {
        this.quadrantsX = quadrantsX;
        this.quadrantsY = quadrantsY;
        BF_WIDTH = quadrantsX*size;
        battleFieldString = new String[quadrantsY][quadrantsX];
        battleField = new Object[quadrantsY][quadrantsX];
        BF_WIDTH = quadrantsX*size;
        BF_HEIGHT = quadrantsY*size;
        generateBattleFieldString();
    }

    public int getBF_WIDTH() {
		return BF_WIDTH;
	}

	public int getBF_HEIGHT() {
		return BF_HEIGHT;
	}


}

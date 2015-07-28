package com.kademika.day8.frame21.BattleField.objects.tanks;

import java.awt.Graphics;
import com.kademika.day8.frame21.BattleField.BattleField;

public class T34 extends AbstractTank {

	private String key = "";
	private String keyFire = "";

	public void setKey(String key) {
		this.key = key;
	}

	public T34(BattleField bf) {
		super(bf);
	}

	public T34(com.kademika.day8.frame21.BattleField.BattleField bf, int x, int y, Direction direction) {
		super(bf,x,y,direction);
	}

	public void setKeyFire(String keyFire) {
		this.keyFire = keyFire;
	}

    @Override
	public void draw(Graphics g) {
		if (this.getDirection() == Direction.UP) {
			img = IMAGES.getImgT34tUp();
		} else if (this.getDirection() == Direction.DOWN) {
			img = IMAGES.getImgT34Down();
		} else if (this.getDirection() == Direction.LEFT) {
			img = IMAGES.getImgT34Left();
		} else {
			img = IMAGES.getImgT34Right();
		}
		g.drawImage(img, x, y, 64, 64, null);
	}

	@Override
	public boolean isFire() {
		if (keyFire == "Space") {
			return true;
		}
		return false;
	}

	@Override
	public char setupTank() {


			if (key == "Up" && direction == Direction.UP) {
				return 'M';
			} else if (key == "Up") {
                return 'U';
            }
			if (key == "Down" && direction == Direction.DOWN) {
				return 'M';
			} else if (key == "Down") {
                return 'D';
            }
			if (key == "Right" && direction == Direction.RIGHT) {
				return 'M';
			} else if (key == "Right") {
                return 'R';
            }
			if (key == "Left" && direction == Direction.LEFT) {
				return 'M';
			} else if (key == "Left") {
                return 'L';
            }
//		    if (key == "Space") {
//				return 'F';
//			}

		return ' ';
	}

}

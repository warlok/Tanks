package com.kademika.day8.frame21.BattleField.objects.tanks;

import com.kademika.day8.frame21.BattleField.BattleField;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BT7 extends AbstractTank {

    public BT7(BattleField bf) {
        super(bf);
        speed = super.speed / 2;
    }

    public BT7(BattleField bf, int x, int y, Direction direction) {
        super(bf, x, y, direction);
    }

    @Override
    public char setupTank() {

        if (x < bf.getQuadrantsX()/2 * 64) {
            while (x != bf.getQuadrantsX()/2 * 64) {
                if (direction != Direction.RIGHT) {
                    return 'R';
                }
                if (interception() || tanksInterception()) {
                    return 'F';
                } else {
                    return 'M';
                }
            }
        } else {
            while (x != bf.getQuadrantsX()/2 * 64) {
                if (direction != Direction.LEFT) {
                    return 'L';
                }
                if (interception() || tanksInterception()) {
                    return 'F';
                } else {
                    return 'M';
                }
            }
        }

        if (y < (bf.getQuadrantsY()-1) * 64) {
            while (y != bf.getQuadrantsY() * 64) {
                if (direction != Direction.DOWN) {
                    return 'D';
                }
                if (interception() || tanksInterception()) {
                    return 'F';
                } else {
                    return 'M';
                }
            }
        } else {
            while (y != bf.getQuadrantsY() * 64) {
                if (direction != Direction.UP) {
                    return 'U';
                }
                if (interception() || tanksInterception()) {
                    return 'F';
                } else {
                    return 'M';
                }
            }
        }
        return ' ';
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage img = null;
        if (this.getDirection() == Direction.UP) {
            img = IMAGES.getImgBT7Up();
        } else if (this.getDirection() == Direction.DOWN) {
            img = IMAGES.getImgBT7Down();
        } else if (this.getDirection() == Direction.LEFT) {
            img = IMAGES.getImgBT7Left();
        } else {
            img = IMAGES.getImgBT7Right();
        }
        g.drawImage(img, x, y, 64, 64, null);
    }
}

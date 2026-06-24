package project.game.horde.entities.bullets;

import java.awt.Graphics;

import project.game.horde.entities.blood.Blood;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class Explosion extends Blood {

    boolean isUpgraded;

    public Explosion(Handler handler, float x, float y, int width, int height, boolean isUpgraded) {
        super(handler, x, y, -1);
        this.timer = 20;
        this.width = width;
        this.height = height;
        this.isUpgraded = isUpgraded;
    }

//	public Explosion(Handler handler, float x, float y, int width, int height, Color color) {
//		super(handler, x, y, -1);
//		this.timer = 20;
//		this.width = width;
//		this.height = height;
//		this.color = color;
//		isArc = false;
//	}
    // public Explosion(Handler handler, Arc2D.Float arc) {
    //     super(handler, arc.x, arc.y, -1);
    //     this.timer = 20;
    //     this.width = (int) arc.width;
    //     this.height = (int) arc.height;
    // }
    int i = 0;

    @Override
    public void render(Graphics g) {
        if (i < 16) {
            if (!isUpgraded) {
                g.drawImage(Assets.explosion[i], (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            } else {
                g.drawImage(Assets.upgradedExplosion[i], (int) (x - handler.getGameCamera().getxOffset()),
                        (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            }
        }

        i++;

    }

}

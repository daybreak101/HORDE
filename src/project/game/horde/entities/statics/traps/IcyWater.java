package project.game.horde.entities.statics.traps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.areas.Areas;
import project.game.horde.main.Handler;

public class IcyWater extends Areas {

    private Handler handler;
    private int width, height;
    Rectangle bounds;

    public IcyWater(Handler handler, float x, float y) {
        super(handler, x, y);
        this.handler = handler;

        width = 500;
        height = 500;
        bounds = new Rectangle((int) x, (int) y, width, height);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0, 20, 200, 100));
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);

    }

    @Override
    public void renderBW(Graphics g) {
        g.setColor(new Color(35, 35, 35, 100));
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);

    }

    public boolean checkIfEntityIsContained(Rectangle hitbox) {
        return bounds.intersects(hitbox);
    }

}

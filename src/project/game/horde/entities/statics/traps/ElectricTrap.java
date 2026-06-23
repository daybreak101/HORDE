package project.game.horde.entities.statics.traps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.main.Handler;

public class ElectricTrap extends Trap {

    private Rectangle cb;

    public ElectricTrap(Handler handler, int id, float x, float y, float switchX, float switchY,  int switchRotation) {
        super(handler, id, x, y, 25, 100, switchX, switchY, switchRotation, 30 * 60, 1000);
        cooldown = 30 * 60;
        cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1, bounds.height + 1);

    }

    @Override
    public void postTick() {
        if (cooldownTimer > cooldown) {
            activated = false;
        } else if (activated && cooldownTimer <= cooldown) {
            killInArea();
        }
    }

    @Override
    public void render(Graphics g) {
        if (!activated) {
            g.setColor(Color.black);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height);
        } else {
            g.setColor(Color.blue);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height);
        }

    }

    @Override
    public void renderBW(Graphics g) {
        if (!activated) {
            g.setColor(Color.black);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height);
        } else {
            g.setColor(new Color(29, 29, 29));
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height);
        }

    }

    public void killInArea() {
        Player player = handler.getCurrentPlayer();
        if (player.getHitbox().intersects(cb)) {
            player.takeDamage(5);
        }

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (e.getHitBox(0, 0).intersects(cb)) {
                e.dieByTrap();
                // handler.getWorld().getEntityManager().getEntities().remove(this);
            }
        }
    }

}

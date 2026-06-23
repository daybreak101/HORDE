package project.game.horde.entities.statics.traps;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.blood.Blood;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Stoker;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.creatures.ZombieType;
import project.game.horde.main.Handler;

public class ConveyorBeltTrap extends Trap {

    private Rectangle furnace, conveyor;

    public ConveyorBeltTrap(Handler handler, int id, float x, float y, float switchX, float switchY,  int switchRotation) {
        super(handler, id, x, y, 200, 200, switchX, switchY, switchRotation, 5 * 60, 2000);
        cooldown = 30 * 60;
        furnace = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1,
                bounds.height + 1);
        conveyor = new Rectangle((int) (x - 600), (int) (y + 25), 600, 150);
    }

    @Override
    public void postTick() {
        if (cooldownTimer > cooldown) {
            activated = false;
        } else if (activated && cooldownTimer <= cooldown) {
            moveEntitiesTowardFurnace();
            killInArea();
        }
    }

    @Override
    public void render(Graphics g) {
        // draw conveyor
        g.setColor(Color.gray);
        g.fillRect((int) (conveyor.x - handler.getGameCamera().getxOffset()),
                (int) (conveyor.y - handler.getGameCamera().getyOffset()), conveyor.width, conveyor.height);

        // draw furnace
        g.setColor(Color.black);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);
        if (!activated) {
            g.setColor(Color.gray);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y + 20 - handler.getGameCamera().getyOffset()), width / 2, height - 40);
        } else {
            g.setColor(Color.orange);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y + 20 - handler.getGameCamera().getyOffset()), width / 2, height - 40);
        }

    }

    @Override
    public void renderBW(Graphics g) {
        // draw conveyor
        g.setColor(Color.gray);
        g.fillRect((int) (conveyor.x - handler.getGameCamera().getxOffset()),
                (int) (conveyor.y - handler.getGameCamera().getyOffset()), conveyor.width, conveyor.height);

        // draw furnace
        g.setColor(Color.black);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);
        if (!activated) {
            g.setColor(Color.gray);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y + 20 - handler.getGameCamera().getyOffset()), width / 2, height - 40);
        } else {
            g.setColor(new Color(179, 179, 179));
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y + 20 - handler.getGameCamera().getyOffset()), width / 2, height - 40);
        }

    }

    public void killInArea() {
        Player player = handler.getCurrentPlayer();
        if (player.getHitbox().intersects(furnace)) {
            player.takeDamage(player.getHealth());
        }

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (e.getHitBox(0, 0).intersects(furnace)) {
                if (e.getZombieType() == ZombieType.STOKER) {
                    ((Stoker) e).getAngry();
                } else {
                    e.dieByTrap();
                }
            }
        }
    }

    public void moveEntitiesTowardFurnace() {
        Player player = handler.getCurrentPlayer();
        if (player.getHitbox().intersects(conveyor)) {
            player.addToMoveX(2);
        }

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (e.getHitBox(0, 0).intersects(conveyor)) {
                e.setxMove(7);
                e.moveX();
            }
        }
        for (Blood e : handler.getWorld().getEntityManager().getBlood()) {
            if (e.getBloodType() != -1 && e.getRect().intersects(conveyor)) {
                e.moveX();
            }
        }
    }

}

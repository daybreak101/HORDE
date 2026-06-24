package project.game.horde.zombieLogic;

import java.awt.Rectangle;
import java.util.ArrayList;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Licker;
import project.game.horde.entities.creatures.Stoker;
import project.game.horde.entities.creatures.Toxen;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;

public class Spawner {

    private Handler handler;
    private float x, y;
    private int room;
    private boolean isActive;
    private boolean isReady;
    private int counter, cooldown;
    private boolean collided;

    public Spawner(Handler handler, float x, float y, int room) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.room = room;
        isReady = false;
        isActive = true;
        counter = 0;
        cooldown = 30;
        collided = false;
    }

    public void tick() {
        counter++;
        if (counter >= cooldown && isActive) {
            collided = false;
            ArrayList<Entity> entities = new ArrayList<>();
            //entities.addAll(handler.getWorld().getEntityManager().getZombies());
            entities.addAll(handler.getWorld().getEntityManager().getEntities());

            for (Entity e : entities) {
                if (e.getCollisionBounds(-5f, -5f).intersects(new Rectangle((int) x - 50, (int) y - 50, 100, 100))) {
                    collided = true;
                    System.out.println("Spawn blocked!");
                }
            }

        }
        if (collided) {

            isReady = false;
        } else if (!collided) {
            isReady = true;
            counter = 0;
        }
    }

    public void spawnZombie(int id, float dspeed, int health) {
        dspeed = RandomUtil.nextFloat(dspeed - .3f, dspeed + .4f);
        float speed = 1.8f + dspeed;
        Zombie zombie = new Zombie(handler, id, x, y, speed, health);
        zombie.tick();
        handler.getWorld().getEntityManager().addZombie(zombie);
        if (handler.getCurrentPlayer().getPeer() != null) {
            handler.getCurrentPlayer().getPeer().spawnZombie(zombie);
        }
    }

    public void spawnLicker(int id, float dspeed, int health) {
        handler.getWorld().getEntityManager().addZombie(new Licker(handler, id, x, y, dspeed, health));
    }

    public void spawnToxen(int id, float dspeed, int health) {
        handler.getWorld().getEntityManager().addZombie(new Toxen(handler, id, x, y, dspeed, health));
    }

    public void spawnStoker(int id, float dspeed, int health) {
        handler.getWorld().getEntityManager().addZombie(new Stoker(handler, id, x, y, dspeed, health));
    }

    public void updateCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void setActive() {
        this.isActive = true;
    }

    public void setInactive() {
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }

    public int getRoom() {
        return room;
    }

}

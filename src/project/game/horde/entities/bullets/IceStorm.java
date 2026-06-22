package project.game.horde.entities.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.util.Random;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.Gun;

public class IceStorm extends Bullet {

    Ellipse2D.Float stormRadius;
    Rectangle[] particles;

    public IceStorm(Handler handler, float x, float y, Gun gun) {
        super(handler, x, y, 5, gun);
        speed = 1;
        particles = new Rectangle[20];
        Random rand = new Random();
        int dx, dy;
        for (int i = 0; i < 20; i++) {
            dx = RandomUtil.nextInt(-130, 130);
            dy = RandomUtil.nextInt(-130, 130);
            particles[i] = new Rectangle((int) (x + dx), (int) (y + dy), 5, 5);
        }
        stormRadius = new Ellipse2D.Float(-1000, -1000, 0, 0);
    }

    @Override
    public void tick() {
        // if bullet hits a rock, it should end there, since it cannot penetrate it
        while (travelTicker < speed) {
            moveX();
            moveY();
            travelTicker++;
            checkForImpact();

        }
        travelTicker = 0;

        die();
        postTick();
    }

    @Override
    public void postTick() {
        int dx, dy;
        for (int i = 0; i < 20; i++) {
            dx = RandomUtil.nextInt(-130, 130);
            dy = RandomUtil.nextInt(-130, 130);
            particles[i] = new Rectangle((int) (x + dx), (int) (y + dy), 5, 5);
        }
    }

    @Override
    public boolean checkForImpact() {
        cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), width + 1, height + 1);
        stormRadius = new Ellipse2D.Float((int) (x - 150), (int) (y - 150), 300, 300);

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (stormRadius.intersects(e.getHitBox(0, 0))) {
                e.getFreezeStatus().freeze(player);
            }
        }

        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
                rangeCounter = (int) (range * 1.5 + 1);
                return true;
            }
        }
        return false;
    }

    Timer stormTimer = new Timer(600);

    public void die() {
        rangeCounter++;
        if (player.getInv().getDeadshot() > -1) {
            if (rangeCounter >= range * 1.5) {
                speed = 0;
                xMove = 0;
                yMove = 0;
                stormTimer.tick();
                checkForImpact();
            }
        } else if (rangeCounter >= range) {
            speed = 0;
            xMove = 0;
            yMove = 0;
            stormTimer.tick();
            checkForImpact();
        }
        if (stormTimer.isReady()) {
            handler.getWorld().getEntityManager().getEntities().remove(this);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0, 160, 240, 20));
        g.fillOval((int) (stormRadius.x - handler.getGameCamera().getxOffset()),
                (int) (stormRadius.y - handler.getGameCamera().getyOffset()), (int) stormRadius.width,
                (int) stormRadius.height);
        if (rangeCounter < range) {
            g.setColor(Color.BLUE);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width * 2, height * 2);
        }

        g.setColor(new Color(255, 255, 255, 100));
        for (Rectangle r : particles) {
            g.fillRect(r.x - (int) handler.getGameCamera().getxOffset(),
                    r.y - (int) handler.getGameCamera().getyOffset(), r.width, r.height);
        }

    }

    @Override
    public void renderBW(Graphics g) {
        g.setColor(new Color(121, 121, 121, 20));
        g.fillOval((int) (stormRadius.x - handler.getGameCamera().getxOffset()),
                (int) (stormRadius.y - handler.getGameCamera().getyOffset()), (int) stormRadius.width,
                (int) stormRadius.height);
        if (rangeCounter < range) {
            g.setColor(Color.BLUE);
            g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width * 2, height * 2);
        }

        g.setColor(new Color(255, 255, 255, 100));
        for (Rectangle r : particles) {
            g.fillRect(r.x - (int) handler.getGameCamera().getxOffset(),
                    r.y - (int) handler.getGameCamera().getyOffset(), r.width, r.height);
        }

    }

    @Override
    public void moveX() {
        x += xMove;
    }

    @Override
    public void moveY() {
        y += yMove;
    }
}

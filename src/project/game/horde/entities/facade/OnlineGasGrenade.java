package project.game.horde.entities.facade;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;
import project.game.horde.weapons.GunVars;

public class OnlineGasGrenade extends OnlineBullet {

    private int counter, timer, explosionTimer;
    private Timer damageTicker = new Timer(30);
    private Timer gasTimer = new Timer(600);
    private Ellipse2D gasRadius;
    private boolean exploded;
    private int damage = GunVars.GAS_GRENADE_DAMAGE;
    private float destX, destY;
    boolean slowed = false;

    public OnlineGasGrenade(Handler handler, float x, float y, float destX, float destY, float angle) {
        super(handler, x, y, 10000, angle, false);
        x = (int) x;
        y = (int) y;
        width = 10;
        height = 10;
        counter = 0;
        timer = 60;
        explosionTimer = 65;
        speed = 8;
        this.angle = (float) Math.toRadians(angle - 90);
        xMove = (float) (Math.cos(this.angle));
        yMove = (float) (Math.sin(this.angle));
        this.destX = destX;
        this.destY = destY;
    }

    @Override
    public void tick() {
        counter++;
        if (counter >= timer) {
            if (counter >= explosionTimer) {
                gasRadius = new Ellipse2D.Float(x - 100, y - 100, 200, 200);
                exploded = true;
                Player player = handler.getCurrentPlayer();
                float dist = Utils.getEuclideanDistance(x, y, player.getX(), player.getY());
                //Sounds.playClip(GunSounds.grenade_launcher_explosion, 1, "grenade_explosion", ((float) 1.0f - dist / 2000), false);

            }
            if (exploded) {
                gasTimer.tick();
                damageTicker.tick();
                if (damageTicker.isReady()) {
                    findEntitiesInRadius();
                    damageTicker.resetTimer();
                }
                if (gasTimer.isReady()) {
                    handler.getWorld().getEntityManager().getEntities().remove(this);
                }
            }

        } else {
            while (travelTicker < speed) {
                moveX();
                moveY();
                travelTicker++;

                if (((int) x == (int) destX || (int) y == (int) destY) && !slowed) {
                    slowed = true;
                    speed = speed / 8;
                }

            }
            travelTicker = 0;
        }
    }

    @Override
    public boolean checkForImpact() {
        cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 2, bounds.height + 2);

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
                return true;
            }
        }

        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (
                !(e instanceof Barrier)
                //!handler.getWorld().getEntityManager().getBarriers().contains(e)
                    && e.getCollisionBounds(0, 0).intersects(cb)) {
                return true;
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
                return true;
            }
        }
        return false;

    }

    public void findEntitiesInRadius() {
        for (Zombie f : handler.getWorld().getEntityManager().getZombies()) {
            if (gasRadius.intersects(f.getHitBox(0, 0))) {
            }
        }

    }

    @Override
    public void moveX() {
        x += xMove;
        if (checkForImpact()) {
            speed = speed / 4;
            x -= xMove;
            xMove = -xMove;
        }

    }

    @Override
    public void moveY() {
        y += yMove;
        if (checkForImpact()) {
            speed = speed / 4;
            y -= yMove;
            yMove = -yMove;
        }
    }

    int i = 0;

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(150, 200, 100));
        g.fillOval((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);

        if (gasRadius != null) {
            if (gasTimer.counter >= 580) {
                i++;
                g.drawImage(Assets.gas_cloud[i], (int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
                        (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()), (int) gasRadius.getWidth(),
                        (int) gasRadius.getHeight(), null);
            } else {
                g.drawImage(Assets.gas_cloud[0], (int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
                        (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()), (int) gasRadius.getWidth(),
                        (int) gasRadius.getHeight(), null);
            }

            // g.setColor(new Color(150, 200, 100, 150));
            // g.fillOval((int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
            // (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()),
            // (int) gasRadius.getWidth(), (int) gasRadius.getHeight());
        }
    }

}

package project.game.horde.entities.bullets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.perks.PhD;
import project.game.horde.weapons.Gun;

public class Rocket extends Bullet {

    private Ellipse2D explosionRadius;

    public Rocket(Handler handler, float x, float y, Gun gun) {
        super(handler, x, y, 1000, gun);
    }

    @Override
    public void tick() {
        // if bullet hits a rock, it should end there, since it cannot penetrate it
        while (travelTicker < speed) {
            moveX();
            moveY();
            if (checkForImpact()) {
                findEntitiesInRadius();
                break;
            }
            travelTicker++;
        }
        travelTicker = 0;

        die(player);
        postTick();
    }

    public void findEntitiesInRadius() {
        explosionRadius = new Ellipse2D.Float(x - 150, y - 150, 300, 300);
        handler.getWorld().getEntityManager().addExplosion(new Explosion(handler, x - 150, y - 150, 300, 300, gunFiredFrom.isUpgraded()));

        float damageMultiplier = 1;
        if (player.getInv().getPhd() == 3) {
            damageMultiplier += PhD.LVL3_EXPLOSIVEDAMAGEBUFF;
        }
        if (player.getInv().getStronghold() > -1) {
            damageMultiplier += player.getStrongholdDamageMultiplier();
        }

        for (Zombie f : handler.getWorld().getEntityManager().getZombies()) {
            if (explosionRadius.intersects(f.getHitBox(0, 0))) {
                f.takeDamage((int) (gunFiredFrom.getDamage() * damageMultiplier), player);
                float currentPercent = (float) ((float) f.getHealth() / (float) f.getMaxHealth());
                float thirtyPercent = (float) ((float) (f.getMaxHealth() * 3 / 10) / (float) f.getMaxHealth());
                if (currentPercent < thirtyPercent && f.getZombieType() == 0) {
                    f.turnToCrawler();
                }
            }
        }

        if (explosionRadius.intersects(player.getCollisionBounds(0, 0))) {
            player.takeExplosionDamage(gunFiredFrom.getDamage() / 50);
        }
    }

    @Override
    public boolean checkForImpact() {
        cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 1, bounds.height + 1);

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (e.getHitBox(0, 0).intersects(cb)) {
                handler.getWorld().getEntityManager().getEntities().remove(this);
                return true;
            }
        }
        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
                handler.getWorld().getEntityManager().getEntities().remove(this);
                return true;
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
                handler.getWorld().getEntityManager().getEntities().remove(this);
                return true;
            }
        }

        return false;
    }

    @Override
    public void render(Graphics g) {
        //g.setColor(Color.yellow);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        int dx = 0;
        int dy = 0;
        g2d.rotate(angle - Math.PI / 2, x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset());
        g.drawImage(Assets.rpg_rocket,
                Math.round(x - handler.getGameCamera().getxOffset()) + dx,
                Math.round(y - handler.getGameCamera().getyOffset()) + dy, 10, 50, null);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);
        g2d.setTransform(old);
    }
}

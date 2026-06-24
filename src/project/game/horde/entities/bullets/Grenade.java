package project.game.horde.entities.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.Handler;
import project.game.horde.perks.PhD;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Utils;
import project.game.horde.weapons.Gun;
import project.game.horde.weapons.GunVars;

public class Grenade extends Bullet {

    private int counter, timer, explosionTimer;
    private Ellipse2D explosionRadius;
    private Color color;
    private boolean isImpact, impacted = false;
    private float destX, destY;
    private boolean isUpgraded;
    int fuseStart = 0;

    //new normal grenades
    public Grenade(Handler handler, float x, float y, boolean isImpact, float destX, float destY, double radianOffset, Player player, int fuseStart) {
        super(handler, x, y, 10000, player);
        x = (int) x;
        y = (int) y;
        width = 10;
        height = 10;
        this.fuseStart = fuseStart;
        counter = fuseStart;
        timer = 60;
        explosionTimer = 300;
        speed = 8;
        this.color = Color.orange;
        this.isImpact = isImpact;
        xMove = (float) (Math.cos(angle + radianOffset));
        yMove = (float) (Math.sin(angle + radianOffset));
        this.destX = destX;
        this.destY = destY;
        isUpgraded = false;
    }

    // normal grenades
    public Grenade(Handler handler, float x, float y, boolean isImpact, float destX, float destY, double radianOffset, Player player) {
        super(handler, x, y, 10000, player);
        x = (int) x;
        y = (int) y;
        width = 10;
        height = 10;
        counter = 0;
        timer = 60;
        explosionTimer = 65;
        speed = 8;
        this.color = Color.orange;
        this.isImpact = isImpact;
        xMove = (float) (Math.cos(angle + radianOffset));
        yMove = (float) (Math.sin(angle + radianOffset));
        this.destX = destX;
        this.destY = destY;
        isUpgraded = false;
    }

    public Grenade(Handler handler, float x, float y, boolean isImpact, float destX, float destY, Player player, Gun gun) {
        super(handler, x, y, 10000, player);
        x = (int) x;
        y = (int) y;
        width = 10;
        height = 10;
        counter = 0;
        timer = 60;
        explosionTimer = 65;
        speed = 8;
        this.color = Color.orange;
        this.isImpact = isImpact;
        xMove = (float) (Math.cos(angle));
        yMove = (float) (Math.sin(angle));
        this.destX = destX;
        this.destY = destY;
        gunFiredFrom = gun;
        isUpgraded = gunFiredFrom.isUpgraded();
    }

//	// idk
    public Grenade(Handler handler, float x, float y, Color color) {
        super(handler, x, y, 10000, color);
        width = 10;
        height = 10;
        counter = 0;
        timer = 7;
        explosionTimer = 65;
        speed = 15;
        this.color = color;
        isImpact = false;
        xMove = (float) (speed * Math.cos(angle));
        yMove = (float) (speed * Math.sin(angle));
    }

    @Override
    public void tick() {
        counter++;
        if (counter >= timer && !isImpact) {
            if (counter + fuseStart >= explosionTimer) {
                findEntitiesInRadius();
                handler.getWorld().getEntityManager().getEntities().remove(this);
                return;
            }
        } else if (isImpact && slowed) {
            findEntitiesInRadius();
            handler.getWorld().getEntityManager().getEntities().remove(this);
            return;
        } else {
//			if (isImpact) {
//				if (checkForImpact()) {
//					findEntitiesInRadius();
//					handler.getWorld().getEntityManager().getEntities().remove(this);
//				}
//			}
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
        if (impacted) {
            findEntitiesInRadius();
            handler.getWorld().getEntityManager().getEntities().remove(this);

        }
    }

    boolean slowed = false;

    @Override
    public boolean checkForImpact() {
        cb = new Rectangle((int) (x), (int) (y), width, height);

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
                return true;
            }
        }

        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
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

    //2000 * 2.5
    public void findEntitiesInRadius() {
        float damageMultiplier = 1;
        if (player.getInv().getPhd() == 3) {
            damageMultiplier += PhD.LVL3_EXPLOSIVEDAMAGEBUFF;
        }
        if (player.getInv().getStronghold() > -1) {
            damageMultiplier += player.getStrongholdDamageMultiplier();
        }

        explosionRadius = new Ellipse2D.Float(x - 100, y - 100, 200, 200);
        for (Zombie f : handler.getWorld().getEntityManager().getZombies()) {
            int damage;
            if (gunFiredFrom != null) {
                damage = (int) (gunFiredFrom.getDamage());
            } else {
                damage = GunVars.GRENADE_DAMAGE;
            }
            if (explosionRadius.intersects(f.getHitBox(0, 0))) {
                f.takeDamage((int) (damage * damageMultiplier), player);
                float currentPercent = (float) ((float) f.getHealth() / (float) f.getMaxHealth());
                float thirtyPercent = (float) ((float) (f.getMaxHealth() * 0.3f) / (float) f.getMaxHealth());
                if (f.getHealth() > 0 && currentPercent < thirtyPercent && f.getZombieType() == 0) {
                    f.turnToCrawler();
                }
            }
        }

        if (explosionRadius.intersects(player.getCollisionBounds(0, 0))) {
            player.takeExplosionDamage(60);
        }
        handler.getWorld().getEntityManager().addExplosion(new Explosion(handler, x - 100, y - 100, 200, 200, isUpgraded));
        float dist = Utils.getEuclideanDistance(x, y, player.getX(), player.getY());
//		System.out.println("grenadeX: " + x + ", grenadeY: " + y);
//		System.out.println("playerX: " + handler.getPlayer().getX() + ", playerY: " + handler.getPlayer().getY());
//		System.out.println("dist:" + dist);

        //Sounds.playClip(GunSounds.grenade_launcher_explosion, 1, "grenade_explosion", ((float) 1.0f - dist / 2000), false);
        Sounds.playClip(GunSounds.GRENADE_LAUNCHER_EXPLOSION_ID, 1, ((float) 1.0f - dist / 2000), false);
    }

    public void findPlayerInRadius() {
        explosionRadius = new Ellipse2D.Float(x - 100, y - 100, 200, 200);
        handler.getWorld().getEntityManager().addExplosion(new Explosion(handler, x - 100, y - 100, 200, 200, isUpgraded));
        // sound maybe here
        if (explosionRadius.intersects(player.getCollisionBounds(0, 0))) {
            player.takeExplosionDamage(20);
        }
    }

    @Override
    public void moveX() {
        x += xMove;
        if (checkForImpact()) {
            if (isImpact) {
                impacted = true;
            } else {
                speed = speed / 4;
                x -= xMove;
                xMove = -xMove;
            }
        }

    }

    @Override
    public void moveY() {
        y += yMove;
        if (checkForImpact()) {
            if (isImpact) {
                impacted = true;
            } else {
                speed = speed / 4;
                y -= yMove;
                yMove = -yMove;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(150, 200, 100));
        g.fillOval((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);
    }



}

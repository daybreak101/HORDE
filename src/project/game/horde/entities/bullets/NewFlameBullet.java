package project.game.horde.entities.bullets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.Gun;

public class NewFlameBullet extends Bullet {

    ArrayList<Zombie> zombiesHit;
    int frame;
    private Shape cb;
    int currentAlpha = 0;

    public NewFlameBullet(Handler handler, float x, float y, int range, Gun gun, int frame) {
        super(handler, x, y, range, gun);
        //isUpgraded = gunFiredFrom.isUpgraded();
        this.frame = frame;
        this.speed = 1;
        width = 200;
        height = 70;
        angle = (float) Math.atan2(-(mouseY), -(mouseX));
        xMove = (float) Math.cos(angle);
        yMove = (float) Math.sin(angle);
        System.out.println(angle);
        zombiesHit = new ArrayList<>();
    }

    @Override
    public void tick() {
        // if bullet hits a rock, it should end there, since it cannot penetrate it
        while (checkForWallImpact()) {
            width -= 10;
        }
        while (travelTicker < speed) {
            moveX();
            moveY();
            travelTicker++;
            if (checkForImpact()) {
                if ((zombiesHit.size() >= 6 && player.getInv().getDoubletap() >= 1 || zombiesHit.size() >= 4)) {
                    System.out.println("impacted");
                    break;
                }
                width -= 10;
            }

        }
        travelTicker = 0;

        die(player);
        if (currentAlpha < 24) {
            currentAlpha++;
        }
    }

    private Shape getRotatedRectangle(float x, float y, int width, int height, float angle) {
        // Create a rectangle
        Rectangle rect = new Rectangle((int) x, (int) y, width, height);

        // Create an AffineTransform instance
        AffineTransform transform = new AffineTransform();
        transform.rotate(angle, rect.getX(), rect.getY() + rect.height / 2);

        // Create a Path2D object from the rectangle
        Path2D path = new Path2D.Double();
        path.append(rect, false);

        // Apply the transformation to the path
        Shape rotatedRect = transform.createTransformedShape(path);
        return rotatedRect;
    }

    @Override
    public boolean checkForImpact() {
        cb = getRotatedRectangle(x, y - height / 2, width, height, angle);

        float damageMultiplier = 1;
        if (gunFiredFrom.getPlayer().getInv().getStronghold() > -1) {
            damageMultiplier += gunFiredFrom.getPlayer().getStrongholdDamageMultiplier();
        }

//		for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
//			if (!handler.getWorld().getEntityManager().getBarriers().contains(e)
//					&& cb.intersects(e.getCollisionBounds(0, 0))) {
//				return true;
//			}
//		}
//		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
//			if (cb.intersects(e.getCollisionBounds(0, 0))) {
//				return true;
//			}
//		}
        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (!zombiesHit.contains(e) && cb.intersects(e.getHitBox(0, 0))) {
                e.takeDamage((int) (gunFiredFrom.getDamage() * damageMultiplier), player);
                e.getBurnStatus().setBurn(Math.round(gunFiredFrom.getDamage() / 2 * damageMultiplier));
                zombiesHit.add(e);
                // handler.getWorld().getEntityManager().getEntities().remove(this);
            }
        }
        return false;
    }

    public boolean checkForWallImpact() {
        cb = getRotatedRectangle(x, y - height / 2, width, height, angle);
        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (cb.intersects(e.getCollisionBounds(0, 0))) {
                return true;
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (cb.intersects(e.getCollisionBounds(0, 0))) {
                return true;
            }
        }
        return false;
    }

    Timer waitToRender = new Timer(2);

    @Override
    public void render(Graphics g) {

        if (waitToRender.checkIsReady()) {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform old = g2d.getTransform();
            int dy = -50;
            int dx = 40;
            g2d.rotate(angle, x - handler.getGameCamera().getxOffset(), y - handler.getGameCamera().getyOffset());
            if (gunFiredFrom.isUpgraded()) {
                g2d.drawImage(Assets.upgraded_flamethrower_bullet[frame][currentAlpha],
                        Math.round(x - handler.getGameCamera().getxOffset()) + dx,
                        Math.round(y - handler.getGameCamera().getyOffset()) + dy, width, 100, null);
            } else {
                g2d.drawImage(Assets.flamethrower_bullet[frame][currentAlpha],
                        Math.round(x - handler.getGameCamera().getxOffset()) + dx,
                        Math.round(y - handler.getGameCamera().getyOffset()) + dy, width, 100, null);
            }

            g2d.setTransform(old);
        } else {
            waitToRender.tick();
        }

    }

  
}

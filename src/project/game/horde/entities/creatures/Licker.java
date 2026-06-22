package project.game.horde.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import project.game.horde.graphics.Animation;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;

public class Licker extends Zombie {

    public Licker(Handler handler, int id, float x, float y, float dspeed, int health) {
        super(handler, id, x, y, dspeed, health);
        zombieType = LICKER;
        zombieAnim = new Animation(300, Assets.lickerAnim);
        BWzombieAnim = new Animation(300, BWAssets.lickerAnim);
        this.speed = 5.0f + dspeed + 0.5f;
        this.health = health / 2;
        this.width = 90;
        this.height = 90;
        bounds.width = 20;
        bounds.height = 20;
        hitbox = new Rectangle(0, 0, width, height);
    }

    @Override
    public void postTick() {
        if (!justAttacked) {
            zombieAnim.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        float moveToX = closestPlayer.getCenterX() - handler.getGameCamera().getxOffset();
        float moveToY = closestPlayer.getCenterY() - handler.getGameCamera().getyOffset();
        float angle = (float) Math
                .toDegrees(Math.atan2(-(x - handler.getGameCamera().getxOffset() - moveToX + width / 2),
                        y - handler.getGameCamera().getyOffset() - moveToY + height / 2));
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
                y - handler.getGameCamera().getyOffset() + height / 2);
        g2d.drawImage(Assets.shadow, (int) (x + 10 - handler.getGameCamera().getxOffset()),
                (int) (y + 10 - handler.getGameCamera().getyOffset()), 70, 70, null);

        if (burnStatus.isBurning()) {
            g2d.setColor(Color.orange);
            g2d.fillOval((int) (x + 10 - handler.getGameCamera().getxOffset()),
                    (int) (y + 10 - handler.getGameCamera().getyOffset()), 70, 70);
        }
        g2d.drawImage(zombieAnim.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

        g2d.setTransform(old);
    }

    @Override
    public void renderBW(Graphics g) {
        float moveToX = closestPlayer.getCenterX() - handler.getGameCamera().getxOffset();
        float moveToY = closestPlayer.getCenterY() - handler.getGameCamera().getyOffset();
        float angle = (float) Math
                .toDegrees(Math.atan2(-(x - handler.getGameCamera().getxOffset() - moveToX + width / 2),
                        y - handler.getGameCamera().getyOffset() - moveToY + height / 2));
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
                y - handler.getGameCamera().getyOffset() + height / 2);
        g2d.drawImage(Assets.shadow, (int) (x + 10 - handler.getGameCamera().getxOffset()),
                (int) (y + 10 - handler.getGameCamera().getyOffset()), 70, 70, null);

        if (burnStatus.isBurning()) {
            g2d.setColor(new Color(179, 179, 179));
            g2d.fillOval((int) (x + 10 - handler.getGameCamera().getxOffset()),
                    (int) (y + 10 - handler.getGameCamera().getyOffset()), 70, 70);
        }

        g2d.drawImage(BWzombieAnim.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

        g2d.setTransform(old);
    }

}

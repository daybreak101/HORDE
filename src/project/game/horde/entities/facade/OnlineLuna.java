package project.game.horde.entities.facade;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Creature;
import project.game.horde.graphics.Animation;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class OnlineLuna extends Entity {

    protected boolean justAttacked = false;
    protected long timer = 0;
    protected Rectangle hitbox;
    protected int attackTicker = 0, attackTimer = 100;
    protected String owner;
    private float angle;
    private Animation wolfAnimation;

    public OnlineLuna(Handler handler, String owner) {
        super(handler, 0, 0, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.owner = owner;
        wolfAnimation = new Animation(100, Assets.aurora);
        this.x = handler.getWorld().getEntityManager().getSpecificPlayer(owner).getX();
        this.y = handler.getWorld().getEntityManager().getSpecificPlayer(owner).getY();
    }

    @Override
    public void render(Graphics g) {
        wolfAnimation.tick();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
                y - handler.getGameCamera().getyOffset() + height / 2);

        int offset = 30;
        g2d.drawImage(wolfAnimation.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset() - offset),
                (int) (y - handler.getGameCamera().getyOffset() - offset), width + offset * 2, height + offset * 2, null);

        g2d.setTransform(old);

    }

    @Override
    public void renderBW(Graphics g) {
        render(g);
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

}

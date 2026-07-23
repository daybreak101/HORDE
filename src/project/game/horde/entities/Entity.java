package project.game.horde.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.InvisibleBounds;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.Handler;

public abstract class Entity {

    protected Handler handler;
    protected float x, y;
    protected int width, height;
    protected int health;
    protected boolean active = true;
    public static final int DEFAULT_HEALTH = 100;
    protected Rectangle bounds;
    protected int closestNode;
    protected boolean renderThis = false;

    public Entity(Handler handler, float x, float y, int width, int height) {
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;
        bounds = new Rectangle(0, 0, width, height);
    }

    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }
            if (!(e instanceof Barrier) && e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (e.equals(this)) {
                continue;
            }
            if (e.getCollisionBounds(0, 0).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (InvisibleBounds e : handler.getWorld().getEntityManager().getBoundaries()) {
            if (e.getCollisionBounds().intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    Rectangle collisionBounds = new Rectangle();
    public Rectangle getCollisionBounds(float xOffset, float yOffset) {
        collisionBounds.setBounds((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), (int) (bounds.width), (int) (bounds.height));
        return collisionBounds;
        //return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), (int) (bounds.width), (int) (bounds.height));
    }

    Ellipse2D.Float circularBounds = new Ellipse2D.Float();
    public Ellipse2D.Float getCircularBounds() {
        circularBounds.setFrame((int) (x + bounds.x), (int) (y + bounds.y), (int) (bounds.width), (int) (bounds.height));
        return circularBounds;
        //return new Ellipse2D.Float((int) (x + bounds.x), (int) (y + bounds.y), (int) (bounds.width), (int) (bounds.height));
    }

    public void setClosestNode() {
        closestNode = handler.getWorld().getPathingLogic().getClosestNode(getCenterX(), getCenterY());
    }

    public int getClosestNode() {
        //if(closestNode == 0)
        closestNode = handler.getWorld().getPathingLogic().getClosestNode(getCenterX(), getCenterY());
        return closestNode;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getCenterX() {
        return (int) x + width / 2;
    }

    public int getCenterY() {
        return (int) y + height / 2;
    }

    public float getRenderX() {
        return x - handler.getGameCamera().getxOffset();
    }

    public float getRenderY() {
        return y - handler.getGameCamera().getyOffset();
    }

    public void tick() {
    }

    public abstract void render(Graphics g);

    public void die(Player player) {
    }

    public void dontMove() {
    }

    public void fulfillInteraction(Player player) {
        // TODO Auto-generated method stub

    }

    public void setRenderThis(boolean renderThis) {
        this.renderThis = renderThis;
    }

    public boolean getRenderThis() {
        return renderThis;
    }

    Rectangle renderBounds = new Rectangle();
    public Rectangle getRenderBounds() {
        renderBounds.setBounds((int) x, (int) y, width, height);
        return renderBounds;
        //return new Rectangle((int) x, (int) y, width, height);
    }

}

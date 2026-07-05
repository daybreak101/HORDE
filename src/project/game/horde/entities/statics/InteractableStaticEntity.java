package project.game.horde.entities.statics;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

import project.game.horde.main.Handler;

public abstract class InteractableStaticEntity extends StaticEntity {

    protected Rectangle trigger;
    protected int cooldown, cooldownTimer;
    protected String triggerText;
    protected int ID;
    protected boolean usedByOtherPlayer = false;

    public InteractableStaticEntity(Handler handler, int id, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        trigger = new Rectangle(0, 0, 0, 0);
        cooldown = 200;
        triggerText = "";
        cooldownTimer = cooldown;
        this.ID = id;
    }

    @Override
    public void tick() {
        cooldownTimer++;
        //trigger = new Rectangle((int) (x + bounds.x  - handler.getGameCamera().getxOffset() - 5), (int) (y + bounds.y  - handler.getGameCamera().getyOffset() - 5), bounds.width + 10, bounds.height + 10);
        boolean found = false;

        //worry about this later. create new shape based of intersection of walls to be new trigger
        Line2D.Float line = new Line2D.Float(handler.getCurrentPlayer().getCenterX(),
                handler.getCurrentPlayer().getCenterY(), this.x + this.width / 2, this.y + this.height / 2);
        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (e == this) {
                //System.out.println(e.getTriggerText());
            } else if (line.intersects(e.getCollisionBounds(0, 0))) {
                found = true;
                break;
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (line.intersects(e.getCollisionBounds(0, 0))) {
                handler.getWorld().getEntityManager().getEntities().remove(this);
                found = true;
                break;
            }
        }
        if (!found) {
            trigger = new Rectangle((int) (x - 10), (int) (y - 10), bounds.width + 20, bounds.height + 20); 
        }else {
            trigger = new Rectangle(0, 0, 0, 0);
        }
        postTick();
    }

    public void sendInteractableBusy() {
        if (handler.getCurrentPlayer().getPeer() != null) {
            handler.getCurrentPlayer().getPeer().sendInteractionBusy(ID, handler.getCurrentPlayer().getUsername(), true);
        }
    }

    public void sendInteractableReady() {
        if (handler.getCurrentPlayer().getPeer() != null) {
            handler.getCurrentPlayer().getPeer().sendInteractionBusy(ID, handler.getCurrentPlayer().getUsername(), false);
        }
    }

    public Rectangle getTriggerRange() {
        return trigger;
    }

    public String getTriggerText() {
        return triggerText;
    }

    public void fulfillInteraction() {
    }

    public void postTick() {
    }

    public int getID() {
        return ID;
    }

    public void setUsedByOther(boolean used) {
        usedByOtherPlayer = used;
    }

}

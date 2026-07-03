package project.game.horde.entities.powerups;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.PowerupSounds;
import project.game.horde.sounds.Sounds;

public class Nuke extends PowerUps {

    int alpha = 255;
    boolean fulfilledInteraction = false;

    public Nuke(Handler handler, int id, float x, float y) {
        super(handler, id, x, y, true);
        name = "Nuke";
        icon = null;
        floatingAsset = Assets.nuke;
        glow = Assets.yellowStar;
    }

    @Override
    public void tick() {
        cooldownTimer++;
        trigger = new Rectangle((int) (x), (int) y, width, height);

        if (cooldownTimer >= cooldown || activeCounter >= cooldown) {
            unbuff();
            handler.getWorld().getEntityManager().getPowerups().remove(this);
            handler.getWorld().getRoundLogic().stopSpawningTemporarily(false);
        } else if (fulfilledInteraction) {
            cooldownTimer = 0;
            if (activeCounter >= cooldownTimer) {
                alpha--;
            }
            if (alpha <= 0) {
                handler.getWorld().getEntityManager().getPowerups().remove(this);
                handler.getWorld().getRoundLogic().stopSpawningTemporarily(false);

            }
        } else if (pickedUp) {
            cooldownTimer = 0;
            activeCounter++;
            fulfillInteraction(playerPicked);

            fulfilledInteraction = true;
        } else if (!pickedUp && cooldownTimer < cooldown) {
            checkPickedUp();
        }
    }

    @Override
    public void fulfillInteraction(String username) {
        //Sounds.playClip(PowerupSounds.nukePickedUp, 1, "nuke", 1, false);
        Sounds.playClip(PowerupSounds.NUKE_PICKED_UP_ID, 1, 1, false);
        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            e.dieByNuke();
            handler.getWorld().getEntityManager().getEntities().remove(e);
        }
        handler.getWorld().getEntityManager().getZombies().clear();
        handler.getCurrentPlayer().getInv().gainPoints(400);
        handler.getWorld().getRoundLogic().stopSpawningTemporarily(true);
    }

    @Override
    public void render(Graphics g) {
        int offset = 25;

        if (!pickedUp) {
            // g.drawImage(glow, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

            // if (floatingAsset != null && isVisible) {
            //     g.drawImage(floatingAsset, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            // }
            g.drawImage(glow,
                    (int) (x - handler.getGameCamera().getxOffset() - offset),
                    (int) (y - handler.getGameCamera().getyOffset() - offset),
                    width + offset * 2, height + offset * 2, null);

            if (floatingAsset != null && isVisible) {
                offset = 10;
                g.drawImage(floatingAsset,
                        (int) (x - handler.getGameCamera().getxOffset() + offset),
                        (int) (y - handler.getGameCamera().getyOffset() + offset),
                        width - offset * 2, height - offset * 2, null);
            }
        } else {
            g.setColor(new Color(255, 255, 255, alpha));
            g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());
        }
    }
}

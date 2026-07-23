package project.game.horde.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.ImageUtils;
import project.game.horde.main.Handler;

public class PowerSwitch extends InteractableStaticEntity {

    private boolean isPowerOn = false;
    private int orientation;
    private BufferedImage powerOnAsset, powerOffAsset;

    public PowerSwitch(Handler handler, int id, float x, float y, int orientation) {
        super(handler, id, x, y, 0, 0);
        this.orientation = orientation;
        switch (orientation) {
            case 0 -> {
                width = 100;
                height = 50;
                powerOnAsset = Assets.powerOn;
                powerOffAsset = Assets.powerOff;
            }
            case 1 -> {
                width = 50;
                height = 100;
                powerOnAsset = ImageUtils.rotate(Assets.powerOn, 90);
                powerOffAsset = ImageUtils.rotate(Assets.powerOff, 90);
            }
            case 2 -> {
                width = 100;
                height = 50;
                powerOnAsset = ImageUtils.rotate(Assets.powerOn, 180);
                powerOffAsset = ImageUtils.rotate(Assets.powerOff, 180);
            }
            case 3 -> {
                width = 50;
                height = 100;
                powerOnAsset = ImageUtils.rotate(Assets.powerOn, 270);
                powerOffAsset = ImageUtils.rotate(Assets.powerOff, 270);
            }
        }
        // width = 100;
        // height = 50;

    }

    @Override
    public void fulfillInteraction(Player player) {
        // spin for perk
        if (usedByOtherPlayer) {
        } else if (!isPowerOn && cooldownTimer >= cooldown) {
            sendInteractableBusy();
            isPowerOn = true;
            handler.getWorld().setPowerOn(true);
            cooldownTimer = 0;
        }
    }

    @Override
    public void postTick() {
        if (!isPowerOn && usedByOtherPlayer) {
            isPowerOn = true;
            handler.getWorld().setPowerOn(true);
            cooldownTimer = 0;
            triggerText = "Busy";

        } else if (!isPowerOn && cooldownTimer >= cooldown) {
            triggerText = "Press F to turn on power";
        } else {
            triggerText = "Already powered on!";
        }
    }

    @Override
    public void render(Graphics g) {
        // g.setColor(new Color(100, 100, 255));
        // g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
        //         (int) (y - handler.getGameCamera().getyOffset()), width, height);

        if (isPowerOn) {
            g.drawImage(powerOnAsset, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else {
            g.drawImage(powerOffAsset, (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }
    }

}

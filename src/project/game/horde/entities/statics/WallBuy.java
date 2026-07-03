package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.AA12;
import project.game.horde.weapons.AK47;
import project.game.horde.weapons.AWP;
import project.game.horde.weapons.Arisaka;
import project.game.horde.weapons.Bren;
import project.game.horde.weapons.DoubleBarrel;
import project.game.horde.weapons.Flamethrower;
import project.game.horde.weapons.G18;
import project.game.horde.weapons.Glock17;
import project.game.horde.weapons.GrenadeLauncher;
import project.game.horde.weapons.Gun;
import project.game.horde.weapons.M16;
import project.game.horde.weapons.M1Garand;
import project.game.horde.weapons.M4;
import project.game.horde.weapons.M60;
import project.game.horde.weapons.P90;
import project.game.horde.weapons.Python;
import project.game.horde.weapons.RPD;
import project.game.horde.weapons.RPG;
import project.game.horde.weapons.Thompson;
import project.game.horde.weapons.Type100;
import project.game.horde.weapons.Uzi;
import project.game.horde.weapons.Winchester1901;

public class WallBuy extends InteractableStaticEntity {

    private String gunId;
    private int whatWall;
    private Gun gun4Sale;
    private int cost;
    private boolean cantAfford = false;
    private boolean gunOwned = false;
    private boolean gunOwnedUpgraded = false;
    private Gun gunOwnedRef = null;

    //top, bottom, right, left
    public WallBuy(Handler handler, int id, float x, float y, String gunId, int whatWall) {
        super(handler, id, x, y, 0, 0);
        this.whatWall = whatWall;
        this.gunId = gunId;
        // bounds = new Rectangle((int)x, (int)y, 1, 1);
        switch (whatWall) {
            case 0 -> { //South
                //this.y -= 10;
                width = 50;
                height = 25;
            }
            case 1 -> { // North
                //this.y += 10;
                width = 50;
                height = 25;
            }
            case 2 -> { // West
                //this.x += 10;
                width = 25;
                height = 50;
            }
            case 3 -> { // East
                //`this.x -= 10;
                width = 25;
                height = 50;
            }
            default -> {
            }
        }

    }

    @Override
    public void postTick() {
        switch (this.gunId) {
            case "Glock17" -> {
                gun4Sale = new Glock17(handler, handler.getCurrentPlayer());
                cost = 500;
            }
            case "Arisaka" -> {
                gun4Sale = new Arisaka(handler, handler.getCurrentPlayer());
                cost = 500;
            }
            case "M1Garand" -> {
                gun4Sale = new M1Garand(handler, handler.getCurrentPlayer());
                cost = 750;
            }
            case "AWP" -> {
                gun4Sale = new AWP(handler, handler.getCurrentPlayer());
                cost = 3000;
            }
            case "Bren" -> {
                gun4Sale = new Bren(handler, handler.getCurrentPlayer());
                cost = 2500;
            }
            case "AK47" -> {
                gun4Sale = new AK47(handler, handler.getCurrentPlayer());
                cost = 2250;
            }
            case "AA12" -> {
                gun4Sale = new AA12(handler, handler.getCurrentPlayer());
                cost = 2750;
            }
            case "DoubleBarrel" -> {
                gun4Sale = new DoubleBarrel(handler, handler.getCurrentPlayer());
                cost = 1500;
            }
            case "Flamethrower" -> {
                gun4Sale = new Flamethrower(handler, handler.getCurrentPlayer());
                cost = 3000;
            }
            case "G18" -> {
                gun4Sale = new G18(handler, handler.getCurrentPlayer());
                cost = 1200;
            }
            case "GrenadeLauncher" -> {
                gun4Sale = new GrenadeLauncher(handler, handler.getCurrentPlayer());
                cost = 5000;
            }
            case "M4" -> {
                gun4Sale = new M4(handler, handler.getCurrentPlayer());
                cost = 2000;
            }
            case "M16" -> {
                gun4Sale = new M16(handler, handler.getCurrentPlayer());
                cost = 1750;
            }
            case "M60" -> {
                gun4Sale = new M60(handler, handler.getCurrentPlayer());
                cost = 3500;
            }
            case "P90" -> {
                gun4Sale = new P90(handler, handler.getCurrentPlayer());
                cost = 1600;
            }
            case "Python" -> {
                gun4Sale = new Python(handler, handler.getCurrentPlayer());
                cost = 1800;
            }
            case "RPD" -> {
                gun4Sale = new RPD(handler, handler.getCurrentPlayer());
                cost = 2800;
            }
            case "RPG" -> {
                gun4Sale = new RPG(handler, handler.getCurrentPlayer());
                cost = 5000;
            }
            case "Thompson" -> {
                gun4Sale = new Thompson(handler, handler.getCurrentPlayer());
                cost = 1200;
            }
            case "Type100" -> {
                gun4Sale = new Type100(handler, handler.getCurrentPlayer());
                cost = 1100;
            }
            case "Uzi" -> {
                gun4Sale = new Uzi(handler, handler.getCurrentPlayer());
                cost = 1300;
            }
            case "Winchester1901" -> {
                gun4Sale = new Winchester1901(handler, handler.getCurrentPlayer());
                cost = 1500;
            }

        }
        // don't give a weapon player already has
        Player player = handler.getCurrentPlayer();
        for (Gun gun : player.getInv().getArsenal()) {
            if (gun != null && gun.getOriginalName().equals(gun4Sale.getOriginalName())) {
                gunOwnedRef = gun;
                gunOwned = true;
                gunOwnedUpgraded = gunOwnedRef.isUpgraded();
            }
        }
        if (cantAfford == true && cooldownTimer < cooldown) {
            triggerText = "Not enough points!";
        } else if (!gunOwned && cooldownTimer >= cooldown) {
            triggerText = "Press F to purchase " + gun4Sale.getName() + ":" + cost;
        } else if (gunOwnedUpgraded && cooldownTimer >= cooldown) {
            triggerText = "Press F to purchase ammo:" + 4500;
        } else if (gunOwned && cooldownTimer >= cooldown) {
            triggerText = "Press F to purchase ammo: " + cost / 2;
        } else {
            triggerText = "";
        }
    }

    @Override
    public void fulfillInteraction(Player player) {
        if (cooldownTimer >= cooldown) {
            if (!gunOwned && player.getInv().purchase(cost)) {
                Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
                cantAfford = false;
                cooldownTimer = 0;
                player.getInv().setGun(gun4Sale);
            } else if (gunOwnedUpgraded && player.getInv().purchase(4500)) {
                Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
                cantAfford = false;
                cooldownTimer = 0;
                gunOwnedRef.setCurrentReserve(gunOwnedRef.getMaxReserve());
            } else if (gunOwned && player.getInv().purchase(cost / 2)) {
                Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
                cantAfford = false;
                cooldownTimer = 0;
                gunOwnedRef.setCurrentReserve(gunOwnedRef.getMaxReserve());
            } else {
                Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
                cantAfford = true;
                cooldownTimer = 0;
            }

        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255, 255, 255));
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height);

    }

}

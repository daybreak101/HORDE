package project.game.horde.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.AA12;
import project.game.horde.weapons.AK47;
import project.game.horde.weapons.AWP;
import project.game.horde.weapons.Arisaka;
import project.game.horde.weapons.Bren;
import project.game.horde.weapons.DoubleBarrel;
import project.game.horde.weapons.Flamethrower;
import project.game.horde.weapons.G18;
import project.game.horde.weapons.GasGrenades;
import project.game.horde.weapons.Glock17;
import project.game.horde.weapons.GrenadeLauncher;
import project.game.horde.weapons.Gun;
import project.game.horde.weapons.M16;
import project.game.horde.weapons.M1911;
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

public class MysteryBox extends InteractableStaticEntity {

    private boolean isOpened;
    private Gun gun;
    private int isOpenedTimer, isOpenedTime;
    private boolean cantAfford = false;
    private boolean isSpecialGrenade = false;
    private BufferedImage gunImage = null,
            spinningGun = null;

    public MysteryBox(Handler handler, int id, float x, float y) {
        super(handler, id, x, y, 150, 75);
        triggerText = "Press F to spin for a random weapon";
        isOpened = false;
        isOpenedTime = 1000;
    }

    //spin for random weapon
    @Override
    public void fulfillInteraction(Player player) {
        //spin for weapon
        if (usedByOtherPlayer) {

        } else if (isOpened == false && cooldownTimer >= cooldown) {
            if (player.getInv().purchase(950)) {
                Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
                sendInteractableBusy();
                openSounds();
                isOpened = true;
                cantAfford = false;
                cooldownTimer = 0;
                gun = getRandomWeapon(player);
                handler.getGlobalStats().addBoxSpin();

                //don't give a weapon player already has
                while (player.getInv().checkArsenal(gun)) {
                    gun = getRandomWeapon(player);
                }
            } else {
                Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
                cantAfford = true;
                cooldownTimer = 0;
            }

        } //grab weapon
        else if (isOpened == true && cooldownTimer >= cooldown && isOpenedTimer < isOpenedTime) {
            cooldownTimer = 0;
            isOpened = false;
            isOpenedTimer = 0;
            if (gun.getOriginalName() == "Gas Grenades") {
                player.getInv().setSpecialGrenade(0);
            } else {
                player.getInv().setGun(gun);
            }
            handler.getGlobalStats().addBoxPull();
            closeSounds();
            sendInteractableReady();
        }

    }

    public void getRandomPicture() {
        Random rand = new Random();
        int rng = rand.nextInt(12);

        spinningGun = switch (rng) {
            case 1 ->
                Assets.ak47;
            case 2 ->
                Assets.p90;
            case 3 ->
                Assets.m4;
            case 4 ->
                Assets.rpd;
            case 5 ->
                Assets.rpg;
            case 6 ->
                Assets.winchester1901;
            case 7 ->
                Assets.awp;
            case 8 ->
                Assets.aa12;
            case 9 ->
                Assets.flamethrower;
            case 10 ->
                Assets.grenadeLauncher;
            case 11 ->
                Assets.gasGrenades;
            default ->
                Assets.glock17;
        };
    }

    public Gun getRandomWeapon(Player player) {
        Random rand = new Random();
        int rng = rand.nextInt(23);

        switch (rng) {
            case 1 -> {
                gunImage = Assets.ak47;
                return new AK47(handler, player);
            }
            case 2 -> {
                gunImage = Assets.p90;
                return new P90(handler, player);
            }
            case 3 -> {
                gunImage = Assets.m4;
                return new M4(handler, player);
            }
            case 4 -> {
                gunImage = Assets.rpd;
                return new RPD(handler, player);
            }
            case 5 -> {
                gunImage = Assets.rpg;
                return new RPG(handler, player);
            }
            case 6 -> {
                gunImage = Assets.winchester1901;
                return new Winchester1901(handler, player);
            }
            case 7 -> {
                gunImage = Assets.awp;
                return new AWP(handler, player);
            }
            case 8 -> {
                gunImage = Assets.aa12;
                return new AA12(handler, player);
            }
            case 9 -> {
                gunImage = Assets.flamethrower;
                return new Flamethrower(handler, player);
            }
            case 10 -> {
                gunImage = Assets.grenadeLauncher;
                return new GrenadeLauncher(handler, player);
            }
            case 11 -> {
                gunImage = Assets.gasGrenades;
                return new GasGrenades(handler, player);
            }
            case 12 -> {
                gunImage = Assets.glock17;
                return new Glock17(handler, player);
            }
            case 13 -> {
                gunImage = Assets.arisaka;
                return new Arisaka(handler, player);
            }
            case 14 -> {
                gunImage = Assets.bren;
                return new Bren(handler, player);
            }
            case 15 -> {
                gunImage = Assets.doubleBarrel;
                return new DoubleBarrel(handler, player);
            }
            case 16 -> {
                gunImage = Assets.g18;
                return new G18(handler, player);
            }
            case 17 -> {
                gunImage = Assets.m16;
                return new M16(handler, player);
            }
            case 18 -> {
                gunImage = Assets.m60;
                return new M60(handler, player);
            }
            case 19 -> {
                gunImage = Assets.python;
                return new Python(handler, player);
            }
            case 20 -> {
                gunImage = Assets.thompson;
                return new Thompson(handler, player);
            }
            case 21 -> {
                gunImage = Assets.type100;
                return new Type100(handler, player);
            }
            case 22 -> {
                gunImage = Assets.uzi;
                return new Uzi(handler, player);
            }
            default -> {
                gunImage = Assets.m1911;
                return new M1911(handler, player);
            }
        }
    }

    public void spinningSounds() {
        float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
        if (isOpened && isOpenedTimer < isOpenedTime) {
            Sounds.playClip(InteractSounds.MYSTERYBOX_MUSIC, 1.0f, newvolume, false);
        } else {
            Sounds.stopClip(InteractSounds.MYSTERYBOX_MUSIC);
        }
    }

    public void closeSounds() {
        float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
        Sounds.playClip(InteractSounds.MYSTERYBOX_CLOSE, 1.0f, newvolume, false);
    }

    public void openSounds() {
        float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
        Sounds.playClip(InteractSounds.MYSTERYBOX_OPEN, 1.1f, newvolume, false);
    }

    Timer rerenderGunImage = new Timer(10);

    @Override
    public void postTick() {
        rerenderGunImage.tick();
        if (rerenderGunImage.isReady()) {
            getRandomPicture();
        }

        if (usedByOtherPlayer) {
            isOpenedTimer = 0;
            triggerText = "Busy";
        } else if (cantAfford == true && cooldownTimer < cooldown) {
            isOpenedTimer = 0;
            triggerText = "Not enough points!";
        } else if (isOpened == true && cooldownTimer >= cooldown) {
            triggerText = "Press F to trade weapon for " + gun.getName();
            isOpenedTimer++;
            if (isOpenedTimer >= isOpenedTime) {
                isOpened = false;
                closeSounds();
                sendInteractableReady();
            }
        } else if (isOpened == false && cooldownTimer >= cooldown) {
            isOpenedTimer = 0;
            triggerText = "Press F to spin for a random weapon: 950";
            gun = null;
        } else if (isOpened == true) {
            spinningSounds();
            triggerText = "Spinning...";
        } else {
            triggerText = "";
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.mysteryBox, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        if (isOpened == true && cooldownTimer >= cooldown) {
            g.drawImage(gunImage, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        } else if (isOpened == true && cooldownTimer < cooldown) {
            g.drawImage(spinningGun, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        }

    }


}

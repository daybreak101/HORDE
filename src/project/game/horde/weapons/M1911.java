package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.bullets.Grenade;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.OnlineBullet;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class M1911 extends Gun {

    public M1911(Handler handler, Player player) {
        super(handler, player, GunVars.M1911_DAMAGE, GunVars.M1911_FIRERATE, GunVars.M1911_RELOADSPEED,
                GunVars.M1911_GUNCLIP, GunVars.M1911_MAXRESERVE, GunVars.M1911_WEIGHT, GunVars.M1911_RANGE, 90);
        this.name = GunVars.M1911_NAME;
        originalName = name;
        upgradedName = GunVars.M1911_UPGRADEDNAME;
        reloadSound = GunSounds.M1911_RELOAD_ID;
        top = Assets.m1911_top;
        gunImageDim = new GunImageDim(40, 50, 20, 80);
    }

    @Override
    public void shootOnline(int x, int y, float angle, float volume) {
        handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, x,
                y, range, angle, isUpgraded));

        if (isUpgraded) {
            Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
        }
        Sounds.playClip(GunSounds.M1911_SHOT_ID, 1, volume, false);
    }

    public void shoot() {
    }

    int heldShot = 0;
    int altHeldShot = 0;

    // guess i figured out how to work semi-auto guns
    public void postTick() {
        if (player.getMouseManager().isLeftPressed() && !isReloading) {
            heldShot++;
        } else if (!player.getMouseManager().isLeftPressed() && heldShot > 0 && !isReloading
                && player.getPlayerInput().canShoot()) {
            shootSingleShot();
            heldShot = 0;
        }

        if (player.getMouseManager().isRightPressed() && !isAltReloading) {
            altHeldShot++;
        } else if (!player.getMouseManager().isRightPressed()
                && altHeldShot > 0
                && !isAltReloading
                && player.getPlayerInput().canShoot()) {
            altShootSingleShot();
            altHeldShot = 0;
        }

    }

    public void uniqueUpgrades() {
        damage = 1000;
        clip = 8;
        maxReserve = 48;
        currentReserve = maxReserve;
        isDual = true;
        weight = weight * 2;
        currentClip = clip;
        currentAltClip = clip;
    }

    public void shootSingleShot() {
        if (currentClip > 0 && !isReloading) {
            readyToFire = false;
            Sounds.playClip(GunSounds.M1911_SHOT_ID, 1, -1.0f, false);

            if (isUpgraded) {
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
            }
            currentClip--;
            if (isUpgraded) {
                handler.getWorld().getEntityManager()
                        .addEntity(new Grenade(handler, player.getCenterX(), player.getCenterY(),
                                isUpgraded, player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset(),
                                player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset(), player,
                                this));
                if (player.getPeer() != null) {
                    player.getPeer().sendPlayerGrenadeLauncherShot(player.getUsername(),
                            (int) (player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
                            (int) (player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset())
                    );
                }
            } else {
                handler.getWorld().getEntityManager().addEntity(
                        new Bullet(handler, player.getCenterX(), player.getCenterY(), range, this));
                if (player.getPeer() != null) {
                    player.getPeer().sendPlayerShot(player.getUsername());
                }
            }

            timerToFire = 0;
        }
    }

    public void altShootSingleShot() {
        if (currentAltClip > 0 && !isAltReloading) {
            altReadyToFire = false;
            Sounds.playClip(GunSounds.M1911_SHOT_ID, 1, -1.0f, false);
            if (isUpgraded) {
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
            }
            currentAltClip--;
            if (isUpgraded) {
                handler.getWorld().getEntityManager()
                        .addEntity(new Grenade(handler, player.getCenterX(), player.getCenterY(),
                                isUpgraded, player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset(),
                                player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset(), player,
                                this));
                if (player.getPeer() != null) {
                    player.getPeer().sendPlayerGrenadeLauncherShot(player.getUsername(),
                            (int) (player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset()),
                            (int) (player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset())
                    );
                }
            } else {
                handler.getWorld().getEntityManager().addEntity(
                        new Bullet(handler, player.getCenterX(), player.getCenterY(), range, this));
                if (player.getPeer() != null) {
                    player.getPeer().sendPlayerShot(player.getUsername());
                }
            }
            altTimerToFire = 0;
        }

    }
}

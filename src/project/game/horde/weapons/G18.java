package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.Gun.GunImageDim;

public class G18 extends Gun {

    public G18(Handler handler, Player player) {
        super(handler, player,
                GunVars.G18_DAMAGE,
                GunVars.G18_FIRERATE,
                GunVars.G18_RELOADSPEED,
                GunVars.G18_GUNCLIP,
                GunVars.G18_MAXRESERVE,
                GunVars.G18_WEIGHT,
                GunVars.G18_RANGE);
        this.name = GunVars.G18_NAME;
        originalName = name;
        upgradedName = GunVars.G18_UPGRADEDNAME;
        reloadSound = GunSounds.GLOCK17_RELOAD_ID;
        top = Assets.g18_top;
        gunImageDim = new GunImageDim(40, 50, 20, 100);
    }

    public void uniqueUpgrades() {
        isDual = true;
        weight = weight * 2;
        currentAltClip = clip;
    }

    public void altShoot() {
        if (altReadyToFire == true && currentAltClip > 0 && !isAltReloading) {
            altReadyToFire = false;
            Sounds.playClip(GunSounds.GLOCK17_SHOT_ID, 1, -1.0f, false);

            if (isUpgraded) {
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
            }
            currentAltClip--;
            handler.getWorld().getEntityManager().addEntity(
                    new Bullet(handler,
                            player.getCenterX(), player.getCenterY(),
                            range, this));
            if (player.getPeer() != null) {
                player.getPeer().sendPlayerShot(player.getUsername());
            }

            altTimerToFire = 0;
        }
    }

    public void shoot() {
        if (readyToFire == true && currentClip > 0 && !isReloading) {
            readyToFire = false;
            Sounds.playClip(GunSounds.GLOCK17_SHOT_ID, 1, -1.0f, false);

            if (isUpgraded) {
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);
            }
            currentClip--;
            handler.getWorld().getEntityManager().addEntity(
                    new Bullet(handler,
                            player.getCenterX(), player.getCenterY(),
                            range, this));
            if (player.getPeer() != null) {
                player.getPeer().sendPlayerShot(player.getUsername());
            }

            timerToFire = 0;
        }
    }

}

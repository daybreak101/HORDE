package project.game.horde.weapons;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.OnlineBullet;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class Thompson extends Gun {

    public Thompson(Handler handler, Player player) {
        super(handler, player,
                GunVars.THOMPSON_DAMAGE,
                GunVars.THOMPSON_FIRERATE,
                GunVars.THOMPSON_RELOADSPEED,
                GunVars.THOMPSON_GUNCLIP,
                GunVars.THOMPSON_MAXRESERVE,
                GunVars.THOMPSON_WEIGHT,
                GunVars.THOMPSON_RANGE, 65);
        this.name = GunVars.THOMPSON_NAME;
        originalName = name;
        upgradedName = GunVars.THOMPSON_UPGRADEDNAME;
        reloadSound = GunSounds.THOMPSON_RELOAD_ID;
        top = Assets.thompson_top;
        gunImageDim = new GunImageDim(40, 45, 12, 80);
    }

    @Override
    public void shootOnline(int x, int y, float angle, float volume) {
        handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, x,
                y, range, angle, isUpgraded));

        if (isUpgraded) {
            Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
        }
        Sounds.playClip(GunSounds.THOMPSON_SHOT_ID, 1, volume, false);
    }

    public void shoot() {
        if (readyToFire == true && currentClip > 0 && isReloading == false) {
            readyToFire = false;
            currentClip--;
            Sounds.playClip(GunSounds.THOMPSON_SHOT_ID, 1, -1.0f, false);

            //Sounds.playClip(GunSounds.m4_shot, 1, "m4_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
            if (isUpgraded) {
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

                //Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
            }

            handler.getWorld().getEntityManager().addEntity(new Bullet(handler,
                    player.getCenterX(),
                    player.getCenterY(),
                    range, this));
            if (player.getPeer() != null) {
                player.getPeer().sendPlayerShot(player.getUsername());
            }

            timerToFire = 0;
        }
    }
}

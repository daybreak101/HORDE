package project.game.horde.weapons;

import project.game.horde.entities.bullets.ShotgunBullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.OnlineShotgunBullet;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class AA12 extends Gun {

    public AA12(Handler handler, Player player) {
        super(handler, player, 120, 7, 140, 12, 120, 0.4f, 5, 20);
        name = "AA12";
        originalName = name;
        upgradedName = "AnarchAnonymous24";
        reloadSound = GunSounds.AA12_RELOAD_ID;
        top = Assets.aa12_top;
        gunImageDim = new GunImageDim(30, 45, 40, 100);
    }

    @Override
    public void shootOnline(int x, int y, float angle, float volume) {
        handler.getWorld().getEntityManager()
                .addEntity(new OnlineShotgunBullet(handler, x, y, range,
                        angle, GunVars.AA12_PELLET_SPREAD, GunVars.AA12_PELLET_COUNT, isUpgraded));

        if (isUpgraded) {
            Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
        }
        Sounds.playClip(GunSounds.AA12_SHOT_ID, 1, volume, false);
    }

    @Override
    public void shoot() {

        if (readyToFire == true && currentClip > 0 && isReloading == false) {
            readyToFire = false;
            currentClip--;

            //Sounds.playClip(GunSounds.aa12_shot, 1, "aa12_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
            Sounds.playClip(GunSounds.AA12_SHOT_ID, 1, -1.0f, false);

            if (isUpgraded) {
                //Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), -1.0f, false);
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, -1.0f, false);

            }

            handler.getWorld().getEntityManager().addEntity(new ShotgunBullet(handler,
                    player.getCenterX(), player.getCenterY(),
                    range, GunVars.AA12_PELLET_SPREAD,
                    GunVars.AA12_PELLET_COUNT, this));
            if (player.getPeer() != null) {
                player.getPeer().sendPlayerShot(player.getUsername());
            }

            timerToFire = 0;
        }
    }

}

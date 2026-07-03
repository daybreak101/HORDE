package project.game.horde.weapons;

import project.game.horde.entities.bullets.NewFlameBullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.OnlineFlameBullet;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;

public class Flamethrower extends Gun {

    private int windup = 0;
    private int windupMax = 40;
    private int currentFrame = 0;

    public Flamethrower(Handler handler, Player player) {
        super(handler, player, 50, 5,// GunVars.FLAMETHROWER_DAMAGE, 5,//GunVars.FLAMETHROWER_FIRERATE,
                GunVars.FLAMETHROWER_RELOADSPEED, GunVars.FLAMETHROWER_GUNCLIP, GunVars.FLAMETHROWER_MAXRESERVE,
                GunVars.FLAMETHROWER_WEIGHT, GunVars.FLAMETHROWER_RANGE, 100);
        this.name = GunVars.FLAMETHROWER_NAME;
        originalName = name;
        upgradedName = GunVars.FLAMETHROWER_UPGRADEDNAME;
        reloadSound = "";
        top = Assets.flamethrower_top;
        gunImageDim = new GunImageDim(40, 50, 10, 100);
    }

    @Override
    public void shootOnline(int x, int y, float angle, float volume) {
        handler.getWorld().getEntityManager().addEntity(new OnlineFlameBullet(handler, x,
                y, range, angle, isUpgraded));

        if (isUpgraded) {
            Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
        }
        //Sounds.playClip(GunSounds.BREN_SHOT_ID, 1, volume, false);
    }

    public void reload() {

    }

    public void tick() {
        maxReserve = 0;
        prevdWindUp = dWindUp;
        dWindUp = 0;
        dWindDown = 0;
        doubletap = player.getInv().getDoubletap();

        if (doubletap >= 2 && timerToFire >= fireRate / 2) {
            readyToFire = true;
            timerToFire = 0;
        } else if (doubletap > -1 && timerToFire >= fireRate * 3 / 4) {
            readyToFire = true;
            timerToFire = 0;
        } else if (timerToFire >= fireRate) {
            readyToFire = true;
            timerToFire = 0;
        }
        timerToFire++;

        if (windup > 0) {
            windup--;
            dWindDown = 1;
        }
        if (prevdWindUp > dWindDown) {
            if (!playWindUpSound) {
                if (windup > 10) {
                    Sounds.playClipFrom(GunSounds.FLAMETHROWER_START_SHOT_ID, 1, .9f,
                            (long) (((float) windup / windupMax) * 1683), false);
                }

                // Sounds.playClipFrom(GunSounds.flamethrower_startshot, 1, "wind_up", ((float)
                // windup / windupMax) * 1683,
                // 0.9f);
                Sounds.stopClip(GunSounds.FLAMETHROWER_END_SHOT_ID);
                playWindUpSound = true;
                playWindDownSound = false;
            }
        } else if (dWindDown > prevdWindUp) {
            if (!playWindDownSound) {
                Sounds.stopClip(GunSounds.FLAMETHROWER_START_SHOT_ID);

                float whateverthefuckthisis = 1270 - ((float) windup * 1270 / windupMax);
                if (windup > 10) {
                    Sounds.playClipFrom(GunSounds.FLAMETHROWER_END_SHOT_ID, 1, .9f, (long) whateverthefuckthisis,
                            false);
                }
                // Sounds.playClipFrom(GunSounds.flamethrower_endshot, 1, "wind_down",
                // whateverthefuckthisis, 0.9f, false);
                playWindUpSound = false;
                playWindDownSound = true;
                windup = 0;
            }
            Sounds.stopClip(GunSounds.FLAMETHROWER_SHOT_ID);

            playShootSound.counter = 0;
        }

    }

    boolean playWindUpSound = false;
    boolean playWindDownSound = false;
    int dWindUp = 0;
    int dWindDown = 0;
    int prevdWindUp = 0;
    Timer playShootSound = new Timer(19);

    @Override
    public void shoot() {
        dWindUp = 2;
        if (readyToFire == true && currentClip > 0 && windup >= windupMax) {
            readyToFire = false;
            currentClip--;
            if (currentFrame > 14) {
                currentFrame = 0;
            }
            handler.getWorld().getEntityManager().addEntity(
                    new NewFlameBullet(handler, player.getCenterX(), player.getCenterY(), range, this, currentFrame));
            if (player.getPeer() != null) {
                player.getPeer().sendPlayerShot(player.getUsername());
            }

            if (playShootSound.isReady()) {
                Sounds.stopClip(GunSounds.FLAMETHROWER_SHOT_ID);

            }
            if (playShootSound.counter == 0) {

                Sounds.playClip(GunSounds.FLAMETHROWER_SHOT_ID, 1, -1.0f, true);

                // Sounds.playClip(GunSounds.flamethrower_shot, 1, "flamethrower_shot", 0.95f,
                // false);
                if (player.getPeer() != null) {
                    player.getPeer().sendFlamethrowerSound(player.getUsername());
                }
            }

            timerToFire = 0;
            playShootSound.tick();
            currentFrame++;
        } else if (readyToFire == true && windup < windupMax) {
            windup++;
            windup++;
        }
    }

}

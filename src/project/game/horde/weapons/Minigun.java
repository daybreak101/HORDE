package project.game.horde.weapons;

import java.awt.geom.Line2D;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.facade.OnlineBullet;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Utils;

public class Minigun extends Gun {

    private int windup = 0;
    private int windupMax = 100;

    public Minigun(Handler handler, Player player) {
        super(handler, player, GunVars.MINIGUN_DAMAGE, GunVars.MINIGUN_FIRERATE, GunVars.MINIGUN_RELOADSPEED,
                GunVars.MINIGUN_GUNCLIP, GunVars.MINIGUN_MAXRESERVE, GunVars.MINIGUN_WEIGHT, GunVars.MINIGUN_RANGE, 100);
        this.name = GunVars.MINIGUN_NAME;
        originalName = name;
        upgradedName = GunVars.MINIGUN_UPGRADEDNAME;
        reloadSound = GunSounds.MINIGUN_FAILSAFE_RELOAD_ID;
        top = Assets.minigun_top;
        gunImageDim = new GunImageDim(30, 45, 40, 100);
    }

    public Minigun(Handler handler, boolean isTurret, Player player) {
        super(handler, player, 2000, 2, 0, 0, 0, 1.5f, 50, 100);
        name = "Minigun";
    }

    @Override
    public void shootOnline(int x, int y, float angle, float volume) {
        handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, x,
                x, range, angle, isUpgraded));

        Sounds.playClip(GunSounds.MINIGUN_SHOT_ID, 1, volume, false);
    }

    public void tick() {
        prevdWindUp = dWindUp;
        dWindUp = 0;
        dWindDown = 0;
        doubletap = -1;
        if (player != null) {
            doubletap = player.getInv().getDoubletap();
        }

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
        postTick();
    }

    boolean playWindUpSound = false;
    boolean playWindDownSound = false;
    boolean playOverheatSound = false;
    int dWindUp = 0;
    int dWindDown = 0;
    int prevdWindUp = 0;

    @Override
    public void shoot() {
        dWindUp = 2;
        if (readyToFire == true && windup >= windupMax) {
            readyToFire = false;
            currentClip--;

            Sounds.playClip(GunSounds.MINIGUN_SHOT_ID, 1, -1.0f, false);

            //Sounds.playClip(GunSounds.minigun_shot, 1, "minigun_shot" + RandomUtil.nextInt(0, 10000), -1.0f, false);
            handler.getWorld().getEntityManager()
                    .addEntity(new Bullet(handler, player.getCenterX(), player.getCenterY(), range, this));
            if (player.getPeer() != null) {
                player.getPeer().sendPlayerShot(player.getUsername());
            }
            playOverheatSound = false;
            heatLevel++;

            timerToFire = 0;

        } else if (readyToFire == true && windup < windupMax) {
            // playSound = !playSound;
            // if(windup % 3 != 1 )

            windup++;
            windup++;

        }

    }

    private int heatLevel = 0;
    private int overheat = 300;

    @Override
    public void postTick() {
        System.out.println("heat:" + heatLevel);

        if (windup > 0) {
            windup--;
            dWindDown = 1;
        }

        if (prevdWindUp > dWindDown) {
            heatLevel++;
            if (!playWindUpSound) {
                Sounds.playClipFrom(GunSounds.MINIGUN_SPIN_UP_ID, 1, -1.0f, (long) (((float) windup / windupMax) * 1683), false);
                System.out.println((long) (((float) windup / windupMax) * 1683));
                //TODO: play from
                //Sounds.playClip(GunSounds.MINIGUN_SPIN_UP_ID, 1, -1.0f, false);
                //Sounds.playClipFrom(GunSounds.minigun_spin_up, 1, "wind_up", ((float) windup / windupMax) * 1683,
                //		-1.0f, false);
                Sounds.stopClip(GunSounds.MINIGUN_SPIN_DOWN_ID);
                playWindUpSound = true;
                playWindDownSound = false;
            }
        } else if (dWindDown > prevdWindUp) {
            heatLevel--;
            if (!playWindDownSound) {
                Sounds.playClip(GunSounds.MINIGUN_SPIN_DOWN_ID, 1, -1.0f, false);

                //Sounds.playClip(GunSounds.minigun_spin_down, 1, "wind_down", -1.0f, false);
                Sounds.stopClip(GunSounds.MINIGUN_SPIN_UP_ID);
                playWindUpSound = false;
                playWindDownSound = true;
            }
            if (heatLevel >= overheat) {
                if (!playOverheatSound) {
                    playOverheatSound = true;
                    Sounds.playClip(GunSounds.MINIGUN_OVERHEAT_ID, 1, -1.0f, false);

                    //Sounds.playClip(GunSounds.minigun_overheat, 1, "overheat", -1.0f, false);
                }
            } else if (heatLevel < overheat) {
                playOverheatSound = false;
            }
        } else if (heatLevel > 0) {
            heatLevel--;
        }
        if (heatLevel < overheat) {
            playOverheatSound = false;
        }

    }

    public void shootAsTurret(float x, float y) {
        int lowestDistanceSoFar = 2000000;
        Zombie closestEntity = null;
        boolean found = false;

        for (Zombie entity : handler.getWorld().getEntityManager().getZombies()) { // This loops through all the
            // entities, setting the variable
            // "entity" to each element.
            int zombieX = (int) (x - entity.getX());
            int zombieY = (int) (y - entity.getY());
            Line2D.Float line = new Line2D.Float(x, y, entity.getX() + entity.getWidth() / 2,
                    entity.getY() + entity.getHeight() / 2);
            double distance = Math.sqrt((zombieX * zombieX) + (zombieY * zombieY));
            if (distance < lowestDistanceSoFar) {
                for (Wall w : handler.getWorld().getEntityManager().getWalls()) {
                    if (line.intersects(w.getCollisionBounds(0, 0))) {
                        found = true;
                    }
                }
                if (!found) {
                    lowestDistanceSoFar = (int) distance;
                    closestEntity = entity;
                }

            }
            found = false;
        }
        if (closestEntity != null) {
            if (readyToFire == true) {
                readyToFire = false;

                float dist = Utils.getEuclideanDistance(x, y,
                        handler.getCurrentPlayer().getCenterX(), handler.getCurrentPlayer().getCenterY());
                float volume = ((float) (1.0f - (float) (dist / 3000) - 0.1f));
                Sounds.playClip(GunSounds.MINIGUN_SHOT_ID, 1, -1.0f, false);

                //Sounds.playClip(GunSounds.minigun_shot, 1, "minigun_shot" + RandomUtil.nextInt(0, 10000), volume, false);
                handler.getWorld().getEntityManager()
                        .addEntity(new Bullet(handler, this, x, y, closestEntity.getX() + closestEntity.getWidth() / 2,
                                closestEntity.getY() + closestEntity.getHeight() / 2, range));

                timerToFire = 0;

            }
        }

    }

}

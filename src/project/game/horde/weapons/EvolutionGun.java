package project.game.horde.weapons;

import java.util.Random;

import project.game.horde.entities.bullets.Bullet;
import project.game.horde.entities.bullets.Grenade;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.OnlineBullet;
import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;

public class EvolutionGun extends Gun {

    //TODO:
    //raygun audio: shoot, reload
    //raygun visuals: bullet, gun top, gun side
    //update dimensions
    //Raygun variants
    //Base: semi-auto, 1 bullet per shot
    //Sniper: bolt-action, unlimited penetration, 5 bullets per shot
    //Shotgun: pump, 4 bullets per shot
    //Charge: semi-auto, charge uses 20 bullets
    //Auto: fully-auto, 1 pullet per shot, faster fire rate
    //Laser: 
    //Dual:
    //Auto Dual:
    private final int VARIANT_UNUPGRADED = -1;
    private final int VARIANT_BASE = 0;
    private final int VARIANT_SNIPER = 1;
    private final int VARIANT_SHOTGUN = 2;
    private final int VARIANT_CHARGE = 3;
    private final int VARIANT_AUTO = 4;
    private final int VARIANT_LASER = 5;
    private final int VARIANT_DUAL = 6;
    private final int VARIANT_AUTO_DUAL = 7;
    private int variant = VARIANT_UNUPGRADED;

    public static final String EVOLUTION_NAME = "Prototype Evo-78";
    public static final String EVOLUTION_UPGRADEDNAME = "Chameleon";

    //base stats
    public static final int EVOLUTION_DAMAGE = 1500;
    public static final int EVOLUTION_FIRERATE = 20;
    public static final int EVOLUTION_RELOADSPEED = 240;
    public static final int EVOLUTION_GUNCLIP = 20;
    public static final int EVOLUTION_MAXRESERVE = 120;
    public static final float EVOLUTION_WEIGHT = 0.2f;
    public static final int EVOLUTION_RANGE = 50;

    public EvolutionGun(Handler handler, Player player) {
        super(handler, player, GunVars.RAYGUN_DAMAGE, GunVars.RAYGUN_FIRERATE, GunVars.RAYGUN_RELOADSPEED,
                GunVars.RAYGUN_GUNCLIP, GunVars.RAYGUN_MAXRESERVE, GunVars.RAYGUN_WEIGHT, GunVars.RAYGUN_RANGE, 69);
        this.name = GunVars.RAYGUN_NAME;
        originalName = name;
        upgradedName = GunVars.RAYGUN_UPGRADEDNAME;
        //reloadSound = GunSounds.RAYGUN_RELOAD_ID;
        //top = Assets.raygun_top;
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

    @Override
    public void uniqueUpgrades() {
        int randomNum;
        //if unupgraded, do not give a variant
        if (variant == VARIANT_UNUPGRADED) {
            variant = VARIANT_BASE;
            randomNum = VARIANT_BASE;
        }
        //if upgraded and attempting to reupgrade, give random new variant
        else {
            randomNum = new Random().nextInt(0, 7 + 1);
            //don't allow same variant
            while (randomNum == variant) {
                randomNum = new Random().nextInt(0, 7 + 1);
            }

        }

        //apply variant stats
        switch (randomNum) {
            case VARIANT_BASE -> {
                damage = EVOLUTION_DAMAGE * 2;
                fireRate = EVOLUTION_FIRERATE;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = (int) ((float) EVOLUTION_GUNCLIP * 1.5f);
                maxReserve = (int) ((float) EVOLUTION_MAXRESERVE * 1.5f);
                currentReserve = maxReserve;
                isDual = false;
                weight = EVOLUTION_WEIGHT;
                range = EVOLUTION_RANGE;
                currentClip = clip;
                currentAltClip = 0;
            }
            case VARIANT_SNIPER -> {
                damage = EVOLUTION_DAMAGE * 5;
                fireRate = 120;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = 8;
                maxReserve = 80;
                currentReserve = maxReserve;
                isDual = false;
                weight = 0.7f;
                range = 100;
                currentClip = clip;
                currentAltClip = 0;
            }
            case VARIANT_SHOTGUN -> {
                damage = EVOLUTION_DAMAGE * 4;
                fireRate = 90;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = 10;
                maxReserve = 100;
                currentReserve = maxReserve;
                isDual = false;
                weight = 0.5f;
                range = 7;
                currentClip = clip;
                currentAltClip = 0;
            }
            case VARIANT_CHARGE -> {
                damage = (int) ((float) EVOLUTION_DAMAGE * 1.5f);
                fireRate = 15;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = 15;
                maxReserve = 150;
                currentReserve = maxReserve;
                isDual = false;
                weight = 0.3f;
                range = EVOLUTION_RANGE;
                currentClip = clip;
                currentAltClip = 0;
            }
            case VARIANT_AUTO -> {
                damage = EVOLUTION_DAMAGE;
                fireRate = 5;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = 50;
                maxReserve = 300;
                currentReserve = maxReserve;
                isDual = false;
                weight = 0.4f;
                range = 14;
                currentClip = clip;
                currentAltClip = 0;
            }
            case VARIANT_LASER -> {
                damage = EVOLUTION_DAMAGE;
                fireRate = 2;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = 100;
                maxReserve = 500;
                currentReserve = maxReserve;
                isDual = false;
                weight = 0.6f;
                range = EVOLUTION_RANGE;
                currentClip = clip;
                currentAltClip = 0;
            }
            case VARIANT_DUAL -> {
                damage = EVOLUTION_DAMAGE * 2;
                fireRate = EVOLUTION_FIRERATE;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = 20;
                maxReserve = 200;
                currentReserve = maxReserve;
                isDual = true;
                weight = 0.4f;
                range = EVOLUTION_RANGE;
                currentClip = clip;
                currentAltClip = clip;
            }
            case VARIANT_AUTO_DUAL -> {
                damage = EVOLUTION_DAMAGE;
                fireRate = 5;
                reloadSpeed = EVOLUTION_RELOADSPEED;
                clip = 50;
                maxReserve = 500;
                currentReserve = maxReserve;
                isDual = true;
                weight = 0.6f;
                range = 14;
                currentClip = clip;
                currentAltClip = clip;
            }
        }
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

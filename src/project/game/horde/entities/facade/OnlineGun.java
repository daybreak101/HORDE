package project.game.horde.entities.facade;

import java.net.URL;

import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Utils;
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
import project.game.horde.weapons.GunVars;
import project.game.horde.weapons.M16;
import project.game.horde.weapons.M1911;
import project.game.horde.weapons.M1Garand;
import project.game.horde.weapons.M4;
import project.game.horde.weapons.M60;
import project.game.horde.weapons.Minigun;
import project.game.horde.weapons.P90;
import project.game.horde.weapons.Python;
import project.game.horde.weapons.RPD;
import project.game.horde.weapons.RPG;
import project.game.horde.weapons.Thompson;
import project.game.horde.weapons.Type100;
import project.game.horde.weapons.Uzi;
import project.game.horde.weapons.Winchester1901;

public class OnlineGun {

    protected PlayerMP playermp;
    protected String name;
    protected Handler handler;
    protected int range;
    protected boolean isUpgraded = false;
    protected URL reloadSound;
    protected String shotSound;
    //protected GunImageDim gunImageDim;
    //protected BufferedImage gunImage;
    protected Gun gunRef;

    public OnlineGun(Handler handler, PlayerMP playermp) {
        this.handler = handler;
        this.playermp = playermp;
        //gunRef = new M1911(handler, null);
        switchWeapon("M1911");

    }

    public Gun getGunRef() {
        return gunRef;
    }

    // public BufferedImage getGunImage() {
    // 	return gunImage;
    // }
    // public GunImageDim getGunImageDim() {
    // 	return gunImageDim;
    // }
    public void switchWeapon(String weapon) {
        name = weapon;
        switch (weapon) {
            case GunVars.AA12_NAME -> {
                gunRef = new AA12(handler, null);
            }
            case GunVars.AA12_UPGRADEDNAME -> {
                gunRef = new AA12(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.AK47_NAME -> {
                gunRef = new AK47(handler, null);
            }
            case GunVars.AK47_UPGRADEDNAME -> {
                gunRef = new AK47(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.ARISAKA_NAME -> {
                gunRef = new Arisaka(handler, null);
            }
            case GunVars.ARISAKA_UPGRADEDNAME -> {
                gunRef = new Arisaka(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.AWP_NAME -> {
                gunRef = new AWP(handler, null);
            }
            case GunVars.AWP_UPGRADEDNAME -> {
                gunRef = new AWP(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.BREN_NAME -> {
                gunRef = new Bren(handler, null);
            }
            case GunVars.BREN_UPGRADEDNAME -> {
                gunRef = new Bren(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.DB_NAME -> {
                gunRef = new DoubleBarrel(handler, null);
            }
            case GunVars.DB_UPGRADEDNAME -> {
                gunRef = new DoubleBarrel(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.FLAMETHROWER_NAME -> {
                gunRef = new Flamethrower(handler, null);
            }
            case GunVars.FLAMETHROWER_UPGRADEDNAME -> {
                gunRef = new Flamethrower(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.G18_NAME -> {
                gunRef = new G18(handler, null);
            }
            case GunVars.G18_UPGRADEDNAME -> {
                gunRef = new G18(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.GLOCK17_NAME -> {
                gunRef = new Glock17(handler, null);
            }
            case GunVars.GLOCK17_UPGRADEDNAME -> {
                gunRef = new Glock17(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.GRENADELAUNCHER_NAME -> {
                gunRef = new GrenadeLauncher(handler, null);
            }
            case GunVars.GRENADELAUNCHER_UPGRADEDNAME -> {
                gunRef = new GrenadeLauncher(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.M1GARAND_NAME -> {
                gunRef = new M1Garand(handler, null);
            }
            case GunVars.M1GARAND_UPGRADEDNAME -> {
                gunRef = new M1Garand(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.M4_NAME -> {
                gunRef = new M4(handler, null);
            }
            case GunVars.M4_UPGRADEDNAME -> {
                gunRef = new M4(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.M16_NAME -> {
                gunRef = new M16(handler, null);
            }
            case GunVars.M16_UPGRADEDNAME -> {
                gunRef = new M16(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.M60_NAME -> {
                gunRef = new M60(handler, null);
            }
            case GunVars.M60_UPGRADEDNAME -> {
                gunRef = new M60(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.M1911_NAME -> {
                gunRef = new M1911(handler, null);
            }
            case GunVars.M1911_UPGRADEDNAME -> {
                gunRef = new M1911(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.MINIGUN_NAME -> {
                gunRef = new Minigun(handler, null);
            }
            case GunVars.MINIGUN_UPGRADEDNAME -> {
                gunRef = new Minigun(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.P90_NAME -> {
                gunRef = new P90(handler, null);
            }
            case GunVars.P90_UPGRADEDNAME -> {
                gunRef = new P90(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.PYTHON_NAME -> {
                gunRef = new Python(handler, null);
            }
            case GunVars.PYTHON_UPGRADEDNAME -> {
                gunRef = new Python(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.RPD_NAME -> {
                gunRef = new RPD(handler, null);
            }
            case GunVars.RPD_UPGRADEDNAME -> {
                gunRef = new RPD(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.RPG_NAME -> {
                gunRef = new RPG(handler, null);
            }
            case GunVars.RPG_UPGRADEDNAME -> {
                gunRef = new RPG(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.THOMPSON_NAME -> {
                gunRef = new Thompson(handler, null);
            }
            case GunVars.THOMPSON_UPGRADEDNAME -> {
                gunRef = new Thompson(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.TYPE100_NAME -> {
                gunRef = new Type100(handler, null);
            }
            case GunVars.TYPE100_UPGRADEDNAME -> {
                gunRef = new Type100(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.UZI_NAME -> {
                gunRef = new Uzi(handler, null);
            }
            case GunVars.UZI_UPGRADEDNAME -> {
                gunRef = new Uzi(handler, null);
                gunRef.upgradeWeapon();
            }
            case GunVars.WINCHESTER1901_NAME -> {
                gunRef = new Winchester1901(handler, null);
            }
            case GunVars.WINCHESTER1901_UPGRADEDNAME -> {
                gunRef = new Winchester1901(handler, null);
                gunRef.upgradeWeapon();
            }
        }

    }

    // public void switchWeapon(String weapon) {
    // 	name = weapon;
    // 	switch (weapon) {
    // 	case GunVars.AA12_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.aa12_reload;
    //                 shotSound = GunSounds.AA12_SHOT_ID; //GunSounds.aa12_shot;
    //                 range = GunVars.AA12_RANGE;
    //             }
    // 	case GunVars.AA12_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.aa12_reload;
    //                 shotSound = GunSounds.AA12_SHOT_ID; //GunSounds.aa12_shot;
    //                 range = GunVars.AA12_RANGE;
    //             }
    // 	case GunVars.AK47_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.ak47_reload;
    //                 shotSound = GunSounds.AK47_SHOT_ID; //GunSounds.ak47_shot;
    //                 range = GunVars.AK47_RANGE;
    //             }
    // 	case GunVars.AK47_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.ak47_reload;
    //                 shotSound = GunSounds.AK47_SHOT_ID; //GunSounds.ak47_shot;
    //                 range = GunVars.AK47_RANGE;
    //             }
    // 	case GunVars.AWP_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.awp_reload;
    //                 shotSound = GunSounds.AWP_SHOT_ID; // GunSounds.ak47_shot;
    //                 range = GunVars.AWP_RANGE;
    //             }
    // 	case GunVars.AWP_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.awp_reload;
    //                 shotSound = GunSounds.AWP_SHOT_ID; //GunSounds.awp_shot;
    //                 range = GunVars.AWP_RANGE;
    //             }
    // 	case GunVars.FLAMETHROWER_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 // shotSound = GunSounds.flamethrower_shot;
    //                 range = GunVars.FLAMETHROWER_RANGE;
    //             }
    // 	case GunVars.FLAMETHROWER_NAME -> {
    //                 isUpgraded = false;
    //                 // shotSound = GunSounds.flamethrower_shot;
    //                 range = GunVars.FLAMETHROWER_RANGE;
    //             }
    // 	case GunVars.GRENADELAUNCHER_NAME -> {
    //                 isUpgraded = false;
    //                 // reloadSound = GunSounds.grenade_launcher_reload;
    //                 shotSound = GunSounds.GRENADE_LAUNCHER_SHOT_ID; //GunSounds.grenade_launcher_shot;
    //                 range = GunVars.GRENADELAUNCHER_RANGE;
    //             }
    // 	case GunVars.GRENADELAUNCHER_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 // reloadSound = GunSounds.grenade_launcher_reload;
    //                 shotSound = GunSounds.GRENADE_LAUNCHER_SHOT_ID; //GunSounds.grenade_launcher_shot;
    //                 range = GunVars.GRENADELAUNCHER_RANGE;
    //             }
    // 	case GunVars.ICESHOTGUN_NAME -> {
    //             }
    // 	case GunVars.ICESHOTGUN_UPGRADEDNAME -> {
    //             }
    // 	case GunVars.GLOCK17_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.glock17_reload;
    //                 shotSound = GunSounds.GLOCK17_SHOT_ID; //GunSounds.glock17_shot;
    //                 range = GunVars.GLOCK17_RANGE;
    //             }
    // 	case GunVars.GLOCK17_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.glock17_reload;
    //                 shotSound = GunSounds.GLOCK17_SHOT_ID; //GunSounds.glock17_shot;
    //                 range = GunVars.GLOCK17_RANGE;
    //             }
    // 	case GunVars.M4_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.m4_reload;
    //                 shotSound = GunSounds.M4_SHOT_ID; //GunSounds.m4_shot;
    //                 range = GunVars.M4_RANGE;
    //             }
    // 	case GunVars.M4_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.m4_reload;
    //                 shotSound = GunSounds.M4_SHOT_ID; //GunSounds.m4_shot;
    //                 range = GunVars.M4_RANGE;
    //             }
    // 	case GunVars.MINIGUN_NAME -> {
    //                 isUpgraded = false;
    //                 shotSound = GunSounds.MINIGUN_SHOT_ID; //GunSounds.minigun_shot;
    //                 range = GunVars.MINIGUN_RANGE;
    //             }
    // 	case GunVars.MINIGUN_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 shotSound = GunSounds.MINIGUN_SHOT_ID; //GunSounds.minigun_shot;
    //                 range = GunVars.MINIGUN_RANGE;
    //             }
    // 	case GunVars.P90_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.p90_reload;
    //                 shotSound = GunSounds.P90_SHOT_ID; //GunSounds.p90_shot;
    //                 range = GunVars.P90_RANGE;
    //             }
    // 	case GunVars.P90_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.p90_reload;
    //                 shotSound = GunSounds.P90_SHOT_ID; //GunSounds.p90_shot;
    //                 range = GunVars.P90_RANGE;
    //             }
    // 	case GunVars.RPD_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.rpd_reload;
    //                 shotSound = GunSounds.RPD_SHOT_ID; //GunSounds.rpd_shot;
    //                 range = GunVars.RPD_RANGE;
    //             }
    // 	case GunVars.RPD_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.rpd_reload;
    //                 shotSound = GunSounds.RPD_SHOT_ID; //GunSounds.rpd_shot;
    //                 range = GunVars.RPD_RANGE;
    //             }
    // 	case GunVars.RPG_NAME -> {
    //                 isUpgraded = false;
    //                 reloadSound = GunSounds.rpg_reload;
    //                 shotSound = GunSounds.RPG_SHOT_ID; //GunSounds.rpg_shot;
    //                 range = GunVars.RPG_RANGE;
    //             }
    // 	case GunVars.RPG_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 reloadSound = GunSounds.rpg_reload;
    //                 shotSound = GunSounds.RPG_SHOT_ID; //GunSounds.rpg_shot;
    //                 range = GunVars.RPG_RANGE;
    //             }
    // 	case GunVars.WINCHESTER1901_NAME -> {
    //                 isUpgraded = false;
    //                 // reloadSound = GunSounds.winchester1901_reload;
    //                 shotSound = GunSounds.WINCHESTER1901_SHOT_ID; //GunSounds.winchester1901_shot;
    //                 range = GunVars.WINCHESTER1901_RANGE;
    //             }
    // 	case GunVars.WINCHESTER1901_UPGRADEDNAME -> {
    //                 isUpgraded = true;
    //                 // reloadSound = GunSounds.winchester1901_reload;
    //                 shotSound = GunSounds.WINCHESTER1901_SHOT_ID; //GunSounds.winchester1901_shot;
    //                 range = GunVars.WINCHESTER1901_RANGE;
    //             }
    // 	}
    // }
    public void shootGrenadeLauncher(int destX, int destY) {
        float dist = Utils.getEuclideanDistance(playermp.getCenterX(), playermp.getCenterY(),
                handler.getCurrentPlayer().getCenterX(), handler.getCurrentPlayer().getCenterY());
        float volume = ((float) (1.0f - (float) (dist / 3000) - 0.1f));
        switch (name) {
            case GunVars.M1911_UPGRADEDNAME:
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
                handler.getWorld().getEntityManager().addEntity(new OnlineGrenade(handler, playermp.getCenterX(),
                        playermp.getCenterY(), isUpgraded, destX, destY, playermp.getAngle()));
                Sounds.playClip(GunSounds.M1911_SHOT_ID, 1, volume, false);
                break;
            case GunVars.GRENADELAUNCHER_UPGRADEDNAME:
                Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            case GunVars.GRENADELAUNCHER_NAME:
                handler.getWorld().getEntityManager().addEntity(new OnlineGrenade(handler, playermp.getCenterX(),
                        playermp.getCenterY(), isUpgraded, destX, destY, playermp.getAngle()));
                Sounds.playClip(GunSounds.GRENADE_LAUNCHER_SHOT_ID, 1, volume, false);
                break;
        }
    }

    public void shoot() {
        int centerX = playermp.getCenterX();
        int centerY = playermp.getCenterY();
        float dist = Utils.getEuclideanDistance(centerX, centerY,
                handler.getCurrentPlayer().getCenterX(), handler.getCurrentPlayer().getCenterY());
        float volume = ((float) (1.0f - (float) (dist / 3000) - 0.1f));
        gunRef.shootOnline(centerX, centerY, playermp.getAngle(), volume);

        ///////////////////
		//switch (name) {
            // case GunVars.AA12_UPGRADEDNAME:
            // 	Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.AA12_NAME:
            // 	handler.getWorld().getEntityManager()
            // 			.addEntity(new OnlineShotgunBullet(handler, playermp.getCenterX(), playermp.getCenterY(), range,
            // 					playermp.getAngle(), GunVars.AA12_PELLET_SPREAD, GunVars.AA12_PELLET_COUNT, isUpgraded));
            // 	Sounds.playClip(GunSounds.AA12_SHOT_ID, 1, volume, false);
            // 	break;
            // case GunVars.AK47_UPGRADEDNAME:
            // 	Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.AK47_NAME:
            // 	handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            // 			playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            // 	Sounds.playClip(GunSounds.AK47_SHOT_ID, 1, volume, false);
            // 	break;
            // case GunVars.AWP_UPGRADEDNAME:
            // 	Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.AWP_NAME:
            // 	handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            // 			playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            // 	Sounds.playClip(GunSounds.AWP_SHOT_ID, 1, volume, false);
            // 	break;
            // case GunVars.FLAMETHROWER_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.FLAMETHROWER_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineFlameBullet(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     break;
            // case GunVars.ICESHOTGUN_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.ICESHOTGUN_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     break;
            // case GunVars.GLOCK17_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.GLOCK17_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     Sounds.playClip(GunSounds.GLOCK17_SHOT_ID, 1, volume, false);
            //     break;
            // case GunVars.M4_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.M4_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     Sounds.playClip(GunSounds.M4_SHOT_ID, 1, volume, false);
            //     break;
            // case GunVars.MINIGUN_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.MINIGUN_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     Sounds.playClip(GunSounds.MINIGUN_SHOT_ID, 1, volume, false);
            //     break;
            // case GunVars.P90_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.P90_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     Sounds.playClip(GunSounds.P90_SHOT_ID, 1, volume, false);
            //     break;
            // case GunVars.RPD_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.RPD_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     Sounds.playClip(GunSounds.RPD_SHOT_ID, 1, volume, false);
            //     break;
            // case GunVars.RPG_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.RPG_NAME:
            //     handler.getWorld().getEntityManager().addEntity(new OnlineRocket(handler, playermp.getCenterX(),
            //             playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
            //     Sounds.playClip(GunSounds.RPG_SHOT_ID, 1, volume, false);
            //     break;
            // case GunVars.WINCHESTER1901_UPGRADEDNAME:
            //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
            // case GunVars.WINCHESTER1901_NAME:
            //     handler.getWorld().getEntityManager()
            //             .addEntity(new OnlineShotgunBullet(handler, playermp.getCenterX(), playermp.getCenterY(), range,
            //                     playermp.getAngle(), GunVars.WINCHESTER1901_PELLET_SPREAD,
            //                     GunVars.WINCHESTER1901_PELLET_COUNT, isUpgraded));
            //     Sounds.playClip(GunSounds.WINCHESTER1901_SHOT_ID, 1, volume, false);
            //     break;
        //}
    }

    public void reload() {
        //Sounds.playClip(reloadSound, 1, -1.0f, false);
    }

    public String getName() {
        return name;
    }

    public boolean isUpgraded() {
        return isUpgraded;
    }

    public void setUpgraded(boolean isUpgraded) {
        this.isUpgraded = isUpgraded;
    }

    public PlayerMP getPlayer() {
        return playermp;
    }

}

package project.game.horde.entities.facade;

import java.net.URL;

import project.game.horde.main.Handler;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Utils;
import project.game.horde.weapons.GunVars;

public class OnlineGun {
	protected PlayerMP playermp;
	protected String name;
	protected Handler handler;
	protected int range;
	protected boolean isUpgraded = false;
	protected URL reloadSound;
	protected String shotSound;

	public OnlineGun(Handler handler, PlayerMP playermp) {
		this.handler = handler;
		this.playermp = playermp;
		switchWeapon("Glock17");
	}

	public void switchWeapon(String weapon) {
		name = weapon;
		switch (weapon) {
		case GunVars.AA12_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.aa12_reload;
                    shotSound = GunSounds.AA12_SHOT_ID; //GunSounds.aa12_shot;
                    range = GunVars.AA12_RANGE;
                }
		case GunVars.AA12_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.aa12_reload;
                    shotSound = GunSounds.AA12_SHOT_ID; //GunSounds.aa12_shot;
                    range = GunVars.AA12_RANGE;
                }
		case GunVars.AK47_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.ak47_reload;
                    shotSound = GunSounds.AK47_SHOT_ID; //GunSounds.ak47_shot;
                    range = GunVars.AK47_RANGE;
                }
		case GunVars.AK47_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.ak47_reload;
                    shotSound = GunSounds.AK47_SHOT_ID; //GunSounds.ak47_shot;
                    range = GunVars.AK47_RANGE;
                }
		case GunVars.AWP_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.awp_reload;
                    shotSound = GunSounds.AWP_SHOT_ID; // GunSounds.ak47_shot;
                    range = GunVars.AWP_RANGE;
                }
		case GunVars.AWP_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.awp_reload;
                    shotSound = GunSounds.AWP_SHOT_ID; //GunSounds.awp_shot;
                    range = GunVars.AWP_RANGE;
                }
		case GunVars.FLAMETHROWER_UPGRADEDNAME -> {
                    isUpgraded = true;
                    // shotSound = GunSounds.flamethrower_shot;
                    range = GunVars.FLAMETHROWER_RANGE;
                }
		case GunVars.FLAMETHROWER_NAME -> {
                    isUpgraded = false;
                    // shotSound = GunSounds.flamethrower_shot;
                    range = GunVars.FLAMETHROWER_RANGE;
                }
		case GunVars.GRENADELAUNCHER_NAME -> {
                    isUpgraded = false;
                    // reloadSound = GunSounds.grenade_launcher_reload;
                    shotSound = GunSounds.GRENADE_LAUNCHER_SHOT_ID; //GunSounds.grenade_launcher_shot;
                    range = GunVars.GRENADELAUNCHER_RANGE;
                }
		case GunVars.GRENADELAUNCHER_UPGRADEDNAME -> {
                    isUpgraded = true;
                    // reloadSound = GunSounds.grenade_launcher_reload;
                    shotSound = GunSounds.GRENADE_LAUNCHER_SHOT_ID; //GunSounds.grenade_launcher_shot;
                    range = GunVars.GRENADELAUNCHER_RANGE;
                }
		case GunVars.ICESHOTGUN_NAME -> {
                }
		case GunVars.ICESHOTGUN_UPGRADEDNAME -> {
                }
		case GunVars.GLOCK17_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.glock17_reload;
                    shotSound = GunSounds.GLOCK17_SHOT_ID; //GunSounds.glock17_shot;
                    range = GunVars.GLOCK17_RANGE;
                }
		case GunVars.GLOCK17_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.glock17_reload;
                    shotSound = GunSounds.GLOCK17_SHOT_ID; //GunSounds.glock17_shot;
                    range = GunVars.GLOCK17_RANGE;
                }
		case GunVars.M4_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.m4_reload;
                    shotSound = GunSounds.M4_SHOT_ID; //GunSounds.m4_shot;
                    range = GunVars.M4_RANGE;
                }
		case GunVars.M4_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.m4_reload;
                    shotSound = GunSounds.M4_SHOT_ID; //GunSounds.m4_shot;
                    range = GunVars.M4_RANGE;
                }
		case GunVars.MINIGUN_NAME -> {
                    isUpgraded = false;
                    shotSound = GunSounds.MINIGUN_SHOT_ID; //GunSounds.minigun_shot;
                    range = GunVars.MINIGUN_RANGE;
                }
		case GunVars.MINIGUN_UPGRADEDNAME -> {
                    isUpgraded = true;
                    shotSound = GunSounds.MINIGUN_SHOT_ID; //GunSounds.minigun_shot;
                    range = GunVars.MINIGUN_RANGE;
                }
		case GunVars.P90_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.p90_reload;
                    shotSound = GunSounds.P90_SHOT_ID; //GunSounds.p90_shot;
                    range = GunVars.P90_RANGE;
                }
		case GunVars.P90_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.p90_reload;
                    shotSound = GunSounds.P90_SHOT_ID; //GunSounds.p90_shot;
                    range = GunVars.P90_RANGE;
                }
		case GunVars.RPD_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.rpd_reload;
                    shotSound = GunSounds.RPD_SHOT_ID; //GunSounds.rpd_shot;
                    range = GunVars.RPD_RANGE;
                }
		case GunVars.RPD_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.rpd_reload;
                    shotSound = GunSounds.RPD_SHOT_ID; //GunSounds.rpd_shot;
                    range = GunVars.RPD_RANGE;
                }
		case GunVars.RPG_NAME -> {
                    isUpgraded = false;
                    reloadSound = GunSounds.rpg_reload;
                    shotSound = GunSounds.RPG_SHOT_ID; //GunSounds.rpg_shot;
                    range = GunVars.RPG_RANGE;
                }
		case GunVars.RPG_UPGRADEDNAME -> {
                    isUpgraded = true;
                    reloadSound = GunSounds.rpg_reload;
                    shotSound = GunSounds.RPG_SHOT_ID; //GunSounds.rpg_shot;
                    range = GunVars.RPG_RANGE;
                }
		case GunVars.WINCHESTER1901_NAME -> {
                    isUpgraded = false;
                    // reloadSound = GunSounds.winchester1901_reload;
                    shotSound = GunSounds.WINCHESTER1901_SHOT_ID; //GunSounds.winchester1901_shot;
                    range = GunVars.WINCHESTER1901_RANGE;
                }
		case GunVars.WINCHESTER1901_UPGRADEDNAME -> {
                    isUpgraded = true;
                    // reloadSound = GunSounds.winchester1901_reload;
                    shotSound = GunSounds.WINCHESTER1901_SHOT_ID; //GunSounds.winchester1901_shot;
                    range = GunVars.WINCHESTER1901_RANGE;
                }

		}
	}

	public void shootGrenadeLauncher(int destX, int destY) {
		float dist = Utils.getEuclideanDistance(playermp.getCenterX(), playermp.getCenterY(),
				handler.getCurrentPlayer().getCenterX(), handler.getCurrentPlayer().getCenterY());
		float volume = ((float) (1.0f - (float) (dist / 3000) - 0.1f));
		switch (name) {
		case GunVars.GRENADELAUNCHER_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.GRENADELAUNCHER_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineGrenade(handler, playermp.getCenterX(),
					playermp.getCenterY(), isUpgraded, destX, destY, playermp.getAngle()));
			//Sounds.playClip(shotSound, 1, "grenadelauncher_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			Sounds.playClip(shotSound, 1, volume, false);
			break;
		}
	}

	public void shoot() {
		float dist = Utils.getEuclideanDistance(playermp.getCenterX(), playermp.getCenterY(),
				handler.getCurrentPlayer().getCenterX(), handler.getCurrentPlayer().getCenterY());
		float volume = ((float) (1.0f - (float) (dist / 3000) - 0.1f));

		switch (name) {
		case GunVars.AA12_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.AA12_NAME:
			handler.getWorld().getEntityManager()
					.addEntity(new OnlineShotgunBullet(handler, playermp.getCenterX(), playermp.getCenterY(), range,
							playermp.getAngle(), GunVars.AA12_PELLET_SPREAD, GunVars.AA12_PELLET_COUNT, isUpgraded));
			Sounds.playClip(GunSounds.AA12_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "aa12_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.AK47_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.AK47_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			Sounds.playClip(GunSounds.AK47_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "ak47_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.AWP_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.AWP_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			Sounds.playClip(GunSounds.AWP_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "awp_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.FLAMETHROWER_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.FLAMETHROWER_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineFlameBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			break;
		case GunVars.ICESHOTGUN_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.ICESHOTGUN_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			//Sounds.playClip(shotSound, 1, "iceshotgun_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.GLOCK17_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.GLOCK17_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			Sounds.playClip(GunSounds.GLOCK17_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "glock17_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.M4_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.M4_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			Sounds.playClip(GunSounds.M4_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "m4_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.MINIGUN_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.MINIGUN_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			Sounds.playClip(GunSounds.MINIGUN_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "minigun_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.P90_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.P90_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			Sounds.playClip(GunSounds.P90_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "p90_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.RPD_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.RPD_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineBullet(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			Sounds.playClip(GunSounds.RPD_SHOT_ID, 1, volume, false);
			//Sounds.playClip(shotSound, 1, "rpd_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			break;
		case GunVars.RPG_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.RPG_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineRocket(handler, playermp.getCenterX(),
					playermp.getCenterY(), range, playermp.getAngle(), isUpgraded));
			//Sounds.playClip(shotSound, 1, "rpg_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			Sounds.playClip(GunSounds.RPG_SHOT_ID, 1, volume, false);
			break;
		case GunVars.WINCHESTER1901_UPGRADEDNAME:
			Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
			//Sounds.playClip(GunSounds.upgraded, 1, "upgraded" + RandomUtil.nextInt(0, 10000), volume, false);
		case GunVars.WINCHESTER1901_NAME:
			handler.getWorld().getEntityManager()
					.addEntity(new OnlineShotgunBullet(handler, playermp.getCenterX(), playermp.getCenterY(), range,
							playermp.getAngle(), GunVars.WINCHESTER1901_PELLET_SPREAD,
							GunVars.WINCHESTER1901_PELLET_COUNT, isUpgraded));
			//Sounds.playClip(shotSound, 1, "1901_shot" + RandomUtil.nextInt(0, 10000), volume, false);
			Sounds.playClip(GunSounds.WINCHESTER1901_SHOT_ID, 1, volume, false);
			break;
		}
	}

	public void reload() {
		//Sounds.playClip(reloadSound, 1, -1.0f, false);
	}

	public String getName() {
		return name;
	}

	public Handler getHandler() {
		return handler;
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

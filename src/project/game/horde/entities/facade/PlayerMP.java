package project.game.horde.entities.facade;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Creature;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;
import project.game.horde.weapons.GunVars;

public class PlayerMP extends Entity{

	private User user;
	private float angle;
	private OnlineGun gun;
	private int health;
	private String activatedBlessing;
	private boolean justTookDamage;
	private Timer tookDamageTimer = new Timer(50);
	private OnlineLuna luna;
	private String username;
	
	private int reviveProgress = 0;
	private int reviveMax = 300;
	private boolean isBeingRevived = false;
	
	public PlayerMP(Handler handler, float x, float y, int z, User user) {
		super(handler, x, y, z, Creature.DEFAULT_CREATURE_WIDTH, 
				Creature.DEFAULT_CREATURE_HEIGHT);
		this.user = user;
		this.username = user.getUsername();
		this.gun = new OnlineGun(handler, this);
		health = 100;
		activatedBlessing = "";
		justTookDamage = false;
	}
	
	public void tick() {
		if(justTookDamage) {
			tookDamageTimer.tick();
			if (tookDamageTimer.isReady()) {
				justTookDamage = false;
			}
		}
	}
	
	public boolean progressRevive() {
		isBeingRevived = true;	
		if(handler.getCurrentPlayer().getInv().getRevive() > -1) {
			reviveProgress++;
		}
		reviveProgress++;
		if(reviveProgress >= reviveMax) {
			isBeingRevived = false;
			reviveProgress = 0;
			//send online data that they have been revived
			if(handler.getCurrentPlayer().getInv().getRevive() == 3)
				handler.getCurrentPlayer().getPeer().sendRevived(handler.getCurrentPlayer().getUsername(), username, 100);
			else 
				handler.getCurrentPlayer().getPeer().sendRevived(handler.getCurrentPlayer().getUsername(), username, 50);
			return true;
		}
		return false;
	}
	
	public void cancelRevive() {
		isBeingRevived = false;
		reviveProgress = 0;
	}
	
	public void spawnLuna() {
		luna = new OnlineLuna(handler, username);
	}
	
	public void despawnLuna() {
		luna = null;
	}
	
	public void moveLuna(float x, float y, float angle) {
		luna.setX(x);
		luna.setY(y);
		luna.setAngle(angle);
	}
	
	public void justTookDamage() {
		justTookDamage = true;
	}
	
	public void shootBullet() {
		gun.shoot();
	}
	
	public void playFlamethrower() {
		float dist = Utils.getEuclideanDistance(getCenterX(), getCenterY(),
				handler.getCurrentPlayer().getCenterX(), handler.getCurrentPlayer().getCenterY());
		float volume = ((float) (1.0f - (float) (dist / 3000) - 0.1f));
		//Sounds.playClip(GunSounds.flamethrower_shot, 1, user.getUsername() + "_flamethrower_shot", volume, false);
		String sound = GunSounds.getFlamethrowerCurrent();
		Sounds.playClip(sound, 1, volume, false);
	}

	
	public void throwGrenade(String grenade, int destX, int destY) {
		switch(grenade) {
		case GunVars.GRENADE_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineGrenade(
					handler, (float) getCenterX(), (float) getCenterY(), z, 
					false, (float) destX, (float) destY, angle));
			break;
		case GunVars.GAS_GRENADE_NAME:
			handler.getWorld().getEntityManager().addEntity(new OnlineGasGrenade(
					handler, (float) getCenterX(), (float) getCenterY(), z, 
					(float) destX, (float) destY, angle));
			break;
		}
	}
	
	public void activateBlessing(String blessing) {
		activatedBlessing = blessing;
		if(activatedBlessing.equals(BlessingInventory.FREEZE_ALL_ZOMBIES)) {
			for(Zombie z : handler.getWorld().getEntityManager().getZombies())
				z.getFreezeStatus().freeze(null);
			activatedBlessing = "";
		}
		if(activatedBlessing.equals(BlessingInventory.ROUND_SKIP)) {
			handler.getRoundLogic().wipeRound();
			for (Zombie z : handler.getWorld().getEntityManager().getZombies()) {
				z.dieByTrap();
			}
			activatedBlessing = "";
		}
	}
	
	public void deactivateBlessing() {
		activatedBlessing = "";
	}
	
	public String getBlessing() {
		return activatedBlessing;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public String getUsername() {
		return user.getUsername();
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	public float getAngle() {
		return angle;
	}
	public OnlineGun getOnlineGun() {
		return gun;
	}
	
	@Override
	public void render(Graphics g) {
		if(luna != null) {
			luna.render(g);
		}
		g.drawImage(Assets.shadow, (int) (x - 10 - handler.getGameCamera().getxOffset()),
				(int) (y - 10 - handler.getGameCamera().getyOffset()), width, height, null);

	
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();

		if (health <= 0) {
			g2d.drawImage(Assets.player[3], (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			g2d.setTransform(old);
		} else {
			g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
					y - handler.getGameCamera().getyOffset() + height / 2);
			
			if (justTookDamage == true) {
				g2d.drawImage(Assets.player[1], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			} else if (health <= 50) {
				g2d.drawImage(Assets.player[2], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			} else {
				g2d.drawImage(Assets.player[0], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			}

			g2d.setTransform(old);
		}
		
		if(user.getUsername() != null) {
			Utils.drawCenteredString(g, user.getUsername(), new Rectangle( (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, 12), new Font(Font.DIALOG, Font.PLAIN,12));
		}
	}
	
	@Override
	public void renderBW(Graphics g) {
		if(luna != null) {
			luna.renderBW(g);
		}
		g.drawImage(BWAssets.shadow, (int) (x - 10 - handler.getGameCamera().getxOffset()),
				(int) (y - 10 - handler.getGameCamera().getyOffset()), width, height, null);

	
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();

		if (health <= 0) {
			g2d.drawImage(BWAssets.player[3], (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			g2d.setTransform(old);
		} else {
			g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
					y - handler.getGameCamera().getyOffset() + height / 2);
			
			if (justTookDamage == true) {
				g2d.drawImage(BWAssets.player[1], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			} else if (health <= 50) {
				g2d.drawImage(BWAssets.player[2], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			} else {
				g2d.drawImage(BWAssets.player[0], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
			}

			g2d.setTransform(old);
		}
		
		if(user.getUsername() != null) {
			Utils.drawCenteredString(g, user.getUsername(), new Rectangle( (int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, 12), new Font(Font.DIALOG, Font.PLAIN,12));
		}
	}

	public int getReviveProgress() {
		return reviveProgress;
	}

	public void setReviveProgress(int reviveProgress) {
		this.reviveProgress = reviveProgress;
	}

	public int getReviveMax() {
		return reviveMax;
	}

	public void setReviveMax(int reviveMax) {
		this.reviveMax = reviveMax;
	}

	public boolean isBeingRevived() {
		return isBeingRevived;
	}

	public void setBeingRevived(boolean isBeingRevived) {
		this.isBeingRevived = isBeingRevived;
	}
	
	

}

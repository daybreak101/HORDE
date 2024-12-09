package project.game.horde.perks;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public abstract class Perk {
	
	Handler handler;
	String name;
	BufferedImage icon;
	int cooldown, maxCooldown;
	int level;
	Player player;
	
	//upgrades menu vars
	String realName, jokeDesc, baseDesc, lvl1Desc, lvl2Desc, lvl3Desc;
	
	public Perk(Handler handler, int level, Player player) {
		this.handler = handler;
		this.level = level;
		this.player = player;
	}
	
	public void tick() {}
	
	public void render(Graphics g) {}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public BufferedImage getIcon() {
		return icon;
	}

	public abstract void buff();
	
	public abstract void debuff();
	
	public abstract void incrementLevelUpgrade(int level);

	public String getRealName() {
		return realName;
	}

	public String getJokeDesc() {
		return jokeDesc;
	}

	public String getBaseDesc() {
		return baseDesc;
	}

	public String getLvl1Desc() {
		return lvl1Desc;
	}

	public String getLvl2Desc() {
		return lvl2Desc;
	}

	public String getLvl3Desc() {
		return lvl3Desc;
	}
	
	
}

package project.game.horde.perks;

import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Wolf;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class Luna extends Perk {

	public static final int REFRESH_DURATION = 10 * 60;
	public static final int ACTIVE_DURATION = 60 * 60;
	public static final int SMALL_POINTS_REWARD = 50;
	public static final int SMALL_HEALTH_REWARD = 5;
	public static final int LVL2_REFRESHDURATION = REFRESH_DURATION / 2;
	
	Timer refresh;
	Timer duration;
	boolean activated = false;
	boolean ready = true;
	
	Wolf luna;
	
	public Luna(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Luna";
		this.icon = Assets.luna;
		realName = "Ghost Pupper";
		jokeDesc = "There's no such things as ghosts. But if you see one while eating these, you should probably see a doctor! And you're a pansy for not tolerating a scoville rating of 4 billion.";
		baseDesc = "Summon Aurora, a dog that aids you in battle";
		lvl1Desc = "Aurora now drops ammo clips and points";
		lvl2Desc = "Aurora is eager to help more often";
		lvl3Desc = "Aurora can drop powerups";
		if(level >= 2)
			refresh = new Timer(REFRESH_DURATION);
		else
			refresh = new Timer(LVL2_REFRESHDURATION);
		duration = new Timer(ACTIVE_DURATION);
		
	}
	

	public void tick() {
		if (luna != null) {
			luna.tick();
		}
		if(activated == false && ready) {
			activated = true;
			ready = false;
			luna = new Wolf(handler, player.getX(), player.getY(), player);
			luna.playEnterSound();
			if(player.getPeer() != null)
				player.getPeer().sendNewLuna(player.getUsername());
		}
		if(activated) {
			duration.tick();
		}
		else if(!activated) {
			refresh.tick();
		}
		
		if(duration.isReady()) {
			duration.resetTimer();
			activated = false;
			ready = false;
			luna.playLeaveSound();
			luna = null;
			if(player.getPeer() != null)
				player.getPeer().sendRemoveLuna(player.getUsername());
		}
		else if(refresh.isReady()) {
			refresh.resetTimer();
			ready = true;
		}
		
	}
	
	public void render(Graphics g) {
		if (luna != null) {
			luna.render(g);
		}
	}
	
	@Override
	public void buff() {
		player.getInv().setLuna(level);
		
	}

	@Override
	public void debuff() {
		player.getInv().setLuna(-1);
		if(luna != null) {
			luna = null;
			if(player.getPeer() != null)
				player.getPeer().sendRemoveLuna(player.getUsername());
	
		}
	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setLunaLvl(level);
		}
	}

}

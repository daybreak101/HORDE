package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class DeadShot extends Perk{
	
	//increased range, increase crit chance and crit damage
	public static final float BASE_RANGEBUFF = 1.5f;
	public static final int LVL1_HEADSHOTPOINTBUFF = 80;
	public static final int LVL2_CRITCHANCEBUFF = 25;
	public static final float LVL3_HEADSHOTDAMAGEBUFF = 3.0f;

	public DeadShot(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "DeadShot";
		this.icon = Assets.deadshot;
		realName = "Buffalo Bullseye";
		jokeDesc = "Ever feel like you never hit your targets? Introducing a flavor that never misses! Since you now hit 100% of the shots you take, pose for that moneyshot baby!";
		baseDesc = "Increased range";
		lvl1Desc = "Headshots give more points";
		lvl2Desc = "Higher chance of headshots";
		lvl3Desc = "Increased headshot damage";
	}

	@Override
	public void buff() {
		player.getInv().setDeadshot(level);
		
		
		
	}

	@Override
	public void debuff() {
		player.getInv().setDeadshot(-1);
		
	}

	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setDeadshotLvl(level);
		}
	}

	

}

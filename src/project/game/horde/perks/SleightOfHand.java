package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class SleightOfHand extends Perk {
	public static final float BASE_RELOADBUFF = .9f;
	public static final float LVL1_RELOADBUFF = .8f;
	public static final float LVL2_RELOADBUFF = .7f;
	public static final float LVL3_RELOADBUFF = .6f;
	
	//increased reload speed

	public SleightOfHand(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Sleight Of Hand";
		this.icon = Assets.fasthand;
		realName = "Reload Ranch";
		jokeDesc = "Are you a caffeine junkie? Are you also a fan of Cool Ranch? Well look no further! These addictive chips contain 10000mg of caffeine each serving. Now look who's heart beats with joy!";
		baseDesc = "Reload faster by 10%";
		lvl1Desc = "Reload faster by 25%";
		lvl2Desc = "Reload faster by 50%";
		lvl3Desc = "Reload faster by 70%";
	}

	@Override
	public void buff() {
		player.getInv().setSpeedcola(level);
		
	}

	@Override
	public void debuff() {
		player.getInv().setSpeedcola(-1);
		
	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setSpeedLvl(level);
		}
	}
}

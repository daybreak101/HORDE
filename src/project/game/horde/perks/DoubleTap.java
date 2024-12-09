package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class DoubleTap extends Perk{
	
	//increase fire rate
	public static final float BASE_FIRERATEBUFF = 0.75f;
	public static final float LVL1_PENETRATEBUFF = 1.5f;
	public static final float LVL2_FIRERATEBUFF = 0.5f;
	public static final float LVL3_DAMAGEBUFF = 1.0f; //is added to multiplier
	
	public DoubleTap(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Double Tap";
		this.icon = Assets.doubletap;
		realName = "Double STUFF3D";
		jokeDesc = "A chip within a chip. These revolutionary chips won't stay for long, so remember not to get too excited, go slow, make eye-contact, and savor every swallow! (Choking hazard)";
		baseDesc = "Slightly increase fire rate";
		lvl1Desc = "Increase bullet penetration";
		lvl2Desc = "Increase fire rate";
		lvl3Desc = "Double damage";
	}

	@Override
	public void buff() {
		player.getInv().setDoubletap(level);
		
	}

	@Override
	public void debuff() {
		player.getInv().setDoubletap(-1);
		
	}

	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setDoubletapLvl(level);
		}
	}
}

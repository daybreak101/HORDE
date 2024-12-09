package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class Revive extends Perk{

	public Revive(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Revive";
		this.icon = Assets.revive;
		realName = "Cardiac Resurrect";
		jokeDesc = "These taste like the ones they give out at church... except saltier. No it's not the same thing, we just like symbolism!";
		baseDesc = "Revive yourself in solo / revive faster in co-op";
		lvl1Desc = "Take less damage while reviving";
		lvl2Desc = "Ability to attack and move while downed";
		lvl3Desc = "Reviving players will regain all health";
	}

	@Override
	public void buff() {
		player.getInv().setRevive(level);
		
	}

	@Override
	public void debuff() {
		player.getInv().setRevive(-1);
		
	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setReviveLvl(level);
		}
	}

}

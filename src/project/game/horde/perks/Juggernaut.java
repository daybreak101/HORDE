package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class Juggernaut extends Perk {

	// increase health
	public static final int BASE_HEALTHBUFF = 110;
	public static final int LVL1_HEALTHBUFF = 125;
	public static final int LVL2_HEALTHBUFF = 150;
	public static final int LVL3_HEALTHBUFF = 200;

	public Juggernaut(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Juggernaut";
		this.icon = Assets.jugg;
		realName = "Meatmunchers";
		jokeDesc = "Many of us like to be called it, but how many of us are? Now you can prove your worth, by putting 7 different kinds of meat in your mouth!";
		baseDesc = "Increase health by 10 hp";
		lvl1Desc = "Increase health by 25 hp";
		lvl2Desc = "Increase health by 50 hp";
		lvl3Desc = "Increase health by 100 hp";
	}

	@Override
	public void buff() {
		player.getInv().setJugg(level);

	}

	@Override
	public void debuff() {
		player.getInv().setJugg(-1);
	}

	@Override
	public void incrementLevelUpgrade(int level) {
		if (level > this.level) {
			handler.getUnlocks().setJuggLvl(level);
		}
	}
}

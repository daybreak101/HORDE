package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class PhD extends Perk {
	
	//immunity to explosives
	public static final float BASE_EXPLOSIVERESIST = .5f;
	public static final float LVL2_EXPLOSIVERESIST = 0f;
	public static final float LVL3_EXPLOSIVEDAMAGEBUFF = 1.0f; //adds to multiplier

	public PhD(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "PhD Armor";
		this.icon = Assets.phd;
		realName = "Bomb-B-Q's";
		jokeDesc = "An explosion of barbeque sauce flavor in your mouth! Will hurt at first but you'll adapt to its explosiveness. (Must have a strong jaw before eating)";
		baseDesc = "Increase explosive damage resistance by 50%";
		lvl1Desc = "Equip cluster grenades";
		lvl2Desc = "Immune to all explosive damage";
		lvl3Desc = "Increase damage of explosives";
	}

	@Override
	public void buff() {
		player.getInv().setPhd(level);
		
	}

	@Override
	public void debuff() {
		player.getInv().setPhd(-1);
		
	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setPhdLvl(level);
		}
	}

}

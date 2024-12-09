package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class MuleKick extends Perk {

	public static final int LVL1_REGAINGRENADECHANCE = 34;
	public static final int LVL1_REGAINSPECIALGRENADECHANCE = 20;

	public MuleKick(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Mule Kick";
		this.icon = Assets.mule;
		realName = "Party Size";
		jokeDesc = "Twosome's are vanilla, Threesome's a party! We are revolutionizing the chip game by adding ONE more chip per bag! Chester's been silent after this one!";
		baseDesc = "Carry a third weapon";
		lvl1Desc = "Chance to regain any grenades after throwing one";
		lvl2Desc = "Carry extra magazines";
		lvl3Desc = "Retrieve your lost weapon after buying perk again";
	}

	@Override
	public void buff() {
		player.getInv().setMule(level);
		if (level >= 2) {
			for (int i = 0; i < player.getInv().getArsenal().length; i++) {
				if (player.getInv().getArsenal()[i] != null)
					player.getInv().getArsenal()[i].activateLevel2MuleKick();
			}
		}
	}

	@Override
	public void debuff() {
		if (level >= 2) {
			for (int i = 0; i < player.getInv().getArsenal().length; i++) {
				if (player.getInv().getArsenal()[i] != null)
					player.getInv().getArsenal()[i].deactivateLevel2MuleKick();
			}
		}

		player.getInv().setMule(-1);
		player.getInv().setCurrentGun(0);
		if (level != 3)
			player.getInv().getArsenal()[2] = null;

	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setMuleLvl(level);
		}
	}

}

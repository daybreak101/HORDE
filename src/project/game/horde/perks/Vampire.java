package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class Vampire extends Perk {
	
	//gain one health per kill, can override health cap by 25
	public static final int TEMPHEALTH_LOSS_TIMER = 120;
	public static final int TEMPHEALTH_GAIN = 1;
	public static final int LVL1_BOSSDRAIN = 5;
	public static final int LVL2_HEALTHSURPLUS = 25;
	
	public Vampire(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Dracula's Hunger";
		this.icon = Assets.vamp;
		realName = 	"Crimson Bites";
		jokeDesc = 	"Produced at night, no expiration date, and burns easy with sunlight. The metal-like taste is just coated heavily oxidized ketchup!";
		baseDesc = "Gain temporary health for every kill.";
		lvl1Desc = "Boss zombie kills gain bigger temporary health";
		lvl2Desc = "Temporary health can surpass max health";
		lvl3Desc = "Temporary health is now permanent";
	}
	
	Timer tempHealthLossTimer = new Timer(Vampire.TEMPHEALTH_LOSS_TIMER);
        @Override
	public void tick() {
		if (player.getInv().getVamp() != 3 && player.getTempHealth() > 0) {
			tempHealthLossTimer.tick();
			if (tempHealthLossTimer.isReady()) {
				player.setTempHealth(player.getTempHealth() - 1);
				tempHealthLossTimer.resetTimer();
			}
		}
	}

	@Override
	public void buff() {
		player.getInv().setVamp(level);
		
	}

	@Override
	public void debuff() {
		player.getInv().setVamp(-1);
		
	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setVampireLvl(level);
		}
	}

}

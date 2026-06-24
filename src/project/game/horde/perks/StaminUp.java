package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class StaminUp extends Perk {
	
	public static final float BASE_NEWDEFAULTSPEED = 4.5f;
	public static final int LVL1_SPRINTCOOLDOWN = 100;
	public static final int LVL3_MAXSTAMINA = 300;

	public StaminUp(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "StaminUp!";
		this.icon = Assets.stam;
		realName = "Keto Kardio";
		jokeDesc = "Feeling overweight? Feeling like cutting out carbs while not compromising flavor? These chips are (not) clinically proven to be the best weight-loss solution on the planet. Just ignore the sodium content, we had to make it taste good somehow!";
		baseDesc = "Increase sprint speed and walk speed";
		lvl1Desc = "Decrease sprint cooldown";
		lvl2Desc = "Increase stamina regeneration";
		lvl3Desc = "Greatly increase sprint duration";
	}

	@Override
	public void buff() {
		player.getInv().setStaminup(level);
		player.setDefaultSpeed(BASE_NEWDEFAULTSPEED);
		if(level >= 1) {
			player.getPlayerSprint().setStaminaCooldown(LVL1_SPRINTCOOLDOWN);
		}
		if (level == 3) {
			player.getPlayerSprint().setMaxStamina(LVL3_MAXSTAMINA);
			player.getPlayerSprint().setCurrentStamina(LVL3_MAXSTAMINA);
		}
	}

	@Override
	public void debuff() {
		player.getInv().setStaminup(-1);
		player.setDefaultSpeed(4.0f);
		player.getPlayerSprint().setStaminaCooldown(180);
		player.getPlayerSprint().setMaxStamina(200);
		player.getPlayerSprint().setCurrentStamina(200);
		
	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setStaminaLvl(level);
		}
	}

}

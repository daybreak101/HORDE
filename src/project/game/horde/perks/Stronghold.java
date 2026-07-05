package project.game.horde.perks;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class Stronghold extends Perk{

	public static final int TIME_TO_ACTIVATE = 300;
	public static final int TICKS_PER_BUFF = 60;
	public static final float DAMAGE_BUFF_INCREMENTS = .05f;
	public static final int ARMOR_GAIN_INCREMENTS = 2;
	public static final float LVL1_DAMAGEBUFFCAP = .25f;
	public static final float LVL2_DAMAGEBUFFCAP = .50f;
	public static final int BASE_MAXARMOR = 10;
	public static final int LVL2_MAXARMOR = 30;
	
	public Stronghold(Handler handler, int level, Player player) {
		super(handler, level, player);
		this.name = "Stronghold";
		this.icon = Assets.stronghold;
		realName = "Fortified Kettle";
		jokeDesc = 	"Stand your ground. This is your favorite flavor. You'll feel more confident defending your bad opinions. (Look how confident you are!)";
		baseDesc = "Gain armor when standing still and remaining within the circle";
		lvl1Desc = "Gain damage as well";
		lvl2Desc = "Max armor and damage is increased";
		lvl3Desc = "Gain armor and damage for every zombie killed within the circle";
	}

	Timer activationTimer = new Timer(TIME_TO_ACTIVATE);
	Timer strengthTick = new Timer(TICKS_PER_BUFF);
	boolean activated = false;
	@Override
	public void tick() {

		if(player.moved == false) {
			activationTimer.tick();
		}
		else {
			activationTimer.resetTimer();
		}
		
		if(activated) {			
			strengthTick.tick();
			if(strengthTick.isReady()) {
				player.gainStrongholdArmor(ARMOR_GAIN_INCREMENTS);
				player.gainStrongholdDamageMultiplier(DAMAGE_BUFF_INCREMENTS);
			}
			
			if(!player.checkIfInStrongholdCircle()){
				activated = false;
			}
		}
		else if(activationTimer.isReady()) {
			activated = true;
			player.setStrongholdCircle();
		}
		else {
			activated = false;
			player.getInv().strongholdActivation = false;
		}
		
	}

	@Override
	public void buff() {
		player.getInv().setStronghold(level);
		
	}

	@Override
	public void debuff() {
		player.getInv().setStronghold(-1);
		player.getInv().strongholdActivation = false;
		player.removeArmor();
		player.removeStrongholdDamageMultiplier();
		activated = false;
		
	}
	
	@Override
	public void incrementLevelUpgrade(int level) {
		if(level > this.level) {
			handler.getUnlocks().setStrongholdLvl(level);
		}
	}

}

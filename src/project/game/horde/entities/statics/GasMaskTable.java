package project.game.horde.entities.statics;

import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.playerinfo.GasMask;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;

public class GasMaskTable extends InteractableStaticEntity {

	private boolean cantAfford = false;
	
	//has gas mask means that they have full durability on gas mask
	private boolean hasGasMask = false;
	
	
	public GasMaskTable(Handler handler, int id, float x, float y, int width, int height) {
		super(handler, id, x, y, width, height);
		triggerText = "Press F to refill gas mask: 1000";
	}
	
	@Override
	public void render(Graphics g) {
		//change asset
		g.drawImage(Assets.ammoBox, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
	}
	


        @Override
	public void fulfillInteraction(Player player) {
		// && gas mask is not full
		GasMask gasMask = player.getInv().getGasMask();
		if(cooldownTimer >= cooldown ) {
			cooldownTimer = 0;
			if(player.getInv().purchase(1000) && gasMask.getCurrentDurability() < gasMask.getMaxDurability()) {
				gasMask.repairMask();;
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				cantAfford = false;
			}
			else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}
			
		}
		
		// already has full has mask
		else if(gasMask.getCurrentDurability() >= gasMask.getMaxDurability()) {
			cooldownTimer = 0;
			hasGasMask = true;
		}
		
		
	}

	@Override
	public void postTick() {
		
		if(cantAfford && cooldownTimer < cooldown) {
			triggerText = "Not enough points!";
		}
		else if(hasGasMask && cooldownTimer < cooldown) {
			triggerText = "Already have gas mask!";
		}
		else if(cooldownTimer >= cooldown) {
			hasGasMask = false;
			triggerText = "Press F to refill gas mask: 1000";
		}
		else {
			triggerText = "";
		}
	}

	
}

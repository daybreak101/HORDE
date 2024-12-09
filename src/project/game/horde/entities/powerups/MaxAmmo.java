package project.game.horde.entities.powerups;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public class MaxAmmo extends PowerUps{

	public MaxAmmo(Handler handler, int id, float x, float y, int z) {
		super(handler, id, x, y, z, true);
		name = "Max Ammo";
		icon = null;
		floatingAsset = null;
	}
	
	public void tick() {
		cooldownTimer++;
		trigger = new Rectangle((int) (x), (int) (y), width, height);
		
		if(cooldownTimer >= cooldown) {
			handler.getWorld().getEntityManager().getPowerups().remove(this);
			//handler.getWorld().getEntityManager().getEntities().remove(this);
		}
		else if(pickedUp) {
			fulfillInteraction(playerPicked);
			handler.getWorld().getEntityManager().getPowerups().remove(this);
			//handler.getWorld().getEntityManager().getEntities().remove(this);
		}
		else if(!pickedUp) {
			checkPickedUp();
		}
	}
	
	@Override
	public void fulfillInteraction(String username) {
		handler.getCurrentPlayer().getInv().maxAmmo();
	}

	@Override
	public void render(Graphics g) {
		if(!pickedUp) {	
			g.setColor(Color.green);
			g.drawOval((int) (x - handler.getGameCamera().getxOffset()) , (int) (y - handler.getGameCamera().getyOffset()), width, height);
		}
	}

}

package project.game.horde.entities.powerups;

import java.awt.Rectangle;

import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class LunaClip extends PowerUps{
	public LunaClip(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, false);
		name = "Luna Health";
		icon = null;
		floatingAsset = null;
		glow = Assets.purpleStar;
	}

        @Override
	public void tick() {
		cooldownTimer++;
		trigger = new Rectangle((int) (x), (int) (y), width/2, height/2);

		if (cooldownTimer >= cooldown) {
			handler.getWorld().getEntityManager().getPowerups().remove(this);
		} else if (pickedUp) {
			if (playerPicked.equals(handler.getCurrentPlayer().getUsername())) {
				fulfillInteraction(playerPicked);
			}
			handler.getWorld().getEntityManager().getPowerups().remove(this);
		} else if (!pickedUp) {
			checkPickedUp();
		}
	}

	@Override
	public void fulfillInteraction(String username) {
		handler.getCurrentPlayer().getInv().getGun().giveClip();

	}

//	@Override
//	public void render(Graphics g) {
//		if (!pickedUp) {
//			g.setColor(Color.green);
//			g.drawOval((int) (x - handler.getGameCamera().getxOffset()),
//					(int) (y - handler.getGameCamera().getyOffset()), width/2, height/2);
//		}
//	}
}

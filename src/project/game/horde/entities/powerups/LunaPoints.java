package project.game.horde.entities.powerups;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.main.Handler;
import project.game.horde.perks.Luna;

public class LunaPoints extends PowerUps {
	public LunaPoints(Handler handler, int id, float x, float y, int z) {
		super(handler, id, x, y, z, false);
		name = "Luna Points";
		icon = null;
		floatingAsset = null;
	}

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
		handler.getCurrentPlayer().getInv().gainPoints(Luna.SMALL_POINTS_REWARD);

	}

	@Override
	public void render(Graphics g) {
		if (!pickedUp) {
			g.setColor(new Color(193, 255, 135));
			g.drawOval((int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width/2, height/2);
		}
	}
}

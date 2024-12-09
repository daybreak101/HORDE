package project.game.horde.entities.powerups;

import java.awt.Color;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class InstaKill extends PowerUps{

	public InstaKill(Handler handler, int id, float x, float y, int z) {
		super(handler, id, x, y, z, true);
		name = "InstaKill";
		icon = Assets.instakill;
		floatingAsset = null;
	}

	@Override
	public void unbuff() {
		handler.getRoundLogic().getPowerups().setInstakillActive(false);
	}

	@Override
	public void fulfillInteraction(String username) {
		handler.getRoundLogic().getPowerups().setInstakillActive(true);
	}

	@Override
	public void render(Graphics g) {
		if(!pickedUp) {	
			g.setColor(Color.red);
			g.drawOval((int) (x - handler.getGameCamera().getxOffset()) , (int) (y - handler.getGameCamera().getyOffset()), width, height);
		}
	}	
	
}

package project.game.horde.entities.powerups;

import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class DoublePoints extends PowerUps{

	public DoublePoints(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, true);
		name = "Double Points";
		icon = Assets.doublepoints;
		floatingAsset = Assets.doublepoints;
		glow = Assets.greenStar;
	}

	@Override
	public void unbuff() {
		handler.getRoundLogic().getPowerups().setDoublePointsActive(false);
	}

	@Override
	public void fulfillInteraction(String username) {
		handler.getRoundLogic().getPowerups().setDoublePointsActive(true);
	}

//
//	@Override
//	public void render(Graphics g) {
//		if(!pickedUp) {
//			g.setColor(Color.blue);
//			g.drawOval((int) (x - handler.getGameCamera().getxOffset()) , (int) (y - handler.getGameCamera().getyOffset()), width, height);
//		}
//	}	
}

package project.game.horde.entities.powerups;

import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class InfiniteAmmo extends PowerUps {

	public InfiniteAmmo(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, true);
		name = "Infinite Ammo";
		icon = Assets.infiniteammo;
		floatingAsset = Assets.infiniteammo;
		glow = Assets.pinkStar;
	}

	@Override
	public void unbuff() {
		handler.getRoundLogic().getPowerups().setInfiniteAmmoActive(false);
	
	}

	@Override
	public void fulfillInteraction(String username) {
		handler.getRoundLogic().getPowerups().setInfiniteAmmoActive(true);
		handler.getCurrentPlayer().getInv().infiniteAmmo();
		
	}

//	@Override
//	public void render(Graphics g) {
//		if(!pickedUp) {	
//			g.setColor(Color.magenta);
//			g.drawOval((int) (x - handler.getGameCamera().getxOffset()) , (int) (y - handler.getGameCamera().getyOffset()), width, height);
//		}
//	}

}

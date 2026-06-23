package project.game.horde.entities.powerups;

import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class DeathMachine extends PowerUps {

	public DeathMachine(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, false);
		name = "Death Machine";
		icon = Assets.deathmachine;
		floatingAsset = Assets.deathmachine;
		glow = Assets.blueStar;
	}

	@Override
	public void unbuff() {
		if (playerPicked.equals(handler.getCurrentPlayer().getUsername())) {
			handler.getCurrentPlayer().getInv().switchWeapon();	
			handler.getRoundLogic().getPowerups().setDeathMachineActive(false);
		}
		
		
	}

	@Override
	public void fulfillInteraction(String username) {
		if (username.equals(handler.getCurrentPlayer().getUsername())) {
			handler.getCurrentPlayer().getInv().setCurrentGun(3);
			handler.getRoundLogic().getPowerups().setDeathMachineActive(true);

		}
	}

//	@Override
//	public void render(Graphics g) {
//		if(!pickedUp) {
//			g.setColor(Color.pink);
//			g.drawOval((int) (x - handler.getGameCamera().getxOffset()) , (int) (y - handler.getGameCamera().getyOffset()), width, height);
//		}
//	}

}

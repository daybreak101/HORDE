package project.game.horde.maps;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.entities.Entity;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;

public class FarmMap extends Entity {

	private BufferedImage map;

	public FarmMap(Handler handler, float x, float y, int z, int width, int height) {
		super(handler, x, y, z, 0, 0);
	}

	public void renderFirstFloor(Graphics g) {
		map = Assets.firstFloorFarm;
		g.drawImage(map, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), map.getWidth(), map.getHeight(), null);

	}
	
	public void renderSecondFloor(Graphics g) {
		map = Assets.secondFloorFarm;
		g.drawImage(map, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), map.getWidth(), map.getHeight(), null);

	}

	@Override
	public void renderBW(Graphics g) {
		//map = BWAssets.factoryMap;
		g.drawImage(map, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void render(Graphics g) {
		int z = handler.getCurrentPlayer().getZ();
		if(z >= 0) {
			renderFirstFloor(g);
		}
		if(z >= 100) {
			renderSecondFloor(g);
		}
	}

}

package project.game.horde.maps;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.entities.Entity;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;

public class FactoryMap extends Entity {

	private BufferedImage map;

	public FactoryMap(Handler handler, float x, float y, int z, int width, int height) {
		super(handler, x, y, z, width, height);
	}

	@Override
	public void render(Graphics g) {
		map = Assets.factoryMap;
		g.drawImage(map, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}

	@Override
	public void renderBW(Graphics g) {
		map = BWAssets.factoryMap;
		g.drawImage(map, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);

	}

}

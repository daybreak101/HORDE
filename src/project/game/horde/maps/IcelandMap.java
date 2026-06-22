package project.game.horde.maps;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.entities.Entity;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;

public class IcelandMap extends Entity {

	private BufferedImage map;

	public IcelandMap(Handler handler, float x, float y, int z, int width, int height) {
		super(handler, x, y, z, 0, 0);
	}

	public void renderStartArea(Graphics g) {
		//map = Assets.seattle_transport;
		int areaX = 0;
		int areaY = 0;
		g.drawImage(Assets.iceland, (int) (areaX - handler.getGameCamera().getxOffset()),
				(int) (areaY - handler.getGameCamera().getyOffset()), Assets.iceland.getWidth(), Assets.iceland.getHeight(), null);
		
	}
	
	@Override
	public void renderBW(Graphics g) {
	}

	@Override
	public void render(Graphics g) {
		int z = handler.getCurrentPlayer().getZ();
		if(z >= 0) {
			renderStartArea(g);
		}
	}

}

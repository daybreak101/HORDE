package project.game.horde.maps;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.entities.Entity;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class SeattleMap extends Entity {
	private BufferedImage map;

	public SeattleMap(Handler handler, float x, float y, int z, int width, int height) {
		super(handler, x, y, z, 0, 0);
	}

	public void renderNeedle(Graphics g) {
		map = Assets.seattle_needle;
		g.drawImage(map, (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), map.getWidth(), map.getHeight(), null);
		
	}
	
	public void renderTransportArea(Graphics g) {
		//map = Assets.seattle_transport;
		int areaX = -4000;
		int areaY = 3000;
		//y: 4776 too low
		g.drawImage(Assets.seattle_transport, (int) (areaX - handler.getGameCamera().getxOffset()),
				(int) (areaY - handler.getGameCamera().getyOffset()), Assets.seattle_transport.getWidth(), Assets.seattle_transport.getHeight(), null);
		
	}
	
	public void renderCentralArea(Graphics g) {
		//map = Assets.seattle_transport;
		int areaX = -10000;
		int areaY = 3000;
		g.drawImage(Assets.seattle_central, (int) (areaX - handler.getGameCamera().getxOffset()),
				(int) (areaY - handler.getGameCamera().getyOffset()), Assets.seattle_central.getWidth(), Assets.seattle_central.getHeight(), null);
		
	}
	
	@Override
	public void renderBW(Graphics g) {
	}

	@Override
	public void render(Graphics g) {
		int z = handler.getCurrentPlayer().getZ();
		if(z >= 0) {
			renderNeedle(g);
			renderTransportArea(g);
			renderCentralArea(g);
		}
	}
}

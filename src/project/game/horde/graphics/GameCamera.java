package project.game.horde.graphics;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;

public class GameCamera {
	private Handler handler;
	private float xOffset, yOffset;


	public GameCamera(Handler handler, float xOffset, float yOffset) {
		this.handler = handler;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
	}
	

	
	public void centerOnEntity(Entity e) {
		
		xOffset = (float) (e.getX() - handler.getWidth()/2/handler.getSettings().getZoomLevel(false) 
				+ e.getWidth()/2  / handler.getSettings().getZoomLevel(false));
		yOffset = (float) (e.getY() - handler.getHeight()/2/ handler.getSettings().getZoomLevel(false) 
				+ e.getHeight()/2 / handler.getSettings().getZoomLevel(false));
	}
	
	public void move(float xAmt, float yAmt) {
		xOffset += xAmt * handler.getSettings().getZoomLevel(false);
		yOffset += yAmt * handler.getSettings().getZoomLevel(false);
	}
	
	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}
}

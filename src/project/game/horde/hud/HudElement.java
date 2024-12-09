package project.game.horde.hud;

import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.main.Handler;

public abstract class HudElement {
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected Handler handler;
	protected boolean isVisible;
	protected boolean isActive;
	
	public HudElement(float x, float y, int width, int height, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
		isVisible = true;
		isActive = true;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	
	public float getX() {
		return x;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public void setVisible(boolean visible) {
		isVisible = visible;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}
}

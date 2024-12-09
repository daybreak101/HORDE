package project.game.horde.ui;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import project.game.horde.main.Handler;
import project.game.horde.sounds.MenuSounds;
import project.game.horde.sounds.Sounds;

public abstract class UIObject {
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hovering = false;
	protected Handler handler;
	protected boolean isVisible = true;
	protected String clickSound = MenuSounds.MENU_BUTTON_CLICKS_ID;
	
	public UIObject(Handler handler) {
		this.handler = handler;
		bounds = new Rectangle();
	}
	
	public UIObject(Handler handler, float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		bounds = new Rectangle((int)x, (int) y, width, height);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void onClick(UIObject ui);
	
	public void onMouseMove(MouseEvent e) {
		if(bounds.contains(e.getX(), e.getY()))
			hovering = true;
		else
			hovering = false;

	}
	
	public void onMouseRelease(MouseEvent e) {
		if(hovering) {
			onClick(this);
			Sounds.playClip(clickSound, 1, 1, false);
		}
			
	}
		
	public void setClickSound(String clipId) {
		this.clickSound = clipId;
	}
	
	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
		bounds.x = (int) x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
		bounds.y = (int) y;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
		bounds.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
		bounds.height = height;
	}


	public boolean isHovering() {
		return hovering;
	}


	public void setHovering(boolean hovering) {
		this.hovering = hovering;
	}


	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public boolean getIsVisible() {
		return isVisible;
	}

	protected abstract Object getInfo();
}

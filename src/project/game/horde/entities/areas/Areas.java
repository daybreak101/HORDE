package project.game.horde.entities.areas;

import java.awt.Graphics;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;

public abstract class Areas extends Entity {
	
	protected Handler handler;
	protected float x, y;
	
	public Areas(Handler handler, float x, float y) {
		super(handler, x, y, 0, 0);
		this.handler = handler;
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
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

	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void renderBW(Graphics g);
	
}
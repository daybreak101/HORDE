package project.game.horde.entities.areas;

import java.awt.Graphics;

import project.game.horde.main.Handler;

public abstract class Areas {
	
	protected Handler handler;
	protected float x, y;
	protected int z;
	
	public Areas(Handler handler, float x, float y, int z) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void renderBW(Graphics g);
	
}

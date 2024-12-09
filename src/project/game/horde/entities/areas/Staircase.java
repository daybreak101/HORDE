package project.game.horde.entities.areas;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.main.Handler;

public class Staircase extends Areas {
	
	private int width, height;
	private int orientation; //0 for north, 1 for east, 2 for south, 3 for west
	private final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;
	private boolean goUp;
	private int zDest;
	private Rectangle bounds;

	public Staircase(Handler handler, float x, float y, int z, int width, int height, int goUp, int orientation) {
		super(handler, x, y, z);
		this.width = width;
		this.height = height;
		this.goUp = (goUp == 1);
		this.zDest = this.goUp ? z + 1 : z - 1;
		this.orientation = orientation;
		bounds = new Rectangle((int) x, (int) y, width, height);
	}

	@Override
	public void tick() {
		checkIfZAxisChange(handler.getCurrentPlayer());
		for(Zombie z : handler.getWorld().getEntityManager().getZombies()) {
			checkIfZAxisChange(z);
		}
	}
	
	//entity must be in bounds of the staircase
	private void checkIfZAxisChange(Entity e) {
		if(!e.getCollisionBounds(0, 0).intersects(bounds))
			return;
		
		
		int xThreshold = (int) (x + width/2);
		int yThreshold = (int) (y + height/2);
		int xPos = e.getCenterX();
		int yPos = e.getCenterY();
		switch(orientation) {
			case NORTH:
				if(yPos <= yThreshold) e.setZ(zDest);
				else e.setZ(z);
				break;
			case EAST:
				if(xPos >= xThreshold) e.setZ(zDest);
				else e.setZ(z);
				break;
			case SOUTH:
				if(yPos >= yThreshold) e.setZ(zDest);
				else e.setZ(z);
				break;
			case WEST:
				if(xPos <= xThreshold) e.setZ(zDest);
				else e.setZ(z);
				break;
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect((int)( x- handler.getGameCamera().getxOffset()), (int) (y- handler.getGameCamera().getyOffset()), width, height);
		
	}

	@Override
	public void renderBW(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}

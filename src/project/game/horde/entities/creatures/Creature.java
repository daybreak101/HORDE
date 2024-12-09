package project.game.horde.entities.creatures;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;

public abstract class Creature extends Entity {
	
	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 75, 
							DEFAULT_CREATURE_HEIGHT = 75;
	public static final int
		ZOMBIE = 0,
		LICKER = 1,
		TOXEN = 2,
		STOKER = 3;
	protected float speed;
	protected float xMove, yMove;

	public Creature(Handler handler, float x, float y, int z, int width, int height) {
		super(handler, x, y, z, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
	}
	
	public void move() {
		if(!checkEntityCollisions(xMove, 0f))
			moveX();
		if(!checkEntityCollisions(0f, yMove))
			moveY();
		
	}
	
	public void moveX() {
				x += xMove;
	}
	
	public void moveY() {
		y+= yMove;
	}
	
	
	//getters and setters

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}


	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void takeDamage(int burnDamage) {
		
	}
	
	public void takeDamage(int damage, Player player) {
		
	}
	
	public void die(Player player) {
		// TODO Auto-generated method stub
		
	}
	
	
}

package project.game.horde.entities.statics;

import project.game.horde.entities.Entity;
import project.game.horde.main.Handler;

public abstract class StaticEntity extends Entity{

	public StaticEntity(Handler handler, float x, float y, int z, int width, int height) {
		super(handler, x, y, z, width, height);
	}
	
	
	
}

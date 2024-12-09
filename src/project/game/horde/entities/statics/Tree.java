package project.game.horde.entities.statics;

import java.awt.Graphics;

import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;

public class Tree extends InteractableStaticEntity{

	public Tree(Handler handler, int id, float x, float y, int z) {
		super(handler, id, x, y, z, 100, 200);
		// TODO Auto-generated constructor stub
//		bounds.x = 0;
//		bounds.y = 0;
//		bounds.width = 0;
//		bounds.height = 0;
		bounds.x = 40;
		bounds.y = 150;
		bounds.width = 20;
		bounds.height = 50;
		
	}
	

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		
		//g.setColor(Color.red);
		//g.fillRect((int)  (x + bounds.x - handler.getGameCamera().getxOffset()), 
		//		(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}
	
	@Override
	public void renderBW(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(BWAssets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		
		//g.setColor(Color.red);
		//g.fillRect((int)  (x + bounds.x - handler.getGameCamera().getxOffset()), 
		//		(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}

}

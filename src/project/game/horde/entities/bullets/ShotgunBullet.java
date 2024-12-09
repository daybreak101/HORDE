package project.game.horde.entities.bullets;

import java.awt.Graphics;
import java.util.ArrayList;
import project.game.horde.main.Handler;
import project.game.horde.weapons.Gun;

public class ShotgunBullet extends Bullet{
	
	ArrayList<Bullet> pellets;
	Handler handler;

	public ShotgunBullet(Handler handler, float x, float y, int z, int range, double spread, int pelletNum, Gun gun) {
		super(handler, x, y, z, range, gun);
	
		pellets = new ArrayList<Bullet>(pelletNum);
		for(int i = 0; i < pelletNum; i++) {
			if(i == 0)
				pellets.add(new Bullet(handler, x, y, z, range, gun));
			else{
				float actualSpread = (float) spread;
				if(i % 2 == 0)
					actualSpread *= -1;
				int divisor = (i + 1) / 2;
				actualSpread = actualSpread / divisor;
				
				pellets.add(new Bullet(handler, x, y, z, range, actualSpread, gun));
			}
		}
		
//		pellets.add(new Bullet(handler, x, y, range, gun));
		
//		pellets.add(new Bullet(handler, x, y, range, (float) spread, gun)); 0
//		pellets.add(new Bullet(handler, x, y, range, (float) -spread, gun)); 1
//		pellets.add(new Bullet(handler, x, y, range, (float) spread/2, gun)); 2
//		pellets.add(new Bullet(handler, x, y, range, (float) -spread/2, gun)); 3
//		pellets.add(new Bullet(handler, x, y, range, (float) spread/4, gun)); 4
//		pellets.add(new Bullet(handler, x, y, range, (float) -spread/4, gun)); 5 
//		pellets.add(new Bullet(handler, x, y, range, (float) spread/8, gun)); 6 
//		pellets.add(new Bullet(handler, x, y, range, (float) -spread/8, gun)); 7
		
	}
	
	public void tick() {
		for(int i = 0; i < pellets.size(); i++) {
			pellets.get(i).tick();
			if(pellets.get(i).checkForImpact() == true) {
				pellets.remove(i);
			}
		}
		die(player);
		
	}

	@Override
	public void render(Graphics g) {
		for(int i = 0; i < pellets.size(); i++) {
			pellets.get(i).render(g);
			
		}
		
	}
	
	public void renderBW(Graphics g) {
		for(int i = 0; i < pellets.size(); i++) {
			pellets.get(i).renderBW(g);
			
		}
	}

}

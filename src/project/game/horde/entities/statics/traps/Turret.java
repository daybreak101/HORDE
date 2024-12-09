package project.game.horde.entities.statics.traps;

import java.awt.Color;
import java.awt.Graphics;

import project.game.horde.main.Handler;
import project.game.horde.weapons.Gun;
import project.game.horde.weapons.Minigun;

public class Turret extends Trap{
	Minigun gun = new Minigun(handler, true, null);
	
	public Turret(Handler handler, int id, float x, float y, int z, float switchX, float switchY,
			int switchZ, int switchRotation) {
		super(handler, id, x, y, z, 50, 50, switchX, switchY, switchZ, switchRotation, 40 * 60, 1500);
		cooldown = 30 * 60;
	}
	
	public void postTick() {
		if(cooldownTimer > cooldown) {
			activated = false;
		}
		else if(activated && cooldownTimer <= cooldown) {
			gun.tick();
			killInArea();
		}
	}
	
	public void render(Graphics g) {
			g.setColor(Color.gray);
			g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
				width, height);

	}
	
	public void renderBW(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
			width, height);

}

	public void killInArea() {
		gun.shootAsTurret(x, y);
	}

}

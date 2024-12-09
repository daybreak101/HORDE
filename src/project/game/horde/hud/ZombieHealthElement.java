package project.game.horde.hud;

import java.awt.Color;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.main.Handler;

public class ZombieHealthElement extends HudElement {

	private int health, maxHealth;
	private Zombie zombie;
	
	public ZombieHealthElement( Handler handler, float x, float y, Zombie zombie) {
		super(x, y, 60, 5, handler);
		this.zombie = zombie;
		this.health = zombie.getHealth();
		this.maxHealth = zombie.getMaxHealth();
	}

	@Override
	public void tick() {
		this.health = zombie.getHealth();
		this.maxHealth = zombie.getMaxHealth();
		x = zombie.getX();
		y = zombie.getY();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(128, 0, 0));
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height);
		g.setColor(handler.getSettings().getHudColor());
		g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), (int) ((double)health/maxHealth * width), height);

	}

}

package project.game.horde.entities.statics.traps;

import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public abstract class Trap extends Entity {
	protected boolean activated = false;
	private int cost;
	protected int cooldown, cooldownTimer;
	protected Player activatedBy;

	public Trap(Handler handler, int id, float x, float y, int width, int height, float switchX, float switchY,
			int switchZ, int switchRotation, int cooldown, int cost) {
		super(handler, x, y, width, height);
		this.cost = cost;
		activatedBy = null;
		handler.getWorld().getEntityManager()
				.addInteractable(new TrapSwitch(handler, id, switchX, switchY, switchRotation, this, cooldown));
	}

	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle(0, 0, 0, 0);
	}

	public void fulfillInteraction(Player player) {
		activated = true;
		activatedBy = player;
		cooldownTimer = 0;
		handler.getGlobalStats().addTrapPull();
	}

	public void fulfillOtherPlayerInteraction() {
		if (!activated) {
			activated = true;
			activatedBy = null;
			cooldownTimer = 0;
		}
	}

	public void tick() {
		cooldownTimer++;
		postTick();
	}

	public abstract void render(Graphics g);

	public abstract void renderBW(Graphics g);

	public void postTick() {
		// TODO Auto-generated method stub

	}

	public boolean getActivation() {
		return activated;
	}

	public Player activatedBy() {
		return activatedBy;
	}

	public int getCost() {
		return cost;
	}

}

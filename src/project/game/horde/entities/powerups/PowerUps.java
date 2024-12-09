package project.game.horde.entities.powerups;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.statics.StaticEntity;
import project.game.horde.main.Handler;

public abstract class PowerUps extends StaticEntity {

	protected Handler handler;
	protected int cooldown, cooldownTimer;
	protected boolean pickedUp = false;
	protected int activeCounter;
	protected String name;
	protected BufferedImage icon;
	protected BufferedImage floatingAsset;
	protected Rectangle trigger;
	protected String playerPicked;
	protected int id;
	protected boolean shared;

	public PowerUps(Handler handler, int id, float x, float y, int z, boolean shared) {
		super(handler, x, y, z, 60, 60);
		this.id = id;
		this.handler = handler;
		trigger = new Rectangle(0, 0, 0, 0);
		bounds = new Rectangle(0, 0, 0, 0);
		cooldown = 1800;
		activeCounter = 0;
		this.shared = shared;
		playerPicked = "";
		System.out.println("Powerup " + name + " spawned with ID: " + id);
	}
	
	public void renderBW(Graphics g) {
		render(g);
	}

	public void tick() {
		cooldownTimer++;
		trigger = new Rectangle((int) (x), (int) y, width, height);

		if (cooldownTimer >= cooldown || activeCounter >= cooldown) {
			unbuff();
			handler.getWorld().getEntityManager().getPowerups().remove(this);
			//handler.getWorld().getEntityManager().getEntities().remove(this);
		}

		else if (pickedUp) {
			cooldownTimer = 0;
			activeCounter++;
			if(shared || playerPicked.equals(handler.getCurrentPlayer().getUsername()))
				fulfillInteraction(playerPicked);
	
		} else if (!pickedUp && cooldownTimer < cooldown) {
			checkPickedUp();
		}

	}

	public void checkPickedUp() {
		Player player = handler.getCurrentPlayer();
		if (trigger.intersects(player.getCollisionBounds(0f, 0f)) && player.getHealth() > 0) {
			playerPicked = player.getUsername();
			for (PowerUps e : handler.getWorld().getEntityManager().getPowerups()) {
				if (e.getName() == this.name && e.isPickedUp() && e != this) {
					handler.getWorld().getEntityManager().getPowerups().remove(e);
					break;
				}
			}
			if (handler.getCurrentPlayer().getPeer() != null) {
				handler.getCurrentPlayer().getPeer().pickedUpPowerup(playerPicked, id);
			}
			pickedUp = true;
		}

	}
	

	public String getName() {
		return name;
	}
	
	public int getID() {
		return id;
	}

	public void unbuff() {
	}

	public boolean isPickedUp() {
		return pickedUp;
	}
	
	public void setPickedUp(boolean pickedUp, String username) {
		for (PowerUps e : handler.getWorld().getEntityManager().getPowerups()) {
			if (e.getName() == this.name && e.isPickedUp() && e != this) {
				handler.getWorld().getEntityManager().getPowerups().remove(e);
				break;
			}
		}
		this.pickedUp = pickedUp;
		playerPicked = username;
		System.out.println("User " + playerPicked + " picked up " + name + 
				" that has ID: " + id +", which states that the fact "
						+ "that it was picked up to be " + pickedUp);
	}

	public BufferedImage getIcon() {
		return icon;
	}

	public void fulfillInteraction(String username) {
	}

	public void postTick() {
	}
	
	
	public String getPlayerPicked() {
		return playerPicked;
	}
	
	public int getActiveCounter() {
		return activeCounter;
	}
}

package project.game.horde.entities.statics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;

public class Door extends Wall{
	private boolean cantAfford;
	private int price;
	private int room1, room2;
	
	public Door(Handler handler, int id, float x, float y, int z, int length, int whatWall, int room1, int room2) {
		super(handler, id, x, y, z, length, whatWall);
		price = 1000;
		this.room1 = room1;
		this.room2 = room2;
	}
	
	@Override
	public void render(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(100, 50, 40));
		g2d.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);
	}
	

	@Override
	public void renderBW(Graphics g) {
	    Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(42,42,42));
		g2d.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset()), bounds.width, bounds.height);

	}
	
	@Override
	public void postTick() {
		if(usedByOtherPlayer) {
			usedByOtherPlayer = false;
			handler.getWorld().getRoomLogic().addOpenedRooms(room1);
			handler.getWorld().getRoomLogic().addOpenedRooms(room2);
			active = false;
		}
		else if (cantAfford && cooldownTimer < cooldown) {
			triggerText = "Not enough points!";
		} else if (health == 100 && cooldownTimer < cooldown) {
			triggerText = "Already repaired!";
		} else if (cooldownTimer >= cooldown) {
			triggerText = "Press F to open door: " + price;
		} else {
			triggerText = "";
		}
	}
	

	public void fulfillInteraction(Player player) {
		if(usedByOtherPlayer) {
			
		}
		else if (cooldownTimer >= cooldown) {
			cooldownTimer = 0;
			if (player.getInv().purchase(price)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				sendInteractableBusy();
				handler.getProgression().gainXP(100);
				cantAfford = false;
				handler.getWorld().getRoomLogic().addOpenedRooms(room1);
				handler.getWorld().getRoomLogic().addOpenedRooms(room2);
				active = false;
			} else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}

		}

	}

}

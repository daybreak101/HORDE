package project.game.horde.entities.statics;

import java.awt.Graphics;
import java.util.Random;

import project.game.horde.entities.creatures.Player;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.weapons.*;
import project.game.horde.weapons.Gun;

public class MysteryBox extends InteractableStaticEntity {
	
	private boolean isOpened;
	private Gun gun;
	private int isOpenedTimer, isOpenedTime;
	private boolean cantAfford = false;
	private boolean isSpecialGrenade = false;

	public MysteryBox(Handler handler, int id, float x, float y, int z) {
		super(handler, id, x, y, z, 150, 75);
		triggerText = "Press F to spin for a random weapon";
		isOpened = false;
		isOpenedTime = 1000;
	}

	//spin for random weapon
	@Override
	public void fulfillInteraction(Player player) {
		//spin for weapon
		if(usedByOtherPlayer) {
			
		}
		else if(isOpened == false && cooldownTimer >= cooldown) {
			if(player.getInv().purchase(950)) {
				Sounds.playClip(InteractSounds.PURCHASE_ID, 1, 1, false);
				sendInteractableBusy();
				isOpened = true;
				cantAfford = false;
				cooldownTimer = 0;
				gun = getRandomWeapon(player);
				handler.getGlobalStats().addBoxSpin();
				
				//don't give a weapon player already has
				while(player.getInv().checkArsenal(gun)) {
					gun = getRandomWeapon(player);
				}
			}
			else {
				Sounds.playClip(InteractSounds.CANTAFFORD_ID, 1, 1, false);
				cantAfford = true;
				cooldownTimer = 0;
			}
			
		}
		//grab weapon
		else if(isOpened == true && cooldownTimer >= cooldown && isOpenedTimer < isOpenedTime) {
			cooldownTimer = 0;
			isOpened = false;
			isOpenedTimer = 0;
			if(gun.getOriginalName() == "Gas Grenades") {
				player.getInv().setSpecialGrenade(0);
			}
			else
				player.getInv().setGun(gun);
			handler.getGlobalStats().addBoxPull();
			sendInteractableReady();
		}
		
	}
	
	public Gun getRandomWeapon(Player player) {
		Random rand = new Random();
		int rng = rand.nextInt(12);
		
		switch(rng) {
		case 1:
			return new AK47(handler, player);
		case 2:
			return new P90(handler, player);
		case 3:
			return new M4(handler, player);
		case 4:
			return new RPD(handler, player);
		case 5:
			return new RPG(handler, player);
		case 6:
			return new Winchester1901(handler, player);
		case 7:
			return new AWP(handler, player);
		case 8:
			return new AA12(handler, player);
		case 9:
			return new Flamethrower(handler, player);
		case 10: 
			return new GrenadeLauncher(handler, player);
		case 11:
			return new GasGrenades(handler, player);
		default:
			return new Glock17(handler, player);
		}
	}
	
	@Override
	public void postTick() {
		if(usedByOtherPlayer) {
			isOpenedTimer = 0;
			triggerText = "Busy";
		}
		else if(cantAfford == true && cooldownTimer < cooldown) {
			isOpenedTimer = 0;
			triggerText = "Not enough points!";
		}	
		else if(isOpened == true && cooldownTimer >= cooldown) {
			triggerText = "Press F to trade weapon for " + gun.getName();
			isOpenedTimer++;
			if(isOpenedTimer >= isOpenedTime) {
				isOpened = false;
				sendInteractableReady();
			}
		}
		else if(isOpened == false && cooldownTimer >= cooldown) {
			isOpenedTimer = 0;
			triggerText = "Press F to spin for a random weapon: 950";
			gun = null;
		}
		else if(isOpened == true) {
			triggerText = "Spinning...";
		}
		else {
			triggerText = "";
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.mysteryBox, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null );
		
	}
	
	@Override
	public void renderBW(Graphics g) {
		g.drawImage(BWAssets.mysteryBox, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null );
		
	}

}

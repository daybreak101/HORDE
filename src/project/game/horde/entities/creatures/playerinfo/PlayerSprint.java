package project.game.horde.entities.creatures.playerinfo;

import project.game.horde.entities.creatures.Player;

public class PlayerSprint {

	private Player player;
	private float sprintMultiplier;
	private int currentStamina,
				maxStamina,
				staminaTicker,
				staminaCooldown;
	
	
	public PlayerSprint(Player player) {
		this.player = player;
		sprintMultiplier = 1;
		currentStamina = 200;
		maxStamina = 200;
		staminaTicker = 0;
		staminaCooldown = 180;
	}
	
	public void sprinting() {
		if (sprintMultiplier == 1) {
			staminaTicker++;
			if (staminaTicker >= staminaCooldown && currentStamina < maxStamina) {
				currentStamina++;
				currentStamina++;
				if (player.getInv().getStaminup() >= 2)
					currentStamina++;
			}
		}
	}
	
	public boolean sprint() {
		if (currentStamina > 0) {
			currentStamina--;
			staminaTicker = 0;
			if (player.getInv().getStaminup() > -1)
				sprintMultiplier = 1.8f;
			else
				sprintMultiplier = 1.5f;
			return true;
		}
		return false;

	}
	
	public void setSprintMultiplier(float sprintMultiplier) {
		this.sprintMultiplier = sprintMultiplier;
	}
	
	public float getSprintMultiplier() {
		return sprintMultiplier;
	}
	
	public int getCurrentStamina() {
		return currentStamina;
	}

	public void setCurrentStamina(int currentStamina) {
		this.currentStamina = currentStamina;
	}

	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int maxStamina) {
		this.maxStamina = maxStamina;
	}
	
	public void setStaminaCooldown(int staminaCooldown) {
		this.staminaCooldown = staminaCooldown;
	}
}

package project.game.horde.entities.creatures.playerinfo;

import project.game.horde.entities.creatures.Player;
import project.game.horde.hud.HudManager;
import project.game.horde.input.GameMouseManager;
import project.game.horde.input.KeyManager;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.sounds.MiscWeaponSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.states.GameState;
import project.game.horde.states.PauseState;
import project.game.horde.states.State;
import project.game.horde.utils.Timer;

public class PlayerInput {
	private KeyManager keyManager;
	private GameMouseManager mouseManager;
	private Player player;
	private Inventory inv;
	private Handler handler;

	public PlayerInput(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		this.inv = player.getInv();
		keyManager = new KeyManager(handler);
		mouseManager = new GameMouseManager(handler, player);
		handler.getGame().setKeyManager(keyManager);
		handler.getGame().setMouseManager(mouseManager);
	}

	Timer cockNade = new Timer(30);
	boolean grenadeSoundPlayed = false;
	Timer recoverTimer;
	int fuseTime = 0;

	public void tick() {
		keyManager.tick();
		PlayerActionState action = player.getActionState();

		if (action == PlayerActionState.RECOVER) {
			recoverTimer.tick();
			if (recoverTimer.isReady())
				player.setActionState(PlayerActionState.IDLE);
		} else if(action == PlayerActionState.REVIVING) {
			if(!keyManager.use) {
				player.cancelRevive();
				player.setActionState(PlayerActionState.RECOVER);
				recoverTimer = new Timer(10);
			} else if(player.getPlayerReviving() != null) {
				boolean finish = player.getPlayerReviving().progressRevive();
				if(finish) {
					player.setActionState(PlayerActionState.RECOVER);
					recoverTimer = new Timer(10);
					player.cancelRevive();
				}
			} else {
				player.cancelRevive();
				player.setActionState(PlayerActionState.RECOVER);
				recoverTimer = new Timer(10);
			}
		} else if (action == PlayerActionState.RELOADING && (!inv.getGun().getIsReloading() && !inv.getGun().getIsAltReloading())) {
			player.setActionState(PlayerActionState.IDLE);
		} else if (action == PlayerActionState.SHOOTING && (!mouseManager.isLeftPressed() || !mouseManager.isRightPressed())) {
			player.setActionState(PlayerActionState.IDLE);
		} else if (action == PlayerActionState.COOKING_GRENADE) {
			if (!grenadeSoundPlayed) {
				Sounds.playClip(MiscWeaponSounds.GRENADE_UNCLIP, 1, 1, false);
				grenadeSoundPlayed = true;
			}
			cockNade.tick();
			if(cockNade.checkIsReady()) {
				fuseTime++;
				if(fuseTime >= 300) {
					cockNade.isReady();
					inv.throwGrenade(fuseTime);
					fuseTime = 0;
					player.setActionState(PlayerActionState.RECOVER);
					recoverTimer = new Timer(30);
					grenadeSoundPlayed = false;
				}
			}
			if (cockNade.checkIsReady() && !keyManager.grenade) {
				cockNade.isReady();
				inv.throwGrenade(fuseTime);
				fuseTime = 0;
				player.setActionState(PlayerActionState.RECOVER);
				recoverTimer = new Timer(30);
				grenadeSoundPlayed = false;
			}
		} else if (action == PlayerActionState.COOKING_SPECIAL_GRENADE) {
			if (!grenadeSoundPlayed) {
				Sounds.playClip(MiscWeaponSounds.GRENADE_UNCLIP, 1, 1, false);
				grenadeSoundPlayed = true;
			}
			cockNade.tick();
			if (cockNade.checkIsReady() && !keyManager.q) {
				cockNade.isReady();
				inv.throwSpecialGrenade();
				player.setActionState(PlayerActionState.RECOVER);
				recoverTimer = new Timer(30);
				grenadeSoundPlayed = false;
			}
		} else if (action == PlayerActionState.SWITCHING_WEAPON) {
			player.setActionState(PlayerActionState.RECOVER);
			int time = Math.round(30 * (1.0f + inv.getGun().getWeight()));
			//System.out.println(inv.getGun().getName() + ": " + time);
			recoverTimer = new Timer(time);
		} else if (action == PlayerActionState.MELEEING) {
			player.setActionState(PlayerActionState.RECOVER);
			recoverTimer = new Timer(50);
		} else if (action == PlayerActionState.PRAYING) {
			player.setActionState(PlayerActionState.RECOVER);
			recoverTimer = new Timer(100);
		} else if (action == PlayerActionState.EATING) {
			player.setActionState(PlayerActionState.RECOVER);
			recoverTimer = new Timer(150);
		} else if (action == PlayerActionState.INTERACT) {
			player.setActionState(PlayerActionState.RECOVER);
			recoverTimer = new Timer(30);
		}

	}

	public boolean canRevive() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE || action == PlayerActionState.REVIVING
						|| action == PlayerActionState.INTERACT);
	}
	
	public boolean canEat() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE || action == PlayerActionState.EATING
						|| action == PlayerActionState.INTERACT);
	}

	public boolean canPray() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE || action == PlayerActionState.PRAYING);
	}

	public boolean canMelee() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE || action == PlayerActionState.MELEEING);
	}

	public boolean canSprint() {
		PlayerMovementState move = player.getMoveState();
		return (move == PlayerMovementState.SPRINTING || move == PlayerMovementState.WALKING);
	}

	public boolean canThrowGrenade() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE || action == PlayerActionState.COOKING_GRENADE);
	}

	public boolean canThrowSpecialGrenade() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE || action == PlayerActionState.COOKING_SPECIAL_GRENADE);
	}

	public boolean canSwitchWeapon() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE || action == PlayerActionState.RELOADING
						|| action == PlayerActionState.SHOOTING || action == PlayerActionState.SWITCHING_WEAPON
						|| action == PlayerActionState.RECOVER);
	}

	public boolean canShoot() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& 
				(action == PlayerActionState.IDLE 
				|| action == PlayerActionState.SHOOTING
				|| action == PlayerActionState.RELOADING);
	}

	public boolean canReload() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& 
				(action == PlayerActionState.IDLE 
				|| action == PlayerActionState.SHOOTING
				|| action == PlayerActionState.RELOADING);
	}

	public boolean canInteract() {
		PlayerMovementState move = player.getMoveState();
		PlayerActionState action = player.getActionState();
		return (move == PlayerMovementState.IDLE || move == PlayerMovementState.WALKING)
				&& (action == PlayerActionState.IDLE);
	}

	public void getInput() {
		if(!(State.getState() instanceof GameState))
			return;
		movementInput();
		switchWeaponInput();
		reloadInput();
		shootInput();
		throwGrenadeInput();
		throwSpecialGrenadeInput();
		meleeInput();
		prayInput();
		otherInputs();
		System.out.println(player.getActionState());
		interactInput();
	}

	private void prayInput() {
		if (keyManager.x) {
			inv.getBlessings().activateBlessing();
			player.setActionState(PlayerActionState.PRAYING);
		}
	}

	private void meleeInput() {
		if (keyManager.melee) {
			inv.getKnife().damageNearbyZombie();
			player.setActionState(PlayerActionState.MELEEING);
		}
	}

	private void otherInputs() {
		HudManager hud = player.getHud();
		User user = player.getUser();
		if (keyManager.escape) {
			hud.setInvisible();
			Sounds.pauseClips();
			//Sounds.pauseAllClips();
			State.setState(new PauseState(handler, user));
		}
		if (keyManager.capslock) {
			hud.getGameplayHUD().setVisible(false);
			hud.getScoreboard().setVisible(true);
		} else {
			hud.getScoreboard().setVisible(false);
			hud.getGameplayHUD().setVisible(true);
		}
	}

	private void interactInput() {
		if (keyManager.use && canInteract() && player.getActionState() != PlayerActionState.EATING) {

			player.setActionState(PlayerActionState.INTERACT);
			player.interact();
		}
	}

	private void shootInput() {
		if (mouseManager.isLeftPressed() && inv.getGun() != null && canShoot()) {
			inv.getGun().shoot();
			player.setActionState(PlayerActionState.SHOOTING);
		}
		if (mouseManager.isRightPressed() && inv.getGun() != null && canShoot()) {
			inv.getGun().altShoot();
			player.setActionState(PlayerActionState.SHOOTING);
		}
	}

	private void reloadInput() {
		if (keyManager.reload && player.getInv().getGun() != null && canReload()) {
			player.getInv().getGun().reload();
			if(player.getInv().getGun().isDual())
				player.getInv().getGun().altReload();
			player.setActionState(PlayerActionState.RELOADING);
		}
	}

	private void switchWeaponInput() {
		if (canSwitchWeapon() && (keyManager.switchWeapon2 || mouseManager.isMouseScrolled())) {
			inv.switchWeapon();
			mouseManager.setMouseScrolled(false);
			player.setActionState(PlayerActionState.SWITCHING_WEAPON);
		}
	}

	private void throwGrenadeInput() {
		if (keyManager.grenade && canThrowGrenade() && inv.getGrenades() > 0)
			player.setActionState(PlayerActionState.COOKING_GRENADE);
	}

	private void throwSpecialGrenadeInput() {
		if (keyManager.q && canThrowSpecialGrenade() && inv.getSpecialGrenadeType() != -1 && inv.getSpecialGrenadeAmt() > 0)
			player.setActionState(PlayerActionState.COOKING_SPECIAL_GRENADE);
	}

	private void movementInput() {

		FreezeStatusForPlayer freezeStatus = player.getFreezeStatus();

		player.setxMove(0);
		player.setyMove(0);
		player.moved = false;

		float slowdown = 1;
		float speed = player.getSpeed();

		if (freezeStatus.inWater()) {
			slowdown += 1;
		}
		if (player.getHealth() <= 0) {
			slowdown += 2;
		}

		// Determine movement direction
		float xMove = 0;
		float yMove = 0;

		if (keyManager.w) {
			yMove -= speed;
		}
		if (keyManager.s) {
			yMove += speed;
		}
		if (keyManager.a) {
			xMove -= speed;
		}
		if (keyManager.d) {
			xMove += speed;
		}

		// Normalize diagonal movement to prevent faster movement
		if (xMove != 0 && yMove != 0) {
			xMove *= Math.sqrt(0.5);
			yMove *= Math.sqrt(0.5);
		}

		// Check for sprinting
		if (keyManager.sprint && (xMove != 0 || yMove != 0) && canSprint() && player.getPlayerSprint().sprint()) {
			float sprintMultiplier = player.getPlayerSprint().getSprintMultiplier();
			xMove *= sprintMultiplier;
			yMove *= sprintMultiplier;
			inv.cancelReload();
			player.cancelRevive();
			player.setActionState(PlayerActionState.RECOVER);
			recoverTimer = new Timer(20);
			player.setMoveState(PlayerMovementState.SPRINTING);
		} else if (xMove != 0 || yMove != 0) {
			player.setMoveState(PlayerMovementState.WALKING);
		} else {
			player.setMoveState(PlayerMovementState.IDLE);
		}

		// Apply movement and state
		player.setxMove(xMove / slowdown);
		player.setyMove(yMove / slowdown);
		player.moved = (xMove != 0 || yMove != 0);
	}

	public void getDownedInput() {

		FreezeStatusForPlayer freezeStatus = player.getFreezeStatus();

		player.setxMove(0);
		player.setyMove(0);
		player.moved = false;

		float slowdown = 3;
		float speed = player.getSpeed();

		if (freezeStatus.inWater()) {
			slowdown += 1;
		}
		if (player.getHealth() <= 0) {
			slowdown += 2;
		}

		// Determine movement direction
		float xMove = 0;
		float yMove = 0;

		if (keyManager.w) {
			yMove -= speed;
		}
		if (keyManager.s) {
			yMove += speed;
		}
		if (keyManager.a) {
			xMove -= speed;
		}
		if (keyManager.d) {
			xMove += speed;
		}

		// Normalize diagonal movement to prevent faster movement
		if (xMove != 0 && yMove != 0) {
			xMove *= Math.sqrt(0.5);
			yMove *= Math.sqrt(0.5);
		}

		if (xMove != 0 || yMove != 0) {
			player.setMoveState(PlayerMovementState.WALKING);
		} else {
			player.setMoveState(PlayerMovementState.IDLE);
		}

		// Apply movement and state
		player.setxMove(xMove / slowdown);
		player.setyMove(yMove / slowdown);
		player.moved = (xMove != 0 || yMove != 0);

		otherInputs();
	}

//		if (freezeStatus.isFrozen()) {
//			if (keyManager.melee) {
//				freezeStatus.breakFreeFromIce();
//			}
//		}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public void setKeyManager(KeyManager keyManager) {
		this.keyManager = keyManager;
	}

	public GameMouseManager getMouseManager() {
		return mouseManager;
	}

	public void setMouseManager(GameMouseManager mouseManager) {
		this.mouseManager = mouseManager;
	}

}

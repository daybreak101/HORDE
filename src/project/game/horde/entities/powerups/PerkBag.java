package project.game.horde.entities.powerups;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.perks.DeadShot;
import project.game.horde.perks.DoubleTap;
import project.game.horde.perks.Juggernaut;
import project.game.horde.perks.Luna;
import project.game.horde.perks.MuleKick;
import project.game.horde.perks.Perk;
import project.game.horde.perks.PhD;
import project.game.horde.perks.Revive;
import project.game.horde.perks.SleightOfHand;
import project.game.horde.perks.StaminUp;
import project.game.horde.perks.Stronghold;
import project.game.horde.perks.Vampire;

public class PerkBag extends PowerUps {

	public PerkBag(Handler handler, int id, float x, float y) {
		super(handler, id, x, y, true);
		name = "Perk Bag";
		icon = null;
		floatingAsset = null;
		glow = null;
	}

        @Override
	public void tick() {
		cooldownTimer++;
		trigger = new Rectangle((int) (x), (int) (y), width, height);

		if (cooldownTimer >= cooldown) {
			handler.getWorld().getEntityManager().getPowerups().remove(this);
			// handler.getWorld().getEntityManager().getEntities().remove(this);
		} else if (pickedUp) {
			fulfillInteraction(playerPicked);
			handler.getWorld().getEntityManager().getPowerups().remove(this);
			// handler.getWorld().getEntityManager().getEntities().remove(this);
		} else if (cooldownTimer >= cooldown) {
			handler.getWorld().getEntityManager().getPowerups().remove(this);
			// handler.getWorld().getEntityManager().getEntities().remove(this);
		} else if (!pickedUp) {
			checkPickedUp();
		}
	}

	@Override
	public void fulfillInteraction(String username) {
		Perk perk;
		Player p = handler.getCurrentPlayer();
		perk = getRandomPerk(p);
		while (p.getInv().checkPerks(perk)) {
			perk = getRandomPerk(p);
		}
		p.getInv().givePerk(perk);

	}

	public Perk getRandomPerk(Player player) {
		Random rand = new Random();
		int rng = rand.nextInt(11);

		switch (rng) {
		case 0:
			return new Juggernaut(handler, handler.getUnlocks().getJuggLvl(), player);
		case 1:
			return new SleightOfHand(handler, handler.getUnlocks().getSpeedLvl(), player);
		case 2:
			return new DoubleTap(handler, handler.getUnlocks().getDoubletapLvl(), player);
		case 3:
			return new DeadShot(handler, handler.getUnlocks().getDeadshotLvl(), player);
		case 4:
			return new PhD(handler, handler.getUnlocks().getPhdLvl(), player);
		case 5:
			return new StaminUp(handler, handler.getUnlocks().getStaminaLvl(), player);
		case 6:
			return new Vampire(handler, handler.getUnlocks().getVampireLvl(), player);
		case 7:
			return new MuleKick(handler, handler.getUnlocks().getMuleLvl(), player);
		case 8:
			return new Revive(handler, handler.getUnlocks().getReviveLvl(), player);
		case 9:
			return new Luna(handler, handler.getUnlocks().getLunaLvl(), player);
		case 10:
			return new Stronghold(handler, handler.getUnlocks().getStrongholdLvl(), player);
		}
		return new MuleKick(handler, handler.getUnlocks().getMuleLvl(), player);
	}

	@Override
	public void render(Graphics g) {
		if (!pickedUp) {
			g.setColor(Color.CYAN);
			g.drawOval((int) (x - handler.getGameCamera().getxOffset()),
					(int) (y - handler.getGameCamera().getyOffset()), width, height);
		}
	}
}

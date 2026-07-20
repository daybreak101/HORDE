package project.game.horde.main;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.Wall;
import project.game.horde.weapons.DoubleBarrel;
import project.game.horde.weapons.Gun;

public class Cheats {

	Handler handler;

	public Cheats(Handler handler) {
		this.handler = handler;
		//applyCheats();
	}

	public void applyCheats() {
		// perks
		//handler.getCurrentPlayer().getInv().givePerk(new DeadShot(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new DoubleTap(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new Juggernaut(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new Luna(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new MuleKick(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new PhD(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new Revive(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new SleightOfHand(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new StaminUp(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new Stronghold(handler, 3, handler.getCurrentPlayer()));
		//handler.getCurrentPlayer().getInv().givePerk(new Vampire(handler, 3, handler.getCurrentPlayer()));

		// points
		int points = 10000;
		handler.getCurrentPlayer().getInv().gainPoints(points);

		// set round
		int round = 10;
		handler.getRoundLogic().setCurrentRound(round);

		// give gun
		Gun gun =
				//new Arisaka(handler, handler.getCurrentPlayer());
				//new AWP(handler, handler.getCurrentPlayer());
				//new AK47(handler, handler.getCurrentPlayer());
				//new RPD(handler, handler.getCurrentPlayer());
				//new GrenadeLauncher(handler, handler.getCurrentPlayer());
				//new Winchester1901(handler, handler.getCurrentPlayer());
				//new AA12(handler, handler.getCurrentPlayer());
				//new RPG(handler, handler.getCurrentPlayer());
				//new Flamethrower(handler, handler.getCurrentPlayer());
				//new IceShotgun(handler, handler.getCurrentPlayer());
				//new P90(handler, handler.getCurrentPlayer());
				//new Minigun(handler, handler.getCurrentPlayer());
				//new M4(handler, handler.getCurrentPlayer());
				//new IceShotgun(handler, handler.getCurrentPlayer());
				//new Bren(handler, handler.getCurrentPlayer());
				new DoubleBarrel(handler, handler.getCurrentPlayer());
				//new G18(handler, handler.getCurrentPlayer());
				//new M1911(handler, handler.getCurrentPlayer());
				//new M60(handler, handler.getCurrentPlayer());
				//new Python(handler, handler.getCurrentPlayer());
				//new Thompson(handler, handler.getCurrentPlayer());
				//new Type100(handler, handler.getCurrentPlayer());
				//new Uzi(handler, handler.getCurrentPlayer());
				//new M16(handler, handler.getCurrentPlayer());
				gun.upgradeWeapon();
		handler.getCurrentPlayer().getInv().setGun(gun);
		
		//give gas grenades
		//handler.getCurrentPlayer().getInv().setSpecialGrenade(0);
		
		//give blessing
		//handler.getCurrentPlayer().getInv().getBlessings().setBlessing(BlessingInventory.TELEPORT);
			
		invisibleWalls();
		//nodesVisible();
		//showPlayerCoords();
	}
	
	public void tick() {
		//freezeZombies();	
		//infiniteAmmo();
	}
	
	public void infiniteAmmo() {
		handler.getCurrentPlayer().getInv().infiniteAmmo();
	}
	
	public void freezeZombies() {
		for(Zombie z : handler.getWorld().getEntityManager().getZombies()) {
			z.getFreezeStatus().freezeByBlessing();
		}
	}
	
	public void invisibleWalls() {
		for(Wall w : handler.getWorld().getEntityManager().getWalls()) {
			w.setVisible(false);
		}
	}
	
	public void nodesVisible() {
		handler.getWorld().showNodesAndEdges();
	}
	
	public void showPlayerCoords() {
		handler.getCurrentPlayer().drawCoords();
	}
}

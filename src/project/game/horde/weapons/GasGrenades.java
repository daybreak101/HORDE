package project.game.horde.weapons;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public class GasGrenades extends Gun{

	//created so it can be in the box...
	public GasGrenades(Handler handler, Player player) {
		super(handler, player, 0, 0, 0, 0, 0, 0, 0);
		name = "Gas Grenades";
		originalName = name;
	}
	

}

package project.game.horde.weapons;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public class GasGrenades extends Gun {

    //created so it can be in the box...
    public GasGrenades(Handler handler, Player player) {
        super(handler, player, 0, 0, 0, 0, 0, 0, 0, 100);
        name = "Gas Grenades";
        originalName = name;
    }

    @Override
    public void shootOnline(int x, int y, float angle, float volume) {
        // handler.getWorld().getEntityManager()
        //         .addEntity(new OnlineShotgunBullet(handler, x, y, range,
        //                 angle, GunVars.WINCHESTER1901_PELLET_SPREAD, GunVars.WINCHESTER1901_PELLET_COUNT, isUpgraded));

        // if (isUpgraded) {
        //     Sounds.playClip(GunSounds.UPGRADED_ID, 1, volume, false);
        // }
        // Sounds.playClip(GunSounds.WINCHESTER1901_SHOT_ID, 1, volume, false);
    }

}

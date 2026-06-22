package project.game.horde.entities.bullets;

import java.awt.Graphics;
import java.util.ArrayList;

import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.Gun;

public class IceBullet extends Bullet {

    ArrayList<Bullet> pellets;
    public IceBullet(Handler handler, float x, float y, int range, Gun gun) {
        super(handler, x, y, range, gun);

        pellets = new ArrayList<Bullet>(9);
        pellets.add(new IcePellet(handler, x, y, range, 0, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) -Math.PI / 4, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) Math.PI / 4, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) -Math.PI / 8, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) Math.PI / 8, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) Math.PI / 16, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) -Math.PI / 16, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) Math.PI / 32, gun));
        pellets.add(new IcePellet(handler, x, y, range, (float) -Math.PI / 32, gun));

    }

    Timer heldShot = new Timer(180);

    @Override
    public void tick() {

        for (int i = 0; i < pellets.size(); i++) {
            pellets.get(i).tick();
            if (pellets.get(i).checkForImpact() == true) {
                pellets.remove(i);
            }
        }
        die(player);

    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < pellets.size(); i++) {
            pellets.get(i).render(g);

        }

    }

    @Override
    public void renderBW(Graphics g) {
        for (int i = 0; i < pellets.size(); i++) {
            pellets.get(i).renderBW(g);

        }

    }
}

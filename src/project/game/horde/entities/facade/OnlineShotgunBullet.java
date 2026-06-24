package project.game.horde.entities.facade;

import java.awt.Graphics;
import java.util.ArrayList;

import project.game.horde.main.Handler;

public class OnlineShotgunBullet extends OnlineBullet {

    ArrayList<OnlineBullet> pellets;

    public OnlineShotgunBullet(Handler handler, float x, float y, int range, float angle, double spread, int pelletNum, boolean isUpgraded) {
        super(handler, x, y, range, (float) angle, isUpgraded);

        pellets = new ArrayList<>(pelletNum);
        for (int i = 0; i < pelletNum; i++) {
            if (i == 0) {
                pellets.add(new OnlineBullet(handler, x, y, range, (float) angle, isUpgraded)); 
            }else {
                float actualSpread = (float) spread;
                if (i % 2 == 0) {
                    actualSpread *= -1;
                }
                int divisor = (i + 1) / 2;
                actualSpread = actualSpread / divisor;

                pellets.add(new OnlineBullet(handler, x, y, range, (float) angle, actualSpread, isUpgraded));
            }
        }

    }

    @Override
    public void tick() {
        for (int i = 0; i < pellets.size(); i++) {
            pellets.get(i).tick();
            if (pellets.get(i).checkForImpact() == true) {
                pellets.remove(i);
            }
        }
        die(null);

    }

    @Override
    public void render(Graphics g) {
        for (int i = 0; i < pellets.size(); i++) {
            pellets.get(i).render(g);

        }

    }

}

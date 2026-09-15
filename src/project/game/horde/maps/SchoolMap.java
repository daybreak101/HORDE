package project.game.horde.maps;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import project.game.horde.entities.Entity;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;

public class SchoolMap extends Entity {

    private BufferedImage map;

    public SchoolMap(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, 0, 0);
    }

    @Override
    public void render(Graphics g) {
        map = Assets.schoolMap;
        g.drawImage(map, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), map.getWidth(), map.getHeight(), null);

    }
}

package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class CritElement extends HudElement {

    private int alpha, fontSize;
    private Timer fontTick;
    private int r, gr, b;

    public CritElement(Handler handler, float x, float y) {
        super(x, y, 25, 25, handler);
        alpha = 255;
        fontSize = 20;
        fontTick = new Timer(5);

    }

    @Override
    public void tick() {
        Color setColor = handler.getSettings().getHudColor();
        r = setColor.getRed();
        gr = setColor.getGreen();
        b = setColor.getBlue();
        y -= 1;
        alpha -= 4;

        fontTick.tick();
        if (fontTick.isReady()) {
            fontTick.resetTimer();
            fontSize--;
        }

        if (alpha <= 0 || fontSize <= 0) {
            isActive = false;
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(r, gr, b, alpha));
        g.setFont(new Font(Font.DIALOG, Font.BOLD, fontSize));
        g.drawString("CRIT", (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()));

    }

}

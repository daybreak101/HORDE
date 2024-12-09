package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;

public class LevelUpNotification extends HudElement {
	private int alpha, type, fontSize;
	private Timer popupTime;
	private Font font;
	private int level;

	public LevelUpNotification(Handler handler, int level) {
		super(handler.getWidth() / 2, handler.getHeight() / 4, 0, 0, handler);
		fontSize = 30;
		font = new Font(Font.DIALOG, Font.PLAIN, fontSize);
		popupTime = new Timer(100);
		alpha = 1;
		this.level = level;
	}

	boolean goDown = false;

	@Override
	public void tick() {
		System.out.println("LEVEL UP NOTIFICATION PRESENTED WITH ALPHA: " + alpha);
		if (alpha < 255) {
			if (!goDown)
				alpha+= 2;
			else
				alpha-= 2;
		} else if (alpha == 255) {
			popupTime.tick();
			if (popupTime.isReady()) {
				goDown = true;
				alpha--;
			}
		}

		if (alpha <= 1) {
			isActive = false;
		}
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		Color color = handler.getSettings().getHudColor();
		int r = color.getRed();
		int gr = color.getGreen();
		int b = color.getBlue();
		g.setColor(new Color(r, gr, b, alpha));
		g.setFont(font);
		Utils.drawCenteredString(g, "Level Up: " + level,
				new Rectangle((int) x, (int) y, 0, 0), font);
	}
}

package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;

public class GoldenCoinNotification extends HudElement {
	private int alpha, type, fontSize;
	private Timer popupTime;
	private Font font;
	private String reason;
	private int amount;

	public GoldenCoinNotification(Handler handler, int amount, String reason) {
		super(handler.getWidth() / 2, handler.getHeight() / 4, 0, 0, handler);
		fontSize = 30;
		font = new Font(Font.DIALOG, Font.PLAIN, fontSize);
		popupTime = new Timer(100);
		alpha = 1;
		this.reason = reason;
		this.amount = amount;
	}

	boolean goDown = false;

	@Override
	public void tick() {
		System.out.println("GOLDEN COIN NOTIFICATION PRESENTED WITH ALPHA: " + alpha);
		if (alpha < 255) {
			if (!goDown) 
				alpha += 2;
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
		Color color = handler.getSettings().getHudColor();
		int r = color.getRed();
		int gr = color.getGreen();
		int b = color.getBlue();
		g.setColor(new Color(r, gr, b, alpha));
		g.setFont(font);
		Utils.drawCenteredString(g, reason, new Rectangle((int) x, (int) y - 50, 0, 0), font);
		Utils.drawCenteredString(g, "+ " + amount,
					new Rectangle((int) x - 40, (int) y - 4, 0, 0), font);
		//AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		//Graphics2D g2d = (Graphics2D) g;
		//g2d.setComposite(ac);
		//g2d.drawImage(MenuAssets.coins[0], (int) x, (int) y, 50, 50, null);
		g.drawImage(MenuAssets.coins[0], (int) x, (int) y - 25, 50, 50, null);

	}
}

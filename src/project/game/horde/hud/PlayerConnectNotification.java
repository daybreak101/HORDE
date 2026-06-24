package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class PlayerConnectNotification extends HudElement {
	private String username;
	private int alpha, fontSize;
	private boolean joined, startFading;

	public PlayerConnectNotification(Handler handler, String username, boolean joined) {
		super(40, (int) handler.getHeight() / 2, 0, 0, handler);
		alpha = 255;
		fontSize = 20;
		this.username = username;
		this.joined = joined;
		startFading = false;
	}

	Timer fontTick = new Timer(120);

	@Override
	public void tick() {
		fontTick.tick();
		if (fontTick.isReady()) {
			startFading = true;
		}
		if(startFading) {
			alpha -=2;
		}

		if (alpha <= 0)
			isActive = false;

	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(255, 0, 0, alpha));
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
		if (joined) {
			g.drawString(username + " has joined the game.", (int) x, (int) y);
		} else {
			g.drawString(username + " has left the game.", (int) x, (int) y);
		}

		

	}
}

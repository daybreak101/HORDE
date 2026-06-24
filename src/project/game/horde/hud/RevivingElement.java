package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class RevivingElement extends HudElement {

	public RevivingElement(Handler handler) {
		super(handler.getGame().getWidth() / 2, handler.getGame().getHeight() / 2, 0, 0, handler);
		// TODO Auto-generated constructor stub
	}

	private int reviveProgress = 0;
	private int reviveMax = 300;

	@Override
	public void tick() {
		if (handler.getCurrentPlayer().getHealth() <= 0) {
			reviveProgress = handler.getCurrentPlayer().getReviveProgress();
			reviveMax = handler.getCurrentPlayer().getReviveMax();
		} else {
			reviveProgress = handler.getCurrentPlayer().getPlayerReviving().getReviveProgress();
			reviveMax = handler.getCurrentPlayer().getPlayerReviving().getReviveMax();
		}

	}

	@Override
	public void render(Graphics g) {
//		double zoomLevel = handler.getSettings().getZoomLevel();
//		Graphics2D g2d = (Graphics2D) g;
//		AffineTransform old = g2d.getTransform();
//		old.scale(zoomLevel, zoomLevel);
//		g2d.setTransform(old);

		g.setColor(Color.white);
		Utils.drawCenteredString(g, "Reviving ", new Rectangle((int) x, (int) y - 10, 0, 0),
				new Font(Font.DIALOG, Font.BOLD, 25));
		g.setColor(Color.black);
		g.fillRect((int) x - reviveMax/2, (int) y + 10, reviveMax, 50);
		g.setColor(Color.white);
		g.fillRect((int) x - reviveMax/2, (int) y + 10, reviveProgress, 50);
	//	old.scale(1 / zoomLevel, 1 / zoomLevel);
	//	g2d.setTransform(old);

	}

}

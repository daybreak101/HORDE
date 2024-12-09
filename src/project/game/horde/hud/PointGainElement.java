package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Timer;

public class PointGainElement extends HudElement {

	private int pointsGained;
	private int alpha, type, fontSize;
	private boolean isGain;

	public PointGainElement(Handler handler, int pointsGained, boolean gain) {
		super(40, (int) handler.getHeight() / 2 + 150, 0, 0, handler);
		this.pointsGained = pointsGained;
		alpha = 255;
		fontSize = 30;
		type = RandomUtil.nextInt(0, 4);
		this.isGain = gain;

	}

	Timer fontTick = new Timer(5);

	@Override
	public void tick() {
		y -= 1;
		alpha -= 2;

		fontTick.tick();
		if (fontTick.isReady()) {
			fontTick.resetTimer();
			fontSize--;
		}

		if (alpha <= 0)
			isActive = false;

	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		if (isGain) {
			g.setColor(new Color(255, 255, 0, alpha));
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
			g.drawString("+" + pointsGained, (int) x, (int) y);
		} else {
			g.setColor(new Color(255, 100, 0, alpha));
			g.setFont(new Font(Font.DIALOG, Font.PLAIN, fontSize));
			g.drawString("-" + pointsGained, (int) x, (int) y);
		}

	}

}

package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.playerinfo.Stats;
import project.game.horde.main.Handler;

public class Scoreboard extends HudElement {
	
	private Color hudColor;
	private Stats stats;
	private Player player;

	public Scoreboard(Handler handler, Player player) {
		super(200, 200, 0, 0, handler);
		this.player = player;
	}

	@Override
	public void tick() {
		stats = player.getStats();
		hudColor = handler.getSettings().getHudColor();

	}

	@Override
	public void render(Graphics g) {
		g.setColor(hudColor);
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		g.drawString("Score", (int) x, (int) y);
		g.drawString("Kills", (int) x + 150, (int) y);
		g.drawString("Headshots", (int) x + 300, (int) y);
		g.drawString("Downs", (int) x + 450, (int) y);

		
		g.drawString(String.valueOf(stats.getScore()), (int) x, (int) y + 50);
		g.drawString(String.valueOf(stats.getKills()), (int) x + 150, (int) y + 50);
		g.drawString(String.valueOf(stats.getHeadshots()), (int) x + 300, (int) y + 50);
		g.drawString(String.valueOf(stats.getDowns()), (int) x + 450, (int) y + 50);

	}
	
}

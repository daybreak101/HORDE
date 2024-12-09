package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.Rectangle;
import java.text.DecimalFormat;

import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class StatsState extends State {

	private UIManager uiManager;
	private User user;
	private Font font;
	private int categoryX, statX;

	public StatsState(Handler handler, User user) {
		super(handler);
		font = new Font(Font.DIALOG, Font.PLAIN, 20);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);

		uiManager.addObject(new TextButton(handler, handler.getWidth()/2 - 50, handler.getHeight() - 100, 100, 50, "Back", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new MenuState(handler, user));

			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

		}));
		statX = handler.getWidth()/2;
		categoryX = statX - 160;
		
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);

		int yStart = 160;
		int dy = 40;
		g.setColor(handler.getSettings().getHudColor());
		Utils.drawCenteredString(g, "STATS", new Rectangle(handler.getWidth()/2, 100, 0, 0), new Font(Font.DIALOG, Font.PLAIN, 50));
		Utils.drawLeftAlignedString(g, "Total Games", new Rectangle(categoryX, yStart, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Total Kills", new Rectangle(categoryX, yStart + dy * 1, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Total Headshots", new Rectangle(categoryX, yStart + dy * 2, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Total Downs", new Rectangle(categoryX, yStart + dy * 3, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Perks Ate", new Rectangle(categoryX, yStart + dy * 4, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Perks Spins", new Rectangle(categoryX, yStart + dy * 5, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Box Pulls", new Rectangle(categoryX, yStart + dy * 6, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Box Spins", new Rectangle(categoryX, yStart + dy * 7, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Traps Used", new Rectangle(categoryX, yStart + dy * 8, 0, 0), font);
		Utils.drawLeftAlignedString(g, "K/D Ration", new Rectangle(categoryX, yStart + dy * 9, 0, 0), font);
		Utils.drawLeftAlignedString(g, "Average Round", new Rectangle(categoryX, yStart + dy * 10, 0, 0), font);

		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getTotalGames()), new Rectangle(statX, yStart, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getGlobalKills()), new Rectangle(statX, yStart + dy * 1, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getGlobalHeadshots()), new Rectangle(statX, yStart + dy * 2, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getGlobalDowns()), new Rectangle(statX, yStart + dy * 3, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getPerksAte()), new Rectangle(statX, yStart + dy * 4, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getPerkSpins()), new Rectangle(statX, yStart + dy * 5, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getBoxPulls()), new Rectangle(statX, yStart + dy * 6, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getBoxSpins()), new Rectangle(statX, yStart + dy * 7, 0, 0), font);
		Utils.drawLeftAlignedString(g, Long.toString(handler.getGlobalStats().getTrapPulls()), new Rectangle(statX, yStart + dy * 8, 0, 0), font);

		DecimalFormat df = new DecimalFormat("0.00");
		if (handler.getGlobalStats().getGlobalDowns() <= 0) {
			Utils.drawLeftAlignedString(
					g, 
					df.format((double) handler.getGlobalStats().getGlobalKills()), 
					new Rectangle(statX, yStart + dy * 9, 0, 0), 
					font);
//			g.drawString(
//					df.format((double) handler.getGlobalStats().getGlobalKills()),
//					handler.getWidth() / 2 - 160, yStart + 450);
		} else {
			Utils.drawLeftAlignedString(
					g, 
					df.format((double) handler.getGlobalStats().getGlobalKills() / handler.getGlobalStats().getGlobalDowns()), 
					new Rectangle(statX, yStart + dy * 9, 0, 0),
					font);
//			g.drawString(df.format(
//					(double) handler.getGlobalStats().getGlobalKills() / handler.getGlobalStats().getGlobalDowns()),
//					handler.getWidth() / 2 - 160, yStart + 450);
		}
		Utils.drawLeftAlignedString(g, df.format(handler.getGlobalStats().getAverageRound()), new Rectangle(statX, yStart + dy * 10, 0, 0), font);

		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);
	}

}

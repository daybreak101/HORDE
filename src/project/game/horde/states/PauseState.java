package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.sounds.Sounds;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class PauseState extends State {

	private UIManager uiManager;


	public PauseState(Handler handler, User user) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);

		int width = 128;
		int height = 64;
		int x = handler.getWidth()/2 - width/2;
		
		uiManager.addObject(new TextButton(handler, x, 350, width, height, "RESUME", 25, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				Sounds.resumeAllClips();
				State.setState(handler.getGame().gameState);

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));

		uiManager.addObject(new TextButton(handler, x, 450, width, height, "SETTINGS", 25, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new SettingsState(handler, user));
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));

		uiManager.addObject(new TextButton(handler, x, 550, width, height, "QUIT", 25, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getGlobalStats().calculateNewAverageRound(handler.getRoundLogic().getCurrentRound());
				handler.getGlobalStats().writeToFile();
				handler.getMouseManager().setUIManager(null);
				System.gc();
				handler.setWorld(null);
				State.setState(new MenuState(handler, user));

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));
	}

	@Override
	public void tick() {
		uiManager.tick(); 
	}

	@Override
	public void render(Graphics g) {
		if(handler.getWorld() != null)
			handler.getWorld().render(g);
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 50));
		g.setColor(handler.getSettings().getHudColor());
		//g.drawString("PAUSED", handler.getWidth() / 2 - 140, 200);
		Utils.drawCenteredString(g, "PAUSED", new Rectangle(handler.getWidth()/2, 200, 0, 0), new Font(Font.DIALOG, Font.PLAIN, 50));
		uiManager.render(g);
		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

		g.drawString(Integer.toString(handler.getProgression().getLevel()), 500, 700);
		g.drawString(Long.toString(handler.getProgression().getXP()), 600, 700);
		g.drawString("/ " + Long.toString(handler.getProgression().getXPNeeded()), 700, 700);

	}

}

package project.game.horde.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;

public class MenuState extends State{
	
	private UIManager uiManager;
	private User user;

	public MenuState(Handler handler, User user) {
		super(handler);
		this.user = user;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);

		int width = 300;
		int x = handler.getWidth()/2 - width/2;
		int yStart = handler.getHeight()/6;
		uiManager.addObject(new TextButton(handler, x, yStart, width,100, "Play Game", 50, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				//handler.getGlobalStats().addGame();
				handler.getMouseManager().setUIManager(null);
				//handler.getGame().gameState = new GameState(handler);
				
				State.setState(new LobbyState(handler, user));
				//handler.getGame().getDisplay().createDisplay(Display.FULLSCREEN);
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}


				
			}));
		
		uiManager.addObject(new TextButton(handler, x, yStart + 100,width,100, "Multiplayer", 50, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				//handler.getGlobalStats().addGame();
				handler.getMouseManager().setUIManager(null);
				//handler.getGame().gameState = new GameState(handler);
				State.setState(new MultiplayerState(handler, user));
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		
		uiManager.addObject(new TextButton(handler, x,yStart+200,width,100, "Settings", 50, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new SettingsState(handler, user));
				
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		
		uiManager.addObject(new TextButton(handler, x,yStart+300,width,100, "Stats", 50, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new StatsState(handler, user));
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		
		
		uiManager.addObject(new TextButton(handler, x,yStart+400,width,100, "Quit", 50, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getGame().closeGame();
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		
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
		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);
		
	}
	

}

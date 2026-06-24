package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;

public class LobbyState extends State{

	private UIManager uiManager;
	private User user;
	private String selectedMap = "test";
	
	public LobbyState(Handler handler, User user) {
		super(handler);
		this.user = user;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		fillUI();
	}
	
	public void fillUI() {
		int bottomY = handler.getHeight() - 100;
		uiManager.addObject(new TextButton(handler,
				handler.getWidth() - 300,
				bottomY,
				300,100, "Launch", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getGlobalStats().addGame();
				handler.getMouseManager().setUIManager(null);
				State.setState(new LoadingState(handler, user, selectedMap));
//				handler.getGame().gameState = new GameState(handler, user);
//				State.setState(handler.getGame().gameState);
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		uiManager.addObject(new TextButton(handler, 
				100,
				bottomY,
				300,100, "Back to Menu", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new MenuState(handler, user));
				
			}



			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		uiManager.addObject(new TextButton(handler, 100,50,300,70, "Change Map", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new MapSelectionState(handler, user, LobbyState.this));
				
			}



			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		uiManager.addObject(new TextButton(handler, 100,120,300,70, "Ward", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new WardState(handler, user, LobbyState.this));
				
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		uiManager.addObject(new TextButton(handler, 100,190,300,70, "Upgrades", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new UpgradesState(handler, user, LobbyState.this));
				
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		uiManager.addObject(new TextButton(handler, 100,260,300,70, "Blessings", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new BlessingsInventoryState(handler, user, LobbyState.this));
				
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		uiManager.addObject(new TextButton(handler, 100,330,300,70, "Change Character", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new CustomizeSkinState(handler, user, LobbyState.this));
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {				
			}}));
		
		uiManager.addObject(new TextButton(handler, 100,400,300,70, "Change Hat", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new CustomizeHatState(handler, user, LobbyState.this));
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
		uiManager.addObject(new TextButton(handler, 100,470,300,70, "Leaderboard", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new UpgradesState(handler, user, LobbyState.this));
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}}));
	}

	int i = 0;
	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);

		uiManager.tick();
		i++;
		if(i == 24)
			i = 0; 
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);
		
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));

		g.drawImage(MenuAssets.coins[i /6], handler.getWidth() - 250, 20, 50, 50, null );
		g.drawString(Integer.toString(handler.getProgression().getCoins()), handler.getWidth() - 190, 55);
		//g.drawString(Integer.toString(10000), handler.getWidth() - 190, 55);

		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);
		
	}
	
        @Override
	public void selectedMap(String s) {
		this.selectedMap = s;
	}

}

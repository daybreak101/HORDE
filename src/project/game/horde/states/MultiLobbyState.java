package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;

import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.network.Peer;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class MultiLobbyState extends State {

	private UIManager uiManager;
	private User localUser;
	private Peer peer;
	private boolean isServer;
	public boolean gameAlreadyStarted = false;
	public String selectedMap = "test";

	private HashMap<Integer, User> users = new HashMap<>();

	public MultiLobbyState(Handler handler, User localUser, boolean isServer) {
		super(handler);
		this.localUser = localUser;
		this.peer = new Peer(this, localUser, isServer);
		this.isServer = isServer;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		fillUI();

		if (isServer) {
			try {
				startServer();
			} catch (IOException e) {
			}
			// startLoading();
		}
	}

	public void fillUI() {
		int bottomY = handler.getHeight() - 100;
		if (isServer) {
			uiManager.addObject(new TextButton(handler, 	
					handler.getWidth() - 300,
					bottomY,
					300,100, "Launch", 30, new ClickListener() {

				@Override
				public void onClick(UIObject ui) {
					startLoading(selectedMap);
				}

				@Override
				public void onMouseRelease(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			}));
		}
		uiManager.addObject(new TextButton(handler, 
				100,
				bottomY,
				300,100,  "Back to Menu", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				if (isServer) {
					peer.stopServer();
				} else {
					peer.stopClient();
				}
				handler.getMouseManager().setUIManager(null);
				State.setState(new MenuState(handler, localUser));

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		}));
		if (isServer) {
			uiManager.addObject(new TextButton(handler, 	
					100, 50, 300, 70, "Change Map", 30, new ClickListener() {

				@Override
				public void onClick(UIObject ui) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new MapSelectionState(handler, localUser, MultiLobbyState.this));
					
				}

				@Override
				public void onMouseRelease(MouseEvent e) {
					// TODO Auto-generated method stub

				}
			}));
		}
		uiManager.addObject(new TextButton(handler, 100, 120, 300, 70, "Ward", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new WardState(handler, localUser, MultiLobbyState.this));

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		}));
		uiManager.addObject(new TextButton(handler, 100, 190, 300, 70, "Upgrades", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new UpgradesState(handler, localUser, MultiLobbyState.this));

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		}));
		uiManager.addObject(new TextButton(handler, 100, 260, 300, 70, "Blessings", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new BlessingsInventoryState(handler, localUser, MultiLobbyState.this));

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		}));
		uiManager.addObject(new TextButton(handler, 100,330,300,70, "Change Character", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new CustomizeSkinState(handler, localUser, MultiLobbyState.this));
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}}));
		
		uiManager.addObject(new TextButton(handler, 100,400,300,70, "Change Hat", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new CustomizeHatState(handler, localUser, MultiLobbyState.this));
				
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}}));
	}

	public void startLoading(String map) {
		if (isServer) {
			peer.startLoading();
		}
		handler.getMouseManager().setUIManager(null);
		// peer.setLoadingState(new LoadingState(handler, ));
		State.setState(new LoadingState(handler, localUser, peer, users, map));
	}

	public void startGame(String map) throws IOException {
		if (isServer) {
			peer.startGame();
		}
		peer.setLoadingState(null);
		GameState gameState = new GameState(handler, map, localUser, peer, users);
		handler.getMouseManager().setUIManager(null);
		handler.getGame().gameState = gameState;
		peer.setGameState(gameState);
		State.setState(handler.getGame().gameState);

	}

	public void endGame() {
		if(isServer) {
			peer.endGame();
		}
		gameAlreadyStarted = false;
	}

	public void startServer() throws IOException {
		peer.startLobby();
		// peer.joinLobby("localhost");
	}

	public void joinServer(String host) throws IOException {
		peer.joinLobby(host);
	}

	public void addUser(User user) {
		users.put(user.getConnection().getID(), user);

		// users.put(user.getConnection() != null ? user.getConnection().getID() :
		// user.getUsername().hashCode(), user);
		System.out.println("User added: " + user.getUsername());
	}

	public void removeUser(int connectionId) {
		System.out.println("User removed with connection ID: " + connectionId);
		users.remove(connectionId);

	}

	public HashMap<Integer, User> getUsers() {
		return users;
	}

	int i = 0;

	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
		if (gameAlreadyStarted) {
			startLoading(selectedMap);
		}
		i++;
		if (i == 24)
			i = 0;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);

		int y = 90;
		int x = handler.getWidth() - 50;
		g.setColor(Color.white);
		//g.drawString("Connected Users: " + users.size(), x, y);
		Rectangle rect = new Rectangle(x, y, 0, 0);
		Utils.drawRightAlignedString(g, "Connected Users: " + users.size(), rect, new Font(Font.DIALOG, Font.PLAIN, 20));
		rect.y += 25;
		for (User user : users.values()) {
			Utils.drawRightAlignedString(g, user.getUsername() != null ? user.getUsername()  : "Unknown User",
					rect, new Font(Font.DIALOG, Font.PLAIN, 30));

//			g.drawString(user.getUsername() != null ? user.getUsername()
//					/* + " " + user.getConnection().getID() */ : "Unknown User", x, y);
			rect.y += 30;
		}

		g.drawImage(MenuAssets.coins[i / 6], handler.getWidth() - 250, 20, 50, 50, null);
		g.drawString(Integer.toString(handler.getProgression().getCoins()), handler.getWidth() - 190, 55);

		g.setColor(Color.RED);
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}
	
        @Override
	public void selectedMap(String s) {
		if(!selectedMap.equals(s)) {
			this.selectedMap = s;
			peer.sendNewMapSelection(s);
		}
	}

	public Peer getPeer() {
		return peer;
	}

}
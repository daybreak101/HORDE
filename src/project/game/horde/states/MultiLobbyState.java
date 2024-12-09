package project.game.horde.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.network.Peer;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;

public class MultiLobbyState extends State {

	private UIManager uiManager;
	private User localUser;
	private Peer peer;
	private boolean isServer;
	
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//startLoading();
        }
	}

	public void fillUI() {
		if(isServer) {
		uiManager.addObject(new TextButton(handler, 700, 700, 300, 100, "Launch", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				startLoading();
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		}));
		}
		uiManager.addObject(new TextButton(handler, 100, 700, 300, 100, "Back to Menu", 30, new ClickListener() {

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
		uiManager.addObject(new TextButton(handler, 100,250,300,100, "Upgrades", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new UpgradesState(handler, localUser, MultiLobbyState.this));
				
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}}));
		uiManager.addObject(new TextButton(handler, 100,350,300,100, "Blessings", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(new BlessingsInventoryState(handler, localUser, MultiLobbyState.this));
				
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}}));
	}
	
	public void startLoading() {
		if(isServer) {
			peer.startLoading();
		}
    	handler.getMouseManager().setUIManager(null);
		//peer.setLoadingState(new LoadingState(handler, ));
		State.setState(new LoadingState(handler, localUser, peer, users, "farmhouse"));
	}
		
	public void startGame() {
    	if(isServer) {
    		peer.startGame();
    	}
    	peer.setLoadingState(null);
    	GameState gameState = new GameState(handler, localUser, peer, users);
    	handler.getMouseManager().setUIManager(null);
		handler.getGame().gameState = gameState;
		peer.setGameState(gameState);
		State.setState(handler.getGame().gameState);

	}
	
    public void startServer() throws IOException {
        peer.startLobby();
        //peer.joinLobby("localhost");
    }

    public void joinServer(String host) throws IOException {
        peer.joinLobby(host);
    }

    public void addUser(User user) {
        users.put(user.getConnection().getID(), user);

       // users.put(user.getConnection() != null ? user.getConnection().getID() : user.getUsername().hashCode(), user);
        System.out.println("User added: " + user.getUsername());
    }

    public void removeUser(int connectionId) {
    	System.out.println("User removed with connection ID: " + connectionId);
        users.remove(connectionId);
        
    }
    
    public HashMap<Integer, User> getUsers() {
        return users;
    }

	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);

		g.setColor(Color.white);
        g.drawString("Connected Users:", 50, 50);
        int y = 70;

        // Display player count and connected users
        g.drawString("Player Count: " + users.size(), 50, y);
        y += 20;
        for (User user : users.values()) {
        	//if(user.getConnection() != null)
			g.drawString(user.getUsername() != null ? user.getUsername()
					/* + " " + user.getConnection().getID() */ : "Unknown User", 50, y);
            y += 20;
        }

		g.setColor(Color.RED);
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}

}
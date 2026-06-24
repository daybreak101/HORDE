package project.game.horde.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.io.IOException;

import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;

public class MultiplayerState extends State {

	private UIManager uiManager;
	private User user;

	public MultiplayerState(Handler handler, User user) {
		super(handler);
		this.user = user;
		// user.setConnection(new Connection());
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);

		uiManager.addObject(new TextButton(handler, 550, 400, 300, 100, "Start Lobby", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				MultiLobbyState lobbyState = new MultiLobbyState(handler, user, true);
				try {
					lobbyState.startServer();
				} catch (IOException e) {
				}
				State.setState(lobbyState);
			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				
			}


		}));
		int bottomY = handler.getHeight() - 100;
		uiManager.addObject(new TextButton(handler, 100,
				bottomY,
				300,100, "Back to Menu", 30, new ClickListener() {

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
		uiManager.addObject(new TextButton(handler, 550, 300, 300, 100, "Join Lobby", 30, new ClickListener() {

            @Override
            public void onClick(UIObject ui) {
                handler.getMouseManager().setUIManager(null);
                MultiLobbyState lobbyState = new MultiLobbyState(handler, user, false);
                try {
                    lobbyState.joinServer("localhost"); // Replace "localhost" with the actual server IP
                } catch (IOException e) {
                }
                State.setState(lobbyState);
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
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);
		g.setColor(Color.RED);
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}

}

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

public class MapSelectionState extends State {

	private UIManager uiManager;
	private State lastState;

	public MapSelectionState(Handler handler, User user, State lastState) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		this.lastState = lastState;
		uiManager.addObject(new TextButton(handler, handler.getWidth() / 2 - 50, handler.getHeight() - 100, 100, 50,
				"Back", new ClickListener() {

					@Override
					public void onClick(UIObject ui) {
						handler.getMouseManager().setUIManager(null);
						State.setState(lastState);
					}

					@Override
					public void onMouseRelease(MouseEvent e) {

					}
				}));
		fillUI();
	}

	public void fillUI() {
		int bottomY = handler.getHeight() - 100;
		uiManager.addObject(new TextButton(handler, 100, 50, 300, 70, "Farmhouse", 30, new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				lastState.selectedMap("test");
				handler.getMouseManager().setUIManager(null);
				State.setState(lastState);

			}

			@Override
			public void onMouseRelease(MouseEvent e) {

			}
		}));
		// uiManager.addObject(new TextButton(handler, 100, 120, 300, 70, "Seattle", 30, new ClickListener() {

		// 	@Override
		// 	public void onClick(UIObject ui) {
		// 		lastState.selectedMap("seattle");
		// 		handler.getMouseManager().setUIManager(null);
		// 		State.setState(lastState);

		// 	}

		// 	@Override
		// 	public void onMouseRelease(MouseEvent e) {

		// 	}
		// }));
		// uiManager.addObject(new TextButton(handler, 100, 190, 300, 70, "Iceland", 30, new ClickListener() {

		// 	@Override
		// 	public void onClick(UIObject ui) {
		// 		lastState.selectedMap("iceland");
		// 		handler.getMouseManager().setUIManager(null);
		// 		State.setState(lastState);

		// 	}

		// 	@Override
		// 	public void onMouseRelease(MouseEvent e) {

		// 	}
		// }));
	
	
	}

	int i = 0;

	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);

		uiManager.tick();
		i++;
		if (i == 24)
			i = 0;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);

		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));

		g.drawImage(MenuAssets.coins[i / 6], handler.getWidth() - 250, 20, 50, 50, null);
		g.drawString(Integer.toString(handler.getProgression().getCoins()), handler.getWidth() - 190, 55);
		// g.drawString(Integer.toString(10000), handler.getWidth() - 190, 55);

		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}

}

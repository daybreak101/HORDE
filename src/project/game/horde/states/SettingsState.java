package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import project.game.horde.display.Display;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.ColorIndex;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIListSelect;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class SettingsState extends State {

	private UIManager uiManager;
	private UIListSelect screenModeOptions;
	private UIListSelect zoomLevelOptions;
	private UIListSelect hudColorOptions;
	private UIListSelect laserColorOptions;
	private UIListSelect zombieCounterOptions;
	private UIListSelect toggleCritOptions;
	private UIListSelect toggleDamageOptions;
	private UIListSelect healthBarOptions;
	private UIListSelect masterVolumeOptions;
	private User user;

	int dw = 100, dh = 50;
	int listWidth = 300;
	int listX = (int) (	handler.getWidth()/2);
	int textX = (int) ( listX - 250);


	public SettingsState(Handler handler, User user) {
		super(handler);
		this.user = user;
		uiManager = new UIManager(handler);
		addScreenModeOptions();
		addZoomLevelOptions();
		addHudColorOptions();
		addLaserColorOptions();
		addZombieCounterOptions();
		addToggleCritOptions();
		addToggleDamageOptions();
		addHealthBarOptions();
		addMasterVolumeOptions();
		uiManager.addObject(new TextButton(handler, listX - 50, handler.getHeight() - 100, 100, 50, "Back", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getSettings().writeToFile();
				handler.getMouseManager().setUIManager(null);
				if (handler.getWorld() != null)
					State.setState(new PauseState(handler, user));
				else
					State.setState(new MenuState(handler, user));

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		}));
		handler.getMouseManager().setUIManager(uiManager);
	}

	public void recreateState() {
		textX = (int) ( handler.getWidth() / 2 - 250);
		listX = (int) (	handler.getWidth()/2);
		uiManager = new UIManager(handler);
		addScreenModeOptions();
		addZoomLevelOptions();
		addHudColorOptions();
		addLaserColorOptions();
		addZombieCounterOptions();
		addToggleCritOptions();
		addToggleDamageOptions();
		addHealthBarOptions();
		addMasterVolumeOptions();
		uiManager.addObject(new TextButton(handler, listX - 50, 700, 100, 50, "Back", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getSettings().writeToFile();
				handler.getMouseManager().setUIManager(null);
				if (handler.getWorld() != null)
					State.setState(new PauseState(handler, user));
				else
					State.setState(new MenuState(handler, user));

			}

			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		}));
		handler.getMouseManager().setUIManager(uiManager);
	}

	public void addHealthBarOptions() {
		healthBarOptions = new UIListSelect(handler, uiManager, listX, 565, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				switch (currentSelection) {
				case 0:
					handler.getSettings().setHealthBar(false);
					break;
				case 1:
					handler.getSettings().setHealthBar(true);
					break;
				default:
					handler.getSettings().setHealthBar(false);
					break;
				}
			}
		};
		healthBarOptions.addOption("Off");
		healthBarOptions.addOption("On");
		uiManager.addObject(healthBarOptions);
		boolean isOn = handler.getSettings().isHealthBar();
		healthBarOptions.setCurrentSelection(isOn ? 1 : 0);
	}

	public void addToggleDamageOptions() {
		toggleDamageOptions = new UIListSelect(handler, uiManager, listX, 515, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				switch (currentSelection) {
				case 0:
					handler.getSettings().setToggleDamage(false);
					break;
				case 1:
					handler.getSettings().setToggleDamage(true);
					break;
				default:
					handler.getSettings().setToggleDamage(false);
					break;
				}
			}
		};
		toggleDamageOptions.addOption("Off");
		toggleDamageOptions.addOption("On");
		uiManager.addObject(toggleDamageOptions);
		boolean isOn = handler.getSettings().isToggleDamage();
		toggleDamageOptions.setCurrentSelection(isOn ? 1 : 0);
	}

	public void addToggleCritOptions() {
		toggleCritOptions = new UIListSelect(handler, uiManager, listX, 465, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				switch (currentSelection) {
				case 0:
					handler.getSettings().setToggleCrits(false);
					break;
				case 1:
					handler.getSettings().setToggleCrits(true);
					break;
				default:
					handler.getSettings().setToggleCrits(false);
					break;
				}
			}
		};
		toggleCritOptions.addOption("Off");
		toggleCritOptions.addOption("On");
		uiManager.addObject(toggleCritOptions);
		boolean isOn = handler.getSettings().isToggleCrits();
		toggleCritOptions.setCurrentSelection(isOn ? 1 : 0);
	}

	public void addZombieCounterOptions() {
		zombieCounterOptions = new UIListSelect(handler, uiManager, listX, 415, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				switch (currentSelection) {
				case 0:
					handler.getSettings().setZombieCounter(false);
					break;
				case 1:
					handler.getSettings().setZombieCounter(true);
					break;
				default:
					handler.getSettings().setZombieCounter(false);
					break;
				}
			}
		};
		zombieCounterOptions.addOption("Off");
		zombieCounterOptions.addOption("On");
		uiManager.addObject(zombieCounterOptions);
		boolean isOn = handler.getSettings().isZombieCounter();
		zombieCounterOptions.setCurrentSelection(isOn ? 1 : 0);
	}

	public void addHudColorOptions() {
		hudColorOptions = new UIListSelect(handler, uiManager, listX, 315, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				handler.getSettings().setHudColor(ColorIndex.getColor(currentSelection));
			}
		};
		hudColorOptions.addOption("Green");
		hudColorOptions.addOption("Yellow");
		hudColorOptions.addOption("Red");
		hudColorOptions.addOption("Blue");
		hudColorOptions.addOption("Magenta");
		hudColorOptions.addOption("Cyan");
		hudColorOptions.addOption("Orange");
		hudColorOptions.addOption("White");
		uiManager.addObject(hudColorOptions);
		Color currentColor = handler.getSettings().getHudColor();
		hudColorOptions.setCurrentSelection(ColorIndex.getKeyByValue(currentColor));
	}

	public void addLaserColorOptions() {
		laserColorOptions = new UIListSelect(handler, uiManager, listX, 365, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				handler.getSettings().setLaserColor(ColorIndex.getColor(currentSelection));
			}
		};
		laserColorOptions.addOption("Green");
		laserColorOptions.addOption("Yellow");
		laserColorOptions.addOption("Red");
		laserColorOptions.addOption("Blue");
		laserColorOptions.addOption("Magenta");
		laserColorOptions.addOption("Cyan");
		laserColorOptions.addOption("Orange");
		laserColorOptions.addOption("White");
		uiManager.addObject(laserColorOptions);
		Color currentColor = handler.getSettings().getLaserColor();
		laserColorOptions.setCurrentSelection(ColorIndex.getKeyByValue(currentColor));
	}

	public void addZoomLevelOptions() {
		zoomLevelOptions = new UIListSelect(handler, uiManager, listX, 265, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				switch (currentSelection) {
				case 0:
					handler.getSettings().setZoomLevel(1.25);
					break;
				case 1:
					handler.getSettings().setZoomLevel(1.30);
					break;
				case 2:
					handler.getSettings().setZoomLevel(1.35);
					break;
				case 3:
					handler.getSettings().setZoomLevel(1.40);
					break;
				case 4:
					handler.getSettings().setZoomLevel(1.45);
					break;
				case 5:
					handler.getSettings().setZoomLevel(1.50);
					break;
				default:
					handler.getSettings().setZoomLevel(1.25);
					break;
				}
				if (handler.getCurrentPlayer() != null) {
					handler.getGameCamera().centerOnEntity(handler.getCurrentPlayer());
				}
			}
		};
		zoomLevelOptions.addOption("0");
		zoomLevelOptions.addOption("1");
		zoomLevelOptions.addOption("2");
		zoomLevelOptions.addOption("3");
		zoomLevelOptions.addOption("4");
		zoomLevelOptions.addOption("5");
		uiManager.addObject(zoomLevelOptions);

		int currentZoom = (int) Math.round((handler.getSettings().getZoomLevel(true) - 1.25) / .05);
		zoomLevelOptions.setCurrentSelection(currentZoom);
	}

	public void addScreenModeOptions() {
		if (handler.getWorld() == null) {
			screenModeOptions = new UIListSelect(handler, uiManager, listX, 215, listWidth, dh) {
				public void handleSelection() {
					System.out.println("Handle Selection called. Current Selection: " + currentSelection);
					switch (currentSelection) {
					case Display.STANDARD:
						handler.getGame().getDisplay().resetDisplay(Display.STANDARD);
						handler.getSettings().setDisplayType(Display.STANDARD);
						break;
					case Display.WINDOWEDFULLSCREEN:
						handler.getGame().getDisplay().resetDisplay(Display.WINDOWEDFULLSCREEN);
						handler.getSettings().setDisplayType(Display.WINDOWEDFULLSCREEN);
						break;
					case Display.FULLSCREEN:
						handler.getGame().getDisplay().resetDisplay(Display.FULLSCREEN);
						handler.getSettings().setDisplayType(Display.FULLSCREEN);
						break;
					}
					recreateState();
				}
			};
			screenModeOptions.addOption("Standard");
			screenModeOptions.addOption("Windowed Full");
			screenModeOptions.addOption("Fullscreen");
			uiManager.addObject(screenModeOptions);
			int currentScreen = (int) (handler.getSettings().getDisplayType());
			screenModeOptions.setCurrentSelection(currentScreen);
		}
	}

	public void addMasterVolumeOptions() {
		masterVolumeOptions = new UIListSelect(handler, uiManager, listX, 615, listWidth, dh) {
			public void handleSelection() {
				System.out.println("Handle Selection called. Current Selection: " + currentSelection);
				handler.getSettings().setMasterVolume((float) currentSelection);

			}
		};
		for (int i = 0; i < 11; i++) {
			masterVolumeOptions.addOption(Integer.toString(i));
		}
		uiManager.addObject(masterVolumeOptions);
		masterVolumeOptions.setCurrentSelection((Math.round(handler.getSettings().getMasterVolume())));
	}

	public void deselectAll(ArrayList<TextButton> objects) {
		for (TextButton o : objects) {
			o.setSelected(false);
		}
	}

	@Override
	public void tick() {

		uiManager.tick();
//		listX = (int) (2 * handler.getWidth() / 4);
//		System.out.println(listX);
//		for(UIObject o : uiManager.getObjects()) {
//			//System.out.println("ticking");
//			o.setX(listX);
//		}
	}

	@Override
	public void render(Graphics g) {
		if (handler.getWorld() != null) {
			handler.getWorld().render(g);
		} else {
			g.setColor(Color.black);
			g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		}
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		// screenModeOptions.render(g);
		uiManager.render(g);
		g.setColor(handler.getSettings().getHudColor());
		//g.drawString("SETTINGS", handler.getWidth() / 2 - 160, 100);
		Utils.drawCenteredString(g, "SETTINGS", new Rectangle(handler.getWidth()/2, 100, 0, 0), new Font(Font.DIALOG, Font.PLAIN, 50));
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		g.drawString("Display Type", textX, 250);
		if (handler.getWorld() != null) {
			Utils.drawCenteredString(g, "Can only be changed in Main Menu", new Rectangle(listX, 215, listWidth, 50), new Font(Font.DIALOG, Font.PLAIN, 13));
		} 
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		g.drawString("Zoom Level", textX, 300);
		g.drawString("HUD Color", textX, 350);
		g.drawString("Laser Color", textX, 400);
		g.drawString("Zombie Counter", textX, 450);
		g.drawString("Toggle Crits", textX, 500);
		g.drawString("Toggle Damage", textX, 550);
		g.drawString("Zombie Health", textX, 600);
		g.drawString("Master Volume", textX, 650);

		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);
	}

}

package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.sounds.MenuSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.RewardPopup;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class WardState extends State {
	private UIManager uiManager;
	private UIManager popupManager;
	private User user;
	private BlessingInventory inventory;
	private State lastState;
	private boolean isPopup = false;
	private RewardPopup popup = new RewardPopup(handler, new ArrayList<String>(), new Font(Font.DIALOG, Font.PLAIN, 30),
			new ClickListener() {

				@Override
				public void onClick(UIObject ui) {
					// TODO Auto-generated method stub
					System.out.println("Popup removed");
					isPopup = false;
					popup.clearRewards();
				}

				@Override
				public void onMouseRelease(MouseEvent e) {

				}
			});

	public WardState(Handler handler, User user, State lastState) {
		super(handler);
		this.lastState = lastState;
		inventory = handler.getBlessings();
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		popupManager = new UIManager(handler);
		popupManager.addObject(popup);
		uiManager.addObject(new TextButton(handler, handler.getWidth() / 2 - 50, handler.getHeight() - 100, 100, 50,
				"Back", new ClickListener() {

					@Override
					public void onClick(UIObject ui) {
						handler.getMouseManager().setUIManager(null);
						State.setState(lastState);
					}

					@Override
					public void onMouseRelease(MouseEvent e) {
						// TODO Auto-generated method stub

					}
				}));
		uiAdd();
	}

	public void uiAdd() {
		TextButton one = new TextButton(handler, handler.getWidth() / 4 - 100, handler.getHeight() / 2, 100, 50, "1",
				new ClickListener() {

					@Override
					public void onClick(UIObject ui) {
						if (handler.getProgression().useGoldenCoins(1)) {
							ArrayList<String> rewards = buyBlessings(1);
							popup.addRewards(rewards);
							System.out.println("Popup added");
						} else {
							Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
						}
					}

					@Override
					public void onMouseRelease(MouseEvent e) {
						// TODO Auto-generated method stub

					}

				});
		TextButton two = new TextButton(handler, handler.getWidth() / 2 - 100, handler.getHeight() / 2, 100, 50, "2",
				new ClickListener() {

					@Override
					public void onClick(UIObject ui) {
						if (handler.getProgression().useGoldenCoins(2)) {
							ArrayList<String> rewards = buyBlessings(2);

							popup.addRewards(rewards);
							System.out.println("Popup added");
						} else {
							Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
						}

					}

					@Override
					public void onMouseRelease(MouseEvent e) {
						// TODO Auto-generated method stub

					}

				});
		TextButton three = new TextButton(handler, 3 * handler.getWidth() / 4 - 100, handler.getHeight() / 2, 100, 50,
				"3", new ClickListener() {

					@Override
					public void onClick(UIObject ui) {
						if (handler.getProgression().useGoldenCoins(3)) {
							ArrayList<String> rewards = buyBlessings(3);

							popup.addRewards(rewards);
							System.out.println("Popup added");
						} else {
							Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
						}

					}

					@Override
					public void onMouseRelease(MouseEvent e) {
						// TODO Auto-generated method stub

					}

				});
		one.setImage(MenuAssets.coins[0], true, 50, 50, 5);
		two.setImage(MenuAssets.coins[0], true, 50, 50, 5);
		three.setImage(MenuAssets.coins[0], true, 50, 50, 5);
		uiManager.addObject(one);
		uiManager.addObject(two);
		uiManager.addObject(three);
	}

	@Override
	public void tick() {
		if (!isPopup && popup.getRewards().size() != 0) {
			isPopup = true;
		}
		if (isPopup) {
			handler.getMouseManager().setUIManager(popupManager);
			popupManager.tick();
		} else {
			handler.getMouseManager().setUIManager(uiManager);
			uiManager.tick();
		}

	}

	public ArrayList<String> buyBlessings(int level) {
		ArrayList<String> rewards = new ArrayList<String>();
		if (level == 1) {
			rewards.add(generateRandomCommon(1));
			System.out.println(rewards);
		} else if (level == 2) {
			rewards.add(generateRandomRare(1));
			rewards.add(generateRandomCommon(2));

		} else if (level == 3) {
			rewards.add(generateRandomEpic(3));
			rewards.add(generateRandomCommon(3));
			rewards.add(generateRandomCommon(3));
		}
		handler.getBlessings().addBlessings(rewards);
		return rewards;
	}

	ArrayList<String> commons = new ArrayList<>(Arrays.asList(BlessingInventory.SPAWN_DOUBLE_POINTS,
			BlessingInventory.SPAWN_NUKE, BlessingInventory.GAIN_POINTS, BlessingInventory.FORCE_CRAWLERS,
			BlessingInventory.TELEPORT, BlessingInventory.INVISIBILITY, BlessingInventory.RANDOM_POWERUP));
	ArrayList<String> rares = new ArrayList<>(Arrays.asList(BlessingInventory.FREEZE_ALL_ZOMBIES,
			BlessingInventory.SPAWN_HEALTH, BlessingInventory.SPAWN_MINIGUN, BlessingInventory.SPAWN_MAX_AMMO,
			BlessingInventory.SPAWN_INFINITE_AMMO));
	ArrayList<String> epics = new ArrayList<>(Arrays.asList(BlessingInventory.SPAWN_INSTAKILL,
			BlessingInventory.POINTS_MULTIPLY, BlessingInventory.GUARANTEE_HEADSHOTS, BlessingInventory.UPGRADE_WEAPON,
			BlessingInventory.RANDOM_PERK));
	ArrayList<String> legendaries = new ArrayList<>(Arrays.asList(BlessingInventory.SPAWN_ALL_DROPS,
			BlessingInventory.ROUND_SKIP, BlessingInventory.GIVE_ALL_PERKS));
	Random rand = new Random();

	public String generateRandomCommon(int level) {
		String blessing;
		int upgrade = rand.nextInt(100);
		if (level == 1 && upgrade > 50) {
			blessing = generateRandomRare(level);
		} else if (level == 2 && upgrade > 33) {
			blessing = generateRandomRare(level);
		} else if (level == 3 && upgrade > 20) {
			blessing = generateRandomRare(level);
		} else {
			int pick = rand.nextInt(commons.size());
			blessing = commons.get(pick);
		}
		return blessing;
	}

	public String generateRandomRare(int level) {
		String blessing;
		int upgrade = rand.nextInt(100);
		if (level == 1 && upgrade > 66) {
			blessing = generateRandomEpic(level);
		} else if (level == 2 && upgrade > 50) {
			blessing = generateRandomEpic(level);
		} else if (level == 3 && upgrade > 33) {
			blessing = generateRandomEpic(level);
		} else {
			int pick = rand.nextInt(rares.size());
			blessing = rares.get(pick);
		}
		return blessing;
	}

	public String generateRandomEpic(int level) {
		String blessing;
		int upgrade = rand.nextInt(100);
		if (level == 1 && upgrade > 80) {
			blessing = generateRandomLegendary();
		} else if (level == 2 && upgrade > 66) {
			blessing = generateRandomLegendary();
		} else if (level == 3 && upgrade > 50) {
			blessing = generateRandomLegendary();
		} else {
			int pick = rand.nextInt(epics.size());
			blessing = epics.get(pick);
		}
		return blessing;
	}

	public String generateRandomLegendary() {
		String blessing;
		int pick = rand.nextInt(legendaries.size());
		blessing = legendaries.get(pick);
		return blessing;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());

		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		g.setColor(handler.getSettings().getHudColor());
		// g.drawString("PERKS", handler.getWidth() - 350, 70);
		Utils.drawCenteredString(g, "WARD", new Rectangle(handler.getWidth() / 2, 70, 0, 0),
				new Font(Font.DIALOG, Font.PLAIN, 30));
		Utils.drawCenteredString(g, "Pay tithe to receive Blessings", new Rectangle(handler.getWidth() / 2, 100, 0, 0),
				new Font(Font.DIALOG, Font.PLAIN, 20));
		if (isPopup)
			popupManager.render(g);
		else
			uiManager.render(g);

		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}
}

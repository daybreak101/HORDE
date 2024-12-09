package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.GridContainer;
import project.game.horde.ui.GridElementImageAndText;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class BlessingsInventoryState extends State {
	private UIManager uiManager;
	private User user;
	private GridContainer grid; 
	private BlessingInventory inventory;

	public BlessingsInventoryState(Handler handler, User user, State lastState) {
		super(handler);
		inventory = handler.getBlessings();
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
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
		int fontSize = 20;
		int size = Math.min((handler.getWidth()/2 - 100)/4, (handler.getHeight() - 200)/5);
		int startx = (handler.getWidth()/2 - (size * 4))/2;
		int rows = 5;
		int columns = 4;
		Font font = new Font(Font.DIALOG, Font.PLAIN, fontSize);
		grid = new GridContainer(handler, startx, 100, size * columns, size * rows, columns, rows, true);
		
		//SPAWN DOUBLE POINTS
		GridElementImageAndText blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_DOUBLE_POINTS), 
				MenuAssets.doubleTimeBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_DOUBLE_POINTS);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};	
		blessing.setInfo(BlessingInventory.SPAWN_DOUBLE_POINTS, BlessingInventory.COMMON, "Activate to force spawn in a Double Points powerup.", inventory.getAmount(BlessingInventory.SPAWN_DOUBLE_POINTS));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_DOUBLE_POINTS));
		grid.addElement(blessing);
		
		//SPAWN NUKE
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_NUKE), MenuAssets.kaboomBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_NUKE);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.SPAWN_NUKE, BlessingInventory.COMMON, "Activate to force spawn in a Nuke powerup.", inventory.getAmount(BlessingInventory.SPAWN_NUKE));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_NUKE));
		grid.addElement(blessing);

		
		//GAIN POINTS
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.GAIN_POINTS), MenuAssets.ezPointsBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.GAIN_POINTS);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.GAIN_POINTS, BlessingInventory.COMMON, "Activate to instantly gain 500 points.", inventory.getAmount(BlessingInventory.GAIN_POINTS));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.GAIN_POINTS));
		grid.addElement(blessing);
		
		//FORCE CRAWLERS
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.FORCE_CRAWLERS), MenuAssets.crawlSpaceBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.FORCE_CRAWLERS);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.FORCE_CRAWLERS, BlessingInventory.COMMON, "Activate to turn all current enemies in the arena to become shambling crawlers.", inventory.getAmount(BlessingInventory.FORCE_CRAWLERS));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.FORCE_CRAWLERS));
		grid.addElement(blessing);
		
		
		//TELEPORT
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.TELEPORT), MenuAssets.anywhereButHereBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.TELEPORT);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.TELEPORT, BlessingInventory.COMMON, "Activate to teleport to a random location. Personal buff.", inventory.getAmount(BlessingInventory.TELEPORT));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.TELEPORT));
		grid.addElement(blessing);
		
		//INVISIBILITY
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.INVISIBILITY), MenuAssets.inPlainSightBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.INVISIBILITY);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.INVISIBILITY, BlessingInventory.COMMON, "Activate to become invisible to all enemies. Personal buff that lasts for 10 seconds.", inventory.getAmount(BlessingInventory.INVISIBILITY));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.INVISIBILITY));
		grid.addElement(blessing);
		
		//RANDOM POWERUP
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.RANDOM_POWERUP), MenuAssets.imFeelingLuckyBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.RANDOM_POWERUP);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.RANDOM_POWERUP, BlessingInventory.COMMON, "Activate to force spawn in a random powerup.", inventory.getAmount(BlessingInventory.RANDOM_POWERUP));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.RANDOM_POWERUP));
		grid.addElement(blessing);
		
		//FREEZE ALL ZOMBIES
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.FREEZE_ALL_ZOMBIES), MenuAssets.brainFreezeBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.FREEZE_ALL_ZOMBIES);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.FREEZE_ALL_ZOMBIES, BlessingInventory.RARE, "Activate to make all current enemies frozen.", inventory.getAmount(BlessingInventory.FREEZE_ALL_ZOMBIES));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.FREEZE_ALL_ZOMBIES));
		grid.addElement(blessing);
		
		
		//SPAWN HEALTHUP
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_HEALTH), MenuAssets.hpUpBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_HEALTH);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.SPAWN_HEALTH, BlessingInventory.RARE, "Activate to force spawn in a Health-UP! powerup. Health-UP! restores personal health to its current maximum capacity.", inventory.getAmount(BlessingInventory.SPAWN_HEALTH));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_HEALTH));
		grid.addElement(blessing);
		
		//SPAWN MINIGUN
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_MINIGUN), MenuAssets.deathMachineBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_MINIGUN);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.SPAWN_MINIGUN, BlessingInventory.RARE, "Activate to force spawn in a Minigun powerup. The temporary Minigun buff is player-specific and has unlimited ammo.", inventory.getAmount(BlessingInventory.SPAWN_MINIGUN));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_MINIGUN));
		grid.addElement(blessing);
		
		//SPAWN MAX AMMO
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_MAX_AMMO), MenuAssets.fullSupplyBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_MAX_AMMO);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.SPAWN_MAX_AMMO, BlessingInventory.RARE, "Activate to force spawn in a Max Ammo powerup. Max Ammo resupplies all ammunition for all players to their maximum capacity.", inventory.getAmount(BlessingInventory.SPAWN_MAX_AMMO));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_MAX_AMMO));
		grid.addElement(blessing);
		
		//SPAWN INFINITE AMMO
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_INFINITE_AMMO), MenuAssets.infiniteSupplyBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_INFINITE_AMMO);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.SPAWN_INFINITE_AMMO, BlessingInventory.RARE, "Activate to force spawn in an Infinite Ammo powerup. Infinite Ammo grants the temporary ability to have endless supply of ammunition for all players.", inventory.getAmount(BlessingInventory.SPAWN_INFINITE_AMMO));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_INFINITE_AMMO));
		grid.addElement(blessing);
		
		//SPAWN INSTAKILL
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_INSTAKILL), MenuAssets.noMercyBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_INSTAKILL);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.SPAWN_INSTAKILL, BlessingInventory.EPIC, "Activate to force spawn in an Instakill powerup. Instakill grants the temporary ability to one-shot enemies with any held weapon for all players.", inventory.getAmount(BlessingInventory.SPAWN_INSTAKILL));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_INSTAKILL));
		grid.addElement(blessing);
		
		//POINTS MULTIPLY
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.POINTS_MULTIPLY), MenuAssets.extraChangeBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.POINTS_MULTIPLY);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.POINTS_MULTIPLY, BlessingInventory.EPIC, "Activate to double earning rate of points. Lasts for 3 minutes. Personal buff.", inventory.getAmount(BlessingInventory.POINTS_MULTIPLY));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.POINTS_MULTIPLY));
		grid.addElement(blessing);
		
		//GUARANTEE HEADSHOTS
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.GUARANTEE_HEADSHOTS), MenuAssets.soNoHeadBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.GUARANTEE_HEADSHOTS);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.GUARANTEE_HEADSHOTS, BlessingInventory.EPIC, "Activate to have a 100% chance of triggering crits. Lasts for a minute. Personal buff.", inventory.getAmount(BlessingInventory.GUARANTEE_HEADSHOTS));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.GUARANTEE_HEADSHOTS));
		grid.addElement(blessing);
		
		//UPGRADE WEAPON
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.UPGRADE_WEAPON), MenuAssets.gradedUpBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.UPGRADE_WEAPON);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.UPGRADE_WEAPON, BlessingInventory.EPIC, "Activate to instantly upgrade your held weapon. Personal buff.", inventory.getAmount(BlessingInventory.UPGRADE_WEAPON));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.UPGRADE_WEAPON));
		grid.addElement(blessing);
		
		//RANDOM PERK
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.RANDOM_PERK), MenuAssets.extraSodiumBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.RANDOM_PERK);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.RANDOM_PERK, BlessingInventory.EPIC, "Activate to force a random perk for all players.", inventory.getAmount(BlessingInventory.RANDOM_PERK));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.RANDOM_PERK));
		grid.addElement(blessing);
		
		//SPAWN ALL POWERUPS
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.SPAWN_ALL_DROPS), MenuAssets.reignDropsBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.SPAWN_ALL_DROPS);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.SPAWN_ALL_DROPS, BlessingInventory.LEGENDARY, "Activate to force spawn in all powerups at once.", inventory.getAmount(BlessingInventory.SPAWN_ALL_DROPS));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.SPAWN_ALL_DROPS));
		grid.addElement(blessing);
		
		//ROUND SKIP
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.ROUND_SKIP), MenuAssets.roundRobbinBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.ROUND_SKIP);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.ROUND_SKIP, BlessingInventory.LEGENDARY, "Activate to skip the current round.", inventory.getAmount(BlessingInventory.ROUND_SKIP));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.ROUND_SKIP));
		grid.addElement(blessing);

		//GIVE ALL PERKS
		blessing = new GridElementImageAndText(handler, inventory.getAmount(BlessingInventory.GIVE_ALL_PERKS), MenuAssets.carboloadBlessing, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					isSelected = inventory.equipOrRemoveBlessing(BlessingInventory.GIVE_ALL_PERKS);
					handler.getMouseManager().reset();
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		};
		blessing.setInfo(BlessingInventory.GIVE_ALL_PERKS, BlessingInventory.LEGENDARY, "Activate to instantly gain all perks.", inventory.getAmount(BlessingInventory.GIVE_ALL_PERKS));
		blessing.setSelected(handler.getBlessings().getEquipped().contains(BlessingInventory.GIVE_ALL_PERKS));
		grid.addElement(blessing);
		
		for(UIObject ui : grid.getUiElements()) {
			uiManager.addObject(ui);
		}
		
	}

	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);

		uiManager.tick();
		grid.tick();

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);
		
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		g.setColor(handler.getSettings().getHudColor());
		//g.drawString("PERKS", handler.getWidth() - 350, 70);
		Utils.drawCenteredString(g, "BLESSINGS", 
				new Rectangle(handler.getWidth()/2, 70, 0, 0), 
				new Font(Font.DIALOG, Font.PLAIN, 30));
		grid.render(g);
		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}
}

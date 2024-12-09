package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import project.game.horde.graphics.Assets;
import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.perks.DeadShot;
import project.game.horde.perks.DoubleTap;
import project.game.horde.perks.Juggernaut;
import project.game.horde.perks.Luna;
import project.game.horde.perks.MuleKick;
import project.game.horde.perks.Perk;
import project.game.horde.perks.PhD;
import project.game.horde.perks.Revive;
import project.game.horde.perks.SleightOfHand;
import project.game.horde.perks.StaminUp;
import project.game.horde.perks.Stronghold;
import project.game.horde.perks.Vampire;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.GridContainer;
import project.game.horde.ui.GridElementImageAndText;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class UpgradesState extends State {

	private UIManager uiManager;
	private User user;
	private GridContainer grid;
	
	public UpgradesState(Handler handler, User user, State lastState) {
		super(handler);
		this.user = user;
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		uiManager.addObject(new TextButton(handler, 
				handler.getWidth()/2 - 50, handler.getHeight() - 100, 100, 50, "Back", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getMouseManager().setUIManager(null);
				State.setState(lastState);
			}


			@Override
			public void onMouseRelease(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}}));
		
		//perks
		int fontSize = 20;
		Perk deadshot = new DeadShot(handler, handler.getUnlocks().getDeadshotLvl(), null);
		Perk doubletap = new DoubleTap(handler, handler.getUnlocks().getDoubletapLvl(), null);
		Perk jugg = new Juggernaut(handler, handler.getUnlocks().getJuggLvl(), null);
		Perk mule = new MuleKick(handler, handler.getUnlocks().getMuleLvl(), null);
		Perk phd = new PhD(handler, handler.getUnlocks().getPhdLvl(), null);
		Perk revive = new Revive(handler, handler.getUnlocks().getReviveLvl(), null);
		Perk speedcola = new SleightOfHand(handler, handler.getUnlocks().getSpeedLvl(), null);
		Perk staminup = new StaminUp(handler, handler.getUnlocks().getStaminaLvl(), null);
		Perk stronghold = new Stronghold(handler, handler.getUnlocks().getStrongholdLvl(), null);
		Perk vampire = new Vampire(handler, handler.getUnlocks().getVampireLvl(), null);
		Perk luna = new Luna(handler, handler.getUnlocks().getLunaLvl(), null);
//		uiManager.addObject(new TextButton(handler, x, y + i * dy, 100, height , "Samurai Wasabi", fontSize, new ClickListener() {
//			@Override
//			public void onClick() {
//				handler.getMouseManager().setUIManager(null);
//				State.setState(new PerkState(handler,
//						"Melee now attacks multiple enemies",
//						"Apply bleed on melee",
//						"Melee kills health 10",
//						"Melee has a chance to instakill",
//						"Applies random elemental effect on melee:
//							Fire: apply burn
//							Wind: apply pushback
//							Earth: either earthquake dizziness or create defensive rock walls
//							Water: apply frozen effect"
//						));
//			}}));
//		i++;
		int size = Math.min((handler.getWidth() - 200)/4, (handler.getHeight() - 200)/3);
		int startx = (handler.getWidth() - (size * 4))/2;
		Font font = new Font(Font.DIALOG, Font.PLAIN, fontSize);
		int rows = 3;
		int columns = 4;
		grid = new GridContainer(handler, startx, 100, size * columns, size * rows, columns, rows, false);
		grid.addElement(new GridElementImageAndText(handler, jugg.getRealName(), MenuAssets.jugg, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, jugg, MenuAssets.jugg, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, speedcola.getRealName(), MenuAssets.fasthand, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, speedcola, MenuAssets.fasthand, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, doubletap.getRealName(), MenuAssets.doubletap, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, doubletap, MenuAssets.doubletap, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, revive.getRealName(), MenuAssets.revive, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, revive, MenuAssets.revive, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, staminup.getRealName(), MenuAssets.stam, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, staminup, MenuAssets.stam, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, phd.getRealName(), MenuAssets.phd, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, phd, MenuAssets.phd, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, mule.getRealName(), MenuAssets.mule, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, mule, MenuAssets.mule, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, deadshot.getRealName(), MenuAssets.deadshot, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, deadshot, MenuAssets.deadshot, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, stronghold.getRealName(), MenuAssets.stronghold, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, stronghold, MenuAssets.stronghold, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, luna.getRealName(), MenuAssets.luna, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, luna, MenuAssets.luna, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
		grid.addElement(new GridElementImageAndText(handler, vampire.getRealName(), MenuAssets.vamp, font){
			@Override
			public void onMouseRelease(MouseEvent e) {
				if (hovering) {
					handler.getMouseManager().setUIManager(null);
					State.setState(new PerkState(handler, vampire, MenuAssets.vamp, user, UpgradesState.this));
				}
			}

			public void onMouseMove(MouseEvent e) {
				if (bounds.contains(e.getX(), e.getY()))
					hovering = true;
				else
					hovering = false;

			}
		});
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
		Utils.drawCenteredString(g, "PERKS", 
				new Rectangle(handler.getWidth()/2, 70, 0, 0), 
				new Font(Font.DIALOG, Font.PLAIN, 30));
		grid.render(g);
		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}

}

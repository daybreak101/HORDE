package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.perks.Perk;
import project.game.horde.sounds.MenuSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class PerkState extends State {

	private final UIManager uiManager;
	private final BufferedImage chipBag;
	private Perk perk;
	private int level;
	private final String perkName, jokeDesc, basedesc, lvl1desc, lvl2desc, lvl3desc;

	public PerkState(Handler handler, Perk perk, BufferedImage perkBag, User user, State lastState) {
		super(handler);
		this.perk = perk;
		this.level = perk.getLevel();
		this.perkName = perk.getRealName();
		this.jokeDesc = perk.getJokeDesc();
		this.basedesc = perk.getBaseDesc();
		this.lvl1desc = perk.getLvl1Desc();
		this.lvl2desc = perk.getLvl2Desc();
		this.lvl3desc = perk.getLvl3Desc();
		this.chipBag = perkBag;

		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		int x = handler.getWidth()/4 - 170;
		int y = handler.getHeight()/2 + 20;
		TextButton lvl1 = new TextButton(handler, x, y, 170, 50, "1  Upgrade", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				if (level == 0 && ui.getIsVisible()) {
					if (handler.getProgression().useGoldenCoins(1)) {
						ui.setIsVisible(false);
						level++;
						perk.incrementLevelUpgrade(level);
					}
					else {
						Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
					}
				}
				else {
					Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
				}
			}

			@Override
			public void onMouseRelease(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			


		});
		lvl1.setImage(MenuAssets.coins[0], true, 50, 50, 10);
		lvl1.setIsOutlined(true);
		lvl1.setClickSound("");

		if (level < 1)
			uiManager.addObject(lvl1);

		TextButton lvl2 = new TextButton(handler, x, y + 50, 170, 50, "2  Upgrade", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				if (level == 1 && ui.getIsVisible()) {
					if (handler.getProgression().useGoldenCoins(2)) {
						ui.setIsVisible(false);
						level++;
						perk.incrementLevelUpgrade(level);
					}
					else {
						Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
					}
				}
				else {
					Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
				}
			}

			@Override
			public void onMouseRelease(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}


		});
		lvl2.setImage(MenuAssets.coins[0], true, 50, 50, 10);
		lvl2.setIsOutlined(true);
		lvl2.setClickSound("");


		if (level < 2)
			uiManager.addObject(lvl2);

		TextButton lvl3 = new TextButton(handler, x, y + 100, 170, 50, "3  Upgrade", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				if (level == 2 && ui.getIsVisible()) {
					if (handler.getProgression().useGoldenCoins(3)) {
						ui.setIsVisible(false);
						level++;
						perk.incrementLevelUpgrade(level);
					}
					else {
						Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
					}
				}
				else {
					Sounds.playClip(MenuSounds.MENU_BUTTON_DENIED_ID, 1, 1, false);
				}
			}

			@Override
			public void onMouseRelease(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

		});
		lvl3.setImage(MenuAssets.coins[0], true, 50, 50, 10);
		lvl3.setIsOutlined(true);
		lvl3.setClickSound("");

		
		if (level < 3)
			uiManager.addObject(lvl3);

		uiManager.addObject(new TextButton(handler, handler.getWidth()/2 - 50, handler.getHeight() - 100, 100, 50, "Back", new ClickListener() {

			@Override
			public void onClick(UIObject ui) {
				handler.getSettings().writeToFile();
				handler.getMouseManager().setUIManager(null);

				cleanUp();
				State.setState(lastState);

			}

			@Override
			public void onMouseRelease(java.awt.event.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

		}));

	}

	private void initFX() {
		Scene scene = createScene();
		handler.getGame().getDisplay().showFXPanel(scene);
	}

	private Scene createScene() {
		Group root = new Group();
		Scene scene = new Scene(root, 500, 400);
		scene.setFill(javafx.scene.paint.Color.BLACK);

		// Create a simple 3D Box
		Box box = new Box(100, 100, 100);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(javafx.scene.paint.Color.CYAN);
		box.setMaterial(material);
		box.getTransforms().addAll(new Rotate(45, Rotate.X_AXIS), new Rotate(45, Rotate.Y_AXIS));
		box.setTranslateX(250); // X coordinate
		box.setTranslateY(200); // Y coordinate
		box.setTranslateZ(100); // Z coordinate

		// Add mouse event handlers for rotation
		// Add mouse event handlers for rotation
		final double[] anchorX = new double[1];
		final double[] anchorY = new double[1];

		scene.setOnMousePressed(event -> {
			anchorX[0] = event.getSceneX();
			anchorY[0] = event.getSceneY();
		});

		scene.setOnMouseDragged(event -> {
			double deltaX = event.getSceneX() - anchorX[0];
			double deltaY = event.getSceneY() - anchorY[0];

			box.getTransforms().add(new Rotate(deltaX, Rotate.Y_AXIS));
			box.getTransforms().add(new Rotate(deltaY, Rotate.X_AXIS));

			anchorX[0] = event.getSceneX();
			anchorY[0] = event.getSceneY();
		});

		root.getChildren().add(box);

		return scene;
//        Group root = new Group();
//        Scene scene = new Scene(root, 500, 400);
//        scene.setFill(javafx.scene.paint.Color.BLACK);
//
//        // Create a simple 3D Box
//        Box box = new Box(100, 100, 100);
//        PhongMaterial material = new PhongMaterial();
//        material.setDiffuseColor(javafx.scene.paint.Color.CYAN);
//        box.setMaterial(material);
//        box.getTransforms().addAll(new Rotate(45, Rotate.X_AXIS), new Rotate(45, Rotate.Y_AXIS));
//
//        root.getChildren().add(box);
//
//        return scene;
	}

	private void cleanUp() {
		Platform.runLater(() -> handler.getGame().getDisplay().hideFXPanel());
	}

	int i = 0;

	@Override
	public void tick() {
		handler.getMouseManager().setUIManager(uiManager);

		i++;
		if (i == 24)
			i = 0;
		uiManager.tick();
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
		uiManager.render(g);
		g.setColor(handler.getSettings().getHudColor());
		Utils.drawLeftAlignedString(g, perkName, new Rectangle(handler.getWidth()/2, handler.getHeight()/5, 0, 0), new Font(Font.DIALOG, Font.BOLD, 40));
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
		g.drawImage(MenuAssets.coins[i / 6], handler.getWidth() - 250, 20, 50, 50, null);
		g.drawString(Integer.toString(handler.getProgression().getCoins()), handler.getWidth() - 190, 55);

		g.setFont(new Font(Font.DIALOG, Font.ITALIC, 20));
		
		int size = Math.min(handler.getWidth()/2, handler.getHeight()/5 + 50);
		int newX = (handler.getWidth()/2 - size)/2;
		int newY = (handler.getHeight()/5 + 50)/2;
		g.drawImage(chipBag, newX, newY, size, size, null);
		
		
		g.setColor(handler.getSettings().getHudColor().darker());
		Utils.drawParagraph((Graphics2D) g, jokeDesc, handler.getWidth()/2, handler.getHeight()/5 + 50, 2 * handler.getWidth()/5);

		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		g.setColor(handler.getSettings().getHudColor());
		int x = handler.getWidth()/4;
		int y = handler.getHeight()/2;
		g.drawString("Base: " + basedesc, x, y);
		g.drawString("      1: " + lvl1desc, x, y + 50);
		g.drawString("      2: " + lvl2desc, x, y + 100);
		g.drawString("      3: " + lvl3desc, x, y + 150);

		g.setColor(handler.getSettings().getLaserColor());
		g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

	}

}

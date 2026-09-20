package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import project.game.horde.graphics.CharAssets;
import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.CustomSkinInventory;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.GridContainer;
import project.game.horde.ui.GridElementImage;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIDialog;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class CustomizeSkinState extends State {

    private UIManager gridManager, dialogManager;
    private User user;
    private GridContainer grid;
    private CustomSkinInventory inventory;
    public UIDialog dialog;

    public CustomizeSkinState(Handler handler, User user, State lastState) {
        super(handler);
        dialog = null;
        inventory = handler.getSkinInv();
        gridManager = new UIManager(handler);
        dialogManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(gridManager);
        gridManager.addObject(new TextButton(handler, handler.getWidth() / 2 - 50, handler.getHeight() - 100, 100, 50,
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
        int fontSize = 20;
        int size = Math.min((handler.getWidth() / 2 - 100) / 4, (handler.getHeight() - 200) / 5);
        int startx = (handler.getWidth() / 2 - (size * 4)) / 2;
        int rows = 5;
        int columns = 4;
        Font font = new Font(Font.DIALOG, Font.PLAIN, fontSize);
        grid = new GridContainer(handler, startx, 100, size * columns, size * rows, columns, rows, false);

        // harry/default
        GridElementImage skin = new GridElementImage(handler, CharAssets.harry, (element, g) -> {
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    for (UIObject ui : grid.getUiElements()) {
                        ((GridElementImage) ui).setSelected(false);
                    }
                    isSelected = inventory.setSkin(CustomSkinInventory.HARRY);
                    handler.getMouseManager().reset();

                    if (lastState instanceof MultiLobbyState multiLobbyState) {
                        multiLobbyState.getPeer().sendUserSkinChange(user.getUsername(), CustomSkinInventory.HARRY);
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        skin.setSelected(handler.getSkinInv().getEquippedSkin() == CustomSkinInventory.HARRY);
        grid.addElement(skin);

        // blue alien
        skin = new GridElementImage(handler, CharAssets.blueAlien, (element, g) -> {
            if (!handler.getSkinInv().checkOwned(CustomSkinInventory.BLUE_ALIEN)) {
                g.setColor(new Color(200, 0, 0, 100));
                g.fillRect((int) element.getX(), (int) element.getY(), element.getWidth(), element.getHeight());
            }
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    if (!inventory.checkOwned(CustomSkinInventory.BLUE_ALIEN)) {
                        //create dialog
                        createDialog(CustomSkinInventory.BLUE_ALIEN, CustomSkinInventory.BLUE_ALIEN_PRICE, "Buy Blue Alien?", CharAssets.blueAlien);
                    } else {
                        //if already owned
                        for (UIObject ui : grid.getUiElements()) {
                            ((GridElementImage) ui).setSelected(false);
                        }
                        isSelected = inventory.setSkin(CustomSkinInventory.BLUE_ALIEN);
                        handler.getMouseManager().reset();

                        if (lastState instanceof MultiLobbyState multiLobbyState) {
                            multiLobbyState.getPeer().sendUserSkinChange(user.getUsername(), CustomSkinInventory.BLUE_ALIEN);
                        }
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        skin.setSelected(handler.getSkinInv().getEquippedSkin() == CustomSkinInventory.BLUE_ALIEN);
        grid.addElement(skin);

        // robot
        skin = new GridElementImage(handler, CharAssets.robot, (element, g) -> {
            if (!handler.getSkinInv().checkOwned(CustomSkinInventory.ROBOT)) {
                g.setColor(new Color(200, 0, 0, 100));
                g.fillRect((int) element.getX(), (int) element.getY(), element.getWidth(), element.getHeight());
            }
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    //if not owned, prompt to buy
                    if (!inventory.checkOwned(CustomSkinInventory.ROBOT)) {
                        //create dialog
                        createDialog(CustomSkinInventory.ROBOT, CustomSkinInventory.ROBOT_PRICE, "Buy Robot?", CharAssets.robot);
                    } else {
                        //if already owned
                        for (UIObject ui : grid.getUiElements()) {
                            ((GridElementImage) ui).setSelected(false);
                        }
                        isSelected = inventory.setSkin(CustomSkinInventory.ROBOT);
                        handler.getMouseManager().reset();

                        if (lastState instanceof MultiLobbyState multiLobbyState) {
                            multiLobbyState.getPeer().sendUserSkinChange(user.getUsername(), CustomSkinInventory.ROBOT);
                        }
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        skin.setSelected(handler.getSkinInv().getEquippedSkin() == CustomSkinInventory.ROBOT);
        grid.addElement(skin);

        for (UIObject ui : grid.getUiElements()) {
            gridManager.addObject(ui);
        }

    }

    private void createDialog(int skinToUnlock, int price, String prompt, BufferedImage[] images) {
        int dialogX = 100;
        int dialogY = 100;
        int dialogWidth = handler.getWidth() - 200;
        int dialogHeight = handler.getHeight() - 200;
        ArrayList<UIObject> buttons = new ArrayList<>();
        TextButton buyButton = new TextButton(handler,
                dialogX + dialogWidth / 2,
                dialogY + dialogHeight - 100,
                200, 50,
                "Buy", new ClickListener() {
            @Override
            public void onClick(UIObject ui) {
                if (handler.getProgression().useGoldenCoins(price)) {
                    inventory.unlockSkin(skinToUnlock);
                    handler.getMouseManager().reset();
                    dialog.setIsVisible(false);
                }
            }

            @Override
            public void onMouseRelease(MouseEvent e) {
            }
        });
        buttons.add(buyButton);
        TextButton cancelButton = new TextButton(handler,
                dialogX + dialogWidth / 2 - 200,
                dialogY + dialogHeight - 100,
                200, 50,
                "Cancel", new ClickListener() {
            @Override
            public void onClick(UIObject ui) {
                dialog.setIsVisible(false);
            }

            @Override
            public void onMouseRelease(MouseEvent e) {
            }
        });
        buttons.add(cancelButton);

        int width = Math.max(images[0].getWidth(), images[1].getWidth());
        int height = Math.max(images[0].getHeight(), images[1].getHeight());

        BufferedImage compiled = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D tg = compiled.createGraphics();
        tg.drawImage(images[0], 0, 0, null);
        tg.drawImage(images[1], 0, 0, null);
        tg.dispose();

        dialog = new UIDialog(handler, 100, 100, handler.getWidth() - 200, handler.getHeight() - 200,
                prompt, compiled,
                buttons, (element, g) -> {
                    g.setColor(handler.getSettings().getHudColor());
                    g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
                    g.drawString(Integer.toString(price), (int) element.getX() + element.getWidth() / 2, 265);
                    g.drawImage(MenuAssets.coins[0], (int) element.getX() + element.getWidth() / 2 - 55, 230, 50, 50, null);
                });
        dialogManager.addObject(cancelButton);
        dialogManager.addObject(buyButton);
        dialogManager.addObject(dialog);
    }

    @Override
    public void tick() {
        if (dialog != null && dialog.getIsVisible()) {
            handler.getMouseManager().setUIManager(dialogManager);
            dialogManager.tick();
            dialog.tick();
        } else {
            handler.getMouseManager().setUIManager(gridManager);
            gridManager.tick();
            grid.tick();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
        gridManager.render(g);
        grid.render(g);
        //grid.postRender(g);
        gridManager.postRender(g);

        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        g.setColor(handler.getSettings().getHudColor());
        Utils.drawCenteredString(g, "CHARACTERS",
                new Rectangle(handler.getWidth() / 2, 70, 0, 0),
                new Font(Font.DIALOG, Font.PLAIN, 30));
        if (dialog != null && dialog.getIsVisible()) {
            dialogManager.render(g);
            dialogManager.postRender(g);
        }
        g.setColor(handler.getSettings().getLaserColor());
        g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

    }
}

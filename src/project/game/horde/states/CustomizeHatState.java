package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import project.game.horde.graphics.CharAssets;
import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.CustomHatInventory;
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

public class CustomizeHatState extends State {

    private UIManager gridManager, dialogManager;
    private User user;
    private GridContainer grid;
    private CustomHatInventory inventory;
    public UIDialog dialog;

    public CustomizeHatState(Handler handler, User user, State lastState) {
        super(handler);
        dialog = null;
        inventory = handler.getHatInv();
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

        // default
        GridElementImage hat = new GridElementImage(handler, MenuAssets.none, (element, g) -> {
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    for (UIObject ui : grid.getUiElements()) {
                        ((GridElementImage) ui).setSelected(false);
                    }
                    isSelected = inventory.setHat(CustomHatInventory.NONE);
                    handler.getMouseManager().reset();

                    if (lastState instanceof MultiLobbyState multiLobbyState) {
                        multiLobbyState.getPeer().sendUserHatChange(user.getUsername(), CustomHatInventory.NONE);
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        hat.setSelected(handler.getHatInv().getEquippedHat() == CustomHatInventory.NONE);
        grid.addElement(hat);

        // christmas
        hat = new GridElementImage(handler, CharAssets.christmasHat, (element, g) -> {
            if (!handler.getHatInv().checkOwned(CustomHatInventory.CHRISTMAS)) {
                g.setColor(new Color(200, 0, 0, 100));
                g.fillRect((int) element.getX(), (int) element.getY(), element.getWidth(), element.getHeight());
            }
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    //if not owned, prompt to buy
                    if (!inventory.checkOwned(CustomHatInventory.CHRISTMAS)) {
                        //create dialog
                        createDialog(CustomHatInventory.CHRISTMAS, CustomHatInventory.CHRISTMAS_PRICE, "Buy Christmas Hat?", CharAssets.christmasHat);
                    } else {
                        //if already owned
                        for (UIObject ui : grid.getUiElements()) {
                            ((GridElementImage) ui).setSelected(false);
                        }
                        isSelected = inventory.setHat(CustomHatInventory.CHRISTMAS);
                        handler.getMouseManager().reset();

                        if (lastState instanceof MultiLobbyState multiLobbyState) {
                            multiLobbyState.getPeer().sendUserHatChange(user.getUsername(), CustomHatInventory.CHRISTMAS);
                        }
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        hat.setSelected(handler.getHatInv().getEquippedHat() == CustomHatInventory.CHRISTMAS);
        grid.addElement(hat);

        //reindeer
        hat = new GridElementImage(handler, CharAssets.reindeer, (element, g) -> {
            if (!handler.getHatInv().checkOwned(CustomHatInventory.REINDEER)) {
                g.setColor(new Color(200, 0, 0, 100));
                g.fillRect((int) element.getX(), (int) element.getY(), element.getWidth(), element.getHeight());
            }
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    //if not owned, prompt to buy
                    if (!inventory.checkOwned(CustomHatInventory.REINDEER)) {
                        //create dialog
                        createDialog(CustomHatInventory.REINDEER, CustomHatInventory.REINDEER_PRICE, "Buy Reindeer Horns?", CharAssets.reindeer);
                    } else {
                        //if already owned
                        for (UIObject ui : grid.getUiElements()) {
                            ((GridElementImage) ui).setSelected(false);
                        }
                        isSelected = inventory.setHat(CustomHatInventory.REINDEER);
                        handler.getMouseManager().reset();

                        if (lastState instanceof MultiLobbyState multiLobbyState) {
                            multiLobbyState.getPeer().sendUserHatChange(user.getUsername(), CustomHatInventory.REINDEER);
                        }
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e
            ) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        hat.setSelected(handler.getHatInv().getEquippedHat() == CustomHatInventory.REINDEER);
        grid.addElement(hat);

        //bunny
        hat = new GridElementImage(handler, CharAssets.bunny, (element, g) -> {
            if (!handler.getHatInv().checkOwned(CustomHatInventory.BUNNY)) {
                g.setColor(new Color(200, 0, 0, 100));
                g.fillRect((int) element.getX(), (int) element.getY(), element.getWidth(), element.getHeight());
            }
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    //if not owned, prompt to buy
                    if (!inventory.checkOwned(CustomHatInventory.BUNNY)) {
                        //create dialog
                        createDialog(CustomHatInventory.BUNNY, CustomHatInventory.BUNNY_PRICE, "Buy Bunny Ears?", CharAssets.bunny);
                    } else {
                        //if already owned
                        for (UIObject ui : grid.getUiElements()) {
                            ((GridElementImage) ui).setSelected(false);
                        }
                        isSelected = inventory.setHat(CustomHatInventory.BUNNY);
                        handler.getMouseManager().reset();
                        if (lastState instanceof MultiLobbyState multiLobbyState) {
                            multiLobbyState.getPeer().sendUserHatChange(user.getUsername(), CustomHatInventory.BUNNY);
                        }
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e
            ) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };

        hat.setSelected(handler.getHatInv().getEquippedHat() == CustomHatInventory.BUNNY);
        grid.addElement(hat);

        //baseball
        hat = new GridElementImage(handler, CharAssets.baseballCap, (element, g) -> {
            if (!handler.getHatInv().checkOwned(CustomHatInventory.BASEBALL_CAP)) {
                g.setColor(new Color(200, 0, 0, 100));
                g.fillRect((int) element.getX(), (int) element.getY(), element.getWidth(), element.getHeight());
            }
        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    //if not owned, prompt to buy
                    if (!inventory.checkOwned(CustomHatInventory.BASEBALL_CAP)) {
                        //create dialog
                        createDialog(CustomHatInventory.BASEBALL_CAP, CustomHatInventory.BASEBALL_CAP_PRICE, "Buy Baseball Cap?", CharAssets.baseballCap);
                    } else {
                        //if already owned
                        for (UIObject ui : grid.getUiElements()) {
                            ((GridElementImage) ui).setSelected(false);
                        }
                        isSelected = inventory.setHat(CustomHatInventory.BASEBALL_CAP);
                        handler.getMouseManager().reset();

                        if (lastState instanceof MultiLobbyState multiLobbyState) {
                            multiLobbyState.getPeer().sendUserHatChange(user.getUsername(), CustomHatInventory.BASEBALL_CAP);
                        }
                    }
                }
            }

            @Override
            public void onMouseMove(MouseEvent e
            ) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        hat.setSelected(handler.getHatInv().getEquippedHat() == CustomHatInventory.BASEBALL_CAP);
        grid.addElement(hat);

        for (UIObject ui : grid.getUiElements()) {
            gridManager.addObject(ui);
        }

    }

    private void createDialog(int hatToUnlock, int price, String prompt, BufferedImage image) {
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
                    inventory.unlockHat(hatToUnlock);
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
        dialog = new UIDialog(handler, 100, 100, handler.getWidth() - 200, handler.getHeight() - 200,
                prompt, image,
                buttons,
                //biconsumer, function get passed into postRender() function
                //element is the this UIObject that its within (dialog), in order to access its values
                (element, g) -> {
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
        grid.postRender(g);
        gridManager.postRender(g);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
        g.setColor(handler.getSettings().getHudColor());
        Utils.drawCenteredString(g, "HATS",
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

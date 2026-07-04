package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import project.game.horde.graphics.CharAssets;
import project.game.horde.main.CustomSkinInventory;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.GridContainer;
import project.game.horde.ui.GridElementImage;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class CustomizeSkinState extends State {

    private UIManager uiManager;
    private User user;
    private GridContainer grid;
    private CustomSkinInventory inventory;

    public CustomizeSkinState(Handler handler, User user, State lastState) {
        super(handler);
        inventory = handler.getSkinInv();
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
        GridElementImage skin = new GridElementImage(handler, CharAssets.harry) {
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
        skin = new GridElementImage(handler, CharAssets.blueAlien) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
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

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        skin.setSelected(handler.getSkinInv().getEquippedSkin() == CustomSkinInventory.BLUE_ALIEN);
        grid.addElement(skin);

        // robot
        skin = new GridElementImage(handler, CharAssets.robot) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
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

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        skin.setSelected(handler.getSkinInv().getEquippedSkin() == CustomSkinInventory.ROBOT);
        grid.addElement(skin);

        for (UIObject ui : grid.getUiElements()) {
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
        Utils.drawCenteredString(g, "CHARACTERS",
                new Rectangle(handler.getWidth() / 2, 70, 0, 0),
                new Font(Font.DIALOG, Font.PLAIN, 30));
        grid.render(g);
        g.setColor(handler.getSettings().getLaserColor());
        g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

    }
}

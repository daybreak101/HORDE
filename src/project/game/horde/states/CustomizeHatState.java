package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import project.game.horde.graphics.CharAssets;
import project.game.horde.main.CustomHatInventory;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.GridContainer;
import project.game.horde.ui.GridElementImage;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class CustomizeHatState extends State {

    private UIManager uiManager;
    private User user;
    private GridContainer grid;
    private CustomHatInventory inventory;

    public CustomizeHatState(Handler handler, User user, State lastState) {
        super(handler);
        inventory = handler.getHatInv();
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

        // default
        GridElementImage hat = new GridElementImage(handler, (BufferedImage) null) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    for (UIObject ui : grid.getUiElements()) {
                        ((GridElementImage) ui).setSelected(false);
                    }
                    isSelected = inventory.setHat(CustomHatInventory.NONE);
                    handler.getMouseManager().reset();
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
        hat = new GridElementImage(handler, CharAssets.christmasHat) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    for (UIObject ui : grid.getUiElements()) {
                        ((GridElementImage) ui).setSelected(false);
                    }
                    isSelected = inventory.setHat(CustomHatInventory.CHRISTMAS);
                    handler.getMouseManager().reset();
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
        hat = new GridElementImage(handler, CharAssets.reindeer) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    for (UIObject ui : grid.getUiElements()) {
                        ((GridElementImage) ui).setSelected(false);
                    }
                    isSelected = inventory.setHat(CustomHatInventory.REINDEER);
                    handler.getMouseManager().reset();
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        hat.setSelected(handler.getHatInv().getEquippedHat() == CustomHatInventory.REINDEER);
        grid.addElement(hat);

        //bunny
        hat = new GridElementImage(handler, CharAssets.bunny) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    for (UIObject ui : grid.getUiElements()) {
                        ((GridElementImage) ui).setSelected(false);
                    }
                    isSelected = inventory.setHat(CustomHatInventory.BUNNY);
                    handler.getMouseManager().reset();
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        hat.setSelected(handler.getHatInv().getEquippedHat() == CustomHatInventory.BUNNY);
        grid.addElement(hat);

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

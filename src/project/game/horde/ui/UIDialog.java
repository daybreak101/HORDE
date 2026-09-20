package project.game.horde.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.BiConsumer;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class UIDialog extends UIObject {

    private String prompt;
    private BufferedImage image;
    private ArrayList<UIObject> uiObjects;

    public UIDialog(Handler handler, float x, float y,
            int width, int height,
            String prompt, BufferedImage image,
            ArrayList<UIObject> uiObjects) {
        super(handler, x, y, width, height, null);
        this.prompt = prompt;
        this.image = image;
        this.uiObjects = uiObjects;
    }

    public UIDialog(Handler handler, float x, float y,
            int width, int height,
            String prompt, BufferedImage image,
            ArrayList<UIObject> uiObjects, BiConsumer<UIObject,Graphics> postRenderAction) {
        super(handler, x, y, width, height, postRenderAction);
        this.prompt = prompt;
        this.image = image;
        this.uiObjects = uiObjects;
    }

    @Override
    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
        for (UIObject ui : uiObjects) {
            ui.setIsVisible(isVisible);
        }
    }

    @Override
    public void render(Graphics g) {
        if (isVisible) {
            //transparency background
            g.setColor(new Color(0, 0, 0, 200));
            g.fillRect((int) x, (int) y, handler.getWidth(), handler.getHeight());

            //draw div
            g.setColor(Color.black);
            g.fillRect((int) x, (int) y, width, height);
            g.setColor(handler.getSettings().getHudColor());
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.drawRect((int) x, (int) y, width, height);
            g2.setStroke(new BasicStroke(1));

            //draw heading, images and text
            g.setColor(handler.getSettings().getHudColor());
            Utils.drawCenteredString(g, prompt,
                    new Rectangle(handler.getWidth() / 2, handler.getHeight() / 4, 0, 0),
                    new Font(Font.DIALOG, Font.PLAIN, 30));
            g.drawImage(image,
                    (int) (x + width / 2 - 100),
                    (int) (y + height / 2 - 100),
                    200, 200,
                    null);

            //draw buttons
            for (UIObject ui : uiObjects) {
                ui.render(g);
            }

        }
    }

    @Override
    public void onClick(UIObject ui) {
    }

    @Override
    protected Object getInfo() {
        return null;
    }

    @Override
    public void tick() {
        for (UIObject ui : uiObjects) {
            ui.tick();
        }
    }

}

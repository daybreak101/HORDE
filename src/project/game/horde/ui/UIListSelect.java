package project.game.horde.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import project.game.horde.main.Handler;
import project.game.horde.utils.Utils;

public class UIListSelect extends UIObject {

    protected float x, y;
    protected int width, height;
    private ArrayList<String> options;
    protected int currentSelection = 0;
    private TextButton goLeft;
    private TextButton goRight;
    private Rectangle stringBound;

    public UIListSelect(Handler handler, UIManager ui, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        options = new ArrayList<>();
        goLeft = new TextButton(handler, x, y, width / 10, height, " < ", new Font(Font.DIALOG, Font.BOLD, 20), new ClickListener() {

            @Override
            public void onClick(UIObject ui) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMouseRelease(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    currentSelection--;
                    if (currentSelection < 0) {
                        currentSelection = options.size() - 1;
                    }
                    handleSelection();
                    handler.getMouseManager().reset();
                }
            }
        };
        goRight = new TextButton(handler, x + width - width / 10, y, width / 10, height, " > ", new Font(Font.DIALOG, Font.BOLD, 20), new ClickListener() {

            @Override
            public void onClick(UIObject ui) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onMouseRelease(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        }) {
            @Override
            public void onMouseRelease(MouseEvent e) {
                if (hovering) {
                    currentSelection++;
                    if (currentSelection > options.size() - 1) {
                        currentSelection = 0;
                    }
                    handleSelection();
                    handler.getMouseManager().reset();
                }
            }

            @Override
            public void onMouseMove(MouseEvent e) {
                hovering = bounds.contains(e.getX(), e.getY());

            }
        };
        ui.addObject(goLeft);
        ui.addObject(goRight);
        stringBound = new Rectangle((int) (x + width / 10), (int) y, (int) (8 * width / 10), height);
    }

    public void tick() {
        goLeft.tick();
        goRight.tick();

    }

    // must override
    public void handleSelection() {

    }

    public void render(Graphics g) {
        if (options.size() > 0) {
            goLeft.render(g);
            Utils.drawCenteredString(g, options.get(currentSelection), stringBound,
                    new Font(Font.DIALOG, Font.PLAIN, 30));
            goRight.render(g);
//			g.setColor(new Color(255, 0, 0, 150));
//			g.fillRect((int) goLeft.x, (int) goLeft.y, goLeft.width, goLeft.height);
//			g.setColor(new Color(0, 0, 255, 150));
//			g.fillRect((int) stringBound.x, (int) stringBound.y, stringBound.width, stringBound.height);
//			g.setColor(new Color(0, 255, 0, 150));
//			g.fillRect((int) goRight.x, (int) goRight.y, goRight.width, goRight.height);

        }
    }

    public void addOption(String o) {
        options.add(o);
    }

    public void setCurrentSelection(int i) {
        currentSelection = i;
    }

    public int getCurrentSelection() {
        return currentSelection;
    }

    @Override
    public void onClick(UIObject ui) {
        // TODO Auto-generated method stub

    }

    public void onMouseRelease(MouseEvent e) {

    }

    public void setX(float x) {
        this.x = x;
        goLeft.setX(x);
        goRight.setX(x + width - width / 10);
        stringBound.x = (int) (x + width / 10);
    }

    @Override
    protected Object getInfo() {
        // TODO Auto-generated method stub
        return null;
    }
}

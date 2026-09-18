package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class UsernameState extends State implements KeyListener {

    private UIManager uiManager;
    private User user;
    private String username;

    public UsernameState(Handler handler, User user) {
        super(handler);
        this.user = user;
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        handler.getGame().getDisplay().getCanvas().addKeyListener(this);
        handler.getGame().getDisplay().getCanvas().setFocusable(true);
        handler.getGame().getDisplay().getCanvas().requestFocus();

        int width = 300;
        int x = handler.getWidth() / 2 - width / 2;

        uiManager.addObject(new TextButton(handler, x, 500, width, 100, "Enter", 50, new ClickListener() {

            @Override
            public void onClick(UIObject ui) {
                //handler.getGlobalStats().addGame();
                handler.getMouseManager().setUIManager(null);
                //handler.getGame().gameState = new GameState(handler);
                if (username != null && username.length() > 0) {
                    user.setUsername(username);
                }
                handler.getGame().getDisplay().getCanvas().removeKeyListener(UsernameState.this);
                State.setState(new MenuState(handler, user));
            }

            @Override
            public void onMouseRelease(MouseEvent e) {

            }

        }));

    }

    @Override
    public void tick() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, handler.getWidth(), handler.getHeight());

        g.setColor(handler.getSettings().getHudColor());
        Utils.drawCenteredString(g, "HORDE",
                new Rectangle(handler.getWidth() / 2, 100, 0, 0),
                new Font(Font.DIALOG, Font.PLAIN, 70));
        Utils.drawCenteredString(g, "Enter Username",
                new Rectangle(handler.getWidth() / 2, 300, 0, 0),
                new Font(Font.DIALOG, Font.PLAIN, 50));
        g.setColor(Color.white);
        Utils.drawCenteredString(g, username == null ? "" : username,
                new Rectangle(handler.getWidth() / 2, 400, 0, 0),
                new Font(Font.DIALOG, Font.PLAIN, 40));
        uiManager.render(g);
        g.setColor(handler.getSettings().getLaserColor());
        g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();

        if (!Character.isLetterOrDigit(c) && c != '_') {
            return;
        }

        // Maximum username length
        if (username == null) {
            username = "";
        }

        if (username.length() < 16) {
            username += c;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (username == null) {
            username = "";
        }

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !username.isEmpty()) {
            username = username.substring(0, username.length() - 1);
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER && !username.isEmpty()) {
            user.setUsername(username);
            handler.getMouseManager().setUIManager(null);
            handler.getGame().getDisplay().getCanvas().removeKeyListener(this);
            State.setState(new MenuState(handler, user));
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER && username.isEmpty()) {
            handler.getMouseManager().setUIManager(null);
            handler.getGame().getDisplay().getCanvas().removeKeyListener(this);
            State.setState(new MenuState(handler, user));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}

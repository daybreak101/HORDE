package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import project.game.horde.hud.LeaderboardSpot;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.ui.ClickListener;
import project.game.horde.ui.TextButton;
import project.game.horde.ui.UIManager;
import project.game.horde.ui.UIObject;
import project.game.horde.utils.Utils;

public class LeaderboardState extends State {

    private UIManager uiManager;
    private State lastState;
    ArrayList<LeaderboardSpot> spots;

    public LeaderboardState(Handler handler, User user, State lastState) {
        super(handler);
        spots = handler.getLeaderboard().getSpots();
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUIManager(uiManager);
        this.lastState = lastState;
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
    }

    int i = 0;

    @Override
    public void tick() {
        handler.getMouseManager().setUIManager(uiManager);

        uiManager.tick();
        i++;
        if (i == 24) {
            i = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
        uiManager.render(g);

        g.setColor(handler.getSettings().getHudColor());
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));

        int x = handler.getWidth() / 2;
        int y = 150;
        Utils.drawCenteredString(g, "Leaderboard",
                new Rectangle(x, y - 100, 0, 0), new Font(Font.DIALOG, Font.PLAIN, 50));

        //change font size
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));

        //draw category names
        g.drawString("Name", 200, y - 5);
        g.drawString("Round", 795, y- 5);

        //draw line separator
        g.fillRect(100, y, 800, 2);
        //display leaderboard data
        for (int i = 0; i < (spots.size()); i++) {
            if (i == 10) {
                break;
            }
            g.drawString(spots.get(i).name, (int) 200, (int) y + ((i + 1) * 40));
            g.drawString(Integer.toString(spots.get(i).round), (int) 800, (int) y + ((i + 1) * 40));
        }


        g.setColor(handler.getSettings().getLaserColor());
        g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);

    }

}

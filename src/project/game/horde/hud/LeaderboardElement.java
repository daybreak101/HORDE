package project.game.horde.hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.sounds.Sounds;
import project.game.horde.states.LobbyState;
import project.game.horde.states.State;

public class LeaderboardElement extends HudElement {

    ArrayList<LeaderboardSpot> spots;

    int newGameTicker = 0, newGameCountdown = 600;
    private Player player;
    private User user;

    public LeaderboardElement(Handler handler, Player player, User user) {
        super(400, 100, 0, 0, handler);
        Sounds.stopClip("backgroundMusic");
        this.user = user;
        this.player = player;
        spots = handler.getLeaderboard().getSpots();
        handler.getGlobalStats().writeToFile();
        checkIfTop10();
    }

    private void checkIfTop10() {
        int kills = handler.getCurrentPlayer().getStats().getKills();
        int downs = handler.getCurrentPlayer().getStats().getDowns();
        int headshots = handler.getCurrentPlayer().getStats().getHeadshots();
        handler.getLeaderboard().addLeaderboard(
                handler.getRoundLogic().getCurrentRound(), kills, headshots, downs);
    }

    @Override
    public void tick() {

        if (newGameTicker > newGameCountdown - 255) {
            transparency++;
        }
        newGameTicker++;
        if (newGameTicker >= newGameCountdown) {
            handler.getGlobalStats().addGame();
            handler.getGlobalStats().writeToFile();
            handler.getMouseManager().setUIManager(null);

            if (handler.getCurrentPlayer().getPeer() != null) {
                State.setState(handler.getCurrentPlayer().getPeer().getLobby());
                handler.getCurrentPlayer().getPeer().getLobby().endGame();
            } else {
                State.setState(new LobbyState(handler, user));
            }
            //Sounds.shutdownThreadPool();
            return;
        }
    }

    int transparency = 0;

    @Override
    public void render(Graphics g) {
        g.setColor(handler.getSettings().getHudColor());
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));

        g.drawString("Leaderboard", (int) x, (int) y);

        for (int i = 0; i < (spots.size()); i++) {
            if (i == 10) {
                break;
            }
            g.drawString(spots.get(i).name, (int) x - 70, (int) y + ((i + 1) * 40));
            g.drawString(Integer.toString(spots.get(i).round), (int) x + 250, (int) y + ((i + 1) * 40));
        }

        if (newGameTicker > newGameCountdown - 255) {

            Color color = new Color(0, 0, 0, transparency);
            //transparency++;

            g.setColor(color);
            g.fillRect(0, 0, handler.getWidth(), handler.getHeight());

        }

    }

}

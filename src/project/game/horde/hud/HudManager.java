package project.game.horde.hud;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import project.game.horde.entities.creatures.Player;
import project.game.horde.main.Handler;

public class HudManager {

    private Handler handler;
    private ArrayList<HudElement> elements;
    private GameplayElement gameplayHUD;
    private Scoreboard scoreboard;
    private Player player;
    private Queue<HudElement> notifQueue = new LinkedList<>();
    private ArrayList<HudElement> inWorldElements = new ArrayList<>();
	private HudElement currentNotif;

    public HudManager(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        scoreboard = new Scoreboard(handler, player);
        scoreboard.isVisible = false;
        gameplayHUD = new GameplayElement(handler, player);
        gameplayHUD.isVisible = true;
        elements = new ArrayList<>();
    }

    public void tick() {
        if (scoreboard.isVisible) {
            scoreboard.tick();
        }
        if (gameplayHUD.isVisible) {
            gameplayHUD.tick();
        }
        for (int i = 0; i < elements.size(); i++) {
            HudElement e = elements.get(i);
            e.tick();
            if (!e.isActive) {
                elements.remove(e);
            }
        }
        if (currentNotif == null) {
            currentNotif = notifQueue.poll();

        }
        if (currentNotif != null) {
            currentNotif.isVisible = true;
            currentNotif.tick();
            if (!currentNotif.isActive) {
                currentNotif = null;
            }
        }
        for (int i = 0; i < inWorldElements.size(); i++) {
            HudElement e = inWorldElements.get(i);
            e.tick();
            if (!e.isActive) {
                inWorldElements.remove(e);
            }
        }
    }

    public void render(Graphics g) {
        for (HudElement o : elements) {
            if (o.isVisible) {
                o.render(g);
            }
        }
        if (scoreboard.isVisible) {
            scoreboard.render(g);
        }
        //System.out.println("Tick: " + gameplayHUD.isVisible);

        if (gameplayHUD.isVisible) {
            gameplayHUD.render(g);
        }
        if (currentNotif != null) {
            currentNotif.render(g);
        }
    }

    public void renderInWorldHud(Graphics g) {
        for (HudElement o : inWorldElements) {
            if (o.isVisible) {
                o.render(g);
            }
        }
    }

    public void addNotifToQueue(HudElement e) {
        notifQueue.add(e);
    }

    public void setInvisible() {
        scoreboard.isVisible = false;
        gameplayHUD.isVisible = false;
        for (HudElement o : elements) {
            o.isVisible = false;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ArrayList<HudElement> getObjects() {
        return elements;
    }

    public void addObject(HudElement o) {
        elements.add(o);
    }

    public void removeObject(HudElement o) {
        elements.remove(o);
    }

    public GameplayElement getGameplayHUD() {
        return gameplayHUD;
    }

    public void setGameplayHUD(GameplayElement gameplayHUD) {
        this.gameplayHUD = gameplayHUD;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void addInWorldElement(HudElement e) {
        inWorldElements.add(e);
    }
}

package project.game.horde.states;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.HashMap;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.main.Cheats;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.network.Peer;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.Music;
import project.game.horde.sounds.Sounds;
import project.game.horde.worlds.World;
import project.game.horde.worlds.World.PlayerPosition;

public class GameState extends State {

    private World world;
    private Cheats cheats;
    private Peer peer;
    private HashMap<Integer, User> users;
//	private Handler handler;

    //offline
    public GameState(Handler handler, String map, User user) throws IOException {
        super(handler);
        handler.getGame().resetManagers();
        world = new World(handler, map,
                "/worlds/" + map + "/world1.txt",
                "/worlds/" + map + "/entities.json",
                "/worlds/" + map + "/edges.txt",
                "/worlds/" + map + "/adjacentRooms.txt",
                user
        );

        // initialize player
        PlayerPosition pos = world.getPlayerPositions().pop();
        world.getEntityManager().addCurrentPlayer(
                new Player(handler, pos.x, pos.y, user));

        cheats = new Cheats(handler);
        Sounds.playClip(Sounds.BACKGROUND_MUSIC_ID, 1, .7f, true);

    }

    //online constructor
    public GameState(Handler handler, String map, User localUser, Peer peer,
            HashMap<Integer, User> users) throws IOException {
        super(handler);
        handler.getGame().resetManagers();
        world = new World(handler, map,
                "/worlds/" + map + "/world1.txt",
                "/worlds/" + map + "/entities.json",
                "/worlds/" + map + "/edges.txt",
                "/worlds/" + map + "/adjacentRooms.txt",
                localUser, peer, users
        );
        //online stuff
        this.peer = peer;
        this.users = users;

        int number = 0;

        // initialize players and their spawn positions
        System.out.println(localUser.getUsername());
        for (User user : users.values()) {
            PlayerPosition pos = world.getPlayerPositions().pop();
            if (!user.getUsername().equals(localUser.getUsername())) {
                world.getEntityManager().addOtherPlayer(
                        new PlayerMP(handler, pos.x, pos.y, user));
                System.out.println("current: " + user.getUsername()
                        + " local: " + localUser.getUsername()
                        + " / added as otherPlayer");
            } else {
                world.getEntityManager().addCurrentPlayer(
                        new Player(handler, pos.x, pos.y, peer));
                System.out.println("current: " + user.getUsername()
                        + " local: " + localUser.getUsername()
                        + " / added as currentPlayer");
            }
            number++;
        }

        cheats = new Cheats(handler);
        Sounds.playClip(Sounds.BACKGROUND_MUSIC_ID, 1, .7f, true);
    }

    boolean outroPlaying = false;

    @Override
    public void tick() {
        if (world != null) {
            if (State.getState() != this) {
                handler.setWorld(null);
                world = null;
                return;
            }
        }

        world.tick();
        boolean oneAlive = false;
        if (!outroPlaying) {
            for (PlayerMP p : world.getEntityManager().getOtherPlayers()) {
                if (p.getHealth() > 0) {
                    oneAlive = true;
                }
            }
            if (handler.getCurrentPlayer().getHealth() > 0) {
                oneAlive = true;
            }

            if (!oneAlive) {
                outroPlaying = true;
                //Sounds.playClip(Music.farmOutro, 1, "outro", 1, false);
                Sounds.playClip(Music.FARM_OUTRO_ID, 1, 0.9f, false);
            }

        }
        cheats.tick();
        if (handler.getCurrentPlayer().getHealth() <= 0) {
            Sounds.stopClip(GunSounds.FLAMETHROWER_SHOT_ID);
        }
    }

    int transparency = 255;

    @Override
    public void render(Graphics g) {
        //double zoomLevel = handler.getSettings().getZoomLevel();
        //Graphics2D g2d = (Graphics2D) g;
        //AffineTransform old = g2d.getTransform();

        //old.scale(zoomLevel, zoomLevel);
        //g2d.setTransform(old);
        /////////////////////////////////////////////////////////////
		
		world.render(g);

        //old.scale(1/zoomLevel, 1/zoomLevel);
        //g2d.setTransform(old);
//		hud.render(g);
        if (transparency > 0) {

            Color color = new Color(0, 0, 0, transparency);
            transparency--;

            g.setColor(color);
            g.fillRect(0, 0, handler.getWidth(), handler.getHeight());

        }
    }

    public World getWorld() {
        return world;
    }

    public Handler getHandler() {
        return handler;
    }

}

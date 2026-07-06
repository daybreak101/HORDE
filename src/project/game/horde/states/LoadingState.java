package project.game.horde.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import project.game.horde.graphics.Assets;
import project.game.horde.graphics.MenuAssets;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.network.Peer;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Utils;

public class LoadingState extends State {

    public String map = "";
    public boolean soundsReady = false, assetsReady = false;
    public final int loadingSounds = 0, loadingAssets = 1;
    public int loadingState;
    public String currentLoad;
    public User localUser;
    public boolean started = false;
    ExecutorService executorSounds, executorAssets, executorReady;
    public Peer peer;
    public HashMap<Integer, User> users;

    // offline
    public LoadingState(Handler handler, User localUser, String map) {
        super(handler);
        this.localUser = localUser;
        handler.getGame().resetManagers();
        loadingState = 0;
        currentLoad = "";
        // Additional initialization code...
        this.map = map;
    }

    // online constructor
    public LoadingState(Handler handler, User localUser, Peer peer, HashMap<Integer, User> users, String map) {
        super(handler);
        this.localUser = localUser;
        this.users = users;
        this.peer = peer;
        handler.getGame().resetManagers();
        loadingState = 0;
        currentLoad = "";
        // Additional initialization code...
        this.map = map;
    }

    boolean printAssetsReady = false;
    boolean printSoundsReady = false;
    @Override
    public void tick() {
        if (!started) {
            if (peer != null && peer.isServer()) {
                peer.gameAlreadyStarted = true;
            }
            // Initialize assets in a new thread using ExecutorService
            executorSounds = Executors.newSingleThreadExecutor();
            executorSounds.submit(() -> {
                Sounds.init(handler);
                loadingState++;
                soundsReady = true;
              
            });
            executorAssets = Executors.newSingleThreadExecutor();
            executorAssets.submit(() -> {
                if (map.equals("test")) {
                    Assets.loadFarm();
                }
                loadingState++;
                assetsReady = true;
            });
            executorReady = Executors.newSingleThreadExecutor();
            executorReady.submit(() -> {
                while(!assetsReady || !soundsReady) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                peer.sendReady(localUser.getUsername());
            });
            started = true;

        }

        switch (loadingState) {
            case loadingSounds ->
                currentLoad = "Loading sounds...";
            case loadingAssets ->
                currentLoad = "Loading assets...";
            default -> {
            }
        }

        if (assetsReady && soundsReady) {
            System.out.println("Shutting down executors...");
            executorReady.shutdown(); // Shutdown the executor after tasks are completed
            executorAssets.shutdown(); // Shutdown the executor after tasks are completed
            executorSounds.shutdown(); // Shutdown the executor after tasks are completed
            System.out.println("Executors shut down");
            if (peer == null) {
                currentLoad = "Loading game...";
                handler.getGlobalStats().addGame();
                handler.getMouseManager().setUIManager(null);
                try {
                    System.out.println("Loading game...");
                    handler.getGame().gameState = new GameState(handler, map, localUser);
                    System.out.println("Loaded game...");
                } catch (IOException e) {
                }
                State.setState(handler.getGame().gameState);
            } else {
                currentLoad = "Waiting for other players...";
                boolean onePlayerNotReady = false;
                for (Map.Entry<Integer, User> entry : users.entrySet()) {
                    if (!entry.getValue().getReady() && !entry.getValue().getUsername().equals(localUser.getUsername())) {
                        onePlayerNotReady = true;
                        break;
                    }

                }

                if (peer.getLobby().gameAlreadyStarted || (!onePlayerNotReady && peer.isServer())) {
                    try {
                        peer.getLobby().startGame(map);
                    } catch (IOException e) {
                    }
                }
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, handler.getWidth(), handler.getHeight());
        if (map.equals("test")) {
            g.drawImage(MenuAssets.farmhouseLoading, 0, 50, handler.getWidth(), handler.getHeight() - 100, null);
        }
        g.setColor(handler.getSettings().getLaserColor());
        Utils.drawCenteredString(g, currentLoad, new Rectangle(handler.getWidth() / 2 - 200, handler.getHeight() - 200, 400, 100), new Font(Font.DIALOG, Font.PLAIN, 30));
        //g.drawString(currentLoad, handler.getWidth() / 2, handler.getHeight() / 2);

        //g.setColor(handler.getSettings().getLaserColor());
        //g.fillRect(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY(), 8, 8);
    }
}

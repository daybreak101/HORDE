package project.game.horde.network;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.entities.powerups.PowerUps;
import project.game.horde.hud.PlayerConnectNotification;
import project.game.horde.main.User;
import project.game.horde.sounds.Sounds;
import project.game.horde.states.GameState;
import project.game.horde.states.LoadingState;
import project.game.horde.states.MultiLobbyState;

public class Peer {

    private Server server;
    private Client client;
    private boolean isServer;
    public boolean gameAlreadyStarted = false;
    private HashMap<Integer, User> users;
    private MultiLobbyState multiLobbyState;
    private LoadingState loadingState;
    private GameState gameState;
    private User localUser;
    private final int KEEP_ALIVE_INTERVAL = 5000;
    private Timer keepAliveTimer;
    private String selectedMap = "test";

    public Peer(MultiLobbyState multiLobbyState, User localUser, boolean isServer) {
        this.multiLobbyState = multiLobbyState;
        this.localUser = localUser;
        this.isServer = isServer;
        this.users = new HashMap<>();

        if (isServer) {
            server = new Server();
            Kryo kryo = server.getKryo();
            initKryo(kryo);
            server.addListener(new Listener() {
                public void received(Connection connection, Object object) {
                    if (object instanceof Message) {
                        handleReceivedMessage(connection, (Message) object);
                    }
                }

                public void connected(Connection connection) {
                    handleNewConnection(connection);
                    if (gameState != null) {
                        gameState.getWorld().getEntityManager().getCurrentPlayer().getHud().addObject(
                                new PlayerConnectNotification(gameState.getHandler(), localUser.getUsername(), true));
                    }
                }

                public void disconnected(Connection connection) {
                    handleDisconnectedConnection(connection);
                    if (gameState != null) {
                        gameState.getWorld().getEntityManager().getCurrentPlayer().getHud().addObject(
                                new PlayerConnectNotification(gameState.getHandler(), localUser.getUsername(), false));
                    }
                }
            });
        } else {
            client = new Client();
            Kryo kryo = client.getKryo();
            initKryo(kryo);
            client.addListener(new Listener() {

                public void received(Connection connection, Object object) {
                    if (object instanceof Message) {
                        // System.out.println("client listener received");
                        handleReceivedMessage(connection, (Message) object);
                    }
                }

                public void connected(Connection connection) {
                    System.out.println("client listener connected");
                    localUser.setConnection(connection);
                    connection.sendTCP(new Message(Message.USER_JOIN, connection.getID(), localUser.getUsername(), null));
                }

                public void disconnected(Connection connection) {
                    System.out.println("client listener disconnected");
                    users.remove(connection.getID());
                    client.sendTCP(new Message(Message.USER_LEAVE, connection.getID(), localUser.getUsername(), null));
//					if(gameState != null) {
//						gameState.getWorld().getEntityManager().getCurrentPlayer().getHud().addObject(
//								new PlayerConnectNotification(gameState.getHandler(), localUser.getUsername(), false));
//					}
                }

            });
        }
        keepAliveTimer = new Timer(KEEP_ALIVE_INTERVAL, e -> sendKeepAlive());
        keepAliveTimer.start();
    }

    private void initKryo(Kryo kryo) {
        kryo.register(Message.class);
        kryo.register(User.class);
        kryo.register(HashMap.class);
        kryo.register(ArrayList.class);
        kryo.register(ZombiePosition.class);
    }

    private void handleReceivedMessage(Connection connection, Message message) {
        switch (message.type) {
            case Message.KEEP_ALIVE -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                handleKeepAlive(message);
            }
            case Message.USER_JOIN -> {
                System.out.println("handled join");
                User newUser = new User(connection, message.username);
                users.put(connection.getID(), newUser);
                multiLobbyState.addUser(newUser);
                if (isServer) {
                    broadcastUserList();
                }
                if (gameState != null) {
                    gameState.getWorld().getEntityManager().getCurrentPlayer().getHud().addObject(
                            new PlayerConnectNotification(gameState.getHandler(), localUser.getUsername(), true));
                }
            }
            case Message.USER_LEAVE -> {
                if (gameState != null) {
                    gameState.getWorld().getEntityManager().getOtherPlayers()
                            .remove(gameState.getWorld().getEntityManager().getSpecificPlayer(message.username));
                }

                users.remove(message.connectionId);
                multiLobbyState.removeUser(message.connectionId);
                if (gameState != null) {
                    gameState.getWorld().getEntityManager().getCurrentPlayer().getHud().addObject(
                            new PlayerConnectNotification(gameState.getHandler(), localUser.getUsername(), false));
                }
            }
            case Message.USER_LIST -> {
                users.clear();
                users.putAll(message.userList);
                multiLobbyState.getUsers().clear();
                for (Map.Entry<Integer, User> entry : message.userList.entrySet()) {
                    User user = entry.getValue();
                    // Set a new connection if it's null
                    if (user.getConnection() == null) {
                        user.setConnection(new Connection() {
                            @Override
                            public int getID() {
                                return entry.getKey();
                            }
                        });
                    }
                    users.put(entry.getKey(), user);
                    multiLobbyState.addUser(user);
                }
            }
            case Message.USER_X_MOVE -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_X_MOVE, message.username, message.message));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username)
                            .setX(Float.parseFloat(message.message));

                };
            }
            case Message.USER_Y_MOVE -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_Y_MOVE, message.username, message.message));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username)
                            .setY(Float.parseFloat(message.message));

                };
            }
            case Message.USER_ROTATE -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_ROTATE, message.username, message.message));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username)
                            .setAngle(Float.parseFloat(message.message));

                };
            }
            case Message.USER_SHOT -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_SHOT, message.username, null));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).shootBullet();
                }
            }
            case Message.ZOMBIE_POSITIONS -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && !isServer) {
                    for (ZombiePosition zp : message.zombiePositions) {
                        if (gameState.getWorld().getEntityManager().getZombieById(zp.zombieID) != null) {
                            gameState.getWorld().getEntityManager().getZombieById(zp.zombieID).setX(zp.positionX);
                            gameState.getWorld().getEntityManager().getZombieById(zp.zombieID).setY(zp.positionY);
                        }
                    }
                }
            }
            case Message.ZOMBIE_SPAWN -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState == null) {
                    break;
                }
                String[] info = message.message.split(":");
                gameState.getWorld().getEntityManager().addZombieForClient(info);
            }
            case Message.ZOMBIE_ATTACKED -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && gameState.getWorld().getEntityManager().getZombieById(message.id) != null) {
                    gameState.getWorld().getEntityManager().getZombieById(Integer.parseInt(message.message)).dontMove();
                }
            }
            case Message.USER_DAMAGED_ZOMBIE -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && gameState.getWorld().getEntityManager().getZombieById(message.id) != null) {
                    gameState.getWorld().getEntityManager().getZombieById(message.id).takeOnlineDamage(message.amount);
                }
            }
            case Message.NEW_ROUND -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (!isServer && gameState != null) {
                    gameState.getWorld().getRoundLogic().setCurrentRound(message.round);
                    gameState.getWorld().getRoundLogic().setZombiesLeft(message.zombiesLeft);
                    gameState.getWorld().getEntityManager().getCurrentPlayer().setHealth();
                    gameState.getWorld().getEntityManager().getCurrentPlayer().getInv().roundReplenishGrenades();
                    Sounds.resetSounds();
                }
            }
            case Message.USER_NEW_HEALTH -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && !localUser.getUsername().equals(message.username) && gameState != null) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username)
                            .setHealth(Integer.parseInt(message.message));
                }
            }
            case Message.USER_TOOK_DAMAGE -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && !localUser.getUsername().equals(message.username) && gameState != null) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).justTookDamage();
                }
            }
            case Message.USER_REFILL_HEALTH -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && !isServer) {
                    gameState.getWorld().getEntityManager().getCurrentPlayer().setHealth();
                }
            }
            case Message.SPAWN_POWERUP -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && !isServer) {
                    gameState.getWorld().getEntityManager().addPowerUpForClient(message);
                }
            }
            case Message.USER_SPAWNED_POWERUP -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && isServer) {
                    switch (message.powerup) {
                        case "doublePoints" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnDoublePoints(message.x, message.y);
                        case "nuke" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnNuke(message.x, message.y);
                        case "maxAmmo" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnMaxAmmo(message.x, message.y);
                        case "infiniteAmmo" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnInfiniteAmmo(message.x, message.y);
                        case "instakill" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnInstaKill(message.x, message.y);
                        case "healthUp" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnHealthUp(message.x, message.y);
                        case "deathMachine" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnDeathMachine(message.x, message.y);
                        case "perkBag" ->
                            gameState.getWorld().getRoundLogic().getPowerups().forceSpawnPerkBag(message.x, message.y);
                    }
                }
            }
            case Message.USER_PICKED_POWERUP -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState == null) {
                    break;
                }
                String username = message.powerup;
                for (PowerUps p : gameState.getWorld().getEntityManager().getPowerups()) {
                    if (p.getID() == message.id) {
                        p.setPickedUp(true, username);
                    }
                }
                if (isServer) {
                    pickedUpPowerup(username, message.id);
                }
            }
            case Message.USER_SWITCHED_WEAPON -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_SWITCHED_WEAPON, message.username, message.message));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).getOnlineGun()
                            .switchWeapon(message.message);
                }
            }
            case Message.ZOMBIE_TURN_CRAWLER -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                // username is powerup
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.ZOMBIE_TURN_CRAWLER, message.powerup, message.id));
                }
                if (gameState != null && !localUser.getUsername().equals(message.powerup)) {
                    if (gameState.getWorld().getEntityManager().getZombieById(message.id) != null) {
                        gameState.getWorld().getEntityManager().getZombieById(message.id).turnToCrawler();
                    }
                }
            }
            case Message.USER_GRENADE_TOSS -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_GRENADE_TOSS, message.username, message.grenade, message.x,
                            message.y));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username)
                            .throwGrenade(message.grenade, message.x, message.y);
                }
            }
            case Message.USER_SHOT_GRENADE_LAUNCHER -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_SHOT_GRENADE_LAUNCHER, message.username, message.grenade,
                            message.x, message.y));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).getOnlineGun()
                            .shootGrenadeLauncher(message.x, message.y);
                }
            }
            case Message.FLAMETHROWER_SOUND -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.FLAMETHROWER_SOUND, message.username));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).playFlamethrower();
                }
            }
            case Message.USER_SPAWN_LUNA -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_SPAWN_LUNA, message.username));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).spawnLuna();
                }
            }
            case Message.USER_REMOVE_LUNA -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_REMOVE_LUNA, message.username));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).despawnLuna();
                }
            }
            case Message.USER_LUNA_MOVED -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_LUNA_MOVED, message.username, message.x, message.y,
                            message.angle));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username).moveLuna(message.x,
                            message.y, message.angle);
                }
            }
            case Message.USER_REVIVED_USER -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (gameState != null && localUser.getUsername().equals(message.message)) {
                    gameState.getWorld().getEntityManager().getCurrentPlayer().gainHealth(message.amount);
                }
            }
            case Message.USER_INTERACT -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_INTERACT, message.username, message.id, message.isBusy));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificInteractable(message.id)
                            .setUsedByOther(message.isBusy);
                }
            }
            case Message.USER_ACTIVATED_BLESSING -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    server.sendToAllTCP(new Message(Message.USER_ACTIVATED_BLESSING, message.username, message.message));
                }
                if (gameState != null && !localUser.getUsername().equals(message.username)) {
                    gameState.getWorld().getEntityManager().getSpecificPlayer(message.username)
                            .activateBlessing(message.message);

                };
            }
            case Message.USER_READY -> {
                if (!isServer) {
                    multiLobbyState.gameAlreadyStarted = true;
                }
                if (isServer) {
                    for (Map.Entry<Integer, User> entry : users.entrySet()) {
                        if (entry.getValue().getUsername().equals(message.username)) {
                            entry.getValue().setReady(true);

                            if (gameAlreadyStarted && gameState != null) {
                                gameState.getWorld().getEntityManager()
                                        .addOtherPlayer(new PlayerMP(gameState.getHandler(), 0, 0, entry.getValue()));
                            }
                            break;
                        }
                    }
                }
            }
            case Message.HOST_CHANGE_MAP -> {
                selectedMap = message.username;
                multiLobbyState.selectedMap(message.username);
            }
            case Message.HOST_SEND_LOADING ->
                multiLobbyState.startLoading(selectedMap);
            case Message.HOST_START_GAME -> {
                try {
                    multiLobbyState.startGame(selectedMap);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            case Message.USER_CHANGED_SKIN -> {
                // if (isServer || !localUser.getUsername().equals(message.powerup)) {
                for (Map.Entry<Integer, User> entry : users.entrySet()) {
                    if (entry.getValue().getUsername().equals(message.powerup)) {
                        entry.getValue().setSkin(message.id);
                        if (isServer) {
                            server.sendToAllTCP(new Message(Message.USER_CHANGED_SKIN, message.powerup, message.id));
                        }
                        break;
                    }
                }
            }
            case Message.USER_CHANGED_HAT -> {
                // if (isServer || !localUser.getUsername().equals(message.powerup)) {
                for (Map.Entry<Integer, User> entry : users.entrySet()) {
                    if (entry.getValue().getUsername().equals(message.powerup)) {
                        entry.getValue().setHat(message.id);
                        if (isServer) {
                            server.sendToAllTCP(new Message(Message.USER_CHANGED_HAT, message.powerup, message.id));
                        }
                        break;
                    }
                }
                // }
            }
        }
        // }

    }

    private Map<String, Long> lastKeepAliveTimes = new HashMap<>();

    public void handleKeepAlive(Message message) {
        lastKeepAliveTimes.put(message.username, System.currentTimeMillis());
    }

    // Periodically check for timeouts
    public void checkTimeouts() {
        long currentTime = System.currentTimeMillis();
        for (Map.Entry<String, Long> entry : lastKeepAliveTimes.entrySet()) {
            if (currentTime - entry.getValue() > KEEP_ALIVE_INTERVAL * 2) {
                // Timeout the client
                disconnectClient(entry.getKey());
            }
        }
    }

    private void sendKeepAlive() {
        if (!isServer) {
            client.sendTCP(new Message(Message.KEEP_ALIVE, localUser.getUsername(), "keep-alive"));
        }
    }

    public void stopKeepAlive() {
        keepAliveTimer.stop();
    }

    private void disconnectClient(String username) {
        for (Map.Entry<Integer, User> entry : users.entrySet()) {
            if (entry.getValue().getUsername().equals(username)) {
                if (isServer) {
                    server.getConnections()[entry.getKey()].close();
                } else {
                    client.close();
                }
//				if (gameState != null) {
//					gameState.getWorld().getEntityManager().getOtherPlayers()
//							.remove(gameState.getWorld().getEntityManager().getSpecificPlayer(username));
//				}
                users.remove(entry.getKey());
                multiLobbyState.removeUser(entry.getKey());
                break;
            }
        }
    }

    private void handleNewConnection(Connection connection) {
        if (isServer) {
            // The local user is already added, handle new connections only
            if (!users.containsKey(localUser.getUsername().hashCode())) {
                // localUser.setConnection(connection);

                users.put(connection.getID(), localUser);
                multiLobbyState.addUser(localUser);
//				server.sendToAllTCP(new Message(Message.GAME_ALREADY_STARTED, connection.getID(), gameAlreadyStarted));
            }
            broadcastUserList();
        }
    }

    private void handleDisconnectedConnection(Connection connection) {
        String userToKick = "";
        if (gameState != null) {
            HashMap<Integer, User> userList = new HashMap<>(users);

            for (Map.Entry<Integer, User> entry : userList.entrySet()) {
                if (entry.getKey() == connection.getID()) {
                    userToKick = entry.getValue().getUsername();
                }
            }
            gameState.getWorld().getEntityManager().getOtherPlayers()
                    .remove(gameState.getWorld().getEntityManager().getSpecificPlayer(userToKick));
        }
        users.remove(connection.getID());
        multiLobbyState.removeUser(connection.getID());

        if (gameState == null) {
            broadcastUserList();
        } else {
            server.sendToAllTCP(new Message(Message.USER_LEAVE, connection.getID(), userToKick, null));
        }
    }

    ////////////////////////// lobby messages ///////////////////////////

	private void broadcastUserList() {
        if (isServer) {
            HashMap<Integer, User> userList = new HashMap<>(users);
            server.sendToAllTCP(new Message(Message.USER_LIST, 0, null, userList));

            for (Map.Entry<Integer, User> entry : userList.entrySet()) {
                Integer key = entry.getKey();
                String value = entry.getValue().getUsername();
                System.out.println("Key=" + key + ", Value=" + value);
            }
        }
    }

    public void startGame() {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.HOST_START_GAME, 0, null, null));
        }
    }

    public void startLoading() {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.HOST_SEND_LOADING));
        }
    }

    public void sendNewMapSelection(String s) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.HOST_CHANGE_MAP, s));
        }
    }

    public void sendReady(String username) {
        client.sendTCP(new Message(Message.USER_READY, username));
    }

    //////////////////////// game messages /////////////////////////
	public void sendNewX(String username, float x) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_X_MOVE, username, Float.toString(x)));
        } else {
            client.sendTCP(new Message(Message.USER_X_MOVE, username, Float.toString(x)));
        }
    }

    public void sendNewY(String username, float y) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_Y_MOVE, username, Float.toString(y)));
        } else {
            client.sendTCP(new Message(Message.USER_Y_MOVE, username, Float.toString(y)));
        }
    }


    public void sendNewAngle(String username, float angle) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_ROTATE, username, Float.toString(angle)));
        } else {
            client.sendTCP(new Message(Message.USER_ROTATE, username, Float.toString(angle)));
        }
    }

    public void sendPlayerShot(String username) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_SHOT, username, null));
        } else {
            client.sendTCP(new Message(Message.USER_SHOT, username, null));
        }
    }

    public void sendZombieUpdates(List<ZombiePosition> zombiePositions) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.ZOMBIE_POSITIONS, zombiePositions));
        }
    }

    public void spawnZombie(Zombie zombie) {
        String message = zombie.getID() + ":" + zombie.getX() + ":" + zombie.getY() + ":"
                + zombie.getSpeed() + ":" + zombie.getHealth();
        if (isServer) {
            server.sendToAllTCP(new Message(Message.ZOMBIE_SPAWN, null, message));
        }
    }

    public void zombieJustAttacked(int id) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.ZOMBIE_ATTACKED, null, Integer.toString(id)));
        } else {
            client.sendTCP(new Message(Message.ZOMBIE_ATTACKED, null, Integer.toString(id)));
        }
    }

    public void playerDamagedZombie(String username, int id, int damage) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_DAMAGED_ZOMBIE, username, id, damage));
        } else {
            client.sendTCP(new Message(Message.USER_DAMAGED_ZOMBIE, username, id, damage));
        }
    }

    public void sendNewRoundInfo(int round, int zombiesLeft) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.NEW_ROUND, round, zombiesLeft));
        }
    }

    public void sendNewHealth(String username, int amount) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_NEW_HEALTH, username, Integer.toString(amount)));
        } else {
            client.sendTCP(new Message(Message.USER_NEW_HEALTH, username, Integer.toString(amount)));
        }
    }

    public void sendUserTookDamage(String username) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_TOOK_DAMAGE, username));
        } else {
            client.sendTCP(new Message(Message.USER_TOOK_DAMAGE, username));
        }
    }

    public void sendRefillHealth() {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_REFILL_HEALTH));
        }
    }

    public void sendNewPowerUp(String powerup, int id, int x, int y) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.SPAWN_POWERUP, powerup, id, x, y));
        }
    }

    public void sendUserSpawnedPowerup(String powerup, int x, int y) {
        if (!isServer) {
            client.sendTCP(new Message(Message.USER_SPAWNED_POWERUP, powerup, 0, x, y));
        }
    }

    public void pickedUpPowerup(String username, int id) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_PICKED_POWERUP, username, id));
        } else {
            client.sendTCP(new Message(Message.USER_PICKED_POWERUP, username, id));
        }
    }

    public void sendCurrentGun(String username, String gunName) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_SWITCHED_WEAPON, username, gunName));
        } else {
            client.sendTCP(new Message(Message.USER_SWITCHED_WEAPON, username, gunName));
        }
    }

    public void turnZombieToCrawler(String username, int id) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.ZOMBIE_TURN_CRAWLER, username, id));
        } else {
            client.sendTCP(new Message(Message.ZOMBIE_TURN_CRAWLER, username, id));
        }
    }

    public void sendNewGrenade(String username, String grenade, int destX, int destY) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_GRENADE_TOSS, username, grenade, destX, destY));
        } else {
            client.sendTCP(new Message(Message.USER_GRENADE_TOSS, username, grenade, destX, destY));
        }
    }

    public void sendPlayerGrenadeLauncherShot(String username, int destX, int destY) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_SHOT_GRENADE_LAUNCHER, username, "", destX, destY));
        } else {
            client.sendTCP(new Message(Message.USER_SHOT_GRENADE_LAUNCHER, username, "", destX, destY));
        }
    }

    public void sendFlamethrowerSound(String username) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.FLAMETHROWER_SOUND, username));
        } else {
            client.sendTCP(new Message(Message.FLAMETHROWER_SOUND, username));
        }
    }

    public void sendRemoveLuna(String username) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_REMOVE_LUNA, username));
        } else {
            client.sendTCP(new Message(Message.USER_REMOVE_LUNA, username));
        }
    }

    public void sendNewLuna(String username) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_SPAWN_LUNA, username));
        } else {
            client.sendTCP(new Message(Message.USER_SPAWN_LUNA, username));
        }
    }

    public void sendNewLunaCoords(String username, float x, float y, float angle) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_LUNA_MOVED, username, Math.round(x), Math.round(y), angle));
        } else {
            client.sendTCP(new Message(Message.USER_LUNA_MOVED, username, Math.round(x), Math.round(y), angle));
        }
    }

    public void sendRevived(String username, String revived, int amount) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_REVIVED_USER, username, revived, amount));
        } else {
            client.sendTCP(new Message(Message.USER_REVIVED_USER, username, revived, amount));
        }
    }

    public void sendInteractionBusy(int id, String username, boolean isBusy) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_INTERACT, username, id, isBusy));
        } else {
            client.sendTCP(new Message(Message.USER_INTERACT, username, id, isBusy));
        }
    }

    public void sendActivatedBlessing(String username, String blessing) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_ACTIVATED_BLESSING, username, blessing));
        } else {
            client.sendTCP(new Message(Message.USER_ACTIVATED_BLESSING, username, blessing));
        }
    }

    public void sendUserSkinChange(String username, int id) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_CHANGED_SKIN, username, id));
        } else {
            client.sendTCP(new Message(Message.USER_CHANGED_SKIN, username, id));
        }
    }

    public void sendUserHatChange(String username, int id) {
        if (isServer) {
            server.sendToAllTCP(new Message(Message.USER_CHANGED_HAT, username, id));
        } else {
            client.sendTCP(new Message(Message.USER_CHANGED_HAT, username, id));
        }
    }

    /////////////////////// lobby stuff //////////////////////////

	public void startLobby() throws IOException {
        server.bind(54555, 54777);
        server.start();

        // Simulate the local user's connection
        Connection localConnection = new Connection() {
            @Override
            public int getID() {
                return 0;
            }
        };
        localUser.setConnection(localConnection);
        users.put(localConnection.getID(), localUser);
        multiLobbyState.addUser(localUser);
        broadcastUserList();
    }

    public void joinLobby(String host) throws IOException {
        client.start();

        InetAddress address = client.discoverHost(54777, 5000);
        client.connect(5000, address, 54555, 54777);

    }

    public void stopServer() {
        if (server != null) {
            server.stop();
        }
    }

    public void stopClient() {
        if (client != null) {
            client.stop();
        }
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public User getLocalUser() {
        return localUser;
    }

    public boolean isServer() {
        return isServer;
    }

    public MultiLobbyState getLobby() {
        return multiLobbyState;
    }

    public void setLoadingState(LoadingState loadingState) {
        this.loadingState = loadingState;
    }

}

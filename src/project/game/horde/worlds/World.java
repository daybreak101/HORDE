package project.game.horde.worlds;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.game.horde.entities.EntityManager;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.AmmoRefill;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.Door;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.InvisibleBounds;
import project.game.horde.entities.statics.MysteryBox;
import project.game.horde.entities.statics.PackAPunch;
import project.game.horde.entities.statics.PowerSwitch;
import project.game.horde.entities.statics.RandomPerk;
import project.game.horde.entities.statics.RitualCircle;
import project.game.horde.entities.statics.Wall;
import project.game.horde.entities.statics.WallBuy;
import project.game.horde.entities.statics.traps.IcyWater;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.maps.FarmMap;
import project.game.horde.network.Peer;
import project.game.horde.network.ZombiePosition;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;
import project.game.horde.zombieLogic.LightingLogic;
import project.game.horde.zombieLogic.PathingLogic;
import project.game.horde.zombieLogic.RoomLogic;
import project.game.horde.zombieLogic.RoundLogic;

public class World {

    private Handler handler;
    private int width, height;
    private int spawnX, spawnY, spawnZ;

    private EntityManager entityManager;
    private RoundLogic rounds;
    private LightingLogic lighting;
    private RoomLogic rooms;
    private PathingLogic pathing;
    private boolean powerOn;
    private String map;

    int ticker = 0, tickerLimit = 600;

    boolean nodesVisible;
    boolean test = false;

    // offline
    public World(Handler handler, String path, String entityPath,
            String nodesPath, String edgesPath, String lightsPath,
            String adjacentRooms, String spawnersPath, String floorsPath,
            User user, String map) throws IOException {
        initializeWorld(handler, path, entityPath, nodesPath,
                edgesPath, lightsPath, adjacentRooms, spawnersPath, map);
    }

    // online
    HashMap<Integer, User> users;
    Peer peer;

    public World(Handler handler, String path, String entityPath,
            String nodesPath, String edgesPath, String lightsPath,
            String adjacentRooms, String spawnersPath, String floorsPath,
            User user, Peer peer, HashMap<Integer, User> users, String map) throws IOException {
        this.peer = peer;
        this.users = users;
        initializeWorld(handler, path, entityPath, nodesPath,
                edgesPath, lightsPath, adjacentRooms, spawnersPath, map);
    }

    public void initializeWorld(Handler handler, String path, String entityPath,
            String nodesPath, String edgesPath, String lightsPath,
            String adjacentRooms, String spawnersPath, String map) throws IOException {
        this.map = map;
        this.handler = handler;
        handler.setWorld(this);
        entityManager = new EntityManager(handler);

        rooms = new RoomLogic(handler, this, adjacentRooms, entityPath);
        rounds = new RoundLogic(handler, rooms.getSpawners(), 0);

        handler.setRoundLogic(rounds);
        loadWorld(path);
        handler.setWorld(this);
        createStaticEntities(entityPath);
        pathing = new PathingLogic(handler, this, nodesPath, edgesPath);

        nodesVisible = false;
        powerOn = false;
        //lighting = new LightingLogic(handler, this, lightsPath);
    }

    private String getString(JsonNode obj, String key, String def) {
        JsonNode props = obj.get("properties");
        if (props != null) {
            for (JsonNode p : props) {
                if (key.equals(p.get("name").asText())) {
                    return p.get("value").asText();
                }
            }
        }

        JsonNode direct = obj.get(key);
        if (direct != null) {
            return direct.asText();
        }

        return def;
    }

    private int getInt(JsonNode obj, String key, int def) {

        JsonNode props = obj.get("properties");
        if (props != null) {
            for (JsonNode p : props) {
                if (key.equals(p.get("name").asText())) {
                    return p.get("value").asInt();
                }
            }
        }

        JsonNode direct = obj.get(key);
        if (direct != null && direct.isNumber()) {
            return direct.asInt();
        }

        return def;
    }

    public void createStaticEntities(String entityPath) {
        System.out.println(
                World.class.getClassLoader().getResource(entityPath)
        );
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream is = World.class.getResourceAsStream(entityPath)) {

            if (is == null) {
                throw new RuntimeException("Could not find map: " + entityPath);
            }

            JsonNode root = mapper.readTree(is);
            JsonNode layers = root.get("layers");
            for (JsonNode layer : layers) {
                if (!"Entities".equals(layer.get("name").asText())) {
                    continue;
                }

                JsonNode objects = layer.get("objects");
                for (JsonNode obj : objects) {
                    String type = obj.get("type").asText();
                    int i = obj.get("id").asInt();
                    int x = obj.get("x").asInt();
                    int y = obj.get("y").asInt();
                    int width = obj.get("width").asInt();
                    int height = obj.get("height").asInt();
                    switch (type) {
                        case "MysteryBox" ->
                            entityManager.addInteractable(new MysteryBox(handler, i, x, y));
                        case "AmmoRefill" ->
                            entityManager.addInteractable(new AmmoRefill(handler, i, x, y));
                        case "RandomPerk" ->
                            entityManager.addInteractable(new RandomPerk(handler, i, x, y));
                        case "PackAPunch" ->
                            entityManager.addInteractable(new PackAPunch(handler, i, x, y));
                        case "Door" -> {
                            int room1 = getInt(obj, "room1", 0);
                            int room2 = getInt(obj, "room2", 0);
                            int cost = getInt(obj, "cost", 0);
                            entityManager.addInteractable(new Door(handler, i, x, y, width, height, room1, room2, cost));
                        }
                        case "Wall" -> {
                            int wallLength = width > height ? width : height;
                            int whatWall = getInt(obj, "wall", 0);
                            entityManager.addWall(new Wall(handler, i, x, y, wallLength, whatWall));
                        }
                        case "FarmMap" ->
                            entityManager.setMap(new FarmMap(handler, 0, 0, 3400, 1700));
                        case "IcyWater" ->
                            entityManager.addArea(new IcyWater(handler, x, y));
                        case "Barrier" -> {
                            int whatWall = getInt(obj, "wall", 0);
                            entityManager.addBarrier(new Barrier(handler, i, x, y, whatWall));
                        }
                        case "RitualCircle" ->
                            entityManager.addInteractable(new RitualCircle(handler, i, x, y));
                        case "WallBuy" -> {
                            String gun = getString(obj, "gun", "none");
                            int whatWall = getInt(obj, "wall", 0);
                            entityManager.addInteractable(new WallBuy(handler, i, x, y, gun, whatWall));
                        }
                        case "PowerSwitch" ->
                            entityManager.addInteractable(new PowerSwitch(handler, i, x, y));
                        case "InvisibleBounds" -> {
                            entityManager.addBoundary(new InvisibleBounds(handler, i, x, y, width, height, 0));
                        }
                        default -> {
                        }
                    }
                }
                // Use the JSON...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // public void createStaticEntities(String entityPath) {
    //     String file = Utils.loadFileAsString(entityPath);
    //     String[] tokens = file.split("\\s+");
    //     int i = 0;
    //     int token = 0;
    //     int x, y;
    //     int sx, sy;
    //     int vertex = 0;
    //     int rotation = 0;
    //     int whatWall = 0, wallLength = 0;
    //     int room1 = 0, room2 = 0;
    //     int gunId = 0;
    //     int width = 0, height = 0;
    //     int orientation = 0;
    //     int cost = 0;
    //     while (i < tokens.length) {
    //         token = Utils.parseInt(tokens[i++]);
    //         x = Utils.parseInt(tokens[i++]);
    //         y = Utils.parseInt(tokens[i++]);
    //         switch (token) {
    //             case 0 ->
    //                 entityManager.addInteractable(new MysteryBox(handler, i, x, y));
    //             case 1 ->
    //                 entityManager.addInteractable(new AmmoRefill(handler, i, x, y));
    //             case 2 ->
    //                 entityManager.addInteractable(new RandomPerk(handler, i, x, y));
    //             case 3 ->
    //                 entityManager.addInteractable(new PackAPunch(handler, i, x, y));
    //             case 4 -> {
    //                 wallLength = Utils.parseInt(tokens[i++]);
    //                 whatWall = Utils.parseInt(tokens[i++]);
    //                 room1 = Utils.parseInt(tokens[i++]);
    //                 room2 = Utils.parseInt(tokens[i++]);
    //                 cost = Utils.parseInt(tokens[i++]);
    //                 entityManager.addInteractable(new Door(handler, i, x, y, wallLength, whatWall, room1, room2, cost));
    //             }
    //             case 5 -> {
    //                 wallLength = Utils.parseInt(tokens[i++]);
    //                 whatWall = Utils.parseInt(tokens[i++]);
    //                 entityManager.addWall(new Wall(handler, i, x, y, wallLength, whatWall));
    //             }
    //             case 6 ->
    //                 entityManager.setMap(new FarmMap(handler, 0, 0, 3400, 1700));
    //             case 7 -> {
    //                 sx = Utils.parseInt(tokens[i++]);
    //                 sy = Utils.parseInt(tokens[i++]);
    //                 rotation = Utils.parseInt(tokens[i++]);
    //                 entityManager.addTrap(new ElectricTrap(handler, i, x, y, sx, sy, rotation));
    //             }
    //             case 8 -> {
    //                 sx = Utils.parseInt(tokens[i++]);
    //                 sy = Utils.parseInt(tokens[i++]);
    //                 rotation = Utils.parseInt(tokens[i++]);
    //                 entityManager.addTrap(new MineFieldTrap(handler, i, x, y, sx, sy, rotation));
    //             }
    //             case 9 -> {
    //                 sx = Utils.parseInt(tokens[i++]);
    //                 sy = Utils.parseInt(tokens[i++]);
    //                 rotation = Utils.parseInt(tokens[i++]);
    //                 entityManager.addTrap(new Turret(handler, i, x, y, sx, sy, rotation));
    //             }
    //             case 10 -> {
    //                 sx = Utils.parseInt(tokens[i++]);
    //                 sy = Utils.parseInt(tokens[i++]);
    //                 rotation = Utils.parseInt(tokens[i++]);
    //                 entityManager.addTrap(new ConveyorBeltTrap(handler, i, x, y, sx, sy, rotation));
    //             }
    //             case 11 ->
    //                 entityManager.addArea(new IcyWater(handler, x, y));
    //             case 12 -> {
    //                 whatWall = Utils.parseInt(tokens[i++]);
    //                 entityManager.addBarrier(new Barrier(handler, i, x, y, whatWall));
    //             }
    //             case 13 ->
    //                 entityManager.addInteractable(new RitualCircle(handler, i, x, y));
    //             case 14 -> {
    //                 gunId = Utils.parseInt(tokens[i++]);
    //                 whatWall = Utils.parseInt(tokens[i++]);
    //                 entityManager.addInteractable(new WallBuy(handler, i, x, y, gunId, whatWall));
    //             }
    //             case 15 -> {
    //                 width = Utils.parseInt(tokens[i++]);
    //                 height = Utils.parseInt(tokens[i++]);
    //                 orientation = Utils.parseInt(tokens[i++]);
    //                 //entityManager.addArea(new Staircase(handler, x, y, width, height, dz, orientation));
    //             }
    //             case 16 ->
    //                 entityManager.addInteractable(new PowerSwitch(handler, i, x, y));
    //             case 17 -> {
    //                 width = Utils.parseInt(tokens[i++]);
    //                 height = Utils.parseInt(tokens[i++]);
    //                 entityManager.addBoundary(new InvisibleBounds(handler, i, x, y, width, height, 0));
    //             }
    //             case 18 -> {
    //             }
    //             //entityManager.setMap(new SeattleMap(handler, 0, 0, 3400, 1700));
    //             case 19 -> {
    //             }
    //             //entityManager.setMap(new IcelandMap(handler, 0, 0, 3400, 1700));
    //             default -> {
    //             }
    //         }
    //     }
    // }
    private Timer sendPositions = new Timer(3);
    private Timer delayRespawns = new Timer(20);

    public void tick() {
        entityManager.tick();
        delayRespawns.tick();
        if (peer != null && peer.isServer()) {
            //rounds.tick();
            //rooms.tick();
            rooms.updateActiveRooms();

            if (delayRespawns.isReady()) {
                rooms.respawnZombiesOutsideOfActive();
            }
            rooms.setActiveSpawners();
            rounds.tick();

        }
        if (peer == null) {
            //rounds.tick();
            //rooms.tick();

            rooms.updateActiveRooms();
            if (delayRespawns.isReady()) {
                rooms.respawnZombiesOutsideOfActive();
            }
            rooms.setActiveSpawners();
            if (!test) {
                rounds.tick();
            }

        }

        if (peer != null && peer.isServer()) {
            sendPositions.tick();
            if (sendPositions.isReady()) {
                ArrayList<ZombiePosition> zombiePositions = new ArrayList<>();
                for (Zombie zombie : entityManager.getZombies()) {
                    zombiePositions.add(new ZombiePosition(zombie.getID(), zombie.getX(), zombie.getY(),
                            zombie.getRotationAngle()));
                }

                // Send the updates to all clients
                peer.sendZombieUpdates(zombiePositions);
            }
        }
    }

    // BufferedImage map;
    public boolean lightRendered = false;

    private AffineTransform precomputedTransform;
    private double currentZoomLevel;

    private void updateZoomLevel(double newZoomLevel) {
        if (newZoomLevel != currentZoomLevel) {
            currentZoomLevel = newZoomLevel;
            precomputedTransform = AffineTransform.getScaleInstance(currentZoomLevel, currentZoomLevel);
        }
    }

    public void render(Graphics g) {
        double zoomLevel = handler.getSettings().getZoomLevel(false);
        updateZoomLevel(zoomLevel);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform originalTransform = g2d.getTransform();

        g2d.setTransform(precomputedTransform);
        entityManager.render(g);

        if (nodesVisible) {
            pathing.renderNodes(g2d);
        }
//		if (!lightRendered) {
//			lightPositions.add(new LightSource(handler, 1000, 1000, 200, 1.0f));
//			lightPositions.add(new LightSource(handler, 1200, 1000, 200, 1.0f));
//			lightRendered = true;
//		}

        //lighting.renderLighting(g);
        entityManager.getCurrentPlayer().renderLaser(g2d);

        g2d.setTransform(originalTransform);
        rooms.render(g2d);
        entityManager.getCurrentPlayer().renderDamage(g2d);
        entityManager.getCurrentPlayer().renderHUD(g2d);

    }

    private void loadWorld(String path) {
        String file = Utils.loadFileAsString(path);
        String[] tokens = file.split("\\s+");
        width = Utils.parseInt(tokens[0]);
        height = Utils.parseInt(tokens[1]);
        spawnX = Utils.parseInt(tokens[2]);
        spawnY = Utils.parseInt(tokens[3]);
        spawnZ = Utils.parseInt(tokens[4]);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean checkForStaticEntities(int x1, int y1, int x2, int y2) {
        Line2D.Float line = new Line2D.Float(x1, y1, x2, y2);
        for (InteractableStaticEntity e : entityManager.getInteractables()) {
            if (line.intersects(e.getCollisionBounds(0, 0))) {
                if (!(e instanceof Barrier)
                        //!handler.getWorld().getEntityManager().getBarriers().contains(e)
                        && !(e instanceof Door)) {
                    return true;
                }
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (line.intersects(e.getCollisionBounds(0, 0))) {
                return true;
            }
        }

        return false;
    }

    public boolean checkWithinStaticEntities(int x, int y, int z) {
        Ellipse2D.Float point = new Ellipse2D.Float(x, y, 20, 20);
        for (InteractableStaticEntity e : entityManager.getInteractables()) {
            if (point.intersects(e.getCollisionBounds(0, 0))) {
                if (!(e instanceof Barrier) //!handler.getWorld().getEntityManager().getBarriers().contains(e)
                        ) {
                    return true;
                }
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (point.intersects(e.getCollisionBounds(0, 0))) {
                return true;
            }
        }
        return false;
    }

    public void showNodesAndEdges() {
        nodesVisible = true;
    }

    public RoundLogic getRoundLogic() {
        return rounds;
    }

    public LightingLogic getLightingLogic() {
        return lighting;
    }

    public RoomLogic getRoomLogic() {
        return rooms;
    }

    public PathingLogic getPathingLogic() {
        return pathing;
    }

    public boolean isPowerOn() {
        return powerOn;
    }

    public void setPowerOn(boolean powerOn) {
        this.powerOn = powerOn;
    }
}

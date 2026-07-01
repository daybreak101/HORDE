package project.game.horde.zombieLogic;

import java.awt.Graphics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.main.Handler;
import project.game.horde.utils.Node;
import project.game.horde.utils.Utils;
import project.game.horde.worlds.World;

public class RoomLogic {

    private Handler handler;
    private World world;
    private int currentRoom;
    private Set<Integer> activeRooms;
    private Set<Integer> openedRooms;
    private ArrayList<Spawner> spawners;
    private HashMap<Integer, Set<Integer>> adjacentRooms;

    public RoomLogic(Handler handler, World world, String adjacentRoomsPath, String spawnersPath) {
        this.handler = handler;
        this.world = world;
        currentRoom = 1;
        activeRooms = new HashSet<>();
        openedRooms = new HashSet<>();
        openedRooms.add(1);
        spawners = new ArrayList<>();
        createAdjacentRooms(adjacentRoomsPath);
        createSpawners(spawnersPath);
    }

    public void createAdjacentRooms(String adjacentRoomsPath) {
        this.adjacentRooms = new HashMap<>();

        // read file
        String file = Utils.loadFileAsString(adjacentRoomsPath);
        String[] tokens = file.split("\\s+");

        // get number of nodes
        int i = 0;

        // process nodes
        int current, room;
        Set<Integer> actives;
        while (i < tokens.length) {
            actives = new HashSet<>();
            current = Utils.parseInt(tokens[i++]);
            actives.add(current);

            while (Utils.parseInt(tokens[i]) != 0) {
                room = Utils.parseInt(tokens[i++]);
                actives.add(room);
            }
            adjacentRooms.put(current, actives);
            i++;
        }
    }

    // public void createSpawners(String spawnersPath) {
    //     String file = Utils.loadFileAsString(spawnersPath);
    //     String[] tokens = file.split("\\s+");
    //     int i = 0;
    //     int x, y, room;
    //     while (i < tokens.length) {
    //         x = Utils.parseInt(tokens[i++]);
    //         y = Utils.parseInt(tokens[i++]);
    //         room = Utils.parseInt(tokens[i++]);
    //         spawners.add(new Spawner(handler, x, y, room));
    //     }
    // }
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

    public void createSpawners(String entityPath) {
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
                if (!"Spawners".equals(layer.get("name").asText())) {
                    continue;
                }

                JsonNode objects = layer.get("objects");
                for (JsonNode obj : objects) {
                    int x = obj.get("x").asInt();
                    int y = obj.get("y").asInt();
					int room = getInt(obj, "room", 0);
					spawners.add(new Spawner(handler, x, y, room));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int addBack = 0;

    public void updateActiveRooms() {
        world.getRoundLogic().addRespawnZombies(addBack);
        addBack = 0;

        int n = handler.getCurrentPlayer().getClosestNode();
        Node node = world.getPathingLogic().getNodes().get(n);

        activeRooms = new HashSet<>();
        activeRooms.addAll(adjacentRooms.get(node.getRoom()));

        //System.out.println("current: " + node.getRoom()
        //				+ " , adjacent: " + adjacentRooms.get(node.getRoom()));
        //check for other players active rooms
        if (handler.getCurrentPlayer().getPeer() != null) {

            for (PlayerMP others : world.getEntityManager().getOtherPlayers()) {
                int m = others.getClosestNode();
                Node node1 = world.getPathingLogic().getNodes().get(m);
                activeRooms.addAll(adjacentRooms.get(node1.getRoom()));
            }
        }

        ArrayList<Integer> active = new ArrayList<>(activeRooms);
        for (int i = 0; i < active.size(); i++) {
            if (!openedRooms.contains(active.get(i))) {
                activeRooms.remove(active.get(i));
            }
        }

//		//respawn zombies that are out of active rooms
//		int o;
//		Node node2;
//		CopyOnWriteArrayList<Zombie> zombies = world.getEntityManager().getZombies();
//		System.out.println("room logic");
//		for (int i = 0; i < zombies.size(); i++) {
//			Zombie e = zombies.get(i);
//			
//			o = e.getClosestNode();
//			node2 = world.getPathingLogic().getNodes().get(o);
//			System.out.println("zombie exist");
//			if(node != null && !activeRooms.contains(node2.getRoom())) {
//				System.out.println("The node: " + node2.getRoom());
//				addBack++;
//				e.setActive(false);
//				//zombies.remove(e);
//			}
//		}
//		
//		//reset spawner queue
//		world.getRoundLogic().resetQueue();
    }

    public void respawnZombiesOutsideOfActive() {
        //respawn zombies that are out of active rooms
        int o;
        Node node2;
        CopyOnWriteArrayList<Zombie> zombies = world.getEntityManager().getZombies();
        System.out.println("room logic");
        for (int i = 0; i < zombies.size(); i++) {
            Zombie e = zombies.get(i);

            o = e.getClosestNode();
            node2 = world.getPathingLogic().getNodes().get(o);
            //System.out.println("zombie exist at node " + o);
            if (node2 != null && !activeRooms.contains(node2.getRoom())) {
                //System.out.println("The node's room: " + node2.getRoom());
                addBack++;
                e.setActive(false);
                //zombies.remove(e);
            }
        }

        //reset spawner queue
        world.getRoundLogic().resetQueue();
    }

    //called when player opens doors
    public void addOpenedRooms(int room) {
        //do not add duplicate rooms
        //if(!openedRooms.contains(room))
        openedRooms.add(room);
    }

    public void setActiveSpawners() {
        //first check if player changed rooms
        //updateActiveRooms();

        //update active spawners based on active rooms
        for (Spawner spawner : spawners) {
            int room = spawner.getRoom();
            if (activeRooms.contains(room)) {
                spawner.setActive();
                spawner.tick();
            } else {
                spawner.setInactive();
            }
        }

    }

    public void render(Graphics g) {
        //g.drawString("Active Rooms:" + activeRooms, 100, 100);
    }

    public ArrayList<Spawner> getSpawners() {
        return spawners;
    }

    public Set<Integer> getOpenedRooms() {
        return openedRooms;
    }

}

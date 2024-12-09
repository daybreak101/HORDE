package project.game.horde.worlds;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

import project.game.horde.entities.EntityManager;
import project.game.horde.entities.areas.Staircase;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.AmmoRefill;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.Door;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.MysteryBox;
import project.game.horde.entities.statics.PackAPunch;
import project.game.horde.entities.statics.RandomPerk;
import project.game.horde.entities.statics.RitualCircle;
import project.game.horde.entities.statics.Wall;
import project.game.horde.entities.statics.WallBuy;
import project.game.horde.entities.statics.traps.ConveyorBeltTrap;
import project.game.horde.entities.statics.traps.ElectricTrap;
import project.game.horde.entities.statics.traps.IcyWater;
import project.game.horde.entities.statics.traps.MineFieldTrap;
import project.game.horde.entities.statics.traps.Turret;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.maps.FactoryMap;
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

	int ticker = 0, tickerLimit = 600;

	boolean nodesVisible;

	// offline
	public World(Handler handler, String path, String entityPath, 
			String nodesPath, String edgesPath, String lightsPath,
			String adjacentRooms, String spawnersPath, User user) {
		initializeWorld(handler, path, entityPath, nodesPath, 
				edgesPath, lightsPath, adjacentRooms, spawnersPath);
	}

	// online
	HashMap<Integer, User> users;
	Peer peer;

	public World(Handler handler, String path, String entityPath, 
			String nodesPath, String edgesPath, String lightsPath,
			String adjacentRooms, String spawnersPath, User user, Peer peer, HashMap<Integer, User> users) {
		this.peer = peer;
		this.users = users;
		initializeWorld(handler, path, entityPath, nodesPath, 
				edgesPath, lightsPath, adjacentRooms, spawnersPath);
	}
	
	public void initializeWorld(Handler handler, String path, String entityPath, 
			String nodesPath, String edgesPath, String lightsPath,
			String adjacentRooms, String spawnersPath) {
		this.handler = handler;
		handler.setWorld(this);
		entityManager = new EntityManager(handler);
		rooms = new RoomLogic(handler, this, adjacentRooms, spawnersPath);
		rounds = new RoundLogic(handler, rooms.getSpawners(), 0);
		lighting = new LightingLogic(handler, this, lightsPath);
		handler.setRoundLogic(rounds);

		loadWorld(path);
		handler.setWorld(this);
		createStaticEntities(entityPath);
		pathing = new PathingLogic(handler, this, nodesPath, edgesPath);
	
		nodesVisible = false;
	}

	public void createStaticEntities(String entityPath) {
		String file = Utils.loadFileAsString(entityPath);
		String[] tokens = file.split("\\s+");
		int i = 0;
		int token = 0;
		int x, y, z;
		int sx, sy, sz;
		int vertex = 0;
		int rotation = 0;
		int whatWall = 0, wallLength = 0;
		int room1 = 0, room2 = 0;
		int gunId = 0;
		int width = 0, height = 0;
		int goUp = 0, orientation = 0;
		while (i < tokens.length) {
			token = Utils.parseInt(tokens[i++]);
			x = Utils.parseInt(tokens[i++]);
			y = Utils.parseInt(tokens[i++]);
			z = Utils.parseInt(tokens[i++]);
			switch (token) {
			case 0:
				entityManager.addInteractable(new MysteryBox(handler, i, x, y, z));
				break;
			case 1:
				entityManager.addInteractable(new AmmoRefill(handler, i, x, y, z));
				break;
			case 2:
				entityManager.addInteractable(new RandomPerk(handler, i, x, y, z));
				break;
			case 3:
				entityManager.addInteractable(new PackAPunch(handler, i, x, y, z));
				break;
			case 4:
				wallLength = Utils.parseInt(tokens[i++]);
				whatWall = Utils.parseInt(tokens[i++]);
				room1 = Utils.parseInt(tokens[i++]);
				room2 = Utils.parseInt(tokens[i++]);
				entityManager.addInteractable(new Door(handler, i, x, y, z, wallLength, whatWall, room1, room2));
				break;
			case 5:
				wallLength = Utils.parseInt(tokens[i++]);
				whatWall = Utils.parseInt(tokens[i++]);
				entityManager.addWall(new Wall(handler, i, x, y, z, wallLength, whatWall));
				break;
			case 6:
				entityManager.setMap(new FactoryMap(handler, 99, 99, 0, 3400, 1700));
				break;
			case 7:
				sx = Utils.parseInt(tokens[i++]);
				sy = Utils.parseInt(tokens[i++]);
				sz = Utils.parseInt(tokens[i++]);
				rotation = Utils.parseInt(tokens[i++]);
				entityManager.addTrap(new ElectricTrap(handler, i, x, y, z, sx, sy, sz, rotation));
				break;
			case 8:
				sx = Utils.parseInt(tokens[i++]);
				sy = Utils.parseInt(tokens[i++]);
				sz = Utils.parseInt(tokens[i++]);
				rotation = Utils.parseInt(tokens[i++]);
				entityManager.addTrap(new MineFieldTrap(handler, i, x, y, z, sx, sy, sz, rotation));
				break;
			case 9:
				sx = Utils.parseInt(tokens[i++]);
				sy = Utils.parseInt(tokens[i++]);
				sz = Utils.parseInt(tokens[i++]);
				rotation = Utils.parseInt(tokens[i++]);
				entityManager.addTrap(new Turret(handler, i, x, y, z, sx, sy, sz, rotation));
				break;
			case 10:
				sx = Utils.parseInt(tokens[i++]);
				sy = Utils.parseInt(tokens[i++]);
				sz = Utils.parseInt(tokens[i++]);
				rotation = Utils.parseInt(tokens[i++]);
				entityManager.addTrap(new ConveyorBeltTrap(handler, i, x, y, z, sx, sy, sz, rotation));
				break;
			case 11:
				entityManager.addArea(new IcyWater(handler, x, y, z));
				break;
			case 12:
				whatWall = Utils.parseInt(tokens[i++]);
				entityManager.addBarrier(new Barrier(handler, i, x, y, z, whatWall));
				break;
			case 13:
				entityManager.addInteractable(new RitualCircle(handler, i, x, y, z));
				break;
			case 14:
				gunId = Utils.parseInt(tokens[i++]);
				whatWall = Utils.parseInt(tokens[i++]);
				entityManager.addInteractable(new WallBuy(handler, i, x, y, z, gunId, whatWall));
				break;
			case 15:
				width = Utils.parseInt(tokens[i++]);
				height = Utils.parseInt(tokens[i++]);
				goUp = Utils.parseInt(tokens[i++]);
				orientation = Utils.parseInt(tokens[i++]);
				entityManager.addArea(new Staircase(handler, x, y, z, width, height, goUp, orientation));
				break;
			default:
				break;

			}
		}

	}

	private Timer sendPositions = new Timer(3);
	private Timer delayRespawns = new Timer(20);

	public void tick() {
		entityManager.tick();
		delayRespawns.tick();
		if (peer != null && peer.isServer()) {
			//rounds.tick();
			//rooms.tick();
			rooms.updateActiveRooms();
			
			if(delayRespawns.isReady())
				rooms.respawnZombiesOutsideOfActive();
			rooms.setActiveSpawners();
			rounds.tick();
			
			
		}			
		if (peer == null) {
			//rounds.tick();
			//rooms.tick();
			
			rooms.updateActiveRooms();
			if(delayRespawns.isReady())
				rooms.respawnZombiesOutsideOfActive();
			rooms.setActiveSpawners();
			rounds.tick();
			
			
		}

		
	
		
		if (peer != null && peer.isServer()) {
			sendPositions.tick();
			if (sendPositions.isReady()) {
				ArrayList<ZombiePosition> zombiePositions = new ArrayList<>();
				for (Zombie zombie : entityManager.getZombies()) {
					zombiePositions.add(new ZombiePosition(zombie.getID(), zombie.getX(), zombie.getY(),
							zombie.getZ(), zombie.getRotationAngle()));
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
		double zoomLevel = handler.getSettings().getZoomLevel();
		updateZoomLevel(zoomLevel);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform originalTransform = g2d.getTransform();

		g2d.setTransform(precomputedTransform);
		entityManager.render(g);

		if(nodesVisible)
			pathing.renderNodes(g2d);
//		if (!lightRendered) {
//			lightPositions.add(new LightSource(handler, 1000, 1000, 200, 1.0f));
//			lightPositions.add(new LightSource(handler, 1200, 1000, 200, 1.0f));
//			lightRendered = true;
//		}
//
//		renderLighting(g);
		entityManager.getCurrentPlayer().renderLaser(g2d);

		g2d.setTransform(originalTransform);

		/////////////////////////////////////////////////////////////
		if (entityManager.getOtherPlayers().size() > 0) {
			if (entityManager.getCurrentPlayer().getHealth() > 0 || entityManager.oneAlive()) {
				entityManager.getCurrentPlayer().renderHUD(g);
			}
			if (!entityManager.oneAlive()) {
				entityManager.getCurrentPlayer().renderHUD(g);
			}
		} else {
			if (entityManager.getCurrentPlayer().getHealth() > 0 || !entityManager.oneAlive()) {
				entityManager.getCurrentPlayer().renderHUD(g);
			}
		}
		rooms.render(g2d);
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

	public boolean checkForStaticEntities(int x1, int y1, int z1, int x2, int y2, int z2) {
		Line2D.Float line = new Line2D.Float(x1, y1, x2, y2);
		for (InteractableStaticEntity e : entityManager.getInteractables()) {
			if (z1 == z2 && z1 == e.getZ() && line.intersects(e.getCollisionBounds(0, 0))) {
				if (!handler.getWorld().getEntityManager().getBarriers().contains(e) &&
						!(e instanceof Door)) {
					return true;
				}
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (z1 == z2 && z1 == e.getZ() && line.intersects(e.getCollisionBounds(0, 0))) {
				return true;
			}
		}

		return false;
	}

	public boolean checkWithinStaticEntities(int x, int y, int z) {
		Ellipse2D.Float point = new Ellipse2D.Float(x, y, 20, 20);
		for (InteractableStaticEntity e : entityManager.getInteractables()) {
			if (z == e.getZ() && point.intersects(e.getCollisionBounds(0, 0))) {
				if (!handler.getWorld().getEntityManager().getBarriers().contains(e)) {
					return true;
				}
			}
		}
		for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
			if (z == e.getZ() && point.intersects(e.getCollisionBounds(0, 0))) {
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

}

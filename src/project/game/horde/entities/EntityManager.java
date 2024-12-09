package project.game.horde.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import project.game.horde.entities.areas.Areas;
import project.game.horde.entities.blood.Blood;
import project.game.horde.entities.bullets.Explosion;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.entities.powerups.DeathMachine;
import project.game.horde.entities.powerups.DoublePoints;
import project.game.horde.entities.powerups.HealthUp;
import project.game.horde.entities.powerups.InfiniteAmmo;
import project.game.horde.entities.powerups.InstaKill;
import project.game.horde.entities.powerups.MaxAmmo;
import project.game.horde.entities.powerups.Nuke;
import project.game.horde.entities.powerups.PerkBag;
import project.game.horde.entities.powerups.PowerUps;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.entities.statics.traps.Trap;
import project.game.horde.main.Handler;
import project.game.horde.network.Message;

public class EntityManager {

	int currentRound;
	public long zombieHealth;

	Random rand = new Random();

//	private Wolf luna;
	private Entity map;
	private Handler handler;
	private CopyOnWriteArrayList<PlayerMP> otherPlayers;
	private Player currentPlayer;
	private CopyOnWriteArrayList<Zombie> zombies;
	private CopyOnWriteArrayList<Entity> entities;

	private CopyOnWriteArrayList<Blood> blood;
	private CopyOnWriteArrayList<PowerUps> powerups;
	private CopyOnWriteArrayList<Explosion> explosions;
	private ArrayList<Trap> traps;
	private ArrayList<InteractableStaticEntity> interactables;
	private ArrayList<Barrier> barriers;
	private ArrayList<Areas> areas;

	private ArrayList<Wall> walls;
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
		public int compare(Entity a, Entity b) {
			if (a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			if (a.getY() + a.getHeight() > b.getY() + b.getHeight())
				return 1;
			return 0;
		}
	};

	public InteractableStaticEntity getSpecificInteractable(int id) {
		for (InteractableStaticEntity interactable : interactables) {
			if (interactable.getID() == id) {
				return interactable;
			}
		}

		return null;
	}

	public int numOfEntities() {
		// 1 for player
		return 1 + zombies.size() + blood.size() + entities.size() + traps.size() + barriers.size() + powerups.size()
				+ areas.size() + interactables.size() + walls.size();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	private Rectangle renderArea;

	public EntityManager(Handler handler) {
		this.handler = handler;
		otherPlayers = new CopyOnWriteArrayList<PlayerMP>();
		areas = new ArrayList<Areas>();
		barriers = new ArrayList<Barrier>();
		blood = new CopyOnWriteArrayList<Blood>();
		entities = new CopyOnWriteArrayList<Entity>();
		interactables = new ArrayList<InteractableStaticEntity>();
		powerups = new CopyOnWriteArrayList<PowerUps>();
		traps = new ArrayList<Trap>();
		zombies = new CopyOnWriteArrayList<Zombie>();
		walls = new ArrayList<Wall>();
		explosions = new CopyOnWriteArrayList<Explosion>();

	}

	public void tick() {
		for (int i = 0; i < otherPlayers.size(); i++) {
			PlayerMP e = otherPlayers.get(i);
			e.tick();
			if (!e.isActive())
				otherPlayers.remove(e);
		}
		for (int i = 0; i < zombies.size(); i++) {
			Zombie e = zombies.get(i);
			e.tick();
			if (!e.isActive())
				zombies.remove(e);
		}
		for (Blood e : blood) {
			e.tick();
			if (e.getCounter() >= e.getTimer()) {
				blood.remove(e);
			}
		}

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
			if (!e.isActive())
				entities.remove(e);
		}
		entities.sort(renderSorter);
//		for (InteractableStaticEntity e : interactables) {
//			e.tick();
//		}
		
		for (int i = 0; i < interactables.size(); i++) {
			Entity e = interactables.get(i);
			e.tick();
			if (!e.isActive())
				interactables.remove(e);
		}
		
		for (PowerUps e : powerups) {
			if (e != null) {
				e.tick();
			}
			if (!e.isActive())
				powerups.remove(e);
		}
		for (Trap e : traps) {
			e.tick();
		}
		for (Areas e : areas) {
			e.tick();
		}
		for (Barrier e : barriers) {
			e.tick();
		}
		for (Explosion e : explosions) {
			if (e != null) {
				e.tick();
			}
			if (!e.isActive())
				explosions.remove(e);
		}
		currentPlayer.tick();
		for (Wall e : walls) {
			e.tick();
		}
	}

	public Zombie getZombieById(int id) {
		for (Zombie z : zombies) {
			if (z.getID() == id) {
				return z;
			}
		}
		return null;
	}

	public void setMap(Entity e) {
		map = e;
	}
	
	private boolean isEntityVisible(Entity e) {
	    // Check if the entity is within the visible area of the screen
	    return e.getX() >= handler.getGameCamera().getxOffset() &&
	           e.getX() <= handler.getGameCamera().getxOffset() + handler.getWidth() &&
	           e.getY() >= handler.getGameCamera().getyOffset() &&
	           e.getY() <= handler.getGameCamera().getyOffset() + handler.getHeight();
	}

	public void render(Graphics g) {
		int z = currentPlayer.getZ();
		if (currentPlayer.getHealth() <= 0) {
			renderBW(g);
		} else {
			if (map != null) {
				map.render(g);
			}
			for (Areas e : areas) {
				e.render(g);
			}
			for (Trap e : traps) {
				if(z >= e.getZ()) e.render(g);
			}
			for (Blood e : blood) {
				if(z >= e.getZ()) e.render(g);
			}

			for (Barrier e : barriers) {
				if(z >= e.getZ()) e.render(g);
			}
			for (InteractableStaticEntity e : interactables) {
				if(z >= e.getZ()) e.render(g);
			}

			for (PowerUps e : powerups) {
				if(z >= e.getZ()) e.render(g);
			}	
			for (Entity e : entities) {
				if(z >= e.getZ()) e.render(g);
			}
			for (Zombie e : zombies) {
				if(z >= e.getZ()) e.render(g);
			}
			for (PlayerMP e : otherPlayers) {
				if(z >= e.getZ()) e.render(g);
			}
			for(Explosion e: explosions) {
				if(z >= e.getZ()) e.render(g);
			}

			currentPlayer.render(g);

			for (Wall e : walls) {
				if(z >= e.getZ()) e.render(g);
			}
		}
	}

	public void renderBW(Graphics g) {
		int z = currentPlayer.getZ();
		if (map != null) {
			map.renderBW(g);
		}
		for (Areas e : areas) {
			e.renderBW(g);
		}
		for (Trap e : traps) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for (Blood e : blood) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for (Entity e : entities) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for (Barrier e : barriers) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for (InteractableStaticEntity e : interactables) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for (PowerUps e : powerups) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for (Zombie e : zombies) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for (PlayerMP e : otherPlayers) {
			if(z == e.getZ()) e.renderBW(g);
		}
		for(Explosion e: explosions) {
			if(z == e.getZ()) e.renderBW(g);
		}
		currentPlayer.renderBW(g);
		for (Wall e : walls) {
			if(z == e.getZ()) e.renderBW(g);
		}
	}

	public void addZombieForClient(String[] info) {
		zombies.add(new Zombie(handler, Integer.parseInt(info[0]), Float.parseFloat(info[1]), Float.parseFloat(info[2]),
				Integer.parseInt(info[3]), Float.parseFloat(info[4]), Integer.parseInt(info[5])));
		handler.getWorld().getRoundLogic().manuallyDecrementZombiesLeft();
	}

	public boolean oneAlive() {
		for (PlayerMP player : otherPlayers) {
			if (player.getHealth() > 0) {
				return true;
			}
		}
		return false;
	}

	public void addPowerUpForClient(Message message) {
		switch (message.powerup) {
		case "maxAmmo":
			addPowerUp(new MaxAmmo(handler, message.id, message.x, message.y, message.z));
			break;
		case "nuke":
			addPowerUp(new Nuke(handler, message.id, message.x, message.y, message.z));
			break;
		case "doublePoints":
			addPowerUp(new DoublePoints(handler, message.id, message.x, message.y, message.z));
			break;
		case "instakill":
			addPowerUp(new InstaKill(handler, message.id, message.x, message.y, message.z));
			break;
		case "infiniteAmmo":
			addPowerUp(new InfiniteAmmo(handler, message.id, message.x, message.y, message.z));
			break;
		case "deathMachine":
			addPowerUp(new DeathMachine(handler, message.id, message.x, message.y, message.z));
			break;
		case "healthUp":
			addPowerUp(new HealthUp(handler, message.id, message.x, message.y, message.z));
			break;
		case "perkBag":
			addPowerUp(new PerkBag(handler, message.id, message.x, message.y, message.z));
		}
	}

	public PlayerMP getSpecificPlayer(String username) {
		for (PlayerMP e : otherPlayers) {
			if (username.equals(e.getUsername())) {
				return e;
			}
		}
		return null;
	}

	public void addOtherPlayer(PlayerMP p) {
		otherPlayers.add(p);

	}

	public CopyOnWriteArrayList<PlayerMP> getOtherPlayers() {
		return otherPlayers;
	}

	public void addCurrentPlayer(Player player) {
		currentPlayer = player;
		handler.setCurrentPlayer(player);
	}

	public void addBlood(Blood e) {
		blood.add(e);
	}

	public void addEntity(Entity e) {
		entities.add(e);
	}

	public void addZombie(Zombie e) {
		// entities.add(e);
		zombies.add(e);
		// zombies.addObject(e);
	}
	
	public CopyOnWriteArrayList<Explosion> getExplosions() {
		return explosions;
	}
	
	public void addExplosion(Explosion explosion) {
		explosions.add(explosion);
	}

	public void addInteractable(InteractableStaticEntity e) {
		// entities.add(e);
		interactables.add(e);
	}

	public void addPowerUp(PowerUps e) {
		powerups.add(e);
	}

	public void addTrap(Trap e) {
		traps.add(e);
	}

	public void addArea(Areas e) {
		areas.add(e);
	}

	public void addBarrier(Barrier e) {
		barriers.add(e);
		interactables.add(e);
	}

	public void addWall(Wall e) {
		walls.add(e);
	}

	// getters and setters.
	public Handler getHandler() {
		return handler;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public CopyOnWriteArrayList<PlayerMP> getPlayers() {
		return otherPlayers;
	}

	public CopyOnWriteArrayList<Entity> getEntities() {
		return entities;
	}

	public ArrayList<Areas> getAreas() {
		return areas;
	}

	public ArrayList<InteractableStaticEntity> getInteractables() {
		return interactables;
	}

	public void setEntities(CopyOnWriteArrayList<Entity> entities) {
		this.entities = entities;
	}

	public CopyOnWriteArrayList<Zombie> getZombies() {
		return zombies;
	}

	public void setZombies(CopyOnWriteArrayList<Zombie> zombies) {
		this.zombies = zombies;
	}

	public CopyOnWriteArrayList<PowerUps> getPowerups() {
		return powerups;
	}

	public void setPowerups(CopyOnWriteArrayList<PowerUps> powerups) {
		this.powerups = powerups;
	}

	public CopyOnWriteArrayList<Blood> getBlood() {
		return blood;
	}

	public ArrayList<Barrier> getBarriers() {
		return barriers;
	}

}

package project.game.horde.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
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
import project.game.horde.entities.statics.InvisibleBounds;
import project.game.horde.entities.statics.Wall;
import project.game.horde.entities.statics.traps.Trap;
import project.game.horde.main.Handler;
import project.game.horde.network.Message;

public class EntityManager {

    public long zombieHealth;

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
    private ArrayList<InvisibleBounds> boundaries;

    private ArrayList<Wall> walls;
    private Comparator<Entity> renderSorter = new Comparator<Entity>() {
        @Override
        public int compare(Entity a, Entity b) {
            if (a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
                return -1;
            }
            if (a.getY() + a.getHeight() > b.getY() + b.getHeight()) {
                return 1;
            }
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
                + areas.size() + interactables.size() + walls.size() + explosions.size() + otherPlayers.size();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public EntityManager(Handler handler) {
        this.handler = handler;
        otherPlayers = new CopyOnWriteArrayList<>();
        areas = new ArrayList<>();
        barriers = new ArrayList<>();
        blood = new CopyOnWriteArrayList<>();
        entities = new CopyOnWriteArrayList<>();
        interactables = new ArrayList<>();
        powerups = new CopyOnWriteArrayList<>();
        traps = new ArrayList<>();
        zombies = new CopyOnWriteArrayList<>();
        walls = new ArrayList<>();
        explosions = new CopyOnWriteArrayList<>();
        boundaries = new ArrayList<>();
    }

    public void tick() {
        Rectangle renderArea = new Rectangle(
                (int) (currentPlayer.getX() - handler.getWidth() / 2 / handler.getSettings().getZoomLevel(false) - 25),
                (int) (currentPlayer.getY() - handler.getHeight() / 2 / handler.getSettings().getZoomLevel(false) - 25),
                (int) (handler.getWidth() + 50), (int) (handler.getHeight() + 50));

        for (int i = 0; i < otherPlayers.size(); i++) {
            PlayerMP e = otherPlayers.get(i);
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
            if (!e.isActive()) {
                otherPlayers.remove(e);
            }

        }
        for (int i = 0; i < zombies.size(); i++) {
            Zombie e = zombies.get(i);
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
            if (!e.isActive()) {
                zombies.remove(e);
            }

        }
        for (Blood e : blood) {
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
            if (e.getCounter() >= e.getTimer()) {
                blood.remove(e);
            }

        }

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
            if (!e.isActive()) {
                entities.remove(e);
            }

        }
        entities.sort(renderSorter);
//		for (InteractableStaticEntity e : interactables) {
//			e.tick();
//		}

        for (int i = 0; i < interactables.size(); i++) {
            InteractableStaticEntity e = interactables.get(i);
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
            if (!e.isActive()) {
                interactables.remove(e);
            }
        }

        for (PowerUps e : powerups) {
            if (e != null) {
                e.tick();
                if (renderArea.intersects(e.getRenderBounds())) {
                    e.setRenderThis(true);
                } else {
                    e.setRenderThis(false);
                }
            }
            if (e != null && !e.isActive()) {
                powerups.remove(e);
            }
        }
        for (Trap e : traps) {
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
        }
        for (Areas e : areas) {
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
        }
        for (Barrier e : barriers) {
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
        }
        for (Explosion e : explosions) {
            if (e != null) {
                e.tick();
                if (renderArea.intersects(e.getRenderBounds())) {
                    e.setRenderThis(true);
                } else {
                    e.setRenderThis(false);
                }
            }
            if (e != null && !e.isActive()) {
                explosions.remove(e);
            }
        }
        currentPlayer.tick();
        for (Wall e : walls) {
            e.tick();
            if (renderArea.intersects(e.getRenderBounds())) {
                e.setRenderThis(true);
            } else {
                e.setRenderThis(false);
            }
        }
        for (InvisibleBounds e : boundaries) {
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

    public void render(Graphics g) {

        if (map != null) {
            map.render(g);
        }
        for (Areas e : areas) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }
        for (Trap e : traps) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }
        for (Blood e : blood) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }

        for (Barrier e : barriers) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }
        for (InteractableStaticEntity e : interactables) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }

        for (PowerUps e : powerups) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }
        for (Entity e : entities) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }
        for (Zombie e : zombies) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }
        for (PlayerMP e : otherPlayers) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }
        for (Explosion e : explosions) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }

        currentPlayer.render(g);

        for (Wall e : walls) {
            if (e.getRenderThis()) {
                e.render(g);
            }
        }

    }

    public void addZombieForClient(String[] info) {
        zombies.add(new Zombie(handler, Integer.parseInt(info[0]), Float.parseFloat(info[1]), Float.parseFloat(info[2]),
                Float.parseFloat(info[3]), Integer.parseInt(info[4])));
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
            case "maxAmmo" ->
                addPowerUp(new MaxAmmo(handler, message.id, message.x, message.y));
            case "nuke" ->
                addPowerUp(new Nuke(handler, message.id, message.x, message.y));
            case "doublePoints" ->
                addPowerUp(new DoublePoints(handler, message.id, message.x, message.y));
            case "instakill" ->
                addPowerUp(new InstaKill(handler, message.id, message.x, message.y));
            case "infiniteAmmo" ->
                addPowerUp(new InfiniteAmmo(handler, message.id, message.x, message.y));
            case "deathMachine" ->
                addPowerUp(new DeathMachine(handler, message.id, message.x, message.y));
            case "healthUp" ->
                addPowerUp(new HealthUp(handler, message.id, message.x, message.y));
            case "perkBag" ->
                addPowerUp(new PerkBag(handler, message.id, message.x, message.y));
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

    public ArrayList<InvisibleBounds> getBoundaries() {
        return boundaries;
    }

    public void addBoundary(InvisibleBounds boundary) {
        boundaries.add(boundary);
    }

}

package project.game.horde.entities.creatures.playerinfo;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.util.Random;

import project.game.horde.entities.EntityManager;
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
import project.game.horde.entities.powerups.PowerUpManager;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.utils.Node;
import project.game.horde.utils.Timer;

public class Blessings {

    private String blessing;
    private Handler handler;
    private Player player;
    private Inventory inv;
    private EntityManager em;

    private int currentPoints;
    private int maxMeterPoints;
    private int activations;
    private boolean running;
    private Timer blessingTimer;

    // debug
    private boolean ignoreRestriction = false;

    public Blessings(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        blessing = "";
        running = false;
        activations = 0;
        currentPoints = 0;
        maxMeterPoints = 3000;

        // debug
        //maxMeterPoints = 100;
    }

    public void tick() {
        inv = player.getInv();
        em = handler.getWorld().getEntityManager();

        if (running) {
            blessingTimer.tick();
            if (blessingTimer.isReady()) {
                running = false;
                blessingTimer = null;
                if (handler.getCurrentPlayer().getPeer() != null) {
                    handler.getCurrentPlayer().getPeer().sendActivatedBlessing(player.getUsername(), "");
                }

                // check if out of blessing, if so make it ""
            }
        }
    }

    public void activateBlessing() {
        if (ignoreRestriction && currentPoints >= maxMeterPoints && blessing != "" && running == false) {
            currentPoints = 0;
            activations++;
            maxMeterPoints = 200;
            doBlessing();
        } else if (currentPoints >= maxMeterPoints && blessing != "" && running == false) {
            if (handler.getBlessings().consumeBlessing(blessing)) {
                currentPoints = 0;
                activations++;
                maxMeterPoints += (500 * (activations * 1.5));
                doBlessing();
            }
        }
    }

    public void doBlessing() {
        PowerUpManager pm = handler.getWorld().getRoundLogic().getPowerups();
        int x = Math.round(player.getX());
        int y = Math.round(player.getY());
        switch (blessing) {
            case BlessingInventory.SPAWN_DOUBLE_POINTS ->
                pm.forceSpawnDoublePoints(x, y);
            case BlessingInventory.SPAWN_NUKE ->
                pm.forceSpawnNuke(x, y);
            case BlessingInventory.SPAWN_MAX_AMMO ->
                pm.forceSpawnMaxAmmo(x, y);
            case BlessingInventory.SPAWN_INFINITE_AMMO ->
                pm.forceSpawnInfiniteAmmo(x, y);
            case BlessingInventory.SPAWN_INSTAKILL ->
                pm.forceSpawnInstaKill(x, y);
            case BlessingInventory.GAIN_POINTS ->
                inv.gainPoints(500);
            case BlessingInventory.SPAWN_HEALTH ->
                pm.forceSpawnHealthUp(x, y);
            case BlessingInventory.SPAWN_MINIGUN ->
                pm.forceSpawnDeathMachine(x, y);
            case BlessingInventory.FORCE_CRAWLERS -> {
                for (Zombie zm : em.getZombies()) {
                    zm.turnToCrawler();
                }
            }
            case BlessingInventory.POINTS_MULTIPLY -> {
                running = true;
                blessingTimer = new Timer(3 * 60 * 60);
            }
            case BlessingInventory.GUARANTEE_HEADSHOTS -> {
                running = true;
                blessingTimer = new Timer(60 * 60);
            }
            case BlessingInventory.UPGRADE_WEAPON -> {
                if (player.getInv().getGun().isUpgraded()) {
                    maxMeterPoints -= 500;
                    currentPoints = maxMeterPoints;
                } else {
                    player.getInv().getGun().upgradeWeapon();
                }
            }
            case BlessingInventory.RANDOM_PERK ->
                pm.forceSpawnPerkBag(x, y);
            case BlessingInventory.SPAWN_ALL_DROPS -> {
                pm.forceSpawnDeathMachine(x, y);
                pm.forceSpawnDoublePoints(x, y);
                pm.forceSpawnHealthUp(x, y);
                pm.forceSpawnInfiniteAmmo(x, y);
                pm.forceSpawnInstaKill(x, y);
                pm.forceSpawnMaxAmmo(x, y);
                pm.forceSpawnNuke(x, y);
                pm.forceSpawnPerkBag(x, y);
            }
            case BlessingInventory.ROUND_SKIP -> {
                if (player.getPeer() != null) {
                    handler.getCurrentPlayer().getPeer().sendActivatedBlessing(player.getUsername(),
                            BlessingInventory.ROUND_SKIP);
                }
                handler.getRoundLogic().wipeRound();
                for (Zombie zm : handler.getWorld().getEntityManager().getZombies()) {
                    zm.dieByTrap();
                }
            }
            case BlessingInventory.RANDOM_POWERUP -> {
                Random rand = new Random();
                int rng = rand.nextInt(8);
                switch (rng) {
                    case 0 ->
                        pm.forceSpawnDoublePoints(x, y);
                    case 1 ->
                        pm.forceSpawnNuke(x, y);
                    case 2 ->
                        pm.forceSpawnMaxAmmo(x, y);
                    case 3 ->
                        pm.forceSpawnInfiniteAmmo(x, y);
                    case 4 ->
                        pm.forceSpawnInstaKill(x, y);
                    case 5 ->
                        pm.forceSpawnHealthUp(x, y);
                    case 6 ->
                        pm.forceSpawnDeathMachine(x, y);
                    case 7 ->
                        pm.forceSpawnPerkBag(x, y);
                    default ->
                        pm.forceSpawnDoublePoints(x, y);
                }
            }
            case BlessingInventory.TELEPORT -> {
                Random rand1 = new Random();
                Node node = handler.getWorld().getPathingLogic().getNodes().get(rand1.nextInt(handler.getWorld().getPathingLogic().getNodes().size()));
                Rectangle radius = new Rectangle(node.getX() - 100, node.getY() - 100, 200, 200);
                while (!node.withinPlayable() || findBlocked(radius) || findBlocked(player.getCollisionBounds(0, 0)) || !handler.getWorld().getRoomLogic().getOpenedRooms().contains(node.getRoom())) {
                    node = handler.getWorld().getPathingLogic().getNodes().get(rand1.nextInt(handler.getWorld().getPathingLogic().getNodes().size()));
                    radius = new Rectangle(node.getX() - 100, node.getY() - 100, 200, 200);

                }
                player.setX(node.getX() - player.getWidth() / 2);
                player.setY(node.getY() - player.getHeight() / 2);
                for (Zombie zm : em.getZombies()) {
                    zm.meander();
                    if (zm.getCollisionBounds(0, 0).intersects(radius)) {
                        zm.dieByTrap();
                    }
                }
            }
            case BlessingInventory.INVISIBILITY -> {
                if (handler.getCurrentPlayer().getPeer() != null) {
                    handler.getCurrentPlayer().getPeer().sendActivatedBlessing(player.getUsername(),
                            BlessingInventory.INVISIBILITY);
                }

                running = true;
                blessingTimer = new Timer(10 * 60);
            }
            case BlessingInventory.FREEZE_ALL_ZOMBIES -> {
                if (handler.getCurrentPlayer().getPeer() != null) {
                    handler.getCurrentPlayer().getPeer().sendActivatedBlessing(player.getUsername(),
                            BlessingInventory.FREEZE_ALL_ZOMBIES);
                }
                for (Zombie zm : em.getZombies()) {
                    zm.getFreezeStatus().freezeByBlessing();
                }
            }
            case BlessingInventory.GIVE_ALL_PERKS ->
                inv.giveAllPerks();
        }
    }

    public boolean findBlocked(Rectangle radius) {

        for (InteractableStaticEntity e : em.getInteractables()) {
            if (e.getCollisionBounds(0, 0).intersects(radius)) {
                return true;
            }
        }
        for (Wall e : em.getWalls()) {
            if (e.getCollisionBounds(0, 0).intersects(radius)) {
                return true;
            }
        }
        for (Barrier e : em.getBarriers()) {
            if (e.getPlayerBarrier().intersects(radius)) {
                return true;
            }
        }
        for (PlayerMP e : em.getOtherPlayers()) {
            if (e.getCollisionBounds(0, 0).intersects(radius)) {
                return true;
            }
        }
        return false;
    }

    public float getBlessingMeter() {
        if (!running) {
            return (float) currentPoints / (float) maxMeterPoints; 
        }else {
            return getBlessingTimer().getDecrementalProgress();
        }
    }

    public void addPoints(int dPoints) {
        if (running == false && blessing != "") {
            currentPoints += dPoints;

            if (currentPoints > maxMeterPoints) {
                currentPoints = maxMeterPoints;
            }
        }
    }

    public String getBlessing() {
        return blessing;
    }

    public void setBlessing(String blessing) {
        running = false;
        currentPoints = 0;
        this.blessing = blessing;
    }

    public boolean isRunning() {
        return running;
    }

    public Timer getBlessingTimer() {
        return blessingTimer;
    }

    public float getCurrent() {
        return currentPoints;
    }

    public float getMax() {
        return maxMeterPoints;
    }
}

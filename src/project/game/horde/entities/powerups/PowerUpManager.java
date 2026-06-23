package project.game.horde.entities.powerups;

import java.util.Random;

import project.game.horde.entities.EntityManager;
import project.game.horde.main.Handler;
import project.game.horde.utils.RandomUtil;

public class PowerUpManager {

    private Handler handler;
    // private Player handler.getCurrentPlayer();
    private EntityManager em;
    private int powerupsLeft, powerupsSpawned, powerupsRound;
    private int totalPowerups;
    private boolean maxAmmo, nuke, doublePoints, instakill, infiniteAmmo, deathMachine;

    private boolean doublePointsActive = false;
    private boolean instakillActive = false;
    private boolean infiniteAmmoActive = false;
    private boolean deathMachineActive = false;

    public PowerUpManager(Handler handler) {
        this.handler = handler;
        // this.player = handler.getCurrentPlayer();
        this.em = handler.getWorld().getEntityManager();
        powerupsLeft = 0;
        totalPowerups = 0;
        maxAmmo = false;
        nuke = false;
        doublePoints = false;
        instakill = false;
        deathMachine = false;
    }

    public void resetManager() {
        int currentRound = handler.getRoundLogic().getCurrentRound();

        if (currentRound < 8) {
            powerupsLeft = 1;
        } else if (currentRound < 14) {
            powerupsLeft = 2;
        } else if (currentRound < 21) {
            powerupsLeft = 3;
        } else {
            powerupsLeft = 4;
        }

        powerupsSpawned = 1;
        powerupsRound = powerupsLeft;

    }

    public boolean spawnMiniByLuna(int x, int y) {
        int rng = RandomUtil.nextInt(0, 100);
        if (powerupsSpawned > 60) {
            powerupsSpawned = 0;
        }
        if (totalPowerups > 60) {
            totalPowerups = 0;
        }
        if (rng < 40) {
            Random rand = new Random();
            int powerup = rand.nextInt(3);
            totalPowerups++;
            switch (powerup) {
                case 0 ->
                    handler.getWorld().getEntityManager()
                            .addPowerUp(new LunaClip(handler, totalPowerups, x, y));
                case 1 ->
                    handler.getWorld().getEntityManager()
                            .addPowerUp(new LunaHealth(handler, totalPowerups, x, y));
                case 2 ->
                    handler.getWorld().getEntityManager()
                            .addPowerUp(new LunaPoints(handler, totalPowerups, x, y));
            }
            return true;
        }
        return false;
    }

    public void spawnByLuna(int x, int y) {

        if (handler.getCurrentPlayer().getPeer() == null || handler.getCurrentPlayer().getPeer().isServer()) {
            int rng = RandomUtil.nextInt(0, 100);
            if (powerupsSpawned > 60) {
                powerupsSpawned = 0;
            }
            if (totalPowerups > 60) {
                totalPowerups = 0;
            }
            if (rng < 15) {
                Random rand = new Random();
                int powerup = rand.nextInt(10000);
                totalPowerups++;
                if (powerup < 3199) {
                    forceSpawnHealthUp(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("healthUp", totalPowerups, x, y);
//					return new HealthUp(handler, totalPowerups, x, y);
                } else if (powerup < 6199) {
                    forceSpawnDoublePoints(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("doublePoints", totalPowerups, x, y);
//					return new DoublePoints(handler, totalPowerups, x, y);
                } else if (powerup < 7199) {
                    forceSpawnInfiniteAmmo(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("infiniteAmmo", totalPowerups, x, y);
//					return new InfiniteAmmo(handler, totalPowerups, x, y);
                } else if (powerup < 8199) {
                    forceSpawnNuke(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("nuke", totalPowerups, x, y);
//					return new Nuke(handler, totalPowerups, x, y);
                } else if (powerup < 8899) {
                    forceSpawnInstaKill(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("instakill", totalPowerups, x, y);
//					return new InstaKill(handler, totalPowerups, x, y);
                } else if (powerup < 9499) {
                    forceSpawnDeathMachine(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("deathMachine", totalPowerups, x, y);
//					return new DeathMachine(handler, totalPowerups, x, y);
                } else if (powerup < 9999) {
                    forceSpawnMaxAmmo(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("maxAmmo", totalPowerups, x, y);
//					return new MaxAmmo(handler, totalPowerups, x, y);
                } else if (powerup == 9999) {
                    forceSpawnPerkBag(x, y);
//					if (handler.getCurrentPlayer().getPeer() != null)
//						handler.getCurrentPlayer().getPeer().sendNewPowerUp("perkBag", totalPowerups, x, y);
//					return new PerkBag(handler, totalPowerups, x, y);
                }
            }
        }
    }

    public boolean isPowerUpReady() {

        int rng = RandomUtil.nextInt(0, 200);
        //int rng = RandomUtil.nextInt(0, 10);

        // fail-safe for powerup id getting too large
        if (powerupsSpawned > 60) {
            powerupsSpawned = 0;
        }
        if (totalPowerups > 60) {
            totalPowerups = 0;
        }
        if (powerupsLeft == 0) {
            return false;
        }

        int zombiesLeft = handler.getRoundLogic().getZombiesLeft();
        int zpr = handler.getRoundLogic().getZpr();
        int checkpoint = (zpr / powerupsRound * powerupsSpawned) - 10;

        if (checkpoint >= (zpr - zombiesLeft) && rng < 20) {
            powerupsLeft--;
            return true;
        }
        if (checkpoint < (zpr - zombiesLeft)) {
            powerupsSpawned++;
        }

        return false;
    }

    public PowerUps generatePowerUp(float fx, float fy) {
        if (handler.getCurrentPlayer().getPeer() == null || handler.getCurrentPlayer().getPeer().isServer()) {
            int x = Math.round(fx);
            int y = Math.round(fy);
            Random rand = new Random();
            int rng = rand.nextInt(7);
            if (maxAmmo && nuke && doublePoints && instakill && infiniteAmmo) {
                maxAmmo = false;
                nuke = false;
                doublePoints = false;
                instakill = false;
                infiniteAmmo = false;
                deathMachine = false;
            }
            switch (rng) {
                case 0 -> {
                    if (maxAmmo) {
                        return generatePowerUp(x, y);
                    } else {
                        maxAmmo = true;
                        powerupsSpawned++;
                        totalPowerups++;
                        if (handler.getCurrentPlayer().getPeer() != null) {
                            handler.getCurrentPlayer().getPeer().sendNewPowerUp("maxAmmo", totalPowerups, x, y);
                        }
                        return new MaxAmmo(handler, totalPowerups, x, y);
                    }
                }
                case 1 -> {
                    if (nuke) {
                        return generatePowerUp(x, y);
                    } else {
                        nuke = true;
                        powerupsSpawned++;
                        totalPowerups++;
                        if (handler.getCurrentPlayer().getPeer() != null) {
                            handler.getCurrentPlayer().getPeer().sendNewPowerUp("nuke", totalPowerups, x, y);
                        }
                        return new Nuke(handler, totalPowerups, x, y);
                    }
                }
                case 2 -> {
                    if (doublePoints) {
                        return generatePowerUp(x, y);
                    } else {
                        doublePoints = true;
                        powerupsSpawned++;
                        totalPowerups++;
                        if (handler.getCurrentPlayer().getPeer() != null) {
                            handler.getCurrentPlayer().getPeer().sendNewPowerUp("doublePoints", totalPowerups, x, y);
                        }
                        return new DoublePoints(handler, totalPowerups, x, y);
                    }
                }
                case 3 -> {
                    if (instakill) {
                        return generatePowerUp(x, y);
                    } else {
                        instakill = true;
                        powerupsSpawned++;
                        totalPowerups++;
                        if (handler.getCurrentPlayer().getPeer() != null) {
                            handler.getCurrentPlayer().getPeer().sendNewPowerUp("instakill", totalPowerups, x, y);
                        }
                        return new InstaKill(handler, totalPowerups, x, y);
                    }
                }
                case 4 -> {
                    if (infiniteAmmo) {
                        return generatePowerUp(x, y);
                    } else {
                        infiniteAmmo = true;
                        powerupsSpawned++;
                        totalPowerups++;
                        if (handler.getCurrentPlayer().getPeer() != null) {
                            handler.getCurrentPlayer().getPeer().sendNewPowerUp("infiniteAmmo", totalPowerups, x, y);
                        }
                        return new InfiniteAmmo(handler, totalPowerups, x, y);
                    }
                }
                case 5 -> {
                    if (deathMachine) {
                        return generatePowerUp(x, y);
                    } else {
                        deathMachine = true;
                        powerupsSpawned++;
                        totalPowerups++;
                        if (handler.getCurrentPlayer().getPeer() != null) {
                            handler.getCurrentPlayer().getPeer().sendNewPowerUp("deathMachine", totalPowerups, x, y);
                        }
                        return new DeathMachine(handler, totalPowerups, x, y);
                    }
                }
                case 6 -> {
                    totalPowerups++;
                    if (handler.getCurrentPlayer().getPeer() != null) {
                        handler.getCurrentPlayer().getPeer().sendNewPowerUp("healthUp", totalPowerups, x, y);
                    }
                    return new HealthUp(handler, totalPowerups, x, y);
                }
            }
        }
        return null;

    }

    public void forceSpawnDoublePoints(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("doublePoints", x, y);
            } else {
                em.addPowerUp(new DoublePoints(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("doublePoints", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new DoublePoints(handler, totalPowerups, x, y));
        }

    }

    public void forceSpawnNuke(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("nuke", x, y);
            } else {
                em.addPowerUp(new Nuke(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("nuke", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new Nuke(handler, totalPowerups, handler.getCurrentPlayer().getX(),
                    handler.getCurrentPlayer().getY()));
        }
    }

    public void forceSpawnMaxAmmo(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("maxAmmo", x, y);
            } else {
                em.addPowerUp(new MaxAmmo(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("maxAmmo", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new MaxAmmo(handler, totalPowerups, x, y));
        }
    }

    public void forceSpawnInfiniteAmmo(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("infiniteAmmo", x, y);

            } else {
                em.addPowerUp(new InfiniteAmmo(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("infiniteAmmo", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new InfiniteAmmo(handler, totalPowerups, x, y));
        }
    }

    public void forceSpawnInstaKill(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("instakill", x, y);

            } else {
                em.addPowerUp(new InstaKill(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("instakill", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new InstaKill(handler, totalPowerups, x, y));
        }
    }

    public void forceSpawnHealthUp(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("healthUp", x, y);

            } else {
                em.addPowerUp(new HealthUp(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("healthUp", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new HealthUp(handler, totalPowerups, x, y));
        }
    }

    public void forceSpawnDeathMachine(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("deathMachine", x, y);

            } else {
                em.addPowerUp(new DeathMachine(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("deathMachine", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new DeathMachine(handler, totalPowerups, x, y));
        }
    }

    public void forceSpawnPerkBag(int x, int y) {
        totalPowerups++;
        if (handler.getCurrentPlayer().getPeer() != null) {
            if (!handler.getCurrentPlayer().getPeer().isServer()) {
                handler.getCurrentPlayer().getPeer().sendUserSpawnedPowerup("perkBag", x, y);

            } else {
                em.addPowerUp(new PerkBag(handler, totalPowerups, x, y));
                handler.getCurrentPlayer().getPeer().sendNewPowerUp("perkBag", totalPowerups, x, y);
            }
        } else {
            em.addPowerUp(new PerkBag(handler, totalPowerups, x, y));
        }
    }

    public boolean isDoublePointsActive() {
        return doublePointsActive;
    }

    public void setDoublePointsActive(boolean doublePointsActive) {
        this.doublePointsActive = doublePointsActive;
    }

    public boolean isInstakillActive() {
        return instakillActive;
    }

    public void setInstakillActive(boolean instakillActive) {
        this.instakillActive = instakillActive;
    }

    public boolean isInfiniteAmmoActive() {
        return infiniteAmmoActive;
    }

    public void setInfiniteAmmoActive(boolean infinteAmmoActive) {
        this.infiniteAmmoActive = infinteAmmoActive;
    }

    public boolean isDeathMachineActive() {
        return deathMachineActive;
    }

    public void setDeathMachineActive(boolean deathMachineActive) {
        this.deathMachineActive = deathMachineActive;
    }

}

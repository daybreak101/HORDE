package project.game.horde.entities.creatures.playerinfo;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import project.game.horde.entities.bullets.GasGrenade;
import project.game.horde.entities.bullets.Grenade;
import project.game.horde.entities.creatures.Player;
import project.game.horde.hud.PointGainElement;
import project.game.horde.input.GameMouseManager;
import project.game.horde.main.Handler;
import project.game.horde.perks.DeadShot;
import project.game.horde.perks.DoubleTap;
import project.game.horde.perks.Juggernaut;
import project.game.horde.perks.Luna;
import project.game.horde.perks.MuleKick;
import project.game.horde.perks.Perk;
import project.game.horde.perks.PhD;
import project.game.horde.perks.Revive;
import project.game.horde.perks.SleightOfHand;
import project.game.horde.perks.StaminUp;
import project.game.horde.perks.Stronghold;
import project.game.horde.perks.Vampire;
import project.game.horde.sounds.GunSounds;
import project.game.horde.sounds.MiscWeaponSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.FragGrenade;
import project.game.horde.weapons.Gun;
import project.game.horde.weapons.GunVars;
import project.game.horde.weapons.Knife;
import project.game.horde.weapons.M1911;
import project.game.horde.weapons.Minigun;

public class Inventory {

    private final Handler handler;
    private final Player player;
    private final Blessings blessings;

    private int currentGun;
    private Gun[] arsenal;
    private Perk[] perks;
    private int points;
    private int grenades;
    private Knife knife;
    private GasMask gasMask;
    private int specialGrenadeAmt;
    private int specialGrenadeType;

    private final Timer switchWeaponTimer;
    private final Timer grenadeReadyTimer;

    // perk variables
    // -1 means not equipped, 0-3 represents perk levels with 0 being base level
    private int jugg = -1, doubletap = -1, speedcola = -1, deadshot = -1, staminup = -1, phd = -1, vamp = -1, mule = -1,
            stronghold = -1, revive = -1, luna = -1;
    public boolean strongholdActivation = false;

    public Inventory(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
        this.blessings = new Blessings(handler, player);
        switchWeaponTimer = new Timer(30);
        grenadeReadyTimer = new Timer(30);

        arsenal = new Gun[6];
        arsenal[0] = new M1911(handler, player);
        arsenal[1] = null;
        arsenal[2] = null;
        arsenal[3] = new Minigun(handler, player);
        // arsenal[4] = new Knife(handler, player);
        arsenal[5] = new FragGrenade(handler, player);
        currentGun = 0;

        perks = new Perk[11];
        perks[0] = null;
        perks[1] = null;
        perks[2] = null;
        perks[3] = null;
        perks[4] = null;
        perks[5] = null;
        perks[6] = null;
        perks[7] = null;
        perks[8] = null;
        perks[9] = null;
        perks[10] = null;

        knife = new Knife(handler, player);
        gasMask = new GasMask(handler);
        gasMask.setCurrentDurability(0);

        specialGrenadeType = -1;
        specialGrenadeAmt = 3;

        grenades = 0;

        points = 500;

    }

    public void tick() {
        blessings.tick();
        switchWeaponTimer.tick();
        grenadeReadyTimer.tick();
        knife.tick();
        for (Perk i : perks) {
            if (i != null) {
                i.tick();
            }
        }

        if (!getGun().getOriginalName().equals(GunVars.FLAMETHROWER_NAME)) {
            Sounds.stopClip(GunSounds.FLAMETHROWER_SHOT_ID);
        }
    }

    public void render(Graphics g) {
        // drawLaser(g);
        knife.render(g);
        for (Perk i : perks) {
            if (i != null) {
                i.render(g);
            }
        }
    }

    public void drawLaser(Graphics g) {
        GameMouseManager mouse = player.getMouseManager();
        int size = 7;
        g.setColor(handler.getSettings().getLaserColor());
        g.fillOval(mouse.getMouseX() - size / 2, mouse.getMouseY() - size / 2, size, size);
    }

    public void wipePerksWhenDowned() {
        for (Perk p : perks) {
            if (p != null) {
                p.debuff();
            }
        }
        perks[0] = null;
        perks[1] = null;
        perks[2] = null;
        perks[3] = null;
        perks[4] = null;
        perks[5] = null;
        perks[6] = null;
        perks[7] = null;
        perks[8] = null;
        perks[9] = null;
        perks[10] = null;

        jugg = -1;
        doubletap = -1;
        speedcola = -1;
        deadshot = -1;
        staminup = -1;
        phd = -1;
        vamp = -1;
        mule = -1;
        stronghold = -1;
        revive = -1;
        luna = -1;
    }

    public boolean throwGrenade(int fuseTime) {
        if (grenadeReadyTimer.isReady() && grenades > 0) {
            if (mule >= 1) {
                Random rand = new Random();
                int rng = rand.nextInt(100);
                if (rng < MuleKick.LVL1_REGAINGRENADECHANCE) {
                    grenades--;
                }
            } else {
                grenades--;
            }
            Sounds.playClip(MiscWeaponSounds.GRENADE_TOSS, 1, 1, false);

            int destX = Math.round(player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset());
            int destY = Math.round(player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset());
            handler.getWorld().getEntityManager().addEntity(new Grenade(handler, player.getCenterX(),
                    player.getCenterY(), false, destX, destY, 0, player, fuseTime));
            if (phd >= 1) {
                handler.getWorld().getEntityManager().addEntity(new Grenade(handler, player.getCenterX() + 20,
                        player.getCenterY() + 20, false, destX, destY, 0, player, fuseTime));
                handler.getWorld().getEntityManager().addEntity(new Grenade(handler, player.getCenterX() - 20,
                        player.getCenterY() - 20, false, destX, destY, 0, player, fuseTime));
            }
            if (player.getPeer() != null) {
                player.getPeer().sendNewGrenade(player.getUsername(), GunVars.GRENADE_NAME, destX, destY);
            }
            return true;
        }
        return false;
    }

    public boolean throwSpecialGrenade() {
        if (grenadeReadyTimer.isReady() && specialGrenadeAmt > 0 && specialGrenadeType != -1) {
            if (mule >= 2) {
                Random rand = new Random();
                int rng = rand.nextInt(100);
                if (rng < MuleKick.LVL1_REGAINSPECIALGRENADECHANCE) {
                    specialGrenadeAmt--;
                }
            } else {
                specialGrenadeAmt--;
            }
            switch (specialGrenadeType) {
                case 0 -> {
                    Sounds.playClip(MiscWeaponSounds.GRENADE_TOSS, 1, 1, false);
                    int destX = Math.round(player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset());
                    int destY = Math.round(player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset());
                    handler.getWorld().getEntityManager()
                            .addEntity(new GasGrenade(handler, player.getCenterX(), player.getCenterY(),
                                    player.getMouseManager().getMouseX() + handler.getGameCamera().getxOffset(),
                                    player.getMouseManager().getMouseY() + handler.getGameCamera().getyOffset(), player));
                    if (player.getPeer() != null) {
                        player.getPeer().sendNewGrenade(player.getUsername(), GunVars.GAS_GRENADE_NAME, destX, destY);
                    }
                }
            }

            return true;
        }
        return false;
    }

    public void cancelReload() {
        if (getGun() != null) {
            Sounds.stopClip("shot");
            if (getGun().getIsReloading() == true) {
                getGun().setReloading(false);
                getGun().setReloadTimer(0);

            }
        }
    }

    public void switchWeapon() {
        if (switchWeaponTimer.counter >= switchWeaponTimer.limit) {
            // switchedWeapon = true;
            switchWeaponTimer.counter = 0;

            // cancel reload progress when switching weapons
            if (getGun() != null) {
                Sounds.stopClip("shot");
                if (getGun().getIsReloading() == true) {
                    getGun().setReloading(false);
                    getGun().setReloadTimer(0);

                }
            }

            // if on primary, switch to secondary, if it exists
            int sendCurrentGun = currentGun;
            switch (currentGun) {
                case 0 -> {
                    if (arsenal[1] != null) {
                        sendCurrentGun = 1; 
                    }else if (arsenal[2] != null && mule > -1) {
                        sendCurrentGun = 2;
                    }
                }
                case 1 -> {
                    if (arsenal[2] != null && mule > -1) {
                        sendCurrentGun = 2; 
                    }else if (arsenal[0] != null) {
                        sendCurrentGun = 0;
                    }
                }
                case 2 -> {
                    if (arsenal[0] != null) {
                        sendCurrentGun = 0; 
                    }else if (arsenal[1] != null) {
                        sendCurrentGun = 1;
                    }
                }
                case 3 -> {
                    if (arsenal[0] != null) {
                        sendCurrentGun = 0; 
                    }else if (arsenal[1] != null) {
                        sendCurrentGun = 1; 
                    }else if (arsenal[2] != null && mule > -1) {
                        sendCurrentGun = 2; 
                    }else {
                        sendCurrentGun = 0;
                    }
                }
                default -> {
                }
            }
            if (currentGun != sendCurrentGun && player.getPeer() != null) {
                player.getPeer().sendCurrentGun(player.getUsername(), arsenal[sendCurrentGun].getName());
            }
            currentGun = sendCurrentGun;
        }
    }

    public void maxAmmo() {
        for (Gun g : arsenal) {
            if (g != null) {
                if ("Flamethrower".equals(g.getOriginalName())) {
                    g.setCurrentClip(g.getClip()); 
                }else {
                    g.setCurrentReserve(g.getMaxReserve());
                }
                if (g.isDual()) {
                    g.setCurrentAltClip(g.getClip());
                }
            }
        }
        grenades = 4;

        if (specialGrenadeType != -1) {
            specialGrenadeAmt = 3;
        }
    }

    public void infiniteAmmo() {
        if (arsenal[currentGun] != null) {
            arsenal[currentGun].setCurrentClip(arsenal[currentGun].getClip());
            arsenal[currentGun].setReloading(false);
            if (arsenal[currentGun].isDual()) {
                arsenal[currentGun].setCurrentAltClip(arsenal[currentGun].getClip());
                arsenal[currentGun].setAltReloading(false);
            }

        }
        grenades = 4;
        if (specialGrenadeType != -1) {
            specialGrenadeAmt = 3;
        }
    }

    //ammo purchase does not fill current clip, this is intentional
    public void purchaseAmmo() {
        if (arsenal[currentGun] != null) {
            if ("Flamethrower".equals(arsenal[currentGun].getOriginalName())) {
                arsenal[currentGun].setCurrentClip(arsenal[currentGun].getClip()); 
            }else {
                arsenal[currentGun].setCurrentReserve(arsenal[currentGun].getMaxReserve());
            }
        }
    }

    public void roundReplenishGrenades() {
        grenades = grenades + 2;
        if (grenades > 4) {
            grenades = 4;
        }
    }

    public void gainPoints(int add) {
        if (blessings.isRunning() && "Extra Change".equals(blessings.getBlessing())) {
            add = add + add / 2;
        }
        if (handler.getRoundLogic().getPowerups().isDoublePointsActive()) {
            add = add * 2;
        }
        points += add;
        player.getStats().gainScore(add);
        player.getHud().addObject(new PointGainElement(handler, add, true));
        blessings.addPoints(add);
    }

    public boolean purchase(int price) {
        if (price <= points) {
            points -= price;
            player.getHud().addObject(new PointGainElement(handler, price, false));
            return true;
        }
        return false;
    }

    public void setGun(Gun gun) {
        int sendCurrentGun = currentGun;
        if (arsenal[0] == null) {
            arsenal[0] = gun;
            sendCurrentGun = 0;
        } else if (arsenal[1] == null) {
            arsenal[1] = gun;
            sendCurrentGun = 1;
        } else if (arsenal[2] == null && mule > -1) {
            arsenal[2] = gun;
            sendCurrentGun = 2;
        } else {
            arsenal[currentGun] = gun;
            if (player.getPeer() != null) {
                player.getPeer().sendCurrentGun(player.getUsername(), arsenal[currentGun].getName());
            }
        }

        if (currentGun != sendCurrentGun && player.getPeer() != null) {
            player.getPeer().sendCurrentGun(player.getUsername(), arsenal[sendCurrentGun].getName());
        }
        currentGun = sendCurrentGun;
    }

    public boolean checkArsenal(Gun gun) {
        for (int i = 0; i < 3; i++) {
            if (arsenal[i] != null) {
                if (arsenal[i].getOriginalName() == null ? gun.getName() == null : arsenal[i].getOriginalName().equals(gun.getName())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void giveAllPerks() {
        ArrayList<Perk> all = new ArrayList<>();
        all.add(new Juggernaut(handler, handler.getUnlocks().getJuggLvl(), player));
        all.add(new SleightOfHand(handler, handler.getUnlocks().getSpeedLvl(), player));
        all.add(new DoubleTap(handler, handler.getUnlocks().getDoubletapLvl(), player));
        all.add(new Revive(handler, handler.getUnlocks().getReviveLvl(), player));
        all.add(new StaminUp(handler, handler.getUnlocks().getStaminaLvl(), player));
        all.add(new PhD(handler, handler.getUnlocks().getPhdLvl(), player));
        all.add(new MuleKick(handler, handler.getUnlocks().getMuleLvl(), player));
        all.add(new DeadShot(handler, handler.getUnlocks().getDeadshotLvl(), player));
        all.add(new Stronghold(handler, handler.getUnlocks().getStrongholdLvl(), player));
        all.add(new Luna(handler, handler.getUnlocks().getLunaLvl(), player));
        all.add(new Vampire(handler, handler.getUnlocks().getVampireLvl(), player));

        for (Perk perk : all) {
            if (!checkPerks(perk)) {
                givePerk(perk);
            }
        }
    }

    public boolean checkPerks(Perk perk) {
        for (int i = 0; i < 8; i++) {
            if (perks[i] != null) {
                if (perks[i].getName() == null ? perk.getName() == null : perks[i].getName().equals(perk.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addPerk(Perk perk) {
        for (int i = 0; i < 4; i++) {
            if (perks[i] == null) {
//				if (perk.getLevel() == 3 && i != 3) {
//					perk.setLevel(2);
//				}
                perks[i] = perk;
                perks[i].buff();
                break;
            }
        }
    }

    public void givePerk(Perk perk) {
        for (int i = 0; i < 11; i++) {
            if (perks[i] == null) {
//				if (perk.getLevel() == 3 && i != 3) {
//					perk.setLevel(2);
//				}
                perks[i] = perk;
                perks[i].buff();
                break;
            }
        }
    }

    public boolean checkPerkEmptySpot() {
        for (int i = 0; i < 4; i++) {
            if (perks[i] == null) {
                return true;
            }
        }
        return false;
    }

    public void removeGunForUpgrade() {
        arsenal[currentGun] = null;
        switchWeapon();
    }

    public Gun[] getArsenal() {
        return arsenal;
    }

    public Knife getKnife() {
        return knife;
    }

    public void setKnife(Knife newMelee) {
        knife = newMelee;
    }

    public void setGasMask(GasMask gasMask) {
        this.gasMask = gasMask;
    }

    public GasMask getGasMask() {
        return gasMask;
    }

    public int getGrenades() {
        return grenades;
    }

    public int getPoints() {
        return points;
    }

    public Gun getGun() {
        return arsenal[currentGun];
    }

    public Perk[] getPerks() {
        return perks;
    }

    public int getCurrentGun() {
        return currentGun;
    }

    public void setCurrentGun(int currentGun) {
        this.currentGun = currentGun;
        if (player.getPeer() != null) {
            player.getPeer().sendCurrentGun(player.getUsername(), arsenal[currentGun].getName());
        }
    }

    public void setJugg(int jugg) {
        this.jugg = jugg;
        if (jugg != -1) {
            player.setHealth();
        }
    }

    public int getDoubletap() {
        return doubletap;
    }

    public void setDoubletap(int doubletap) {
        this.doubletap = doubletap;
    }

    public int getSpeedcola() {
        return speedcola;
    }

    public void setSpeedcola(int speedcola) {
        this.speedcola = speedcola;
    }

    public int getDeadshot() {
        return deadshot;
    }

    public void setDeadshot(int deadshot) {
        this.deadshot = deadshot;
    }

    public int getStaminup() {
        return staminup;
    }

    public void setStaminup(int staminup) {
        this.staminup = staminup;
    }

    public int getPhd() {
        return phd;
    }

    public void setPhd(int phd) {
        this.phd = phd;
    }

    public int getVamp() {
        return vamp;
    }

    public void setVamp(int vamp) {
        this.vamp = vamp;
    }

    public int getMule() {
        return mule;
    }

    public void setMule(int mule) {
        this.mule = mule;
    }

    public int getStronghold() {
        return stronghold;
    }

    public void setStronghold(int stronghold) {
        this.stronghold = stronghold;
    }

    public int getRevive() {
        return revive;
    }

    public void setRevive(int revive) {
        this.revive = revive;
    }

    public int getLuna() {
        return luna;
    }

    public void setLuna(int luna) {
        this.luna = luna;
    }

    public int getJugg() {
        return jugg;
    }

    public int getSpecialGrenadeAmt() {
        return specialGrenadeAmt;
    }

    public int getSpecialGrenadeType() {
        return specialGrenadeType;
    }

    public void setSpecialGrenade(int type) {
        specialGrenadeType = type;
        specialGrenadeAmt = 3;
    }

    public Blessings getBlessings() {
        return blessings;
    }
}

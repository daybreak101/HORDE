package project.game.horde.entities.creatures;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.playerinfo.BurnStatusForPlayer;
import project.game.horde.entities.creatures.playerinfo.FreezeStatusForPlayer;
import project.game.horde.entities.creatures.playerinfo.Inventory;
import project.game.horde.entities.creatures.playerinfo.PlayerActionState;
import project.game.horde.entities.creatures.playerinfo.PlayerInput;
import project.game.horde.entities.creatures.playerinfo.PlayerMovementState;
import project.game.horde.entities.creatures.playerinfo.PlayerSprint;
import project.game.horde.entities.creatures.playerinfo.Stats;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.InvisibleBounds;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.CharAssets;
import project.game.horde.graphics.GameCamera;
import project.game.horde.hud.GameplayElement;
import project.game.horde.hud.HudManager;
import project.game.horde.hud.LeaderboardElement;
import project.game.horde.hud.RevivingElement;
import project.game.horde.input.GameMouseManager;
import project.game.horde.input.KeyManager;
import project.game.horde.main.CustomHatInventory;
import project.game.horde.main.Handler;
import project.game.horde.main.User;
import project.game.horde.network.Peer;
import project.game.horde.perks.Juggernaut;
import project.game.horde.perks.PhD;
import project.game.horde.perks.Stronghold;
import project.game.horde.perks.Vampire;
import project.game.horde.sounds.CreatureSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;
import project.game.horde.weapons.Gun.GunImageDim;

public class Player extends Creature {

    Peer peer;
    boolean isOnline;
    User user;
    String username;
    Inventory inv;
    Stats stats;

    // hud
    private HudManager hud;
    private RevivingElement reviveHud = null;

    // buffs & statuses
    private PlayerActionState actionState = PlayerActionState.IDLE;
    private PlayerMovementState moveState = PlayerMovementState.IDLE;
    private PlayerMP playerReviving = null;
    private boolean justTookDamage = false;
    private boolean isReviving = false;
    private float weight;
    private float defaultSpeed;
    PlayerSprint playerSprint;
    BurnStatusForPlayer burnStatus;
    FreezeStatusForPlayer freezeStatus;
    float strongholdDamageMultiplier = 0.0f;
    private int tempHealth = 0;
    private int armor;

    // input
    private PlayerInput playerInput;
    private Ellipse2D.Float interactRadius = new Ellipse2D.Float();

    // camera
    private GameCamera gameCamera;

    // customization
    private BufferedImage[] skin = null;
    private int hat = 0;

    //debug
    private boolean coords = false;

    // online
    public Player(Handler handler, float x, float y, Peer peer) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.peer = peer;
        this.user = peer.getLocalUser();
        this.username = user.getUsername();
        isOnline = true;
        initPlayer();
    }

    // offline
    public Player(Handler handler, float x, float y, User user) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.user = user;
        this.username = user.getUsername();
        isOnline = false;
        initPlayer();
    }

    private void initPlayer() {
        hud = new HudManager(handler, this);
        hud.addObject(new GameplayElement(handler, this));
        gameCamera = new GameCamera(handler, 0, 0);
        gameCamera.centerOnEntity(this);
        inv = new Inventory(handler, this);
        playerInput = new PlayerInput(handler, this);
        stats = new Stats(handler);
        playerSprint = new PlayerSprint(this);
        bounds = new Rectangle(5, 5, 65, 65);

        speed = 4.0f;
        defaultSpeed = speed;
        health = 100;

        burnStatus = new BurnStatusForPlayer(this);
        freezeStatus = new FreezeStatusForPlayer(handler, this);

        skin = handler.getSkinInv().getSkin(handler.getSkinInv().getEquippedSkin());
        hat = handler.getHatInv().getEquippedHat();
    }

    Timer tookDamageTimer = new Timer(60);

    @Override
    public void tick() {

//		if (justTookDamage == true) {
//			timer++;
//			if (timer == 50) {
//				justTookDamage = false;
//				timer = 0;
//			}
//		}
        isReviving = false;
        playerInput.tick();
        if (health <= 0 && inv.getRevive() > -1
                && handler.getWorld().getEntityManager().getOtherPlayers().isEmpty()) {
            reviving();
            if (inv.getRevive() >= 2) {
                playerInput.getDownedInput();
                move();
                handler.getGameCamera().centerOnEntity(this);
                if (inv.getGun() != null) {
                    inv.getGun().tick();
                }
            }
        } else if (health <= 0) {
            if (!isOnline) {
                die();
            } else {
                boolean oneAlive = false;
                for (PlayerMP players : handler.getWorld().getEntityManager().getOtherPlayers()) {
                    if (players.getHealth() > 0) {
                        oneAlive = true;
                    }
                }
                if (!oneAlive) {
                    die();
                } else {
                    if (inv.getRevive() >= 2) {
                        playerInput.getDownedInput();
                        move();
                        handler.getGameCamera().centerOnEntity(this);
                        if (inv.getGun() != null) {
                            inv.getGun().tick();
                        }
                    }
                }
            }

        } else {
            interactRadius.setFrame(getCenterX() - 50, getCenterY() - 50, 100, 100);
            playHeartbeat();
            setClosestNode();
            freezeStatus.checkIfInIcyWater();
            freezeStatus.freezing();
            tookDamageTimer.tick();
            if (tookDamageTimer.isReady()) {
                justTookDamage = false;
                tookDamageTimer.resetTimer();
            }
            if (freezeStatus.isFrozen()) {
                playerInput.getInput();
            }
            freezeStatus.getBreakCooldown().tick();
            if (!freezeStatus.isFrozen()) {
                playerSprint.setSprintMultiplier(1);
                move();

                if (inv.getGun() != null) {
                    inv.getGun().tick();
                    weight = inv.getGun().getWeight();
                    speed = defaultSpeed - weight;
                } else {
                    speed = defaultSpeed;
                }
                inv.tick();
                playerInput.getInput();

                handler.getGameCamera().centerOnEntity(this);

                playerSprint.sprinting();
                burnStatus.burn();
            }
        }
        hud.tick();
        // System.out.println("Closest Player Node: " +
        // handler.getWorld().getPathingLogic().getClosestNode(getCenterX(),
        // getCenterY()));
        // System.out.println("Closest Player Node: " + closestNode);
        float mouseX = playerInput.getMouseManager().getMouseX();
        float mouseY = playerInput.getMouseManager().getMouseY();
        angle = (float) Math.toDegrees(Math.atan2(-(x - handler.getGameCamera().getxOffset() - mouseX + width / 2),
                y - handler.getGameCamera().getyOffset() - mouseY + height / 2));
        sendRotateUpdate.tick();
        if (lastAngle != angle && isOnline && sendRotateUpdate.isReady()) {
            peer.sendNewAngle(username, angle);
        }
        lastAngle = angle;
    }

    public void playHeartbeat() {
        if (health <= 20 && health > 0) {
            Sounds.stopClip(CreatureSounds.SLOW_HEARTBEAT);
            Sounds.playClip(CreatureSounds.FAST_HEARTBEAT, 1, 1, false);
        } else if (health <= 40 && health > 20) {
            Sounds.stopClip(CreatureSounds.FAST_HEARTBEAT);
            Sounds.playClip(CreatureSounds.SLOW_HEARTBEAT, 1, 1, false);
        } else {
            Sounds.stopClip(CreatureSounds.SLOW_HEARTBEAT);
            Sounds.stopClip(CreatureSounds.FAST_HEARTBEAT);
        }
    }

    public void cancelRevive() {
        if (playerReviving != null) {
            playerReviving.cancelRevive();
            playerReviving = null;
            removeReviveHud();
        }
    }

    private int reviveProgress = 0;
    private final int reviveMax = 300;

    public void reviving() {
        isReviving = true;
        reviveProgress++;
        if (reviveProgress >= reviveMax) {
            reviveProgress = 0;
            inv.wipePerksWhenDowned();
            setHealth();
            isReviving = false;
        }
        if (isReviving && reviveHud == null) {
            reviveHud = new RevivingElement(handler);
            hud.addObject(reviveHud);
        } else if (isReviving && reviveHud != null) {

        } else {
            hud.removeObject(reviveHud);
            reviveHud = null;
        }

    }

    public int getReviveProgress() {
        return reviveProgress;
    }

    public int getReviveMax() {
        return reviveMax;
    }

    public Rectangle getHitbox() {
        return new Rectangle((int) (x + bounds.x + 15), (int) (y + bounds.y + 15), bounds.width - 30,
                bounds.height - 30);

    }

    @Override
    public void takeDamage(int damage) {
        if (justTookDamage) {
            return;
        }
        resetDamageRender = true;
        if (playerReviving != null && inv.getRevive() >= 2) {
            damage /= 2;
        }
        if (armor > 0) {
            armor = armor - damage;
            if (armor < 0) {
                damage = -armor;
                armor = 0;
            } else {
                damage = 0;
            }
        }
        if (tempHealth > 0) {
            tempHealth = tempHealth - damage;
            if (tempHealth < 0) {
                damage = -tempHealth;
                tempHealth = 0;
            } else {
                damage = 0;
            }
        }
        health = health - damage;
        freezeStatus.breakPlayerIceWhenHit();
        if (isOnline) {
            peer.sendNewHealth(username, health);
            peer.sendUserTookDamage(username);
        }
    }

    boolean died = false;

    public void die() {
        if (!died) {
            stats.gainDown();
            handler.getGlobalStats().calculateNewAverageRound(handler.getRoundLogic().getCurrentRound());
            died = true;
            System.out.println("YOU LOSE");
            hud.getObjects().clear();
            hud.setInvisible();
            hud.getObjects().add(new LeaderboardElement(handler, this, user));
        }

    }

    public boolean moved = false;

    private final Timer sendMoveUpdate = new Timer(2);

    @Override
    public void move() {
        boolean sendUpdate = false;
        sendMoveUpdate.tick();
        if (sendMoveUpdate.isReady()) {
            sendUpdate = true;
        }
        if (!checkEntityCollisions(xMove, 0f)) {
            moveX();
            if (isOnline && Math.abs(xMove) > 0 && sendUpdate) {
                peer.sendNewX(username, x);
            }
        }

        if (!checkEntityCollisions(0f, yMove)) {
            moveY();
            if (isOnline && Math.abs(yMove) > 0 && sendUpdate) {
                peer.sendNewY(username, y);
            }
        }
    }

    @Override
    public void setX(float x) {
        this.x = x;
        if (isOnline) {
            peer.sendNewX(username, x);
        }
    }

    @Override
    public void setY(float y) {
        this.y = y;
        if (isOnline) {
            peer.sendNewY(username, y);
        }
    }

    @Override
    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        for (PlayerMP e : handler.getWorld().getEntityManager().getOtherPlayers()) {
            if (e.getCollisionBounds(1f, 1f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {

            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
            if (e.equals(this)) {
                continue;
            }

            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (Barrier e : handler.getWorld().getEntityManager().getBarriers()) {
            if (e.getPlayerBarrier().intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (e.getCollisionBounds(0, 0).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        for (InvisibleBounds e : handler.getWorld().getEntityManager().getBoundaries()) {
            if (e.getCollisionBounds().intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public void interact() {

        // Ellipse2D.Float radius = new Ellipse2D.Float(getCenterX() - 50, getCenterY() - 50, 100, 100);
        InteractableStaticEntity closestInteract = null;
        float closestDist = 2000000;
        float eDist;
        for (PlayerMP others : handler.getWorld().getEntityManager().getOtherPlayers()) {
            if (interactRadius.intersects(others.getCollisionBounds(0, 0))
                    && others.getHealth() <= 0) {
                if (others.progressRevive()) {
                    isReviving = false;
                    playerReviving = null;
                    actionState = PlayerActionState.RECOVER;
                    hud.removeObject(reviveHud);
                    reviveHud = null;
                } else {
                    isReviving = true;
                    playerReviving = others;
                    actionState = PlayerActionState.REVIVING;
                    reviveHud = new RevivingElement(handler);
                    hud.addObject(reviveHud);
                }

                return;
            }
        }

        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            eDist = Utils.getEuclideanDistance(x, y, e.getX(), e.getY());
            if (closestInteract == null) {
                closestInteract = e;
                closestDist = eDist;
            }
            if (eDist < closestDist) {
                closestInteract = e;
                closestDist = eDist;
            }
        }

        if (closestInteract != null) {
            if (interactRadius.intersects(closestInteract.getTriggerRange())) {
                closestInteract.fulfillInteraction(this);
            }
        }

    }

    public boolean checkIfInStrongholdCircle() {
        if (!strongholdRadius.intersects(getCollisionBounds(0, 0))) {
            getInv().strongholdActivation = false;
            removeArmor();
            removeStrongholdDamageMultiplier();
            return false;
        }
        return true;
    }

    private Ellipse2D strongholdRadius;

    public void setStrongholdCircle() {
        getInv().strongholdActivation = true;
        strongholdRadius = new Ellipse2D.Float(x + width / 2 - 125, y + height / 2 - 125, 250, 250);

    }

    public Ellipse2D getStrongholdRadius() {
        return strongholdRadius;
    }

    public void renderHUD(Graphics g) {
        hud.render(g);
    }

    public void renderLaser(Graphics g) {
        inv.drawLaser(g);
    }

    float angle, lastAngle;
    private final Timer sendRotateUpdate = new Timer(3);

    boolean resetDamageRender = false;
    int alpha = 0;

    public void renderDamage(Graphics g) {
        if (resetDamageRender) {
            alpha = 255;
            resetDamageRender = false;
        }
        if (justTookDamage) {
            if (alpha > 0) {
                alpha -= 5;
                if (alpha < 0) {
                    alpha = 0;
                }
            }

            Graphics2D g2d = (Graphics2D) g;

            int w = handler.getWidth();
            int h = handler.getHeight();
            int border = 100; // Thickness of the effect

            // Top
            g2d.setPaint(new GradientPaint(0, 0,
                    new Color(255, 0, 0, alpha),
                    0, border,
                    new Color(255, 0, 0, 0)));
            g2d.fillRect(0, 0, w, border);

            // Bottom
            g2d.setPaint(new GradientPaint(0, h,
                    new Color(255, 0, 0, alpha),
                    0, h - border,
                    new Color(255, 0, 0, 0)));
            g2d.fillRect(0, h - border, w, border);

            // Left
            g2d.setPaint(new GradientPaint(0, 0,
                    new Color(255, 0, 0, alpha),
                    border, 0,
                    new Color(255, 0, 0, 0)));
            g2d.fillRect(0, 0, border, h);

            // Right
            g2d.setPaint(new GradientPaint(w, 0,
                    new Color(255, 0, 0, alpha),
                    w - border, 0,
                    new Color(255, 0, 0, 0)));
            g2d.fillRect(w - border, 0, border, h);
        }
    }

    public void renderStronghold(Graphics g) {
        if (getInv().strongholdActivation) {
            g.setColor(new Color(0, 0, 200, 50));
            g.fillOval((int) (strongholdRadius.getX() - handler.getGameCamera().getxOffset()),
                    (int) (strongholdRadius.getY() - handler.getGameCamera().getyOffset()), 250, 250);
            g.setColor(new Color(100, 0, 100));
            g.drawOval((int) (strongholdRadius.getX() - handler.getGameCamera().getxOffset()),
                    (int) (strongholdRadius.getY() - handler.getGameCamera().getyOffset()), 250, 250);
        }
    }

    public void renderShadow(Graphics g) {
        g.drawImage(Assets.shadow, (int) (x - 10 - handler.getGameCamera().getxOffset()),
                (int) (y - 10 - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public void renderBurn(Graphics g) {
        if (burnStatus.isBurning() && health > 0) {
            g.setColor(Color.orange);
            g.fillOval((int) (x - 10 - handler.getGameCamera().getxOffset()),
                    (int) (y - 10 - handler.getGameCamera().getyOffset()), width + 25, height + 25);
        }
    }

    public void renderUsername(Graphics g) {
        if (username != null && health > 0) {
            g.setColor(handler.getSettings().getHudColor());
            Utils.drawCenteredString(g, username,
                    new Rectangle((int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y - handler.getGameCamera().getyOffset()), width, 12),
                    new Font(Font.DIALOG, Font.PLAIN, 12));
        }
    }

    public void renderCoords(Graphics g) {
        if (coords) {
            g.setColor(Color.black);
            Utils.drawCenteredString(g, getCenterX() + ", " + getCenterY(),
                    new Rectangle((int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y + height - handler.getGameCamera().getyOffset()), width, 12),
                    new Font(Font.DIALOG, Font.PLAIN, 15));
        }
    }

    public void renderGun(Graphics2D g2d) {
        GunImageDim dim = inv.getGun().getGunImageDim();
        if (dim == null) {
            return;
        }

        if (actionState == PlayerActionState.COOKING_GRENADE) {
            g2d.drawImage(Assets.frag, (int) (x + 40 - handler.getGameCamera().getxOffset()),
                    (int) (y - 10 - handler.getGameCamera().getyOffset()), 30, 30, null);

        } else if (actionState == PlayerActionState.EATING || actionState == PlayerActionState.RECOVER_EATING) {
            g2d.drawImage(Assets.chipBag, (int) (x + 40 - handler.getGameCamera().getxOffset()),
                    (int) (y + 0 - handler.getGameCamera().getyOffset()), 30, 30, null);

        } else if (inv.getGun().isDual()) {
            g2d.drawImage(inv.getGun().getGunImage(),
                    (int) (x - 10 + dim.startX - handler.getGameCamera().getxOffset()),
                    (int) (y - dim.startY - handler.getGameCamera().getyOffset()), dim.width, dim.height, null);
            g2d.drawImage(inv.getGun().getGunImage(), (int) (x + 8 + dim.startX - handler.getGameCamera().getxOffset()),
                    (int) (y - dim.startY - handler.getGameCamera().getyOffset()), dim.width, dim.height, null);

        } else {
            g2d.drawImage(inv.getGun().getGunImage(), (int) (x + dim.startX - handler.getGameCamera().getxOffset()),
                    (int) (y - dim.startY - handler.getGameCamera().getyOffset()), dim.width, dim.height, null);

        }
    }

    public void renderCharacterCustoms(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        if (health <= 0) {
            g2d.drawImage(Assets.player[3], (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
            g2d.setTransform(old);
        } else {
            g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
                    y - handler.getGameCamera().getyOffset() + height / 2);

            g2d.drawImage(skin[0], (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

            renderGun(g2d);

            g2d.drawImage(skin[1], (int) (x - handler.getGameCamera().getxOffset()),
                    (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

            switch (hat) {
                case CustomHatInventory.CHRISTMAS ->
                    g2d.drawImage(CharAssets.christmasHat, (int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y + 25 - handler.getGameCamera().getyOffset()), width, height, null);
                case CustomHatInventory.REINDEER ->
                    g2d.drawImage(CharAssets.reindeer, (int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y + 25 - handler.getGameCamera().getyOffset()), width, height, null);
                case CustomHatInventory.BUNNY ->
                    g2d.drawImage(CharAssets.bunny, (int) (x - handler.getGameCamera().getxOffset()),
                            (int) (y + 25 - handler.getGameCamera().getyOffset()), width, height, null);
                default -> {
                }
            }

            g2d.setTransform(old);
            //renderDamage(g);
        }
    }

    public void renderInteractRadius(Graphics g) {
        g.setColor(Color.red);
        g.fillOval((int) (interactRadius.getX() - handler.getGameCamera().getxOffset()),
                (int) (interactRadius.getY() - handler.getGameCamera().getyOffset()),
                (int) interactRadius.getWidth(), (int) interactRadius.getHeight());
    }

    @Override
    public void render(Graphics g) {
        inv.render(g);
        // hud.render(g);
//		float mouseX = playerInput.getMouseManager().getMouseX();
//		float mouseY = playerInput.getMouseManager().getMouseY();
//		angle = (float) Math.toDegrees(Math.atan2(-(x - handler.getGameCamera().getxOffset() - mouseX + width / 2),
//				y - handler.getGameCamera().getyOffset() - mouseY + height / 2));
//		sendRotateUpdate.tick();
//		if (lastAngle != angle && isOnline && sendRotateUpdate.isReady()) {
//			peer.sendNewAngle(username, angle);
//		}
//		lastAngle = angle;
        renderStronghold(g);
        //renderInteractRadius(g);
        renderShadow(g);
        renderBurn(g);
        renderCharacterCustoms(g);
        renderUsername(g);
        renderCoords(g);
        //renderHUD(g);
    }

    public void drawCoords() {
        coords = true;
    }

    @Override
    public void setClosestNode() {
        closestNode = handler.getWorld().getPathingLogic().getClosestNode(getCenterX(), getCenterY());
    }

    @Override
    public int getClosestNode() {
        closestNode = handler.getWorld().getPathingLogic().getClosestNode(getCenterX(), getCenterY());
        return closestNode;
    }

    public PlayerSprint getPlayerSprint() {
        return playerSprint;
    }

    public void gainStrongholdArmor(int dArmor) {
        if (inv.getStronghold() >= 2) {
            if (armor < Stronghold.LVL2_MAXARMOR) {
                armor += dArmor;
            }
            if (armor > Stronghold.LVL2_MAXARMOR) {
                armor = Stronghold.LVL2_MAXARMOR;
            }
        } else if (inv.getStronghold() >= 0) {
            if (armor < Stronghold.BASE_MAXARMOR) {
                armor += dArmor;
            }
            if (armor > Stronghold.BASE_MAXARMOR) {
                armor = Stronghold.BASE_MAXARMOR;
            }
        }

    }

    public void gainStrongholdDamageMultiplier(float dDamageMultiplier) {

        if (inv.getStronghold() >= 2) {
            if (strongholdDamageMultiplier < Stronghold.LVL2_DAMAGEBUFFCAP) {
                strongholdDamageMultiplier += dDamageMultiplier;
            }
            if (strongholdDamageMultiplier > Stronghold.LVL2_DAMAGEBUFFCAP) {
                strongholdDamageMultiplier = Stronghold.LVL2_DAMAGEBUFFCAP;
            }
        } else if (inv.getStronghold() >= 1) {
            if (strongholdDamageMultiplier < Stronghold.LVL1_DAMAGEBUFFCAP) {
                strongholdDamageMultiplier += dDamageMultiplier;
            }
            if (strongholdDamageMultiplier > Stronghold.LVL1_DAMAGEBUFFCAP) {
                strongholdDamageMultiplier = Stronghold.LVL1_DAMAGEBUFFCAP;
            }
        }
    }

    public void removeArmor() {
        if (armor > 0) {
            armor = 0;
        }
    }

    public void removeStrongholdDamageMultiplier() {
        if (strongholdDamageMultiplier > 0) {
            strongholdDamageMultiplier = 0;
        }
    }

    public int getArmor() {
        return armor;
    }

    public float getStrongholdDamageMultiplier() {
        return strongholdDamageMultiplier;
    }

    public void setHealth() {
        if (health <= 0) {
            health = 0;
            inv.wipePerksWhenDowned();
        }
        if (inv.getJugg() == 0 && !(health > Juggernaut.BASE_HEALTHBUFF)) {
            health = Juggernaut.BASE_HEALTHBUFF;
        } else if (inv.getJugg() == 1 && !(health > Juggernaut.LVL1_HEALTHBUFF)) {
            health = Juggernaut.LVL1_HEALTHBUFF;
        } else if (inv.getJugg() == 2 && !(health > Juggernaut.LVL2_HEALTHBUFF)) {
            health = Juggernaut.LVL2_HEALTHBUFF;
        } else if (inv.getJugg() == 3 && !(health > Juggernaut.LVL3_HEALTHBUFF)) {
            health = Juggernaut.LVL3_HEALTHBUFF;
        } else if (inv.getJugg() == -1 && !(health > 100)) {
            health = 100;
        }
        if (isOnline) {
            peer.sendNewHealth(username, health);
        }
    }

    public void gainHealth(int amount) {
        if (health <= 0) {
            health = 0;
            inv.wipePerksWhenDowned();
        }
        health += amount;
        if (inv.getJugg() == 0 && (health > Juggernaut.BASE_HEALTHBUFF)) {
            health = Juggernaut.BASE_HEALTHBUFF;
        } else if (inv.getJugg() == 1 && (health > Juggernaut.LVL1_HEALTHBUFF)) {
            health = Juggernaut.LVL1_HEALTHBUFF;
        } else if (inv.getJugg() == 2 && (health > Juggernaut.LVL2_HEALTHBUFF)) {
            health = Juggernaut.LVL2_HEALTHBUFF;
        } else if (inv.getJugg() == 3 && (health > Juggernaut.LVL3_HEALTHBUFF)) {
            health = Juggernaut.LVL3_HEALTHBUFF;
        } else if (inv.getJugg() == -1 && (health > 100)) {
            health = 100;
        }
        if (isOnline) {
            peer.sendNewHealth(username, health);
        }
    }

    public void incrementTempHealth(int increment) {
        if (inv.getVamp() >= 2) {
            if (inv.getJugg() == 0 && tempHealth + health < Juggernaut.BASE_HEALTHBUFF) {
                tempHealth += increment;
            } else if (inv.getJugg() == 1
                    && tempHealth + health < Juggernaut.LVL1_HEALTHBUFF + Vampire.LVL2_HEALTHSURPLUS) {
                tempHealth += increment;
            } else if (inv.getJugg() == 2
                    && tempHealth + health < Juggernaut.LVL2_HEALTHBUFF + Vampire.LVL2_HEALTHSURPLUS) {
                tempHealth += increment;
            } else if (inv.getJugg() == 3
                    && tempHealth + health < Juggernaut.LVL3_HEALTHBUFF + Vampire.LVL2_HEALTHSURPLUS) {
                tempHealth += increment;
            } else if (inv.getJugg() == -1 && tempHealth + health < 100 + Vampire.LVL2_HEALTHSURPLUS) {
                tempHealth += increment;
            }
        } else if (inv.getVamp() >= 0) {
            if (inv.getJugg() == 0 && tempHealth + health < Juggernaut.BASE_HEALTHBUFF) {
                tempHealth += increment;
            } else if (inv.getJugg() == 1 && tempHealth + health < Juggernaut.LVL1_HEALTHBUFF) {
                tempHealth += increment;
            } else if (inv.getJugg() == 2 && tempHealth + health < Juggernaut.LVL2_HEALTHBUFF) {
                tempHealth += increment;
            } else if (inv.getJugg() == 3 && tempHealth + health < Juggernaut.LVL3_HEALTHBUFF) {
                tempHealth += increment;
            } else if (inv.getJugg() == -1 && tempHealth + health < 100) {
                tempHealth += increment;
            }
        }
//		if (health + tempHealth < 100)
//			tempHealth += increment;

    }

    public void takeExplosionDamage(int damage) {
        if (inv.getPhd() >= 2) {
            damage = Math.round(damage * PhD.LVL2_EXPLOSIVERESIST);
        } else if (inv.getPhd() >= 0) {
            damage = Math.round(damage * PhD.BASE_EXPLOSIVERESIST);
        }
        takeDamage(damage);
    }

    public void setDefaultSpeed(float defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    public void justTookDamage() {
        justTookDamage = true;
    }

    public Inventory getInv() {
        return inv;
    }

    public BurnStatusForPlayer getBurnStatus() {
        return burnStatus;
    }

    public FreezeStatusForPlayer getFreezeStatus() {
        return freezeStatus;
    }

    public Stats getStats() {
        return stats;
    }

    public void addToMoveX(int dx) {
        xMove += dx;
    }

    public boolean getJustTookDamage() {
        return justTookDamage;
    }

    public void setTempHealth(int newTemp) {
        tempHealth = newTemp;
    }

    public int getTempHealth() {
        return tempHealth;
    }

    public String getUsername() {
        return username;
    }

    public KeyManager getKeyManager() {
        return playerInput.getKeyManager();
    }

    public void setKeyManager(KeyManager keyManager) {
        playerInput.setKeyManager(keyManager);
    }

    public GameMouseManager getMouseManager() {
        return playerInput.getMouseManager();
    }

    public void setMouseManager(GameMouseManager mouseManager) {
        playerInput.setMouseManager(mouseManager);
    }

    public GameCamera getGameCamera() {
        return gameCamera;
    }

    public void setGameCamera(GameCamera gameCamera) {
        this.gameCamera = gameCamera;
    }

    public HudManager getHud() {
        return hud;
    }

    public Peer getPeer() {
        return peer;
    }

    public PlayerMP getPlayerReviving() {
        return playerReviving;
    }

    public void setPlayerReviving(PlayerMP player) {
        this.playerReviving = player;
    }

    public User getUser() {
        return user;
    }

    public PlayerInput getPlayerInput() {
        return playerInput;
    }

    public PlayerMovementState getMoveState() {
        return moveState;
    }

    public void setMoveState(PlayerMovementState newState) {
        moveState = newState;
    }

    public PlayerActionState getActionState() {
        return actionState;
    }

    public void setActionState(PlayerActionState newState) {
        actionState = newState;
    }

    public RevivingElement getReviveHud() {
        return reviveHud;
    }

    public void removeReviveHud() {
        hud.removeObject(reviveHud);
    }

    public Ellipse2D.Float getInteractRadius() {
        return interactRadius;
    }
}

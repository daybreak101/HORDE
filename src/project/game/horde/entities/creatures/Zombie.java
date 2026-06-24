package project.game.horde.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import project.game.horde.entities.Entity;
import project.game.horde.entities.blood.Blood;
import project.game.horde.entities.creatures.zombieinfo.BurnStatus;
import project.game.horde.entities.creatures.zombieinfo.FreezeStatus;
import project.game.horde.entities.creatures.zombieinfo.SlownessStatus;
import project.game.horde.entities.facade.PlayerMP;
import project.game.horde.entities.statics.Barrier;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Animation;
import project.game.horde.graphics.Assets;
import project.game.horde.hud.CritElement;
import project.game.horde.hud.DamageElement;
import project.game.horde.hud.ZombieHealthElement;
import project.game.horde.main.BlessingInventory;
import project.game.horde.main.Handler;
import project.game.horde.perks.DeadShot;
import project.game.horde.perks.Stronghold;
import project.game.horde.perks.Vampire;
import project.game.horde.sounds.Sounds;
import project.game.horde.sounds.ZombieSounds;
import project.game.horde.utils.Node;
import project.game.horde.utils.RandomUtil;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;

public class Zombie extends Creature {

    protected int id;
    protected Animation zombieAnim, zombieAttackAnim, crawlerAnim, crawlerAttackAnim, enhancedZombieAnim,
            enhancedZombieAttackAnim;
    protected boolean justAttacked = false;
    private int attackDamage;
    protected Random rand = new Random();
    private float angley = rand.nextInt(handler.getWorld().getHeight() * 100),
            anglex = rand.nextInt(handler.getWorld().getWidth() * 100);
    Node goTo = null;
    private int source, player;
    boolean moving = false;
    Entity closestPlayer;

    protected int zombieType;
    protected boolean isCrawler = false;

    protected int maxHealth;

    protected BurnStatus burnStatus;
    protected FreezeStatus freezeStatus;
    protected SlownessStatus slownessStatus;

    protected Timer attackCooldown = new Timer(100);
    protected Rectangle hitbox;
    protected ZombieHealthElement healthBar;

    public Zombie(Handler handler, int id, float x, float y, float speed, int health) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.id = id;
        zombieType = ZOMBIE;
        zombieAnim = new Animation(150, Assets.zombieAnim);
        zombieAttackAnim = new Animation(100, Assets.zombieAttackAnim, true);
        crawlerAnim = new Animation(100, Assets.crawlerAnim);
        this.speed = speed;
        attackDamage = 10;
        this.health = health;
        maxHealth = health;
        hitbox = new Rectangle(0, 0, width, height);
        bounds = new Rectangle(25, 25, 25, 25);
        healthBar = new ZombieHealthElement(handler, x, y, this);
        burnStatus = new BurnStatus(handler, this);
        freezeStatus = new FreezeStatus(handler, this);
        slownessStatus = new SlownessStatus(this);
        closestPlayer = handler.getCurrentPlayer();
        active = true;
    }

    public int getID() {
        return id;
    }

    @Override
    public void setSpeed(float dSpeed) {
        rand = new Random();
        dSpeed = RandomUtil.nextFloat(dSpeed - .3f, dSpeed + .4f);
        speed = 1.8f + dSpeed;
    }

    public void removeSpeed() {
        speed = 0;
    }

    boolean reachedGoal = false;
    Timer delaySource = new Timer(40);

    @Override
    public void tick() {
        freezeStatus.checkIfInIcyWater();
        if (freezeStatus.isFrozen()) {
        } else if (!handler.noVisibleOrAlivePlayers()) {
            meander();
            move();
        } else {
            slownessStatus.setSlowness(1);
            if (freezeStatus.inWater()) {
                slownessStatus.addToSlowness(.5f);
            }
            closestPlayer = handler.getClosestPlayerToZombie(getCenterX(), getCenterY());
            if (closestPlayer == null) {
                closestPlayer = handler.getCurrentPlayer();
            }
            delaySource.tick();
            if (reachedGoal || delaySource.isReady()) {
                source = handler.getWorld().getPathingLogic().getClosestNode(getCenterX(), getCenterY());
            }

            if (justAttacked) {
                attackCooldown.tick();
                if (attackCooldown.isReady()) {
                    zombieAttackAnim.resetAnim();
                    justAttacked = false;
                    attackCooldown.resetTimer();
                }
            } else if (checkForObstacles()) {
//				System.out.println("pre-obstacles");
                followPath();
                //System.out.println("post-obstacle");
            } else if (goTo != null) {
                // if (source != goTo.getVertex()) {

                float dist = Utils.getEuclideanDistance(getCenterX(), getCenterY(), goTo.getX(), goTo.getY());
                if (dist < 50) {
                    // if(getCenterX() != goTo.getX() && getCenterY() != goTo.getY()) {
                    reachedGoal = true;
                    followPlayer();
                    //System.out.println("reached goal, follow player");
                } else {
                    //System.out.println("following path goal: " + goTo.getVertex() + "; distance, " + dist);
                    reachedGoal = false;
                    followPath();
                }
            } else {
                followPlayer();
                //System.out.println("last resort");
            }

            attack();
            postTick();

        }
        burnStatus.burn();

        healthBar.tick();
    }

    // Timer refactorGoTo = new Timer(5);
    public void followPath() {
//		source = handler.getWorld().getClosestNode(getCenterX(), getCenterY());
        player = closestPlayer.getClosestNode();
        //System.out.println("pre-obstacles");

//		refactorGoTo.tick();
//		if(refactorGoTo.isReady())
        if (source == -1) {
            //System.out.println("source is -1");

            followPlayer();
            return;
        }
        goTo = handler.getWorld().getPathingLogic().getNextStep(source, player);
        if (goTo != null) {
            if (handler.getWorld().getPathingLogic().getNodes().get(source).checkWithinNode(this)) {
                source = goTo.getVertex();
                goTo = handler.getWorld().getPathingLogic().getNextStep(source, player);
            }
        }
        if (goTo == null) {
            meander();
            move();
        } else if (source == goTo.getVertex() && goTo.checkWithinNode(this)) {
            meander();
            move();
        } else {
            float moveToX = goTo.getX() - getCenterX();
            float moveToY = goTo.getY() - getCenterY();
            float angle = (float) Math.toDegrees(Math.atan2(moveToY, moveToX));

            xMove = (float) (speed * (float) Math.cos(Math.toRadians(angle)) / slownessStatus.getSlowness());
            yMove = (float) (speed * (float) Math.sin(Math.toRadians(angle)) / slownessStatus.getSlowness());

            if (!checkEntityCollisions(xMove, 0f)) {
                moveX();
            }
            if (!checkEntityCollisions(0f, yMove)) {
                moveY();
            }
            checkForRandomGrowl();
        }
        //System.out.println(goTo.getVertex());
    }

    public void followPlayer() {
        source = -1;
        player = -1;
        goTo = null;

        xMove = 0;
        yMove = 0;

        float moveToX = closestPlayer.getCenterX() - getCenterX();
        float moveToY = closestPlayer.getCenterY() - getCenterY();

        float angle = (float) Math.toDegrees(Math.atan2(moveToY, moveToX));
        xMove = (float) (speed * (float) Math.cos(Math.toRadians(angle)) / slownessStatus.getSlowness());
        yMove = (float) (speed * (float) Math.sin(Math.toRadians(angle)) / slownessStatus.getSlowness());

        if (!checkEntityCollisions(xMove, 0f)) {
            moveX();
        }
        if (!checkEntityCollisions(0f, yMove)) {
            moveY();
        }
        checkForRandomGrowl();
    }

    Timer attacking = new Timer(20);
    boolean isAttacking = false;

    public void attack() {
        Player attackPlayer = null;
//		for (Player p : handler.getWorld().getEntityManager().getPlayers()) {
//			if (this.getHitBox(0, 0).intersects(p.getHitbox()) && !justAttacked) {
//				isAttacking = true;
//			}
//			if (this.getHitBox(0, 0).intersects(p.getHitbox()) && !p.getJustTookDamage()) {
//				attackPlayer = p;
//			}
//		}
        Player current = handler.getCurrentPlayer();
        if (!justAttacked && current.getHealth() > 0 && getHitBox(0, 0).intersects(current.getHitbox())
                && !(current.getInv().getBlessings().getBlessing().equals(BlessingInventory.INVISIBILITY)
                && current.getInv().getBlessings().isRunning())) {

            isAttacking = true;
        }
        if (this.getHitBox(0, 0).intersects(current.getHitbox())) {// && !current.getJustTookDamage()) {
            attackPlayer = current;
        }

        if (isAttacking) {
            attacking.tick();
            if (attacking.isReady()) {
                if (attackPlayer != null) {
                    attackPlayer.takeDamage(attackDamage);
                    attackPlayer.justTookDamage();
                }
                dontMove();
                attacking.resetTimer();
                isAttacking = false;
            }
        } else {
            for (Barrier e : handler.getWorld().getEntityManager().getBarriers()) {
                if (e.getCollisionBounds(0, 0).intersects(this.getHitBox(0, 0))) {
                    if (justAttacked() == false) {
                        e.takeDamage(attackDamage);
                        dontMove();
                    }
                }
            }
        }
    }

    public Rectangle getHitBox(float xOffset, float yOffset) {
        return new Rectangle((int) (x + hitbox.x + xOffset), (int) (y + hitbox.y + yOffset), hitbox.width,
                hitbox.height);
    }

    public void turnToCrawler() {
        isCrawler = true;
        this.speed = 1.0f;
        if (handler.getCurrentPlayer().getPeer() != null) {
            handler.getCurrentPlayer().getPeer().turnZombieToCrawler(handler.getCurrentPlayer().getUsername(), id);
        }
    }

    public boolean isCritical(Player player) {
        if ("So No Head?".equals(player.getInv().getBlessings().getBlessing()) && player.getInv().getBlessings().isRunning()) {
            return true;
        }

        int criticalChance = rand.nextInt(100);

        if (player.getInv().getDeadshot() >= 2) {
            if (criticalChance < DeadShot.LVL2_CRITCHANCEBUFF) {
                return true;
            }
        } else {
            if (criticalChance < 10) {
                return true;
            }
        }

        return false;
    }

    public void takeOnlineDamage(int amount) {
        if (handler.getRoundLogic().getPowerups().isInstakillActive()) {
            health = 0;
        } else {
            health -= amount;
        }
        if (freezeStatus.isFrozen()) {
            freezeStatus.freezeNearbyZombies(null);
            active = false;
            handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, zombieType));
        } else if (health <= 0 && freezeStatus.inWater()) {
            freezeStatus.freeze(null);
        } else if (health <= 0 && active == true) {
            active = false;
            handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, zombieType));
            powerupChecker();
        }
    }

    @Override
    public void takeDamage(int amount, Player player) {
        System.out.println("take damage: " + amount);
        float volume = ZombieSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
        Sounds.playClip(ZombieSounds.ZOMBIE_IMPACT, 1.0f, volume, false);
        boolean crit = false;
        if (handler.getRoundLogic().getPowerups().isInstakillActive()) {
            if (handler.getSettings().isToggleDamage()) {
                player.getHud().addObject(new DamageElement(handler, x + width / 2 + 10, y + height / 2 + 10, health));
            }
            if (handler.getCurrentPlayer().getPeer() != null) {
                handler.getCurrentPlayer().getPeer().playerDamagedZombie(handler.getCurrentPlayer().getUsername(), id,
                        health);
            }
            health = 0;

        } else {
            crit = isCritical(player);
            if (crit) {
                Sounds.playClip(ZombieSounds.ZOMBIE_HEADSHOT, 1.0f, volume, false);
            }

            if (crit && player.getInv().getDeadshot() == 3) {
                amount = Math.round(amount * DeadShot.LVL3_HEADSHOTDAMAGEBUFF); 
            }else if (crit) {
                amount = (amount * 2);
            }
            if (handler.getSettings().isToggleCrits() && crit) {
                player.getHud().addObject(new CritElement(handler, x + width / 2, y + height / 2));
            }
            if (handler.getSettings().isToggleDamage()) {
                player.getHud().addObject(new DamageElement(handler, x + width / 2 + 10, y + height / 2 + 10, amount));
            }
            health -= amount;
            if (handler.getCurrentPlayer().getPeer() != null) {
                handler.getCurrentPlayer().getPeer().playerDamagedZombie(handler.getCurrentPlayer().getUsername(), id,
                        amount);
            }
        }

        if (freezeStatus.isFrozen()) {
            freezeStatus.freezeNearbyZombies(player);
            active = false;
            die(player);
        } else if (health <= 0 && freezeStatus.inWater()) {
            freezeStatus.freeze(player);
        } else if (health <= 0 && active == true) {
            if (crit) {
                if (player.getInv().getDeadshot() >= 1) {
                    player.getInv().gainPoints(DeadShot.LVL1_HEADSHOTPOINTBUFF); 
                }else {
                    player.getInv().gainPoints(60);
                }
                player.getStats().addHeadshot();
            } else {
                player.getInv().gainPoints(50);
            }

            active = false;
            die(player);
        } else {
            player.getInv().gainPoints(10);
        }
    }

    public void postTick() {
        if (!justAttacked && !freezeStatus.isFrozen()) {
            zombieAnim.tick(); 
        }else if (justAttacked && !freezeStatus.isFrozen()) {
            zombieAttackAnim.tick();
        }

    }

    @Override
    public void dontMove() {
        if (!justAttacked && handler.getCurrentPlayer().getPeer() != null) {
            handler.getCurrentPlayer().getPeer().zombieJustAttacked(id);
        }
        justAttacked = true;
    }

    @Override
    public void move() {
        checkForRandomGrowl();
        if (!checkEntityCollisions(xMove, 0f)) {
            moveX();
        }
        if (!checkEntityCollisions(0f, yMove)) {
            moveY();
        }

    }

    Timer randomGrowl = new Timer(1);

    public void checkForRandomGrowl() {
        randomGrowl.tick();
        if (randomGrowl.isReady()) {
            randomGrowl = new Timer(RandomUtil.nextInt(200, 900));
            ZombieSounds
                    .playRandomGrowl(ZombieSounds.calculateVolumeBasedOffDistance(handler.getCurrentPlayer(), this));
        }
    }

    public void meander() {
        xMove = 0;
        yMove = 0;

        float moveToX = anglex - x;
        float moveToY = angley - y;

        float angle = (float) Math.atan2(moveToY, moveToX);
        xMove = (float) (speed * Math.cos(angle) / slownessStatus.getSlowness());
        yMove = (float) (speed * Math.sin(angle) / slownessStatus.getSlowness());

        if (5 >= moveToX && 0 <= moveToX) {
            anglex = rand.nextInt(handler.getWorld().getWidth() * 100);
        }
        if (5 >= moveToY && 0 <= moveToY) {
            angley = rand.nextInt(handler.getWorld().getHeight() * 100);
        }

    }

    @Override
    public void moveX() {
        x += xMove;

    }

    @Override
    public void moveY() {
        y += yMove;
    }

    Line2D[] z2p = new Line2D[5];

    public boolean checkForObstacles() {
        // if not working, check for all players
        // Player player = handler.getPlayer();
        z2p[0] = new Line2D.Float(x + width / 2, y + height / 2, closestPlayer.getCenterX(),
                closestPlayer.getCenterY());
        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if (z2p[0].intersects(e.getCollisionBounds(0, 0))) {
                if (
                    !(e instanceof Barrier)
                    //!handler.getWorld().getEntityManager().getBarriers().contains(e)
                ) {
                    return true;
                }
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (z2p[0].intersects(e.getCollisionBounds(0, 0))) {
                return true;
            }
        }

        int offset = 30;
        z2p[1] = new Line2D.Float(x + offset, y + offset, closestPlayer.getCenterX(), closestPlayer.getCenterY());
        z2p[2] = new Line2D.Float(x + width - offset, y + offset, closestPlayer.getCenterX(),
                closestPlayer.getCenterY());
        z2p[3] = new Line2D.Float(x + offset, y + height - offset, closestPlayer.getCenterX(),
                closestPlayer.getCenterY());
        z2p[4] = new Line2D.Float(x + width - offset, y + height - offset, closestPlayer.getCenterX(),
                closestPlayer.getCenterY());

        for (int i = 1; i < 5; i++) {
            for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
                if (z2p[i].intersects(e.getCollisionBounds(0, 0))) {
                    if (
                        !(e instanceof Barrier)
                        //!handler.getWorld().getEntityManager().getBarriers().contains(e)
                    ) {
                        return true;
                    }
                }
            }
            for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
                if (z2p[i].intersects(e.getCollisionBounds(0, 0))) {
                    return true;
                }
            }
        }
        return false;

    }

    float rotationAngle;

    @Override
    public void render(Graphics g) {
        float moveToX, moveToY;
        if (xMove == 0 && yMove == 0) {
        } else if (!freezeStatus.isFrozen()) {
            moveToX = x - (x + xMove);
            moveToY = y - (y + yMove);
            rotationAngle = (float) Math.toDegrees(Math.atan2(-moveToX, moveToY));
        }
        if (!isCrawler) {
            g.drawImage(Assets.shadow, (int) (getRenderX()), (int) (getRenderY()), width, height, null);
        }

        if (burnStatus.isBurning()) {
            g.setColor(Color.orange);
            g.fillOval((int) (getRenderX()), (int) (getRenderY()), width, height);
        }

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(rotationAngle), getRenderX() + width / 2, getRenderY() + height / 2);

        BufferedImage currentImage = Assets.crawler;
        if (freezeStatus.isFrozen()) {
            currentImage = Assets.frozenZombie;
        } else if (isCrawler) {
            g2d.drawImage(Assets.crawler, (int) (x - (height * 1.25 / 2) - handler.getGameCamera().getxOffset()),
                    (int) (y - (width * 1.25 / 2) - handler.getGameCamera().getyOffset()), (int) (width * 1.25),
                    (int) (height * 1.25), null);
        } else if (justAttacked) {
            currentImage = zombieAttackAnim.getCurrentFrame();
        } else {
            currentImage = zombieAnim.getCurrentFrame();
        }

        if (!isCrawler) {
            g2d.drawImage(currentImage, (int) (getRenderX()), (int) (getRenderY()), width, height, null);
        }
        g2d.setTransform(old);

        if (handler.getSettings().isHealthBar()) {
            healthBar.render(g);
        }

    }



    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float angle) {
        rotationAngle = angle;
    }

    @Override
    public void die(Player player) {
        if (player != null) {
            if (player.getInv().getVamp() >= 0) {
                player.incrementTempHealth(Vampire.TEMPHEALTH_GAIN);
            }
            if (player.getInv().getStronghold() == 3) {
                if (player.getStrongholdRadius() != null) {
                    if (player.getStrongholdRadius().intersects(getHitBox(0, 0))) {
                        player.gainStrongholdArmor(Stronghold.ARMOR_GAIN_INCREMENTS);
                        player.gainStrongholdDamageMultiplier(Stronghold.DAMAGE_BUFF_INCREMENTS);
                    }
                }
            }
            player.getStats().gainKill();
        }

        handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, zombieType));
        ZombieSounds.playRandomDeath(ZombieSounds.calculateVolumeBasedOffDistance(handler.getCurrentPlayer(), this));
        powerupChecker();
    }

    public void powerupChecker() {
        int vertex = handler.getWorld().getPathingLogic().getClosestNode((int) (getCenterX()), (int) (getCenterY()));
        // Node node = ;
        if (handler.getWorld().getPathingLogic().getNodes().get(vertex).withinPlayable()) {
            if (handler.getRoundLogic().getPowerups().isPowerUpReady()) {
                handler.getWorld().getEntityManager()
                        .addPowerUp(handler.getRoundLogic().getPowerups().generatePowerUp(x, y));
            }
//			if (handler.getRoundLogic().getZombiesLeft() <= 1 && handler.getRoundLogic().isDogRound()) {
//				handler.getWorld().getEntityManager().addPowerUp(new MaxAmmo(handler, x, y));
//			}

        }
    }

    public void dieByGoodBoy() {
        boolean smallSpawned = false;
        handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, zombieType));
        if (handler.getCurrentPlayer().getPeer() != null) {
            handler.getCurrentPlayer().getPeer().playerDamagedZombie(handler.getCurrentPlayer().getUsername(), id,
                    health);
        }
        if (handler.getCurrentPlayer().getInv().getLuna() >= 1) {
            smallSpawned = handler.getRoundLogic().getPowerups().spawnMiniByLuna(Math.round(x), Math.round(y));
        }

        if (handler.getCurrentPlayer().getInv().getLuna() == 3 && !smallSpawned) {
            handler.getRoundLogic().getPowerups().spawnByLuna(Math.round(x), Math.round(y));
        }
        active = false;
    }

    public void dieByNuke() {
        handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, zombieType));
        powerupChecker();
    }

    public void dieByTrap() {
        handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, zombieType));
        active = false;
    }

    public void damageByTrap(int damage) {
        health -= damage;
        if (health <= 0 && active == true) {
            handler.getWorld().getEntityManager().addBlood(new Blood(handler, x, y, zombieType));
            active = false;
        }
    }

    @Override
    public boolean checkEntityCollisions(float xOffset, float yOffset) {
        if (handler.getCurrentPlayer().getCollisionBounds(0, 0).intersects(getCollisionBounds(xOffset, yOffset))) {
            return true;
        }
        for (PlayerMP e : handler.getWorld().getEntityManager().getOtherPlayers()) {
            if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
//		for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
//			if (e.equals(this))
//				continue;
//			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
//				return true;
//		}
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
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (e.getCollisionBounds(0, 0).intersects(getCollisionBounds(xOffset, yOffset))) {
                return true;
            }
        }
        return false;
    }

    public boolean justAttacked() {
        return justAttacked;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getZombieType() {
        return zombieType;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public BurnStatus getBurnStatus() {
        return burnStatus;
    }

    public FreezeStatus getFreezeStatus() {
        return freezeStatus;
    }

}

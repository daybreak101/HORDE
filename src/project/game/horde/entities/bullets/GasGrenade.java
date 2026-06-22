package project.game.horde.entities.bullets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.statics.InteractableStaticEntity;
import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;
import project.game.horde.perks.PhD;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.MiscWeaponSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.weapons.GunVars;

public class GasGrenade extends Bullet {

    private int counter, timer, explosionTimer;
    private Timer damageTicker = new Timer(30);
    private Timer gasTimer = new Timer(600);
    private Ellipse2D gasRadius;
    private boolean exploded;
    private int damage = GunVars.GAS_GRENADE_DAMAGE;
    private float destX, destY;
    boolean slowed = false;

    public GasGrenade(Handler handler, float x, float y, float destX, float destY, Player player) {
        super(handler, x, y, 10000, player);
        this.player = player;
        x = (int) x;
        y = (int) y;
        width = 10;
        height = 10;
        counter = 0;
        timer = 60;
        explosionTimer = 65;
        speed = 8;
        xMove = (float) (Math.cos(angle));
        yMove = (float) (Math.sin(angle));
        this.destX = destX;
        this.destY = destY;
    }

    @Override
    public void tick() {
        postTick();
        counter++;
        if (counter >= timer) {
            if (counter >= explosionTimer) {
                gasRadius = new Ellipse2D.Float(x - 100, y - 100, 200, 200);
                exploded = true;
            }
            if (exploded) {
                gasTimer.tick();
                damageTicker.tick();
                if (damageTicker.isReady()) {
                    findEntitiesInRadius();
                    damageTicker.resetTimer();
                }
                if (gasTimer.isReady()) {
                    Sounds.stopClip(dedicatedSound);
                    //Sounds.stopClip(InteractSounds.VENDING_STATIC);
                    handler.getWorld().getEntityManager().getEntities().remove(this);
                }
                if (gasTimer.counter >= 580) {
                    i++;
                }
            }

        } else {
            while (travelTicker < speed) {
                moveX();
                moveY();
                travelTicker++;

                if (((int) x == (int) destX || (int) y == (int) destY) && !slowed) {
                    slowed = true;
                    speed = speed / 8;
                }

            }
            travelTicker = 0;
        }

    }

    @Override
    public void postTick() {

        if (exploded) {
            updateSound.tick();
            if (updateSound.isReady()) {
                staticSounds();
            }
        }
    }

    int updater = 10;
    Timer updateSound = new Timer(updater);
    float lastStaticVolume = 0;
    long lastStaticPosition = 0;
    String currentStaticSound = "";
    String dedicatedSound = MiscWeaponSounds.getGasGrenadeCurrent();

    public void staticSounds() {
        float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());

        // turn off current playing sound if it is too far
        if (newvolume <= 0) {
            Sounds.stopClip(currentStaticSound);
            currentStaticSound = "";
        } else {
            String newSound = dedicatedSound;

            if (!newSound.equals(currentStaticSound)) {
                Sounds.stopClip(currentStaticSound);
                currentStaticSound = newSound;
                Sounds.playClip(currentStaticSound, 1.0f, newvolume, true);
            }

            if (lastStaticVolume != newvolume) {
                lastStaticPosition = Sounds.getMillisecondPosition(currentStaticSound, updater);
                Sounds.stopClip(currentStaticSound);
                Sounds.playClipFrom(currentStaticSound, 1.0f, newvolume, lastStaticPosition, true);
                lastStaticVolume = newvolume;
            }
        }
    }

    @Override
    public boolean checkForImpact() {
        cb = new Rectangle((int) (x + bounds.x - 1), (int) (y + bounds.y - 1), bounds.width + 2, bounds.height + 2);

        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if ( e.getCollisionBounds(0, 0).intersects(cb)) {
                return true;
            }
        }

        for (InteractableStaticEntity e : handler.getWorld().getEntityManager().getInteractables()) {
            if ( !handler.getWorld().getEntityManager().getBarriers().contains(e)
                    && e.getCollisionBounds(0, 0).intersects(cb)) {
                return true;
            }
        }
        for (Wall e : handler.getWorld().getEntityManager().getWalls()) {
            if (e.getCollisionBounds(0, 0).intersects(cb)) {
                return true;
            }
        }
        return false;

    }

    public void findEntitiesInRadius() {
        float damageMultiplier = 1;
        if (player.getInv().getPhd() == 3) {
            damageMultiplier += PhD.LVL3_EXPLOSIVEDAMAGEBUFF;
        }
        if (player.getInv().getStronghold() > -1) {
            damageMultiplier += player.getStrongholdDamageMultiplier();
        }

        for (Zombie f : handler.getWorld().getEntityManager().getZombies()) {
            if (gasRadius.intersects(f.getHitBox(0, 0))) {
                f.takeDamage((int) (damage * damageMultiplier), player);
                float currentPercent = (float) ((float) f.getHealth() / (float) f.getMaxHealth());
                float thirtyPercent = (float) ((float) (f.getMaxHealth() * 3 / 10) / (float) f.getMaxHealth());
                if (currentPercent < thirtyPercent && f.getZombieType() == 0) {
                    f.turnToCrawler();
                }
            }
        }

        if (gasRadius.intersects(player.getCollisionBounds(0, 0))) {
            player.takeExplosionDamage(5);
        }
    }

    @Override
    public void moveX() {
        x += xMove;
        if (checkForImpact()) {
            speed = speed / 4;
            x -= xMove;
            xMove = -xMove;
        }

    }

    @Override
    public void moveY() {
        y += yMove;
        if (checkForImpact()) {
            speed = speed / 4;
            y -= yMove;
            yMove = -yMove;
        }
    }

    int i = 0;

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(150, 200, 100));
        g.fillOval((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);

        if (gasRadius != null) {
            if (gasTimer.counter >= 580) {
                //i++;
                g.drawImage(Assets.gas_cloud[i], (int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
                        (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()), (int) gasRadius.getWidth(),
                        (int) gasRadius.getHeight(), null);
            } else {
                g.drawImage(Assets.gas_cloud[0], (int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
                        (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()), (int) gasRadius.getWidth(),
                        (int) gasRadius.getHeight(), null);
            }

            // g.setColor(new Color(150, 200, 100, 150));
            // g.fillOval((int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
            // (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()),
            // (int) gasRadius.getWidth(), (int) gasRadius.getHeight());
        }
    }

    @Override
    public void renderBW(Graphics g) {
        g.setColor(new Color(150, 200, 100));
        g.fillOval((int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()),
                width, height);

        if (gasRadius != null) {
            if (gasTimer.counter >= 580) {
                //i++;
                g.drawImage(BWAssets.gas_cloud[i], (int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
                        (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()), (int) gasRadius.getWidth(),
                        (int) gasRadius.getHeight(), null);
            } else {
                g.drawImage(BWAssets.gas_cloud[0], (int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
                        (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()), (int) gasRadius.getWidth(),
                        (int) gasRadius.getHeight(), null);
            }

            // g.setColor(new Color(150, 200, 100, 150));
            // g.fillOval((int) (gasRadius.getX() - handler.getGameCamera().getxOffset()),
            // (int) (gasRadius.getY() - handler.getGameCamera().getyOffset()),
            // (int) gasRadius.getWidth(), (int) gasRadius.getHeight());
        }
    }

}

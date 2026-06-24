package project.game.horde.entities.creatures;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import project.game.horde.entities.statics.Wall;
import project.game.horde.graphics.Animation;
import project.game.horde.graphics.Assets;
import project.game.horde.main.Handler;
import project.game.horde.sounds.CreatureSounds;
import project.game.horde.sounds.InteractSounds;
import project.game.horde.sounds.Sounds;
import project.game.horde.utils.Timer;
import project.game.horde.utils.Utils;

public class Wolf extends Creature {

    protected boolean justAttacked = false;
    protected long timer = 0;
    protected Rectangle hitbox;
    protected int attackTicker = 0, attackTimer = 100;
    protected Zombie closestZombie;
    protected Player player;
    protected float angle;
    private Timer sendMoveUpdate = new Timer(2);
    private Animation wolfAnimation;

    public Wolf(Handler handler, float x, float y, Player player) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        wolfAnimation = new Animation(100, Assets.aurora);
        this.player = player;
        speed = 4.0f;
        hitbox = new Rectangle(0, 0, width, height);
        bounds.x = 65 / 2;
        bounds.y = 65 / 2;
        bounds.width = 10;
        bounds.height = 10;
        angle = 0;
    }

    public void playEnterSound() {
        float volume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
        if (volume > 0) {
            Sounds.playClip(CreatureSounds.AURORA_ENTER, 1.0f, volume, false);
        }
    }

    public void playLeaveSound() {
        float volume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
        if (volume > 0) {
            Sounds.stopClip(CreatureSounds.AURORA_BARK);
            Sounds.stopClip(CreatureSounds.AURORA_GROWL);
            Sounds.playClip(CreatureSounds.AURORA_LEAVE, 1.0f, volume, false);
        }
    }

    public void playGrowlSound() {
        float volume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());
        if (volume > 0) {
            Sounds.stopClip(CreatureSounds.AURORA_BARK);
            Sounds.playClip(CreatureSounds.AURORA_GROWL, 1.0f, volume, false);
        }
    }

    int updater = 10;
    Timer updateSound = new Timer(updater);
    float lastStaticVolume = 0;
    long lastStaticPosition = 0;
    String currentStaticSound = "";
    boolean onCooldown = false;
    Timer barkCooldown = new Timer(300);

    public void playBarkSounds() {
        float newvolume = InteractSounds.calculateVolumeBasedOffDistance(this, handler.getCurrentPlayer());

        // turn off current playing sound if it is too far
        if (newvolume <= 0) {
            Sounds.stopClip(currentStaticSound);
            currentStaticSound = "";
        } else {
            String newSound = CreatureSounds.AURORA_BARK;

            if (!newSound.equals(currentStaticSound)) {
                Sounds.stopClip(currentStaticSound);
                currentStaticSound = newSound;
                Sounds.playClip(currentStaticSound, 1.0f, newvolume, false);
            }

            if (lastStaticVolume != newvolume) {
                lastStaticPosition = Sounds.getMillisecondPosition(currentStaticSound, updater);
                Sounds.stopClip(currentStaticSound);
                Sounds.playClipFrom(currentStaticSound, 1.0f, newvolume, lastStaticPosition, false);
                lastStaticVolume = newvolume;
            }
            if (lastStaticPosition >= Sounds.getMicrosecondLength(CreatureSounds.AURORA_BARK) - (updater * 60)) {
                currentStaticSound = "";
                onCooldown = true;
                lastStaticPosition = 0;
            }
        }
    }

    public void followClosestZombie() {
        xMove = 0;
        yMove = 0;

        if (justAttacked == true) {
            timer++;
            if (timer == 100) {
                justAttacked = false;
                timer = 0;
            }
        } else {
            // find closest zombie
            Ellipse2D.Float attackRadius = new Ellipse2D.Float(x - 250, y - 250, 500, 500);
            closestZombie = null;
            float closestDist = 500;
            float eDist;
            int vertex;
            Line2D.Float line;
            boolean wallFound = false;

            for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
                eDist = Utils.getEuclideanDistance(x, y, e.getX(), e.getY());
                vertex = handler.getWorld().getPathingLogic().getClosestNode((int) (e.getCenterX()), (int) (e.getCenterY()));
                line = new Line2D.Float(e.getCenterX(), e.getCenterY(), x, y);

                for (Wall w : handler.getWorld().getEntityManager().getWalls()) {
                    if (line.intersects(w.getCollisionBounds(0, 0))) {
                        wallFound = true;
                        break;
                    }
                }
                if (handler.getWorld().getPathingLogic().getNodes().get(vertex).withinPlayable()
                        && attackRadius.intersects(e.getCollisionBounds(0, 0)) && !wallFound) {
                    if (closestZombie == null) {
                        closestZombie = e;
                        closestDist = eDist;
                    }
                    if (eDist < closestDist) {
                        closestZombie = e;
                        closestDist = eDist;
                    }
                }

            }

            // move towards closest zombie
            if (closestZombie != null) {
                float moveToX = closestZombie.getX() - x;
                float moveToY = closestZombie.getY() - y;
                angle = (float) Math.atan2(moveToY, moveToX);
                xMove = (float) (speed * Math.cos(angle));
                yMove = (float) (speed * Math.sin(angle));

                if (!checkEntityCollisions(xMove, 0f)) {
                    moveX();
                }

                if (!checkEntityCollisions(0f, yMove)) {

                    moveY();
                }

                if (this.getCollisionBounds(0, 0).intersects(closestZombie.getHitBox(0, 0))) {
                    playGrowlSound();
                    if (justAttacked() == false) {
                        closestZombie.dieByGoodBoy();
                        justAttacked = true;
                    }
                }
            } else {
                followPlayer();
            }

        }

    }

    public void followPlayer() {
        Ellipse2D.Float radius = new Ellipse2D.Float(player.getCenterX() - 50, player.getCenterY() - 50, 100, 100);
        int offset = 30;
        Rectangle newBounds = new Rectangle((int) (x - offset), (int) (y - offset), width + offset * 2,
                height + offset * 2);
        if (!radius.intersects(newBounds)) {
            float moveToX = player.getCenterX() - x;
            float moveToY = player.getCenterY() - y;
            angle = (float) Math.atan2(moveToY, moveToX);
            xMove = (float) (speed * Math.cos(angle));
            yMove = (float) (speed * Math.sin(angle));

            if (!checkEntityCollisions(xMove, 0f)) {
                moveX();
            }

            if (!checkEntityCollisions(0f, yMove)) {
                moveY();
            }
        } else {
            xMove = 0;
            yMove = 0;
        }

        if (onCooldown) {
            barkCooldown.tick();
            if (barkCooldown.isReady()) {
                onCooldown = false;
            }
        }
        updateSound.tick();
        if (updateSound.isReady() && !onCooldown) {
            playBarkSounds();
        }
    }

    @Override
    public void tick() {
        followClosestZombie();
        wolfAnimation.tick();
    }

    @Override
    public void render(Graphics g) {

        float moveToX, moveToY;
        if (closestZombie != null) {
            moveToX = closestZombie.getCenterX() - handler.getGameCamera().getxOffset();
            moveToY = closestZombie.getCenterY() - handler.getGameCamera().getyOffset();
        } else {
            moveToX = player.getCenterX() - handler.getGameCamera().getxOffset();
            moveToY = player.getCenterY() - handler.getGameCamera().getyOffset();
        }

        float angle = (float) Math
                .toDegrees(Math.atan2(-(x - handler.getGameCamera().getxOffset() - moveToX + width / 2),
                        y - handler.getGameCamera().getyOffset() - moveToY + height / 2));

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(angle), x - handler.getGameCamera().getxOffset() + width / 2,
                y - handler.getGameCamera().getyOffset() + height / 2);

        sendMoveUpdate.tick();
        if (player.getPeer() != null && sendMoveUpdate.isReady()) {
            player.getPeer().sendNewLunaCoords(player.getUsername(), x, y, angle);
        }

        int offset = 30;
        g2d.drawImage(wolfAnimation.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset() - offset),
                (int) (y - handler.getGameCamera().getyOffset() - offset), width + offset * 2, height + offset * 2,
                null);

        g2d.setTransform(old);

    }

    public boolean justAttacked() {
        return justAttacked;
    }

}

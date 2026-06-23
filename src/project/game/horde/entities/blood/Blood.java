package project.game.horde.entities.blood;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import project.game.horde.entities.Entity;
import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.Zombie;
import project.game.horde.entities.creatures.ZombieType;
import project.game.horde.graphics.Assets;
import project.game.horde.graphics.BWAssets;
import project.game.horde.main.Handler;
import project.game.horde.utils.Timer;

public class Blood extends Entity {

    protected int timer, counter;
    protected float x, y;
    protected int width, height;
    protected Handler handler;
    protected int bloodType;
    Rectangle rect;
    BufferedImage bloodImage, BWBloodImage;
    protected int damageToPlayer;
    protected int damageToZombie;
    Timer damageTimer;

    public Blood(Handler handler, float x, float y, int bloodType) {
        super(handler, x, y, 75, 75);
        this.handler = handler;
        this.x = x;
        this.y = y;
        timer = 300;
        counter = 0;
        width = 75;
        height = 75;
        damageToPlayer = 0;
        damageToZombie = 0;
        damageTimer = new Timer(5000);
        rect = new Rectangle((int) x, (int) y, width, height);
        this.bloodType = bloodType;

        switch (bloodType) {
            case ZombieType.ZOMBIE -> {
                bloodImage = Assets.zombieBlood;
                BWBloodImage = BWAssets.zombieBlood;
            }
            case ZombieType.LICKER ->
                BWBloodImage = BWAssets.lickerBlood;
            case ZombieType.TOXEN -> {
                BWBloodImage = BWAssets.toxenBlood;
                timer = 900;
                damageTimer = new Timer(30);
                damageToPlayer = 5;
                damageToZombie = 500;
            }
            case ZombieType.STOKER -> {
                BWBloodImage = BWAssets.toxenBlood;
                timer = 600;
                damageTimer = new Timer(30);
                damageToPlayer = 5;
                damageToZombie = 500;
            }
            default ->
                timer = 30;
        }
    }

    public void damagePlayers() {
        Player player = handler.getCurrentPlayer();
        if (rect.intersects(player.getCollisionBounds(0, 0)) && damageTimer.isReady()) {
            if (bloodType == ZombieType.STOKER) {
                player.getBurnStatus().setBurn(damageToPlayer);
            }
            player.takeDamage(damageToPlayer / 2);
            damageTimer.resetTimer();
        }

    }

    public void damageZombies() {
        for (Zombie e : handler.getWorld().getEntityManager().getZombies()) {
            if (e.getCollisionBounds(0, 0).intersects(rect) && e.getZombieType() != bloodType
                    && damageTimer.isReady()) {
                if (bloodType == ZombieType.STOKER) {
                    e.getBurnStatus().setBurn(damageToZombie / 5);
                }
                e.takeDamage(damageToZombie);
                damageTimer.resetTimer();
            }
        }
    }

    @Override
    public void tick() {
        rect = new Rectangle((int) x, (int) y, width, height);
        counter++;
        damageTimer.tick();
        damagePlayers();
        damageZombies();
        if (counter > timer) {
            active = false;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bloodImage, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

    }

    @Override
    public void renderBW(Graphics g) {
        g.drawImage(BWBloodImage, (int) (x - handler.getGameCamera().getxOffset()),
                (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    public int getCounter() {
        return counter;
    }

    public int getTimer() {
        return timer;
    }

    public int getBloodType() {
        return bloodType;
    }

    public void moveX() {
        x += 2;
    }

    public Rectangle getRect() {
        return rect;
    }

}

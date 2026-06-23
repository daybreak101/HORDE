package project.game.horde.network;

import java.io.Serializable;

// Inner class to represent the position of a zombie
public class ZombiePosition implements Serializable {
    private static final long serialVersionUID = 1L;
    public int zombieID;
    public float positionX;
    public float positionY;
    public float angle;
    
    public ZombiePosition() {}

    public ZombiePosition(int zombieID, float positionX, float positionY, float angle) {
        this.zombieID = zombieID;
        this.positionX = positionX;
        this.positionY = positionY;
        this.angle = angle;
    }
}
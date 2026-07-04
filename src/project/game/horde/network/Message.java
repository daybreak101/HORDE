package project.game.horde.network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import project.game.horde.entities.Entity;
import project.game.horde.main.User;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int KEEP_ALIVE = 0;
    public static final int USER_JOIN = 1;
    public static final int USER_LEAVE = 2;
    public static final int USER_LIST = 3;
    public static final int HOST_START_GAME = 4;
    public static final int USER_X_MOVE = 5;
    public static final int USER_Y_MOVE = 6;
    public static final int USER_ROTATE = 7;
    public static final int USER_SHOT = 8;
    public static final int USER_GRENADE_TOSS = 9;
    public static final int USER_RELOAD = 10;
    public static final int USER_MELEE = 11;
    public static final int USER_TOOK_DAMAGE = 12;
    public static final int ZOMBIE_POSITIONS = 13;
    public static final int ZOMBIE_SPAWN = 14;
    public static final int ZOMBIE_ATTACKED = 15;
    public static final int USER_DAMAGED_ZOMBIE = 16;
    public static final int NEW_ROUND = 17;
    public static final int USER_NEW_HEALTH = 18;
    public static final int USER_REFILL_HEALTH = 19;
    public static final int SPAWN_POWERUP = 20;
    public static final int USER_SPAWNED_POWERUP = 21;
    public static final int USER_PICKED_POWERUP = 22;
    public static final int USER_SWITCHED_WEAPON = 23;
    public static final int ZOMBIE_TURN_CRAWLER = 24;
    public static final int USER_SHOT_GRENADE_LAUNCHER = 25;
    public static final int FLAMETHROWER_SOUND = 26;
    public static final int USER_REMOVE_LUNA = 27;
    public static final int USER_SPAWN_LUNA = 28;
    public static final int USER_LUNA_MOVED = 29;
    public static final int USER_REVIVED_USER = 30;
    public static final int USER_INTERACT = 31;
    public static final int USER_READY = 32;
    public static final int HOST_SEND_LOADING = 33;
    public static final int USER_ACTIVATED_BLESSING = 34;
    public static final int USER_Z_MOVE = 35;
    public static final int GAME_ALREADY_STARTED = 36;
    public static final int ASK_FOR_GAME_STARTED = 37;
	public static final int HOST_CHANGE_MAP = 38;
	public static final int USER_CHANGED_SKIN = 39;
	public static final int USER_CHANGED_HAT = 40;
    public static final int HOST_END_GAME = 41;

    public int type;
    public int connectionId;
    public String username;
    public HashMap<Integer, User> userList;
    public String message, powerup, grenade;
    public Entity entity;
    public List<ZombiePosition> zombiePositions; // New field for zombie positions
    public int amount, id, round, zombiesLeft, x, y;
    public float angle;
    public boolean isBusy, gameAlreadyStarted;

    public Message() {}
    
    
    public Message(int type, int connection, boolean gameAlreadyStarted) {
    	this.connectionId = connection;
    	this.gameAlreadyStarted = gameAlreadyStarted;
    }

    public Message(int type, int connectionId, String username, HashMap<Integer, User> userList) {
        this.type = type;
        this.connectionId = connectionId;
        this.username = username;
        this.userList = userList;
    }
    
    public Message(int type, String username, String message) {
    	this.type = type;
    	this.username = username;
    	this.message = message;
    }    
    
    public Message(int type, List<ZombiePosition> zombiePositions) {
        this.type = type;
        this.zombiePositions = zombiePositions;
    }
    
    public Message(int type, int round, int zombiesLeft) {
    	this.type = type;
    	this.round = round;
    	this.zombiesLeft = zombiesLeft;
    }
    
    public Message(int type, String username, int id, int amount) {
    	this.type = type;
    	this.username = username;
    	this.id = id;
    	this.amount = amount;
    }
    
    public Message(int type, String username) {
    	this.type = type;
    	this.username = username;
    }
    
    public Message(int type) {
    	this.type = type;
    }
    
    public Message(int type, String powerup, int id,int x, int y) {
    	this.type = type;
    	this.powerup = powerup;
    	this.id = id;
    	this.x = x;
    	this.y = y;
    }
    
    //powerup, hat, skin
    public Message(int type, String powerup, int id)
    {
    	this.type = type;
    	this.powerup = powerup;
    	this.id = id;
    }
    
    public Message(int type, String username, String grenade, int x, int y) {
    	this.type = type;
    	this.username = username;
    	this.grenade = grenade;
    	this.x = x;
    	this.y = y;
    }
    
    public Message(int type, String username, int x, int y, float angle) {
    	this.type = type;
    	this.username = username;
    	this.x = x;
    	this.y = y;
    	this.angle = angle;
    }
    
    public Message(int type, String username, String message, int amount) {
    	this.type = type;
    	this.username = username;
    	this.message = message;
    	this.amount = amount;
    }    
    
    public Message(int type, String username, int id, boolean isBusy) {
    	this.type = type;
    	this.username = username;
    	this.id = id;
    	this.isBusy = isBusy;
    }
    
}

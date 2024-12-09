package project.game.horde.main;


import java.io.Serializable;

import com.esotericsoftware.kryonet.Connection;

import project.game.horde.entities.creatures.Player;
import project.game.horde.entities.creatures.playerinfo.Stats;

public class User implements Serializable {
	private Stats stats;
	private Player player;
    private String username;
    private transient Connection connection;
    public boolean isReady = false;
    
    public User() {}
    
    public User(Connection connection) {
    	this.connection = connection;
    }
    
    public User(String username) {
        this.username = username;
    }
    
    public User(Connection connection, String username) {
    	this.connection = connection;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return getConnection() == user.getConnection();
    }

    @Override
    public int hashCode() {
        return connection.getID();
    }

    public void setReady(boolean isReady) {
    	this.isReady = isReady;
    }
    
    public boolean getReady() {
    	return isReady;
    }
}

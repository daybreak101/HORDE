package project.game.horde.main;


import java.io.Serializable;

import com.esotericsoftware.kryonet.Connection;

public class User implements Serializable {
    private String username;
    private transient Connection connection;
    public boolean isReady = false;
    public int skin = 0;
    public int hat = 0;
    
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
    
    public void setHat(int hat) {
    	this.hat = hat;
    }
    
    public int getHat() {
    	return hat;
    }
    
    public void setSkin(int skin) {
    	this.skin = skin;
    }
    
    public int getSkin() {
    	return skin;
    }
}

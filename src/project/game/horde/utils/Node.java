package project.game.horde.utils;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;
import java.util.ArrayList;

import project.game.horde.entities.creatures.Zombie;

public class Node {
	int vertex;
	int x, y, z, room;
	ArrayList<Node> nextNodes;
	Ellipse2D.Float radius;
	boolean withinPlayable;
	
	public Node(int vertex, int x, int y, int z, int room,  int withinPlayable) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.room = room;
		this.vertex = vertex;
		nextNodes = new ArrayList<Node>();
		radius = new Ellipse2D.Float(x - 5, y - 5, 10, 10);
		if(withinPlayable == 1) 
			this.withinPlayable = true;
		else
			this.withinPlayable = false;
		//radius = new Ellipse2D.Float(x - 50, y - 50, 100, 100);
	}
	
	
	public boolean checkWithinNode(Zombie z) {
		//Ellipse2D.Float point = new Ellipse2D.Float( (z.getX() + z.getWidth()/2), 
		//										(z.getY() + z.getHeight()/2), 1, 1);
		if(this.z == z.getZ() && radius.intersects(z.getCollisionBounds(0, 0))) {
			//z.setGoToNode(nextNode);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setNextNodes(Node nextNode) {
		
		this.nextNodes.add(nextNode);
	}
	
	public Node getNextNode(int node) {
		return nextNodes.get(node);
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getZ() {
		return z;
	}
	
	public boolean withinPlayable() {
		return withinPlayable;
	}
	
	public int getRoom() {
		return room;
	}
	
}

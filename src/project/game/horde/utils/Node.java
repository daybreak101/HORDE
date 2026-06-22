package project.game.horde.utils;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import project.game.horde.entities.creatures.Zombie;

public class Node {

    int vertex;
    int x, y, room;
    ArrayList<Node> nextNodes;
    Ellipse2D.Float radius;
    boolean withinPlayable;

    public Node(int vertex, int x, int y, int room, int withinPlayable) {
        this.x = x;
        this.y = y;
        this.room = room;
        this.vertex = vertex;
        nextNodes = new ArrayList<Node>();
        radius = new Ellipse2D.Float(x - 5, y - 5, 10, 10);
        this.withinPlayable = withinPlayable == 1;
        //radius = new Ellipse2D.Float(x - 50, y - 50, 100, 100);
    }

    public boolean checkWithinNode(Zombie z) {
        return radius.intersects(z.getCollisionBounds(0, 0));
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

    public boolean withinPlayable() {
        return withinPlayable;
    }

    public int getRoom() {
        return room;
    }

}

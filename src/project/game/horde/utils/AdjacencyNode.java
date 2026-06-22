package project.game.horde.utils;

public class AdjacencyNode {
	public int vertex;
	public float weight;
	public AdjacencyNode next;
	
	public AdjacencyNode(int vertex, float weight) {
	
		this.vertex = vertex;
		this.weight = weight;
		this.next = null;

	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public AdjacencyNode getNext() {
		return next;
	}

	public void setNext(AdjacencyNode next) {
		this.next = next;
	}

}

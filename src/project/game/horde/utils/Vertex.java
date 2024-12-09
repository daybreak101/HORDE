package project.game.horde.utils;

public class Vertex {
	public int vertex;
	public int pred;
	public float dist;
	
	public Vertex(int vertex) {
		this.vertex = vertex;
		pred = -1;
		dist = 2147483647;
	}

	public int getPred() {
		return pred;
	}

	public void setPred(int pred) {
		this.pred = pred;
	}

	public float getDist() {
		return dist;
	}

	public void setDist(float dist) {
		this.dist = dist;
	}

	public int getVertex() {
		return vertex;
	}

	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	
	
}

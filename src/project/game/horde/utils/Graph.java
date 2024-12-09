package project.game.horde.utils;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {
	private AdjacencyList[] list;
	private Vertex[] vertices;
	private int size;

	public Graph(int size) {
		this.size = size;
		// list = new ArrayList<AdjacencyList>();
		list = new AdjacencyList[size];
		vertices = new Vertex[size];

		for (int i = 0; i < size; i++) {
			list[i] = new AdjacencyList();
			vertices[i] = new Vertex(i);
		}
	}

	public void createEdge(int src, int dest, float weight) {
		AdjacencyNode node = new AdjacencyNode(dest, weight);
		if (list[src].head == null) {
			node.next = null;
			list[src].head = node;
		} else {
			node.next = list[src].head;
			list[src].head = node;
		}

	}

	public void dijkstra(int source) {
		// System.out.println(source);
		vertices[source].dist = 0;
		Vertex u;

		MinHeap queue = new MinHeap(size);
		for (int i = 0; i < size; i++) {
			// queue.minheap[i] = vertices[i];
			queue.minheap[i].dist = vertices[i].dist;
			queue.minheap[i].pred = vertices[i].pred;
			queue.minheap[i].vertex = vertices[i].vertex;
			queue.pos[i] = i;
		}
		queue.heapify();

		// do until there is no more nodes in minheap
		while (queue.getSize() != 0) {

			// extract vertex with the shortest distance from source
			u = queue.extractMin();
			queue.heapify();
			// System.out.println(u.getVertex());

			// temp traverses through u's adjacency list.
			AdjacencyNode temp = list[u.vertex].head;

			// traverse until you hit the end of the list, aka it will be NULL
			while (temp != null) {

				// point to vertex in graph with data gathered from adjacency list
				Vertex v = vertices[temp.vertex];

				// dpnt do anything with verteces that are trying to connect to itself
				// or with vertices that are not in queue, aka they already have a shortest path
				if (u.vertex != v.vertex && queue.inQueue(v.vertex)) {
					// relax the edge
					// queue.relax(u, v, temp.getWeight());

					if (v.dist > u.dist + temp.weight) {
						vertices[v.vertex].dist = u.dist + temp.weight;
						vertices[v.vertex].pred = u.vertex;
//						System.out.println("Relaxed: " + vertices[v.vertex].vertex + " pred is " +
//						 vertices[v.vertex].pred);
					}

					// update the queue with the new distance value
					queue.decreaseKey(v.vertex, v.dist);
				}
				// go to next vertex in adjacency list of u.
				temp = temp.next;
				 queue.heapify();
				/// temp = list.get(temp.getVertex()).getHead().getNext();
			}
		}
		// System.gc();
	}

	public int[] findPaths(int src) {
		int[] paths = new int[size];
		dijkstra(vertices[src].vertex);
		// int nextStep = -1;

		for (int i = 0; i < size; i++) {
			// if the distance to the destination vertex is INT_MAX, or a overflowed
			// negative value, that means there is no path from the source to destination
			if (vertices[i].dist < 0 || vertices[i].dist == 2147483647) {
				paths[i] = -1;
			} else if (src == i) {
				paths[i] = src;
			} else if (vertices[i].pred == src) {
				paths[i] = src;
			}
			// otherwise print the path
			else {
				// nextStep = -1;
				// using the vertex predecessor value, we could traverse back from the
				// destination vertex to the source vertex, giving us our path backwards
				Vertex traverse = vertices[i];
				// path.add(traverse.vertex);
				// traverse until we hit the source vertex
				while (traverse.vertex != src) {// && traverse.pred != -1){

					// "save" our path
					if (traverse.vertex == -1) {
						System.out.println("oops");
						paths[i] = -1;
						break;

					}
					paths[i] = traverse.vertex;
					// path.add(traverse.pred);
					// go to next predecessor
					if (traverse.pred != -1)
						traverse = vertices[traverse.pred];
				}
			}

		}
		return paths;
	}


}

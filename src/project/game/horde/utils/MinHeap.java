package project.game.horde.utils;

public class MinHeap {
	public Vertex[] minheap;
	public int[] pos;
	public int size;
	
	public MinHeap(int size) {
		this.size = size;
		pos = new int[size];
		minheap = new Vertex[size];
		for(int i = 0; i < size; i++) {
			minheap[i] = new Vertex(i);
		}
	}
	
	public void swap(int a, int b) {
		Vertex temp = minheap[a];
		minheap[a] = minheap[b];
		minheap[b] = temp;
	}
	
	public void minheapify(int i) {
		int smallest = i;
		int right = (2 * i + 2);
		int left = (2 * i + 1);
		
		if(left < size && minheap[left].dist < minheap[smallest].dist){
			smallest = left;
		}
		if(right < size && minheap[right].dist < minheap[smallest].dist){
			smallest = right;
		}
		if(smallest != i) {
		   	//pointers for simplicity
	    	Vertex smallestNode = minheap[smallest];
	    	Vertex iNode = minheap[i];

	    	//swap positions
	    	pos[smallestNode.vertex] = i;
	    	pos[iNode.vertex] = smallest;

	    	//swap the two vertices
	    	swap(i, smallest);
	    	minheapify(smallest);
		}
	}
	
	public void heapify(){
		int lastNonLeaf = size/2 - 1;
		for(int i = lastNonLeaf; i >= 0; --i){
			minheapify(i);
		}
	}
	
	//not used in program, but it creates a new node in minheap
	public void insert(int node, float key){
		//increase size of minheap
		size = size + 1;

		//use decrease key to organize
		decreaseKey(size, key);
	}

	//return the root
	public Vertex minimum(){
		return minheap[0];
	}

	//return the root and delete it
	public Vertex extractMin(){
		//min is the root of tree
		Vertex min = minheap[0];

		//the largest value becomes the new root so it doesnt get deleted
		//it replaces the root, as a simple way of deletion
		Vertex last = minheap[size - 1];
		minheap[0] = last;

		//update positions
		pos[min.vertex] = size - 1;
		pos[last.vertex] = 0;

		//decrease the size, which "deletes" the last value
		size = size - 1;

		//maintain heap property
		heapify();

		//return root
		return min;
	}

	//ensure a vertex is still within the heap, used in dijkstra function
	public boolean inQueue(int vertex){
		return pos[vertex] < size;
	}

	//update the value of a vertex in the minheap
	public void decreaseKey(int node, float value){
		//i is position of the node within minheap
		int i = pos[node];

		//update the distance of the minheap vertex to the new value
		minheap[i].dist = value;

		//traverse through and maintain heap property
		heapify();
	}

	public Vertex[] getMinheap() {
		return minheap;
	}

	public void setMinheap(Vertex[] minheap) {
		this.minheap = minheap;
	}

	public int[] getPos() {
		return pos;
	}

	public void setPos(int[] pos) {
		this.pos = pos;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	

}

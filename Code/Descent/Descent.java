package Descent;

import java.util.ArrayList;
import java.util.Random;

import Structure.*;

/* Class containing the traversing method to know the weight of a path */
public class Descent {
	private static Random rand = new Random();
	
	public Graph mountain;
	public ArrayList<Edge> path; 
	public int totalDistance;
	
	/* This methods computes a path and its total distance */
	public void Descend() {
		/* The current node is initialized on the top of the mountain */
		Node currentNode = mountain.root;
		Edge currentEdge = null;
		
		/* Until we reach the foot of the mountain */
		while ( !currentNode.equals(mountain.end) )
		{
			/* The next edge is picked randomly */
			currentEdge = pickEdge(currentNode);
			/* The edge is added to the path */
			path.add(currentEdge);
			/* The total weight of the path is updated */
			totalDistance += currentEdge.weight;
			/* The end of the edge becomes the current node */
			currentNode = currentEdge.output;
		}
	}
	
	/* This method returns an outbounding edge from the currentNode picked randomly */
	public Edge pickEdge(Node currentNode) {
		Edge edge = null;
		/* An integer between 0 and the number of outbound edges is picked randomly */
		int index = rand.nextInt( (currentNode.outbound).size() );
		/* The next edge corresponds to the index picked previously */
		edge = currentNode.outbound.get(index);
		
		return edge;
	}
	
	/* Constructor */
	public Descent(Graph mountain_) {
		mountain = mountain_;
		path = new ArrayList<Edge>();
		totalDistance = 0;
	}
}

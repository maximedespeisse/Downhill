package Generator;

import java.util.ArrayList;
import java.util.Iterator;

import Structure.Edge;
import Structure.Graph;
import Structure.Node;

public class GraphGenerator {
	private int nNodes = 200;
	private int nEdges = 40;
	private int levels = 12;
	private SkiSet topSet = null;
	private Graph graph;
	private int nodeCounter = 0;
	private ArrayList<Node> allNodes = new ArrayList<Node>();
	private ArrayList<Edge> allEdges = new ArrayList<Edge>();
	
	public static void main(String args[]) {
		GraphGenerator gn = new GraphGenerator();
		
		gn.graph = new Graph();
		gn.graph.root = null;
		gn.graph.end = null;
		
		gn.fillSets();
		gn.generateDescendingEdges();
		gn.displayGraph();
		System.out.println(gn.generateGraphML());
	}
	
	public Graph generateGraph() {
		return null;
	}
	
	public String generateGraphML() {
		String graphML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n\t<graph id=\"G\" edgedefault=\"directed\">\n";
		Iterator<Node> iNodes = allNodes.iterator();
		Iterator<Edge> iEdges = allEdges.iterator();
		Node currentNode = null;
		Edge currentEdge = null;
		
		while(iNodes.hasNext()) {
			currentNode = iNodes.next();
			graphML += "\t\t<node id=\"" + currentNode.id + "\" />\n";
		}
		
		while(iEdges.hasNext()) {
			currentEdge = iEdges.next();
			graphML += "\t\t<edge id=\"e"+currentEdge.weight+"c"+currentEdge.input.id+"_"+currentEdge.output.id+"\" source=\"" + currentEdge.input.id + "\" target=\"" + currentEdge.output.id + "\" />\n";
		}
		graphML += "\t</graph>\n</graphml>";
		return graphML;
	}
	
	public String exportToGraphML(Graph graph) {
		Node currentNode = null;
		String graphML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\">\n\t<graph id=\"G\" edgedefault=\"directed\">\n";
		
		graphML += getGraphMLrec(graph.root);
		
		graphML += "\t</graph>\n</graphml>";
		return graphML;
	}
	
	public String getGraphMLrec(Node n)
	{
		Iterator<Edge> it = null;
		String tmp = new String();
		Edge currentEdge = null;
		tmp += "\t\t<node id=\"" + n.id + "\" />\n";
		if(n.outbound!=null) {
			it = n.outbound.iterator();
			while(it.hasNext()) {
				currentEdge = it.next();
				tmp += "\t\t<edge id=\"e"+currentEdge.weight+"c"+currentEdge.input.id+"_"+currentEdge.output.id+"\" source=\"" + currentEdge.input.id + "\" target=\"" + currentEdge.output.id + "\" />\n";
				tmp += getGraphMLrec(currentEdge.output);
			}
		}
		
		return tmp;
	}
	
	public void fillSets() {
		SkiSet currentSet = new SkiSet();
		SkiSet tmpSon;
		int remainingNodes = nNodes;
		graph.end = new Node();
		allNodes.add(graph.end);
		currentSet.addNode(graph.end);
		graph.end.id = nodeCounter++;
		currentSet.level = 0;
		
		
		for (int i=1; i<levels-1; i++) {
			tmpSon = currentSet;
			
			currentSet = new SkiSet();
			currentSet.level = tmpSon.level+1;
			
			int k = MyRandom.generateNumberOfNodesInLevel(levels, i, remainingNodes);
			remainingNodes -= k;
			for (int j=0; j<k; j++) {
				Node x = new Node();
				x.id = nodeCounter++;
				currentSet.addNode(x);
				
				allNodes.add(x);
			}
			
			currentSet.son = tmpSon;
		}
		tmpSon = currentSet;
		currentSet = new SkiSet();
		Node root = new Node();
		currentSet.addNode(root);
		allNodes.add(root);
		currentSet.son = tmpSon;
		currentSet.level = tmpSon.level+1;
		topSet = currentSet;
		graph.root = root;
		root.id = nodeCounter++;
	}
	
	public void generateDescendingEdges() {
		Iterator<Node> iterator = null;
		SkiSet currentSet = topSet;
		SkiSet underSet;
		Edge currentEdge = null;
		
		while(currentSet != null) {
			iterator = currentSet.nodes.iterator();
			while(iterator.hasNext()) {
				currentEdge = new Edge();
				currentEdge.input = iterator.next();
				currentEdge.output = findEdgeRec(currentSet.son, 1);
				if(currentEdge.input != null && !edgeExists(currentEdge.input, currentEdge) && currentEdge.input.id != currentEdge.output.id) {
					currentEdge.input.outbound.add(currentEdge);
					allEdges.add(currentEdge);
				}
			}
			
			currentSet = currentSet.son;
		}
		
		currentSet = topSet;
		underSet = currentSet.son;
		while(underSet != null) {
			iterator = underSet.nodes.iterator();
			while(iterator.hasNext()) {
				currentEdge = new Edge();
				currentEdge.output = iterator.next();
				currentEdge.input = selectNodeRandomly(currentSet);
				if(!edgeExists(currentEdge.input, currentEdge) && currentEdge.input.id != currentEdge.output.id) {
					allEdges.add(currentEdge);
					currentEdge.input.outbound.add(currentEdge);
				}
			}
			currentSet = underSet;
			underSet = currentSet.son;
		}
	}
	
	public boolean edgeExists(Node node, Edge edge) {
		Iterator<Edge> it = node.outbound.iterator();
		while(it.hasNext()) {
			Edge tmpEdge = it.next();
			if(tmpEdge.input.id == edge.input.id && tmpEdge.output.id == edge.output.id) {
				return true;
			}
		}
		return false;
	}
	
	public Node findEdgeRec(SkiSet currentSet, int distance) {
		if(currentSet == null) {
			return graph.end;
		}
		else {
			if(MyRandom.inverseExponentialTest(distance)) {
				return selectNodeRandomly(currentSet);
			}
			else {
				return findEdgeRec(currentSet.son, distance+1);
			}
		}
	}
	
	public static Node selectNodeRandomly(SkiSet set) {
		return set.nodes.get(MyRandom.randomInteger(set.n));
	}
	
	public void generateEdgeSkeleton() {
		generateDescendingEdges();
	}
	
	public void addRemainingEdges() {
		
	}
	
	public void displayGraph() {
		SkiSet currentSet = topSet;
		while(currentSet != null) {
			System.out.println(currentSet);
			currentSet = currentSet.son;
		}
	}
	
}

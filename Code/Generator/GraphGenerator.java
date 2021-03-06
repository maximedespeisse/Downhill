package Generator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import Structure.Edge;
import Structure.Graph;
import Structure.Node;

public class GraphGenerator {
	private int nNodes = 100;
	private int nEdges = 40;
	private int levels = 5;
	private SkiSet topSet = null;
	public Node root;
	public Node end;
	private int nodeCounter = 0;
	private ArrayList<Node> allNodes = new ArrayList<Node>();
	private ArrayList<Edge> allEdges = new ArrayList<Edge>();
	
	public static void main(String args[]) {
		GraphGenerator gn = new GraphGenerator();
		
		//gn.graph = new Graph();
		gn.root = null;
		gn.end = null;
		
		gn.fillSets();
		gn.generateDescendingEdges();
		gn.displayGraph();
		String tmptxt = gn.generateGraphML();
		//System.out.println(tmptxt);
		
		gn.saveGraphMLFile(tmptxt);
	}
	
	public GraphGenerator() {
		this(100, 5);
	}
	
	public GraphGenerator(int n, int l) {
		nNodes = n;
		levels = l;
		
		fillSets();
		generateDescendingEdges();
		saveGraphMLFile(generateGraphML());
		//displayGraph();
		//System.out.println(root);
	}
	
	public Graph generateGraph() {
		return null;
	}
	
	public void saveGraphMLFile(String graphML) {
		PrintWriter out = null;
		try {
			out = new PrintWriter("test100.graphml");
			out.println(graphML);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			out.close();
		}
		
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
		end = new Node();
		allNodes.add(end);
		currentSet.addNode(end);
		end.id = nodeCounter++;
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
		root = new Node();
		currentSet.addNode(root);
		allNodes.add(root);
		currentSet.son = tmpSon;
		currentSet.level = tmpSon.level+1;
		topSet = currentSet;
		root = root;
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
			return end;
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

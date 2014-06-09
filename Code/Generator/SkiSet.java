package Generator;

import java.util.ArrayList;
import java.util.Iterator;

import Structure.Node;


public class SkiSet {
	public int n = 0;
	public int level;
	public ArrayList<Node> nodes = new ArrayList<Node>();
	public SkiSet son = null;
	
	public void addNode(Node newNode) {
		nodes.add(newNode);
		n++;
	}
	
	public String toString() {
		String ret = level+" ";
		Iterator<Node> it = nodes.iterator();
		
		while(it.hasNext()) {
			ret += it.next() + " ";
		}
		
		
		/*for(int i=0;i<n;i++)
			ret += "x "; */
		return ret;
	}
}

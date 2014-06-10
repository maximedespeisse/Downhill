package Structure;
import Generator.GraphGenerator;

public class Graph {
	public Node root;
	public Node end;
	
	private GraphGenerator graphGen;
	
	public Graph() {
		graphGen = new GraphGenerator();
		root = graphGen.root;
		end = graphGen.end;
	}
	
	public Graph(int n, int l) {
		graphGen = new GraphGenerator(n, l);
		root = graphGen.root;
		end = graphGen.end;
	}
}

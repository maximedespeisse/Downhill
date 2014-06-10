package Structure;
import Generator.MyRandom;


public class Edge {
	public Node input;
	public Node output;
	public int weight;
	
	public String toString() {
		return ""+output.id;
	}
	public Edge() {
		weight = MyRandom.randomInteger(100);
	}
}

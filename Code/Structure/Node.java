package Structure;
import java.util.ArrayList;
import java.util.Iterator;


public class Node {
	public int id;
	public ArrayList<Edge> outbound = new ArrayList<Edge>();
	
	public String toString() {
		String ret = "{"+id+" :";
		Iterator<Edge> it = outbound.iterator();
		while(it.hasNext()) {
			ret += " "+it.next();
		}
		ret += "}";
		return ret;
	}
	
	
}

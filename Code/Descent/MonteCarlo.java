package Descent;

import java.util.ArrayList;
import Structure.*;

public class MonteCarlo {
	
	//int iterations;
	Graph mountain;
	public int bestTotalDistance;
	public ArrayList<Edge> bestPath;
	Descent descent;
	
	/* We run the Monte Carlo experiment */
	public void run(int iterations) {	
		System.out.println(iterations);
		for (int i = 0; i < iterations; i++) {
			/* We do a new descent */
			descent = new Descent(mountain);
			//descent.Descend();
			/* If the new solution is better we change the best path and distance */
			//if (bestTotalDistance > descent.totalDistance)
			//{
			//	bestTotalDistance = descent.totalDistance;
			//	bestPath = descent.path;
			//}
		}
	}
	
	public MonteCarlo() {
		mountain = new Graph();
		bestTotalDistance = 99999;
	}
}

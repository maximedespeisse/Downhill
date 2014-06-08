package Descent;

import java.util.ArrayList;
import Structure.*;

public class MonteCarlo {
	
	int iterations;
	Graph mountain;
	int bestTotalDistance;
	ArrayList<Edge> bestPath;
	Descent descent;
	
	/* We run the Monte Carlo experiment */
	public void run() {	
		for (int i = 0; i < iterations; i++) {
			/* We do a new descent */
			descent = new Descent(mountain);
			descent.Descend();
			/* If the new solution is better we change the best path and distance */
			if (bestTotalDistance > descent.totalDistance)
			{
				bestTotalDistance = descent.totalDistance;
				bestPath = descent.path;
			}
		}
	}
	
	public MonteCarlo(int iterations_) {
		iterations = iterations_;
		mountain = new Graph();
		bestTotalDistance = 99999;
	}
}

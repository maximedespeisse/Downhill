package Generator;
import java.util.Random;


public class MyRandom {
	private static Random rng = new Random();
	
	public static int discreteGaussian(int a, int b) {
		return (int) ((rng.nextGaussian()-a)/b);
	}
	
	public static int generateNumberOfNodesInLevel(int nLevels, int currentLevel, int nNodes) {
		double normalizedDistanceToMiddle = (double)(1.0d+Math.abs(currentLevel - nLevels/2))/(double)(nLevels/2);
		
		//return (int) (Math.abs( rng.nextGaussian()/normalizedDistanceToMiddle )*nNodes);
		//return (int) (nNodes/nLevels/(1+normalizedDistanceToMiddle)/(1+normalizedDistanceToMiddle));
		return 1+(int) ((nNodes/(1-1/nLevels))/(1+normalizedDistanceToMiddle)/(1+normalizedDistanceToMiddle));
	}

	public static int randomInteger(int n) {
		return (int)(rng.nextDouble()*n);
	}
	
	public static boolean inverseExponentialTest(int x) {
		return rng.nextDouble() > 1/(Math.exp((double)x));
	}
}

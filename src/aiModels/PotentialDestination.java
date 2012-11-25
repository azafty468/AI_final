package aiModels;

import primary.Point;

public class PotentialDestination {
	public double expectedArrivalRate;
	public int targetX;
	public int targetY;
	
	public PotentialDestination(double newRate, int newX, int newY) {
		expectedArrivalRate = newRate;
		targetX = newX;
		targetY = newY;
	}
	
	public PotentialDestination(double newRate, Point targetP) {
		expectedArrivalRate = newRate;
		targetX = targetP.x;
		targetY = targetP.y;
	}
	
}

package aiModels;

import gameObjects.Board;
import gameObjects.GameObject;
import gameObjects.GameObjectCreature;
import primary.ApplicationModel;
import primary.Constants;
import primary.Point;
import primary.Constants.PolicyMove;
import view.PrintListNode;
import actions.ActionMove;

public class AIModelLearning extends AIModel {
    public ActionMove action;
    public GameObjectCreature mySelf;
    public PolicyNode myPolicies[][] = null;
	private int maxIterations = 10;
    
    private class PolicyInterim {
		public double utility;
		public int count;
	}
	
	private class PolicyNode {
		public double utility;
		public PolicyMove myPolicy;
		public boolean utilityFixed;
		public boolean unreachableSquare;
		
		PolicyNode() {
			utility = 0.0;
			myPolicy = PolicyMove.UNKNOWN;
			utilityFixed = false;
			unreachableSquare = false;
		}
		
		PolicyNode(PolicyNode dupe) {
			utility = dupe.utility;
			myPolicy = dupe.myPolicy;
			utilityFixed = dupe.utilityFixed;
			unreachableSquare = dupe.unreachableSquare;
		}
	}
    
	public AIModelLearning() {	}
	
	@Override
	public void assignToCreature(GameObjectCreature newSelf) {
		mySelf = newSelf;
		mySelf.myAIModel = this;
	}
	
	@Override
	public String describeActionPlan() { 
		return "Reinforcement Learning AI"; 
	}

	@Override
	public ActionMove planNextMove() {
		if (myPolicies == null) {
			Board myBoard = ApplicationModel.getInstance().myBoard;
			myPolicies = new PolicyNode[myBoard.height][myBoard.width];
			maxIterations = Math.max(myBoard.height, myBoard.width);
			
			for(int y = 0; y < myBoard.height; y++)
				for (int x = 0; x < myBoard.width; x++) {
					myPolicies[y][x] = new PolicyNode();
				}
			
			populateUtilities();
			determinePolicies();
		}
		
		if (mySelf == null)
			return null;
		
		if (myPolicies[mySelf.myLocation.y][mySelf.myLocation.x] == null)
			return null;
		
		PolicyMove myPolicy = myPolicies[mySelf.myLocation.y][mySelf.myLocation.x].myPolicy;
		Point targetP = Constants.outcomeOfMove(myPolicy, mySelf.myLocation);
		

		return new ActionMove(targetP, mySelf, myPolicy);
	}
	
	@Override
	public void clearTarget(GameObject oldTarget) {
		myPolicies = null;
		mySelf.currentAction = null;
	}	
	/*
	private Point getDirectionOfPolicy(PolicyMove myDirection) {
		Point myPoint = new Point(mySelf.myLocation);

		//ignore unknown
		if (myDirection == PolicyMove.LEFT)
			myPoint.x--;
		if (myDirection == PolicyMove.DOWNLEFT) {
			myPoint.y++;
			myPoint.x--;
		}
		if (myDirection == PolicyMove.DOWN)
			myPoint.y++;
		if (myDirection == PolicyMove.DOWNRIGHT) {
			myPoint.x++;
			myPoint.y++;
		}
		if (myDirection == PolicyMove.RIGHT)
			myPoint.x++;
		if (myDirection == PolicyMove.UPRIGHT) {
			myPoint.x++;
			myPoint.y--;
		}
		if (myDirection == PolicyMove.UP)
			myPoint.y--;
		if (myDirection == PolicyMove.UPLEFT) {
			myPoint.y--;
			myPoint.x--;
		}
		
		return myPoint;
	}*/
	
	private void determinePolicies() {
		for (int y = 0; y < myPolicies.length; y++) 
			for (int x = 0; x < myPolicies[y].length; x++) {
				myPolicies[y][x].myPolicy = PolicyMove.UNKNOWN;
				double bestValue = -1;
				
				if (y > 0)
					if (myPolicies[y-1][x].utility > bestValue && !myPolicies[y-1][x].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.UP;
						bestValue = myPolicies[y-1][x].utility;
					}
				if (y > 0 && x > 0)
					if (myPolicies[y-1][x-1].utility > bestValue && !myPolicies[y-1][x-1].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.UPLEFT;
						bestValue = myPolicies[y-1][x-1].utility;
					}
				if (x > 0)
					if (myPolicies[y][x-1].utility > bestValue && !myPolicies[y][x-1].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.LEFT;
						bestValue = myPolicies[y][x-1].utility;
					}
				if (x > 0 && y < (myPolicies.length - 1))
					if (myPolicies[y+1][x-1].utility > bestValue && !myPolicies[y+1][x-1].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.DOWNLEFT;
						bestValue = myPolicies[y+1][x-1].utility;
					}
				if (y < (myPolicies.length - 1))
					if (myPolicies[y+1][x].utility > bestValue && !myPolicies[y+1][x].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.DOWN;
						bestValue = myPolicies[y+1][x].utility;
					}
				if (y < (myPolicies.length - 1) && x < (myPolicies[0].length - 1))
					if (myPolicies[y+1][x+1].utility > bestValue && !myPolicies[y+1][x+1].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.DOWNRIGHT;
						bestValue = myPolicies[y+1][x+1].utility;
					}
				if (x < (myPolicies[0].length - 1))
					if (myPolicies[y][x+1].utility > bestValue && !myPolicies[y][x+1].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.RIGHT;
						bestValue = myPolicies[y][x+1].utility;
					}
				if (x < (myPolicies[0].length - 1) && y > 0)
					if (myPolicies[y-1][x+1].utility > bestValue && !myPolicies[y-1][x+1].unreachableSquare) {
						myPolicies[y][x].myPolicy = PolicyMove.UPRIGHT;
						bestValue = myPolicies[y-1][x+1].utility;
					}
			}
	}
	
	/**
	 * sets utilities for each type of space
	 * 100 for strawberries
	 * -1 for pits
	 * -2 for walls
	 * -1 for empty spaces
	 */
	private void populateUtilities() {
		// populate the initial values
		for (int y = 0; y < myPolicies.length; y++) 
			for (int x = 0; x < myPolicies[y].length; x++) {
				GameObject localGO = ApplicationModel.getInstance().findGOByLocation(new Point(x, y));
				if (localGO.name.equals("Strawberry")) {
					myPolicies[y][x].utilityFixed = true;
					myPolicies[y][x].utility = 100;
				}
				else if (localGO.name.equals("Pit")) {
					myPolicies[y][x].utilityFixed = true;
					myPolicies[y][x].utility = -1;
				}
				else if (localGO.name.equals("Wall")) {
					myPolicies[y][x].utilityFixed = true;
					myPolicies[y][x].unreachableSquare = true;
					myPolicies[y][x].utility = -2;
				}
				else {
					myPolicies[y][x].utilityFixed = false;
					myPolicies[y][x].utility = -1;
				}
			}
		
		//how many times should I do this?.  Right now it is set to the maximum of the board size
		for (int i = 0; i < maxIterations; i++)
			iteratePolicies();
	}
	
	private void iteratePolicies() {
		// average out everything in between
		PolicyNode tmpPN[][] = new PolicyNode[myPolicies.length][myPolicies[0].length];
		for (int y = 0; y < myPolicies.length; y++) 
			for (int x = 0; x < myPolicies[y].length; x++) {
				tmpPN[y][x] = new PolicyNode();
				if (myPolicies[y][x].utilityFixed) {
					tmpPN[y][x] = new PolicyNode(myPolicies[y][x]);
				} else if (myPolicies[y][x].unreachableSquare) {
					tmpPN[y][x] = new PolicyNode(myPolicies[y][x]);
				}
				else {
					PolicyInterim myPI = new PolicyInterim();
					myPI.utility = 0;
					myPI.count = 0;
					tmpPN[y][x] = new PolicyNode();
					
					//set the utility based off the neighboring utilities
					addPolicyInterim(myPI, myPolicies[y][x]); //center square
					if (y > 0)
						addPolicyInterim(myPI, myPolicies[y-1][x]); //up square
					if (y > 0 && x > 0)
						addPolicyInterim(myPI, myPolicies[y-1][x-1]); //up left square
					if (x > 0)
						addPolicyInterim(myPI, myPolicies[y][x-1]); //left square
					if (x > 0 && y < (myPolicies.length - 1))
						addPolicyInterim(myPI, myPolicies[y+1][x-1]); //down left square
					if (y < (myPolicies.length - 1))
						addPolicyInterim(myPI, myPolicies[y+1][x]); //down square
					if (y < (myPolicies.length - 1) && x < (myPolicies[0].length - 1))
						addPolicyInterim(myPI, myPolicies[y+1][x+1]); //down right square
					if (x < (myPolicies[0].length - 1))
						addPolicyInterim(myPI, myPolicies[y][x+1]); //right square
					if (x < (myPolicies[0].length - 1) && y > 0)
						addPolicyInterim(myPI, myPolicies[y-1][x+1]); //up right square
					
					if (myPI.count == 0)
						tmpPN[y][x].utility = myPolicies[y][x].utility;
					else
						tmpPN[y][x].utility = myPI.utility / myPI.count;
				}
			}
		myPolicies = tmpPN;
	}
	
	private void addPolicyInterim(PolicyInterim localPI, PolicyNode testPN) {
		if (testPN.unreachableSquare)
			return;
		
		if ((testPN.utility * 0.9) > localPI.utility)
			localPI.utility = (testPN.utility * 0.9);
		
		localPI.count = 1;
	}
	
	@Override
	public void setAdvancedView(PrintListNode[][] myPL) {
		if (myPolicies == null) 
			return;
		
		for (int y = 0; y < myPolicies.length; y++) 
			for (int x = 0; x < myPolicies[y].length; x++)
				if (myPolicies[y][x] != null)
					if (!myPolicies[y][x].unreachableSquare)
						myPL[y][x].setUtilityValue((int)myPolicies[y][x].utility);
	}

	@Override
	public void setPolicyView(PrintListNode[][] myPL) { 
		if (myPolicies == null) 
			return;
		
		for (int y = 0; y < myPolicies.length; y++) 
			for (int x = 0; x < myPolicies[y].length; x++)
				if (myPolicies[y][x] != null)
					if (!myPolicies[y][x].unreachableSquare)
						myPL[y][x].setPolicyValue(myPolicies[y][x].myPolicy);
	} 
	
	/**
	 * This function allows the player to receive a reward from the environment
	 * when it explores so it can determine what is good and what is bad. If the feedback
	 * is greater than 0, it should update the utility of the strawberries to be higher. If
	 * the feedback is less than 0, it should update the other utilities to be lower.
	 */
	@Override
	public void receiveFeedbackFromEnvironment(int feedback) {
		if(feedback > 0) {
			updateUtilities(100);
		}
		else if(feedback <= 0) {
			updateUtilities(-10);
		}
	}
	
	/**
	 * This should update the utilities of the various board objects as the player learns
	 * what is good and bad.
	 * @param reward
	 */
	private void updateUtilities(int reward) {
		// populate the new learned values
		for (int y = 0; y < myPolicies.length; y++) 
			for (int x = 0; x < myPolicies[y].length; x++) {
				GameObject localGO = ApplicationModel.getInstance().findGOByLocation(new Point(x, y));
				if (localGO.name.equals("Strawberry")) {
					myPolicies[y][x].utilityFixed = true;
					myPolicies[y][x].utility += reward;
				}
				else if (localGO.name.equals("Pit")) {
					myPolicies[y][x].utilityFixed = true;
					myPolicies[y][x].utility += reward;
				}
				else if (localGO.name.equals("Wall")) {
					myPolicies[y][x].utilityFixed = true;
					myPolicies[y][x].unreachableSquare = true;
					myPolicies[y][x].utility += reward;
				}
				else {
					myPolicies[y][x].utilityFixed = false;
					myPolicies[y][x].utility += reward;
				}
			}
		
		for (int i = 0; i < maxIterations; i++)
			iteratePolicies();
	}
}

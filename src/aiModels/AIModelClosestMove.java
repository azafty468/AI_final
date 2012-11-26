package aiModels;

import java.util.ArrayList;

import gameObjects.*;
import primary.ApplicationModel;
import primary.Constants;
import primary.Point;
import primary.Constants.PolicyMove;
import view.PrintListNode;
import actions.ActionMove;

public class AIModelClosestMove extends AIModel {	
	GameObjectCreature mySelf;
	GameObjectToken[][] allTokens;
	GameObjectToken latestTarget = null;
	
	public AIModelClosestMove() {
	}
	
	@Override
	public void assignToCreature(GameObjectCreature newSelf) {
		mySelf = newSelf;
		mySelf.myAIModel = this;
	}
	
	@Override
	public ActionMove planNextMove() {
		if (allTokens == null) { // select a new target
			Board myBoard = ApplicationModel.getInstance().myBoard;
			allTokens = new GameObjectToken[myBoard.height][myBoard.width];
			
			for(int y = 0; y < myBoard.height; y++)
				for (int x = 0; x < myBoard.width; x++) {
					allTokens[y][x] = null;
				}
			
			for (int i = 0; i < myBoard.myTokens.size(); i++) {
				GameObjectToken tempToken = myBoard.myTokens.get(i);
				
				allTokens[tempToken.myLocation.y][tempToken.myLocation.x]= tempToken; 
			}
		}
		
		latestTarget = null;
		Point myLocation = mySelf.myLocation;
		int closestDistance = -1;
		
		for (int y = 0; y < allTokens.length; y++) 
			for(int x = 0; x < allTokens[y].length; x++)
				if (allTokens[y][x] != null) {
					if ((closestDistance < 0) || (closestDistance > 
							Math.max(Math.abs(myLocation.x - allTokens[y][x].myLocation.x), 
							Math.abs(myLocation.y - allTokens[y][x].myLocation.y)))) {
						latestTarget = allTokens[y][x];
						
						closestDistance = Math.max(Math.abs(myLocation.x - allTokens[y][x].myLocation.x), Math.abs(myLocation.y - allTokens[y][x].myLocation.y));
					}
				}
		
		if (latestTarget == null)
			return null;
		
		ArrayList<PolicyMove> bestMoveList = Constants.populateBestMoveDeterministicList(mySelf.myLocation.x, mySelf.myLocation.y, latestTarget.myLocation.x, latestTarget.myLocation.y);
		PolicyMove moveTarget = bestMove(bestMoveList);
		
		if (moveTarget == null)
			return null;
		
		Point targetP = Constants.outcomeOfMove(moveTarget, mySelf.myLocation);
		
		return new ActionMove(targetP, mySelf, moveTarget);
	}
	
	private PolicyMove bestMove(ArrayList<PolicyMove> bestList) {
		for (int i = 0; i < bestList.size(); i++) {
			PolicyMove testPolicy = bestList.get(i);
			Point targetPoint = Constants.outcomeOfMove(testPolicy, mySelf.myLocation);
			
			if (!ApplicationModel.getInstance().myBoard.myGO[targetPoint.y][targetPoint.x].canBlockMovement)
				return testPolicy;
		}
		
		return null;
	}
	
	@Override
	public void clearTarget(GameObject oldTarget) {
		allTokens[oldTarget.myLocation.y][oldTarget.myLocation.x]= null; 
	}	

	@Override
	public String describeActionPlan() {
		String retval = "AIModelClosestMove - locate the Token with shortest distance with lookahead of 1 square";
		
		if (latestTarget != null) 
		  retval += "  Token: " + latestTarget.name + " at (" + latestTarget.myLocation.x + ", " + latestTarget.myLocation.y + ")";
		
		return retval;
	}
	
	@Override
	public void setAdvancedView(PrintListNode[][] myPL) {		
		if (allTokens == null)
			return;
		
		for (int y = 0; y < allTokens.length; y++) 
			for (int x = 0; x < allTokens[y].length; x++)
				if (visibleSquares[y][x])
					if (allTokens[y][x] != null) {
						if (latestTarget == null) 
							myPL[y][x].setUtilityValue(50);					
						else if (allTokens[y][x].equals(latestTarget))
							myPL[y][x].setUtilityValue(100);
						else
							myPL[y][x].setUtilityValue(50);
					}
	}

}

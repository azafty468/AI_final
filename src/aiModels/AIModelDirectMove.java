package aiModels;

import java.util.ArrayList;
import java.util.Stack;

import primary.ApplicationModel;
import primary.Constants;
import primary.Constants.PolicyMove;
import primary.Point;
import gameObjects.Board;
import gameObjects.GameObject;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectCreature.CreatureAlliance;
import gameObjects.GameObjectPlayer;
import gameObjects.GameObjectToken;
import actions.ActionMove;

/**
 * Move directly at the target with a look ahead of 1 square
 *
 * @author Trevor - primary author
 * @author Andrew - minor updates
 */
public class AIModelDirectMove extends AIModel {
	GameObject myTarget;
	GameObjectCreature mySelf;

	public AIModelDirectMove() {
		myTarget = null;
	}
	
	@Override
	public void assignToCreature(GameObjectCreature newSelf) {
		mySelf = newSelf;
		mySelf.myAIModel = this;
	}
	
	@Override
	public ActionMove planNextMove() {
		if (myTarget == null) {
			
			if (mySelf.myAlliance == CreatureAlliance.PLAYER) {
				Board myBoard = ApplicationModel.getInstance().myBoard;
				if (myBoard.myTokens.isEmpty()) 
					return null;
				
				myTarget = myBoard.myTokens.get(0);
			}
			else //this is a ghost
				myTarget = ApplicationModel.getInstance().myPlayer;
		}

		if (myTarget == null)
			return null;

		ArrayList<PolicyMove> bestMoveList = Constants.populateBestMoveDeterministicList(mySelf.myLocation.x, mySelf.myLocation.y, myTarget.myLocation.x, myTarget.myLocation.y);
		PolicyMove moveTarget = bestMove(bestMoveList);
		
		if (moveTarget == null)
			return null;
		
		Point targetP = Constants.outcomeOfMove(moveTarget, mySelf.myLocation);
		
		return new ActionMove(targetP, mySelf, moveTarget);
	}
	
	@Override
	public void clearTarget(GameObject oldTarget) {
		if (myTarget == null)
			return;
		
		if (oldTarget == myTarget) {
			myTarget = null;
		}
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
	public String describeActionPlan() {
		String retval = "Direct Move - Move directly to the target object with look ahead of 1 square for blocking squares only";
		
		if (myTarget != null) 
			retval += "  Target: " + myTarget.name + " at (" + myTarget.myLocation.x + ", " + myTarget.myLocation.y + ")";
		
		return retval;
	}
}

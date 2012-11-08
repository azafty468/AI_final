package aiModels;

import primary.ApplicationModel;
import gameObjects.Board;
import gameObjects.GameObject;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectPlayer;
import gameObjects.GameObjectToken;
import actions.ActionMove;

public class AIModelDirectMove extends AIModel {
	GameObjectToken myTarget;
	GameObjectCreature mySelf;
	GameObjectPlayer player;
	
	public AIModelDirectMove() {
		myTarget = null;
		player = ApplicationModel.getInstance().myPlayer;
	}
	
	@Override
	public void assignToCreature(GameObjectCreature newSelf) {
		mySelf = newSelf;
		mySelf.myAIModel = this;
	}
	
	@Override
	public ActionMove planNextMove() {
		if (myTarget == null) { // select a new target
			Board myBoard = ApplicationModel.getInstance().myBoard;
			if (myBoard.myTokens.isEmpty()) 
				return null;
			
			for(int i=0; i < myBoard.myTokens.size(); i++) {
				myTarget = myBoard.myTokens.get(i);
				if(myTarget == null) {
					myTarget = myBoard.myTokens.get(i);
				}
			}
		}
		
		int x, y;
		x = mySelf.myLocation.x;
		y = mySelf.myLocation.y;
		if (x < myTarget.myLocation.x)
			x++;
		if (x >myTarget.myLocation.x)
			x--;
		if (y < myTarget.myLocation.y)
			y++;
		if (y > myTarget.myLocation.y)
			y--;
		
		return new ActionMove(x-1, y, mySelf);
	}
	
	@Override
	public void clearTarget(GameObject oldTarget) {
		if (myTarget == null)
			return;
		
		if (oldTarget == myTarget) {
			myTarget = null;
		}
	}
	
	@Override
	public String describeActionPlan() {
		String retval = "AIModelDirectMove - Move to top token in Token ArrayList.";
		
		if (myTarget != null) 
			retval += "  Token: " + myTarget.name + " at (" + myTarget.myLocation.x + ", " + myTarget.myLocation.y + ")";
		
		return retval;
	}
}

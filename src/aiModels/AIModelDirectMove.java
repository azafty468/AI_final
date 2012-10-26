package aiModels;

import primary.ApplicationModel;
import gameObjects.Board;
import gameObjects.GameObject;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectToken;
import actions.ActionMove;

public class AIModelDirectMove extends AIModel {
	GameObjectToken myTarget;
	GameObjectCreature mySelf;
	
	public AIModelDirectMove(GameObjectCreature newSelf) {
		myTarget = null;
		mySelf = newSelf;
	}
	
	@Override
	public ActionMove planNextMove() {
		if (myTarget == null) { // select a new target
			Board myBoard = ApplicationModel.getInstance().myBoard;
			if (myBoard.myTokens.isEmpty()) 
				return null;
			
			myTarget = myBoard.myTokens.get(0);
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
		
		return new ActionMove(x, y, mySelf);
	}
	
	@Override
	public void clearTarget(GameObject oldTarget) {
		if (myTarget == null)
			return;
		
		if (oldTarget == myTarget) {
			myTarget = null;
		}
	}
}

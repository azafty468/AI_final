package aiModels;

import gameObjects.*;
import primary.ApplicationModel;
import primary.Point;
import actions.ActionMove;

public class AIModelClosestMove extends AIModel {	
	GameObjectCreature mySelf;
	GameObjectToken[][] allTokens;
	GameObjectToken latestTarget = null;
	
	public AIModelClosestMove(GameObjectCreature newSelf) {
		mySelf = newSelf;
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
		
		int x, y;
		x = mySelf.myLocation.x;
		y = mySelf.myLocation.y;
		if (x < latestTarget.myLocation.x)
			x++;
		if (x > latestTarget.myLocation.x)
			x--;
		if (y < latestTarget.myLocation.y)
			y++;
		if (y > latestTarget.myLocation.y)
			y--;
		
		return new ActionMove(x, y, mySelf);
	}
	
	@Override
	public void clearTarget(GameObject oldTarget) {
		allTokens[oldTarget.myLocation.y][oldTarget.myLocation.x]= null; 
	}	

	@Override
	public String describeActionPlan() {
		String retval = "AIModelClosestMove - locate the Token with shortest distance.";
		
		if (latestTarget != null) 
		  retval += "  Token: " + latestTarget.name + " at (" + latestTarget.myLocation.x + ", " + latestTarget.myLocation.y + ")";
		
		return retval;
	}

}

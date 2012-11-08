package aiModels;

import primary.ApplicationModel;
import primary.Point;
import gameObjects.Board;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectPlayer;
import actions.ActionMove;

/**
 * @author Trevor Hodde
 */
public class AIModelDijkstraAlgorithm extends AIModel {
	GameObjectCreature enemy;
	GameObjectPlayer player;
	
	//constructor to grab a reference to the red ghost
	public AIModelDijkstraAlgorithm() {
	}
	
	@Override
	public void assignToCreature(GameObjectCreature newSelf) {
		enemy = newSelf;
		enemy.myAIModel = this;
	}

	@Override
	public ActionMove planNextMove() {
		//grabs the board and the player objects
		Board myBoard = ApplicationModel.getInstance().myBoard;
		player = ApplicationModel.getInstance().myPlayer;
		
		//these are used to track where the enemy currently is
		//and where the player currently is
		GameObjectPlayer closestTarget = null;
		Point myLocation = enemy.myLocation;
		Point playerLocation = player.myLocation;
		int closestDistance = -1;
		
		//This allows the enemy to follow the player around
		for (int y = 0; y < myBoard.height; y++) {
			for(int x = 0; x < myBoard.width; x++) {
				if (playerLocation != null) {
					if ((closestDistance < 0) || (closestDistance > 
							Math.max(Math.abs(myLocation.x - playerLocation.x), 
							Math.abs(myLocation.y - playerLocation.y)))) {
						closestTarget = player;
						closestDistance = Math.max(Math.abs(myLocation.x -playerLocation.x), Math.abs(myLocation.y - playerLocation.y));
					}
				}
			}
		}
		
		//makes sure there is an actual player set up
		if (closestTarget == null)
			return null;
		
		//moves the enemy towards the player
		int x, y;
		x = enemy.myLocation.x;
		y = enemy.myLocation.y;
		if (x < closestTarget.myLocation.x)
			x++;
		if (x > closestTarget.myLocation.x)
			x--;
		if (y < closestTarget.myLocation.y)
			y++;
		if (y > closestTarget.myLocation.y)
			y--;
		
		return new ActionMove(x, y, enemy);
	}
}

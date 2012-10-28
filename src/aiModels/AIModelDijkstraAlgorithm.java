package aiModels;

import primary.ApplicationModel;
import primary.Point;
import gameObjects.Board;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectToken;
import actions.ActionMove;

/**
 * @author Trevor Hodde
 */
public class AIModelDijkstraAlgorithm extends AIModel {
	GameObjectCreature enemy;
	GameObjectToken[][] allTokens;
	
	public AIModelDijkstraAlgorithm(GameObjectCreature enemy) {
		this.enemy = enemy;
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
		
		GameObjectToken closestTarget = null;
		Point myLocation = enemy.myLocation;
		int closestDistance = -1;
		
		for (int y = 0; y < allTokens.length; y++) 
			for(int x = 0; x < allTokens[y].length; x++)
				if (allTokens[y][x] != null) {
					if ((closestDistance < 0) || (closestDistance > 
							Math.max(Math.abs(myLocation.x - allTokens[y][x].myLocation.x), 
							Math.abs(myLocation.y - allTokens[y][x].myLocation.y)))) {
						closestTarget = allTokens[y][x];
						closestDistance = Math.max(Math.abs(myLocation.x - allTokens[y][x].myLocation.x), Math.abs(myLocation.y - allTokens[y][x].myLocation.y));
					}
				}
		
		if (closestTarget == null)
			return null;
		
		int x, y;
		x = enemy.myLocation.x;
		y = enemy.myLocation.y;
		if (x < closestTarget.myLocation.x)
			x++;
		if (x >closestTarget.myLocation.x)
			x--;
		if (y < closestTarget.myLocation.y)
			y++;
		if (y > closestTarget.myLocation.y)
			y--;
		
		return new ActionMove(x, y, enemy);
	}
}

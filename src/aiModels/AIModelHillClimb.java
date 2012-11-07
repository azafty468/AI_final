package aiModels;

import gameObjects.Board;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectPlayer;

import primary.ApplicationModel;
import primary.Point;

import actions.ActionMove;

/**
 * @author Trevor
 * 
 * Definitely Does not work yet.....
 */
class PotentialMove {
	public int xCoordinate, yCoordinate;
	
	PotentialMove(){
	}
	
	PotentialMove(int newX, int newY) {
		xCoordinate = newX;
		yCoordinate = newY;
	}
}

public class AIModelHillClimb extends AIModelEnemy {
	protected Board myBoard;
	protected GameObjectPlayer player;
	protected GameObjectCreature enemy;
	protected Point playerLocation;
	protected Point enemyLocation;
	protected boolean foundBetterMove;
	public enum testDirection { LEFT, UP, DOWN, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT; } 
	
	public AIModelHillClimb(GameObjectCreature enemy) {
		myBoard = ApplicationModel.getInstance().myBoard;
		player = ApplicationModel.getInstance().myPlayer;
		this.enemy = enemy;
		playerLocation = player.myLocation;
		enemyLocation = enemy.myLocation;
	}

	public void populateHillValues(){
		for (int i = 0; i <= (myBoard.height-1); i++){
			enemyLocation.x += 1; 
			setSquareValues(testDirection.UPLEFT, i, enemyLocation.x, false);
			setSquareValues(testDirection.UPRIGHT, i, enemyLocation.x, false);
			setSquareValues(testDirection.DOWNLEFT, i, enemyLocation.x, false);
			setSquareValues(testDirection.DOWNRIGHT, i, enemyLocation.x, false);
			setSquareValues(testDirection.LEFT, i, enemyLocation.x, false);
			setSquareValues(testDirection.RIGHT, i, enemyLocation.x, false);
			setSquareValues(testDirection.DOWN, i, enemyLocation.x, false);
			setSquareValues(testDirection.UP, i, enemyLocation.x, false);
		}
	}

	public void setSquareValues(testDirection direction, int x, int y, boolean incrementSquare){
		if ((x >= 0) && (x < myBoard.width) && (y>= 0) && (y < myBoard.height)){
			if (incrementSquare)
				enemyLocation.x++;
			int newX = x;
			int newY = y;
			switch (direction){
			case UPLEFT:
				newX--;
				newY--;
				break;
			case UPRIGHT:
				newX++;
				newY--;
				break;
			case DOWNLEFT:
				newX--;
				newY++;
				break;
			case DOWNRIGHT:
				newX++;
				newY++;
				break;
			case UP:
				newY--;
				break;
			case DOWN:
				newY++;
				break;
			case RIGHT:
				newX++;
				break;
			case LEFT:
				newX--;
				break;
			default:
				break;
			}
			
			setSquareValues(direction, newX, newY, true);
		}
	}
	
	@Override
	public ActionMove planNextMove() {
		//reset the flag that indicates if a move has been found that decreases the heuristic
		foundBetterMove = false;
		
		//fill in the appropriate heuristic values
		populateHillValues();
		
		PotentialMove bestMove = new PotentialMove();
		int closestDistance = -1;

		//Find the square with the lowest heuristic value.  this should really write the values to an array.  
		int lowestSquareValue = myBoard.height;
		for (int y = 0; y < myBoard.height; y++) {
			for (int x = 0; x < myBoard.width; x++){
				if ((closestDistance < lowestSquareValue) &&
						(playerLocation.y != enemyLocation.y) &&
						(playerLocation.x < enemyLocation.x)) { //and in fact is better than the currently occupied square
					lowestSquareValue = closestDistance;
					bestMove.xCoordinate = x;
					bestMove.yCoordinate = y;
				}
			}
		}
		
		//Only flag that a better move is available if the lowest square is better than all squares currently occupied by a queen 
		for (int i = 0; i < myBoard.height; i++) {
			if (lowestSquareValue < closestDistance)
				foundBetterMove = true;
		}
		
		//make the move
		if (foundBetterMove) {
			enemyLocation.y = bestMove.yCoordinate;
		}
		
		return new ActionMove(bestMove.xCoordinate, bestMove.yCoordinate, enemy);
	}
}

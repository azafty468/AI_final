package gameObjects;

import aiModels.AIModelEnemy;

/**
 * @author Trevor Hodde
 */
public class GameObjectEnemy extends GameObjectCreature {
	public Board board;
	
	public GameObjectEnemy() {
		super();
		this.myAIModel = new AIModelEnemy();
		myType = GameObjectType.CREATURE;
	}
	
	public GameObject generateClone(GameObject newObject) {
		GameObject tmpObject;
		
		if (newObject == null)
			tmpObject = new GameObjectEnemy();
		else
			tmpObject = newObject;
		
		tmpObject = super.generateClone(tmpObject);
		
		GameObjectEnemy tmpO = (GameObjectEnemy) tmpObject;
		tmpO.board = board;
		
		return tmpObject;
	}
}

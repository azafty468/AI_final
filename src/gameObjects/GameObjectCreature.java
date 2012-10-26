package gameObjects;

import actions.Action;
import actions.ActionMove;
import aiModels.AIModel;

/**
 * Controls all aspects of GameObjects that are capable of independent action
 * @author Andrew
 *
 */
public class GameObjectCreature extends GameObject {
	public AIModel myAIModel;
	public Action currentAction;
	
	public GameObjectCreature() {
		super();
		canBlockMovement = true;
		currentAction = null;
		myAIModel = null;
		myType = GameObjectType.CREATURE;
	}
	
	public GameObject generateClone(GameObject newObject) {
		GameObject tmpObject;
		
		if (newObject == null)
			tmpObject = new GameObjectCreature();
		else
			tmpObject = newObject;
		
		tmpObject = super.generateClone(tmpObject);
		
		GameObjectCreature tmpO = (GameObjectCreature) tmpObject;
		tmpO.myAIModel = myAIModel;
		
		return tmpObject;
	}
	
	public void planNextMove() {
		if (myAIModel == null) {
			return;
		}
		currentAction = myAIModel.planNextMove();
	}
	
	public void clearTarget(GameObject target) {
		if (myAIModel == null) {
			return;
		}
		myAIModel.clearTarget(target);
	}
}

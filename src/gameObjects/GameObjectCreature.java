package gameObjects;

import actions.Action;

/**
 * Controls all aspects of GameObjects that are capable of independent action
 * @author Andrew
 *
 */
public class GameObjectCreature extends GameObject {
	public AIModel myAIModel;
	Action currentAction;
	
	GameObjectCreature() {
		super();
		canBlockMovement = true;
		currentAction = null;
		myAIModel = null;
	}
}

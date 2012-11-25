package actions;

import primary.Constants.PolicyMove;
import primary.PhysicsEngine;
import primary.Point;
import gameObjects.GameObjectCreature;

/*
 * The action container for any Move action
 */
public class ActionMove extends Action {
	public int targetX;
	public int targetY;
	public PolicyMove moveDirection;
	public GameObjectCreature initiator;
	
	public ActionMove(int newX, int newY, GameObjectCreature newInitiator) {
		super();
		
		targetX = newX;
		targetY = newY;
		initiator = newInitiator;
		moveDirection = PolicyMove.UNKNOWN;
	}
	
	public ActionMove(Point targetLocation, GameObjectCreature newInitiator, PolicyMove targetDirection) {
		super();
		
		targetX = targetLocation.x;
		targetY = targetLocation.y;
		initiator = newInitiator;
		moveDirection = targetDirection;
	}

	public void processAction() {
		PhysicsEngine.moveCreature(this);
	}

	@Override
	public String describeAction() {
		String retval = initiator.name + " moving to (" + targetX + ", " + targetY + ")";
		return retval;
	}

}

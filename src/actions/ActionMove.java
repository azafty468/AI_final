package actions;

import primary.PhysicsEngine;
import primary.Point;
import gameObjects.GameObjectCreature;

/*
 * The action container for any Move action
 */
public class ActionMove extends Action {
	public int targetX;
	public int targetY;
	public GameObjectCreature initiator;
	
	public ActionMove(int newX, int newY, GameObjectCreature newInitiator) {
		super();
		
		targetX = newX;
		targetY = newY;
		initiator = newInitiator;
	}
	
	public ActionMove(Point targetLocation, GameObjectCreature newInitiator) {
		super();
		
		targetX = targetLocation.x;
		targetY = targetLocation.y;
		initiator = newInitiator;
	}
	
	public void processAction() {
		PhysicsEngine.moveCreature(this);
	}

}

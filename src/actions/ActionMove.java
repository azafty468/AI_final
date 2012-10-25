package actions;

import primary.PhysicsEngine;
import primary.Point;
import gameObjects.GameObject;

/*
 * The action container for any Move action
 */
public class ActionMove extends Action {
	public int targetX;
	public int targetY;
	public GameObject initiator;
	
	public ActionMove(int newX, int newY, GameObject newInitiator) {
		super();
		
		targetX = newX;
		targetY = newY;
		initiator = newInitiator;
	}
	
	public ActionMove(Point targetLocation, GameObject newInitiator) {
		super();
		
		targetX = targetLocation.x;
		targetY = targetLocation.y;
		initiator = newInitiator;
	}
	
	public void processAction() {
		PhysicsEngine.moveCreature(this);
	}

}

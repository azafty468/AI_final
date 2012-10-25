package primary;

import view.ApplicationView;
import gameObjects.GameObject;
import actions.ActionMove;


public class PhysicsEngine {

	public static void moveCreature(ActionMove myAM) {
		int targetX = myAM.initiator.myLocation.x;
		int targetY = myAM.initiator.myLocation.y;
		
		if (targetX < myAM.targetX) {
			targetX++;
		}
		if (targetX > myAM.targetX) {
			targetX--;
		}
		if (targetY < myAM.targetY) {
			targetY++;
		}		
		if (targetY > myAM.targetY) {
			targetY--;
		}
		
		ApplicationModel myModel = ApplicationModel.getInstance();
		GameObject tmp = myModel.findGOByLocation(new Point(targetX, targetY));
		
		ApplicationView myView = ApplicationView.getInstance();
		
		if (tmp == null) {
			myAM.setIsDone(true);
			myView.displayMessage("Error, attempting to move '" + myAM.initiator.name + "' to a location that does not have a background.");
		}
		else if (tmp.canBlockMovement) {
			myAM.setIsDone(true);
			myView.displayMessage("Error, while moving '" + myAM.initiator.name + "' encountered a square blocked by '" + tmp.name + "'");
		}
		else {
			myAM.initiator.myLocation.x = targetX;
			myAM.initiator.myLocation.y = targetY;
		}

		if ((myAM.initiator.myLocation.x == myAM.targetX) && (myAM.initiator.myLocation.y == myAM.targetY))
			myAM.setIsDone(true);
	}
}
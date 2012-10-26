package primary;

import view.ApplicationView;
import gameObjects.*;
import gameObjects.GameObject.GameObjectType;
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
			myView.displayMessage("Error, attempting to move '" + myAM.initiator.name + "' to a location that does not have a background object.");
		}
		else if (tmp.canBlockMovement) {
			myAM.setIsDone(true);
			myView.displayMessage("Error, while moving '" + myAM.initiator.name + "' encountered a square blocked by '" + tmp.name + "'");
		}
		else {
			if ((tmp.getType() == GameObjectType.TOKEN) && (myAM.initiator.getType() == GameObjectType.PLAYER)) {
				GameObjectPlayer tmpPlayer = (GameObjectPlayer) myAM.initiator;
				GameObjectToken tmpToken = (GameObjectToken) tmp;
				tmpPlayer.pointsGained += tmpToken.pointValue;
				
				myModel.myBoard.removeToken(tmpToken);
				myView.displayMessage("Player '" + tmpPlayer.name + "' captured token '" + tmpToken.name + "'.  Player now has " + tmpPlayer.pointsGained + " points.");
			}
			
			myAM.initiator.myLocation.x = targetX;
			myAM.initiator.myLocation.y = targetY;
		}

		if ((myAM.initiator.myLocation.x == myAM.targetX) && (myAM.initiator.myLocation.y == myAM.targetY))
			myAM.setIsDone(true);
	}
}
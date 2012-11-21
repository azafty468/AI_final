package primary;

import view.ApplicationView;
import gameObjects.*;
import gameObjects.GameObject.GameObjectType;
import actions.*;

public class PhysicsEngine {
	//change this for non-deterministic worlds
	public static boolean deterministicMovement = true;
	
	//the overall multiplier for non-deterministic worlds.  the forward location is at [1][2].  Array values must add up to 1
	public static double movementModifier[][] = { {0, 0, 0}, {0, 0, 1}, {0, 0, 0}};

	public static void moveCreature(ActionMove myAM) {
		int targetX = myAM.initiator.myLocation.x;
		int targetY = myAM.initiator.myLocation.y;

		if (deterministicMovement) {
			targetX = myAM.initiator.myLocation.x;
			targetY = myAM.initiator.myLocation.y;
		}
		
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
		
		ApplicationController.getInstance().loggedEvents.add(new EventMove(myAM.initiator.name, new Point(targetX, targetY)).writeLogString());
		
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
				ApplicationController.getInstance().currentEvents.push(new EventPickupToken(tmpPlayer, tmpToken));
			}
			
			myAM.initiator.myLocation.x = targetX;
			myAM.initiator.myLocation.y = targetY;
			myAM.initiator.stepsTaken++;
			
			// make the square and all around it visible
			if (myAM.initiator.getType() == GameObjectType.PLAYER)
				myModel.myBoard.setVisibleSquares(myAM.initiator.myLocation);
			
			if (myModel.myBoard.myGO[targetY][targetX].name.equals("Pit"))
				ApplicationController.getInstance().currentEvents.push(new EventFallenInPit(myAM.initiator));
			
			GameObjectCreature redGhost = myModel.redGhost;
			if (redGhost != null)
				if ((myAM.initiator.myLocation.equals(redGhost.myLocation)) && (myAM.initiator.myAlliance != redGhost.myAlliance)) {
					GameObjectPlayer tmpPlayer = (GameObjectPlayer) myAM.initiator;
					ApplicationController.getInstance().currentEvents.push(new EventGhostTouchPlayer(myModel.redGhost, tmpPlayer));
				}
			
			GameObjectCreature blueGhost = myModel.blueGhost;
			if (blueGhost != null)
		if ((myAM.initiator.myLocation.equals(blueGhost.myLocation)) && (myAM.initiator.myAlliance != blueGhost.myAlliance)) {
			GameObjectPlayer tmpPlayer = (GameObjectPlayer) myAM.initiator;
			ApplicationController.getInstance().currentEvents.push(new EventGhostTouchPlayer(myModel.blueGhost, tmpPlayer));
		}
			
			GameObjectPlayer myPlayer = myModel.myPlayer;
			if ((myAM.initiator.myLocation.equals(myPlayer.myLocation)) && (myAM.initiator.myAlliance != myPlayer.myAlliance)) {
				ApplicationController.getInstance().currentEvents.push(new EventGhostTouchPlayer(myAM.initiator, myPlayer));
			}
		}

		if ((myAM.initiator.myLocation.x == myAM.targetX) && (myAM.initiator.myLocation.y == myAM.targetY))
			myAM.setIsDone(true);
	}
}
package actions;

import java.io.BufferedWriter;
import java.io.IOException;

import primary.ApplicationController;
import primary.ApplicationModel;
import primary.Constants;
import view.ApplicationView;
import gameObjects.*;

public class EventPickupToken extends Event {
	GameObjectPlayer localPlayer;
	GameObjectToken localToken;
	
	public EventPickupToken(GameObjectPlayer newPlayer, GameObjectToken newToken) {
		localPlayer = newPlayer;
		localToken = newToken;
	}
	
	@Override
	public void processEvent() {
		localPlayer.setPointsGained(localToken.pointValue);
		localPlayer.berriesPickedUp++;
		
		ApplicationController.getInstance().loggedEvents.add(writeLogString());
		
		ApplicationModel.getInstance().myBoard.removeToken(localToken);
		ApplicationView.getInstance().displayMessage("Player '" + localPlayer.name + "' captured token '" + 
				localToken.name + "'.  Player now has " + localPlayer.getPointsGained() + " points.");
		
		//TODO there is a better mechanism than this
		ApplicationModel.getInstance().myPlayer.clearTarget(localToken);
		
		if (ApplicationModel.getInstance().blueGhost != null)
			ApplicationModel.getInstance().blueGhost.clearTarget(localToken);
		
		if (ApplicationModel.getInstance().redGhost != null)
			ApplicationModel.getInstance().redGhost.clearTarget(localToken);
		
		if (ApplicationModel.getInstance().myBoard.myTokens.size() == 0) {
			ApplicationView.getInstance().displayMessage("Congratulations, you have collected all the tokens in " + localPlayer.stepsTaken + " moves ending with " + localPlayer.getPointsGained() + " points.  The game is over!");
			ApplicationController.getInstance().finishGame("");
		}

	}

	@Override
	public String writeLogString() {
		return "<EventPickupToken player='" + localPlayer.name + "' points='" + localToken.pointValue + "' " +
					"tokenName='" + localToken.name + "' " +
					"location='(" + localToken.myLocation.x + "," + localToken.myLocation.y + ")' " +
					"/>";
	}

}

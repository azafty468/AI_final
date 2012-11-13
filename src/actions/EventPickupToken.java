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
		localPlayer.pointsGained += localToken.pointValue;
		
		ApplicationController.getInstance().loggedEvents.add(writeLogString());
		
		ApplicationModel.getInstance().myBoard.removeToken(localToken);
		ApplicationView.getInstance().displayMessage("Player '" + localPlayer.name + "' captured token '" + 
				localToken.name + "'.  Player now has " + localPlayer.pointsGained + " points.");
		
		//TODO there is a better mechanism than this
		ApplicationModel.getInstance().myPlayer.clearTarget(localToken);
		ApplicationModel.getInstance().blueGhost.clearTarget(localToken);
		ApplicationModel.getInstance().redGhost.clearTarget(localToken);
		
		if (ApplicationModel.getInstance().myBoard.myTokens.size() == 0) {
			//TODO: end game reached.  What to print?
			ApplicationView.getInstance().displayMessage("Congratulations, you have collected all the tokens in " + localPlayer.stepsTaken + " moves ending with " + localPlayer.pointsGained + " points.  The game is over!");
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

package actions;

import primary.ApplicationController;
import primary.ApplicationModel;
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
			ApplicationController.getInstance().gameOver = true;
		}

	}

}

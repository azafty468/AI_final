package actions;

import primary.ApplicationController;
import view.ApplicationView;
import gameObjects.*;

public class EventGhostTouchPlayer extends Event {
	GameObjectCreature toucher;
	GameObjectPlayer touchee;
	
	public EventGhostTouchPlayer(GameObjectCreature toucher, GameObjectPlayer touchee) {
		this.toucher = toucher;
		this.touchee = touchee;
	}

	@Override
	public void processEvent() {
		ApplicationController.getInstance().loggedEvents.add(this);
		touchee.touchedByGhost++;
		ApplicationView.getInstance().displayMessage("Player '" + touchee.name + "' has been touched by '" + toucher.name + "'." );
	}

}

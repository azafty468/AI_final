package actions;

import java.io.BufferedWriter;
import java.io.IOException;

import primary.ApplicationController;
import primary.Constants;
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
		ApplicationController.getInstance().loggedEvents.add(writeLogString());
		touchee.touchedByGhost++;
		ApplicationView.getInstance().displayMessage("Player '" + touchee.name + "' has been touched by '" + toucher.name + "'." );
	}

	@Override
	public String writeLogString() {
		return "<EventGhostTouch player='" + touchee.name + "' " +
				"ghost='" + toucher.name + "' location='(" + touchee.myLocation.x + "," + touchee.myLocation.y + ")' " + "/>";
	}

}

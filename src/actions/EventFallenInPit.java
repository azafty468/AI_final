package actions;

import view.ApplicationView;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectPlayer;

public class EventFallenInPit extends Event {
	GameObjectCreature targetCreature;
	
	public EventFallenInPit(GameObjectCreature newTarget) {
		targetCreature = newTarget;
	}
	
	@Override
	public void processEvent() {
		if (targetCreature instanceof GameObjectPlayer) {
			GameObjectPlayer tmpP = (GameObjectPlayer) targetCreature;
			ApplicationView.getInstance().displayMessage("Player '" + tmpP.name + "' has fallen into a pit.  100 points have been deducted.");
			tmpP.pointsGained -= 100;
		}
		else
			ApplicationView.getInstance().displayMessage("Creature '" + targetCreature.name + "' has fallen into a pit.");
		
		targetCreature.currentAction = new ActionStuckInPit();
	}

}

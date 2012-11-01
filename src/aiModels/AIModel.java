package aiModels;

import gameObjects.GameObject;
import actions.ActionMove;

public abstract class AIModel {
	
	/**
	 * This action returns the next move to be done.  However, this doesn't limit the AI Model
	 * to only level deep action planning.  Internal representations can store multiple actions,
	 * and this method can pop them off one at a time.
	 * 
	 * @return
	 */
	public abstract ActionMove planNextMove();
	public void clearTarget(GameObject oldTarget) { return; }
	public String describeActionPlan() { return "undefined"; }
}

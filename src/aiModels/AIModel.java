package aiModels;

import actions.ActionMove;

public abstract class AIModel {
	
	/**
	 * This action returns the next move to be done.  However, this doesn't limit the AI Model
	 * to only level deep action planning.  Internal representations can store multiple actions,
	 * and this method can pop them off one at a time.
	 * 
	 * @return
	 */
	public ActionMove planNextMove() {
		return null;
	}
}

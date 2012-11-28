package aiModels;

import primary.ApplicationController;
import primary.ApplicationModel;
import primary.Point;
import view.PrintListNode;
import gameObjects.Board;
import gameObjects.GameObject;
import gameObjects.GameObjectCreature;
import actions.ActionMove;

public abstract class AIModel {
	public boolean visibleSquares[][];
	
	public void setInitialValues(boolean initialValue) {
		Board localBoard = ApplicationModel.getInstance().myBoard;
		visibleSquares = new boolean[localBoard.height][localBoard.width];
		for (int y = 0; y < visibleSquares.length; y++)
			for (int x = 0; x < visibleSquares[y].length; x++)
				visibleSquares[y][x] = initialValue;
	}
	
	public void setVisibleSquares(Point centerPoint) {
		if (ApplicationController.getInstance().myLoadConfiguration.visibleWorld)
			return;
		
		int sightRange = ApplicationController.VISIBILITYRANGE;
		
		for (int y = centerPoint.y - sightRange; y <= (centerPoint.y + sightRange); y++)
			for (int x = centerPoint.x - sightRange; x <= (centerPoint.x + sightRange); x++) {
				if (x >= 0 && x < visibleSquares[0].length && y >= 0 && y < visibleSquares.length) {
					visibleSquares[y][x] = true;
				}
			}
	}
	
	
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
	
	/**
	 * This sets the more advanced view features such as coloring and utility/heuristic values.  It is up to the 
	 * child AIModel to determine how this is used.
	 * 
	 * @param myPL
	 */
	public void setAdvancedView(PrintListNode[][] myPL) { return; } 
	
	/**
	 * This sets the more advanced view features for policies.  This is only implemented on models that actually 
	 * do some level of policy determination.  It is up to the child AIModel to determine how this is used.  This guy
	 * should be overridden second to the setAdvancedView function above.
	 * 
	 * @param myPL
	 */
	public void setPolicyView(PrintListNode[][] myPL) { return; } 
	
	/**
	 * This method assigns the AI model to the creature and creates any necessary linking back in the AI model
	 * UPDATE (11/8/12): This is a change from the old code.  This replaces the multiple different constructors we were
	 * using.  This is set up so that we can create the AI model before the creature is created.
	 * 
	 * @param newSelf
	 */
	public void assignToCreature(GameObjectCreature newSelf) { newSelf.myAIModel = this; }
	
	/**
	 * This method is used for communicating information from the environment back to the AI model since
	 * not all the attributes of a model or a game object are public and we need to share feedback somehow
	 * in order to help the player learn about its environment as it explores.
	 * 
	 * @param feedback
	 */
	public void receiveFeedbackFromEnvironment(double feedback, String itemName) {	}
}

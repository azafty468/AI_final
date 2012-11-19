package aiModels;

import view.PrintListNode;
import gameObjects.GameObject;
import gameObjects.GameObjectCreature;
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
}

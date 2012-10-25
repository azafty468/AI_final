package actions;

/*
 * Generic class for any planned action
 */
public abstract class Action {
	private boolean isDone;
	
	public Action() {
		isDone = true;
	}
	
	public boolean getIsDone() { return isDone; }
	public void setIsDone(boolean newValue) { isDone = newValue; }
	public abstract void processAction();
}

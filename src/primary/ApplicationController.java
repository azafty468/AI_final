package primary;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import actions.ActionMove;
import primary.Point;

import view.ApplicationView;

/**
 * Negotiates all interactions between the User, View and Model
 * @author Andrew
 *
 */
public class ApplicationController {
	static Random generator = null;
	static ApplicationController thisController = null;
	private final Timer renderTimer;	// our time keeper
	private TimerTask renderTask; // the main render and update task.
	
	public ApplicationController() {
		renderTimer = new Timer();
	}
	
	public static Random getGenerator() {
		if (generator == null)
			generator = new Random();
		
		return generator;
	}
	
	public static ApplicationController getInstance() {
		if (thisController == null)
			thisController = new ApplicationController();
		
		return thisController;
	}
	
	public boolean initialize() {
		return true;
	}
	
	public void renderGraphics() {
		//Ask the model for a list of all objects that need to be drawn and pass it along to the View for display
		ApplicationView.getInstance().renderGraphics(ApplicationModel.getInstance().buildPrintList());
	}
	
	public void receiveKeyInput(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_NUMPAD1:
			case KeyEvent.VK_NUMPAD2:
			case KeyEvent.VK_NUMPAD3:
			case KeyEvent.VK_NUMPAD4:
			case KeyEvent.VK_NUMPAD6:
			case KeyEvent.VK_NUMPAD7:
			case KeyEvent.VK_NUMPAD8:
			case KeyEvent.VK_NUMPAD9:
				moveCursor(e);
				break;
		
			default: 
				String newMessage = new String("Error, unrecognized command: " + e.getKeyChar());
				ApplicationView.getInstance().displayMessage(newMessage);
				break;
		}
	}

	private void moveCursor(KeyEvent e) {
		ApplicationModel myModel = ApplicationModel.getInstance();
		Point myLocation = new Point(myModel.myPlayer.myLocation);
		
		if (e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			myLocation.x--;
			myLocation.y++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD3) {
			myLocation.x++;
			myLocation.y++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD9) {
			myLocation.x++;
			myLocation.y--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD7) {
			myLocation.x--;
			myLocation.y--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) {
			myLocation.x--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			myLocation.x++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) {
			myLocation.y--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			myLocation.y++;
		}
		
		ActionMove newAction = new ActionMove(myLocation, myModel.myPlayer);
		myModel.myPlayer.currentAction = newAction;
	}

	
	
	/*
	 * Actions are intended before they are carried out.  This step processes all intended actions
	 */
	public void processActions() {
		ApplicationModel myModel = ApplicationModel.getInstance();
		
		if (myModel.myPlayer.currentAction != null) {
			myModel.myPlayer.currentAction.processAction();
			
			if (myModel.myPlayer.currentAction.getIsDone()) 
				myModel.myPlayer.currentAction = null;
		}
		
	}
	
	/*
	 * This step plans future actions
	 */
	public void planNextMoves() {
		
	}
	
	public void startGraphicTimer() {
		if (renderTask != null) {
			renderTask.cancel();
		}

		renderTask = new TimerTask() {
			@Override
			public void run() {
				processActions();
				
				renderGraphics();
				
				/* to be used once we need a repetitive message pump
					//Process Moves
					myController.processActions();
					
					//Process UI Requests.  <--- This part depends largely on the underlying draw methods as it can be asynchronous
					
					//Plan next AI action
					myController.planNextMoves();
					
					//update screen <---- this also depends on the underlying graphics engine.  
					myController.setCurrentGraphics();
					myView.drawGraphics();
				*/
				
			}
		};
		renderTimer.schedule(renderTask, 0, 16);
	}

	/**
	 * Stops the rendering cycle so that the application can close gracefully.
	 */
	public void stop() {
		renderTask.cancel();
	}
}

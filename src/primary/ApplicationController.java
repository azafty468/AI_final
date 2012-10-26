package primary;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import actions.Action;
import actions.ActionMove;
import actions.Event;
import aiModels.AIModelDirectMove;
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
	public boolean gameOver;
	private final Timer renderTimer;	// our time keeper
	private TimerTask renderTask; // the main render and update task.
	public Stack<Event> currentEvents;
	
	public ApplicationController() {
		renderTimer = new Timer();
		currentEvents = new Stack<Event>();
		gameOver = false;
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
	
	public boolean initialize(boolean automatePlayer) {
		
		if (automatePlayer)
			ApplicationModel.getInstance().myPlayer.myAIModel = new AIModelDirectMove(ApplicationModel.getInstance().myPlayer);
		
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

	public void processAIPhase() {
		if (ApplicationModel.getInstance().myPlayer.currentAction == null) {
			ApplicationModel.getInstance().myPlayer.planNextMove();
		}
			
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
	
	public void processEvents() {
		while (!currentEvents.empty()) {
			currentEvents.pop().processEvent();
		}
		
		return;
	}
	
	public void startGraphicTimer() {
		if (renderTask != null) {
			renderTask.cancel();
		}

		renderTask = new TimerTask() {
			@Override
			public void run() {
				//TODO at some point we'll need to moderate the speed of this so that the game runs at a managed pace
				if (!gameOver) {
					processActions();
					processEvents();
					processAIPhase();
					renderGraphics();
				}
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

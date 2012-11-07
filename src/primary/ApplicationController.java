package primary;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import actions.ActionMove;
import actions.Event;
import aiModels.*;
import primary.GamePlayTimeKeeper.PlayRate;
import primary.Point;

import view.ApplicationView;

/**
 * Negotiates all interactions between the User, View and Model
 * @author Andrew and Trevor
 *
 */
public class ApplicationController {
	static Random generator = null;
	static ApplicationController thisController = null;
	private final Timer renderTimer;	// our time keeper
	private TimerTask renderTask; // the main render and update task.
	public Stack<Event> currentEvents;
	public GamePlayTimeKeeper myTimeKeeper;
	
	public ApplicationController() {
		renderTimer = new Timer();
		currentEvents = new Stack<Event>();
		myTimeKeeper = new GamePlayTimeKeeper(PlayRate.AIAUTOMATION);
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
		
		if (automatePlayer) {
			//ApplicationModel.getInstance().myPlayer.myAIModel = new AIModelDirectMove(ApplicationModel.getInstance().myPlayer);
			ApplicationModel.getInstance().myPlayer.myAIModel = new AIModelClosestMove(ApplicationModel.getInstance().myPlayer);
			myTimeKeeper = new GamePlayTimeKeeper(PlayRate.AIAUTOMATION);
		}
		else
			myTimeKeeper = new GamePlayTimeKeeper(PlayRate.HUMANPLAYER);
		
		ApplicationModel.getInstance().redGhost.myAIModel = new AIModelDijkstraAlgorithm(ApplicationModel.getInstance().redGhost);
		ApplicationModel.getInstance().blueGhost.myAIModel = new AIModelDirectMove(ApplicationModel.getInstance().blueGhost);
		
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
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_LEFT:
				moveCursor(e);
				break;
				
			case KeyEvent.VK_P:
				myTimeKeeper.invertPause();
				break;
				
			case KeyEvent.VK_ADD:
			case KeyEvent.VK_PLUS:
			case '+':
				myTimeKeeper.alterDelayBetweenTurns(20);
				break;
				
			case KeyEvent.VK_MINUS:
			case KeyEvent.VK_SUBTRACT:
				myTimeKeeper.alterDelayBetweenTurns(-20);
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
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4) { //left
			myLocation.x--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6) { //right
			myLocation.x++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8) { //up
			myLocation.y--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2) { //down
			myLocation.y++;
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP) {
			myLocation.y--;
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			myLocation.y++;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			myLocation.x++;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			myLocation.x--;
		}
		
		ActionMove newAction = new ActionMove(myLocation, myModel.myPlayer);
		myModel.myPlayer.currentAction = newAction;
	}

	public void processAIPhase() {
		if (ApplicationModel.getInstance().myPlayer.currentAction == null) {
			ApplicationModel.getInstance().myPlayer.planNextMove();
		}
		
		if (ApplicationModel.getInstance().redGhost.currentAction == null) {
			ApplicationModel.getInstance().redGhost.planNextMove();
		}
		
		if (ApplicationModel.getInstance().blueGhost.currentAction == null) {
			ApplicationModel.getInstance().blueGhost.planNextMove();
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
		
		if (myModel.redGhost.currentAction != null) {
			myModel.redGhost.currentAction.processAction();
			
			if (myModel.redGhost.currentAction.getIsDone()) 
				myModel.redGhost.currentAction = null;
		}
		
		if (myModel.blueGhost.currentAction != null) {
			myModel.blueGhost.currentAction.processAction();
			
			if (myModel.blueGhost.currentAction.getIsDone()) 
				myModel.blueGhost.currentAction = null;
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
				renderGraphics();
				if (myTimeKeeper.isTimeForTurn()) {
					processActions();
					processEvents();
					processAIPhase();
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

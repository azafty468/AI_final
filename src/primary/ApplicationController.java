package primary;

import gameObjects.GameObjectPlayer;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import actions.ActionMove;
import actions.Event;
import aiModels.*;
import primary.GamePlayTimeKeeper.PlayRate;
import primary.Point;

import view.ApplicationView;
import xml.Message;

/**
 * Negotiates all interactions between the User, View and Model
 * @author Andrew and Trevor
 *
 */
public class ApplicationController {
	static Random generator = null;
	static ApplicationController thisController = null;
	private Timer renderTimer;	// our time keeper
	private TimerTask renderTask; // the main render and update task.
	public Stack<Event> currentEvents;
	private GamePlayTimeKeeper myTimeKeeper;
	private String logFile;
	public ArrayList<String> loggedEvents;
	private boolean automatePlayer;
	public boolean advancedViewSetting;
	public boolean advancedViewPolicySetting;
	
	public ApplicationController() {
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
	
	private boolean loadFromXMLFile(String xmlFile) {
		if (!Message.configure("logs" + Constants.fileDelimiter + "Board.xsd")) { 
			System.err.println("Error, cannot load Board XSD file");
			System.exit(-1);
		}
		
		Message inMessage = null;
		
		try {
			String inFile = "";
			BufferedReader inBR = new BufferedReader(new FileReader("logs" + Constants.fileDelimiter + xmlFile));

			String inSegment = inBR.readLine();
			while (inSegment != null) {
				inFile += inSegment;
				inSegment = inBR.readLine();
			}
			inBR.close();
	
			inMessage = new Message(inFile);
		} catch (Exception e) {
			System.err.println("Error while reading in XML file");
			return false;
		}
		
		if (inMessage.contents.getAttributes().getNamedItem("automatePlayer").getNodeValue().equals("true"))
			automatePlayer = true;
		else
			automatePlayer = false;
	
		return ApplicationModel.getInstance().initialize(inMessage.contents.getFirstChild());
	}
	
	public boolean initialize(boolean automatePlayer, String loadFile) {
		loggedEvents = new ArrayList<String>();
		if (loadFile == null) {
			this.automatePlayer = automatePlayer;
			AIModel playerAI;
			AIModel redAI;
			AIModel blueAI;
			int boardWidth = (int) (getScreenWorkingWidth() * 0.8 / Constants.baseImageSize);
			int boardHeight = (int) (getScreenWorkingHeight() * 0.8 / Constants.baseImageSize);
			
			if (automatePlayer)
				playerAI = new AIModelBasicUtility();
				//playerAI = new AIModelClosestMove();
			else
				playerAI = new AIModelPlayer();
			
			redAI = new AIModelDijkstraAlgorithm();
			blueAI = new AIModelDirectMove();
			
			if(!ApplicationModel.getInstance().initialize(boardWidth, boardHeight, playerAI, redAI, blueAI)) {
				JOptionPane.showMessageDialog(null, "Error while initializating the base Application Model.  Exiting.");
				System.exit(0);		
			}
			writeInitialLog();
		}
		else {
			logFile = loadFile;
			if (!loadFromXMLFile(loadFile))
				return false;
		}
		
		if (this.automatePlayer)
			myTimeKeeper = new GamePlayTimeKeeper(PlayRate.AIAUTOMATION);
		else
			myTimeKeeper = new GamePlayTimeKeeper(PlayRate.HUMANPLAYER);

		renderTimer = new Timer();
		ApplicationView.getInstance().displayMessage("Starting Game.  Current State - PAUSED");

		advancedViewSetting = false;
		advancedViewPolicySetting = false;
		return true;
	}
	
	public void renderGraphics() {
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
				
			case KeyEvent.VK_SPACE:
				myTimeKeeper.stepOneRound = true;
				ApplicationView.getInstance().displayMessage("---Stepping forward one round---");
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
				
			case KeyEvent.VK_H:
				String tmpStr = "(H) help, (P) pause, (V) view detailed AI, (B) more detailed AI, (+) increase game speed" + 
						" (-) decrease game speed, (space) step forward one round, (arrow keys) move character, " +
						"(A) abort game with unspecified error, (S) abort game due to unwinnable, (D) abort game due to error in game ";
				ApplicationView.getInstance().displayMessage(tmpStr);
				break;
				
			case KeyEvent.VK_R:
				myTimeKeeper.setPause(true);
				renderTimer.cancel();
				//stop();
				//renderTask = null;
				//renderTimer = null;
				ApplicationModel.getInstance().resetModel();
				ApplicationView.getInstance().displayMessage("---Game Reset Command Received---");
				if (!initialize(automatePlayer, logFile)) {
					System.err.println("Error while reseting environment");
					System.exit(-1);
				}
				break;
				
			case KeyEvent.VK_A:
				ApplicationView.getInstance().displayMessage("Aborting game for undefined reason");
				finishGame("unknown reason");
				break;
			
			case KeyEvent.VK_S:
				finishGame("unwinnable");
				ApplicationView.getInstance().displayMessage("Aborting game because it is deemed unwinnable");
				break;
				
			case KeyEvent.VK_D:
				ApplicationView.getInstance().displayMessage("Aborting game due to an error in the game");
				finishGame("error in game");
				break;
				
			case KeyEvent.VK_V:
				myTimeKeeper.setPause(true);
				ApplicationView.getInstance().displayMessage("Rotating Detailed AI View");
				advancedViewSetting = !advancedViewSetting;
				advancedViewPolicySetting = false;
				break;
				
			case KeyEvent.VK_B:
				myTimeKeeper.setPause(true);
				ApplicationView.getInstance().displayMessage("Rotating Policy AI View");
				advancedViewPolicySetting = !advancedViewPolicySetting;
				advancedViewSetting = false;
				break;
		
			default: 
				String newMessage = new String("Error, unrecognized command: " + e.getKeyChar());
				ApplicationView.getInstance().displayMessage(newMessage);
				break;
		}
	}
	
	public void writeInitialLog() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		Date date = new Date();
		logFile = "Board_" + dateFormat.format(date) + ".xml";
		
		try {
			BufferedWriter myOut = new BufferedWriter(new FileWriter("logs" + Constants.fileDelimiter + logFile));
			myOut.write("<Game automatePlayer='" + automatePlayer + "'>" + Constants.newline);
			ApplicationModel.getInstance().writeToXMLFile(myOut);
			myOut.write("</Game>");
			myOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error while writing to log file: " + logFile);
			System.exit(-1);
		}
	}
	
	public void finishGame(String terminateReason) {
		myTimeKeeper.setGameOver();

		String runLog = logFile.replace(".xml", "") + "_Run_";
		
		int counter = 0;
	    final File folder = new File("logs" + Constants.fileDelimiter);
	    for (final File fileEntry : folder.listFiles())
	        if (!(fileEntry.isDirectory()))
	        	if (fileEntry.getName().contains(runLog))
	        		counter++;
		
	    counter++;
		runLog += counter + ".xml";
		GameObjectPlayer myPlayer = ApplicationModel.getInstance().myPlayer;
		try {
			BufferedWriter myOut = new BufferedWriter(new FileWriter("logs" + Constants.fileDelimiter + runLog));
			myOut.write("<GameRun Board='" + logFile + "' run='" + counter + "' ");
			if (terminateReason.isEmpty())
				myOut.write("finish='success' ");
			else
				myOut.write("finish='abort' reason='" + terminateReason + "' ");

			myOut.write("availableBerries='" + ApplicationModel.getInstance().myBoard.startingBerries + "' " +
				"finalScore='" + myPlayer.pointsGained + "' " +
				"ghostTouches='" + myPlayer.touchedByGhost  + "' " +
				"stepsTaken='" + myPlayer.stepsTaken  + "'>" + Constants.newline);
			
			for (int i = 0; i < loggedEvents.size(); i++)
				myOut.write(loggedEvents.get(i) + Constants.newline);
			myOut.write("</GameRun>");
			myOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error while writing to log file: " + logFile);
			System.exit(-1);
		}
		loggedEvents.clear();
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
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_LEFT) { //left
			myLocation.x--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_RIGHT) { //right
			myLocation.x++;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_UP) { //up
			myLocation.y--;
		}
		else if (e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_DOWN) { //down
			myLocation.y++;
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

	
	public static int getScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int getScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
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

package primary;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Negotiates all interactions between the User, View and Model
 * @author Andrew
 *
 */
public class ApplicationController {
	ApplicationModel myModel;
	ApplicationView myView;
	private final Timer renderTimer;	// our time keeper
	private TimerTask renderTask; // the main render and update task.
	
	public ApplicationController(ApplicationModel newModel, ApplicationView newView) {
		myModel = newModel;
		myView = newView;
		renderTimer = new Timer();
		myView.setController(this);
	}
	
	public boolean initialize() {
		return true;
	}
	
	public void renderGraphics() {
		//Ask the model for a list of all objects that need to be drawn and pass it along to the View for display
		myView.renderGraphics(myModel.buildPrintList());
	}

	
	public void receiveKeyInput(KeyEvent e) {
		switch (e.getKeyCode()) {
		default: 
			String newMessage = new String("Error, unrecognized command: " + e.getKeyChar());
			myView.displayMessage(newMessage);
			break;
		}
	}

	
	/*
	 * Actions are intended before they are carried out.  This step processes all intended actions
	 */
	public void processActions() {
		
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

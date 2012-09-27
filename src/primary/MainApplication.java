package primary;

import javax.swing.JOptionPane;

public class MainApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int boardWidth = 15;
		int boardHeight = 10;
		
		ApplicationView myView = new ApplicationView();
		if (!myView.initializeScreen()) {
			JOptionPane.showMessageDialog(null, "Error while initialization the base Application View.  Exiting.");
			System.exit(0);
		}
		
		ApplicationModel myModel = new ApplicationModel();
		if(!myModel.initialize(boardWidth, boardHeight)) {
			JOptionPane.showMessageDialog(null, "Error while initialization the base Application Model.  Exiting.");
			System.exit(0);		
		}
		
		ApplicationController myController = new ApplicationController(myModel, myView);
		if(!myController.initialize()) {
			JOptionPane.showMessageDialog(null, "Error while initialization the base Application Controller.  Exiting.");
			System.exit(0);				
		}
		
		myController.setCurrentGraphics();
		myView.drawGraphics();
		
		/* to be used once we need a repetitive message pump
		boolean done = false;
		done = true;
		while (!done) {
			//Process Moves
			myController.processActions();
			
			//Process UI Requests.  <--- This part depends largely on the underlying draw methods as it can be asynchronous
			
			//Plan next AI action
			myController.planNextMoves();
			
			//update screen <---- this also depends on the underlying graphics engine.  
			myController.setCurrentGraphics();
			myView.drawGraphics();
		}
		*/
	}

}

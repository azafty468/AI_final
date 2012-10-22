package primary;

import javax.swing.JOptionPane;

public class MainApplication {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int boardWidth = getScreenWorkingWidth();
		int boardHeight = getScreenWorkingHeight();
		
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

		myController.startGraphicTimer();
		myView.displayMessage(new String("Load complete"));
	}
	
	public static int getScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int getScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}
}

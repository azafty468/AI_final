package primary;

import javax.swing.JOptionPane;

import view.ApplicationView;
import view.ShowStartupScreen;

public class MainApplication {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		if(!ShowStartupScreen.getInstance().initializeScreen()) {
			JOptionPane.showMessageDialog(null, "Error while initializating the Startup View.  Exiting.");
			System.exit(0);
		}
	}
	
	public static void startGame() {
		int boardWidth = getScreenWorkingWidth();
		int boardHeight = getScreenWorkingHeight();
		
		if (!ApplicationView.getInstance().initializeScreen()) {
			JOptionPane.showMessageDialog(null, "Error while initializating the base Application View.  Exiting.");
			System.exit(0);
		}
		
		if(!ApplicationModel.getInstance().initialize(boardWidth, boardHeight)) {
			JOptionPane.showMessageDialog(null, "Error while initializating the base Application Model.  Exiting.");
			System.exit(0);		
		}
		
		if(!ApplicationController.getInstance().initialize(true)) {
			JOptionPane.showMessageDialog(null, "Error while initializating the base Application Controller.  Exiting.");
			System.exit(0);				
		}

		ApplicationController.getInstance().startGraphicTimer();
		ApplicationView.getInstance().displayMessage(new String("Load complete"));
	}
	
	public static int getScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int getScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}
}

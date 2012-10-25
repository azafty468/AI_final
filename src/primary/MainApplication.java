package primary;

import javax.swing.JOptionPane;

import view.ApplicationView;

public class MainApplication {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int boardWidth = getScreenWorkingWidth();
		int boardHeight = getScreenWorkingHeight();
		
		if (!ApplicationView.getInstance().initializeScreen()) {
			JOptionPane.showMessageDialog(null, "Error while initialization the base Application View.  Exiting.");
			System.exit(0);
		}
		
		if(!ApplicationModel.getInstance().initialize(boardWidth, boardHeight)) {
			JOptionPane.showMessageDialog(null, "Error while initialization the base Application Model.  Exiting.");
			System.exit(0);		
		}
		
		if(!ApplicationController.getInstance().initialize()) {
			JOptionPane.showMessageDialog(null, "Error while initialization the base Application Controller.  Exiting.");
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

package primary;

import javax.swing.JOptionPane;

import view.ApplicationView;
import view.ShowStartupScreen;

import aiModels.*;

public class MainApplication {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ShowStartupScreen start = new ShowStartupScreen();
		start.setVisible(true);
	}
	
	public static void startGame(boolean isHumanControlled) {
		
		if (!ApplicationView.getInstance().initializeScreen()) {
			JOptionPane.showMessageDialog(null, "Error while initializating the base Application View.  Exiting.");
			System.exit(0);
		}
		
		if(!ApplicationController.getInstance().initialize(isHumanControlled, "")) {
			JOptionPane.showMessageDialog(null, "Error while initializating the base Application Controller.  Exiting.");
			System.exit(0);				
		}
		
		ApplicationController.getInstance().startGraphicTimer();
		ApplicationView.getInstance().displayMessage(new String("Load complete"));
	}
}

package primary;

import java.awt.image.BufferedImage;


/**
 * Negotiates all interactions between the User, View and Model
 * @author Andrew
 *
 */
public class ApplicationController {
	ApplicationModel myModel;
	ApplicationView myView;
	
	public ApplicationController(ApplicationModel newModel, ApplicationView newView) {
		myModel = newModel;
		myView = newView;
	}
	
	public boolean initialize() {
		return true;
	}
	
	public void setCurrentGraphics() {
		//Ask the model for a list of all objects that need to be drawn
		BufferedImage[][] myPrintList = myModel.buildPrintList();

		//pass it along to the View for display
		myView.setDisplayGraphics(myPrintList);
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
}

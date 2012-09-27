package primary;

import java.awt.image.BufferedImage;

import gameObjects.Board;

/**
 * Controls all aspects of the Model objects
 * @author Andrew
 *
 */
public class ApplicationModel {
	private Board myBoard;
	
	public boolean initialize(int width, int height) {
		myBoard = new Board(width, height);
		
		return true;
	}
	
	public BufferedImage[][] buildPrintList() {
		BufferedImage[][] printList = new BufferedImage[myBoard.height][myBoard.width];
		
		for (int y = 0; y < myBoard.height; y++) {
			for (int x = 0; x < myBoard.width; x++) {
				//Does a GameObject Creature exist at this spot?  No, then assign it the Background at that location.
				
				//for now just assign it the background object
				printList[y][x] = myBoard.myGO[y][x].getGraphics();
			}
		}
		
		return printList;
	}
}

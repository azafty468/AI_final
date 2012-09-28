package primary;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*
 * Manages the drawing of the graphics screen
 */
public class GraphicsCanvas extends Canvas {
	BufferedImage[][] myPrintList;

	/**
	 * Defaulted required from parent class
	 */
	private static final long serialVersionUID = 1L;
	
	public void setPrintList(BufferedImage[][] newPL) {
		myPrintList = newPL;
	}
	
	public void paint(Graphics g) {
		if (myPrintList == null)
			return;
		for (int y = 0; y < myPrintList.length; y++) {
			for (int x = 0; x < myPrintList[y].length; x++) {
				if (myPrintList[y][x] != null) {
					g.drawImage(myPrintList[y][x],  x*32, y*32, null); //ImageObserver
				}
			}
		}
	}
}

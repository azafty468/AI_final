package primary;

import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Manages all aspects of the View
 * @author Andrew
 *
 */
public class ApplicationView {
	private JFrame masterFrame;
	private GraphicsCanvas myGraphicCanvas;
	
	public boolean initializeScreen() {
		masterFrame = new JFrame("Test View");
		masterFrame.setSize(800, 600);
		masterFrame.setLayout(null);
	    masterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    masterFrame.setVisible(true);
	    
	    Insets insets = masterFrame.getInsets();
	    myGraphicCanvas = new GraphicsCanvas(); 
	    myGraphicCanvas.setBounds(insets.left + 5, insets.top + 5, 800-3*(insets.left + 5), 400-2*(insets.top + 5));
	    myGraphicCanvas.setVisible(true);
	    masterFrame.add(myGraphicCanvas);
	    
	    return true;
	}
	
	/**
	 * Graphics are defined outside of the drawing cycle.  Instead, the drawing application just writes whatever the current graphic list is at the time
	 *   of calling.
	 * @param printList
	 */
	public void setDisplayGraphics(BufferedImage[][] printList) {
		myGraphicCanvas.setPrintList(printList);
	}
	
	/**
	 * Actually prompts the graphics canvas to draw itself
	 */
	public void drawGraphics() {
		myGraphicCanvas.repaint();
	}
}

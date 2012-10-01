package primary;

import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * Manages all aspects of the View
 * @author Andrew
 *
 */
public class ApplicationView extends JFrame implements KeyListener, WindowListener {
	private static final long serialVersionUID = 1L;
	private GraphicsCanvas myGraphicCanvas;
	private CommandOutJPanel myOutput;
	private ApplicationController myController;
	
	public boolean initializeScreen() {
		addKeyListener(this);
		addWindowListener(this);
		
		setSize(800, 600);
		setLayout(null);
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    Insets insets = getInsets();
	    myGraphicCanvas = new GraphicsCanvas(0, 0, 800-3*(insets.left + 5), 400-2*(insets.top + 5));
	    myGraphicCanvas.setVisible(true);
	    add(myGraphicCanvas);

	    myOutput = new CommandOutJPanel(0, 375, 800-3*(insets.left + 5), 150);
	    add(myOutput);
	    myOutput.revalidate();

	    repaint();
	    myGraphicCanvas.initialize();
	    
	    return true;
	}
	
	public void setController(ApplicationController newController) {
		myController = newController;
	}

	public void displayMessage(String newMessage){
		myOutput.displayMessage(newMessage);
	}
	
	/**
	 * Graphics are defined outside of the drawing cycle.  Instead, the drawing application just writes whatever the current graphic list is at the time
	 *   of calling.
	 * @param printList
	 */
	public void renderGraphics(BufferedImage[][] printList) {
		myGraphicCanvas.render(printList);
	}

	public void keyPressed(KeyEvent arg0) {
		myController.receiveKeyInput(arg0);
	}

	public void windowClosing(WindowEvent arg0) {
		if (myController != null)
			myController.stop();
		System.exit(0);
	}

	
	public void windowActivated(WindowEvent arg0) { ; }
	public void windowClosed(WindowEvent arg0) { ; }
	public void windowDeactivated(WindowEvent arg0) { ; }
	public void windowDeiconified(WindowEvent arg0) { ; }
	public void windowIconified(WindowEvent arg0) { ; }
	public void windowOpened(WindowEvent arg0) { ; }
	public void keyReleased(KeyEvent arg0) { ; }
	public void keyTyped(KeyEvent arg0) { ; }
}

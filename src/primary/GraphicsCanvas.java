package primary;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/*
 * Manages the drawing of the graphics screen
 */
public class GraphicsCanvas extends Canvas {
	private static final long serialVersionUID = 1L; //required by parent
	private int height;
	private int width;
	private BufferStrategy myBufferStrategy;
	private Paint backgroundGradient; // Used to paint over everything between renders
	
	public GraphicsCanvas(int newLeft, int newTop, int newWidth, int newHeight) {
		super();
		height = newHeight;
		width = newWidth;
		setBounds(newLeft, newTop, newWidth, newHeight);
	}

	public void initialize() {
		backgroundGradient = new GradientPaint(0, 0, Color.gray, getWidth(), getHeight(), Color.lightGray.brighter());
		this.createBufferStrategy(2);
		myBufferStrategy = this.getBufferStrategy();
	}

	public void render(BufferedImage[][] printList) {
		if (printList == null)
			return;
		
		Graphics2D bkG = (Graphics2D) myBufferStrategy.getDrawGraphics();

		bkG.setPaint(backgroundGradient);
		bkG.fillRect(0, 0, getWidth(), getHeight());

		if (printList != null) {
			for (int y = 0; (y < printList.length) && (y*32 < height); y++) {
				for (int x = 0; (x < printList[y].length) && (x*32 < width); x++) {
					if (printList[y][x] != null) {
						bkG.drawImage(printList[y][x],  x*32, y*32, null); //ImageObserver
					}
				}
			}
		}
		bkG.dispose();
		myBufferStrategy.show();
		Toolkit.getDefaultToolkit().sync();
	}

}

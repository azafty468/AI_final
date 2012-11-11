package view;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import primary.Constants;


/*
 * Manages the drawing of the graphics screen
 */
public class GraphicsCanvas extends Canvas {
	private static final long serialVersionUID = 1L; //required by parent
	private int height;
	private int width;
	private BufferStrategy myBufferStrategy;
	
	public GraphicsCanvas(int newLeft, int newTop, int newWidth, int newHeight) {
		super();
		height = newHeight;
		width = newWidth;
		setBounds(newLeft, newTop, newWidth, newHeight);
	}

	public void initialize() {
		this.createBufferStrategy(2);
		myBufferStrategy = this.getBufferStrategy();
	}

	public void render(PrintListNode[][] printList) {
		if (printList == null)
			return;
		
		Graphics2D bkG = (Graphics2D) myBufferStrategy.getDrawGraphics();
		bkG.setFont(new Font( "SansSerif", Font.BOLD, 9));

		bkG.setPaint(bkG.getBackground());
		bkG.fillRect(0, 0, getWidth(), getHeight());

		if (printList != null) {
			for (int y = 0; (y < printList.length) && (y*Constants.baseImageSize < height); y++) {
				for (int x = 0; (x < printList[y].length) && (x*Constants.baseImageSize < width); x++) {
					if (printList[y][x] != null) {
						if (printList[y][x].overrideColor){
							bkG.setXORMode(printList[y][x].baseColor);
							bkG.drawImage(printList[y][x].myImage,  x*Constants.baseImageSize, y*Constants.baseImageSize, null);
							bkG.setPaintMode();
						}
						else
							bkG.drawImage(printList[y][x].myImage,  x*Constants.baseImageSize, y*Constants.baseImageSize, null);
						
						if (printList[y][x].hasUtilityValue)
							bkG.drawString(String.valueOf(printList[y][x].utilityValue), x*Constants.baseImageSize+12, y*Constants.baseImageSize+24);
					}
				}
			}
		}
		bkG.dispose();
		myBufferStrategy.show();
		Toolkit.getDefaultToolkit().sync();
	}

}

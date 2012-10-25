package gameObjects;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import primary.ApplicationController;
import view.ApplicationView;

/**
 * Holds all the GameObject Backgrounds that make up the game board.
 * @author Andrew
 *
 */
public class Board {
	public int width, height;
	public GameObjectBackground[][] myGO;
	
	public Board(int newWidth, int newHeight) {
		height = newHeight;
		width = newWidth;
		
		if (height < 3)
			height = 3;
		if (height > 25)
			height = 25;
		
		if (width < 3) 
			width = 3;
		if (width > 40)
			width = 40;
		myGO = new GameObjectBackground[height][width];
		
		GameObjectBackground boundaryWall = new GameObjectBackground();
		GameObjectBackground boundaryOpen = new GameObjectBackground();
		try {
			BufferedImage imgBlocked = ImageIO.read(new File("images\\TestObstruction.bmp"));
			boundaryWall.setGraphics(ApplicationView.convertImageToLocalSettings(imgBlocked));
			
			BufferedImage imgOpen = ImageIO.read(new File("images\\BackgroundEmpty.bmp"));
			boundaryOpen.setGraphics(ApplicationView.convertImageToLocalSettings(imgOpen));
			boundaryOpen.canBlockMovement = false;
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while loading base graphics with exception: " + e.getMessage());
		}
		
		GameObjectBackground cursor;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((x == 0) || (x == (width - 1)) || (y == 0) || (y == (height - 1))) {
					cursor = (GameObjectBackground) boundaryWall.generateClone(null); 
				}
				else {
					cursor = (GameObjectBackground) boundaryOpen.generateClone(null); 
				}
				cursor.setXY(x,  y);
				myGO[y][x] = cursor;
			}
		}
	}
}

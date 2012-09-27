package gameObjects;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

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
		if (height > 40)
			height = 40;
		
		if (width < 3) 
			width = 3;
		if (width > 40)
			width = 40;
		myGO = new GameObjectBackground[height][width];
		
		GameObjectBackground boundaryWall = new GameObjectBackground();
		GameObjectBackground boundaryOpen = new GameObjectBackground();
		try {
			BufferedImage imgBlocked = ImageIO.read(new File("images\\TestObstruction.bmp"));
			boundaryWall.setGraphics(imgBlocked);
			
			BufferedImage imgOpen = ImageIO.read(new File("images\\BackgroundEmpty.bmp"));
			boundaryOpen.setGraphics(imgOpen);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error while loading base graphics with exception: " + e.getMessage());
		}
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if ((x == 0) || (x == (width - 1)) || (y == 0) || (y == (height - 1))) {
					myGO[y][x] = boundaryWall; //normally I would use Clone here, for now these are all pointing to the same obstruction
				}
				else {
					myGO[y][x] = boundaryOpen;
				}
					
			}
		}
	}
}

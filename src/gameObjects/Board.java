package gameObjects;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
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
			boundaryWall.setGraphics(convertImageToLocalSettings(imgBlocked));
			
			BufferedImage imgOpen = ImageIO.read(new File("images\\BackgroundEmpty.bmp"));
			boundaryOpen.setGraphics(convertImageToLocalSettings(imgOpen));
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

	private BufferedImage convertImageToLocalSettings(BufferedImage input) {
		GraphicsConfiguration gfx_config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration(); 

		// if image is already compatible and optimized for current system settings, simply return it 
		if (input.getColorModel().equals(gfx_config.getColorModel())) 
			return input; 
		
		// image is not optimized, so create a new image that is 
		BufferedImage new_image = gfx_config.createCompatibleImage(input.getWidth(), input.getHeight(), input.getTransparency()); 

		// get the graphics context of the new image to draw the old image on 
		Graphics2D g2d = (Graphics2D) new_image.getGraphics(); 

		// actually draw the image and dispose of context no longer needed 
		g2d.drawImage(input, 0, 0, null); 
		g2d.dispose(); 

		// return the new optimized image 
		return new_image; 
	}
}

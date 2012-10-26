package gameObjects;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

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
	public ArrayList<GameObjectToken> myTokens;
	private GameObjectToken templateStrawberryToken;
	
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
		
		myTokens = new ArrayList<GameObjectToken>();
		
		GameObjectBackground boundaryWall = new GameObjectBackground();
		GameObjectBackground boundaryOpen = new GameObjectBackground();
		templateStrawberryToken = new GameObjectToken();
		templateStrawberryToken.name = "Strawberry";
		templateStrawberryToken.pointValue = 100;
		templateStrawberryToken.canBlockMovement = false;
		try {
			BufferedImage imgTemp = ImageIO.read(new File("images\\TestObstruction.bmp"));
			boundaryWall.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			
			imgTemp = ImageIO.read(new File("images\\BackgroundEmpty.bmp"));
			boundaryOpen.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			boundaryOpen.canBlockMovement = false;
			
			imgTemp = ImageIO.read(new File("images\\strawberry.bmp"));
			templateStrawberryToken.setGraphics(imgTemp);
			
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
		
		int totalBerries =  ApplicationController.getGenerator().nextInt(15)+15;
		for (int a = 0; a < totalBerries; a++) {
			GameObjectToken tempTok = (GameObjectToken) templateStrawberryToken.generateClone(null);
			tempTok.setXY(ApplicationController.getGenerator().nextInt(width-2)+1, ApplicationController.getGenerator().nextInt(height-2)+1);
			myTokens.add(tempTok);
		}
	}
	
	public boolean removeToken(GameObjectToken removeTok) {
		for (int i = 0; i < myTokens.size(); i++) {
			if (myTokens.get(i).myLocation.equals(removeTok.myLocation)) {
				myTokens.remove(i);
				return true;
			}
		}
		
		return false;
	}
}

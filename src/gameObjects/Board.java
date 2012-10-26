package gameObjects;

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
	private GameObjectBackground templateBackgroundPit; 
	private GameObjectBackground templateBoundaryWall;
	private GameObjectBackground templateBoundaryOpen;
	
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
		
		templateBoundaryWall = new GameObjectBackground();
		templateBoundaryOpen = new GameObjectBackground();
		
		templateBackgroundPit = new GameObjectBackground();
		templateBackgroundPit.name = "Pit";
		templateBackgroundPit.canBlockMovement = false;
		
		templateStrawberryToken = new GameObjectToken();
		templateStrawberryToken.name = "Strawberry";
		templateStrawberryToken.pointValue = 100;
		templateStrawberryToken.canBlockMovement = false;
		try {
			BufferedImage imgTemp = ImageIO.read(new File("images\\TestObstruction.bmp"));
			templateBoundaryWall.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			
			imgTemp = ImageIO.read(new File("images\\BackgroundEmpty.bmp"));
			templateBoundaryOpen.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			templateBoundaryOpen.canBlockMovement = false;
			
			imgTemp = ImageIO.read(new File("images\\pit.bmp"));
			templateBackgroundPit.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			templateBackgroundPit.canBlockMovement = false;
			
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
					cursor = (GameObjectBackground) templateBoundaryWall.generateClone(null); 
				}
				else {
					cursor = (GameObjectBackground) templateBoundaryOpen.generateClone(null); 
				}
				cursor.setXY(x,  y);
				myGO[y][x] = cursor;
			}
		}
		
		int totalBerries = ApplicationController.getGenerator().nextInt(15)+15;
		int counter = 0;
		while (counter < totalBerries) {
			boolean safeToAdd = true;
			GameObjectToken tempTok = (GameObjectToken) templateStrawberryToken.generateClone(null);
			tempTok.setXY(ApplicationController.getGenerator().nextInt(width-2)+1, ApplicationController.getGenerator().nextInt(height-2)+1);
			
			for (int i = 0; i < myTokens.size(); i++) 
				if (myTokens.get(i).myLocation.equals(tempTok.myLocation))
					safeToAdd = false;
			
			if (safeToAdd) {
				myTokens.add(tempTok);
				counter++;
			}
		}
		
		int totalPits = ApplicationController.getGenerator().nextInt(15)+15;
		counter = 0;
		while (counter < totalPits) {
			GameObjectBackground tempBack = (GameObjectBackground) templateBackgroundPit.generateClone(null);
			tempBack.setXY(ApplicationController.getGenerator().nextInt(width-2)+1, ApplicationController.getGenerator().nextInt(height-2)+1);

			if (!(myGO[tempBack.myLocation.y][tempBack.myLocation.x].name.equals("Pit"))) {
				myGO[tempBack.myLocation.y][tempBack.myLocation.x] = tempBack;
				counter = counter + 1;
			}
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

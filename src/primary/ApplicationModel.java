package primary;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import view.ApplicationView;
import view.PrintListNode;

import gameObjects.Board;
import gameObjects.GameObject;
import gameObjects.GameObjectEnemy;
import gameObjects.GameObjectPlayer;
import gameObjects.GameObjectToken;

/**
 * Controls all aspects of the Model objects
 * @author Andrew
 *
 */
public class ApplicationModel {
	public Board myBoard;
	public GameObjectPlayer myPlayer;
	public GameObjectEnemy redGhost;
	static ApplicationModel thisModel = null;
	
	public boolean initialize(int width, int height) {
		myBoard = new Board(width, height);
		myPlayer = new GameObjectPlayer();
		myPlayer.setXY(5,  5);
		redGhost = new GameObjectEnemy();
		redGhost.setXY(10, 10);
		
		try {
			BufferedImage imgBasePlayer = ImageIO.read(new File("images\\Pacman.bmp"));
			myPlayer.setGraphics(ApplicationView.convertImageToLocalSettings(imgBasePlayer));
			myPlayer.overrideColor = true;
			myPlayer.baseColor = new Color(ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255));
			myPlayer.name = "Pac-man";
			
			BufferedImage imgBaseRedGhost = ImageIO.read(new File("images\\RedGhost.bmp"));
			redGhost.setGraphics(ApplicationView.convertImageToLocalSettings(imgBaseRedGhost));
			redGhost.overrideColor = true;
			redGhost.baseColor = new Color(ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255));
			redGhost.name = "Red-Ghost";
		}
		catch (Exception e) {
			System.out.println("Error while creating a character");
			return false;
		}
				
		return true;
	}
	
	public static ApplicationModel getInstance() {
		if (thisModel == null)
			thisModel = new ApplicationModel();
		
		return thisModel;
	}
	
	public PrintListNode[][] buildPrintList() {
		int height = myBoard.height;
		int width = myBoard.width;

		PrintListNode[][] printList = new PrintListNode[height][width];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if ((x == myPlayer.myLocation.x) && (y == myPlayer.myLocation.y))
					printList[y][x] = myPlayer.generateDisplayNode();
				else {
					printList[y][x] = myBoard.myGO[y][x].generateDisplayNode();

					ArrayList<GameObjectToken> tempList = myBoard.myTokens;
					for (int i = 0; i < tempList.size(); i++) {
						if (tempList.get(i).myLocation.equals(new Point(x, y)))
							printList[y][x] = tempList.get(i).generateDisplayNode();
					}
				}
			}
		}

		return printList;
	}
	
	public GameObject findGOByLocation(Point targetLocation) {
		if ((targetLocation.x >= 0) && (targetLocation.x < myBoard.width) && (targetLocation.y >= 0) && (targetLocation.y < myBoard.height)) {
			if (myPlayer.myLocation.x == targetLocation.x && myPlayer.myLocation.y == targetLocation.y)
				return myPlayer;
			else {
				ArrayList<GameObjectToken> tempList = myBoard.myTokens;
				for (int i = 0; i < tempList.size(); i++) {
					if (tempList.get(i).myLocation.equals(targetLocation))
						return tempList.get(i);
				}
				
				return myBoard.myGO[targetLocation.y][targetLocation.x];
			}
		}
		
		return null;
	}
}

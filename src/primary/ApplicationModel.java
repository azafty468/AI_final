package primary;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import view.ApplicationView;
import view.PrintListNode;

import gameObjects.Board;
import gameObjects.GameObject;
import gameObjects.GameObjectCreature;
import gameObjects.GameObjectPlayer;

/**
 * Controls all aspects of the Model objects
 * @author Andrew
 *
 */
public class ApplicationModel {
	private Board myBoard;
	public GameObjectPlayer myPlayer;
	static ApplicationModel thisModel = null;
	
	public boolean initialize(int width, int height) {
		myBoard = new Board(width, height);
		myPlayer = new GameObjectPlayer();
		myPlayer.setXY(5,  5);
		
		try {
			BufferedImage imgBasePlayer = ImageIO.read(new File("images\\Pacman.bmp"));
			myPlayer.setGraphics(ApplicationView.convertImageToLocalSettings(imgBasePlayer));
			myPlayer.overrideColor = true;
			myPlayer.baseColor = new Color(ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255));
			myPlayer.name = "Pac-man";
		}
		catch (Exception e) {
			System.out.println("Error while creating the base player");
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
				else 
					printList[y][x] = myBoard.myGO[y][x].generateDisplayNode();
			}
		}

		return printList;
	}
	
	public GameObject findGOByLocation(Point targetLocation) {
		if ((targetLocation.x >= 0) && (targetLocation.x < myBoard.width) && (targetLocation.y >= 0) && (targetLocation.y < myBoard.height)) {
			//go through each player...currently only one
			if (myPlayer.myLocation.x == targetLocation.x && myPlayer.myLocation.y == targetLocation.y)
				return myPlayer;
			else
				return myBoard.myGO[targetLocation.y][targetLocation.x];
		}
		
		return null;
	}
}

package primary;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import aiModels.AIModel;

import view.ApplicationView;
import view.PrintListNode;

import gameObjects.*;
import gameObjects.GameObjectCreature.CreatureAlliance;

/**
 * Controls all aspects of the Model objects
 * @author Andrew
 *
 */
public class ApplicationModel {
	public Board myBoard;
	public GameObjectPlayer myPlayer;
	public GameObjectCreature redGhost;
	public GameObjectCreature blueGhost;
	static ApplicationModel thisModel = null;
	
	public boolean initialize(int width, int height, AIModel playerAI, AIModel redAI, AIModel blueAI) {
		myBoard = new Board(width, height);
		myPlayer = new GameObjectPlayer();
		myPlayer.setXY(5, 5);
		myPlayer.myAlliance = CreatureAlliance.PLAYER;
		playerAI.assignToCreature(myPlayer);
		
		redGhost = new GameObjectCreature();
		redGhost.setXY(10, 10);
		redGhost.myAlliance = CreatureAlliance.GHOST;
		redAI.assignToCreature(redGhost);
		
		blueGhost = new GameObjectCreature();
		blueGhost.setXY(15, 15);
		blueGhost.myAlliance = CreatureAlliance.GHOST;
		blueAI.assignToCreature(blueGhost);
		
		try {
			BufferedImage imgBasePlayer = ImageIO.read(new File("images\\Pacman.bmp"));
			myPlayer.setGraphics(ApplicationView.convertImageToLocalSettings(imgBasePlayer));
			//myPlayer.overrideColor = true;
			//myPlayer.baseColor = new Color(ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255), ApplicationController.getGenerator().nextInt(255));
			myPlayer.name = "Pac-man";
			
			BufferedImage imgBaseRedGhost = ImageIO.read(new File("images\\RedGhost.bmp"));
			redGhost.setGraphics(ApplicationView.convertImageToLocalSettings(imgBaseRedGhost));
			redGhost.name = "Red-Ghost";
			
			BufferedImage imgBaseBlueGhost = ImageIO.read(new File("images\\BlueGhost.bmp"));
			blueGhost.setGraphics(ApplicationView.convertImageToLocalSettings(imgBaseBlueGhost));
			blueGhost.name = "Blue-Ghost";
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
				else if((x == redGhost.myLocation.x) && (y == redGhost.myLocation.y))
					printList[y][x] = redGhost.generateDisplayNode();
				else if((x == blueGhost.myLocation.x) && (y == blueGhost.myLocation.y))
					printList[y][x] = blueGhost.generateDisplayNode();
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

	public void writeToXMLFile(BufferedWriter outWR) {
		try {
			outWR.write("<Model>\r\n");
			myBoard.writeToXMLFile(outWR);
			outWR.write("</Model>\r\n");
		} catch (IOException e) {
			System.out.println("Error, cannot write to log file");
			System.exit(0);
		}
	}
}

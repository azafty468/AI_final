package primary;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.w3c.dom.Node;

import aiModels.*;

import view.ApplicationView;
import view.PrintListNode;
import xml.Message;

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
		if(!setDefaultValues())
			return false;
		playerAI.assignToCreature(myPlayer);
		redAI.assignToCreature(redGhost);
		blueAI.assignToCreature(blueGhost);

		myBoard = new Board();
		myBoard.setDimensions(width, height);
		myBoard.generateRandomMap();
		myPlayer.setXY(5, 5);
		redGhost.setXY(10, 10);
		blueGhost.setXY(15, 15);
		return true;
	}
	
	private boolean setDefaultValues() {
		myPlayer = new GameObjectPlayer();
		myPlayer.myAlliance = CreatureAlliance.PLAYER;

		redGhost = new GameObjectCreature();
		redGhost.myAlliance = CreatureAlliance.GHOST;

		blueGhost = new GameObjectCreature();
		blueGhost.myAlliance = CreatureAlliance.GHOST;

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
	
	private AIModel readAIFromXML(String aiName) {
		AIModel retVal = null;
		
		if (aiName.equals("class aiModels.AIModelClosestMove"))
			retVal = new AIModelClosestMove();
		else if (aiName.equals("class aiModels.AIModelDijkstraAlgorithm")) 
			retVal = new AIModelDijkstraAlgorithm();
		else if (aiName.equals("class aiModels.AIModelDirectMove")) 
			retVal = new AIModelDirectMove();
		else if (aiName.equals("class aiModels.AIModelEnemy")) 
			retVal = new AIModelEnemy();
		else if (aiName.equals("class aiModels.AIModelHillClimb")) 
			retVal = new AIModelHillClimb();
		else if (aiName.equals("class aiModels.AIModelPlayer")) 
			retVal = new AIModelPlayer();
		return retVal;
	}
	
	public boolean readFromXMLFile(Node inMessage) {
		if(!setDefaultValues())
			return false;
		
		Node localNode = inMessage.getFirstChild();
		
		int readWidth;
		int readHeight;
		String readAI;
		AIModel readModel;
		
		myPlayer.name = localNode.getAttributes().getNamedItem("name").getNodeValue();
		myPlayer.myLocation = new Point(Integer.parseInt(localNode.getAttributes().getNamedItem("x").getNodeValue()), 
				Integer.parseInt(localNode.getAttributes().getNamedItem("y").getNodeValue()));
		readAI = localNode.getAttributes().getNamedItem("AIModel").getNodeValue();
		readModel = readAIFromXML(readAI);
		readModel.assignToCreature(myPlayer);
		
		//localNode.getNextSibling();
		localNode = inMessage.getChildNodes().item(1);
		redGhost.name = localNode.getAttributes().getNamedItem("name").getNodeValue();
		redGhost.myLocation = new Point(Integer.parseInt(localNode.getAttributes().getNamedItem("x").getNodeValue()), 
				Integer.parseInt(localNode.getAttributes().getNamedItem("y").getNodeValue()));
		readAI = localNode.getAttributes().getNamedItem("AIModel").getNodeValue();
		readModel = readAIFromXML(readAI);
		readModel.assignToCreature(redGhost);
		
		localNode = inMessage.getChildNodes().item(2);
		//localNode.getNextSibling();
		blueGhost.name = localNode.getAttributes().getNamedItem("name").getNodeValue();
		blueGhost.myLocation = new Point(Integer.parseInt(localNode.getAttributes().getNamedItem("x").getNodeValue()), 
				Integer.parseInt(localNode.getAttributes().getNamedItem("y").getNodeValue()));
		readAI = localNode.getAttributes().getNamedItem("AIModel").getNodeValue();
		readModel = readAIFromXML(readAI);
		readModel.assignToCreature(blueGhost);
		
		localNode = inMessage.getChildNodes().item(3);
		
		myBoard = new Board();
		return myBoard.loadFromXML(localNode);
	}

	public void writeToXMLFile(BufferedWriter outWR) {
		try {
			outWR.write("<Model>\r\n");
			outWR.write("<Player name='" + myPlayer.name + "' x='" + myPlayer.myLocation.x + "' y='" + myPlayer.myLocation.y + 
					"' AIModel='" + myPlayer.myAIModel.getClass().toString() + "'/>\r\n");
			outWR.write("<RedGhost name='" + redGhost.name + "' x='" + redGhost.myLocation.x + "' y='" + redGhost.myLocation.y + 
					"' AIModel='" + redGhost.myAIModel.getClass().toString() + "'/>\r\n");
			outWR.write("<BlueGhost name='" + blueGhost.name + "' x='" + blueGhost.myLocation.x + "' y='" + blueGhost.myLocation.y + 
					"' AIModel='" + blueGhost.myAIModel.getClass().toString() + "'/>\r\n");

			myBoard.writeToXMLFile(outWR);
			outWR.write("</Model>\r\n");
		} catch (IOException e) {
			System.out.println("Error, cannot write to log file");
			System.exit(0);
		}
	}
}

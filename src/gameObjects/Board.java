package gameObjects;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import primary.ApplicationController;
import primary.Constants;
import primary.Point;
import view.ApplicationView;

/**
 * Holds all the GameObject Backgrounds that make up the game board.
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
	
	/**
	 * Constructor for a randomly generated Board
	 * 
	 * @param newWidth
	 * @param newHeight
	 */
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
		
		setGameObjectTemplates();
	}

	/** 
	 * Constructor for a Board loaded from an XML node
	 * 
	 * @param inNode
	 */
	public Board(Node inNode) {
		width = Integer.parseInt(inNode.getAttributes().getNamedItem("width").getNodeValue());
		height = Integer.parseInt(inNode.getAttributes().getNamedItem("height").getNodeValue());

		myGO = new GameObjectBackground[height][width];
		myTokens = new ArrayList<GameObjectToken>();
		setGameObjectTemplates();
		inNode = inNode.getFirstChild();
		
		NodeList childList = inNode.getChildNodes();
		GameObjectBackground newGOB = null;
		for (int i = 0; i < childList.getLength(); i++) {
			Node child = childList.item(i);
			
			if (child.getAttributes().getNamedItem("name").getNodeValue().equals(templateBoundaryWall.name))
				newGOB = (GameObjectBackground) templateBoundaryWall.generateClone(null);
			else if (child.getAttributes().getNamedItem("name").getNodeValue().equals(templateBackgroundPit.name))
				newGOB = (GameObjectBackground) templateBackgroundPit.generateClone(null);
			else if (child.getAttributes().getNamedItem("name").getNodeValue().equals(templateBoundaryOpen.name))
				newGOB = (GameObjectBackground) templateBoundaryOpen.generateClone(null);
			else {
				System.err.println("unrecognized Background name: " + child.getAttributes().getNamedItem("name").getNodeValue());
				System.exit(-1);
			}
			
			newGOB.myLocation = new Point(Integer.parseInt(child.getAttributes().getNamedItem("x").getNodeValue()), 
					Integer.parseInt(child.getAttributes().getNamedItem("y").getNodeValue()));
			myGO[newGOB.myLocation.y][newGOB.myLocation.x]= newGOB; 
		}
		
		inNode = inNode.getNextSibling();
		childList = inNode.getChildNodes();
		GameObjectToken newGOT = null;
		for (int i = 0; i < childList.getLength(); i++) {
			Node child = childList.item(i);
			if (child.getAttributes().getNamedItem("name").getNodeValue().equals(templateStrawberryToken.name))
				newGOT = (GameObjectToken) templateStrawberryToken.generateClone(null);
			else {
				System.err.println("Unrecognized Token name: " + child.getAttributes().getNamedItem("name").getNodeValue());
				System.exit(-1);
			}
			
			newGOT.myLocation = new Point(Integer.parseInt(child.getAttributes().getNamedItem("x").getNodeValue()), 
					Integer.parseInt(child.getAttributes().getNamedItem("y").getNodeValue()));
			newGOT.pointValue = Integer.parseInt(child.getAttributes().getNamedItem("pointValue").getNodeValue());
			myTokens.add(newGOT);
		}
	}
	
	private void setGameObjectTemplates() {
		templateBoundaryWall = new GameObjectBackground();
		templateBoundaryWall.name = "Wall";
		templateBoundaryOpen = new GameObjectBackground();
		templateBoundaryOpen.name = "Open";
		
		templateBackgroundPit = new GameObjectBackground();
		templateBackgroundPit.name = "Pit";
		templateBackgroundPit.canBlockMovement = false;
		
		templateStrawberryToken = new GameObjectToken();
		templateStrawberryToken.name = "Strawberry";
		templateStrawberryToken.pointValue = 100;
		templateStrawberryToken.canBlockMovement = false;
		try {
			BufferedImage imgTemp = ImageIO.read(new File("images"+ Constants.fileDelimiter + "TestObstruction.bmp"));
			templateBoundaryWall.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			
			imgTemp = ImageIO.read(new File("images"+ Constants.fileDelimiter + "BackgroundEmpty.bmp"));
			templateBoundaryOpen.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			templateBoundaryOpen.canBlockMovement = false;
			
			imgTemp = ImageIO.read(new File("images"+ Constants.fileDelimiter + "pit.bmp"));
			templateBackgroundPit.setGraphics(ApplicationView.convertImageToLocalSettings(imgTemp));
			templateBackgroundPit.canBlockMovement = false;
			
			imgTemp = ImageIO.read(new File("images"+ Constants.fileDelimiter + "strawberry.bmp"));
			templateStrawberryToken.setGraphics(imgTemp);
			
		}
		catch (Exception e) {
			System.err.println("Error while loading base graphics with exception: " + e.getMessage());
			System.exit(-1);
		}
	}
		
	public boolean generateRandomMap() {
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
		return true;
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
	
	
	public void writeToXMLFile(BufferedWriter outWR) {
		try {
			outWR.write("<Board height='" + height + "' width='" + width + "'>" + Constants.newline);
			outWR.write("<Backgrounds>" + Constants.newline);
			for (int y = 0; y < height; y++) 
				for (int x = 0; x < width; x++) {
					outWR.write("<Background x='" + myGO[y][x].myLocation.x + "' y='" + myGO[y][x].myLocation.y + "' name='" +
							myGO[y][x].name +  "'/>" + Constants.newline);
				}
			outWR.write("</Backgrounds>" + Constants.newline); 
			
			outWR.write("<Tokens>" + Constants.newline);
			for (int i = 0; i < myTokens.size(); i++) 
				outWR.write("<Token x='" + myTokens.get(i).myLocation.x + "' y='" + myTokens.get(i).myLocation.y + 
						"' name='" + myTokens.get(i).name + "' pointValue='" + myTokens.get(i).pointValue + "'/>" + Constants.newline);
			outWR.write("</Tokens>" + Constants.newline);
			outWR.write("</Board>");
		} catch (IOException e) {
			System.out.println("Error, cannot write to log file");
			System.exit(0);
		}
	}
}

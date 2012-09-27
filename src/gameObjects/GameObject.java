package gameObjects;

import java.awt.image.BufferedImage;

/**
 *  GameObject is a very generic class to hold anything that the game interacts with.  They are broken down into GameObjectCreature (those capable of
 * 	independent action, GameObjectBackground (objects which cannot move or act) and GameObject.... (something here that indicates a goal)
 */
public abstract class GameObject {
	private BufferedImage myGraphics; //the actual display object.  Current a 32X32 pixel bitmap image
	public boolean canBlockMovement;
	
	public GameObject() {
		myGraphics = null;
	}
	
	public BufferedImage getGraphics() {
		return myGraphics;
	}
	
	public void setGraphics(BufferedImage newGraphics) {
		myGraphics = newGraphics;
	}
}

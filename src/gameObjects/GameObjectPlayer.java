package gameObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObjectPlayer extends GameObjectCreature {
	public Board board;
	
	public GameObjectPlayer() {
		super();
		this.myAIModel = new AIModelPlayer();
	}
	
	public GameObject generateClone(GameObject newObject) {
		GameObject tmpObject;
		
		if (newObject == null)
			tmpObject = new GameObjectPlayer();
		else
			tmpObject = newObject;
		
		tmpObject = super.generateClone(tmpObject);
		
		GameObjectPlayer tmpO = (GameObjectPlayer) tmpObject;
		tmpO.board = board;
		
		return tmpObject;
	}
}

package gameObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameObjectPlayer extends GameObjectCreature {
	public int x;
	public int y;
	public Board board;
	public GameObjectBackground playerPosition;
	public BufferedImage imgPlayer;
	
	public GameObjectPlayer(int startX, int startY) {
		super();
		this.myAIModel = new AIModelPlayer();
		this.x = startX;
		this.y = startY;
		
		try {
			this.imgPlayer = ImageIO.read(new File("images\\Pacman.bmp"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public BufferedImage getImage() {
		return imgPlayer;
	}
}

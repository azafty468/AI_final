package gameObjects;

/**
 * Houses all information on GameObjects that cannot perform action and primarily exist to block movement or add character to the game
 * @author Andrew
 *
 */
public class GameObjectBackground extends GameObject {
	GameObjectBackground() {
		super();
		canBlockMovement = true;
	}
}

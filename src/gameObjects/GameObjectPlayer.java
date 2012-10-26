package gameObjects;

import aiModels.AIModelPlayer;

public class GameObjectPlayer extends GameObjectCreature {
	public Board board;
	public int pointsGained;
	
	public GameObjectPlayer() {
		super();
		this.myAIModel = new AIModelPlayer();
		pointsGained = 0;
		myType = GameObjectType.PLAYER;
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
		tmpO.pointsGained = pointsGained;
		
		return tmpObject;
	}
}

package gameObjects;

public class GameObjectToken extends GameObject {
	GameObjectToken() {
		super();
		canBlockMovement = true;
	}
	
	public GameObject generateClone(GameObject newObject) {
		GameObject tmpObject;
		
		if (newObject == null)
			tmpObject = new GameObjectToken();
		else
			tmpObject = newObject;
		
		tmpObject = super.generateClone(tmpObject);
		
		return tmpObject;
	}

}

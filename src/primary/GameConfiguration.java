package primary;

import aiModels.*;

public class GameConfiguration {
	public String playerAIModel;
	public String redGhostAIModel;
	public boolean hasRedGhost;
	public String blueGhostAIModel;
	public boolean hasBlueGhost;
	public boolean visibleWorld;
	public boolean deterministicWorld;
	public boolean randomlyGenerateWorld;
	public String preexistingBoard;
	public boolean informativeZones; // these are the areas around a pit and/or a strawberry that indicate something is near
	public boolean isHumanControlled;
	public boolean hasInternalWalls;
	private int autoRepeatCounter;
	public boolean onAutoRepeat;
	public int nonDeterministicMovement [] = { 80, 10, 10, 5, 5, 0, 0, 0, 0 };
	public boolean killOnGhostTouch;
	public boolean fullValidationRun;
	
	/**
	 * 
	 * @param hasVisibleWorld
	 * @param playerAIName
	 * @param hasDeterministicWorld
	 * @param hasInformativeZones
	 */
	public GameConfiguration(boolean hasVisibleWorld, boolean hasDeterministicWorld, boolean hasInformativeZones, boolean hasInternalWalls, boolean hasKills) {
		visibleWorld = hasVisibleWorld;
		deterministicWorld = hasDeterministicWorld;
		randomlyGenerateWorld = true;
		hasRedGhost = false;
		hasBlueGhost = false;
		informativeZones = hasInformativeZones;
		this.hasInternalWalls = hasInternalWalls; 
		autoRepeatCounter = 1;
		onAutoRepeat = false;
		killOnGhostTouch = hasKills;
	}
	
	/**
	 * 
	 * @return Returns true if another setting exists, false otherwise
	 */
	public boolean rotateNextSetting() {
		boolean validationComplete = false;
		boolean rotateGhostModel = false;
		boolean rotatePlayerModel = false;

		if (deterministicWorld) {
			deterministicWorld = false;
			
			if (killOnGhostTouch) {
				killOnGhostTouch = false;
				
				if (visibleWorld) {
					visibleWorld = false;
					rotateGhostModel = true;
				}
				else
					visibleWorld = true;
				
			}
			else killOnGhostTouch = true;
		}
		else
			deterministicWorld = true;
		
		if (rotateGhostModel) {
			if (!hasRedGhost) {
				hasRedGhost = true;
				redGhostAIModel = "class aiModels.AIModelBlindlyForward";
			}
			else if (hasRedGhost && redGhostAIModel.equals("class aiModels.AIModelBlindlyForward"))
				redGhostAIModel = "class aiModels.AIModelDirectMove";
			else if (hasRedGhost && redGhostAIModel.equals("class aiModels.AIModelDirectMove"))
				redGhostAIModel = "class aiModels.AIModelClosestMove";
			else if (hasRedGhost && redGhostAIModel.equals("class aiModels.AIModelClosestMove"))
				redGhostAIModel = "class aiModels.AIModelHillClimb";
			else if (hasRedGhost && redGhostAIModel.equals("class aiModels.AIModelHillClimb")) {
				redGhostAIModel = "class aiModels.AIModelBlindlyForward";
				if (!hasBlueGhost) {
					hasBlueGhost = true;
					blueGhostAIModel = "class aiModels.AIModelBlindlyForward";
				}
				else if (hasBlueGhost && blueGhostAIModel.equals("class aiModels.AIModelBlindlyForward"))
					blueGhostAIModel = "class aiModels.AIModelDirectMove";
				else if (hasBlueGhost && blueGhostAIModel.equals("class aiModels.AIModelDirectMove"))
					blueGhostAIModel = "class aiModels.AIModelClosestMove";
				else if (hasBlueGhost && blueGhostAIModel.equals("class aiModels.AIModelClosestMove"))
					blueGhostAIModel = "class aiModels.AIModelHillClimb";
				else if (hasBlueGhost && blueGhostAIModel.equals("class aiModels.AIModelHillClimb")) {
					hasBlueGhost = false;
					blueGhostAIModel = "";
					hasRedGhost = false;
					redGhostAIModel = "";
					rotatePlayerModel = true;
				}
			}
			
		}
		
		if (rotatePlayerModel) {
			if (playerAIModel.equals("class aiModels.AIModelBlindlyForward"))
				playerAIModel = "class aiModels.AIModelDirectMove";
			else if (playerAIModel.equals("class aiModels.AIModelDirectMove"))
				playerAIModel = "class aiModels.AIModelClosestMove";
			else if (playerAIModel.equals("class aiModels.AIModelClosestMove"))
				playerAIModel = "class aiModels.AIModelHillClimb";
			else if (playerAIModel.equals("class aiModels.AIModelHillClimb")) 
				playerAIModel = "class aiModels.AIModelBasicUtility";
			else if (playerAIModel.equals("class aiModels.AIModelBasicUtility")) 
				playerAIModel = "class aiModels.AIModelLearning";
			else
				validationComplete = true;
		}
		
		if (validationComplete) {
			autoRepeatCounter = 0;
			return false;
		}		
		
		autoRepeatCounter = 10;
		return true;
	}
	
	public void setFullValidationRun() {
		// clear to base settings
		deterministicWorld = false;
		killOnGhostTouch = false;
		visibleWorld = false;
		hasRedGhost = false;
		hasBlueGhost = false;
		playerAIModel = "class aiModels.AIModelBlindlyForward";

		autoRepeatCounter = 10;
		onAutoRepeat = true;
		fullValidationRun = true;
	}
	
	public static AIModel getAIModel(String aiName) {
		AIModel retVal = null;
		
		if (aiName.equals("class aiModels.AIModelClosestMove"))
			retVal = new AIModelClosestMove();
		else if (aiName.equals("class aiModels.AIModelBlindlyForward")) 
			retVal = new AIModelBlindlyForward();
		else if (aiName.equals("class aiModels.AIModelDirectMove")) 
			retVal = new AIModelDirectMove();
		else if (aiName.equals("class aiModels.AIModelHillClimb")) 
			retVal = new AIModelHillClimb();
		else if (aiName.equals("class aiModels.AIModelPlayer")) 
			retVal = new AIModelPlayer();
		else if (aiName.equals("class aiModels.AIModelBasicUtility"))
			retVal = new AIModelBasicUtility();
		else if (aiName.equals("class aiModels.AIModelLearning"))
			retVal = new AIModelLearning();
		return retVal;
	}
	
	public void setPlayerAI(String aiName) {
		playerAIModel = aiName;
		if (aiName.equals("class aiModels.AIModelPlayer")) 
			isHumanControlled = true;
		else 
			isHumanControlled = false;
	}
	
	public void setRedGhostAI(String aiName) {
		hasRedGhost = true;
		redGhostAIModel = aiName;
	}
	
	public void setInitialBoard(String fileName) {
		if (fileName == null)
			randomlyGenerateWorld = true;
		else 
			randomlyGenerateWorld = false;
		preexistingBoard = fileName;
	}
	
	public void setAutoRepeatCounter(int newCounter) {
		if (newCounter > 1)
			onAutoRepeat = true;
		autoRepeatCounter = newCounter;
	}
	
	public void decrementAutoRepeatCounter() {
		autoRepeatCounter--;
	}
	
	public int getAutoRepeatCounter() {
		return autoRepeatCounter;
	}
	
	public void setBlueGhostAI(String aiName) {
		hasBlueGhost = true;
		blueGhostAIModel = aiName;
	}
	
	public String describeState() {
		String retVal = "Player AI: " + playerAIModel + " Runs Remaining: " + autoRepeatCounter +  " Deterministic: " + deterministicWorld + " Visible: " + visibleWorld + 
				" Ghost Kills: " + killOnGhostTouch + " Red Ghost: " + hasRedGhost;
		if (hasRedGhost)
			retVal += " Red Ghost AI: " + redGhostAIModel;
		retVal += " Blue Ghost: " + hasBlueGhost;
		if (hasBlueGhost) 
			retVal += " Red Ghost AI: " + blueGhostAIModel;
		
		return retVal;
	}
}

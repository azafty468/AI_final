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
	
	/**
	 * 
	 * @param hasVisibleWorld
	 * @param playerAIName
	 * @param hasDeterministicWorld
	 * @param hasInformativeZones
	 */
	public GameConfiguration(boolean hasVisibleWorld, String playerAIName, boolean hasDeterministicWorld, boolean hasInformativeZones) {
		visibleWorld = hasVisibleWorld;
		deterministicWorld = hasDeterministicWorld;
		randomlyGenerateWorld = true;
		hasRedGhost = false;
		hasBlueGhost = false;
		informativeZones = hasInformativeZones;
		setPlayerAI(playerAIName);
	}
	
	public static AIModel getAIModel(String aiName) {
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
		else if (aiName.equals("class aiModels.AIModelBasicUtility"))
			retVal = new AIModelBasicUtility();
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
	
	public void setBlueGhostAI(String aiName) {
		hasBlueGhost = true;
		blueGhostAIModel = aiName;
	}
}

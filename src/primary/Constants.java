package primary;

import java.util.ArrayList;

public class Constants {
	public static final String fileDelimiter = System.getProperty("file.separator");
	public static final String newline = System.getProperty("line.separator");
	public static final int baseImageSize = 32;
	public static enum PolicyMove {UP, UPLEFT, LEFT, DOWNLEFT, DOWN, DOWNRIGHT, RIGHT, UPRIGHT, NOWHERE, UNKNOWN; }
	
	public static PolicyMove getMoveDirectionToRight(PolicyMove targetMove) {
		if (targetMove == PolicyMove.UP)
			return PolicyMove.UPRIGHT;
		if (targetMove == PolicyMove.UPRIGHT)
			return PolicyMove.RIGHT;
		if (targetMove == PolicyMove.RIGHT)
			return PolicyMove.DOWNRIGHT;
		if (targetMove == PolicyMove.DOWNRIGHT)
			return PolicyMove.DOWN;
		if (targetMove == PolicyMove.DOWN)
			return PolicyMove.DOWNLEFT;
		if (targetMove == PolicyMove.DOWNLEFT)
			return PolicyMove.LEFT;
		if (targetMove == PolicyMove.LEFT)
			return PolicyMove.UPLEFT;
		if (targetMove == PolicyMove.UPLEFT)
			return PolicyMove.UP;
		
		return PolicyMove.UNKNOWN;
	}

	
	public static Point outcomeOfMove(PolicyMove moveDirection, Point sourcePt) {
		if (moveDirection == PolicyMove.UP)
			return new Point(sourcePt.x, sourcePt.y-1);
		if (moveDirection == PolicyMove.UPRIGHT)
			return new Point(sourcePt.x+1, sourcePt.y-1);
		if (moveDirection == PolicyMove.RIGHT)
			return new Point(sourcePt.x+1, sourcePt.y);
		if (moveDirection == PolicyMove.DOWNRIGHT)
			return new Point(sourcePt.x+1, sourcePt.y+1);
		if (moveDirection == PolicyMove.DOWN)
			return new Point(sourcePt.x, sourcePt.y+1);
		if (moveDirection == PolicyMove.DOWNLEFT)
			return new Point(sourcePt.x-1, sourcePt.y+1);
		if (moveDirection == PolicyMove.LEFT)
			return new Point(sourcePt.x-1, sourcePt.y);
		if (moveDirection == PolicyMove.UPLEFT)
			return new Point(sourcePt.x-1, sourcePt.y-1);
		if (moveDirection == PolicyMove.NOWHERE)
			return new Point(sourcePt);
		
		return new Point(sourcePt);
		
	}
	

	public static ArrayList<PolicyMove> populateBestMoveDeterministicList(int sourceX, int sourceY, int targetX, int targetY) {
		ArrayList<PolicyMove> bestMoveList = new ArrayList<PolicyMove>();
		
		if (sourceX > targetX && sourceY < targetY) {
			bestMoveList.add(PolicyMove.DOWNLEFT);
			bestMoveList.add(PolicyMove.DOWN);
			bestMoveList.add(PolicyMove.LEFT);
			bestMoveList.add(PolicyMove.DOWNRIGHT);
			bestMoveList.add(PolicyMove.UPLEFT);
			bestMoveList.add(PolicyMove.RIGHT);
			bestMoveList.add(PolicyMove.UP);
			bestMoveList.add(PolicyMove.UPRIGHT);
		}
		else if (sourceX == targetX && sourceY < targetY) {
			bestMoveList.add(PolicyMove.DOWN);
			bestMoveList.add(PolicyMove.DOWNLEFT);
			bestMoveList.add(PolicyMove.DOWNRIGHT);
			bestMoveList.add(PolicyMove.LEFT);
			bestMoveList.add(PolicyMove.RIGHT);
			bestMoveList.add(PolicyMove.UPLEFT);
			bestMoveList.add(PolicyMove.UPRIGHT);
			bestMoveList.add(PolicyMove.UP);
		}
		else if (sourceX > targetX && sourceY == targetY) {
			bestMoveList.add(PolicyMove.LEFT);
			bestMoveList.add(PolicyMove.DOWNLEFT);
			bestMoveList.add(PolicyMove.UPLEFT);
			bestMoveList.add(PolicyMove.UP);
			bestMoveList.add(PolicyMove.DOWN);
			bestMoveList.add(PolicyMove.DOWNRIGHT);
			bestMoveList.add(PolicyMove.UPRIGHT);
			bestMoveList.add(PolicyMove.RIGHT);
		}
		else if (sourceX > targetX && sourceY > targetY) {
			bestMoveList.add(PolicyMove.UPLEFT);
			bestMoveList.add(PolicyMove.LEFT);
			bestMoveList.add(PolicyMove.UP);
			bestMoveList.add(PolicyMove.DOWNLEFT);
			bestMoveList.add(PolicyMove.UPRIGHT);
			bestMoveList.add(PolicyMove.DOWN);
			bestMoveList.add(PolicyMove.RIGHT);
			bestMoveList.add(PolicyMove.DOWNRIGHT);
		}
		else if (sourceX == targetX && sourceY > targetY) {
			bestMoveList.add(PolicyMove.UP);
			bestMoveList.add(PolicyMove.UPLEFT);
			bestMoveList.add(PolicyMove.UPRIGHT);
			bestMoveList.add(PolicyMove.LEFT);
			bestMoveList.add(PolicyMove.RIGHT);
			bestMoveList.add(PolicyMove.DOWNLEFT);
			bestMoveList.add(PolicyMove.DOWNRIGHT);
			bestMoveList.add(PolicyMove.DOWN);
		}
		else if (sourceX < targetX && sourceY == targetY) {
			bestMoveList.add(PolicyMove.RIGHT);
			bestMoveList.add(PolicyMove.DOWNRIGHT);
			bestMoveList.add(PolicyMove.UPRIGHT);
			bestMoveList.add(PolicyMove.UP);
			bestMoveList.add(PolicyMove.DOWN);
			bestMoveList.add(PolicyMove.DOWNLEFT);
			bestMoveList.add(PolicyMove.UPLEFT);
			bestMoveList.add(PolicyMove.LEFT);
		}
		else if (sourceX < targetX && sourceY < targetY) {
			bestMoveList.add(PolicyMove.DOWNRIGHT);
			bestMoveList.add(PolicyMove.DOWN);
			bestMoveList.add(PolicyMove.RIGHT);
			bestMoveList.add(PolicyMove.DOWNLEFT);
			bestMoveList.add(PolicyMove.UPRIGHT);
			bestMoveList.add(PolicyMove.LEFT);
			bestMoveList.add(PolicyMove.UP);
			bestMoveList.add(PolicyMove.UPLEFT);
		}
		else if (sourceX < targetX && sourceY > targetY) {
			bestMoveList.add(PolicyMove.UPRIGHT);
			bestMoveList.add(PolicyMove.RIGHT);
			bestMoveList.add(PolicyMove.UP);
			bestMoveList.add(PolicyMove.DOWNRIGHT);
			bestMoveList.add(PolicyMove.UPLEFT);
			bestMoveList.add(PolicyMove.DOWN);
			bestMoveList.add(PolicyMove.LEFT);
			bestMoveList.add(PolicyMove.DOWNLEFT);
		}
			
		
		return bestMoveList;
	}
}

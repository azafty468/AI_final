package primary;

public class Point {
	public int x;
	public int y;
	
	public Point(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public Point(Point copyPoint) {
		x = copyPoint.x;
		y = copyPoint.y;
	}
}

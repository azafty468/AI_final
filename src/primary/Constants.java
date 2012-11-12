package primary;

public class Constants {
	public static final String fileDelimiter = System.getProperty("file.separator");
	public static final String newline = System.getProperty("line.separator");
	public static final int baseImageSize = 32;
	public static enum PolicyMove {UP, UPLEFT, LEFT, DOWNLEFT, DOWN, DOWNRIGHT, RIGHT, UPRIGHT, UNKNOWN; }
}

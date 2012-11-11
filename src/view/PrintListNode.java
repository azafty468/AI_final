package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class PrintListNode {
	public BufferedImage myImage;
	public Color baseColor;
	public boolean overrideColor;
	public int utilityValue;
	public boolean hasUtilityValue;
	
	public PrintListNode(BufferedImage newImage, boolean overrideBase, Color newColor) {
		myImage = newImage;
		overrideColor = overrideBase;
		baseColor = newColor;
		hasUtilityValue = false;
	}
	
	public void setUtilityValue(int newUtility) {
		hasUtilityValue = true;
		utilityValue = newUtility;
	}
		
}
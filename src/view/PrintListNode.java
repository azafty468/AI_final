package view;

import java.awt.Color;
import java.awt.image.BufferedImage;

import primary.Constants.PolicyMove;

public class PrintListNode {
	public BufferedImage myImage;
	public Color baseColor;
	public boolean overrideColor;
	public int utilityValue;
	public boolean hasUtilityValue;
	public boolean hasPolicyMove;
	public PolicyMove myPolicyMove;
	
	public PrintListNode(BufferedImage newImage, boolean overrideBase, Color newColor) {
		myImage = newImage;
		overrideColor = overrideBase;
		baseColor = newColor;
		hasUtilityValue = false;
		utilityValue = 0;
		hasPolicyMove = false;
	}
	
	public void setUtilityValue(int newUtility) {
		hasUtilityValue = true;
		utilityValue = newUtility;
		hasPolicyMove = false;
	}
	
	public void setPolicyValue(PolicyMove newPolicy) {
		hasUtilityValue = false;
		hasPolicyMove = true;
		myPolicyMove = newPolicy;
	}
		
}
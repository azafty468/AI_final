package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.List;
import java.io.File;

import javax.swing.JPanel;

import primary.Constants;

public class PastRunsJPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public List localList; 

	public PastRunsJPanel(int xlocation, int ylocation, int width, int height) {
		setBounds(xlocation, ylocation, width, height);
	    setLayout(new FlowLayout());
	    setBackground(Color.GREEN);
	    setFont(new Font("Helvetica", Font.PLAIN, 12));
	    localList = new List(10, false);
	    localList.setBounds(xlocation,  ylocation,  width,  height);
	    
	    int counter = 0;
	    final File folder = new File("logs" + Constants.fileDelimiter);
	    for (final File fileEntry : folder.listFiles()) {
	        if (!(fileEntry.isDirectory()) && counter < 15) {
	        	if (!(fileEntry.getName().contains(".xsd")) && !(fileEntry.getName().contains("Run"))) {
	        		localList.add(fileEntry.getName());
	            counter++;
	        	}
	        }
	    }
	    add(localList);

    	setVisible(true);
	}
}

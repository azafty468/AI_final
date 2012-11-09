package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import primary.Constants;
import primary.MainApplication;

public class ShowStartupScreen extends JFrame {
	static final long serialVersionUID = 1L;
	JPanel contentPane;
	JButton btnAIController;
	JButton btnHumanController;
	JButton btnExit;
	ImageIcon strawberry;
	ImageIcon blueGuy;
	ImageIcon orangeGuy;
	ImageIcon logo;
	JLabel pastRunsLabel;
	PastRunsJPanel myPastRunsJPanel;

	public ShowStartupScreen() {
		//creates the JFrame and contentPane to hold everything
		setTitle("AI Final Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 500);
		//the sizes are all relative, so don't resize it
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//the green matches the logo nicely
		contentPane.setBackground(Color.GREEN);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//this centers the frame on the screen
		setLocationRelativeTo(null);
	    
		//displays the logo
	    logo = new ImageIcon("images" + Constants.fileDelimiter + "AI_Logo.png");
	    JLabel imageContainer = new JLabel(logo);
	    imageContainer.setBounds(0, 20, 800, 200);
	    add(imageContainer);
	    
	    //displays a few berries to look cute
	    strawberry = new ImageIcon("images" + Constants.fileDelimiter + "berry.png");
	    /* Removed to clear up some room
	    JLabel berry1 = new JLabel(strawberry);
	    berry1.setBounds(100, 300, 50, 50);
	    add(berry1);
	    */
	    
	    JLabel berry2 = new JLabel(strawberry);
	    berry2.setBounds(500, 390, 50, 50);
	    add(berry2);
	    
	    JLabel berry3 = new JLabel(strawberry);
	    berry3.setBounds(600, 200, 50, 50);
	    add(berry3);
	    
	    //displays the blue ghost for fun
	    blueGuy = new ImageIcon("images" + Constants.fileDelimiter + "blue.png");
	    JLabel blue = new JLabel(blueGuy);
	    blue.setBounds(30, 50, 50, 50);
	    add(blue);
	    
	    //displays the orange ghost for fun
	    orangeGuy = new ImageIcon("images" + Constants.fileDelimiter + "orange.png");
	    JLabel orange = new JLabel(orangeGuy);
	    orange.setBounds(700, 50, 50, 50);
	    add(orange);
	    
	    //adds a button to use the AI model for the player
	    btnAIController = new JButton("AI Controller");
	    btnAIController.setBounds(335, 230, 130, 50);
	    add(btnAIController);
	    
	    //adds a button for a human controlled player
	    btnHumanController = new JButton("Human Controller");
	    btnHumanController.setBounds(335, 290, 130, 50);
	    add(btnHumanController);
	    
	    //add a button so the user can exit
	    btnExit = new JButton("Exit");
	    btnExit.setBounds(335, 350, 130, 50);
	    add(btnExit);
	    
	    
	    //The past runs document
	    pastRunsLabel = new JLabel("Prior Runs:");
	    pastRunsLabel.setBounds(25, 205, 400, 25);
	    add(pastRunsLabel);
	    myPastRunsJPanel = new PastRunsJPanel(25, 225, 200, 400);
	    add(myPastRunsJPanel);
	    
	    //This starts the player AI model 
	    btnAIController.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				dispose();
				MainApplication.startGame(true, myPastRunsJPanel.localList.getSelectedItem());
			}
	    });
	    
	    //This starts the human controlled player object
	    btnHumanController.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				dispose();
				MainApplication.startGame(false, myPastRunsJPanel.localList.getSelectedItem());
			}
	    });
	    
	    //exits the application
	    btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				dispose();
				System.exit(0);
			}
	    });
	}
}

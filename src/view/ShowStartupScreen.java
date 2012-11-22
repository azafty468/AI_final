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
import primary.GameConfiguration;
import primary.MainApplication;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import aiModels.AIModelClosestMove;
import aiModels.AIModelDijkstraAlgorithm;
import aiModels.AIModelDirectMove;
import aiModels.AIModelEnemy;
import aiModels.AIModelHillClimb;
import aiModels.AIModelPlayer;
import java.awt.Font;

public class ShowStartupScreen extends JFrame {
	static final long serialVersionUID = 1L;
	JPanel contentPane;
	JButton btnStartContoller;
	JButton btnExit;
	ImageIcon strawberry;
	ImageIcon blueGuy;
	ImageIcon orangeGuy;
	ImageIcon logo;
	JLabel pastRunsLabel;
	PastRunsJPanel myPastRunsJPanel;
	JCheckBox chckbxInteriorWalls, chckbxFullyVisibleWorld, chckbxRedGhost, chckbxBlueGhost, chckbxDeterministicMove;
	JComboBox redGhostAICombo, blueGhostAICombo, playerAICombo;

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
	    getContentPane().add(imageContainer);
	    
	    //displays a few berries to look cute
	    strawberry = new ImageIcon("images" + Constants.fileDelimiter + "berry.png");
	    
	    JLabel berry3 = new JLabel(strawberry);
	    berry3.setBounds(500, 313, 50, 50);
	    getContentPane().add(berry3);
	    
	    //displays the blue ghost for fun
	    blueGuy = new ImageIcon("images" + Constants.fileDelimiter + "blue.png");
	    JLabel blue = new JLabel(blueGuy);
	    blue.setBounds(30, 50, 50, 50);
	    getContentPane().add(blue);
	    
	    //displays the orange ghost for fun
	    orangeGuy = new ImageIcon("images" + Constants.fileDelimiter + "orange.png");
	    JLabel orange = new JLabel(orangeGuy);
	    orange.setBounds(700, 50, 50, 50);
	    getContentPane().add(orange);
	    
	    //adds a button to use the AI model for the player
	    btnStartContoller = new JButton("Start");
	    btnStartContoller.setBounds(335, 289, 130, 50);
	    getContentPane().add(btnStartContoller);
	    
	    //add a button so the user can exit
	    btnExit = new JButton("Exit");
	    btnExit.setBounds(335, 350, 130, 50);
	    getContentPane().add(btnExit);
	    
	    
	    //The past runs document
	    pastRunsLabel = new JLabel("Prior Runs:");
	    pastRunsLabel.setBounds(25, 205, 400, 25);
	    getContentPane().add(pastRunsLabel);
	    myPastRunsJPanel = new PastRunsJPanel(25, 225, 200, 400);
	    getContentPane().add(myPastRunsJPanel);
	    
	    chckbxInteriorWalls = new JCheckBox("Interior Walls");
	    chckbxInteriorWalls.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    chckbxInteriorWalls.setBounds(581, 356, 116, 23);
	    chckbxInteriorWalls.setSelected(true);
	    contentPane.add(chckbxInteriorWalls);
	    
	    chckbxFullyVisibleWorld = new JCheckBox("Visible World");
	    chckbxFullyVisibleWorld.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    chckbxFullyVisibleWorld.setBounds(581, 330, 116, 23);
	    chckbxFullyVisibleWorld.setSelected(true);
	    contentPane.add(chckbxFullyVisibleWorld);
	    
	    chckbxRedGhost = new JCheckBox("Red Ghost");
	    chckbxRedGhost.setBounds(520, 227, 97, 23);
	    chckbxRedGhost.setSelected(true);
	    contentPane.add(chckbxRedGhost);
	    
	    chckbxBlueGhost = new JCheckBox("Blue Ghost");
	    chckbxBlueGhost.setBounds(520, 257, 97, 23);
	    chckbxBlueGhost.setSelected(true);
	    contentPane.add(chckbxBlueGhost);
	    
	    chckbxDeterministicMove = new JCheckBox("Deterministic Move");
	    chckbxDeterministicMove.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    chckbxDeterministicMove.setBounds(581, 304, 116, 23);
	    chckbxDeterministicMove.setSelected(true);
	    contentPane.add(chckbxDeterministicMove);
	    
	    redGhostAICombo = new JComboBox();
	    redGhostAICombo.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    redGhostAICombo.setBounds(623, 228, 145, 20);
	    redGhostAICombo.addItem("AIModelClosestMove");
	    redGhostAICombo.addItem("AIModelDijkstraAlgorithm");
	    redGhostAICombo.addItem("AIModelDirectMove");
	    redGhostAICombo.addItem("AIModelEnemy");
	    redGhostAICombo.addItem("AIModelHillClimb");
	    redGhostAICombo.addItem("AIModelPlayer");
	    redGhostAICombo.addItem("AIModelBasicUtility");
	    redGhostAICombo.addItem("AIModelLearning");
	    redGhostAICombo.setSelectedIndex(1);
	    contentPane.add(redGhostAICombo);
	    
	    blueGhostAICombo = new JComboBox();
	    blueGhostAICombo.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    blueGhostAICombo.setBounds(623, 258, 145, 20);
	    blueGhostAICombo.addItem("AIModelClosestMove");
	    blueGhostAICombo.addItem("AIModelDijkstraAlgorithm");
	    blueGhostAICombo.addItem("AIModelDirectMove");
	    blueGhostAICombo.addItem("AIModelEnemy");
	    blueGhostAICombo.addItem("AIModelHillClimb");
	    blueGhostAICombo.addItem("AIModelPlayer");
	    blueGhostAICombo.addItem("AIModelBasicUtility");
	    blueGhostAICombo.addItem("AIModelLearning");
	    blueGhostAICombo.setSelectedIndex(2);
	    contentPane.add(blueGhostAICombo);
	    
	    JLabel lblPlayersAi = new JLabel("Players AI Model");
	    lblPlayersAi.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    lblPlayersAi.setBounds(500, 405, 86, 14);
	    contentPane.add(lblPlayersAi);
	    
	    playerAICombo = new JComboBox();
	    playerAICombo.setFont(new Font("Tahoma", Font.PLAIN, 10));
	    playerAICombo.setBounds(593, 402, 145, 20);
	    playerAICombo.addItem("AIModelClosestMove");
	    playerAICombo.addItem("AIModelDijkstraAlgorithm");
	    playerAICombo.addItem("AIModelDirectMove");
	    playerAICombo.addItem("AIModelEnemy");
	    playerAICombo.addItem("AIModelHillClimb");
	    playerAICombo.addItem("AIModelPlayer");
	    playerAICombo.addItem("AIModelBasicUtility");
	    playerAICombo.addItem("AIModelLearning");
	    playerAICombo.setSelectedIndex(7);
	    contentPane.add(playerAICombo);
			    
	    //This starts the player AI model 
	    btnStartContoller.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				dispose();
								
				MainApplication.startGame(getConfiguration());
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
	
	private GameConfiguration getConfiguration() {
		boolean hasVisibleWorld = chckbxFullyVisibleWorld.isSelected();
		boolean hasDeterministicWorld = chckbxDeterministicMove.isSelected();
		boolean hasInformativeZones = true;
		boolean hasInternalWalls = chckbxInteriorWalls.isSelected();
		
		GameConfiguration retVal = new GameConfiguration(hasVisibleWorld, hasDeterministicWorld, hasInformativeZones, hasInternalWalls);
		
		if (chckbxRedGhost.isSelected())
			retVal.setRedGhostAI("class aiModels." +  (String)redGhostAICombo.getSelectedItem());
		
		if (chckbxBlueGhost.isSelected())
			retVal.setBlueGhostAI("class aiModels." +  (String)blueGhostAICombo.getSelectedItem());
		
		retVal.setPlayerAI("class aiModels." +  (String)playerAICombo.getSelectedItem());
		
		retVal.setInitialBoard(myPastRunsJPanel.localList.getSelectedItem());
		
		return retVal;
	}
}

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

import primary.MainApplication;

public class ShowStartupScreen extends JFrame {
	static final long serialVersionUID = 1L;
	JPanel contentPane;
	JButton btnAIController;
	JButton btnHumanController;
	JButton btnExit;
	ImageIcon logo;

	public ShowStartupScreen() {
		setTitle("AI Final Project");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(800, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.GREEN);
		setContentPane(contentPane);
		contentPane.setLayout(null);
	    
	    logo = new ImageIcon("images\\AI_Logo.png");
	    JLabel imageContainer = new JLabel(logo);
	    imageContainer.setBounds(0, 20, 800, 200);
	    add(imageContainer);
	    
	    btnAIController = new JButton("AI Controller");
	    btnAIController.setBounds(335, 230, 130, 50);
	    add(btnAIController);
	    
	    btnHumanController = new JButton("Human Controller");
	    btnHumanController.setBounds(335, 290, 130, 50);
	    add(btnHumanController);
	    
	    btnExit = new JButton("Exit");
	    btnExit.setBounds(335, 350, 130, 50);
	    add(btnExit);
	    
	    //This starts the player AI model 
	    btnAIController.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				dispose();
				MainApplication.startGame(true);
			}
	    });
	    
	    //This starts the human controlled player object
	    btnHumanController.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				dispose();
				MainApplication.startGame(false);
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

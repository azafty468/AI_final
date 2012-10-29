package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import primary.MainApplication;

public class ShowStartupScreen extends JFrame implements WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	static ShowStartupScreen thisView = null;
	public static boolean canStart = false;
	private JFrame jframe;
	private JPanel jpanel1;
	private JPanel jpanel2;
	private JPanel jpanel3;
	private JButton btnStartGame;
	private JButton btnExit;
	private ImageIcon logo;

	ShowStartupScreen() {
		super();
		getContentPane().setPreferredSize( Toolkit.getDefaultToolkit().getScreenSize());
	    setResizable(true);
	}
	
	public static ShowStartupScreen getInstance() {
		if (thisView == null)
			thisView = new ShowStartupScreen();
		
		return thisView;
	}
	
	public boolean initializeScreen() {
		addWindowListener(this);
		
		//Set up the Frame and center it on the screen
	    jframe = new JFrame("AI Final Project");
	    //jframe.setSize(500, 500);
	    //Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    //jframe.setLocation(dim.width/2-jframe.getSize().width/2, dim.height/2-jframe.getSize().height/2);
	    logo = new ImageIcon("images\\AI_Logo.png");
	    Container contentArea = getContentPane();
	    contentArea.setBackground(Color.white);
	    
	    //create containers for the components
	    jpanel1 = new JPanel();
	    jpanel2 = new JPanel();
	    jpanel3 = new JPanel();
	    
	    btnStartGame = new JButton("Start");
	    jpanel1.add(btnStartGame);
	    
	    btnExit = new JButton("Exit");
	    jpanel2.add(btnExit);
	    
	    jpanel3.add(new JLabel(logo));
	    
	    //this grid organizes the components
	    GridBagLayout gridManager = new GridBagLayout();
	    GridBagConstraints pos = new GridBagConstraints();
	    contentArea.setLayout(gridManager);
	    pos.gridx = 1;
	    pos.gridy = 0;
	    contentArea.add(jpanel3, pos);
	    pos.gridx = 0;
	    pos.gridy = 2;
	    contentArea.add(jpanel1, pos);
	    pos.gridx = 2;
	    pos.gridy = 2;
	    contentArea.add(jpanel2, pos);
	    jframe.setContentPane(contentArea);
	    
	    btnStartGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				jframe.setVisible(false);
				MainApplication.startGame();
			}
	    });
	    
	    btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent a) {
				closeApplication();
			}
	    });

	    jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    jframe.pack();
	    jframe.setVisible(true);
	    
	    return true;
	}
	
	public void closeApplication() {
		this.dispose();
		System.exit(0);
	}
	
	public static int getScreenWorkingWidth() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	}

	public static int getScreenWorkingHeight() {
	    return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
}

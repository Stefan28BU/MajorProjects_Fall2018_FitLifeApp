package displayTest;


/*
 * Program: FitLife
 * File: WindowManager.java
 * Description: Creates various windows and handles moving between windows
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class WindowManager implements ActionListener{
	JFrame window;
	WindowManager() {
		window = null;
		
		toLogIn();
		
		/*
		window = makeWindow();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//window.pack();
		window.setSize(300, 300);
		window.setVisible(true);
		*/
	}
	
	private JFrame makeWindow() {
		//Handles Base construction of frame
		//Constructs a frame with a menu bar with various pages
		
		JFrame f = new JFrame();
		JMenuBar m = new JMenuBar();
		JMenuItem home = new JMenuItem("Home");
		JMenuItem logOut = new JMenuItem("Log Out");
		JMenuItem toCal = new JMenuItem("View Calendar");
		//Buttons to swap between pages
		
		home.addActionListener(this);
		logOut.addActionListener(this);
		toCal.addActionListener(this);
		
		m.add(home);
		m.add(logOut);
		m.add(toCal);
		
		f.add(m, BorderLayout.PAGE_START);
		
		//Doesn't handle sizing page or making visible
		return f;
	}
	
	public void toHome() {
		//Displays home page
		//Not sure what all would go here
		//Currently used for BorderLayout testing
		
		window.dispose();
		window = makeWindow();
		//Get rid of whatever is currently displayed
		//Make basic frame
		
		JLabel l1 = new JLabel("Info One: Line Start");
		JLabel l2 = new JLabel("Info Two: Line End");
		JLabel l3 = new JLabel("Info Three: Page End");
		JLabel l4 = new JLabel("Info Fore: Center");
		//Labels for BorderLayout tests
		
		window.add(l1, BorderLayout.LINE_START);
		window.add(l2, BorderLayout.LINE_END);
		window.add(l3, BorderLayout.PAGE_END);
		window.add(l4, BorderLayout.CENTER);
		
		
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 30, (int)window.getSize().getHeight() + 100);
		//Testing for window sizes.
		//In my opinion, just saying pack makes it too compact
		
		window.setVisible(true);
	}
	
	public void toLogIn() {
		//Makes log in page
		//Was experimenting with Grid bag Layout
		//Actually turned out pretty good
		
		if(window != null) {
			window.dispose();
			//If window isn't null, meaning it came from another window, get rid of it
		}
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//Make a panel for grid bag layout
		
		JLabel uLabel = new JLabel("Username: ");
		c.fill = GridBagConstraints.HORIZONTAL;
		//Have it fill the entire horizontal of the grid
		c.weightx = 0;
		//Make it a thinner column than others
		c.gridx = 0;
		c.gridy = 0;
		//Set position
		pane.add(uLabel, c);
		
		JTextField uName = new JTextField();
		c.weightx = 1;
		//Have a wider column
		c.gridx = 1;
		c.gridy = 0;
		pane.add(uName, c);
		
		JLabel pLabel = new JLabel("Password: ");
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(pLabel, c);
		
		JTextField pWord = new JTextField();
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(pWord, c);
		
		JButton createAcct = new JButton("Create Account...");
		createAcct.addActionListener(this);
		c.fill = GridBagConstraints.NONE;
		//Don't worry about filling the column
		//If set to horizontal, all buttons would be connected
		//I prefer a gap
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(createAcct, c);
		
		JButton lostPW = new JButton("Forgot Password");
		lostPW.addActionListener(this);
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		pane.add(lostPW, c);
		
		JButton logIn = new JButton("Log In");
		logIn.addActionListener(this);
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		pane.add(logIn, c);

		
		window = new JFrame("LogIn");
		window.add(pane);
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 80, (int)window.getSize().getHeight() + 20);
		window.setVisible(true);
		
	}
	
	public void toCalendar() {
		window.dispose();
		window = makeWindow();
		
		JTable cal = new JTable(4, 7);
		//Display an empty 28 day calendar
		
		window.add(cal, BorderLayout.CENTER);
		window.pack();
		window.setSize((int)window.getSize().getWidth() + 50, (int)window.getSize().getHeight() + 50);
		window.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Log In")) {
			toHome();
		} else if(e.getActionCommand().equals("Home")) {
			toHome();
			System.out.println("HOME");
		} else if(e.getActionCommand().equals("Log Out")) {
			toLogIn();
			System.out.println("LogOute");
		} else if(e.getActionCommand().equals("View Calendar")) {
			toCalendar();
			System.out.println("View Cal");
		} else {
			System.err.println("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}

package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.AcctCipher;
import edu.baylor.ecs.Listeners.LoginListener;

public final class LogInController extends WindowManager{

	// Used for encryption. Guaranteed unpredictable

	private static JTextField uName; // Used to hold username inputs
	private JPasswordField pWord; // Used to hold password inputs
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = screenSize.width-100;
	private int screenHeight = screenSize.height-100;
	private JFrame window;
	
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(LogInController.class.getName());
		logger.setLevel(Level.ALL);
	}
	
	/*The singleton code*/
	private static volatile LogInController instance = null;
	
	private LogInController() {}
	
	public static LogInController getInstance() {
		if(instance == null) {
			synchronized(LogInController.class) {
				if(instance == null) {
					instance = new LogInController();
				}
			}
		}
		return instance;
	}
	
	//Makes the login window
	public JFrame makeWindow() {
		
		window = new JFrame("Welcome");
		
		//Set layout and close operation
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		window.setLayout(flowLayout);
	    
		//Create boxes
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b1.setMaximumSize(new Dimension(2000,150));
		
		Box b2 = new Box(BoxLayout.Y_AXIS);
		b2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b2.setMaximumSize(new Dimension(2000,150));
		
		Box b3 = new Box(BoxLayout.Y_AXIS);
		b3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b3.setMaximumSize(new Dimension(2000,150));
		
		Box b4 = new Box(BoxLayout.Y_AXIS);
		b4.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b4.setMaximumSize(new Dimension(2000,150));

		Box board = new Box(BoxLayout.Y_AXIS);
		board.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		//Create labels
		JLabel uLabel = new JLabel("Username");
		uLabel.setFont(new Font("Username", Font.PLAIN, 20));
		
		uName = new JTextField();
		uName.setMaximumSize(new Dimension(2000,30));
		uName.setFont(new Font("", Font.PLAIN, 20));
		
		b1.add(uLabel);
		b1.add(uName);
		
		JLabel pLabel = new JLabel("Password");
		pLabel.setFont(new Font("Password", Font.PLAIN, 20));

		pWord = new JPasswordField();
		pWord.setMaximumSize(new Dimension(2000,30));
		pWord.setFont(new Font("", Font.PLAIN, 20));
		
		b2.add(pLabel);
		b2.add(pWord);

		//create buttons
		JButton createAcct = new JButton("Create Account");
		createAcct.setFont(new Font("Create Account", Font.PLAIN, 20));
		createAcct.setForeground(Color.white);
		createAcct.setBackground(new Color( 44, 62, 80 ));
		createAcct.setMaximumSize(new Dimension(2000,50));
		createAcct.addActionListener(new LoginListener());
		
		JButton lostPW = new JButton("Forgot Password");
		lostPW.setFont(new Font("Forgot Password", Font.PLAIN, 20));
		lostPW.setForeground(Color.white);
		lostPW.setBackground(new Color( 44, 62, 80 ));
		lostPW.setMaximumSize(new Dimension(2000,50));
		lostPW.addActionListener(new LoginListener());

		JButton logIn = new JButton("Sign In");
		logIn.setFont(new Font("Sign In", Font.PLAIN, 20));
		logIn.setForeground(Color.white);
		logIn.setBackground(new Color( 44, 62, 80 ));
		logIn.setMaximumSize(new Dimension(2000,50));
		logIn.addActionListener(new LoginListener());
		
		//add buttons
		b3.add(createAcct);
		b3.add(Box.createVerticalStrut(5));
		b3.add(lostPW);
		b3.add(Box.createVerticalStrut(5));
		b3.add(logIn);

		board.add(Box.createVerticalStrut(screenHeight/2-250));
		
		//create and add label
		JLabel welcome = new JLabel("Welcome to FitLife");	
		welcome.setFont(new Font("Welcome to FitLife", Font.PLAIN, 50));
		b4.add(welcome);
		
		//add boxes
		board.add(b4);
		board.add(Box.createVerticalStrut(40));
		board.add(b1);
		board.add(Box.createVerticalStrut(10));
		board.add(b2);
		board.add(Box.createVerticalStrut(10));
		board.add(b3);
		
		//add the board
		window.add(board);
		window.getContentPane().setBackground(new Color(234, 242, 248));
		window.repaint();

		//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); <---- this was already done
		window.pack();
		
		//set screen size and make the window spawn in the middle of the screen, regardless the monitor resolution
		window.setSize(new Dimension(screenWidth, screenHeight));
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		return window;
	}
	
	//Validates the accounts existence
	public boolean validate() {

		BufferedReader br = null;
		Scanner scnr = null;
		String fileContents = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Accounts.FIT")));
			scnr = new Scanner(br);
			scnr.useDelimiter("\\Z");
			fileContents = scnr.next();
			scnr.close();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(new JFrame(), "No accounts were found", "Dialog", JOptionPane.ERROR_MESSAGE);
			
			logger.log(Level.SEVERE, e1.getMessage(), e1);

			return false;
		}
		
		logger.info("ENCRYPTED LOGIN: " + fileContents);
		
		fileContents = AcctCipher.decrypt(fileContents, "UnGuEsSaBlEkEyke");
		
		logger.info("DECRYPTED LOGIN: " + fileContents);
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(fileContents.split("\n")));

		boolean isTrue = false;

		for (int i = 0; i < lines.size() && !isTrue; i++) {
			String[] acct;
			acct = lines.get(i).split(",");
			if (acct.length >= 2) {
				if (uName.getText().equals(acct[0]) && Arrays.equals(pWord.getPassword(), acct[1].toCharArray())) {
					isTrue = true;
				}
			}
		}

		return isTrue;
	}

	
	
	public Account getAcct() {

		boolean gotID = false;
		int id = -1;
		Account a = null;	
		BufferedReader br = null;
		Scanner scnr = null;
		String fileContents = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Accounts.FIT")));
			scnr = new Scanner(br);
			scnr.useDelimiter("\\Z");
			fileContents = scnr.next();
			scnr.close();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(new JFrame(), "No accounts were found. This shouldn't happen",
					"Dialog", JOptionPane.ERROR_MESSAGE);
			
			logger.log(Level.SEVERE, e1.getMessage(), e1);
			
			return null;
		}
		
		logger.info("ENCRYPTED getAcct: " + fileContents);
		
		fileContents = AcctCipher.decrypt(fileContents, "UnGuEsSaBlEkEyke");
		
		logger.info("DECRYPTED getAcct: " + fileContents);
		
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(fileContents.split("\n")));
	
		for(int i = 0; i < lines.size() && !gotID; i++) {
			String[] line;
			line = lines.get(i).split(",");
			if (line.length >= 3) {
				if (uName.getText().equals(line[0])) {
					gotID = true;
					id = Integer.parseInt(line[2]);
				}
			}
		}

		if (gotID && id != -1) {
			File acct = new File("ACCT" + Integer.toString(id));
			try {
				
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(acct)));
					scnr = new Scanner(br);
					scnr.useDelimiter("\\Z");
					fileContents = scnr.next();
					scnr.close();
				} catch (FileNotFoundException e1) {

					JOptionPane.showMessageDialog(new JFrame(), "No accounts were found. This "
							+ " REALLY shouldn't happen",
							"Dialog", JOptionPane.ERROR_MESSAGE);
					
					logger.log(Level.SEVERE, e1.getMessage(), e1);

					return null;

				}
				
				fileContents = AcctCipher.decrypt(fileContents, "UnGuEsSaBlEkEyke");
				StringReader strReader = new StringReader(fileContents);
				
				
				JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				a = (Account) jaxbUnmarshaller.unmarshal(strReader);
				a.setuName(uName.getText());
				strReader.close();
			} catch (JAXBException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Error unmarshalling", "Dialog", JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return null;
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Could not find account or no associated ID", "Dialog",
					JOptionPane.ERROR_MESSAGE);
		}

		return a;

	}
	
	//Destroys the login window
	public void destroyWindow() {
			window.dispose();
	}
	
	public static JTextField getuName() {
		return uName;
	}

	public JPasswordField getpWord() {
		return pWord;
	}
}
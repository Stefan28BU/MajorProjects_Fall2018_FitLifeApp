package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.AcctCipher;
import edu.baylor.ecs.Listeners.AcctCreatorListener;

public final class AcctCreator extends WindowManager {
	// Used for encryption. Guaranteed unpredictable

	static JTextField uName; // Used to hold username inputs
	static JPasswordField pWord; // Used to hold password inputs
	static JPasswordField pWord2; // Used when creating account
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = screenSize.width-100;
	private int screenHeight = screenSize.height-100;
	private JFrame window = null;
	
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(AcctCreator.class.getName());
		logger.setLevel(Level.ALL);
	}
	
	/*The singleton code*/
	private static volatile AcctCreator instance = null;
	
	private AcctCreator() {}
	
	public static AcctCreator getInstance() {
		if(instance == null) {
			synchronized(AcctCreator.class) {
				if(instance == null) {
					instance = new AcctCreator();
				}
			}
		}
		return instance;
	}
	

	public void makeWindow() {

		// Makes log in page
		// Was experimenting with Grid bag Layout
		// Actually turned out pretty good
		
		window = new JFrame("Create Account");

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		window.setLayout(flowLayout);

		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b1.setMaximumSize(new Dimension(2000,150));

		JLabel createLabel = new JLabel("Create Your Account");	
		createLabel.setFont(new Font("Welcome to FitLife", Font.PLAIN, 50));

		JLabel uLabel = new JLabel("Username");
		uLabel.setFont(new Font("Username", Font.PLAIN, 20));
		uName = new JTextField();
		uName.setMaximumSize(new Dimension(2000,30));
		uName.setFont(new Font("", Font.PLAIN, 20));

		JLabel pLabel = new JLabel("Password");
		pLabel.setFont(new Font("Password", Font.PLAIN, 20));
		pWord = new JPasswordField();
		pWord.setMaximumSize(new Dimension(2000,30));
		pWord.setFont(new Font("", Font.PLAIN, 20));

		JLabel pRepeatLabel = new JLabel("Please re-type password");
		pRepeatLabel.setFont(new Font("Please re-type password", Font.PLAIN, 20));
		pWord2 = new JPasswordField();
		pWord2.setMaximumSize(new Dimension(2000,30));
		pWord2.setFont(new Font("", Font.PLAIN, 20));

		JButton createAcct = new JButton("Create Account");
		createAcct.setFont(new Font("Create Account", Font.PLAIN, 20));
		createAcct.setForeground(Color.white);
		createAcct.setBackground(new Color( 44, 62, 80 ));
		createAcct.setMaximumSize(new Dimension(2000,150));
		createAcct.addActionListener(new AcctCreatorListener());

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Cancel", Font.PLAIN, 20));
		cancelButton.setForeground(Color.white);
		cancelButton.setBackground(new Color( 44, 62, 80 ));
		cancelButton.setMaximumSize(new Dimension(2000,150));
		cancelButton.addActionListener(new AcctCreatorListener());

		b1.add(Box.createVerticalStrut(screenHeight/2-250));
		b1.add(createLabel);
		b1.add(Box.createVerticalStrut(40));
		b1.add(uLabel);
		b1.add(uName);
		b1.add(Box.createVerticalStrut(10));
		b1.add(pLabel);
		b1.add(pWord);
		b1.add(Box.createVerticalStrut(10));
		b1.add(pRepeatLabel);
		b1.add(pWord2);
		b1.add(Box.createVerticalStrut(10));
		b1.add(createAcct);
		b1.add(Box.createVerticalStrut(10));
		b1.add(cancelButton);

		window.add(b1);

		window.getContentPane().setBackground(new Color(234, 242, 248));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();

		//set screen size and make the window spawn in the middle of the screen, regardless the monitor resolution
		window.setSize(new Dimension(screenWidth, screenHeight));
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public boolean createAcct() {

		if (!Arrays.equals(pWord.getPassword(), pWord2.getPassword())) {
			return false;
		}

		boolean alreadyExists = false;
		int accountCount = 0;
		String fileContents = "";
		File accts = new File("Accounts.FIT");
		if (accts.exists()) {
			BufferedReader br = null;
			Scanner scnr = null;
			fileContents = null;
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("Accounts.FIT")));
				scnr = new Scanner(br);
				scnr.useDelimiter("\\Z");
				fileContents = scnr.next();
				scnr.close();
			} catch (FileNotFoundException e) {

				JOptionPane.showMessageDialog(new JFrame(), "Account file exists but not found", "Failed Creation",
						JOptionPane.ERROR_MESSAGE);
				logger.log(Level.SEVERE, e.getMessage(), e);
				return false;

			}

			fileContents = AcctCipher.decrypt(fileContents, "UnGuEsSaBlEkEyke");

			ArrayList<String> lines = new ArrayList<String>(Arrays.asList(fileContents.split("\n")));

			for (int i = 0; i < lines.size() && !alreadyExists; i++) {
				String[] acct;
				acct = lines.get(i).split(",");
				if (acct.length >= 3) {
					if (uName.getText().equals(acct[0])) {
						alreadyExists = true;
					}
					try {
						if (accountCount < Integer.parseInt(acct[2])) {
							accountCount = Integer.parseInt(acct[2]);
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(
										new JFrame(), "Invalid Account ID found.\nAccount Causing "
												+ "problems has ID '" + acct[2] + "' in Accounts.FIT",
										"Failed Creation", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}

		}

		if (alreadyExists) {
			return false;
		}

		accountCount += 1;

		fileContents += "\n" + uName.getText() + "," + new String(pWord.getPassword()) + ","
				+ Integer.toString(accountCount);
		fileContents = AcctCipher.encrypt(fileContents, "UnGuEsSaBlEkEyke");
		
		BufferedWriter bw;
		try {
			accts = new File("ACCT" + Integer.toString(accountCount));
			if (!accts.exists()) { // I don't know when
				// this would fail, but it can't hurt

				bw = new BufferedWriter(new FileWriter(new File("Accounts.FIT")));
				bw.write(fileContents);
				bw.close();

				bw = new BufferedWriter(new FileWriter(accts));

				String marshed = null;
				StringWriter strWriter = new StringWriter();

				JAXBContext context = JAXBContext.newInstance(Account.class);
				Marshaller m = context.createMarshaller();
				m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(new Account(accountCount), strWriter);
				
				logger.info(strWriter.toString());
				marshed = AcctCipher.encrypt(strWriter.toString(), "UnGuEsSaBlEkEyke");
				bw.write(marshed);
				
				logger.info(marshed);
				bw.close();
			} else {
				JOptionPane.showMessageDialog(new JFrame(),
						"Account ID chosen for new file already exists. Account IDs might be misordered",
						"Failed Creation", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public void destroy() {
		window.dispose();
	}
	
	
	
}
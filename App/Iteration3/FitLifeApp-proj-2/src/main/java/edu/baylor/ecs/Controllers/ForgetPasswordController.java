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
import javax.swing.JTextField;
import edu.baylor.ecs.FitLifeApp.AcctCipher;
import edu.baylor.ecs.Listeners.ForgetPasswordListener;

public final class ForgetPasswordController extends WindowManager {
	// Used for encryption. Guaranteed unpredictable

	static JTextField uName; // Used to hold username inputs
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int screenWidth = screenSize.width-100;
	private int screenHeight = screenSize.height-100;
	private JFrame window;
	
	/*The singleton code*/
	private static volatile ForgetPasswordController instance = null;
	
	private ForgetPasswordController() {}
	
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(ForgetPasswordController.class.getName());
		logger.setLevel(Level.ALL);
	}
	
	public static ForgetPasswordController getInstance() {
		if(instance == null) {
			synchronized(ForgetPasswordController.class) {
				if(instance == null) {
					instance = new ForgetPasswordController();
				}
			}
		}
		return instance;
	}

	public void makeWindow() {

		window = new JFrame("Forget Password");

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.CENTER);
		window.setLayout(flowLayout);

		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		b1.setMaximumSize(new Dimension(2000,150));

		JLabel createLabel = new JLabel("Forget Password");	
		createLabel.setFont(new Font("Welcome to FitLife", Font.PLAIN, 50));

		JLabel uLabel = new JLabel("Enter your Username");
		uLabel.setFont(new Font("Username", Font.PLAIN, 20));
		uName = new JTextField();
		uName.setMaximumSize(new Dimension(2000,30));
		uName.setFont(new Font("", Font.PLAIN, 20));
		
		JButton checkAcct = new JButton("Check Account");
		checkAcct.setFont(new Font("Create Account", Font.PLAIN, 20));
		checkAcct.setForeground(Color.white);
		checkAcct.setBackground(new Color( 44, 62, 80 ));
		checkAcct.setMaximumSize(new Dimension(2000,150));
		checkAcct.addActionListener(new ForgetPasswordListener());

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setFont(new Font("Cancel", Font.PLAIN, 20));
		cancelButton.setForeground(Color.white);
		cancelButton.setBackground(new Color( 44, 62, 80 ));
		cancelButton.setMaximumSize(new Dimension(2000,150));
		cancelButton.addActionListener(new ForgetPasswordListener());

		b1.add(Box.createVerticalStrut(screenHeight/2-250));
		b1.add(createLabel);
		b1.add(Box.createVerticalStrut(40));
		b1.add(uLabel);
		b1.add(uName);
		b1.add(Box.createVerticalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(checkAcct);
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

	public boolean checkAccount() {

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
						JOptionPane.showMessageDialog(new JFrame(), "Invalid Account ID found.\nAccount Causing "
												+ "problems has ID '" + acct[2] + "' in Accounts.FIT",
										"Failed Creation", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
			}
		}

		if (alreadyExists) {
			return true;
		}
		return false;
	}
	
	public void destroy() {
		window.dispose();
	}
	
}

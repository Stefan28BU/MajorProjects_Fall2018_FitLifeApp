package edu.baylor.ecs.FitLifeApp;

/*
 * File:		LogIn.java
 * Description: Handles the creation of the log in window
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class LogIn {

	static private JTextField uName; // Used to hold username inputs
	static private JPasswordField pWord; // Used to hold password inputs

	// Listener used for buttons in LogIn window
	static class LogInListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Log In")) {
				if (LogIn.validate()) {
					WindowManager.setAcct(LogIn.getAcct());
					WindowManager.toHome();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Incorrect Username/Password", "Failed Login",
							JOptionPane.ERROR_MESSAGE);
				}
			} else if (e.getActionCommand().equals("Create Account")) {
				WindowManager.toAcctCreation();
			} else if (e.getActionCommand().equals("Forgot Password")) {
				WindowManager.toAcctCreation();
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static JFrame makeWindow(JFrame window) {
		// Makes log in page
		// Was experimenting with Grid bag Layout
		// Actually turned out pretty good

		if (window != null) {
			window.dispose();
			// If window isn't null, meaning it came from another window, get rid of it
		}
		JPanel pane = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		// Make a panel for grid bag layout

		JLabel uLabel = new JLabel("Username: ");
		c.fill = GridBagConstraints.HORIZONTAL;
		// Have it fill the entire horizontal of the grid
		c.weightx = 0;
		// Make it a thinner column than others
		c.gridx = 0;
		c.gridy = 0;
		// Set position
		pane.add(uLabel, c);

		uName = new JTextField();
		c.weightx = 1;
		// Have a wider column
		c.gridx = 1;
		c.gridy = 0;
		pane.add(uName, c);

		JLabel pLabel = new JLabel("Password: ");
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(pLabel, c);

		pWord = new JPasswordField();
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(pWord, c);

		JButton createAcct = new JButton("Create Account");
		createAcct.addActionListener(new LogInListener());
		c.fill = GridBagConstraints.NONE;
		// Don't worry about filling the column
		// If set to horizontal, all buttons would be connected
		// I prefer a gap
		c.weightx = 0;
		c.weighty = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(createAcct, c);

		JButton lostPW = new JButton("Forgot Password");
		lostPW.addActionListener(new LogInListener());
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		pane.add(lostPW, c);

		JButton logIn = new JButton("Log In");
		logIn.addActionListener(new LogInListener());
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 2;
		c.gridy = 2;
		pane.add(logIn, c);

		window = new JFrame("LogIn");
		window.add(pane);
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 80, (int) window.getSize().getHeight() + 20);
		window.setVisible(true);

		return window;
	}

	static boolean validate() {
		BufferedReader br;
		Scanner scnr;
		boolean isTrue = false;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Accounts.FIT")));
			scnr = new Scanner(br);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(), "No accounts were found", "Dialog", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		while (scnr.hasNextLine() && !isTrue) {
			String[] acct;
			acct = scnr.nextLine().split(",");
			if (acct.length >= 2) {
				if (uName.getText().equals(acct[0]) && Arrays.equals(pWord.getPassword(), acct[1].toCharArray())) {
					isTrue = true;
				}
			}
		}

		scnr.close();

		return isTrue;
	}

	public static Account getAcct() {
		File acct = new File("Accounts.FIT");
		BufferedReader br;
		Scanner scnr;
		boolean gotID = false;
		int id = -1;
		Account a = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(acct)));
			scnr = new Scanner(br);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(new JFrame(), "No accounts were found", "Dialog", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		while (scnr.hasNextLine() && !gotID) {
			String[] line;
			line = scnr.nextLine().split(",");
			if (line.length >= 3) {
				if (uName.getText().equals(line[0])) {
					gotID = true;
					id = Integer.parseInt(line[2]);
				}
			}
		}

		scnr.close();

		if (gotID && id != -1) {
			acct = new File("ACCT" + Integer.toString(id));
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(Account.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				a = (Account) jaxbUnmarshaller.unmarshal(acct);
			} catch (JAXBException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(new JFrame(), "Error unmarshalling", "Dialog", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Could not find account or no associated ID", "Dialog", JOptionPane.ERROR_MESSAGE);
		}

		return a;
	}

}
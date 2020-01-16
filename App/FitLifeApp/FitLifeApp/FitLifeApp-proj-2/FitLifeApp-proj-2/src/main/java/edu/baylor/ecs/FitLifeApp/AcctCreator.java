package edu.baylor.ecs.FitLifeApp;

/*
 * File:		AcctCreator.java
 * Description: Handles CreateAccount window and the actual creation of an account
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


public class AcctCreator {

	static JTextField uName; // Used to hold username inputs
	static JPasswordField pWord; // Used to hold password inputs
	static JPasswordField pWord2; // Used when creating account

	static class AcctCreatorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Create Account")) {
				if (AcctCreator.createAcct()) {
					WindowManager.toLogIn();
				} else {
					JOptionPane.showMessageDialog(new JFrame(), "Account Creation Failed", "Failed Creation",
							JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(new JFrame(), "Somehow you pressed a non-existent button?", "Failed",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	static public JFrame makeWindow(JFrame window) {

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
		c.weightx = 5;
		// Have a wider column
		c.gridx = 1;
		c.gridy = 0;
		pane.add(uName, c);

		JLabel pLabel = new JLabel("Password: ");
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(pLabel, c);

		pWord = new JPasswordField();
		c.weightx = 5;
		c.weighty = 2;
		c.gridx = 1;
		c.gridy = 1;
		pane.add(pWord, c);

		JLabel pRepeatLabel = new JLabel("Please retype password: ");
		c.weightx = 0;
		c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(pRepeatLabel, c);

		pWord2 = new JPasswordField();
		c.weightx = 5;
		c.weighty = 2;
		c.gridx = 1;
		c.gridy = 2;
		pane.add(pWord2, c);

		JButton createAcct = new JButton("Create Account");
		createAcct.addActionListener(new AcctCreatorListener());
		c.fill = GridBagConstraints.NONE;
		// Don't worry about filling the column
		// If set to horizontal, all buttons would be connected
		// I prefer a gap
		c.weightx = 0;
		c.weighty = 2;
		c.gridx = 1;
		c.gridy = 3;
		pane.add(createAcct, c);

		window = new JFrame("LogIn");
		window.add(pane);
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 80, (int) window.getSize().getHeight() + 20);
		window.setVisible(true);

		return window;

	}

	static public boolean createAcct() {
		if (!Arrays.equals(pWord.getPassword(), pWord2.getPassword())) {
			return false;
		}

		int accountCount = 0;
		BufferedReader br = null;
		Scanner scnr = null;
		boolean alreadyExists = false;

		File accts = new File("Accounts.FIT");
		if (accts.exists()) {
			try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(accts)));
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(new JFrame(), "Account file exists but not found", "Failed Creation",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return false;
			}

			if (br != null) {
				scnr = new Scanner(br);

				while (scnr.hasNextLine() && !alreadyExists) {
					String[] acct;
					acct = scnr.nextLine().split(",");
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
									new JFrame(), "Invalid Account ID found.\nAccount Causing " + "problems has ID '"
											+ acct[2] + "' in Accounts.FIT",
									"Failed Creation", JOptionPane.ERROR_MESSAGE);
						}
					}

				}
				scnr.close();
			}
		}
		if (alreadyExists) {
			return false;
		}

		accountCount += 1;
		BufferedWriter bw;
		try {
			accts = new File("ACCT" + Integer.toString(accountCount));
			if (!accts.exists()) {
				//I don't know when this would fail, but it can't hurt

				bw = new BufferedWriter(new FileWriter(new File("Accounts.FIT"), true));
				bw.write("\n" + uName.getText() + ","
						+ new String(pWord.getPassword()) + "," + Integer.toString(accountCount));
				bw.close();

				bw = new BufferedWriter(new FileWriter(accts));

				JAXBContext context = JAXBContext.newInstance(Account.class);
				Marshaller m = context.createMarshaller();
				m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);
				m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
				m.marshal(new Account(accountCount), bw);
				//m.marshal(new Account(accountCount), System.out);
				bw.close();
			} else {
				JOptionPane.showMessageDialog(
						new JFrame(), "Account ID chosen for new file already exists. Account IDs might be misordered",
						"Failed Creation", JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return true;
	}
}

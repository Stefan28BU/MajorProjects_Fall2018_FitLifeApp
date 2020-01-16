package edu.baylor.ecs.FitLifeApp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

public class AcctCreator {

	static JTextField uName; // Used to hold username inputs
	static JPasswordField pWord; // Used to hold password inputs
	static JPasswordField pWord2; // Used when creating account

	static public JFrame createAcctWindow(JFrame window, ActionListener listener) {

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
		createAcct.addActionListener(listener);
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
		if(!Arrays.equals(pWord.getPassword(), pWord2.getPassword())) {
			return false;
		}
		
		BufferedReader br;
		Scanner scnr = null;
		boolean alreadyExists = false;
		boolean noFile = false;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream("Accounts.FIT")));
			scnr = new Scanner(br);
		} catch (FileNotFoundException e) {
			noFile = true;
		}

		if (scnr != null) {
			while (scnr.hasNextLine() && !alreadyExists) {
				String[] acct;
				acct = scnr.nextLine().split(",");
				if (acct.length >= 2) {
					if (uName.getText().equals(acct[0])) {
						alreadyExists = true;
					}
				}
			}
			scnr.close();
		}
		if (alreadyExists) {
			return false;
		}

		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(new File("Accounts.FIT"), true));
			bw.write("\n" + uName.getText() + "," + new String(pWord.getPassword()));
			bw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return true;
	}
}

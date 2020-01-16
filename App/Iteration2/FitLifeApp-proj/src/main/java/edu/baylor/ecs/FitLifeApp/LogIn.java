package displayTest;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.swing.*;

public class LogIn {

	static JTextField uName;
	static JPasswordField pWord;

	static JFrame makeWindow(JFrame window, ActionListener listener) {
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

		JButton createAcct = new JButton("Create Account...");
		createAcct.addActionListener(listener);
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
		lostPW.addActionListener(listener);
		c.weightx = 1;
		c.weighty = 0.5;
		c.gridx = 1;
		c.gridy = 2;
		pane.add(lostPW, c);

		JButton logIn = new JButton("Log In");
		logIn.addActionListener(listener);
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

	static boolean validate(JFrame window) {
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
				if (uName.getText().equals(acct[0]) && pWord.getText().equals(acct[1])) {
					isTrue = true;
				}
			}
		}

		if (scnr != null) {
			scnr.close();
		}
		
		return isTrue;
	}
}

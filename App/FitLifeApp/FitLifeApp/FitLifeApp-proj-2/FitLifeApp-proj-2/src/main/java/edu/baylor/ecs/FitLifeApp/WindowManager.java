package edu.baylor.ecs.FitLifeApp;

/*
 * File:		WindowManager.java
 * Description: Handles the base creation of windows while logged in
 * 				As well as swapping between windows
 */
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;

import java.awt.event.*;

import java.util.*;

public class WindowManager {
	// private boolean flag = false;
	// private UtilDateModel model = new UtilDateModel();
	// private JDatePanelImpl datePanel;
	// private Date day = java.util.Calendar.getInstance().getTime();

	// ---------------------------
	// These still need to be moved into their own, but it's late and I am tired

	static JPanel cards; // a panel that uses CardLayout

	final static String ex1 = "Aerobic Exercise";
	final static String ex2 = "Strength Exercise";
	final static String ex3 = "Balance Exercise";
	final static String ex4 = "Flexibility Exercise";

	// -----------------------------------------

	private static JFrame window;
	private static Account acct;

	/*
	 * //I assume leftovers JLabel result; String currentPattern;
	 */
	static class BasicActListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Home")) {
				toHome();
				System.out.println("HOME");
			} else if (e.getActionCommand().equals("Log Out")) {
				window.dispose();
				toLogIn();
				System.out.println("Logged Out");
			} else if (e.getActionCommand().equals("View Calendar")) {

				// Might get removed

				WindowManager.toCalendar();
				System.out.println("View Cal");
			} else if (e.getActionCommand().equals("Date selected")) {

				// Might get removed

				/*
				 * System.out.println((Date) datePanel.getModel().getValue()); // Call toDay if
				 * the selected day already has been selected. (or the selected // day is today)
				 * if (day.equals((Date) datePanel.getModel().getValue())) {
				 * 
				 * if (CalendarWindow.getFlag() == true) { day = (Date)
				 * datePanel.getModel().getValue(); addWorkoutWindow(); } else { toDay((Date)
				 * datePanel.getModel().getValue()); } } day = (Date)
				 * datePanel.getModel().getValue();
				 */

				JOptionPane.showMessageDialog(new JFrame(), "\"Date Selected\" in WindowManager/BasicActListener",
						"Failed Login", JOptionPane.ERROR_MESSAGE);
			} else if (e.getActionCommand().equals("Confirm")) {
				try {
					CalendarWindow.showAddWorkoutDialog();
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			} else if (e.getActionCommand().equals("Plan Workout")) {
				// addWorkoutWindow();
				System.out.println("Planning Workout");
			} else {
				System.err.println("Unhandled Action Command: " + e.getActionCommand());
			}
		}
	}

	static class BasicItemListener implements ItemListener {

		@Override
		public void itemStateChanged(ItemEvent evt) {
			CardLayout cl = (CardLayout) (cards.getLayout());
			cl.show(cards, (String) evt.getItem());
		}

	}

	WindowManager() {
		acct = null;
		window = null;
		toLogIn();
	}

	private static JFrame makeWindow() {
		// Handles Base construction of frame
		// Constructs a frame with a menu bar with various pages

		JFrame f = new JFrame("Home");
		JMenuBar jmb1 = new JMenuBar();
		jmb1.setBackground(acct.getColorBase2());

		JMenu menu = new JMenu("Menu");
		menu.setFont(new Font("Menu", Font.PLAIN, 25));
		menu.setPreferredSize(new Dimension(70, 70));
		menu.setForeground(Color.white);

		JMenuItem home = new JMenuItem("Home");
		home.setFont(new Font("Home", Font.PLAIN, 20));
		home.setBackground(acct.getColorBase2());
		home.setForeground(Color.white);

		JMenuItem logOut = new JMenuItem("Log Out");
		logOut.setFont(new Font("Log Out", Font.PLAIN, 20));
		logOut.setBackground(acct.getColorBase2());
		logOut.setForeground(Color.white);

		JMenuItem toCal = new JMenuItem("View Calendar");
		toCal.setFont(new Font("View Calendar", Font.PLAIN, 20));
		toCal.setBackground(acct.getColorBase2());
		toCal.setForeground(Color.white);

		JMenuItem addWorkout = new JMenuItem("Add Workout");
		addWorkout.setFont(new Font("Add Workout", Font.PLAIN, 20));
		addWorkout.setBackground(acct.getColorBase2());
		addWorkout.setForeground(Color.white);

		JMenuItem planWorkout = new JMenuItem("Plan Workout");
		planWorkout.setFont(new Font("Plan Workout", Font.PLAIN, 20));
		planWorkout.setBackground(acct.getColorBase2());
		planWorkout.setForeground(Color.white);

		menu.add(home);
		menu.add(toCal);
		menu.add(addWorkout);
		menu.add(planWorkout);
		menu.add(logOut);

		// Buttons to swap between pages

		home.addActionListener(new BasicActListener());
		logOut.addActionListener(new BasicActListener());
		toCal.addActionListener(new BasicActListener());
		addWorkout.addActionListener(new BasicActListener());
		planWorkout.addActionListener(new BasicActListener());

		jmb1.add(menu, BorderLayout.CENTER);
		// jmb1.add(logOut, BorderLayout.LINE_START);

		f.add(jmb1, BorderLayout.PAGE_START);
		// Doesn't handle sizing page or making visible
		return f;
	}

	static void toHome() {

		// moved to HomePage.java
		window.dispose();
		window = makeWindow();

		window = HomePage.makeWindow(window, acct);
	}

	public static void toLogIn() {
		// moved to LogIn.java
		window = LogIn.makeWindow(window);
	}

	public static void toAcctCreation() {
		// moved to AcctCreator.java
		window = AcctCreator.makeWindow(window);
	}

	public static void toCalendar() {
		// window.dispose();
		window = makeWindow();

		// Moved to CalendarWindow.java
		window = CalendarWindow.makeWindow(window);

	}

	// Not moved yet.
	public static void toDay(Date day) {
		window.dispose();
		window = makeWindow();

		File file = new File("workout.csv");
		int row = 0;
		ArrayList<String[]> arr = new ArrayList<String[]>();
		try {
			Scanner input = new Scanner(file);
			while (input.hasNext()) {
				String string = input.nextLine();
				String[] temp = new String[6];
				String[] str = string.split(",");
				for (int i = 0; i < str.length; i++) {
					temp[i] = str[i];
				}
				arr.add(temp);
				row++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		JTable data = new JTable(row, 4);
		for (int i = 0; i < data.getRowCount(); i++) {
			for (int j = 0; j < data.getColumnCount(); j++) {
				String str = arr.get(i)[5];
				String d = str.substring(0, 10);
				System.out.println(d);
				if (day.toString().contains(d)) {
					data.setValueAt(arr.get(i)[j + 1], i, j);
				}
			}

			// if (data.getValueAt(i, 0).equals(null)) {
			//
			// data.removeRowSelectionInterval(i, i);
			// }
		}

		window.add(data, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setSize((int) window.getSize().getWidth() + 50, (int) window.getSize().getHeight() + 50);
		window.setVisible(true);
	}

	// Also not yet moved
	public static void addWorkoutWindow() {
		window = new JFrame("Select a Type");

		JPanel comboBoxPane = new JPanel(); // use FlowLayout
		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(new BasicItemListener());
		comboBoxPane.add(cb);

		JButton j1 = new JButton("Confirm");

		j1.addActionListener(new BasicActListener());

		// Create the "cards".
		JPanel card1 = new JPanel();
		card1.add(j1);

		// Create the panel that contains the "cards".
		cards = new JPanel(new CardLayout());
		cards.add(card1, ex1);
		cards.add(card1, ex2);
		cards.add(card1, ex3);
		cards.add(card1, ex4);

		window.getContentPane().add(comboBoxPane, BorderLayout.PAGE_START);
		window.getContentPane().add(cards, BorderLayout.CENTER);

		window.setLocationRelativeTo(null);

		// Display the window.
		window.pack();
		window.setSize((int) window.getSize().getWidth() + 100, (int) window.getSize().getHeight() + 30);
		window.setVisible(true);
	}

	public static void setAcct(Account src) {
		// TODO Auto-generated method stub
		System.out.println(src.toString());
		WindowManager.acct = src;

	}

}
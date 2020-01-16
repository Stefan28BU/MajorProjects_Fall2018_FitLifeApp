package edu.baylor.ecs.Controllers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.Listeners.HomeListener;


public final class HomePageController extends WindowManager {

	private static volatile HomePageController instance = null;
	private JFrame window;

	private HomePageController() {
	}

	public static HomePageController getInstance() {
		if (instance == null) {
			synchronized (HomePageController.class) {
				if (instance == null) {
					instance = new HomePageController();
				}
			}
		}
		return instance;
	}

	public void makeWindow(Account acct) {
		window = new JFrame("Home");

		window.getContentPane().removeAll();
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());
		FlowLayout experimentLayout = new FlowLayout();

		// setup menubar
		JMenuBar jmb1 = new JMenuBar();
		jmb1.setPreferredSize(new Dimension(2000, 70));
		jmb1.setBackground(acct.getColorBase2());

		// setup menu
		JMenu menu = new JMenu("Menu");
		menu.setFont(new Font("Menu", Font.PLAIN, 25));
		menu.setPreferredSize(new Dimension(2000, 70));
		menu.setForeground(Color.white);

		// setup menu items
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

		// add items to the menu
		menu.setMaximumSize(new Dimension(2000, 50));
		menu.add(home);
		menu.add(toCal);
		menu.add(addWorkout);
		menu.add(planWorkout);
		menu.add(logOut);

		// Buttons to swap between pages
		home.addActionListener(new HomeListener());
		logOut.addActionListener(new HomeListener());
		toCal.addActionListener(new HomeListener());
		addWorkout.addActionListener(new HomeListener());
		planWorkout.addActionListener(new HomeListener());

		// add the menu
		jmb1.add(menu);
		window.add(jmb1, BorderLayout.NORTH);

		// add panels
		final JPanel p1 = new JPanel();
		experimentLayout.setAlignment(FlowLayout.TRAILING);
		p1.setLayout(experimentLayout);

		final JPanel p2 = new JPanel();
		p2.setLayout(experimentLayout);

		// create layout
		Box board = new Box(BoxLayout.Y_AXIS);
		board.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		// add buttons
		JButton jb1 = new JButton("NUTRITION");
		jb1.setFont(new Font("NUTRITION", Font.PLAIN, 50));
		jb1.setBackground(acct.getColorTrim2());
		jb1.setForeground(Color.WHITE);
		jb1.addActionListener(new HomeListener());

		JButton jb2 = new JButton("EXERCISE");
		jb2.setFont(new Font("EXERCISE", Font.PLAIN, 50));
		jb2.setBackground(acct.getColorTrim2());
		jb2.setForeground(Color.WHITE);
		jb2.addActionListener(new HomeListener());

		JButton jb3 = new JButton("SLEEP");
		jb3.setFont(new Font("SLEEP", Font.PLAIN, 50));
		jb3.setBackground(acct.getColorTrim2());
		jb3.setForeground(Color.WHITE);
		jb3.addActionListener(new HomeListener());

		JButton jb4 = new JButton("Review NUTRITION");
		jb4.setFont(new Font("Review NUTRITION", Font.PLAIN, 18));
		jb4.setBackground(acct.getColorTrim2());
		jb4.setForeground(Color.WHITE);
		jb4.addActionListener(new HomeListener());

		JButton jb5 = new JButton("Review EXERCISE");
		jb5.setFont(new Font("Review EXERCISE", Font.PLAIN, 18));
		jb5.setBackground(acct.getColorTrim2());
		jb5.setForeground(Color.WHITE);
		jb5.addActionListener(new HomeListener());

		JButton jb6 = new JButton("Review SLEEP");
		jb6.setFont(new Font("Review SLEEP", Font.PLAIN, 18));
		jb6.setBackground(acct.getColorTrim2());
		jb6.setForeground(Color.WHITE);
		jb6.addActionListener(new HomeListener());

		// set sizes of buttons
		jb1.setPreferredSize(new Dimension(400, 400));
		jb2.setPreferredSize(new Dimension(400, 400));
		jb3.setPreferredSize(new Dimension(400, 400));

		jb4.setPreferredSize(new Dimension(400, 50));
		jb5.setPreferredSize(new Dimension(400, 50));
		jb6.setPreferredSize(new Dimension(400, 50));

		// Add buttons to the experiment layout
		p1.add(jb1);
		p1.add(jb2);
		p1.add(jb3);

		p2.add(jb4);
		p2.add(jb5);
		p2.add(jb6);

		// Left to right component orientation is selected by default
		p1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		p2.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		String userName = LogInController.getuName().getText();

		JTextField jtf1 = new JTextField("Welcome Home, " + userName);
		jtf1.setEnabled(false);
		jtf1.setHorizontalAlignment(JTextField.CENTER);
		jtf1.setFont(new Font("Welcome Home", Font.PLAIN, 30));
		jtf1.setBackground(acct.getColorBase2());

		JLabel title = new JLabel("FitLife");
		title.setHorizontalAlignment(JTextField.CENTER);
		title.setFont(new Font("FitLife", Font.PLAIN, 100));
		title.setForeground(Color.white);

		JPanel titlePane = new JPanel();
		titlePane.setBackground(acct.getColorTrim1());
		titlePane.add(title);

		board.add(Box.createVerticalStrut(40));
		board.add(titlePane);
		board.add(Box.createVerticalStrut(40));
		board.add(p1);
		board.add(p2);

		JPanel fp = new JPanel();
		fp.add(board);
		fp.setBackground(acct.getColorTrim1());

		window.add(fp, BorderLayout.CENTER);
		window.add(jtf1, BorderLayout.SOUTH);

		window.repaint();
		window.pack();

		// set screen size and make the window spawn in the middle of the screen,
		// regardless the monitor resolution
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width - 100;
		int screenHeight = screenSize.height - 100;
		window.setSize(new Dimension(screenWidth, screenHeight));
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public void destory() {
		window.dispose();
	}
}

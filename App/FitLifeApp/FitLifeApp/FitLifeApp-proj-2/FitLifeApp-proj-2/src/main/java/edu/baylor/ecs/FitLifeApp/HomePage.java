package edu.baylor.ecs.FitLifeApp;

/*
 * File:		HomePage.java
 * Description: Handles the creation of the home page
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomePage {

	//Moved relevant listener code here, away from WindowManager
	static class HomeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("EXERCISE")) {
				//flag = true;
				CalendarWindow.setFlag(true);
				WindowManager.toCalendar();
				System.out.println("View Cal");
				System.out.println("Adding Workout");

			} else if (e.getActionCommand().equals("Review EXERCISE")) {
				WindowManager.toCalendar();
				CalendarWindow.setFlag(false);
				//flag = false;
				System.out.println("View Cal");
				System.out.println("Adding Workout");

			} else {
				//If this occurs, I missed a button somewhere
				JOptionPane.showMessageDialog(new JFrame(), "Hmm, more non-existant buttons...\nFind them all for a prize at the end!",
						"Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	static JFrame makeWindow(JFrame window, Account acct) {
		//Displays the home page
		//Mostly copied and pasted from toHome function
		//Only changes were removal of parts that I don't think did anything
		//And changing syntax to allow for class cahnges

		FlowLayout experimentLayout = new FlowLayout();

		
		final JPanel p1 = new JPanel();
		p1.setLayout(experimentLayout);
		experimentLayout.setAlignment(FlowLayout.TRAILING);

		final JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		experimentLayout.setAlignment(FlowLayout.TRAILING);

		JPanel board = new JPanel();
		board.setLayout(new FlowLayout());

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

		JTextField jtf1 = new JTextField("Welcome Home");
		jtf1.setEnabled(false);
		jtf1.setFont(new Font("Welcome Home", Font.PLAIN, 30));
		jtf1.setBackground(acct.getColorBase2());

		JPanel jpt = new JPanel();

		jpt.setBackground(acct.getColorBase2());
		jpt.add(jtf1, BorderLayout.CENTER);

		board.add(p1, BorderLayout.CENTER);
		board.add(p2, BorderLayout.AFTER_LINE_ENDS);

		board.setBackground(acct.getColorTrim1());
		window.add(board, BorderLayout.CENTER);
		window.add(jpt, BorderLayout.SOUTH);

		window.pack();
		window.setSize((int) window.getSize().getWidth() + 500, (int) window.getSize().getHeight() + 600);
		// Testing for window sizes.
		// In my opinion, just saying pack makes it too compact
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		return window;
	}
}

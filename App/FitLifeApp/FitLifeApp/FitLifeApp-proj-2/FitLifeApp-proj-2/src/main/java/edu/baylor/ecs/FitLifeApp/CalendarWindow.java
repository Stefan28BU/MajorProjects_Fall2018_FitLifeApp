package edu.baylor.ecs.FitLifeApp;

/*
 * File:		CalendarWindow.java
 * Description:	Handles calendar window creation and show workout dialog
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

//Not sure where exactly, but there is the issue of also showing the menu when opening the calendar
//So you could swap to another screen while looking at the calendar.
//Might cause file problems, might cause other problems


public class CalendarWindow {
	static boolean flag = false;
	static JDatePanelImpl datePanel = null;
	static Date day = java.util.Calendar.getInstance().getTime();

	
	//Listener for Calendar specific buttons
	//Like the home listener, I might have missed some
	//Because I'm not familiar with this part of the code
	static class CalendarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("View Calendar")) {
				WindowManager.toCalendar();
				System.out.println("View Cal");
			} else if (e.getActionCommand().equals("Date selected")) {
				System.out.println((Date) datePanel.getModel().getValue());
				// Call toDay if the selected day already has been selected. (or the selected
				// day is today)
				if (day.equals((Date) datePanel.getModel().getValue())) {

					if (CalendarWindow.getFlag() == true) {
						day = (Date) datePanel.getModel().getValue();
						WindowManager.addWorkoutWindow();
					} else {
						WindowManager.toDay((Date) datePanel.getModel().getValue());
					}
				}
				day = (Date) datePanel.getModel().getValue();
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
	
	//To move flag out of WindowManager, it is put here
	//I'm not sure what flag does, so if it belongs somewhere else
	//Feel free to move it
	
	public static boolean getFlag() {
		return flag;
	}

	public static void setFlag(boolean flag) {
		CalendarWindow.flag = flag;
	}
	
	
	//Copied from toCalendar
	public static JFrame makeWindow(JFrame window) {
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		datePanel = new JDatePanelImpl(new UtilDateModel(), p);

		datePanel.setShowYearButtons(true);
		datePanel.setEnabled(true);
		datePanel.setFocusable(true);
		datePanel.addActionListener(new CalendarListener());
			//This listener is larger than it needs to be because I don't know what is checked

		window.add(datePanel, BorderLayout.CENTER);
		window.pack();
		window.setLocationRelativeTo(null);
		// window.setSize((int)window.getSize().getWidth() + 50,
		// (int)window.getSize().getHeight() + 50);
		window.setSize((int) window.getSize().getWidth() + 100, (int) window.getSize().getHeight() + 100);
		window.setVisible(true);
		
		return window;
	}
	
	

	public static void showAddWorkoutDialog() throws Exception {
		File file = new File("workout.csv");
		JTextField field1 = new JTextField();
		JTextField field2 = new JTextField();
		JTextField field3 = new JTextField();

		Object[] message = { "Name of Workout", field1, "Duration", field2, "Your Weight", field3, };
		int opt = JOptionPane.showConfirmDialog(null, message, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		String exercise = field1.getText();
		String duration = field2.getText();
		String weight = field3.getText();
		FileWriter w = new FileWriter(file, true);
		PrintWriter p = new PrintWriter(w);
		p.write("fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		System.out.println(
				"fu" + "," + exercise + "," + "160" + "," + weight + "," + duration + "," + day.toString() + "\n");
		w.close();
		p.close();

		


		
		//I have no idea what this is for
		//Strictly speaking, I have no idea how most of this works
		/*
		if (opt == JOptionPane.OK_OPTION) {
			window.dispose();
		}
		*/
	}
	
}

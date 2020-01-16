package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.WindowManager;

public /*
 * //I assume leftovers JLabel result; String currentPattern; This seems fine
 * but why
 */
class BasicActListener implements ActionListener {

	WindowManager wm = WindowManager.getInstance();

	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(BasicActListener.class.getName());
		logger.setLevel(Level.ALL);
	}

	public void actionPerformed(ActionEvent e) {

		// Home button is clicked, spawn home page
		if (e.getActionCommand().equals("Home")) {
			wm.toHome();
			logger.info("HOME");

			// if logout is selected displose of window and spawn log in screen
		} else if (e.getActionCommand().equals("View Calendar")) {

			// Might get removed
			wm.toCalendar();
			logger.info("View Calendar");

			// if a date is selected use joption pane
		} else if (e.getActionCommand().equals("Date selected")) {

			JOptionPane.showMessageDialog(new JFrame(), "\"Date Selected\" in WindowManager/BasicActListener",
					"Failed Login", JOptionPane.ERROR_MESSAGE);

			// if the confirm button is hit show the add workout dialog
		} else if (e.getActionCommand().equals("Plan Workout")) {
			// addWorkoutWindow();
			logger.info("Plan Workout");
		} else if (e.getActionCommand().equals("Confirm")) {
			// addWorkoutWindow();
			wm.getCalendarWindow().destroyWindow();
			wm.getWorkoutSelectionWindow().destroy();

		} else if (e.getActionCommand().equals("Cancel")) {
			// addWorkoutWindow();
			wm.getCalendarWindow().destroyWindow();
			wm.getWorkoutSelectionWindow().destroy();
		} else {
			logger.severe("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}

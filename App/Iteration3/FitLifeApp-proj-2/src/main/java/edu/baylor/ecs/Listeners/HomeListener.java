package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.baylor.ecs.Controllers.CalendarController;
import edu.baylor.ecs.Controllers.HomePageController;

public class HomeListener implements ActionListener  {

	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(HomeListener.class.getName());
		logger.setLevel(Level.ALL);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		HomePageController home = HomePageController.getInstance();
		if (e.getActionCommand().equals("EXERCISE")) {
			CalendarController.setFlag(true);
			CalendarController.setMode(Integer.valueOf(0));
			home.toCalendar();
			logger.info("View Calendar");
			logger.info("Adding Workout");

		} else if (e.getActionCommand().equals("Review EXERCISE")) {
			CalendarController.setFlag(false);
			CalendarController.setMode(Integer.valueOf(0));
			home.toCalendar();
			logger.info("View Calendar");
			logger.info("Reviewing Workout");

		} else if (e.getActionCommand().equals("NUTRITION")) {
			// set a flag and do nutrition stuff
			CalendarController.setFlag(true);
			CalendarController.setMode(Integer.valueOf(1));
			home.toCalendar();
			logger.info("View Calendar");
			logger.info("Adding Meal");

		} else if (e.getActionCommand().equals("Review NUTRITION")) {
			// set a flag and do the review nutrition stuff
			CalendarController.setFlag(false);
			CalendarController.setMode(Integer.valueOf(1));
			home.toCalendar();
			logger.info("View Calendar");
			logger.info("Reviewing Meal");

		} else if (e.getActionCommand().equals("SLEEP")) {
			// set flag and do the sleep stuff
			CalendarController.setFlag(true);
			CalendarController.setMode(Integer.valueOf(2));
			home.toCalendar();
			logger.info("View Calendar");
			logger.info("Adding Sleep");

		} else if (e.getActionCommand().equals("Review SLEEP")) {
			// set flag nd do the review sleep stuff
			CalendarController.setFlag(false);
			CalendarController.setMode(Integer.valueOf(2));
			home.toCalendar();
			logger.info("View Calendar");
			logger.info("Reviewing Sleep");

		} else if (e.getActionCommand().equals("Log Out")) {
			home.destory();
			home.toLogIn();
			logger.info("Logged Out");
			// if view calendar is selected spawn calendar
		} else {
			// If this occurs, I missed a button somewhere
			JOptionPane.showMessageDialog(new JFrame(),
					"Hmm, more non-existant buttons...\nFind them all for a prize at the end!", "Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}

package edu.baylor.ecs.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.baylor.ecs.Controllers.CalendarController;

//Listener for Calendar specific buttons
//Like the home listener, I might have missed some
//Because I'm not familiar with this part of the code
public class CalendarListener implements ActionListener  {

	CalendarController calendarWindow = CalendarController.getInstance();

	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(CalendarListener.class.getName());
		logger.setLevel(Level.ALL);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("View Calendar")) {

			calendarWindow.toCalendar();
			logger.info("View Calendar");
		} else if (e.getActionCommand().equals("Date selected")) {

			//logger.info((Supplier<String>) calendarWindow.getDatePanel().getModel().getValue());
			// Call toDay if the selected day already has been selected. (or the selected
			// day is today)
			if (calendarWindow.getDay().equals((Date) calendarWindow.getDatePanel().getModel().getValue())) {

				if (calendarWindow.getFlag() == true) {

					//set the day with the day selected
					calendarWindow.setDay((Date) calendarWindow.getDatePanel().getModel().getValue());
					//determine the mode
					
					//Workout mode
					if(calendarWindow.getMode().intValue() == 0) {
						try {
							calendarWindow.showAddWorkoutDialog();
						} catch (Exception e1) {
							logger.severe(e1.getMessage());
						}
						
					//Nutrition Mode
					} else if (calendarWindow.getMode().intValue() == 1) {
						try {
							calendarWindow.showAddMealDialog();
						} catch (Exception e1) {
							logger.severe(e1.getMessage());
						}
					//Sleep mode	
					} else if (calendarWindow.getMode().intValue() == 2) {
						try {
							calendarWindow.showAddSleepDialog();
						} catch (Exception e1) {
							logger.severe(e1.getMessage());
						}
					}
					
				//we're in review mode	
				} else {
					calendarWindow.toDay((Date) calendarWindow.getDatePanel().getModel().getValue());
				}
			}
			calendarWindow.setDay((Date) calendarWindow.getDatePanel().getModel().getValue());
		} else if (e.getActionCommand().equals("Plan Workout")) {

			// addWorkoutWindow();
			logger.info("Planning Workout");
		} else {

			logger.severe("Unhandled Action Command: " + e.getActionCommand());
		}
	}
}

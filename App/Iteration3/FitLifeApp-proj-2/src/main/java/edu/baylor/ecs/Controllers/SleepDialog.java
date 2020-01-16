package edu.baylor.ecs.Controllers;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import edu.baylor.ecs.FLADatabase.SleepController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Sleep;

public final class SleepDialog {
	private static volatile SleepDialog instance = null;
	private JFrame window;
	private SleepController sc = SleepController.getInstance();
	private String rating = null;
	private final String ex1 = "0";
	private final String ex2 = "1";
	private final String ex3 = "2";
	private final String ex4 = "3";
	private final String ex5 = "4";
	private final String ex6 = "5";
	private final String ex7 = "6";
	private final String ex8 = "7";
	private final String ex9 = "8";
	private final String ex10 = "9";
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(SleepDialog.class.getName());
		logger.setLevel(Level.ALL);
	}

	private SleepDialog() {
	}

	public static SleepDialog getInstance() {
		if (instance == null) {
			synchronized (SleepDialog.class) {
				if (instance == null) {
					instance = new SleepDialog();
				}
			}
		}
		return instance;
	}

	/*
	 * function opens the window to get sleep information from the user
	 */
	public void addSleep(Date day) throws IOException {
		//------------------------------------------------------------------>
		//new add sleep window
		//------------------------------------------------------------------>
		
		window = new JFrame("Enter Sleep");
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);		
		b1.setPreferredSize(new Dimension(500,500));

		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20))); 

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		window.setLayout(flowLayout);

		JTextField field1 = new JTextField();
		field1.setMaximumSize(new Dimension(2000,30));
		field1.setBackground(new Color(232, 248, 245));
		field1.setFont(new Font(null, Font.PLAIN, 20));

		/* Sets up a JSpinner for time */
		SpinnerDateModel sdm = new SpinnerDateModel();
		JSpinner timeSpinner = new JSpinner(sdm);
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);

		/* Setup a JComboBox for the */
		String comboBoxItems[] = { ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		setRating(String.valueOf(cb.getSelectedItem()));
		cb.setEditable(false);

		JLabel l1 = new JLabel("Duration");
		l1.setFont(new Font("Duration", Font.PLAIN, 20));

		JLabel l2 = new JLabel("Rating");
		l2.setFont(new Font("Rating", Font.PLAIN, 20));

		JLabel l3 = new JLabel("Start Time");
		l3.setFont(new Font("Start Time", Font.PLAIN, 20));

		JPanel p1 = new JPanel();
		p1.setLayout(flowLayout);

		JPanel p2 = new JPanel();
		p2.setLayout(flowLayout);

		JPanel p3 = new JPanel();
		p3.setLayout(flowLayout);

		p1.add(l1);
		p2.add(l2);
		p3.add(l3);

		JLabel title = new JLabel("New Sleep");
		title.setFont(new Font("New Sleep", Font.PLAIN, 40));

		JPanel tt = new JPanel(); // use FlowLayout
		tt.setBackground(new Color(174, 214, 241));
		tt.add(title);

		b1.add(Box.createVerticalStrut(50));
		b1.add(tt);
		b1.add(Box.createVerticalStrut(30));
		b1.add(p1);
		b1.add(field1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p2);
		b1.add(cb);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p3);
		b1.add(timeSpinner);
		b1.add(Box.createVerticalStrut(100));

		int opt = JOptionPane.showConfirmDialog(null, b1, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if (opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {

			// convert date and time to just time
			SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
			sdf.setTimeZone(TimeZone.getDefault());
			String da = sdf.format(timeSpinner.getValue());
			Double duration = null;

			try {
				/* create sleep object and add it to the database */
				Sleep aSleep = new Sleep(Double.valueOf(field1.getText()),
						Integer.valueOf(cb.getSelectedItem().toString()), Time.valueOf(da));

				/* test integrity of the entered value */
				duration = Double.valueOf(field1.getText());
				if (duration.compareTo(Double.valueOf("0.1")) < 0 || duration.compareTo(Double.valueOf("23.9")) > 0) {
					throw new NumberFormatException();
				}

				sc.add(Account.getuName(), aSleep, day);
			} catch (NumberFormatException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid entry for Duration.\nMust be a number from 0.1 to 23.9", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);
				
				return;
			}
		}
	}

	public void editSleep(Sleep aSleep) {

		//------------------------------------------------------------------>
		//new edit sleep window
		//------------------------------------------------------------------>
		window = new JFrame("Edit Sleep");

		JTextField field1 = new JTextField(aSleep.getDuration().toString());
		field1.setMaximumSize(new Dimension(2000,30));
		field1.setBackground(new Color(232, 248, 245));
		field1.setFont(new Font(aSleep.getDuration().toString(), Font.PLAIN, 20));

		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);		
		b1.setPreferredSize(new Dimension(500,500));

		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20))); 

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		window.setLayout(flowLayout);
		
		/* Sets up a JSpinner for time */
		SpinnerDateModel sdm = new SpinnerDateModel();
		JSpinner timeSpinner = new JSpinner(sdm);
		JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
		timeSpinner.setEditor(timeEditor);
		timeSpinner.setBackground(new Color(232, 248, 245));

		/* Setup a JComboBox for the */
		String comboBoxItems[] = { ex1, ex2, ex3, ex4, ex5, ex6, ex7, ex8, ex9, ex10 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setBackground(new Color(232, 248, 245));
		setRating(String.valueOf(cb.getSelectedItem()));
		cb.setEditable(false);

		JLabel l1 = new JLabel("Duration");
		l1.setFont(new Font("Duration", Font.PLAIN, 20));

		JLabel l2 = new JLabel("Rating");
		l2.setFont(new Font("Rating", Font.PLAIN, 20));

		JLabel l3 = new JLabel("Start Time");
		l3.setFont(new Font("Start Time", Font.PLAIN, 20));

		JPanel p1 = new JPanel();
		p1.setLayout(flowLayout);

		JPanel p2 = new JPanel();
		p2.setLayout(flowLayout);

		JPanel p3 = new JPanel();
		p3.setLayout(flowLayout);

		p1.add(l1);
		p2.add(l2);
		p3.add(l3);

		JLabel title = new JLabel("Edit Sleep");
		title.setFont(new Font("Edit Sleep", Font.PLAIN, 40));

		JPanel tt = new JPanel(); // use FlowLayout
		tt.setBackground(new Color(174, 214, 241));
		tt.add(title);

		b1.add(Box.createVerticalStrut(50));
		b1.add(tt);
		b1.add(Box.createVerticalStrut(30));
		b1.add(p1);
		b1.add(field1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p2);
		b1.add(cb);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p3);
		b1.add(timeSpinner);
		b1.add(Box.createVerticalStrut(100));

		int opt = JOptionPane.showConfirmDialog(null, b1, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if (opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {

			// convert date and time to just time
			SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
			sdf.setTimeZone(TimeZone.getDefault());
			String da = sdf.format(timeSpinner.getValue());
			Double duration = null;

			try {
				/* create sleep object and add it to the database */
				aSleep = new Sleep(aSleep.getId(),Double.valueOf(field1.getText()),
						Integer.valueOf(cb.getSelectedItem().toString()), Time.valueOf(da));

				/* test integrity of the entered value */
				duration = Double.valueOf(field1.getText());
				if (duration.compareTo(Double.valueOf("0.1")) < 0 || duration.compareTo(Double.valueOf("23.9")) > 0) {
					throw new NumberFormatException();
				}
				sc.edit(aSleep);
			} catch (NumberFormatException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid entry for Duration.\nMust be a number from 0.1 to 23.9", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			}
		}
	}


	public void destroy() {
		window.dispose();
	}


	public void deleteSleep(Sleep aSleep) {
		window = new JFrame("Delete Sleep");

		int opt = JOptionPane.showConfirmDialog(window, "Warning!\nYou are about to delete the selected Sleep.\nIs this what you want?", "Enter Information", JOptionPane.YES_NO_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.NO_OPTION) {
			sc.delete(aSleep.getId());
		}
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
}

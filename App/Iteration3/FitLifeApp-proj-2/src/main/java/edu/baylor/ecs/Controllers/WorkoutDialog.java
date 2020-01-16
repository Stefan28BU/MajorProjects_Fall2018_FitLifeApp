package edu.baylor.ecs.Controllers;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import edu.baylor.ecs.FLADatabase.WorkoutController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Workout;

public final class WorkoutDialog {

	private static volatile WorkoutDialog instance = null;
	private JFrame window = null;
	private final String ex1 = "Aerobic Exercise";
	private final String ex2 = "Strength Exercise";
	private final String ex3 = "Balance Exercise";
	private final String ex4 = "Flexibility Exercise";
	private WorkoutController wc = WorkoutController.getInstance();
	private WorkoutDialog() {}
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(WorkoutDialog.class.getName());
		logger.setLevel(Level.ALL);
	}

	public static WorkoutDialog getInstance() {
		if (instance == null) {
			synchronized (WorkoutDialog.class) {
				if (instance == null) {
					instance = new WorkoutDialog();
				}
			}
		}
		return instance;
	}

	public void addWorkout(Date day) throws Exception {

		//------------------------------------------------------------------>
		//new addworkout window
		//------------------------------------------------------------------>
		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);		
		b1.setPreferredSize(new Dimension(500,500));

		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20))); 

		window = new JFrame("Enter Workout");

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		window.setLayout(flowLayout);

		JTextField field1 = new JTextField();
		field1.setMaximumSize(new Dimension(2000,30));
		field1.setBackground(new Color(232, 248, 245));
		field1.setFont(new Font(null, Font.PLAIN, 20));

		JTextField field2 = new JTextField();
		field2.setMaximumSize(new Dimension(2000,30));
		field2.setBackground(new Color(232, 248, 245));
		field2.setFont(new Font(null, Font.PLAIN, 20));

		JTextField field3 = new JTextField();
		field3.setMaximumSize(new Dimension(2000,30));
		field3.setBackground(new Color(232, 248, 245));
		field3.setFont(new Font(null, Font.PLAIN, 20));

		JTextField field4 = new JTextField();
		field4.setMaximumSize(new Dimension(2000,30));
		field4.setBackground(new Color(232, 248, 245));
		field4.setFont(new Font(null, Font.PLAIN, 20));

		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setBackground(new Color(232, 248, 245));
		cb.setEditable(false);

		JLabel l1 = new JLabel("Workout Type");
		l1.setFont(new Font("Workout Type", Font.PLAIN, 20));

		JLabel l2 = new JLabel("Name of Workout");
		l2.setFont(new Font("Name of Workout", Font.PLAIN, 20));

		JLabel l3 = new JLabel("Duration");
		l3.setFont(new Font("Duration", Font.PLAIN, 20));

		JLabel l4 = new JLabel("Your Weight");
		l4.setFont(new Font("Your Weight", Font.PLAIN, 20));

		JLabel l5 = new JLabel("Weight Used");
		l5.setFont(new Font("Weight Used", Font.PLAIN, 20));

		JPanel p1 = new JPanel();
		p1.setLayout(flowLayout);

		JPanel p2 = new JPanel();
		p2.setLayout(flowLayout);

		JPanel p3 = new JPanel();
		p3.setLayout(flowLayout);

		JPanel p4 = new JPanel();
		p4.setLayout(flowLayout);

		JPanel p5 = new JPanel();
		p5.setLayout(flowLayout);

		p1.add(l1);
		p2.add(l2);
		p3.add(l3);
		p4.add(l4);
		p5.add(l5);

		JLabel title = new JLabel("New Workout");
		title.setFont(new Font("New Workout", Font.PLAIN, 40));

		JPanel tt = new JPanel(); // use FlowLayout
		tt.setBackground(new Color(174, 214, 241));
		tt.add(title);

		b1.add(Box.createVerticalStrut(50));
		b1.add(tt);
		b1.add(p1);
		b1.add(cb);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p2);
		b1.add(field1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p3);
		b1.add(field2);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p4);
		b1.add(field3);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p5);
		b1.add(field4);
		b1.add(Box.createVerticalStrut(50));

		int opt = JOptionPane.showConfirmDialog(null, b1, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {

			//creates a workout and adds it to the database
			String type = null, exercise = null;
			Integer duration = null;
			Double weight = null, weightUsed = null;
			try {

				type = cb.getSelectedItem().toString();
				exercise = field1.getText();
				duration = Integer.valueOf(field2.getText());
				weight = Double.valueOf(field3.getText());
				weightUsed = Double.valueOf(field4.getText());

				if(duration.compareTo(Integer.valueOf(1)) < 0 || duration.compareTo(Integer.valueOf(1440)) > 0 || 
						weight.compareTo(Double.valueOf("4.0")) < 0 || weight.compareTo(Double.valueOf("1400.0")) > 0 || 
						weightUsed.compareTo(Double.valueOf("4.0")) < 0 || weight.compareTo(Double.valueOf("1400.0")) > 0) {
					throw new NumberFormatException();
				}else if(exercise.length() < 4) {
					throw new IllegalArgumentException();
				}
			}catch(NumberFormatException e){
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"That's not a valid number!.\nYour Weight and Weight Used must be a number from 4.0 to 1400.0\nDuration must be an integer from 1 to 1440.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			}catch(IllegalArgumentException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Not a valid exercise name!.\nMust be 4 or more characters", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			}

			Workout aWorkout = new Workout(duration,
					exercise, type, weight,
					weightUsed);
			wc.add(Account.getuName(), aWorkout, day);
		}
	}

	public void editWorkout(Workout aWorkout){

		//------------------------------------------------------------------>
		//new editworkout window
		//------------------------------------------------------------------>

		window = new JFrame("Edit Workout");

		JTextField field1 = new JTextField(aWorkout.getName());
		field1.setMaximumSize(new Dimension(2000,30));
		field1.setFont(new Font(aWorkout.getName(), Font.PLAIN, 20));
		field1.setBackground(new Color(232, 248, 245));

		JTextField field2 = new JTextField(aWorkout.getDuration().toString());
		field2.setMaximumSize(new Dimension(2000,30));
		field2.setFont(new Font(aWorkout.getDuration().toString(), Font.PLAIN, 20));
		field2.setBackground(new Color(232, 248, 245));

		JTextField field3 = new JTextField(aWorkout.getUserWeight().toString());
		field3.setMaximumSize(new Dimension(2000,30));
		field3.setFont(new Font(aWorkout.getUserWeight().toString(), Font.PLAIN, 20));
		field3.setBackground(new Color(232, 248, 245));

		JTextField field4 = new JTextField(aWorkout.getWorkoutWeights().toString());
		field4.setMaximumSize(new Dimension(2000,30));
		field4.setFont(new Font(aWorkout.getWorkoutWeights().toString(), Font.PLAIN, 20));
		field4.setBackground(new Color(232, 248, 245));

		Box b1 = new Box(BoxLayout.Y_AXIS);
		b1.setAlignmentX(JComponent.CENTER_ALIGNMENT);		
		b1.setPreferredSize(new Dimension(500,500));

		UIManager.put("OptionPane.background", new Color(174, 214, 241));
		UIManager.put("Panel.background", new Color(174, 214, 241));
		UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,20))); 

		window = new JFrame("Enter Workout");

		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		window.setLayout(flowLayout);

		String comboBoxItems[] = { ex1, ex2, ex3, ex4 };
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cb = new JComboBox(comboBoxItems);
		cb.setBackground(new Color(232, 248, 245));
		cb.setEditable(false);

		JLabel l1 = new JLabel("Workout Type");
		l1.setFont(new Font("Workout Type", Font.PLAIN, 20));

		JLabel l2 = new JLabel("Name of Workout");
		l2.setFont(new Font("Name of Workout", Font.PLAIN, 20));

		JLabel l3 = new JLabel("Duration");
		l3.setFont(new Font("Duration", Font.PLAIN, 20));

		JLabel l4 = new JLabel("Your Weight");
		l4.setFont(new Font("Your Weight", Font.PLAIN, 20));

		JLabel l5 = new JLabel("Weight Used");
		l5.setFont(new Font("Weight Used", Font.PLAIN, 20));

		JPanel p1 = new JPanel();
		p1.setLayout(flowLayout);

		JPanel p2 = new JPanel();
		p2.setLayout(flowLayout);

		JPanel p3 = new JPanel();
		p3.setLayout(flowLayout);

		JPanel p4 = new JPanel();
		p4.setLayout(flowLayout);

		JPanel p5 = new JPanel();
		p5.setLayout(flowLayout);

		p1.add(l1);
		p2.add(l2);
		p3.add(l3);
		p4.add(l4);
		p5.add(l5);

		JLabel title = new JLabel("Edit Workout");
		title.setFont(new Font("Edit Workout", Font.PLAIN, 40));

		JPanel tt = new JPanel(); // use FlowLayout
		tt.setBackground(new Color(174, 214, 241));
		tt.add(title);

		b1.add(Box.createVerticalStrut(50));
		b1.add(tt);
		b1.add(p1);
		b1.add(cb);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p2);
		b1.add(field1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p3);
		b1.add(field2);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p4);
		b1.add(field3);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p5);
		b1.add(field4);
		b1.add(Box.createVerticalStrut(50));

		int opt = JOptionPane.showConfirmDialog(null, b1, "Enter Information", JOptionPane.OK_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {

			//creates a workout and adds it to the database
			String type = null, exercise = null;
			Integer duration = null;
			Double weight = null, weightUsed = null;
			try {

				type = cb.getSelectedItem().toString();
				exercise = field1.getText();
				duration = Integer.valueOf(field2.getText());
				weight = Double.valueOf(field3.getText());
				weightUsed = Double.valueOf(field4.getText());

				if(duration.compareTo(Integer.valueOf(1)) < 0 || duration.compareTo(Integer.valueOf(1440)) > 0 || 
						weight.compareTo(Double.valueOf("4.0")) < 0 || weight.compareTo(Double.valueOf("1400.0")) > 0 || 
						weightUsed.compareTo(Double.valueOf("4.0")) < 0 || weight.compareTo(Double.valueOf("1400.0")) > 0) {
					throw new NumberFormatException();
				}else if(exercise.length() < 4) {
					throw new IllegalArgumentException();
				}

			}catch(NumberFormatException e){
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"That's not a valid number!.\nYour Weight and Weight Used must be a number from 4.0 to 1400.0\nDuration must be an integer from 1 to 1440.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			}catch(IllegalArgumentException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Not a valid exercise name!.\nMust be 4 or more characters", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			}
			aWorkout = new Workout(aWorkout.getId(), duration, exercise, type, weight, weightUsed);

			wc.edit(aWorkout);
		}
	}

	public void deleteWorkout(Workout aWorkout) {
		window = new JFrame("Delete Workout");

		int opt = JOptionPane.showConfirmDialog(window, "Warning!\nYou are about to delete the selected Workout.\nIs this what you want?", "Enter Information", JOptionPane.YES_NO_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.NO_OPTION) {

			wc.delete(aWorkout.getId());
		}
	}

	public void destroy() {
		window.dispose();
	}
}

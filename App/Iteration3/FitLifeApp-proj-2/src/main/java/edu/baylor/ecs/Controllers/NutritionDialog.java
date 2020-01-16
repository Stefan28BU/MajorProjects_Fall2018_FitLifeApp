package edu.baylor.ecs.Controllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import edu.baylor.ecs.FLADatabase.MealController;
import edu.baylor.ecs.FitLifeApp.Account;
import edu.baylor.ecs.FitLifeApp.Meal;

public class NutritionDialog {
	private static volatile NutritionDialog instance = null;
	private JFrame window;	
	private MealController mc = MealController.getInstance();
	@SuppressWarnings("deprecation")
	private final Integer LOWER = new Integer(0);
	@SuppressWarnings("deprecation")
	private final Integer UPPER = new Integer(99999);
	private NutritionDialog() {}
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(NutritionDialog.class.getName());
		logger.setLevel(Level.ALL);
	}

	public static NutritionDialog getInstance() {
		if(instance == null) {
			synchronized(NutritionDialog.class) {
				if(instance == null) {
					instance = new NutritionDialog();
				}
			}
		}
		return instance;
	}

	/*
	 * function opens the window to get sleep information from the user
	 */
	public void addMeal(Date day) throws IOException {

		//------------------------------------------------------------------>
		//new add meal window
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

		JTextField field5 = new JTextField();
		field5.setMaximumSize(new Dimension(2000,30));
		field5.setBackground(new Color(232, 248, 245));
		field5.setFont(new Font(null, Font.PLAIN, 20));

		JLabel l1 = new JLabel("Name");
		l1.setFont(new Font("Name", Font.PLAIN, 20));

		JLabel l2 = new JLabel("Carbs");
		l2.setFont(new Font("Carbs", Font.PLAIN, 20));

		JLabel l3 = new JLabel("Fat");
		l3.setFont(new Font("Fat", Font.PLAIN, 20));

		JLabel l4 = new JLabel("Protein");
		l4.setFont(new Font("Protein", Font.PLAIN, 20));

		JLabel l5 = new JLabel("Hydration");
		l5.setFont(new Font("Hydration", Font.PLAIN, 20));

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

		JLabel title = new JLabel("New Meal");
		title.setFont(new Font("New Meal", Font.PLAIN, 40));

		JPanel tt = new JPanel(); // use FlowLayout
		tt.setBackground(new Color(174, 214, 241));
		tt.add(title);

		b1.add(Box.createVerticalStrut(50));
		b1.add(tt);
		b1.add(p1);
		b1.add(field1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p2);
		b1.add(field2);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p3);
		b1.add(field3);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p4);
		b1.add(field4);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p5);
		b1.add(field5);
		b1.add(Box.createVerticalStrut(50));

		int opt = JOptionPane.showConfirmDialog(null, b1, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			String name;
			Integer carbs; 
			Integer fat;
			Integer protein;
			Integer hydration;

			try {
				name = field1.getText();
				carbs = Integer.valueOf(field2.getText());
				fat = Integer.valueOf(field3.getText());
				protein = Integer.valueOf(field4.getText());
				hydration = Integer.valueOf(field5.getText());

				if(name.length() < 4) {
					throw new IllegalArgumentException();
				}else if(carbs.compareTo(LOWER) < 0 || carbs.compareTo(UPPER) > 0 ||
						fat.compareTo(LOWER) < 0 || fat.compareTo(UPPER) > 0 ||
						protein.compareTo(LOWER) < 0 || protein.compareTo(UPPER) > 0 || 
						hydration.compareTo(LOWER) < 0 || hydration.compareTo(UPPER) > 0) {

					throw new NumberFormatException();
				}
			}catch (NumberFormatException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"That's not a valid number!.\n All macros must be from 1 to 99999.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			} catch (IllegalArgumentException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid Name!\nName field must be at least 4 characters.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			}


			Meal aMeal = new Meal(carbs, fat, hydration, name, protein);
			mc.add(Account.getuName(), aMeal, day);

			logger.info(Account.getuName() + "," + name + "," + 
						carbs.toString() + "," + 
						fat.toString() + ","+ 
						protein.toString() + "," + 
						hydration.toString() +"\n");
		}
	}


	public void editMeal(Meal aMeal) {

		//------------------------------------------------------------------>
		//new edit meal window
		//------------------------------------------------------------------>
		window = new JFrame("Edit Meal");

		JTextField field1 = new JTextField(aMeal.getName());
		JTextField field2 = new JTextField(aMeal.getCarbs().toString());
		JTextField field3 = new JTextField(aMeal.getFat().toString());
		JTextField field4 = new JTextField(aMeal.getProtein().toString());
		JTextField field5 = new JTextField(aMeal.getHydration().toString());

		field1.setMaximumSize(new Dimension(2000,30));
		field1.setBackground(new Color(232, 248, 245));
		field1.setFont(new Font(aMeal.getName(), Font.PLAIN, 20));

		field2.setMaximumSize(new Dimension(2000,30));
		field2.setBackground(new Color(232, 248, 245));
		field2.setFont(new Font(aMeal.getCarbs().toString(), Font.PLAIN, 20));

		field3.setMaximumSize(new Dimension(2000,30));
		field3.setBackground(new Color(232, 248, 245));
		field3.setFont(new Font(aMeal.getFat().toString(), Font.PLAIN, 20));

		field4.setMaximumSize(new Dimension(2000,30));
		field4.setBackground(new Color(232, 248, 245));
		field4.setFont(new Font(aMeal.getProtein().toString(), Font.PLAIN, 20));

		field5.setMaximumSize(new Dimension(2000,30));
		field5.setBackground(new Color(232, 248, 245));
		field5.setFont(new Font(aMeal.getHydration().toString(), Font.PLAIN, 20));


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

		JLabel l1 = new JLabel("Name");
		l1.setFont(new Font("Name", Font.PLAIN, 20));

		JLabel l2 = new JLabel("Carbs");
		l2.setFont(new Font("Carbs", Font.PLAIN, 20));

		JLabel l3 = new JLabel("Fat");
		l3.setFont(new Font("Fat", Font.PLAIN, 20));

		JLabel l4 = new JLabel("Protein");
		l4.setFont(new Font("Protein", Font.PLAIN, 20));

		JLabel l5 = new JLabel("Hydration");
		l5.setFont(new Font("Hydration", Font.PLAIN, 20));

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

		JLabel title = new JLabel("New Meal");
		title.setFont(new Font("New Meal", Font.PLAIN, 40));

		JPanel tt = new JPanel(); // use FlowLayout
		tt.setBackground(new Color(174, 214, 241));
		tt.add(title);

		b1.add(Box.createVerticalStrut(50));
		b1.add(tt);
		b1.add(p1);
		b1.add(field1);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p2);
		b1.add(field2);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p3);
		b1.add(field3);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p4);
		b1.add(field4);
		b1.add(Box.createVerticalStrut(10));
		b1.add(p5);
		b1.add(field5);
		b1.add(Box.createVerticalStrut(50));

		int opt = JOptionPane.showConfirmDialog(null, b1, "Enter Information", JOptionPane.OK_CANCEL_OPTION);

		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.CLOSED_OPTION) {
			String name;
			Integer carbs; 
			Integer fat;
			Integer protein;
			Integer hydration;

			try {
				name = field1.getText();
				carbs = Integer.valueOf(field2.getText());
				fat = Integer.valueOf(field3.getText());
				protein = Integer.valueOf(field4.getText());
				hydration = Integer.valueOf(field5.getText());

				if(name.length() < 4) {
					throw new IllegalArgumentException();
				}else if(carbs.compareTo(LOWER) < 0 || carbs.compareTo(UPPER) > 0 ||
						fat.compareTo(LOWER) < 0 || fat.compareTo(UPPER) > 0 ||
						protein.compareTo(LOWER) < 0 || protein.compareTo(UPPER) > 0 || 
						hydration.compareTo(LOWER) < 0 || hydration.compareTo(UPPER) > 0) {

					throw new NumberFormatException();
				}
			}catch (NumberFormatException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"That's not a valid number!.\n All macros must be from 1 to 99999.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			} catch (IllegalArgumentException e) {
				window.dispose();
				JOptionPane.showMessageDialog(new JFrame(),
						"Invalid Name!\nName field must be at least 4 characters.", "Failed",
						JOptionPane.ERROR_MESSAGE);
				
				logger.log(Level.SEVERE, e.getMessage(), e);

				return;
			}

			aMeal = new Meal(aMeal.getId(), carbs, fat, hydration, name, protein);
			mc.edit(aMeal);
		}
	}


	public void destroy() {
		window.dispose();
	}


	public void deleteMeal(Meal aMeal) {
		window = new JFrame("Delete Meal");

		int opt = JOptionPane.showConfirmDialog(window, "Warning!\nYou are about to delete the selected Meal.\nIs this what you want?", "Enter Information", JOptionPane.YES_NO_CANCEL_OPTION);
		if(opt != JOptionPane.CANCEL_OPTION && opt != JOptionPane.NO_OPTION) {
			mc.delete(aMeal.getId());
		}
	}
}

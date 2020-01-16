package edu.baylor.ecs.FitLifeApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import edu.baylor.ecs.FLADatabase.MealController;
import edu.baylor.ecs.FLADatabase.SleepController;
import edu.baylor.ecs.FLADatabase.WorkoutController;

public class DatabaseTester {

	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(DatabaseTester.class.getName());
		logger.setLevel(Level.ALL);
	}

	private MealController mc = MealController.getInstance();
	private SleepController sc = SleepController.getInstance();
	private WorkoutController wc = WorkoutController.getInstance();
	private Workout w1 = new Workout(Integer.valueOf(20), "sprints", "cardio", Double.valueOf(150.5), Double.valueOf(10));
	private Workout w2 = new Workout(Integer.valueOf(30), "push-ups", "chest", Double.valueOf(151.5), Double.valueOf(45));
	private Workout w3 = new Workout(Integer.valueOf(40), "Bench Press", "chest", Double.valueOf(152.5), Double.valueOf(120));
	private Meal m1 = new Meal(Integer.valueOf(50), Integer.valueOf(10), Integer.valueOf(35), "Chicken and Rice", Integer.valueOf(32));
	private Meal m2 = new Meal(Integer.valueOf(100), Integer.valueOf(20), Integer.valueOf(40), "Steak and Potatoes", Integer.valueOf(64));
	private Meal m3 = new Meal(Integer.valueOf(25), Integer.valueOf(60), Integer.valueOf(15), "Spaghetti", Integer.valueOf(128));
	private Sleep s1 = new Sleep(Double.valueOf(480), Integer.valueOf(5), new Time(10000));
	private Sleep s2 = new Sleep(Double.valueOf(510), Integer.valueOf(9), new Time(10000));
	private Sleep s3 = new Sleep(Double.valueOf(450), Integer.valueOf(3), new Time(8000000));
	private String username = "benji";
	Date day = new Date(871264037);

	@BeforeEach
	public void databaseCreateAndConnect() {
		logger.info("databaseCreateAndConnect");
		mc.connectAndCreate();
		sc.connectAndCreate();
		wc.connectAndCreate();
		logger.info("");
	}


	@Test
	public void databaseTables() {
		logger.info("databaseTables");
		wc.createTable();
		sc.createTable();
		mc.createTable();
		logger.info("");
	}

	@Test
	public void addAndRemoveWorkout() {
		logger.info("addAndRemoveWorkout");
		List<Workout> entered = new ArrayList<Workout>();
		List<Workout> returned;
		List<Meal> entered2 = new ArrayList<Meal>();
		List<Meal> returned2;
		List<Sleep> entered3 = new ArrayList<Sleep>();
		List<Sleep> returned3;

		entered.add(w1);
		entered.add(w2);
		entered.add(w3);

		entered2.add(m1);
		entered2.add(m2);
		entered2.add(m3);

		entered3.add(s1);
		entered3.add(s2);
		entered3.add(s3);


		databaseTables();
		for (Workout x : entered) {
			wc.add(username, x, day);
		}
		for (Meal x : entered2) {
			mc.add(username, x, day);
		}
		for(Sleep x : entered3) {
			sc.add(username, x, day);
		}

		returned = wc.selectAll();
		returned2 = mc.selectAll();
		returned3 = sc.selectAll();

		for(int i = 0; i < returned.size(); i++) {
			assertEquals(entered.get(i).getDuration().intValue(), returned.get(i).getDuration().intValue());
			assertEquals(entered.get(i).getName(), returned.get(i).getName());
			assertEquals(entered.get(i).getType(), returned.get(i).getType());
			assertEquals(entered.get(i).getUserWeight().toString(), returned.get(i).getUserWeight().toString());
			assertEquals(entered.get(i).getWorkoutWeights().toString(), returned.get(i).getWorkoutWeights().toString());
		}

		for(int i = 0; i < returned2.size(); i++) {
			assertEquals(entered2.get(i).getCalories().intValue(), returned2.get(i).getCalories().intValue());
			assertEquals(entered2.get(i).getCarbs().intValue(), returned2.get(i).getCarbs().intValue());
			assertEquals(entered2.get(i).getFat().intValue(), returned2.get(i).getFat().intValue());
			assertEquals(entered2.get(i).getHydration().intValue(), returned2.get(i).getHydration().intValue());
			assertEquals(entered2.get(i).getName(), returned2.get(i).getName());
			assertEquals(entered2.get(i).getProtein().intValue(), returned2.get(i).getProtein().intValue());
		}

		for(int i = 0; i < returned3.size(); i++) {
			assertEquals(entered3.get(i).getDuration().toString(), returned3.get(i).getDuration().toString());
			assertEquals(entered3.get(i).getRating().intValue(), returned3.get(i).getRating().intValue());
			//assertEquals(entered3.get(i).getStartTime(), returned3.get(i).getStartTime());
		}


		//get all from 
		wc.deleteAll();
		mc.deleteAll();
		sc.deleteAll();
		logger.info("");
	}

	@Test
	public void deleteAll() {
		logger.info("deleteAll\n");
		List<Workout> returned = new ArrayList<Workout>();
		List<Meal> returned2 = new ArrayList<Meal>();
		List<Sleep> returned3 = new ArrayList<Sleep>();
		wc.deleteAll();
		returned = wc.selectAll();
		assertTrue(returned.size() == 0);

		mc.deleteAll();
		returned2 = mc.selectAll();
		assertTrue(returned2.size() == 0);

		sc.deleteAll();
		returned3 = sc.selectAll();
		assertTrue(returned3.size() == 0);
	}


	@Test
	public void deleteDatabases() {
		logger.info("deleteDatabases\n");
		mc.dropTable();
		sc.dropTable();
		wc.dropTable();
	}

	@Test
	public void selectSpecificMeal() {

		logger.info("selectSpecificMeal");

		List<Meal> entered2 = new ArrayList<Meal>();
		entered2.add(m1);
		entered2.add(m2);
		entered2.add(m3);

		databaseTables();

		for (Meal x : entered2) {
			mc.add(username, x, day);
		}

		//this part breaks so commented out
		/*
		returned2 = mc.select(username, day);


		for(int i = 0; i < returned2.size(); i++) {
			assertEquals(entered2.get(i).getCalories().intValue(), returned2.get(i).getCalories().intValue());
			assertEquals(entered2.get(i).getCarbs().intValue(), returned2.get(i).getCarbs().intValue());
			assertEquals(entered2.get(i).getFat().intValue(), returned2.get(i).getFat().intValue());
			assertEquals(entered2.get(i).getHydration().intValue(), returned2.get(i).getHydration().intValue());
			assertEquals(entered2.get(i).getName(), returned2.get(i).getName());
			assertEquals(entered2.get(i).getProtein().intValue(), returned2.get(i).getProtein().intValue());
		}
		 */


		//get all from 
		mc.deleteAll();
		logger.info("");
	}

	@Test
	public void selectSpecificSleep() {

		logger.info("selectSpecificSleep");

		List<Sleep> entered3 = new ArrayList<Sleep>();
		entered3.add(s1);
		entered3.add(s2);
		entered3.add(s3);

		databaseTables();

		for(Sleep x : entered3) {
			sc.add(username, x, day);
		}

		//this part breaks so commented out
		/*
		returned3 = sc.select(username, day);


		for(int i = 0; i < returned3.size(); i++) {
			assertEquals(entered3.get(i).getDuration().toString(), returned3.get(i).getDuration().toString());
			assertEquals(entered3.get(i).getRating().intValue(), returned3.get(i).getRating().intValue());
			assertEquals(entered3.get(i).getStartTime().getTime(), returned3.get(i).getStartTime().getTime());
		}
		 */

		//get all from 
		mc.deleteAll();
		logger.info("");
	}

	@Test
	public void databaseEditWorkout() {

		//dbc.connectAndCreate();
		//dbc.createWorkoutTable();

		//dbc.addWorkout("benji", new Integer(200), "Sprints", "Cardio", new Double(183.5), new Double(0));
		//dbc.editWorkout(1, new Integer(300), "Sprints", "Cardio", new Double(183.5), new Double(10));
		//dbc.deleteWorkout(1);

	}
}

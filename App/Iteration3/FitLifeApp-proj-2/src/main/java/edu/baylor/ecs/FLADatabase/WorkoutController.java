package edu.baylor.ecs.FLADatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.baylor.ecs.FitLifeApp.LogItem;
import edu.baylor.ecs.FitLifeApp.Workout;

//make singleton
public final class WorkoutController extends DatabaseController{
	 private static volatile WorkoutController instance = null;
	
	 private WorkoutController(){}
	 
	 private static Logger logger = null;

		static {
			System.setProperty("java.util.logging.SimpleFormatter.format",
					"[%1$tF %1$tT] [%4$-7s] %5$s %n");
			logger = Logger.getLogger(WorkoutController.class.getName());
			logger.setLevel(Level.ALL);
		}
	 
	 public static WorkoutController getInstance() {
		 if(instance == null) {
			 synchronized(WorkoutController.class) {
				 if(instance == null) {
					 instance = new WorkoutController();
				 }
			 }
		 }
		 return instance;
	 }
	
	
	/* Creates the workoutTable that needs to be used for all the users' workouts
	 * This only needs to be created once at the beginning of execution the first time
	 * However it will not break anything if it is run after the table has already been made
	 * */
	public void createTable() {
		String createTableSQL = "CREATE TABLE Workout(" + "userName VARCHAR(255) NOT NULL, " + "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "duration INT NOT NULL, " + "name VARCHAR(255) NOT NULL, "
				+ "type VARCHAR(255) NOT NULL, "+ "userWeight FLOAT NOT NULL, " + "workoutWeight FLOAT NOT NULL, " + "day DATE NOT NULL, " + "PRIMARY KEY (id) " + ")";
		try (Connection dbConnection = super.getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(createTableSQL);
			// execute the SQL statement
			logger.info(createTableSQL);
			logger.info("Table \"Workout\" is created!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}
	
	
	/* Inserts a record into the Workout table
	 * */
	public void add(String username, Workout aWorkout, Date day) {
		String insertTableSQL = "INSERT INTO Workout" + "(userName, duration, name, type, userWeight, workoutWeight, day) " + "VALUES"
				+ "('"+ username + "'," + aWorkout.getDuration().intValue() + ",'" + aWorkout.getName() + "','" + aWorkout.getType() + 
				"'," + aWorkout.getUserWeight().doubleValue() + "," + aWorkout.getWorkoutWeights().doubleValue() + ",?" + ")";
		
		try (Connection dbConnection = getDBConnection();
				PreparedStatement statement = dbConnection.prepareStatement(insertTableSQL);){
			
			statement.setDate(1, new java.sql.Date(day.getTime()));
			logger.info(insertTableSQL);
			statement.executeUpdate();
			logger.info("Record is inserted into Workout table!");
			
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}
	
	
	/*
	 * edits a workout already existing in the workout table by the given id
	 * Doesn't change the username
	 * */
	public void edit(Workout aWorkout) {
			String updateTableSQL = "UPDATE Workout" + " SET duration = "  + aWorkout.getDuration().intValue() + ", name = '" + aWorkout.getName() + 
					"', type = '" + aWorkout.getType() + "', userWeight = " + aWorkout.getUserWeight().doubleValue() + ", workoutWeight = " + aWorkout.getWorkoutWeights().doubleValue() + 
					" WHERE id = " + aWorkout.getId().intValue();
			try (Connection dbConnection = getDBConnection();
					Statement statement = dbConnection.createStatement();){
				
				logger.info(updateTableSQL);
				statement.execute(updateTableSQL);
				logger.info("Record is updated to Workout table!");
			} catch (SQLException e) {
				logger.severe(e.getMessage());
			}
	}
	
	
	/*
	 * Deletes a record from the Workout table specified by its id
	 * */
	public void delete(Integer id) {
		
		String deleteTableSQL = "DELETE FROM Workout WHERE id = " + id.intValue();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
		
			logger.info(deleteTableSQL);
			statement.execute(deleteTableSQL);
			logger.info("Record is deleted from Workout table!");
			
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}
	
	
	public void deleteAll() {
		String deleteTableSQL = "DELETE FROM Workout";
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
		
			logger.info(deleteTableSQL);
			statement.execute(deleteTableSQL);
			logger.info("All Records deleted from Workout table!");
			
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}
	
	
	/* This function selects all records from the Workout table	
	 * 
	 */
	public List<Workout> selectAll() {
		String deleteTableSQL = "SELECT * FROM Workout";
		List<Workout> row = new ArrayList<Workout>();
		try ( Connection dbConnection = getDBConnection();
			  Statement statement = dbConnection.createStatement();){
			
			logger.info(deleteTableSQL);
			ResultSet rs = statement.executeQuery(deleteTableSQL);
			logger.info("Record selected from Workout table!");
			
			//loops through and return as a list of strings
			if(rs.next() == false) {
				logger.info("No results from Workout table");
			}else {
				do {
					Workout aWorkout = new Workout(Integer.valueOf(rs.getInt("id")), 
							Integer.valueOf(rs.getInt("duration")), rs.getString("name"), 
							rs.getString("type"), Double.valueOf(rs.getDouble("userWeight")), 
							Double.valueOf(rs.getDouble("workoutWeight")));
					
					row.add(aWorkout);
				}while(rs.next());	
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
		return row;
	}
	
	
	public List<LogItem> select(String username, Date day){
		String selectTableSQL = "SELECT * FROM Workout WHERE userName = '" + username + "' AND day = ?";
		List<LogItem> row = new ArrayList<LogItem>();
		try ( Connection dbConnection = getDBConnection();
			  PreparedStatement statement = dbConnection.prepareStatement(selectTableSQL);){
			
			statement.setDate(1, new java.sql.Date(day.getTime()));
			logger.info(selectTableSQL);
			ResultSet rs = statement.executeQuery();
			logger.info("Records selected from Workout table!");
			
			//loops through and return as a list of strings
			if(rs.next() == false) {
				logger.info("No results from Workout table");
			}else {
				do {
					Workout aWorkout = new Workout(Integer.valueOf(rs.getInt("id")), 
							Integer.valueOf(rs.getInt("duration")), rs.getString("name"), 
							rs.getString("type"), Double.valueOf(rs.getDouble("userWeight")), 
							Double.valueOf(rs.getDouble("workoutWeight")));
					
					row.add(aWorkout);
				}while(rs.next());	
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
		return row;
	}
	
	public void dropTable(){
		String dropTableSQL = "DROP TABLE Workout";
		try ( Connection dbConnection = getDBConnection();
			  Statement statement = dbConnection.createStatement();){
			
			logger.info(dropTableSQL);
			statement.execute(dropTableSQL);
			logger.info("Dropped Workout table!");
			
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
		
	}

	public void add(String string, Integer duration, String name, String type, Double userWeight,
			Double workoutWeights) {
		// TODO Auto-generated method stub
	}
}

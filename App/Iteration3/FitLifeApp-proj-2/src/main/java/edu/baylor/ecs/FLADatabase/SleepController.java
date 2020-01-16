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
import edu.baylor.ecs.FitLifeApp.Sleep;

public final class SleepController extends DatabaseController{


	private static volatile SleepController instance = null;

	private SleepController(){}
	
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(SleepController.class.getName());
		logger.setLevel(Level.ALL);
	}

	public static SleepController getInstance() {
		if(instance == null) {
			synchronized(SleepController.class) {
				if(instance == null) {
					instance = new SleepController();
				}
			}
		}
		return instance;
	}

	/* Creates the Meal Table that needs to be used for all the users' Meals
	 * This only needs to be created once at the beginning of execution the first time
	 * However it will not break anything if it is run after the table has already been made
	 * */
	public void createTable() {
		String createTableSQL = "CREATE TABLE Sleep(" + "userName VARCHAR(255) NOT NULL, " + "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "duration FLOAT NOT NULL, " + " rating INT NOT NULL, "+ "startTime TIME NOT NULL," + "day DATE NOT NULL," + "PRIMARY KEY (id) " + ")";
		try (Connection dbConnection = super.getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(createTableSQL);
			statement.execute(createTableSQL);
			logger.info("Table \"Sleep\" is created!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}


	/* Inserts a record into the Sleep table
	 * */
	public void add(String username, Sleep aSleep, Date day) {
		String insertTableSQL = "INSERT INTO Sleep" + "(userName, duration, rating, startTime, day) " + "VALUES"
				+ "('"+ username + "'," + aSleep.getDuration().doubleValue() + ", " + aSleep.getRating().intValue() + ", ?" + ", ?" + ")";
		try (Connection dbConnection = getDBConnection();
				PreparedStatement statement = dbConnection.prepareStatement(insertTableSQL);){

			statement.setDate(2, new java.sql.Date(day.getTime()));
			statement.setTime(1,aSleep.getStartTime());
			logger.info(insertTableSQL);
			statement.executeUpdate();
			logger.info("Record is inserted into Sleep table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}


	/*
	 * edits a meal already existing in the Sleep table by the given id
	 * Doesn't change the username
	 * */
	public void edit(Sleep aSleep) {
		//update the table
		String updateTableSQL = "UPDATE Sleep" + " SET duration = "  + aSleep.getDuration().doubleValue() + ", rating = " + aSleep.getRating().intValue() + 
				", startTime = ?" + " WHERE id = " + aSleep.getId().intValue();
		try (Connection dbConnection = getDBConnection();
				PreparedStatement statement = dbConnection.prepareStatement(updateTableSQL);){

			statement.setTime(1,aSleep.getStartTime());
			logger.info(updateTableSQL);
			statement.execute();
			logger.info("Record is updated to Sleep table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}


	/*
	 * Deletes a record from the Sleep table specified by its id
	 * */
	public void delete(Integer id) {

		String deleteTableSQL = "DELETE FROM Sleep WHERE id = " + id.intValue();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(deleteTableSQL);
			statement.execute(deleteTableSQL);
			logger.info("Record is deleted from Sleep table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}

	public void deleteAll() {

		String deleteTableSQL = "DELETE FROM Sleep";
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(deleteTableSQL);
			statement.execute(deleteTableSQL);
			logger.info("All Records deleted from Sleep table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}


	/* This function selects all records from the sleep table	
	 * 
	 */
	public List<Sleep> selectAll() {
		String selectTableSQL = "SELECT * FROM Sleep";
		List<Sleep> row = new ArrayList<Sleep>();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(selectTableSQL);
			ResultSet rs = statement.executeQuery(selectTableSQL);
			logger.info("Record selected from Sleep table!");

			//loops through and return as a list of strings
			if(rs.next() == false) {
				logger.info("No results from Sleep table");
			}else {
				do {
					Sleep aSleep= new Sleep(Integer.valueOf(rs.getInt("id")), Double.valueOf(rs.getDouble("duration")), Integer.valueOf(rs.getString("rating")), 
							rs.getTime("startTime"));

					row.add(aSleep);
				}while(rs.next());	
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return row;
	}


	public List<LogItem> select(String username, Date day){
		String selectTableSQL = "SELECT * FROM Sleep WHERE userName = '" + username + "' AND day = ?";
		List<LogItem> row = new ArrayList<LogItem>();
		try ( Connection dbConnection = getDBConnection();
				PreparedStatement statement = dbConnection.prepareStatement(selectTableSQL);){

			statement.setDate(1, new java.sql.Date(day.getTime()));
			System.out.println(selectTableSQL);
			ResultSet rs = statement.executeQuery();
			System.out.println("Records selected from Sleep table!");

			//loops through and return as a list of strings
			if(rs.next() == false) {
				System.out.print("No results from Sleep table");
			}else {
				do {
					Sleep aSleep= new Sleep(Integer.valueOf(rs.getInt("id")), Double.valueOf(rs.getDouble("duration")), Integer.valueOf(rs.getString("rating")), 
							rs.getTime("startTime"));

					row.add(aSleep);
				}while(rs.next());	
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return row;
	}


	public void dropTable(){
		String dropTableSQL = "DROP TABLE Sleep";
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){

			logger.info(dropTableSQL);
			statement.execute(dropTableSQL);
			logger.info("Dropped Sleep table!");

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}
}

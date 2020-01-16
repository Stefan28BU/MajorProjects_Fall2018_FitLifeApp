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
import edu.baylor.ecs.FitLifeApp.Meal;

//make singleton
public final class MealController extends DatabaseController {

	private static volatile MealController instance = null;

	private MealController(){}
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(MealController.class.getName());
		logger.setLevel(Level.ALL);
	}

	public static MealController getInstance() {
		if(instance == null) {
			synchronized(MealController.class) {
				if(instance == null) {
					instance = new MealController();
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
		String createTableSQL = "CREATE TABLE Meal(" + "userName VARCHAR(255) NOT NULL, " + "id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
				+ "calories INT NOT NULL, " + "name VARCHAR(255) NOT NULL, "
				+ " carbs INT NOT NULL, "+ "fat INT NOT NULL, " + "protein INT NOT NULL, " + "hydration INT NOT NULL, " + "day DATE NOT NULL, " + "PRIMARY KEY (id) " + ")";
		try (Connection dbConnection = super.getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(createTableSQL);
			statement.execute(createTableSQL);
			logger.info("Table \"Meal\" is created!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}

	/* Inserts a record into the Meal table
	 * */
	public void add(String username, Meal aMeal, Date day) {
		String insertTableSQL = "INSERT INTO Meal" + "(userName, calories, name, carbs, fat, protein, hydration, day) " + "VALUES"
				+ "('"+ username + "'," + aMeal.getCalories().intValue() + ",'" + aMeal.getName() + "'," + aMeal.getCarbs().intValue() + "," + aMeal.getFat().intValue() + "," + aMeal.getProtein().intValue() + "," + aMeal.getHydration().intValue() +",?" + ")";
		try (Connection dbConnection = getDBConnection();
				PreparedStatement statement = dbConnection.prepareStatement(insertTableSQL);){

			statement.setDate(1, new java.sql.Date(day.getTime()));
			logger.info(insertTableSQL);
			statement.executeUpdate();
			logger.info("Record is inserted into Meal table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}


	/*
	 * edits a meal already existing in the Meal table by the given id
	 * Doesn't change the username
	 * */
	public void edit(Meal aMeal) {
		//update the table
		String updateTableSQL = "UPDATE Meal" + " SET calories = "  + aMeal.getCalories().intValue() + ", name = '" + aMeal.getName() + 
				"', carbs = " + aMeal.getCarbs().intValue() + ", fat = " + aMeal.getFat().intValue() + ", protein = " + aMeal.getProtein().intValue() + ", hydration = " + aMeal.getHydration().intValue() +
				" WHERE id = " + aMeal.getId().intValue();
		try (Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(updateTableSQL);
			statement.execute(updateTableSQL);
			logger.info("Record is updated to Meal table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}



	/*
	 * Deletes a record from the Meal table specified by its id
	 * */
	public void delete(Integer id) {
		String deleteTableSQL = "DELETE FROM Meal WHERE id = " + id.intValue();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(deleteTableSQL);
			statement.execute(deleteTableSQL);
			logger.info("Record is deleted from Meal table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}


	public void deleteAll() {
		String deleteTableSQL = "DELETE FROM Meal";
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){

			logger.info(deleteTableSQL);
			statement.execute(deleteTableSQL);
			logger.info("ALL Record is deleted from Meal table!");
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}



	/* This function selects all records from the meal table	
	 * 
	 */
	public List<Meal> selectAll() {
		String selectAllTableSQL = "SELECT * FROM Meal";
		List<Meal> row = new ArrayList<Meal>();
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(selectAllTableSQL);
			ResultSet rs = statement.executeQuery(selectAllTableSQL);
			logger.info("Record selected from Meal table!");

			//loops through and return as a list of strings
			if(rs.next() == false) {
				logger.info("No results from Meal table");
			}else {
				do {
					Meal aMeal= new Meal(Integer.valueOf(rs.getInt("id")), Integer.valueOf(rs.getInt("carbs")), Integer.valueOf(rs.getString("fat")), 
							Integer.valueOf(rs.getString("hydration")), rs.getString("name"), Integer.valueOf(rs.getInt("protein")));

					row.add(aMeal);
				}while(rs.next());	
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return row;
	}


	public List<LogItem> select(String username, Date day){
		String selectTableSQL = "SELECT * FROM Meal WHERE userName = '" + username + "' AND day = ?";
		List<LogItem> row = new ArrayList<LogItem>();
		try ( Connection dbConnection = getDBConnection();
				PreparedStatement statement = dbConnection.prepareStatement(selectTableSQL);){

			statement.setDate(1, new java.sql.Date(day.getTime()));
			logger.info(selectTableSQL);
			ResultSet rs = statement.executeQuery();
			logger.info("Records selected from Meal table!");

			//loops through and return as a list of strings
			if(rs.next() == false) {
				logger.info("No results from Meal table");
			}else {
				do {
					Meal aMeal= new Meal(Integer.valueOf(rs.getInt("id")), Integer.valueOf(rs.getInt("carbs")), Integer.valueOf(rs.getString("fat")), 
							Integer.valueOf(rs.getString("hydration")), rs.getString("name"), Integer.valueOf(rs.getInt("protein")));

					row.add(aMeal);
				}while(rs.next());	
			}
		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}

		return row;
	}


	public void dropTable(){
		String dropTableSQL = "DROP TABLE Meal";
		try ( Connection dbConnection = getDBConnection();
				Statement statement = dbConnection.createStatement();){
			
			logger.info(dropTableSQL);
			statement.execute(dropTableSQL);
			logger.info("Dropped Meal table!");

		} catch (SQLException e) {
			logger.severe(e.getMessage());
		}
	}
}

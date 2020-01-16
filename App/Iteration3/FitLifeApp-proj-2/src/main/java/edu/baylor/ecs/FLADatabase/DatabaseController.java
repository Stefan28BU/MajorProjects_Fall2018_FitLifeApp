package edu.baylor.ecs.FLADatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.iapi.error.StandardException;

public class DatabaseController {
	protected static final String DB_CONNECTION = "jdbc:derby:FitLifeApp;";
	protected static final String DB_USER = null;
	protected static final String DB_PASSWORD = null;
	
	private static Logger logger = null;

	static {
		System.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tF %1$tT] [%4$-7s] %5$s %n");
		logger = Logger.getLogger(DatabaseController.class.getName());
		logger.setLevel(Level.ALL);
	}
	
	
	/* method is used to connect to and create a database
	 * Realistically this method only needs to be called once at the 
	 * beginning of execution before any login or account creation can be done
	 */
	public void connectAndCreate() {
		logger.info("-------- Derby JDBC Connection Testing ------------");
		
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			logger.severe("Where is your Derby JDBC Driver?");
			logger.severe(e.getMessage());
			
			return;
		}
		logger.info("Derby JDBC Driver Registered!");
		
		try (Connection connection = DriverManager.getConnection("jdbc:derby:FitLifeApp;create=true", "", "");){
			
			if (connection != null) {
				logger.info("All good!");
			} else {
				logger.severe("Failed to make connection!");
			}
		} catch (SQLException e) {
			logger.severe("Connection Failed! Check output console");
			logger.severe(e.getMessage());
			
			return;
		}
		
	}
	
	/*	Method is used to conenct to the existing database after the connection
	 * 	has been closed.
	 * 
	 */
	public void connect() {
		logger.info("-------- Derby JDBC Connection Testing ------------");
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			logger.severe("Where is your Derby JDBC Driver?");
			logger.severe(e.getMessage());
			
			return;
		}
		logger.info("Derby JDBC Driver Registered!");
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:derby:FitLifeApp;", "", "");
			if (connection != null) {
				logger.info("You made it, take control your database now!");
			} else {
				logger.severe("Failed to make connection!");
			}
		} catch (SQLException e) {
			logger.severe("Connection Failed! Check output console");
			logger.severe(e.getMessage());
			
			return;
		} finally {
			if (connection != null) {
				try {
					if (!connection.isClosed()) {
						connection.close();
					}
				} catch (SQLException e) {
					logger.severe(e.getMessage());
				}
			}
		}
	}
	
	
	
	/*	This method is used in all queries to get the
	 * 	Connection to the database
	 */
	protected Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			logger.severe(e.getMessage());
			System.exit(0);
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			logger.severe(e.getMessage());
			
			if(e.getCause() instanceof StandardException) {
				logger.severe(e.getCause().toString());
				logger.severe("Most likely connection open already (and new cannot be opened)");
			}
			System.exit(0);
		}
		return dbConnection;
	}
}

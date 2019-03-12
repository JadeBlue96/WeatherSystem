package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;

public class DBConnector {
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());

	private Connection conn = null;
	
	public DBConnector(String dbUrl, String user, String pw, boolean ssl) 
	{
		Properties properties = new Properties();
		properties.setProperty( "user", user );
		properties.setProperty( "password", pw );
		if(ssl) properties.setProperty( "ssl", "true" );

		try 
		{
			conn = DriverManager.getConnection( dbUrl, properties );
		}
		catch (SQLException e) 
		{
			logger.log(Level.SEVERE, this.getClass().getName() + "Failed to establish connection to database.");
			return;
		}
	}
	
	public Connection getConnection()
	{
		return conn;
	}
	
	public void close() 
	{	
		try
		{
			if( conn != null )
			{
				conn.close();
			}
		}
		catch (SQLException e) 
		{
			logger.log(Level.SEVERE, this.getClass().getName() + "Error during closing connection to database.");
			return;
		}
	}
		
}

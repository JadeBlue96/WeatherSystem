package com.isoft.base.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.isoft.base.logging.PropLogger;
import com.isoft.base.property.CityPropReader;

public class DBConnector {
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    private static volatile DBConnector db_inst = new DBConnector();

    private Connection conn = null;
    
    private DBConnector() 
    {
        Properties properties = new Properties();
        CityPropReader db_credentials = new CityPropReader("../resources/db_credentials/db_cred.properties");
        final String username = db_credentials.getPropertyValue("username");
        final String password = db_credentials.getPropertyValue("password");
        final String url = db_credentials.getPropertyValue("url");
        
        properties.setProperty("user", username );
        properties.setProperty("password", password );

        try 
        {
            conn = DriverManager.getConnection(url, properties);
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
    
    // for thread-safe CRUD operations
    public static DBConnector getInstance() 
    {
        if(db_inst == null)
        {
            synchronized (DBConnector.class)
            {
                if(db_inst == null)
                {
                    db_inst = new DBConnector();
                }
            }
        }
        return db_inst;
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

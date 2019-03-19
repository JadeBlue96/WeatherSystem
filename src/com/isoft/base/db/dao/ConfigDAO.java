package com.isoft.base.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.isoft.base.db.DBConnector;
import com.isoft.base.db.model.ConfigData;
import com.isoft.base.db.model.WeatherData;
import com.isoft.base.logging.PropLogger;

public class ConfigDAO {
    
    DBConnector db = null;
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    private static final String SQL_DELETE_CONFIG = "DELETE FROM \"Config\" WHERE config_id = ?\n";
    private static final String SQL_INSERT_CONFIG = "INSERT INTO \"Config\"(city, country, site) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_SELECT_CONFIG_ALL = "SELECT * from \"Config\"";
    private static final String SQL_UPDATE_CONFIG = "UPDATE \"Config\" SET city = ?, country = ?, site = ? WHERE config_id = ?\n";
    
    public ConfigDAO(DBConnector db)
    {
        this.db = db;
    }
    
    public int deleteConfig(String SQL_CONFIG, WeatherData weather_data, long config_id)
    {
        int affectedRows = 0;
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_CONFIG,Statement.RETURN_GENERATED_KEYS)) 
            {
                pstmt.setLong(1, config_id);
                affectedRows = pstmt.executeUpdate();
            } 
            catch (SQLException ex) 
            {
                logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                return 0;
            } 
        }
        return affectedRows;
    }
    
    public Long insertConfig(String SQL_CONFIG, WeatherData weather_data, long config_id)
    {
        if (weather_data != null)
        {
         try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_CONFIG,Statement.RETURN_GENERATED_KEYS)) 
         {
                   pstmt.setString(1, weather_data.getConfig_data().getCity());
                pstmt.setString(2, weather_data.getConfig_data().getCountry());
                pstmt.setString(3, weather_data.getConfig_data().getSite());

                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) 
                {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) 
                    {
                        if (rs.next()) 
                        {
                            config_id = rs.getLong("config_id");
                        }
                    } 
                    catch (SQLException ex) {
                        logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                        return null;
                    }
                }
         } 
         catch (SQLException ex) 
         {
                logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                return null;
         } 
         
        }
        
         return config_id;
    }
    
    public List<ConfigData> getConfigData()
    {
        List<ConfigData> conf_list = new ArrayList<>();
        ConfigData conf_data = new ConfigData();
        try (Statement stmt = db.getConnection().createStatement()) 
        {
            ResultSet rs = stmt.executeQuery(SQL_SELECT_CONFIG_ALL);
            while (rs.next())
            {
                    long id = rs.getLong("config_id");
                    String city = rs.getString("city");
                    String country = rs.getString("country");
                    String site = rs.getString("site");
                    conf_data = new ConfigData(id, city, country, site);
                    conf_list.add(conf_data);
            }
        } 
        catch (SQLException e) 
        {
           logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
           return null;
        }
        return conf_list;
    }
    
    public Long updateConfig(String SQL_CONFIG, WeatherData weather_data, long config_id)
    {
        long affectedRows = 0;
        
        if (weather_data != null)
        {
         try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_CONFIG,Statement.RETURN_GENERATED_KEYS)) 
         {       
                pstmt.setString(1, weather_data.getConfig_data().getCity());
                pstmt.setString(2, weather_data.getConfig_data().getCountry());
                pstmt.setString(3, weather_data.getConfig_data().getSite());
                pstmt.setLong(4, config_id);
     
                affectedRows = pstmt.executeUpdate();
         } 
         catch (SQLException ex) 
         {
                logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                return null;
         }
        }
        
        return affectedRows;
    }
}

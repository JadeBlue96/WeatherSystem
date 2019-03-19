package com.isoft.base.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.isoft.base.logging.PropLogger;
import com.isoft.base.model.WeatherData;

public class DBWeatherDeleter {

DBConnector db = null;
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    private static final String SQL_DELETE_WIND = "DELETE FROM \"Wind\" WHERE wind_id = ?\n";
    private static final String SQL_DELETE_CONFIG = "DELETE FROM \"Config\" WHERE config_id = ?\n";
    private static final String SQL_DELETE_ADDITIONAL  = "DELETE FROM \"Additional\" WHERE add_id = ?\n";
    private static final String SQL_DELETE_WEATHER = "DELETE FROM \"Weather\" WHERE weather_id = ?\n";
    

    public DBWeatherDeleter(DBConnector db) {
        this.db = db;
    }
    
    public int deleteWind(String SQL_WIND, WeatherData weather_data, long wind_id)
    {
        int affectedRows = 0;
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WIND,Statement.RETURN_GENERATED_KEYS)) 
            {
                pstmt.setLong(1, wind_id);
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
    
    public int deleteAdditional(String SQL_ADD, WeatherData weather_data, long add_id)
    {
        int affectedRows = 0;
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_ADD,Statement.RETURN_GENERATED_KEYS)) 
            {
                pstmt.setLong(1, add_id);
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
    
    public int deleteWeather(String SQL_WEATHER, WeatherData weather_data, long weather_id, long add_id, long config_id, long wind_id) {
        
        int affectedRows = 0;
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WEATHER,Statement.RETURN_GENERATED_KEYS)) 
            {
                pstmt.setLong(1, weather_id);
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
    
    public void deleteWeatherList(List<WeatherData> weather_list) {
        
        
        long[] wind_upd = {0}, config_upd = {0}, weather_upd = {0}, add_upd = {0};

        if (weather_list != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            weather_list.parallelStream().forEach(weather_data -> {
                
                long wind_id = 0, config_id = 0, weather_id = 0, add_id = 0;
                
                wind_id = weather_data.getWind_data().getId();
                config_id = weather_data.getConfig_data().getId();
                add_id = weather_data.getAdditional_data().getId();
                weather_id = weather_data.getId();
                
                weather_upd[0] += deleteWeather(SQL_DELETE_WEATHER, weather_data, weather_id, add_id, config_id, wind_id);
                wind_upd[0] += deleteWind(SQL_DELETE_WIND, weather_data, wind_id);
                config_upd[0] += deleteConfig(SQL_DELETE_CONFIG, weather_data, config_id);
                add_upd[0] += deleteAdditional(SQL_DELETE_ADDITIONAL, weather_data, add_id);
                
            });
        
            System.out.println("Parallel stream db delete time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            
        logger.info(this.getClass().getName().toString() + ": Delete:\n Wind:" + wind_upd[0] + " rows.\n" +
                "Additional:" + add_upd[0] + " rows.\n" + "Config:" + config_upd[0] + " rows.\n" + "Weather:" + weather_upd[0] + " rows.\n" +
                "Data deleted successfully.");
        }

    }

}

package com.isoft.base.db.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.isoft.base.db.DBConnector;
import com.isoft.base.db.model.WeatherData;
import com.isoft.base.logging.PropLogger;

public class WeatherDAO {
    DBConnector db = null;
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    private static final String SQL_SELECT_WEATHER_ALL = "SELECT * from \"Weather\"";
    
    public WeatherDAO(DBConnector db)
    {
        this.db = db;
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
    
    public Long insertWeather(String SQL_WEATHER, WeatherData weather_data, long weather_id, long add_id, long config_id, long wind_id) {
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WEATHER,Statement.RETURN_GENERATED_KEYS)) 
            {
                pstmt.setInt(1, weather_data.getTemp());
                if(weather_data.getFeel_temp() != null) 
                {
                    pstmt.setInt(2, weather_data.getFeel_temp());
                }
                else 
                {
                    pstmt.setNull(2, Types.INTEGER);
                }
                pstmt.setString(3, weather_data.getStatus());
                pstmt.setLong(4, add_id);
                pstmt.setLong(5, config_id);
                pstmt.setLong(6, wind_id);
                pstmt.setTimestamp(7, Timestamp.from(weather_data.getQuery_date().toInstant()));
     
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) 
                {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) 
                    {
                        if (rs.next()) 
                        {
                            weather_id = rs.getLong("weather_id");
                        }
                    } 
                    catch (SQLException ex) 
                    {
                        logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                        return (Long) null;
                    }
                }
            } 
            catch (SQLException ex) 
            {
                logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                return (Long) null;
            }
        }
        return weather_id;
    }
    
    public List<WeatherData> getWeatherData()
    {
        List<WeatherData> weather_dblist = new ArrayList<>();
        try (Statement stmt = db.getConnection().createStatement()) 
        {
            ResultSet rs = stmt.executeQuery(SQL_SELECT_WEATHER_ALL);
            while (rs.next()) 
            {
                long id = rs.getLong("weather_id");
                int temp = rs.getInt("temp");
                int feel_temp = rs.getInt("feel_temp");
                String status = rs.getString("status");
                Timestamp query_date = rs.getTimestamp("query_date");
                
                WeatherData db_weather_data = new WeatherData(id, temp, feel_temp, status, query_date);
                weather_dblist.add(db_weather_data);
            }
        } 
        catch (SQLException e) 
        {
            logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
            return null;
        }
        return weather_dblist;
    }
    
    public Long updateWeather(String SQL_WEATHER, WeatherData weather_data, long weather_id, long add_id, long config_id, long wind_id) {
        long affectedRows = 0;
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WEATHER,Statement.RETURN_GENERATED_KEYS)) 
            {      
                pstmt.setInt(1, weather_data.getTemp());
                if(weather_data.getFeel_temp() != null) 
                {
                    pstmt.setInt(2, weather_data.getFeel_temp());
                }
                else 
                {
                    pstmt.setNull(2, Types.INTEGER);
                }
                pstmt.setString(3, weather_data.getStatus());
                pstmt.setLong(4, add_id);
                pstmt.setLong(5, config_id);
                pstmt.setLong(6, wind_id);
                pstmt.setLong(7, weather_id);
     
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

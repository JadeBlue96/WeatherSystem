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
import com.isoft.base.db.model.WeatherData;
import com.isoft.base.db.model.Wind;
import com.isoft.base.logging.PropLogger;

public class WindDAO {
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    DBConnector db = null;
    
    
    private static final String SQL_INSERT_WIND = "INSERT INTO \"Wind\"(wind_spd, wind_status, wind_direction) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_INSERT_CONFIG = "INSERT INTO \"Config\"(city, country, site) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_SELECT_WIND_ALL = "SELECT * from \"Wind\"";
    private static final String SQL_UPDATE_WIND = "UPDATE \"Wind\" SET wind_spd = ?, wind_status = ?, wind_direction = ? WHERE wind_id = ? \n";
    
    public WindDAO(DBConnector db)
    {
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
    
    public Long updateWind(String SQL_WIND, WeatherData weather_data, long wind_id)
    {
        long affectedRows = 0;
        
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WIND,Statement.RETURN_GENERATED_KEYS)) 
            {                 
                pstmt.setDouble(1, weather_data.getWind_data().getWind_spd());
                pstmt.setString(2, weather_data.getWind_data().getWind_status());
                pstmt.setString(3, weather_data.getWind_data().getWind_direction());
                pstmt.setLong(4, wind_id);
     
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
    
    public Long insertWind(String SQL_WIND, WeatherData weather_data, long wind_id)
    {
        if (weather_data != null)
        {
            try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WIND,Statement.RETURN_GENERATED_KEYS)) 
            {
                pstmt.setDouble(1, weather_data.getWind_data().getWind_spd());
                pstmt.setString(2, weather_data.getWind_data().getWind_status());
                pstmt.setString(3, weather_data.getWind_data().getWind_direction());
     
                int affectedRows = pstmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet rs = pstmt.getGeneratedKeys()) 
                    {
                        if (rs.next()) 
                        {
                            wind_id = rs.getLong("wind_id");
                        }
                    } 
                    catch (SQLException ex) 
                    {
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
        return wind_id;
    }
    
    public List<Wind> getWindData()
    {
        List<Wind> wind_list = new ArrayList<>();
        Wind wind_data = new Wind();
        try (Statement stmt = db.getConnection().createStatement()) 
        {
                ResultSet rs = stmt.executeQuery(SQL_SELECT_WIND_ALL);
                while (rs.next()) 
                {
                    long id = rs.getLong("wind_id");
                    double wind_spd = rs.getDouble("wind_spd");
                    String wind_status = rs.getString("wind_status");
                    String wind_direction = rs.getString("wind_direction");
                    wind_data = new Wind(id, wind_spd, wind_status, wind_direction);
                    wind_list.add(wind_data);
                }
        }
        catch (SQLException e) 
        {
            logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
            return null;
        }
        return wind_list;
    }
    
    
}

package db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import model.WeatherData;

public class DBWeatherInserter {
	
	DBConnector db = null;
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	private static final String SQL_INSERT_WIND = "INSERT INTO \"Wind\"(wind_spd, wind_status, wind_direction) "
            + "VALUES(?,?,?)\n";
	private static final String SQL_INSERT_CONFIG = "INSERT INTO \"Config\"(city, country, site) "
            + "VALUES(?,?,?)\n";
	private static final String SQL_INSERT_ADDITIONAL  = "INSERT INTO \"Additional\"(humidity, visibility, pressure) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_INSERT_WEATHER = "INSERT INTO \"Weather\"(temp, feel_temp, status, weather_add_id, weather_config_id, weather_wind_id, query_date) "
            + "VALUES(?,?,?,?,?,?,?)\n";
    

    public DBWeatherInserter(DBConnector db) {
		this.db = db;
	}
	
	public Long insertWind(String SQL_WIND, WeatherData weather_data, Long wind_id)
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
	
	public Long insertConfig(String SQL_CONFIG, WeatherData weather_data, Long config_id)
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
	
	public Long insertAdditional(String SQL_ADD, WeatherData weather_data, Long add_id)
	{
		if (weather_data != null)
		{
			try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_ADD,Statement.RETURN_GENERATED_KEYS)) 
			{
	            if(weather_data.getAdditional_data().getHumidity() != null) 
	            {
	            	pstmt.setDouble(1, weather_data.getAdditional_data().getHumidity());
	            }
	            else 
	            {
	            	pstmt.setNull(1, Types.INTEGER);
	            }
	            if(weather_data.getAdditional_data().getVisibility() != null) 
	            {
	            	pstmt.setDouble(2, weather_data.getAdditional_data().getVisibility());
	            }
	            else 
	            {
	            	pstmt.setNull(2, Types.DOUBLE);
	            }
	            if(weather_data.getAdditional_data().getPressure() != null) 
	            {
	            	pstmt.setInt(3, weather_data.getAdditional_data().getPressure());
	            }
	            else 
	            {
	            	pstmt.setNull(3, Types.INTEGER);
	            }
	 
	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) 
	            {
	                try (ResultSet rs = pstmt.getGeneratedKeys()) 
	                {
	                    if (rs.next()) 
	                    {
	                        add_id = rs.getLong("add_id");
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
		return add_id;
	}
	
	public Long insertWeather(String SQL_WEATHER, WeatherData weather_data, Long weather_id, Long add_id, Long config_id, Long wind_id) {
		
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
	
	public void insertWeatherList(List<WeatherData> weather_list) {
		
		if (weather_list != null)
		{
			Long timeStarted = System.currentTimeMillis();
			
			weather_list.parallelStream().forEach(weather_data -> {

				long wind_id = 0, config_id = 0, weather_id = 0, add_id = 0;
				wind_id = insertWind(SQL_INSERT_WIND, weather_data, wind_id);
		        weather_data.getWind_data().setId(wind_id);
		        config_id = insertConfig(SQL_INSERT_CONFIG, weather_data, config_id);
		        weather_data.getConfig_data().setId(config_id);
		        add_id = insertAdditional(SQL_INSERT_ADDITIONAL, weather_data, add_id); 
		        weather_data.getAdditional_data().setId(add_id);
		        weather_id = insertWeather(SQL_INSERT_WEATHER, weather_data, weather_id, add_id, config_id, wind_id);
		        weather_data.setId(weather_id);
			         
			    if(weather_id > 0)    logger.info(this.getClass().getName().toString() + ": ID: " + weather_id + " Data inserted successfully."); 
			    
			});
			
			System.out.println("Parallel stream db insert time: " + (System.currentTimeMillis() - timeStarted) + "ms");
		}

	}

}

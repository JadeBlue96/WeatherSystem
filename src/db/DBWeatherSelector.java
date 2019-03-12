package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import model.Additional;
import model.ConfigData;
import model.WeatherData;
import model.Wind;

public class DBWeatherSelector {
	
	DBConnector db = null;
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	private static final String SQL_SELECT_WIND_ALL = "SELECT * from \"Wind\"";
    private static final String SQL_SELECT_CONFIG_ALL = "SELECT * from \"Config\"";
    private static final String SQL_SELECT_ADDITIONAL_ALL = "SELECT * from \"Additional\"";
    private static final String SQL_SELECT_WEATHER_ALL = "SELECT * from \"Weather\"";

	public DBWeatherSelector(DBConnector db) {
		this.db = db;
	}
	
	public List<Additional> getAddData()
	{
		List<Additional> add_list = new ArrayList<>();
		Additional add_data = new Additional();
		 try (Statement stmt = db.getConnection().createStatement()) 
		 {
	        	ResultSet rs = stmt.executeQuery(SQL_SELECT_ADDITIONAL_ALL);
	            while (rs.next()) 
	            {
	            	Long id = rs.getLong("add_id");
	                Integer humidity = rs.getInt("humidity");
	                Double visibility = rs.getDouble("visibility");
	                Integer pressure = rs.getInt("pressure");
	                add_data = new Additional(id, humidity, visibility, pressure);
	                add_list.add(add_data);
	            }
	     } 
		 catch (SQLException e) 
		 {
	        logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
	        return null;
	     }
		 return add_list;
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
	            	Long id = rs.getLong("config_id");
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
	
	public List<Wind> getWindData()
	{
		List<Wind> wind_list = new ArrayList<>();
		Wind wind_data = new Wind();
		try (Statement stmt = db.getConnection().createStatement()) 
		{
	        	ResultSet rs = stmt.executeQuery(SQL_SELECT_WIND_ALL);
	            while (rs.next()) 
	            {
	            	Long id = rs.getLong("wind_id");
	                Double wind_spd = rs.getDouble("wind_spd");
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
	
	public List<WeatherData> getWeatherData()
	{
		List<WeatherData> weather_dblist = new ArrayList<>();
		try (Statement stmt = db.getConnection().createStatement()) 
		{
        	ResultSet rs = stmt.executeQuery(SQL_SELECT_WEATHER_ALL);
            while (rs.next()) 
            {
            	Long id = rs.getLong("weather_id");
                Integer temp = rs.getInt("temp");
                Integer feel_temp = rs.getInt("feel_temp");
                String status = rs.getString("status");
                Long weather_add_id = rs.getLong("weather_add_id");
                Long weather_config_id = rs.getLong("weather_config_id");
                Long weather_wind_id = rs.getLong("weather_wind_id");
                Timestamp query_date = rs.getTimestamp("query_date");
                
                WeatherData db_weather_data = new WeatherData(id, temp, feel_temp, status,weather_add_id, weather_config_id, weather_wind_id, query_date);
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

	public List<WeatherData> DBOToModelObjects() {
		List<WeatherData> weather_objlist = new ArrayList<>();
        List<WeatherData> weather_dblist = new ArrayList<>();
        
        List<ConfigData> conf_list = getConfigData();
        List<Additional> add_list = getAddData();
        List<Wind> wind_list = getWindData();
        weather_dblist = getWeatherData();
        
        if(weather_dblist != null)
        {
        	Long timeStarted = System.currentTimeMillis();
        	
        	weather_dblist.parallelStream().forEach(weather_db_data -> {
        		Long db_add_id = weather_db_data.getWeather_add_id();
	        	Long db_config_id = weather_db_data.getWeather_config_id();
	        	Long db_wind_id = weather_db_data.getWeather_wind_id();
	        	WeatherData weather_obj_data = new WeatherData();
	        	add_list.forEach(add -> {
	        		if(add.getId().equals(db_add_id)) {
	        			weather_obj_data.setAdditional_data(add);
	        			return;
	        		}
	        	});
	        	conf_list.forEach(config -> {
	        		if(config.getId().equals(db_config_id))
	        		{
	        			weather_obj_data.setConfig_data(config);
	        			return;
	        		}
	        	});

	        	wind_list.forEach(wind -> {
	        		if(wind.getId().equals(db_wind_id))
	        		{
	        			weather_obj_data.setWind_data(wind);
	        			return;
	        		}
	        	});

	        	weather_obj_data.setTemp(weather_db_data.getTemp());
	        	weather_obj_data.setFeel_temp(weather_db_data.getFeel_temp());
	        	weather_obj_data.setStatus(weather_db_data.getStatus());
	        	weather_obj_data.setQuery_date(weather_db_data.getQuery_date());
	        	
	        	weather_objlist.add(weather_obj_data);
        	});
        	
        	System.out.println("Parallel stream db select time: " + (System.currentTimeMillis() - timeStarted) + "ms");
	        
        	return weather_objlist;
        }
        
        logger.log(Level.SEVERE, this.getClass().getName().toString() + " : Failed to retrieve data from PostgreSQL database.");
        return null;
        
	}

}

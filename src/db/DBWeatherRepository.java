package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import model.Additional;
import model.ConfigData;
import model.WeatherData;
import model.Wind;

public class DBWeatherRepository implements IWeatherRepository{
	DBConnector db = null;
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	private static final String SQL_INSERT_WIND = "INSERT INTO \"Wind\"(wind_spd, wind_status, wind_direction) "
            + "VALUES(?,?,?)\n";
	private static final String SQL_INSERT_CONFIG = "INSERT INTO \"Config\"(city, country, site) "
            + "VALUES(?,?,?)\n";
	private static final String SQL_INSERT_ADDITIONAL  = "INSERT INTO \"Additional\"(humidity, visibility, pressure) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_INSERT_WEATHER = "INSERT INTO \"Weather\"(temp, feel_temp, status, weather_add_id, weather_config_id, weather_wind_id) "
            + "VALUES(?,?,?,?,?,?)\n";
    
    private static final String SQL_SELECT_WIND_ALL = "SELECT * from \"Wind\"";
    private static final String SQL_SELECT_CONFIG_ALL = "SELECT * from \"Config\"";
    private static final String SQL_SELECT_ADDITIONAL_ALL = "SELECT * from \"Additional\"";
    private static final String SQL_SELECT_WEATHER_ALL = "SELECT * from \"Weather\"";
	
	public DBWeatherRepository(String url, String user, String pw)
	{
		db = new DBConnector( url, user, pw, false );
		logger.info(this.getClass().getName() + ": Connection to database successful.");
	}
	
	public Long insertWind(String SQL_WIND, WeatherData weather_data, Long wind_id)
	{
		try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WIND,Statement.RETURN_GENERATED_KEYS)) {
			 
            pstmt.setDouble(1, weather_data.getWind_data().getWind_spd());
            pstmt.setString(2, weather_data.getWind_data().getWind_status());
            pstmt.setString(3, weather_data.getWind_data().getWind_direction());
 
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        wind_id = rs.getLong("wind_id");
                    }
                } catch (SQLException ex) {
                	logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                	return null;
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
            return null;
        } 
		return wind_id;
	}
	
	public Long insertConfig(String SQL_CONFIG, WeatherData weather_data, Long config_id)
	{
		 try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_CONFIG,Statement.RETURN_GENERATED_KEYS)) {
	       	 
	            pstmt.setString(1, weather_data.getConfig_data().getCity());
	            pstmt.setString(2, weather_data.getConfig_data().getCountry());
	            pstmt.setString(3, weather_data.getConfig_data().getSite());
	 
	            int affectedRows = pstmt.executeUpdate();
	            if (affectedRows > 0) {
	                try (ResultSet rs = pstmt.getGeneratedKeys()) {
	                    if (rs.next()) {
	                    	config_id = rs.getLong("config_id");
	                    }
	                } catch (SQLException ex) {
	                	logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
	                	return null;
	                }
	            }
	        } catch (SQLException ex) {
	            logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
	            return null;
	        } 
		 return config_id;
	}
	
	public Long insertAdditional(String SQL_ADD, WeatherData weather_data, Long add_id)
	{
		try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_ADD,Statement.RETURN_GENERATED_KEYS)) {
			 
            pstmt.setDouble(1, weather_data.getAdditional_data().getHumidity());
            pstmt.setDouble(2, weather_data.getAdditional_data().getVisibility());
            pstmt.setInt(3, weather_data.getAdditional_data().getPressure());
 
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        add_id = rs.getLong("add_id");
                    }
                } catch (SQLException ex) {
                	logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                	return null;
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
            return null;
        } 
		return add_id;
	}
	
	public boolean insertWeather(String SQL_WEATHER, WeatherData weather_data, Long weather_id, Long add_id, Long config_id, Long wind_id) {
		try (PreparedStatement pstmt = db.getConnection().prepareStatement(SQL_WEATHER,Statement.RETURN_GENERATED_KEYS)) {
	       	 
            pstmt.setInt(1, weather_data.getTemp());
            pstmt.setInt(2, weather_data.getFeel_temp());
            pstmt.setString(3, weather_data.getStatus());
            pstmt.setLong(4, add_id);
            pstmt.setLong(5, config_id);
            pstmt.setLong(6, wind_id);
 
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        weather_id = rs.getLong("weather_id");
                    }
                } catch (SQLException ex) {
                	logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
                	return false;
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + ex.getMessage());
            return false;
        }
		return true;
	}
	
	
	public long insertWeatherData(WeatherData weather_data) {
		
		long wind_id = 0, config_id = 0, weather_id = 0, add_id = 0;
        boolean success = false;
        
        wind_id = insertWind(SQL_INSERT_WIND, weather_data, wind_id);
        config_id = insertConfig(SQL_INSERT_CONFIG, weather_data, config_id);
        add_id = insertAdditional(SQL_INSERT_ADDITIONAL, weather_data, add_id); 
        success = insertWeather(SQL_INSERT_WEATHER, weather_data, weather_id, add_id, config_id, wind_id);
	         
	    if(success)    logger.info(this.getClass().getName().toString() + ": Data inserted successfully.");
		return weather_id;
	}
	
	public void close()
	{
		db.close();
		logger.info(this.getClass().getName().toString() + ": Connection to database closed.");
	}
	
	public List<Additional> getAddData()
	{
		List<Additional> add_list = new ArrayList<>();
		Additional add_data = new Additional();
		 try (Statement stmt = db.getConnection().createStatement()) {
	        	ResultSet rs = stmt.executeQuery(SQL_SELECT_ADDITIONAL_ALL);
	            while (rs.next()) {
	            	Long id = rs.getLong("add_id");
	                Double humidity = rs.getDouble("humidity");
	                Integer visibility = rs.getInt("visibility");
	                Integer pressure = rs.getInt("pressure");
	                add_data = new Additional(id, visibility, humidity, pressure);
	                add_list.add(add_data);
	            }
	        } catch (SQLException e) {
	        	logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
	            return null;
	        }
		 return add_list;
	}
	
	public List<ConfigData> getConfigData()
	{
		List<ConfigData> conf_list = new ArrayList<>();
		ConfigData conf_data = new ConfigData();
		 try (Statement stmt = db.getConnection().createStatement()) {
	        	ResultSet rs = stmt.executeQuery(SQL_SELECT_CONFIG_ALL);
	            while (rs.next()) {
	            	Long id = rs.getLong("config_id");
	                String city = rs.getString("city");
	                String country = rs.getString("country");
	                String site = rs.getString("site");
	                conf_data = new ConfigData(id, city, country, site);
	                conf_list.add(conf_data);
	            }
	        } catch (SQLException e) {
	        	logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
	            return null;
	        }
		 return conf_list;
	}
	
	public List<Wind> getWindData()
	{
		List<Wind> wind_list = new ArrayList<>();
		Wind wind_data = new Wind();
		 try (Statement stmt = db.getConnection().createStatement()) {
	        	ResultSet rs = stmt.executeQuery(SQL_SELECT_WIND_ALL);
	            while (rs.next()) {
	            	Long id = rs.getLong("wind_id");
	                Double wind_spd = rs.getDouble("wind_spd");
	                String wind_status = rs.getString("wind_status");
	                String wind_direction = rs.getString("wind_direction");
	                wind_data = new Wind(id, wind_spd, wind_status, wind_direction);
	                wind_list.add(wind_data);
	            }
	        } catch (SQLException e) {
	        	logger.log(Level.SEVERE, this.getClass().getName().toString() + ": " + e.getMessage());
	            return null;
	        }
		 return wind_list;
	}
	
	public List<WeatherData> getWeatherData()
	{
		List<WeatherData> weather_dblist = new ArrayList<>();
		try (Statement stmt = db.getConnection().createStatement()) {
        	ResultSet rs = stmt.executeQuery(SQL_SELECT_WEATHER_ALL);
            while (rs.next()) {
            	Long id = rs.getLong("weather_id");
                Integer temp = rs.getInt("temp");
                Integer feel_temp = rs.getInt("feel_temp");
                String status = rs.getString("status");
                Long weather_add_id = rs.getLong("weather_add_id");
                Long weather_config_id = rs.getLong("weather_config_id");
                Long weather_wind_id = rs.getLong("weather_wind_id");
                
                WeatherData db_weather_data = new WeatherData(id, temp, feel_temp, status,weather_add_id, weather_config_id, weather_wind_id);
                weather_dblist.add(db_weather_data);
            }
        } catch (SQLException e) {
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
        for(WeatherData weather_db_data: weather_dblist)
        {
        	Long db_add_id = weather_db_data.getWeather_add_id();
        	Long db_config_id = weather_db_data.getWeather_config_id();
        	Long db_wind_id = weather_db_data.getWeather_wind_id();
        	WeatherData weather_obj_data = new WeatherData();
        	for(Additional add: add_list)
        	{
        		if(add.getId().equals(db_add_id)) {
        			weather_obj_data.setAdditional_data(add);
        			break;
        		}
        	}
        	for(ConfigData config: conf_list)
        	{
        		if(config.getId().equals(db_config_id))
        		{
        			weather_obj_data.setConfig_data(config);
        			break;
        		}
        	}
        	for(Wind wind: wind_list)
        	{
        		if(wind.getId().equals(db_wind_id))
        		{
        			weather_obj_data.setWind_data(wind);
        			break;
        		}
        	}
        	weather_obj_data.setTemp(weather_db_data.getTemp());
        	weather_obj_data.setFeel_temp(weather_db_data.getFeel_temp());
        	weather_obj_data.setStatus(weather_db_data.getStatus());
        	
        	weather_objlist.add(weather_obj_data);
        }
        
        return weather_objlist;
	}
	
}

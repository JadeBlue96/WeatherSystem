package main.com.isoft.rest.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.com.isoft.base.logging.PropLogger;
import main.com.isoft.rest.db.DBConnector;
import main.com.isoft.rest.db.model.Additional;
import main.com.isoft.rest.db.model.ConfigData;
import main.com.isoft.rest.db.model.WeatherData;
import main.com.isoft.rest.db.model.Wind;

public class WeatherListDAO {
    DBConnector db = null;
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    private static final String SQL_DELETE_WIND = "DELETE FROM \"Wind\" WHERE wind_id = ?\n";
    private static final String SQL_DELETE_CONFIG = "DELETE FROM \"Config\" WHERE config_id = ?\n";
    private static final String SQL_DELETE_ADDITIONAL  = "DELETE FROM \"Additional\" WHERE add_id = ?\n";
    private static final String SQL_DELETE_WEATHER = "DELETE FROM \"Weather\" WHERE weather_id = ?\n";
    
    private static final String SQL_INSERT_WIND = "INSERT INTO \"Wind\"(wind_spd, wind_status, wind_direction) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_INSERT_CONFIG = "INSERT INTO \"Config\"(city, country, site) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_INSERT_ADDITIONAL  = "INSERT INTO \"Additional\"(humidity, visibility, pressure) "
            + "VALUES(?,?,?)\n";
    private static final String SQL_INSERT_WEATHER = "INSERT INTO \"Weather\"(temp, feel_temp, status, weather_add_id, weather_config_id, weather_wind_id, query_date) "
            + "VALUES(?,?,?,?,?,?,?)\n";
    
    private static final String SQL_UPDATE_WIND = "UPDATE \"Wind\" SET wind_spd = ?, wind_status = ?, wind_direction = ? WHERE wind_id = ? \n";
    private static final String SQL_UPDATE_ADDITIONAL = "UPDATE \"Additional\" SET humidity = ?, visibility = ?, pressure = ? WHERE add_id = ?\n";
    private static final String SQL_UPDATE_CONFIG = "UPDATE \"Config\" SET city = ?, country = ?, site = ? WHERE config_id = ?\n";
    private static final String SQL_UPDATE_WEATHER = "UPDATE \"Weather\" SET temp = ?, feel_temp = ?, status = ?, "
            + "weather_add_id = ?, weather_config_id = ?, weather_wind_id = ? WHERE weather_id = ?\n";
    
    public WeatherListDAO(DBConnector db)
    {
        this.db = db;
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
                
                WeatherDAO weather_dao = new WeatherDAO(db);
                WindDAO wind_dao = new WindDAO(db);
                ConfigDAO config_dao = new ConfigDAO(db);
                AdditionalDAO add_dao = new AdditionalDAO(db);
                
                weather_upd[0] += weather_dao.deleteWeather(SQL_DELETE_WEATHER, weather_data, weather_id, add_id, config_id, wind_id);
                wind_upd[0] += wind_dao.deleteWind(SQL_DELETE_WIND, weather_data, wind_id);
                config_upd[0] += config_dao.deleteConfig(SQL_DELETE_CONFIG, weather_data, config_id);
                add_upd[0] += add_dao.deleteAdditional(SQL_DELETE_ADDITIONAL, weather_data, add_id);
                
            });
        
            System.out.println("Parallel stream db delete time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            
        logger.info(this.getClass().getName().toString() + ": Delete:\n Wind:" + wind_upd[0] + " rows.\n" +
                "Additional:" + add_upd[0] + " rows.\n" + "Config:" + config_upd[0] + " rows.\n" + "Weather:" + weather_upd[0] + " rows.\n" +
                "Data deleted successfully.");
        }

    }
    
    public void updateWeatherList(List<WeatherData> weather_list)
    {
        
        long[] wind_upd = {0}, config_upd = {0}, weather_upd = {0}, add_upd = {0};
        
        if(weather_list != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            weather_list.parallelStream().forEach(weather_data -> {
                long wind_id = 0, config_id = 0, weather_id = 0, add_id = 0;
                
                wind_id = weather_data.getWind_data().getId();
                config_id = weather_data.getConfig_data().getId();
                add_id = weather_data.getAdditional_data().getId();
                weather_id = weather_data.getId();
                
                WeatherDAO weather_dao = new WeatherDAO(db);
                WindDAO wind_dao = new WindDAO(db);
                ConfigDAO config_dao = new ConfigDAO(db);
                AdditionalDAO add_dao = new AdditionalDAO(db);
                
                wind_upd[0] += wind_dao.updateWind(SQL_UPDATE_WIND, weather_data, wind_id);
                config_upd[0] += config_dao.updateConfig(SQL_UPDATE_CONFIG, weather_data, config_id);
                add_upd[0] += add_dao.updateAdditional(SQL_UPDATE_ADDITIONAL, weather_data, add_id); 
                weather_upd[0] += weather_dao.updateWeather(SQL_UPDATE_WEATHER, weather_data, weather_id, add_id, config_id, wind_id);
            });
            
            System.out.println("Parallel stream db update time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            
            logger.info(this.getClass().getName().toString() + ": Update:\n Wind:" + wind_upd[0] + " rows.\n" +
                    "Additional:" + add_upd[0] + " rows.\n" + "Config:" + config_upd[0] + " rows.\n" + "Weather:" + weather_upd[0] + " rows.\n" +
                    "Data updated successfully.");
        }
    }
    
    public void insertWeatherList(List<WeatherData> weather_list) {
        
        if (weather_list != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            WeatherDAO weather_dao = new WeatherDAO(db);
            WindDAO wind_dao = new WindDAO(db);
            ConfigDAO config_dao = new ConfigDAO(db);
            AdditionalDAO add_dao = new AdditionalDAO(db);
            
            weather_list.parallelStream().forEach(weather_data -> {

                long wind_id = 0, config_id = 0, weather_id = 0, add_id = 0;
                wind_id = wind_dao.insertWind(SQL_INSERT_WIND, weather_data, wind_id);
                weather_data.getWind_data().setId(wind_id);
                config_id = config_dao.insertConfig(SQL_INSERT_CONFIG, weather_data, config_id);
                weather_data.getConfig_data().setId(config_id);
                add_id = add_dao.insertAdditional(SQL_INSERT_ADDITIONAL, weather_data, add_id); 
                weather_data.getAdditional_data().setId(add_id);
                weather_id = weather_dao.insertWeather(SQL_INSERT_WEATHER, weather_data, weather_id, add_id, config_id, wind_id);
                weather_data.setId(weather_id);
                     
                if(weather_id > 0)    logger.info(this.getClass().getName().toString() + ": ID: " + weather_id + " Data inserted successfully."); 
                
            });
            
            System.out.println("Parallel stream db insert time: " + (System.currentTimeMillis() - timeStarted) + "ms");
        }

    }
    
    public List<WeatherData> DBOToModelObjects() {
        List<WeatherData> weather_objlist = new ArrayList<>();
        List<WeatherData> weather_dblist = new ArrayList<>();
        
        WeatherDAO weather_dao = new WeatherDAO(db);
        WindDAO wind_dao = new WindDAO(db);
        ConfigDAO config_dao = new ConfigDAO(db);
        AdditionalDAO add_dao = new AdditionalDAO(db);
        
        List<ConfigData> conf_list = config_dao.getConfigData();
        List<Additional> add_list = add_dao.getAddData();
        List<Wind> wind_list = wind_dao.getWindData();
        weather_dblist = weather_dao.getWeatherData();
        
        if(weather_dblist != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            weather_dblist.parallelStream().forEach(weather_db_data -> {
                //Long db_add_id = weather_db_data.getWeather_add_id();
                //Long db_config_id = weather_db_data.getWeather_config_id();
                //Long db_wind_id = weather_db_data.getWeather_wind_id();
                WeatherData weather_obj_data = new WeatherData();
                add_list.forEach(add -> {
                        weather_obj_data.setAdditional_data(add);
                        return;
                });
                conf_list.forEach(config -> {
                        weather_obj_data.setConfig_data(config);
                        return;
                });

                wind_list.forEach(wind -> {
                        weather_obj_data.setWind_data(wind);
                        return;
                });

                weather_obj_data.setTemp(weather_db_data.getTemp());
                weather_obj_data.setFeel_temp(weather_db_data.getFeel_temp());
                weather_obj_data.setStatus(weather_db_data.getStatus());
                weather_obj_data.setQuery_date();
                
                weather_objlist.add(weather_obj_data);
            });
            
            System.out.println("Parallel stream db select time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            
            return weather_objlist;
        }
        
        logger.log(Level.SEVERE, this.getClass().getName().toString() + " : Failed to retrieve data from PostgreSQL database.");
        return null;
        
    }
}

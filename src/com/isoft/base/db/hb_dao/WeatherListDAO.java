package com.isoft.base.db.hb_dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.isoft.base.db.model.Additional;
import com.isoft.base.db.model.ConfigData;
import com.isoft.base.db.model.WeatherData;
import com.isoft.base.db.model.Wind;
import com.isoft.base.logging.PropLogger;

public class WeatherListDAO {
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    public void deleteWeatherList(List<WeatherData> weather_list) {
        

        if (weather_list != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            weather_list.parallelStream().forEach(weather_data -> {
                
                long wind_id = 0, config_id = 0, weather_id = 0, add_id = 0;
                
                wind_id = weather_data.getWind_data().getId();
                config_id = weather_data.getConfig_data().getId();
                add_id = weather_data.getAdditional_data().getId();
                weather_id = weather_data.getId();
                
                WeatherDAO weather_dao = new WeatherDAO();
                WindDAO wind_dao = new WindDAO();
                ConfigDAO config_dao = new ConfigDAO();
                AdditionalDAO add_dao = new AdditionalDAO();
                
                weather_dao.deleteWeather(weather_id);
                wind_dao.deleteWind(wind_id);
                config_dao.deleteConfig(config_id);
                add_dao.deleteAdditional(add_id);
                
            });
        
            System.out.println("Parallel stream db delete time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            
        logger.info(this.getClass().getName().toString() + ": Successfully deleted + " + weather_list.size() + " rows.");
        }

    }
    
    public void updateWeatherList(List<WeatherData> weather_list)
    {
        
        if(weather_list != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            weather_list.parallelStream().forEach(weather_data -> {
                long wind_id = 0, config_id = 0, weather_id = 0, add_id = 0;
                
                wind_id = weather_data.getWind_data().getId();
                config_id = weather_data.getConfig_data().getId();
                add_id = weather_data.getAdditional_data().getId();
                weather_id = weather_data.getId();
                
                WeatherDAO weather_dao = new WeatherDAO();
                WindDAO wind_dao = new WindDAO();
                ConfigDAO config_dao = new ConfigDAO();
                AdditionalDAO add_dao = new AdditionalDAO();
                
                wind_dao.updateWind(weather_data, wind_id);
                config_dao.updateConfig(weather_data, config_id);
                add_dao.updateAdditional(weather_data, add_id); 
                weather_dao.updateWeather(weather_data, weather_id);
            });
            
            System.out.println("Parallel stream db update time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            
            logger.info(this.getClass().getName().toString() + ": Successfully updated + " + weather_list.size() + " rows.");
        }
    }
    
    public void insertWeatherList(List<WeatherData> weather_list) {
        
        if (weather_list != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            WeatherDAO weather_dao = new WeatherDAO();
            WindDAO wind_dao = new WindDAO();
            ConfigDAO config_dao = new ConfigDAO();
            AdditionalDAO add_dao = new AdditionalDAO();
            
            weather_list.parallelStream().forEach(weather_data -> {

                wind_dao.insertWind(weather_data);
                config_dao.insertConfig(weather_data);
                add_dao.insertAdditional(weather_data); 
                weather_dao.insertWeather(weather_data);
                
            });
            
            System.out.println("Parallel stream db insert time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            
            logger.info(this.getClass().getName().toString() + ": Successfully updated + " + weather_list.size() + " rows.");
        }

    }
    
    
    public List<WeatherData> DBOToModelObjects() {
        List<WeatherData> weather_objlist = new ArrayList<>();
        List<WeatherData> weather_dblist = new ArrayList<>();
        
        WeatherDAO weather_dao = new WeatherDAO();
        WindDAO wind_dao = new WindDAO();
        ConfigDAO config_dao = new ConfigDAO();
        AdditionalDAO add_dao = new AdditionalDAO();
        
        List<ConfigData> conf_list = config_dao.getConfigData();
        List<Additional> add_list = add_dao.getAddData();
        List<Wind> wind_list = wind_dao.getWindData();
        weather_dblist = weather_dao.getWeatherData();
        
        if(weather_dblist != null)
        {
            Long timeStarted = System.currentTimeMillis();
            
            weather_dblist.parallelStream().forEach(weather_db_data -> {
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
                weather_obj_data.setQuery_date(weather_db_data.getQuery_date());
                
                weather_objlist.add(weather_obj_data);
            });
            
            System.out.println("Parallel stream db select time: " + (System.currentTimeMillis() - timeStarted) + "ms");
            logger.info(this.getClass().getName().toString() + ": Successfully selected + " + weather_objlist.size() + " rows.");
            return weather_objlist;
        }
        
        logger.log(Level.SEVERE, this.getClass().getName().toString() + " : Failed to retrieve data from PostgreSQL database.");
        return null;
        
    }
    
}

package com.isoft.base.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.isoft.base.db.DBWeatherRepository;
import com.isoft.base.db.HBWeatherRepository;
import com.isoft.base.db.hb_dao.WeatherListDAO;
import com.isoft.base.db.model.WeatherData;
import com.isoft.base.logging.PropLogger;
import com.isoft.base.property.CityConfig;
import com.isoft.base.validation.Validator;
import com.isoft.base.weather.WeatherExtractor;

public class Main {
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    public static void main(String[] args)
    {
        
        List<CityConfig> cities = new ArrayList<CityConfig>();
        boolean isXMLValidationType = false;
        cities = Validator.getValidCitiesByType(isXMLValidationType);
        WeatherExtractor weather_extractor = new WeatherExtractor();
        List<WeatherData> weather_list = weather_extractor.getDataForCity(cities);
        WeatherExtractor.printWeatherList(weather_list);
        
        
        HBWeatherRepository hbw_repos = new HBWeatherRepository();
        hbw_repos.DBInsertList(weather_list);
        //weather_list.get(0).setFeel_temp(666);
        //hbw_repos.DBUpdateList(weather_list);
       // List<WeatherData> db_weather_list = hbw_repos.DBSelect();
        hbw_repos.DBDeleteList(weather_list);
        //WeatherExtractor.printWeatherList(db_weather_list);
        hbw_repos.close();
            
    }
        
        
        
    
    }




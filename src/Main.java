
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import db.DBWeatherRepository;
import db.DBWeatherUpdater;
import logging.PropLogger;
import model.WeatherData;
import property.CityConfig;
import validation.Validator;
import weather.WeatherExtractor;

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
        
            
        DBWeatherRepository dbw_repos = new DBWeatherRepository();
        dbw_repos.DBInsert(weather_list);
        weather_list.get(0).setFeel_temp(666);
        dbw_repos.DBUpdate(weather_list);
        //List<WeatherData> db_weather_list = dbw_repos.DBSelect();
        //dbw_repos.DBDelete(weather_list);
        //WeatherExtractor.printWeatherList(db_weather_list);
        dbw_repos.close();
            
    }
        
        
        
    
    }




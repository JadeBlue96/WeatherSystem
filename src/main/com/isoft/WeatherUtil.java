package main.com.isoft;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import main.com.isoft.base.logging.PropLogger;
import main.com.isoft.base.property.CityConfig;
import main.com.isoft.base.validation.Validator;
import main.com.isoft.base.weather.WeatherExtractor;
import main.com.isoft.rest.controller.WeatherController;
import main.com.isoft.rest.db.model.WeatherData;

@Component
public class WeatherUtil implements CommandLineRunner{
    
    @Autowired
    private WeatherController w_ctr;
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    public void startWeatherService() {
        List<CityConfig> cities = new ArrayList<CityConfig>();
        boolean isXMLValidationType = false;
        cities = Validator.getValidCitiesByType(isXMLValidationType);
        WeatherExtractor weather_extractor = new WeatherExtractor();
        List<WeatherData> weather_list = weather_extractor.getDataForCity(cities);
        w_ctr.insertWeatherList(weather_list);
        //weather_list = w_ctr.getWeatherData();
        WeatherExtractor.printWeatherList(weather_list);
        logger.log(Level.INFO, "Saved queried data to database.");
    }

    @Override
    public void run(String... args) throws Exception {
        //startWeatherService();
        
    }
    
}

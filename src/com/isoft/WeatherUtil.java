package com.isoft;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.isoft.base.logging.PropLogger;
import com.isoft.base.property.CityConfig;
import com.isoft.base.validation.Validator;
import com.isoft.base.weather.WeatherExtractor;
import com.isoft.rest.controller.WeatherController;
import com.isoft.rest.db.model.WeatherData;
import com.isoft.rest.db.repository.WeatherRepository;

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
        startWeatherService();
        
    }
    
}

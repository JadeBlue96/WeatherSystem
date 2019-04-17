package main.com.isoft;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import main.com.isoft.base.logging.PropLogger;
import main.com.isoft.base.property.CityConfig;
import main.com.isoft.base.validation.Validator;
import main.com.isoft.base.weather.WeatherExtractor;
import main.com.isoft.rest.controller.WeatherController;
import main.com.isoft.rest.db.model.WeatherData;
import main.com.isoft.rest.db.repository.WeatherRepository;


@SpringBootApplication
public class Main {
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    
    public static void main(String[] args)
    {
        SpringApplication.run(Main.class, args);
        
        /*
        HBWeatherRepository hbw_repos = new HBWeatherRepository();
        hbw_repos.DBInsertList(weather_list);
        //weather_list.get(0).setFeel_temp(666);
        //hbw_repos.DBUpdateList(weather_list);
        //List<WeatherData> db_weather_list = hbw_repos.DBSelect();
        //hbw_repos.DBDeleteList(weather_list);
        //WeatherExtractor.printWeatherList(db_weather_list);
        hbw_repos.close();
        */  
    }
    
    @Bean
    public CommandLineRunner run(WeatherRepository w_repos) throws Exception {
        
        return (String[] args) -> {
        
        
        List<CityConfig> cities = new ArrayList<CityConfig>();
        boolean isXMLValidationType = false;
        cities = Validator.getValidCitiesByType(isXMLValidationType);
        WeatherExtractor weather_extractor = new WeatherExtractor();
        List<WeatherData> weather_list = weather_extractor.getDataForCity(cities);
        w_repos.saveAll(weather_list);
        //weather_list = w_ctr.getWeatherData();
        WeatherExtractor.printWeatherList(weather_list);
        logger.log(Level.INFO, "Saved queried data to database.");
        
        };
    }
    
    
        
        
        
    
    }




package com.isoft;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.isoft.base.logging.PropLogger;


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
    
    
        
        
        
    
    }




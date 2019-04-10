package com.isoft.rest.db;

import java.util.List;

import com.isoft.rest.db.model.WeatherData;

/*
 * Database CRUD operations and connection management.
 */
public interface IWeatherRepository {
    
    /*
    * Applies CRUD operations for a list of retrieved weather objects.
    * 
    * @param weather_list      the list of retrieved weather objects from the weather service
    */
    public List<WeatherData> DBSelect();
    public void DBDeleteList(List<WeatherData> weather_list);
    public void DBUpdateList(List<WeatherData> weather_list);
    public void DBInsertList(List<WeatherData> weather_list);
    
    public void close();


}

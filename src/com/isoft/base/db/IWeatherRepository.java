package com.isoft.base.db;

import java.util.List;

import com.isoft.base.model.WeatherData;

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
    public void DBDelete(List<WeatherData> weather_list);
    public void DBUpdate(List<WeatherData> weather_list);
    public void DBInsert(List<WeatherData> weather_list);
    
    public void close();
    public DBConnector getConnector();


}

package db;

import java.util.List;

import model.WeatherData;


/*
* Database CRUD operations and connection management.
*/
public interface IWeatherRepository {
    
    public List<WeatherData> DBSelect();
    public void DBDelete(List<WeatherData> weather_list);
    public void DBUpdate(List<WeatherData> weather_list);
    public void DBInsert(List<WeatherData> weather_list);
    
    public void close();
    public DBConnector getConnector();


}

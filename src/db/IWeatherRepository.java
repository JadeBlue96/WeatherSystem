package db;

import java.util.List;

import model.WeatherData;

public interface IWeatherRepository {
    
    public List<WeatherData> DBSelect();
    public void DBDelete(List<WeatherData> weather_list);
    public void DBUpdate(List<WeatherData> weather_list);
    public void DBInsert(List<WeatherData> weather_list);
    
    public void close();
    public DBConnector getConnector();


}

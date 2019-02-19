package db;

import java.util.List;

import model.WeatherData;

public interface IWeatherRepository {
	
	public List<WeatherData> DBOToModelObjects();
	public void insertWeatherData(List<WeatherData> weather_list);

}

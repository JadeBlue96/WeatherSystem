package db;

import java.util.List;

import model.WeatherData;

public interface IWeatherRepository {
	
	public List<WeatherData> findAll();
	public long insertWeatherData(WeatherData weather_data);

}

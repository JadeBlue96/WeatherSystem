package weather;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import model.Additional;
import model.WeatherData;
import model.Wind;

public class WeatherDeserializer {
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	public WeatherData createWeatherData(String file_name) {
		
		WeatherData weather_data = new WeatherData();
		Wind wind_data = new Wind();
		Additional add_data = new Additional();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
		    String line;
		    
		    while ((line = br.readLine()) != null) {
		    	String[] line_values = line.split(": ");
		    	if(line_values[0].equals(WType.TEMP.toString()))
		    	{
		    		weather_data.setTemp(Integer.parseInt(line_values[1]));
		    	}
		    	else if(line_values[0].equals(WType.FEELTEMP.toString()))
		    	{
		    		weather_data.setFeel_temp(Integer.parseInt(line_values[1]));
		    	}
		    	else if(line_values[0].equals(WType.WINDST.toString()))
		    	{
		    		wind_data.setWind_status(line_values[1]);
		    	}
		    	else if(line_values[0].equals(WType.WINDDIR.toString()))
		    	{
		    		wind_data.setWind_direction(line_values[1]);
		    	}
		    	else if(line_values[0].equals(WType.WIND.toString()))
		    	{
		    		wind_data.setWind_spd(Double.parseDouble(line_values[1]));
		    	}
		    	else if(line_values[0].equals(WType.STATUS.toString()))
		    	{
		    		weather_data.setStatus(line_values[1]);
		    	}
		    	else if(line_values[0].equals(WType.HUMIDITY.toString()))
		    	{
		    		add_data.setHumidity(Integer.parseInt(line_values[1]));
		    	}
		    	else if(line_values[0].equals(WType.PRESSURE.toString()))
		    	{
		    		add_data.setPressure(Integer.parseInt(line_values[1]));
		    	}
		    	else if(line_values[0].equals(WType.VISIBILITY.toString()))
		    	{
		    		add_data.setVisibility(Double.parseDouble(line_values[1]));
		    	}
		    	else 
		    	{
		    		logger.log(Level.SEVERE, this.getClass().getName().toString() + "Unknown site name.");
		    		return null;
		    	}
		    	 	
		    }
		    if(add_data != null)	weather_data.setAdditional_data(add_data);
	    	if(wind_data != null)	weather_data.setWind_data(wind_data);
	    	return weather_data;
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, this.getClass().getName().toString() + "Values file not found.");
    		return null;
		} catch (IOException e) {
			logger.log(Level.SEVERE, this.getClass().getName().toString() + "Unknown file format.");
    		return null;
		}
		
		/*
		if(values != null)
		{
			for(String value: values) {
				if(site_name.equals("Dir"))
				{
					temp = Double.valueOf(values.get(0));
					wind_status = values.get(1);
					wind_spd = Double.valueOf(values.get(2));
					WeatherData weather_data = new WeatherData(temp, status, new Wind(wind_spd, wind_status, wind_direction), 
							new Additional(humidity, visibility, pressure));
				}
				else if(site_name.equals("Dalivali"))
				{
					temp = Double.valueOf(values.get(0));
					status = values.get(1);
					wind_spd = Double.valueOf(values.get(2));
					wind_direction = values.get(3);
					if(values.size() > 4)
					{
						pressure = Integer.valueOf(values.get(4));
						humidity = Integer.valueOf(values.get(5));
					}
					WeatherData weather_data = new WeatherData(temp, status, new Wind(wind_spd, wind_status, wind_direction), 
							new Additional(humidity, visibility, pressure));
				}
				else if(site_name.equals("Sinoptik"))
				{
					temp = Double.valueOf(values.get(0));
					wind_status = values.get(1);
					if(values.size() > 2)
					{
					pressure = Integer.valueOf(values.get(2));
					humidity = Integer.valueOf(values.get(3));
					visibility = Double.valueOf(values.get(4));
					}
					WeatherData weather_data = new WeatherData(temp, status, new Wind(wind_spd, wind_status, wind_direction), 
							new Additional(humidity, visibility, pressure));
				}
				else {
					logger.log(Level.SEVERE, this.getClass().getName().toString() + "Unknown site name.");
					return;
				}
				*/
				
			}
	
	public void printWeatherData(WeatherData weather_data)
	{
		if(weather_data != null) System.out.println(weather_data.toString());
	}
		
}
	


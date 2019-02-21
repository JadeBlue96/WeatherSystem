package weather;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import model.Additional;
import model.ConfigData;
import model.WeatherData;
import model.Wind;

public class WeatherDeserializer {
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	public WeatherData createWeatherData(String file_name) {
		
		WeatherData weather_data = new WeatherData();
		Wind wind_data = new Wind();
		Additional add_data = new Additional();
		ConfigData config_data;
		
		String[] file_name_info = file_name.split("_");
		String[] file_site_info = file_name_info[3].split("\\.");
		config_data = new ConfigData(file_name_info[1], file_name_info[2], file_site_info[0]);
		
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
		    		if(!line_values[1].equals("-"))
		    		{
		    			weather_data.setFeel_temp(Integer.parseInt(line_values[1]));
		    		}
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
	    	if(config_data != null) weather_data.setConfig_data(config_data);
	    	return weather_data;
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, this.getClass().getName().toString() + "Values file not found.");
    		return null;
		} catch (IOException e) {
			logger.log(Level.SEVERE, this.getClass().getName().toString() + "Unknown file format.");
    		return null;
		}
		
				
	}
	
	public void printWeatherData(WeatherData weather_data)
	{
		if(weather_data != null) System.out.println(weather_data.toString());
	}
		
}
	


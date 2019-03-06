package weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import logging.PropLogger;
import model.WeatherData;
import property.CityConfig;

public class ExtractorService {
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	private final static Integer NUM_THREADS = 5;
	private final static Integer MS_BEFORE_TERMINATION = 10000;
	List<WeatherData> weather_list = new ArrayList<WeatherData>();
	
	ExecutorService weather_service = Executors.newFixedThreadPool(NUM_THREADS);
	
	public void runService(CityConfig city)
	{
		logger.info("Starting async http service.");
		weather_service.execute(new Runnable() {
		    public void run() {
		    	
		    	WeatherData weather_data = new WeatherData();
		    	WeatherExtractor weather_extractor = new WeatherExtractor();
		    	WeatherDeserializer weather_deserializer = new WeatherDeserializer();
		    	
				HashMap<String,String> url_map = city.getUrl_map(); 
				
				try {
					weather_extractor.retrieveUrlData(city);
					for(HashMap.Entry<String, String> url_entry: url_map.entrySet())
					{
						for(String file: weather_extractor.getWeatherFileNames())
						{
							if(file.toUpperCase().contains(((String) url_entry.getKey()).toUpperCase().toString()) && 
									(!weather_extractor.getLoadedFileNames().contains(file.toString())))
							{
								weather_data = weather_deserializer.createWeatherData(file);
								weather_list.add(weather_data);
								weather_extractor.getLoadedFileNames().add(file);
							}
						}
					}
					
				}
				catch (MalformedURLException e) {
					logger.log(Level.SEVERE, this.getClass().getName().toString() + ": Incorrect URL format.");
					return;
				} catch (IOException e) {
					logger.log(Level.SEVERE, this.getClass().getName().toString() + ": Incorrect HTML format.");
					return;
				}
				}

		    }
		);

	}
	
	public void shutdownService()
	{
		weather_service.shutdown();
		try {
		    weather_service.awaitTermination(MS_BEFORE_TERMINATION, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			weather_service.shutdownNow();
			logger.log(Level.SEVERE, "Interrupted shutdown.");
			return;	
		}
	}
	
	public List<WeatherData> getWeatherList() {
		return weather_list;
	}
	
	
}

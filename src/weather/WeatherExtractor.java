package weather;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import logging.PropLogger;
import model.WeatherData;
import property.CityConfig;

public class WeatherExtractor {
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	private Set<String> weather_file_names = new HashSet<String>();
	private Set<String> loaded_file_names = new HashSet<String>();
	
	public void retrieveUrlData(CityConfig city) throws MalformedURLException, IOException
    {
		
		city.getUrl_map().entrySet().parallelStream().forEach((ent) ->  {
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(ent.getValue().toString());
			HttpResponse response;
			try {
				response = client.execute(request);
			
			
			logger.info("Response Code : " + response.getStatusLine().getStatusCode());
		
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		
		    writeHtmlToFile(result.toString(), ent.getKey().toString(), city);
		    parseDataFromHTML(result.toString(), ent.getKey().toString(), city);
			}
		    catch (ClientProtocolException e) {
		    	logger.log(Level.SEVERE, e.getMessage());
		    	return;
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
				return;
			}
		    }
		);
			
		/*
		Long timeStarted = System.currentTimeMillis();
		city.getUrl_map().entrySet().stream().forEach((ent) ->  {
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(ent.getValue().toString());
			HttpResponse response;
			try {
				response = client.execute(request);
			
			
			logger.info("Response Code : " + response.getStatusLine().getStatusCode());
		
			BufferedReader rd = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
		
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
		
		    writeHtmlToFile(result.toString(), ent.getKey().toString(), city);
		    parseDataFromHTML(result.toString(), ent.getKey().toString(), city);
			}
		    catch (ClientProtocolException e) {
		    	logger.log(Level.SEVERE, e.getMessage());
		    	return;
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
				return;
			}
		    }
		);
		System.out.println("Sequential stream query time: " + (System.currentTimeMillis() - timeStarted));
		*/
        
    }
	
	public void writeToFile(HashMap<String, String> tagValues, String file_name) throws IOException
	{
		Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(file_name), "utf-8"));
		tagValues.entrySet().parallelStream().forEach(ent -> {
			try {
				writer.write(ent.getKey() + ": " + ent.getValue() + "\n");
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage());
		    	return;
			}
		});
		writer.close();
	}
	
	public HashMap<String, String> mapMatches(List<Matcher> matchers, HashMap<String, HashMap<WType, String>> reg_map) {
		HashMap<String, String> mapped_values = new HashMap<String, String>();
		matchers.parallelStream().forEach(matcher -> {
			while (matcher.find()) {
				String pattern = matcher.pattern().pattern();
				String key = "";
				
				for (HashMap.Entry<String, HashMap<WType, String>> ent: reg_map.entrySet()) {
					HashMap<WType,String> reg_inner_map = (HashMap<WType, String>) ent.getValue();
					for(HashMap.Entry<WType, String> inner_ent: reg_inner_map.entrySet())
					{
				        if (Objects.equals(pattern, inner_ent.getValue().toString())) {
				            key = inner_ent.getKey().toString();
				            break;
				        }
					}
			    }
				if(!key.equals("") && !pattern.equals(""))	mapped_values.put(key, matcher.group(1));
		    }
		});
		return mapped_values;
	}
	
	public void parseDataFromHTML(String html, String site_name, CityConfig city) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		HashMap<String, HashMap<WType,String>> reg_map = city.getSite_map();
		HashMap<String, String> tagValues = new HashMap<String, String>();
		final List<Matcher> matchers = new ArrayList<Matcher>();
		
		
		reg_map.entrySet().parallelStream().forEach((reg_entry) -> {
			if(reg_entry.getKey().toString().equals(site_name))
			{
				HashMap<WType,String> reg_inner_map = (HashMap<WType, String>) reg_entry.getValue();
				reg_inner_map.entrySet().forEach(reg_inner_entry -> {
					String str_entry = reg_inner_entry.getValue().toString();
					Pattern p_entry = Pattern.compile(str_entry, Pattern.DOTALL);
					matchers.add(p_entry.matcher(html));
				});
			}
		}
		);
		
		
		if(matchers != null) {
			tagValues = mapMatches(matchers, reg_map);
			if (!city.getUrl_map().keySet().contains(site_name)){
				String message = this.getClass().toString() + ": Invalid site name.";
				logger.log(Level.SEVERE, message);
				return;
			}
			if(tagValues != null)
			{
				String file = "output/meteo_" + city.getCityName() + "_" + city.getCountryName() + "_" + site_name + ".txt";
				writeToFile(tagValues, (file).toLowerCase());
				weather_file_names.add(file);
			}
		}
		
	}
	
	public void writeHtmlToFile(String html, String site_name, CityConfig city){
		
		try {
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(("output/html_" + city.getCityName() + "_" + city.getCountryName() + "_" + site_name + ".txt").toLowerCase()), 
					"utf-8"));
			
			if (!city.getUrl_map().keySet().contains(site_name)){
				String message = this.getClass().toString() + ": Invalid site name.";
				logger.log(Level.SEVERE, message);
				writer.close();
				return;
			}
			
			writer.write(html);
			writer.close();
		}
		catch (UnsupportedEncodingException e) {
			String message = this.getClass().toString() + ": Unsupported encoding.";
			logger.log(Level.SEVERE, message);
			return;
		} catch (FileNotFoundException e) {
			String message = this.getClass().toString() + ": Output file not found.";
			logger.log(Level.SEVERE, message);
			return;
		} catch (IOException e) {
			String message = this.getClass().toString() + ": File format error.";
			logger.log(Level.SEVERE, message);
			return;
		}
	}
	
	public static void printWeatherList(List<WeatherData> weather_list)
	{
		weather_list.forEach(weather -> {
			System.out.println(weather.toString() + "\n");
		});
	}
	
	public List<WeatherData> getDataForCity(List<CityConfig> cities) {
		logger.info("Retrieving HTML data...");
		ExtractorService async_service = new ExtractorService();
		
		Long timeStarted = System.currentTimeMillis();
		for(CityConfig city: cities) {
			async_service.runService(city);
		}
		
		async_service.shutdownService();
		System.out.println("Parallel stream query time: " + (System.currentTimeMillis() - timeStarted) + "ms");

		return async_service.getWeatherList();
		
		
		}
	
	public Set<String> getWeatherFileNames() {
		return weather_file_names;
	}
	
	public Set<String> getLoadedFileNames() {
		return loaded_file_names;
	}
		

	}
	


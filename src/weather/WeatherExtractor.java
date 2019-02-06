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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import logging.PropLogger;
import model.WeatherData;
import property.CityConfig;

public class WeatherExtractor {
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	private List<String> weather_file_names = new ArrayList<String>();
	
	public void retrieveUrlData(CityConfig city) throws MalformedURLException, IOException
    {
		for(HashMap.Entry ent: city.getUrl_map().entrySet())
		{
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(ent.getValue().toString());
			HttpResponse response = client.execute(request);
			
		
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
        
    }
	
	public void writeToFile(HashMap<String, String> values, String file_name) throws IOException
	{
		Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(file_name), "utf-8"));
		for (HashMap.Entry ent: values.entrySet()) {
				writer.write(ent.getKey() + ": " + ent.getValue() + "\n");
		}
		writer.close();
	}
	
	public HashMap<String, String> mapMatches(List<Matcher> matchers, HashMap<String, HashMap<WType, String>> reg_map) {
		HashMap<String, String> mapped_values = new HashMap<String, String>();

		for (Matcher matcher: matchers) {			
			while (matcher.find()) {
				String pattern = matcher.pattern().pattern();
				String key = "";
				for (HashMap.Entry ent: reg_map.entrySet()) {
					HashMap<WType,String> reg_inner_map = (HashMap<WType, String>) ent.getValue();
					for(HashMap.Entry inner_ent: reg_inner_map.entrySet())
					{
				        if (Objects.equals(pattern, inner_ent.getValue().toString())) {
				            key = inner_ent.getKey().toString();
				            break;
				        }
					}
			    }
				if(!key.equals("") && !pattern.equals(""))	mapped_values.put(key, matcher.group(1));
		    }
		}
		return mapped_values;
	}
	
	public void parseDataFromHTML(String html, String site_name, CityConfig city) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		HashMap<String, HashMap<WType,String>> reg_map = city.getSite_map();
		HashMap<String, String> tagValues = new HashMap<String, String>();
		final List<Matcher> matchers = new ArrayList<Matcher>();
		
		for(HashMap.Entry reg_entry:reg_map.entrySet()) {
			if(reg_entry.getKey().toString().equals(site_name))
			{
				HashMap<WType,String> reg_inner_map = (HashMap<WType, String>) reg_entry.getValue();
				for(HashMap.Entry reg_inner_entry:reg_inner_map.entrySet())
				{
					String str_entry = reg_inner_entry.getValue().toString();
					Pattern p_entry = Pattern.compile(str_entry, Pattern.DOTALL);
					matchers.add(p_entry.matcher(html));
				}
			}
		}
		
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
	
	public void getDataForCity(CityConfig city) {
		logger.info("Retrieving HTML data...");
		
		HashMap<String,String> url_map = city.getUrl_map(); 
		HashMap<String,HashMap<WType,String>> reg_map = city.getSite_map();
		HashMap<WType,String> reg_inner_map = new HashMap<WType,String>();
		WeatherDeserializer weather_deserializer = new WeatherDeserializer();
		WeatherData weather_data = new WeatherData();
		
			try {
				retrieveUrlData(city);
				for(HashMap.Entry url_entry: url_map.entrySet())
				{
					for(String file: weather_file_names)
					{
						if(file.toUpperCase().contains(((String) url_entry.getKey()).toUpperCase().toString()))
						{
							weather_data = weather_deserializer.createWeatherData(file);
							weather_deserializer.printWeatherData(weather_data);
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
	


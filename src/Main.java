import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import db.DBWeatherRepository;
import logging.PropLogger;
import model.Additional;
import model.WeatherData;
import model.Wind;
import property.CityConfig;
import property.CityPropReader;
import validation.IValidator;
import weather.WeatherExtractor;

public class Main {
	
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	/*
	
	private static final Pattern DIR_TEMP_REGEX = Pattern.compile("<span class=\"temp\">\\s+<span class=\\\"degrees\\\">(.+?)°C<\\/span>", Pattern.DOTALL);
	private static final Pattern DIR_WIND_REGEX = Pattern.compile("</strong>\\s(.+?)\\sкм/час", Pattern.DOTALL);
	private static final Pattern DIR_ST_REGEX = Pattern.compile("Време:</strong>\\s(.+?)\\s<br>", Pattern.DOTALL);
	
	private static final Pattern DV_TEMP_REGEX = Pattern.compile("<span class=\"number\">(.+?)</span>", Pattern.DOTALL);
	private static final Pattern DV_ST_REGEX = Pattern.compile("<h2 class=\"descr\">(.+?)</h2>", Pattern.DOTALL);
	private static final Pattern DV_WIND_REGEX = Pattern.compile("<span id=\"wind-today\">(.+?)</span>", Pattern.DOTALL);
	private static final Pattern DV_WIND_DIR_REGEX = Pattern.compile("<span id=\"dr-today\">(.+?)</span>", Pattern.DOTALL);
	private static final Pattern DV_PRESSURE_REGEX = Pattern.compile("<span id=\"atmP-today\">(.+?)</span>", Pattern.DOTALL);
	private static final Pattern DV_HUMIDITY_REGEX = Pattern.compile("<span id=\"rain-today\">(.+?)</span>", Pattern.DOTALL);
	
	private static final Pattern SP_TEMP_REGEX = Pattern.compile("<span class=\"wfCurrentTemp\">(.+?)&deg.C</span>", Pattern.DOTALL);
	private static final Pattern SP_WIND_REGEX = Pattern.compile("wfCurrentWind\\s.+\\\"\\stitle=\\\".*.\\s.+\\\">\\s+(.+?)\\s", Pattern.MULTILINE);
	private static final Pattern SP_WIND_ST_REGEX = Pattern.compile("wfCurrentWind\\s.+\\\"\\stitle=\\\".*.\\s(.+?)\\\">\\s+\\d.\\d\\s");
	private static final Pattern SP_PRESSURE_REGEX = Pattern.compile("Атмосферно налягане:<\\/span>\\s+<span class=\\\"wfCurrentValue\\\">(.+?)\\shPa", 
			Pattern.MULTILINE);
	private static final Pattern SP_HUMIDITY_REGEX = Pattern.compile("Влажност:<\\/span>\\s+<span class=\\\"wfCurrentValue\\\">(.+?)%<\\/span>", 
			Pattern.MULTILINE);
	private static final Pattern SP_VISIBILITY_REGEX = Pattern.compile("Видимост:<\\/span>\\s+<span class=\\\"wfCurrentValue\\\">(.+?) km<\\/span>", 
			Pattern.MULTILINE);
	
	*/
	
	
	
	
	public static List<CityConfig> getValidCities(boolean isXMLValidationType) throws ClassNotFoundException, InstantiationException, IllegalAccessException
	{
		List<CityConfig> cities = new ArrayList<CityConfig>();
		final Class<?> file_validator = Class.forName("validation.PropFileValidator");
		final Class<?> obj_validator = Class.forName("validation.PropObjectValidator");
		final Class<?> xml_validator = Class.forName("xml.XmlPropertyValidator");
		IValidator validator;		
		
		if(!isXMLValidationType)
		{
			validator = (IValidator) file_validator.newInstance();
			boolean isValidFile = false;
			isValidFile = validator.validate();
			if(isValidFile)
			{
				validator = (IValidator) obj_validator.newInstance();
				validator.validate();
				cities = validator.getValidCities(cities);
				validator.printValidCities(cities);
			}
		}
		else {
			validator = (IValidator) xml_validator.newInstance();
			boolean isValidXML = validator.validate();
			if(isValidXML)
			{
				cities = xml.XmlPropertyParser.readXMLObjects();
				if(cities != null)
				{
					validator.printValidCities(cities);
				}
			}
		}
		return cities;
	}

	

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		List<CityConfig> cities = new ArrayList<CityConfig>();
		boolean isXMLValidationType = false;
		cities = getValidCities(isXMLValidationType);
		WeatherExtractor weather_extractor = new WeatherExtractor();
		List<WeatherData> weather_list = weather_extractor.getDataForCity(cities.get(0));

		
		CityPropReader db_credentials = new CityPropReader("resource/db_cred.properties");
		final String username = db_credentials.getPropertyValue("username");
		final String password = db_credentials.getPropertyValue("password");
		final String url = db_credentials.getPropertyValue("url");
		
		if(username != null && password != null && url != null)
		{
			DBWeatherRepository crud = new DBWeatherRepository(url,username,password);
			crud.insertWeatherData(weather_list.get(0));
			crud.close();
		}
		
		
		
		
		/*
		regex.site.temp = regex;
		regex.site.humidity = regex;
		
		city.name.sites = <site,url>;<site,url>
		city.name.name = "";
		city.name.country = "";
		
		cities = all_props.getItems();
		cities = PropObjectValidator.validatePropObjects(cities, all_props);
		
		for(CityInfo city: cities)
		{
			System.out.println(city.ToString());
		}	
		*/
		
			/*
			if(cities != null)
			{
				getDataForCity(cities.get(1));
			}
			*/		
	}
	}



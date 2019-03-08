
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.DBWeatherRepository;
import model.WeatherData;
import property.CityConfig;
import property.CityPropReader;
import validation.IValidator;
import weather.WeatherExtractor;

public class Main {
	
	
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

	

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		
		List<CityConfig> cities = new ArrayList<CityConfig>();
		boolean isXMLValidationType = false;
		cities = getValidCities(isXMLValidationType);
		WeatherExtractor weather_extractor = new WeatherExtractor();
		List<WeatherData> weather_list = weather_extractor.getDataForCity(cities);
		WeatherExtractor.printWeatherList(weather_list);
		
		
		
		CityPropReader db_credentials = new CityPropReader("../resources/db_credentials/db_cred.properties");
		final String username = db_credentials.getPropertyValue("username");
		final String password = db_credentials.getPropertyValue("password");
		final String url = db_credentials.getPropertyValue("url");
		
		if(username != null && password != null && url != null)
		{
			
			DBWeatherRepository dbw_repos = new DBWeatherRepository(url, username, password);
			dbw_repos.DBInsert(weather_list);
			//weather_list.get(0).setFeel_temp(666);
			//dbw_repos.DBUpdate(weather_list);
			//List<WeatherData> db_weather_list = dbw_repos.DBSelect();
			//dbw_repos.DBDelete(weather_list);
			//WeatherExtractor.printWeatherList(db_weather_list);
			dbw_repos.close();
			
		}
		
		
		
	
	}
	}



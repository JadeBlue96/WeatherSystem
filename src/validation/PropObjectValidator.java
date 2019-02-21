package validation;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import property.CityConfig;
import property.CityPropReader;
import weather.WType;

public class PropObjectValidator extends Validator implements IValidator{
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	private List<CityConfig> valid_cities = new ArrayList<CityConfig>();
	private List<String> errorMessages = new ArrayList<String>();
	private CityPropReader prop_reader;
	
	
	public boolean checkFields(CityConfig city, int idx) {
		
			logger.info("Validating city "+ city.getCityName());
			if(city.getCityName().equals("") || city.getCityName().equals(" "))
			{
				String message = this.getClass().getName() + ": Invalid key at line " + idx + ".";
				errorMessages.add(message);
				logger.log(Level.SEVERE, message);
				return false;
			}
			else if(city.getCountryName().equals("") || city.getCountryName().equals(" "))
			{
				String message = this.getClass().getName() + ": Country name not specified for city " + city.getCityName() + " at line " + idx + ".";
				errorMessages.add(message);
				logger.log(Level.SEVERE, message);
				return false;
			}
			else if(city.getUrl_map().toString().equals("") || city.getUrl_map().toString().equals(" "))
			{
				String message = this.getClass().getName() + ": URL map for " + city.getCityName() + " is empty at line " + idx + ".";
				errorMessages.add(message);
				logger.log(Level.SEVERE, message);
				return false;
			}
			else {
				for(HashMap.Entry<String,String> ent: city.getUrl_map().entrySet())
				{
					String key = ent.getKey();
					String value = ent.getValue();
					if(key.toString().equals("") || key.toString().equals(" ")) {
						String message = this.getClass().getName() + ": Missing key for value " + value.toString() + " at line " + idx + ".";
						errorMessages.add(message);
						logger.log(Level.SEVERE, message);
						return false;
					}
					else if(value.toString().equals("") || value.toString().equals(" "))
					{
						String message = this.getClass().getName() + ": Missing value for key " + key.toString() + " at line " + idx + ".";
						errorMessages.add(message);
						logger.log(Level.SEVERE, message);
						return false;
					}  
					else if(!(value.toLowerCase().toString().contains(key.toLowerCase().toString())))
					{
						String message = this.getClass().getName() + ": " + key.toString() + " does not exist in url: " + value.toString() + " at line " + idx + ".";
						errorMessages.add(message);
						logger.log(Level.SEVERE, message);
						return false;
					}
				}
			}
		return true;
		
	}
	
	
	@Override
	public boolean validate() {
		List<CityConfig> cities = new ArrayList<CityConfig>();
		boolean isValid = false;
		cities = getValidPropObjects(cities);
		if(cities != null)
		{
			isValid = true;
		}
		logger.info("Validation complete for "+ this.getClass().getName() +" with result: "+ isValid);
		return isValid;
	}

	
	public List<CityConfig> initCities()
	{
		List<CityConfig> cities = new ArrayList<CityConfig>();
		prop_reader = new CityPropReader("../resources/prop_configs/metdata.properties");
		cities = prop_reader.buildCityConfig();
		return cities;
	}
	
	private static boolean containsCity(List<CityConfig> cities, String city_name) {
	    for (CityConfig city: cities) {
	        if (city.getCityName().equals(city_name)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static List<CityConfig> getValidPropObjects(List<CityConfig> cities) {
		
		boolean valid_fields = false, valid_urls = false, valid_regex = false;
		List<CityConfig> valid_cities = new ArrayList<CityConfig>();
		HashMap<String, HashMap<WType,String>> reg_map = new HashMap<String, HashMap<WType, String>>();
		HashMap<WType,String> inner_map = new HashMap<WType,String>();
		
		if(cities != null)
		{
			/*
			for(CityInfo city: cities) {
				System.out.println(city.ToString());
			}
			*/
			for(final ListIterator<CityConfig> it = cities.listIterator(); it.hasNext();) {
				CityConfig city = it.next();
				int city_idx = it.nextIndex();
				PropObjectValidator validator = new PropObjectValidator();
				UrlValidator url_validator = new UrlValidator();
				RegexValidator reg_validator = new RegexValidator();
				
				valid_fields = validator.checkFields(city, city_idx);
				if(valid_fields)
				{
					for(Object url: city.getUrl_map().values()) {
							valid_urls = url_validator.validateURL(url.toString(), city_idx);		
							if(valid_urls == false) break;
					}
					if(valid_urls) 
					{
						for(Map.Entry<String, HashMap<WType, String>> ent: city.getSite_map().entrySet())
						{
							inner_map = new HashMap<WType, String>();
							HashMap<WType, String> cur_map = (HashMap<WType, String>) ent.getValue();
							for(Map.Entry<WType, String> reg_entry: cur_map.entrySet())
							{
								valid_regex = reg_validator.validateRegex(reg_entry.getValue().toString(), city_idx);
								if(valid_regex == true) inner_map.put(WType.valueOf(reg_entry.getKey().toString()), reg_entry.getValue().toString());
							}
							reg_map.put(ent.getKey().toString(), inner_map);
							
						}
						city.setSite_map(reg_map);
						if(containsCity(valid_cities, city.getCityName()) == false)
						{
							valid_cities.add(city);
						}
					}
					valid_urls = false;
					valid_fields = false;
				}
				
			}
		}
		
		
		return valid_cities;
	}
   
    public List<CityConfig> getValidCities(List<CityConfig> cities)
    {
    	cities = initCities();
    	valid_cities = getValidPropObjects(cities);
    	return valid_cities;
    }
    
    public void printValidCities(List<CityConfig> cities)
    {
    	for(CityConfig city: cities) {
    		System.out.println(city.ToString() + "\n");
    	}
    }


	@Override
	public List<String> getValidationMessages() {
		return errorMessages;
	}


}

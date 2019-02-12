package property;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import weather.WType;

public class CityPropReader extends CityConfig {
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	private Properties prop;
	private String[] city_data;
    private String[] url_data;
    private String[] regex_data;
    private String[] site_elements;
    private HashMap<String, HashMap<WType, String>> weather_map;
    private HashMap<WType, String> wtype_map;
    CityPropReader all_props;
    
	
	public CityPropReader(String file_name) {
		
		/*
		Class clazz = Class.forName(propertyReaderClassName);
		IPropertyReader rdr = clazz.newInstance();
		rdr.setSource(propertySource);
		List<CityInfo> citiesInfo = rdr.read();
		*/
		
		
		
		InputStream is = null;
		logger.info("Initializing property file.");
		
        try {
            this.prop = new SequentialProperties();
            is = this.getClass().getResourceAsStream(file_name);
            if(is != null)
            {
            prop.load(is);
            }
        } catch (FileNotFoundException e) {
        	logger.log(Level.SEVERE, "Missing property file.");
            return;
        } catch (IOException e) {
        	logger.log(Level.SEVERE, "Incorrect property file format.");
            return;
        } 
        
	}
	
	public Set<String> initTypes() {
		Set<String> enum_values = new HashSet<String>();
		for (WType type: WType.values())
		{
			enum_values.add(type.name());
		}
		return enum_values;
	}
	
	public String escapeDelim(String city_str)
	{
		String new_str = city_str;
		for (int i=0; i<city_str.length() - 1; i++)
    	{
    		if(city_str.charAt(i) == ';' || city_str.charAt(i) == ',')
    		{
    			if((!Character.isDigit(city_str.charAt(i+1))) && (!Character.isLetter(city_str.charAt(i+1))))
    			{
    				new_str = city_str.substring(0, i + 1) + ' ' + city_str.substring(i + 1, city_str.length());
    			}
    		}
    	}
		return new_str;
	}
	
	public HashMap<WType, String> mapWeatherTypes(Set<String> types, String site, String value)
	{
		HashMap<WType, String> wtype_map = new HashMap<WType, String>();
		List<String> regex = new ArrayList<String>();

		for (Map.Entry e : (Set<Map.Entry<Object,Object>>)prop.entrySet()){
			String cur_key = e.getKey().toString();
			if(e.getKey().toString().contains("regex"))
			{
				regex_data = cur_key.split("\\.");
				if(regex_data[1].equals(site)) {
					if(types.contains(regex_data[2].toUpperCase()))
					{
						wtype_map.put(WType.valueOf(regex_data[2].toUpperCase()), e.getValue().toString());
					}
				}
				
			}
		}
		return wtype_map;
	}
	
	public HashMap<String, HashMap<WType, String>> mapSiteToWeatherTypes(Set<String> types, Set<String> sites, String new_str)
	{
		weather_map = new HashMap<String, HashMap<WType, String>>();
    	wtype_map = new HashMap<WType, String>();
    
		for(String cur_site: sites)
		{
			wtype_map = mapWeatherTypes(types, cur_site, new_str);
			weather_map.put(cur_site, wtype_map);
		}
    	return weather_map;
	}
	
	
	public Set<String> fillSites()
	{
		Set<String> sites = new HashSet<String>();
		for (Map.Entry e : (Set<Map.Entry<Object,Object>>)prop.entrySet()){
			String cur_key = e.getKey().toString();
			if(e.getKey().toString().contains("regex"))
			{
				regex_data = cur_key.split("\\.");
				if(regex_data[1] != null) {
					sites.add(regex_data[1]);
				}
				
			}
		}
		return sites;
	}
	
	public List<CityConfig> buildCityConfig()
	{
		all_props = new CityPropReader("resource/metdata.properties");
		Set<String> enum_values = initTypes();
    	Set<String> sites;
    	HashMap<String, HashMap<WType, String>> weather_map = new HashMap<String, HashMap<WType, String>>();
    	HashMap<String, String> url_map = new HashMap<String,String>();
    	List<CityConfig> cities = new ArrayList<CityConfig>();
    	CityConfig city = new CityConfig();
    	
    	for (Map.Entry e : (Set<Map.Entry<Object,Object>>)prop.entrySet()){
    		String cur_key = e.getKey().toString();
    		String[] cur_data = cur_key.split("\\.");
    		
    		if(cur_data[0].equals("city"))
	    	{
    		
	    	String city_str = all_props.getPropertyValue(e.getKey().toString());
	    	String new_str = escapeDelim(city_str);

	    		city_data = cur_key.split("\\.");
	    		if(city_data != null && city_data.length < 4)
	    		{
	    			if(city_data[2].equals("country")) city.setCountryName(new_str);
	    			else if(city_data[2].equals("name")) city.setCityName(new_str);
	    			else if(city_data[2].equals("sites"))
	    			{
	    				site_elements = new_str.split(";");
	    				url_map = new HashMap<String, String>();
	    				for(int i=0; i<site_elements.length; i++) {
	    					url_data = site_elements[i].split(",");
	    					if(url_data.length == 2)
	    					{
	    						url_map.put(url_data[0], url_data[1]);
	    					}
	    				}
	    				city.setUrl_map(url_map);
	    				sites = new HashSet<String>();
	    				for(Object k: url_map.keySet())
	    				{
	    					sites.add(k.toString());
	    				}
	    				weather_map = mapSiteToWeatherTypes(enum_values, sites, new_str);
	    				city.setSite_map(weather_map);
	    				cities.add(city);
	    				city = new CityConfig();
	    			}
	    			else {
	    				logger.log(Level.SEVERE, "Incorrect key in property file.");
	    			}
	    		}
	    	}
    	}
    	
    	return cities;
	}
	
	/*
	public List<CityConfig> getItems(){
		cities = new ArrayList<CityConfig>();
        HashMap<String,String> url_map;
        Set<String> enum_values = initTypes();
        
        Set<String> sites = fillSites();
        weather_map = mapSiteToWeatherTypes(enum_values);
        
        
        if (all_props != null) {
        CityConfig city;
        for (Map.Entry e : (Set<Map.Entry<Object,Object>>)prop.entrySet()){
        	
        	url_map = new HashMap<String,String>();
        	
        	wtype_map = new HashMap<WType, String>();
        	
        	String cur_key = e.getKey().toString();
        	
        	String city_str = all_props.getPropertyValue(e.getKey().toString());
        	String new_str = escapeDelim(city_str);
        	
        	
        	if(cur_key.contains("regex"))
        	{
        		regex_data = cur_key.split(".");
        		for(String cur_site: sites)
        		{
        			wtype_map = mapWeatherTypes(enum_values, cur_site, new_str);
        			weather_map.put(cur_site, wtype_map);
        		}
        	}
        	
        	
	
        	if(e.getKey().toString().contains("city"))
        	{
        		
        	}
        	//System.out.println(new_str);
			city_data = new_str.split(";");
			for(int i=1; i<city_data.length; i++) {
				url_data = city_data[i].split(",");
				if(url_data.length > 1)
				{
					url_map.put(url_data[0], url_data[1]);
					key_str = prop.getProperty(url_data[2]).toString();
					if(key_str != null)
					{
						regex_data = key_str.split("@");
						for(int k=0; k<regex_data.length; k++)
						{
							regex_elements = regex_data[k].split("~");
							if(enum_values.contains(regex_elements[0]))
							{
								weather_regex_map.put(MDType.valueOf(regex_elements[0]), regex_elements[1]);
							}
							else {
								logger.log(Level.SEVERE, "Incorrect regex key in property file.");
					            return null;
							}
						}
					}
				}
			}
			city = new CityConfig(e.getKey().toString(), city_data[0], url_map, weather_regex_map);
			cities.add(city);
		}
        
        }
        return cities;
    }

*/
	/*
	public List<CityConfig> getItems(){
		cities = new ArrayList<CityConfig>();
        HashMap<String,String> url_map;
        Set<String> enum_values = initTypes();
        CityPropReader all_props = new CityPropReader();
        
        if (all_props != null) {
        CityConfig city;
        for (Map.Entry e : (Set<Map.Entry<Object,Object>>)prop.entrySet()){
        	
        	url_map = new HashMap<String,String>();
        	weather_regex_map = new HashMap<MDType, String>();
        	String city_str = all_props.getPropertyValue(e.getKey().toString());
        	String new_str = city_str;
        	for (int i=0; i<city_str.length() - 1; i++)
        	{
        		if(city_str.charAt(i) == ';' || city_str.charAt(i) == ',' || city_str.charAt(i) == '~' || city_str.charAt(i) == '@')
        		{
        			if((!Character.isDigit(city_str.charAt(i+1))) && (!Character.isLetter(city_str.charAt(i+1))))
        			{
        				new_str = city_str.substring(0, i + 1) + ' ' + city_str.substring(i + 1, city_str.length());
        			}
        		}
        	}
        	
        	if(e.getKey().toString().contains("KEY")) continue;
        	//System.out.println(new_str);
			city_data = new_str.split(";");
			for(int i=1; i<city_data.length; i++) {
				url_data = city_data[i].split(",");
				if(url_data.length > 1)
				{
					url_map.put(url_data[0], url_data[1]);
					key_str = prop.getProperty(url_data[2]).toString();
					if(key_str != null)
					{
						regex_data = key_str.split("@");
						for(int k=0; k<regex_data.length; k++)
						{
							regex_elements = regex_data[k].split("~");
							if(enum_values.contains(regex_elements[0]))
							{
								weather_regex_map.put(MDType.valueOf(regex_elements[0]), regex_elements[1]);
							}
							else {
								logger.log(Level.SEVERE, "Incorrect regex key in property file.");
					            return null;
							}
						}
					}
				}
			}
			city = new CityConfig(e.getKey().toString(), city_data[0], url_map, weather_regex_map);
			cities.add(city);
		}
        
        }
        return cities;
    }
    */
	

    public String getPropertyValue(String key){
        return this.prop.getProperty(key);
    }

	

}

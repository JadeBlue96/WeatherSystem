package validation;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import logging.PropLogger;
import property.CityConfig;
import property.CityPropReader;

public abstract class Validator {
    
    private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
    private List<String> errorMessages = new ArrayList<String>();
    private CityPropReader prop_reader;
    private List<CityConfig> valid_cities = new ArrayList<CityConfig>();
    
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
        
        boolean valid_fields, valid_urls = false;
        List<CityConfig> valid_cities = new ArrayList<CityConfig>();
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
                valid_fields = validator.checkFields(city, city_idx);
                if(valid_fields)
                {
                    for(Object url: city.getUrl_map().values()) {
                            valid_urls = url_validator.validateURL(url.toString(), city_idx);        
                            if(valid_urls == false) 
                            {
                                break;
                            }
                    }
                    if(valid_urls) 
                    {
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
    
    public static List<CityConfig> getValidCitiesByType(boolean isXMLValidationType)
    {
        List<CityConfig> cities = new ArrayList<CityConfig>();
        Class<?> file_validator;
        
        try 
        {
            file_validator = Class.forName("validation.PropFileValidator");
            final Class<?> obj_validator = Class.forName("validation.PropObjectValidator");
            final Class<?> xml_validator = Class.forName("validation.XmlPropertyValidator");
        
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
        
        } 
        catch (ClassNotFoundException e) 
        {
            logger.log(Level.SEVERE, "Incorrect validation class name.");
            return null;
        }
        catch (InstantiationException e)
        {
            logger.log(Level.SEVERE, "Incorrect class instantiation.");
            return null;
        }
        catch (IllegalAccessException e)
        {
            logger.log(Level.SEVERE, "Incorrect access to instantiated class.");
            return null;
        }
        return cities;
    }
    
    public List<String> getValidationMessages() {
        return errorMessages;
    }
}

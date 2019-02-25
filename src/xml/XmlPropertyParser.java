package xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import logging.PropLogger;

import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;

import property.CityConfig;
import validation.RegexValidator;
import validation.UrlValidator;
import weather.WType;

public class XmlPropertyParser extends CityConfig {
	
	private final static Logger logger = Logger.getLogger(PropLogger.class.getName());
	
	private static final String CITY = "city";
	private static final String COUNTRY = "country";
	private static final String SITE = "site";
	private static final String URL = "url";
	
	
	private static final String NAME = "name";
	
	public Set<String> initTypes() {
		Set<String> enum_values = new HashSet<String>();
		for (WType type: WType.values())
		{
			enum_values.add(type.name());
		}
		return enum_values;
	}
	
	public List<CityConfig> readConfig(String config_file) {
		List<CityConfig> cities = new ArrayList<CityConfig>();
		
		try {
			
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true);
            InputStream in = new FileInputStream(config_file);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            CityConfig city = null;
            ConcurrentHashMap<String, String> url_map = new ConcurrentHashMap<String, String>();
            ConcurrentHashMap<String, ConcurrentHashMap<WType,String>> reg_outer_map = new ConcurrentHashMap<String, ConcurrentHashMap<WType,String>>();
            ConcurrentHashMap<WType,String> reg_inner_map = new ConcurrentHashMap<WType, String>();
            List<String> urls = new ArrayList<String>(), names = new ArrayList<String>();
            Set<String> enum_values = initTypes();
            UrlValidator url_validator = new UrlValidator();
            RegexValidator regex_validator = new RegexValidator();
            
            String cur_site = "";
            
            int city_idx = 1;
            
            
            while(eventReader.hasNext())
            {
            	XMLEvent event = eventReader.nextEvent();
            	
            	if(event.isStartElement())
            	{
            		StartElement startElement = event.asStartElement();

                    if (startElement.getName().getLocalPart().equals(CITY)) {
                        city = new CityConfig();

						Iterator<Attribute> attributes = startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            if (attribute.getName().toString().equals(NAME)) {
                                city.setCityName(attribute.getValue());
                            }
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(COUNTRY)) {
                        event = eventReader.nextEvent();
                        city.setCountryName(event.asCharacters().getData());
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(SITE)) {
                    	Iterator<Attribute> attributes = startElement.getAttributes();
                        Attribute attribute = attributes.next();
                        if (attribute.getName().toString().equals(NAME)) {
                        	names.add(attribute.getValue());
                        	cur_site = attribute.getValue();
                        }
                        continue;
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(URL)) {
                        event = eventReader.nextEvent();
                        boolean valid_url = url_validator.validateURL(event.asCharacters().getData(), city_idx);
                        if(valid_url) urls.add(event.asCharacters().getData());
                        continue;
                    }
                    if(enum_values.contains(event.asStartElement().getName().getLocalPart().toUpperCase()))
                    {
                    	String event_key = event.asStartElement().getName().getLocalPart().toUpperCase();
                    	event = eventReader.nextEvent();
	                    boolean valid_reg = regex_validator.validateRegex(event.asCharacters().getData(), city_idx);
	                    if(valid_reg) reg_inner_map.put(WType.valueOf(event_key), event.asCharacters().getData());
	                    continue;
                    }
            }
            	
            	if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if(endElement.getName().getLocalPart().equals(SITE))
                    {
                    	reg_outer_map.put(cur_site, reg_inner_map);
                    	reg_inner_map = new ConcurrentHashMap<WType, String>(); 
                    	
                    }
                    if (endElement.getName().getLocalPart().equals(CITY)) {
                    	if(urls.size() != names.size())
                    	{
                    		logger.log(Level.SEVERE, this.getClass().getName() + ": Names and urls don't match in the property file.");
                            return null;
                    	}
                    	
                    	Iterator<String> url_it = urls.iterator();
                    	Iterator<String> name_it = names.iterator();
                    	url_map = new ConcurrentHashMap<String, String>();
                    	
                    	
                    	while(url_it.hasNext() && name_it.hasNext())
                    	{
                    		url_map.put(name_it.next(), url_it.next());
                    	}
                    	city.setUrl_map(url_map);
                    	
                    	city.setSite_map(reg_outer_map);
                        cities.add(city);
                        
                        urls.removeAll(urls);
                        names.removeAll(names);
                        
                        reg_inner_map = new ConcurrentHashMap<WType, String>(); 
                        reg_outer_map = new ConcurrentHashMap<String, ConcurrentHashMap<WType, String>>();
                        city_idx++;
                    }
                }
            }
		}
        catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, this.getClass().getName() + ": Failed to find XML file.");
            return null;
        } catch (XMLStreamException e) {
        	logger.log(Level.SEVERE, this.getClass().getName() + ": Invalid XML format.");
            return null;
        }
        return cities;
		}
	
	public static List<CityConfig> readXMLObjects() {
		List<CityConfig> cities = new ArrayList<CityConfig>();
		XmlPropertyParser prop_parser = new XmlPropertyParser();
		cities = prop_parser.readConfig("../resources/xml_configs/wdata.xml");
		return cities;
	}
	
}


package property;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import weather.WType;

public class CityConfig {
	private String cityName;
	private String countryName;
	private ConcurrentHashMap<String, String> url_map;
	private ConcurrentHashMap<String, ConcurrentHashMap<WType,String>> site_map;
	
	
	public CityConfig()
	{
		setCityName("");
		setCountryName("");
		setSite_map(null);
		setUrl_map(null);
	}
	public CityConfig(String city, String country, ConcurrentHashMap<String, String> url_map, ConcurrentHashMap<String, ConcurrentHashMap<WType, String>> site_map) {
		this.setCityName(city);
		this.setCountryName(country);
		this.setUrl_map(url_map);
		this.setSite_map(site_map);
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String printUrl() {
		String res = "\n";
		for (Object k: url_map.keySet()) {
			res += k.toString() + ": " + url_map.get(k) + "\n";
		}
		return res;
	}
	public String printSiteMap() {
		String res = "\n";
		for (Object k: site_map.keySet()) {
			res += k.toString() + ": " + printRegex(site_map.get(k)) + "\n";
		}
		return res;
	}
	public String printRegex(ConcurrentHashMap<WType, String> concurrentHashMap) {
		String res = "\n";
		for (Object k: concurrentHashMap.keySet()) {
			res += k.toString() + ": " + concurrentHashMap.get(k) + "\n";
		}
		return res;
	}
	public String ToString() {
		return "City: "+ this.getCityName() + "\nCountry: "+ this.getCountryName() + "\nURLs:" + printUrl() + "\nRegex:" + printSiteMap();	
	}
	public ConcurrentHashMap<String, ConcurrentHashMap<WType, String>> getSite_map() {
		return site_map;
	}
	public void setSite_map(ConcurrentHashMap<String, ConcurrentHashMap<WType, String>> site_map) {
		this.site_map = site_map;
	}
	public ConcurrentHashMap<String, String> getUrl_map() {
		return url_map;
	}
	public void setUrl_map(ConcurrentHashMap<String, String> url_map) {
		this.url_map = url_map;
	}
	
}

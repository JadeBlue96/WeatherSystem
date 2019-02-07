package model;

public class ConfigData {
	private String city;
	private String country;
	private String site;
	
	public ConfigData() {
		city = country = site = "";
	}
	public ConfigData(String city, String country, String site)
	{
		this.city = city;
		this.country = country;
		this.site = site;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	@Override
	public String toString() {
		return "ConfigData [city=" + city + ", country=" + country + ", site=" + site + "]";
	}
	
	

}

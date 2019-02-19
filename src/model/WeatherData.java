package model;

import java.util.List;

public class WeatherData {
	
	private Long id;
	private Integer temp;
	private Integer feel_temp;
	private String status;
	private Wind wind_data;
	private Additional additional_data;
	private ConfigData config_data;
	
	private Long weather_add_id;
	private Long weather_config_id;
	private Long weather_wind_id;
	
	public WeatherData() {
		temp = 0;
		feel_temp = 0;
		status = "";
		wind_data = new Wind();
		additional_data = new Additional();
		setConfig_data(new ConfigData());
		weather_add_id = weather_config_id = weather_wind_id = (long) 0;
	}
	public WeatherData(Integer temp, Integer feel_temp, String status, Wind wind_data, Additional additional_data, ConfigData config_data) {
		this.temp = temp;
		this.status = status;
		this.wind_data = wind_data;
		this.additional_data = additional_data;
		this.setConfig_data(config_data);
	}
	public WeatherData(Long id,Integer temp, Integer feel_temp, String status,
			Long weather_add_id, Long weather_config_id, Long weather_wind_id) {
		this.id = id;
		this.temp = temp;
		this.feel_temp = feel_temp;
		this.status = status;
		this.weather_add_id = weather_add_id;
		this.weather_config_id = weather_config_id;
		this.weather_wind_id = weather_wind_id;
	}
	public Wind getWind_data() {
		return wind_data;
	}
	public void setWind_data(Wind wind_data) {
		this.wind_data = wind_data;
	}
	public Additional getAdditional_data() {
		return additional_data;
	}
	public void setAdditional_data(Additional additional_data) {
		this.additional_data = additional_data;
	}
	public Integer getTemp() {
		return temp;
	}
	public void setTemp(Integer temp) {
		this.temp = temp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "WeatherData [temp=" + temp + "°C, feel_temp=" + feel_temp + "°C, status=" + status + ", wind_data=" + wind_data.toString() + ", additional_data="
				+ additional_data.toString() + ", " + config_data.toString() + "]";
	}
	public Integer getFeel_temp() {
		return feel_temp;
	}
	public void setFeel_temp(Integer feel_temp) {
		this.feel_temp = feel_temp;
	}
	public ConfigData getConfig_data() {
		return config_data;
	}
	public void setConfig_data(ConfigData config_data) {
		this.config_data = config_data;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public Long getWeather_config_id() {
		return weather_config_id;
	}
	public Long getWeather_add_id() {
		return weather_add_id;
	}
	public Long getWeather_wind_id() {
		return weather_wind_id;
	}
	
	

	
}

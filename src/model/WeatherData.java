package model;

public class WeatherData {
	private Integer temp;
	private Integer feel_temp;
	private String status;
	private Wind wind_data;
	private Additional additional_data;
	
	public WeatherData() {
		temp = 0;
		feel_temp = 0;
		status = "";
		wind_data = new Wind();
		additional_data = new Additional();
	}
	public WeatherData(Integer temp, Integer feel_temp, String status, Wind wind_data, Additional additional_data) {
		this.temp = temp;
		this.status = status;
		this.wind_data = wind_data;
		this.additional_data = additional_data;
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
				+ additional_data.toString() + "]";
	}
	public Integer getFeel_temp() {
		return feel_temp;
	}
	public void setFeel_temp(Integer feel_temp) {
		this.feel_temp = feel_temp;
	}
	

	
}

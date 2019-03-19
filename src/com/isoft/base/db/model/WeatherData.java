package com.isoft.base.db.model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;

public class WeatherData {
    
    private long id;
    private int temp;
    private int feel_temp;
    private String status;
    private Wind wind_data;
    private Additional additional_data;
    private ConfigData config_data;
    private Timestamp query_date;
    
    private long weather_add_id;
    private long weather_config_id;
    private long weather_wind_id;
    
    public WeatherData() {
        temp = 0;
        feel_temp = 0;
        status = "";
        wind_data = new Wind();
        additional_data = new Additional();
        setConfig_data(new ConfigData());
        weather_add_id = weather_config_id = weather_wind_id = (long) 0;
        query_date = null;
    }
    public WeatherData(int temp, int feel_temp, String status, Wind wind_data, 
            Additional additional_data, ConfigData config_data, Timestamp query_date) {
        this.temp = temp;
        this.status = status;
        this.wind_data = wind_data;
        this.additional_data = additional_data;
        this.setConfig_data(config_data);
        this.query_date = query_date;
    }
    public WeatherData(long id, int temp, int feel_temp, String status,
            long weather_add_id, long weather_config_id, long weather_wind_id, Timestamp query_date) {
        this.id = id;
        this.temp = temp;
        this.feel_temp = feel_temp;
        this.status = status;
        this.weather_add_id = weather_add_id;
        this.weather_config_id = weather_config_id;
        this.weather_wind_id = weather_wind_id;
        this.query_date = query_date;
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
    public void setTemp(int temp) {
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
        return "WeatherData [temp=" + temp + "�C, feel_temp=" + feel_temp + "�C, status=" + status + ", wind_data=" + wind_data.toString() + ", additional_data="
                + additional_data.toString() + ", " + config_data.toString() + "]" + " [Date added:" + query_date.toString() + "]";
    }
    public Integer getFeel_temp() {
        return feel_temp;
    }
    public void setFeel_temp(int feel_temp) {
        this.feel_temp = feel_temp;
    }
    public ConfigData getConfig_data() {
        return config_data;
    }
    public void setConfig_data(ConfigData config_data) {
        this.config_data = config_data;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public long getWeather_config_id() {
        return weather_config_id;
    }
    public long getWeather_add_id() {
        return weather_add_id;
    }
    public long getWeather_wind_id() {
        return weather_wind_id;
    }
    public Timestamp getQuery_date() {
        return query_date;
    }
    public void setQuery_date(Timestamp query_date) {
        this.query_date = query_date;
    }
    
    

    
}

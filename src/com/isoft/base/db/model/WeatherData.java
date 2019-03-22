package com.isoft.base.db.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "Weather")
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "weather_id")
    private long id;
    
    @Column(name = "temp")
    private int temp;
    
    @Column(name = "feel_temp")
    private int feel_temp;
    
    @Column(name = "status", length = 50)
    private String status;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="weather_wind_id")
    private Wind wind_data;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="weather_add_id")
    private Additional additional_data;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="weather_config_id")
    private ConfigData config_data;
    
    @Column(name = "query_date")
    private Timestamp query_date;
    
    
    public WeatherData() {
        temp = 0;
        feel_temp = 0;
        status = "";
        wind_data = new Wind();
        additional_data = new Additional();
        setConfig_data(new ConfigData());
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
            Timestamp query_date) {
        this.id = id;
        this.temp = temp;
        this.feel_temp = feel_temp;
        this.status = status;
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
        return "WeatherData [temp=" + temp + "°C, feel_temp=" + feel_temp + "°C, status=" + status + ", wind_data=" + wind_data.toString() + ", additional_data="
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
    
   
    public Timestamp getQuery_date() {
        return query_date;
    }
    public void setQuery_date(Timestamp query_date) {
        this.query_date = query_date;
    }
    
    

    
}

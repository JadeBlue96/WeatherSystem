package com.isoft.rest.db.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "Weather")
@EntityListeners(AuditingEntityListener.class)
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "weather_id")
    private long id;
    
    @Column(name = "temp")
    private Integer temp;
    
    @Column(name = "feel_temp")
    private Integer feel_temp;
    
    @Column(name = "status", length = 50)
    private String status;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "weather_wind_id", unique = true)
    private Wind wind_data;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "weather_add_id", unique = true)
    private Additional additional_data;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name ="weather_config_id", unique = true)
    private ConfigData config_data;
    
    @Column(name = "query_date")
    private Timestamp query_date;
    
    @Column(name = "day")
    private Integer day;
    
    @Column(name = "month")
    private String month;
    
    @Column(name = "year")
    private Integer year;
    
    
    public WeatherData() {
        temp = null;
        feel_temp = null;
        status = "";
        wind_data = new Wind();
        additional_data = new Additional();
        setConfig_data(new ConfigData());
        query_date = null;
        day = null;
        year = null;
        month = "";
    }
    public WeatherData(int temp, int feel_temp, String status, Wind wind_data, 
            Additional additional_data, ConfigData config_data, Timestamp query_date) {
        this.temp = temp;
        this.status = status;
        this.wind_data = wind_data;
        this.additional_data = additional_data;
        this.setConfig_data(config_data);
        this.query_date = query_date;
        this.day = this.query_date.getDay();
        this.month = Month.of(this.query_date.getMonth()).name();
        this.year = this.query_date.getYear();
    }
    public WeatherData(long id, int temp, int feel_temp, String status,
            Timestamp query_date) {
        this.id = id;
        this.temp = temp;
        this.feel_temp = feel_temp;
        this.status = status;
        this.query_date = query_date;
        this.day = this.query_date.getDay();
        this.month = Month.of(this.query_date.getMonth()).name();
        this.year = this.query_date.getYear();
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
    
   
    public Timestamp getQuery_date() {
        return query_date;
    }
    public void setDay(Integer day) {
        this.day = day;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getDay() {
        return day;
    }
    public String getMonth() {
        return month;
    }
    public Integer getYear() {
        return year;
    }
    
    public void setQuery_date() {
        this.query_date = Timestamp.from(Instant.now());
        LocalDate currentDate = LocalDate.now();
        
        int dom = currentDate.getDayOfMonth();
        Month m = currentDate.getMonth();
        int y = currentDate.getYear();

        setDay(dom);
        setMonth(m.name());
        setYear(y);
    }
    
    

    
}

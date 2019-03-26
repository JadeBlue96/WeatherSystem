package com.isoft.base.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Additional")
public class Additional {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "add_id")
    private long id;
    
    @Column(name = "humidity")
    private Integer humidity;
    
    @Column(name = "visibility")
    private Double visibility;
    
    @Column(name = "pressure")
    private Integer pressure;
    
    public Additional() {
        humidity = pressure = null;
        visibility = null;
        id = (long) 0;
    }
    public Additional(int humidity, double visibility, int pressure) {
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
    }
    public Additional(long id, int humidity, double visibility, int pressure) {
        this.id = id;
        this.humidity = humidity;
        this.visibility = visibility;
        this.pressure = pressure;
    }
    
    
    public Integer getHumidity() {
        return humidity;
    }
    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
    
    
    public Double getVisibility() {
        return visibility;
    }
    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }
    
    
    public Integer getPressure() {
        return pressure;
    }
    public void setPressure(int pressure) {
        this.pressure = pressure;
    }
    @Override
    public String toString() {
        return "Additional [humidity=" + humidity + " %, visibility=" + visibility + " km, pressure=" + pressure + " hPa]";
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    
    public Long getId() {
        return id;
    }
    
    
}
